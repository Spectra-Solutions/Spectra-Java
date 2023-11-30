package Spectra.DAO;


import Spectra.Connection.ConexaoMysQl;
import Spectra.Connection.ConexaoSQLServer;
import Spectra.DTO.JanelasAbertas;
import Spectra.DTO.Maquina;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JanelasAbertasDao {
    ConexaoMysQl conexao = new ConexaoMysQl();
    JdbcTemplate con = conexao.getConexaoMySQl();
    ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
    JdbcTemplate conSqlServer = conexaoSQLServer.getConexaoSqlServer();
    JanelasAbertas janelasAbertas = new JanelasAbertas();

    public Integer getfkMaquina(){
        Maquina maquina = new Maquina();
        String sql = "SELECT idMaquina FROM Maquina where hostName = ?";
        Integer idMaquina = null;

        try {
            idMaquina = conSqlServer.queryForObject(sql, Integer.class, maquina.getHostName());
        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado no idMaquina Janelas");
        }
        return idMaquina;
    }

    public List <String> getJanelasProibidas(Integer idMaquina) {
        String sql = "SELECT janelaProibida FROM proibicoesJanela WHERE fkMaquinaProibida = ?";
        List<String> janelasProibidas = null;

        try {
            janelasProibidas = conSqlServer.queryForObject(sql, List.class, idMaquina);
        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado");
        }
        return janelasProibidas;
    }

    public void registrarInfracao(Integer idMaquina, String janelaInfratora){
        conSqlServer.update("""
                INSERT INTO infracaoJanela(fkMaquinaInfratora, janelaProibidaAberta) 
                VALUES (?, ?)""", idMaquina, janelaInfratora);
    }
}
