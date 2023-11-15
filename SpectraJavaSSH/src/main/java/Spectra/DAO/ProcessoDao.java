package Spectra.DAO;

import Spectra.DTO.ProcessoClass;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processos.Processo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ProcessoDao {
    Conexao conexao = new Conexao();
    JdbcTemplate conMysql = conexao.getConexaoDoBancoMySQL();
    JdbcTemplate conSqlServer = conexao.getConexaoDoBancoSQLServer();
    ProcessoClass processoClass = new ProcessoClass();
    Looca looca = new Looca();
    List<Processo> processos = looca.getGrupoDeProcessos().getProcessos();

    public void getfkMaquina(String hostname){
        String sql = "SELECT idMaquina FROM Maquina WHERE hostname = ?";
        Integer idMaquina = null;

        try {
            idMaquina = conMysql.queryForObject(sql, Integer.class, hostname);
            processoClass.setFkMaquinaProcesso(idMaquina);

            try {
                idMaquina = conSqlServer.queryForObject(sql, Integer.class, hostname);
                processoClass.setFkMaquinaProcessoSqlServer(idMaquina);
                salvarDadosProcessos();
            } catch (EmptyResultDataAccessException e){
                System.out.println("Nenhum resultado no idMaquina no processo!");
            }

        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado no idMaquina no processo!");
        }
    }

    public void salvarDadosProcessos(){
        for (int i = 0; i < processos.size(); i++) {
            Processo processoDaVez = processos.get(i);

            processoClass.setPidProcesso(processoDaVez.getPid());
            processoClass.setNomeProcesso(processoDaVez.getNome());
            processoClass.setUsoCpu(processoDaVez.getUsoCpu());
            processoClass.setUsoMemoria(processoDaVez.getUsoMemoria());

            conMysql.update("INSERT INTO Processo (idProcesso, PidProcesso, nomeProcesso, usoCpu, usoMemoria, fkMaquinaProcesso) VALUES (?, ?, ?, ?, ?, ?)",
                    processoClass.getIdProcesso(), processoClass.getPidProcesso(), processoClass.getNomeProcesso(), processoClass.getUsoCpu(), processoClass.getUsoMemoria(), processoClass.getFkMaquinaProcesso());

            conSqlServer.update("INSERT INTO Processo (PidProcesso, nomeProcesso, usoCpu, usoMemoria, fkMaquinaProcesso) VALUES (?, ?, ?, ?, ?)",
                    processoClass.getPidProcesso(), processoClass.getNomeProcesso(), processoClass.getUsoCpu(), processoClass.getUsoMemoria(), processoClass.getFkMaquinaProcessoSqlServer());
        }
    }
}
