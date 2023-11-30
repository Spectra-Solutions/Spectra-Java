package Spectra.DAO;

import Spectra.Connection.ConexaoMysQl;
import Spectra.Connection.ConexaoSQLServer;
import Spectra.DTO.MemoriaRam;
import Spectra.Log.Log;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

public class MemoriaRamDao extends Dao{
    MemoriaRam memoriaRam = new MemoriaRam();
    public MemoriaRamDao(){
        super();
    }

    @Override
    public void getFkMaquina(String hostName) throws IOException {
        String sql = "SELECT idMaquina FROM Maquina WHERE hostName = ?";

        try {
            Integer idMaquina = conSqlServer.queryForObject(sql, Integer.class, hostName);
            memoriaRam.setFkMaquina(idMaquina);
            getFkComponente();
        }

        catch (EmptyResultDataAccessException e){
            System.err.println("Nenhum resultado encontrado no fkMaquina Cpu");
        }
    }

    @Override
    public void getFkComponente() throws IOException {
        String sql = "SELECT idComponente FROM Componente WHERE idComponente = 2";

        try {
            Integer idComponente = conSqlServer.queryForObject(sql, Integer.class);
            memoriaRam.setFkComponente(idComponente);
            salvarDados();
        }

        catch (EmptyResultDataAccessException e){
            System.err.println("Nenhum resultado encontrado no fkComponente Cpu");
        }
    }

    @Override
    public void salvarDados() throws IOException {
        String sql = "INSERT INTO RegistroComponente (armazenamentoTotal, consumoAtual, armazenamentoDisponivel, fkComponente, fkMaquina) VALUES (?, ?, ?, ?, ?)";
        Integer linhasAlteradas = conSqlServer.update(sql, memoriaRam.getArmazenamentoTotal(), memoriaRam.getConsumoAtual(), memoriaRam.getArmazenamentoDisponivel(), memoriaRam.getFkComponente(), memoriaRam.getFkMaquina());

        if (linhasAlteradas > 0){
            System.out.println("Inserção no SqlServer MemoriaRam realizada com sucesso!");
        }

        else {
            System.err.println("Erro no cadastro dos dados da Memoria ram no SqlServer!");

            String sql1 = "INSERT INTO RegistroComponente (idRegistroComponente, armazenamentoTotal, consumoAtual, armazenamentoDisponivel) VALUES (?, ?, ?, ?)";
            Integer linhasAlteradas1 = conMysql.update(sql1, memoriaRam.getIdRegistro(), memoriaRam.getArmazenamentoTotal(), memoriaRam.getConsumoAtual(), memoriaRam.getArmazenamentoDisponivel());

            if (linhasAlteradas1 > 0){
                System.out.println("Inserção no Mysql MemoriaRam realizada com sucesso!");
            }

            else {
                System.err.println("Erro no cadastro dos dados da Memoria ram no MySQL!");
            }
        }
    }
}
