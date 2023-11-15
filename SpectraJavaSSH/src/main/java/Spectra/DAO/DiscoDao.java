package Spectra.DAO;

import Spectra.DTO.DiscoClass;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DiscoDao {
    Conexao conexao = new Conexao();
    JdbcTemplate conMysql = conexao.getConexaoDoBancoMySQL();
    JdbcTemplate conSqlServer = conexao.getConexaoDoBancoSQLServer();
    DiscoClass disco = new DiscoClass();
    Looca looca = new Looca();
    List<Volume> volumes = looca.getGrupoDeDiscos().getVolumes();
    private Integer GB = 1024 * 1024 * 1024;

    public void getFkComponenteDisco(String hostname){
        String sql = "SELECT idComponente FROM Componente WHERE idComponente = 3";

        Integer idComponenteDisco = null;

        try{
            idComponenteDisco = conMysql.queryForObject(sql, Integer.class);
            disco.setFkComponenteDisco(idComponenteDisco);

            try{
                idComponenteDisco = conSqlServer.queryForObject(sql, Integer.class);
                disco.setFkComponenteDisco(idComponenteDisco);
                getfkMaquina(hostname);
            } catch (EmptyResultDataAccessException e){
                System.out.println("Nenhum resultado encontrado no Disco!");
            }

        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado no Disco!");
        }
    }

    public void getfkMaquina(String hostname){
        String sql = "SELECT idMaquina FROM Maquina WHERE hostname = ?";
        Integer idMaquina = null;

        try {
            idMaquina = conMysql.queryForObject(sql, Integer.class, hostname);
            disco.setFkMaquina(idMaquina);

            try {
                idMaquina = conSqlServer.queryForObject(sql, Integer.class, hostname);
                disco.setFkMaquinaSqlServer(idMaquina);
                salvarDadosDisco();
            } catch (EmptyResultDataAccessException e){
                System.out.println("Nenhum resultado no idMaquina na ram!");
            }

        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado no idMaquina na ram!");
        }
    }

    public void salvarDadosDisco(){
        for (int i = 0; i < volumes.size(); i++){
            Volume volumeDaVez = volumes.get(i);

            disco.setConsumoAtual(volumeDaVez.getTotal() - volumeDaVez.getDisponivel());
            disco.setArmazenamentoTotal((double) (volumeDaVez.getTotal() / GB));
            disco.setArmazenamentoDisponivel((double) (volumeDaVez.getDisponivel() / GB));

            conMysql.update("INSERT INTO RegistroComponente (idRegistroComponente, consumoAtual, armazenamentoTotal, armazenamentoDisponivel, fkComponente, fkMaquina) VALUES (?, ?, ?, ?, ?, ?)",
                    disco.getIdRegistroDisco(), disco.getConsumoAtual(), disco.getArmazenamentoTotal(), disco.getArmazenamentoDisponivel(), disco.getFkComponenteDisco(), disco.getFkMaquina());

            conSqlServer.update("INSERT INTO RegistroComponente (consumoAtual, armazenamentoTotal, armazenamentoDisponivel, fkComponente, fkMaquina) VALUES (?, ?, ?, ?, ?)",
                    disco.getConsumoAtual(), disco.getArmazenamentoTotal(), disco.getArmazenamentoDisponivel(), disco.getFkComponenteDisco(), disco.getFkMaquinaSqlServer());
        }
    }
}
