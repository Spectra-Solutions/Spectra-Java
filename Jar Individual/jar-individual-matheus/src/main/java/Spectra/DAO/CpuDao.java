package Spectra.DAO;

import Spectra.Connection.ConexaoMysQl;
import Spectra.Connection.ConexaoSQLServer;
import Spectra.DTO.Cpu;
import Spectra.Log.Log;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

public class CpuDao extends Dao{
    Cpu cpu = new Cpu();

    public CpuDao(){
        super();
    }

    @Override
    public void getFkMaquina(String hostName) throws IOException {
        String sql = "SELECT idMaquina FROM Maquina WHERE hostName = ?";

        try {
            Integer idMaquina = conSqlServer.queryForObject(sql, Integer.class, hostName);
            cpu.setFkMaquina(idMaquina);
            getFkComponente();
        }

        catch (EmptyResultDataAccessException e){
            System.err.println("Nenhum resultado encontrado no fkMaquina Cpu");
        }
    }

    @Override
    public void getFkComponente() throws IOException {
        String sql = "SELECT idComponente FROM Componente WHERE idComponente = 1";

        try {
            Integer idComponente = conSqlServer.queryForObject(sql, Integer.class);
            cpu.setFkComponente(idComponente);
            salvarDados();
        }

        catch (EmptyResultDataAccessException e){
            System.err.println("Nenhum resultado encontrado no fkComponente Cpu");
        }
    }

    @Override
    public void salvarDados() throws IOException {
        String sql = "INSERT INTO RegistroComponente (especificacao, consumoAtual, fkComponente, fkMaquina) VALUES (?, ?, ?, ?)";
        Integer linhasAlteradas = conSqlServer.update(sql, cpu.getEspecificacao(), cpu.getConsumoAtual(), cpu.getFkComponente(), cpu.getFkMaquina());

        if (linhasAlteradas > 0) {
            System.out.println("Inserção no SqlServer cpu realizada com sucesso!");
        } else {
            System.err.println("Erro no cadastro dos dados da cpu no SqlServer!");

            String sql1 = "INSERT INTO RegistroComponente (idRegistroComponente, especificacao, consumoAtual) VALUES (?, ?, ?)";
            Integer linhasAlteradas1 = conMysql.update(sql1, cpu.getIdRegistro(), cpu.getEspecificacao(), cpu.getConsumoAtual());

            if (linhasAlteradas1 > 0){
                System.out.println("Inserção no Mysql cpu realizada com sucesso!");
            }

            else {
                System.err.println("Erro no cadastro dos dados da cpu no MySQL!");
            }
        }
    }
}
