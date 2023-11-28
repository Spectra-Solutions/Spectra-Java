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
    Log log = new Log();

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
            log.setMensagem(String.format("Erro na busca do idMaquina baseado no hostName Cpu %s", e));
            log.gerarLog("erro");
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
            log.setMensagem(String.valueOf(e));
            log.gerarLog("erro");
            System.err.println("Nenhum resultado encontrado no fkComponente Cpu");
        }
    }

    @Override
    public void salvarDados() throws IOException {
        String sql = "INSERT INTO RegistroComponente (especificacao, consumoAtual, fkComponente, fkMaquina) VALUES (?, ?, ?, ?)";
        Integer linhasAlteradas = conSqlServer.update(sql, cpu.getEspecificacao(), cpu.getConsumoAtual(), cpu.getFkComponente(), cpu.getFkMaquina());

        if (linhasAlteradas > 0) {
            System.out.println("Inserção no Mysql cpu realizada com sucesso!");
        } else {
            log.setMensagem("Erro no cadastro dos dados da cpu no MySQL!");
            log.gerarLog("erro");
            System.err.println("Erro no cadastro dos dados da cpu no MySQL!");
        }
    }
}
