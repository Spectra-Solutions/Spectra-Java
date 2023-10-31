package JAR.DAO;

import JAR.DTO.JanelasAbertas;
import JAR.DTO.Maquina;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JanelasAbertasDao {
    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();
    JanelasAbertas janelasAbertas = new JanelasAbertas();

    public Integer getfkMaquina(){
        Maquina maquina = new Maquina();
        String sql = "SELECT idMaquina FROM Maquina where hostName = ?";
        Integer idMaquina = null;

        try {
            idMaquina = con.queryForObject(sql, Integer.class, maquina.getHostName());
        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado no idMaquina Janelas");
        }
        return idMaquina;
    }

    public List <String> getJanelasProibidas(Integer idMaquina) {
        String sql = "SELECT janelaProibida FROM proibicoesJanela WHERE fkMaquinaProibida = ?";
        List<String> janelasProibidas = null;

        try {
            janelasProibidas = con.queryForObject(sql, List.class, idMaquina);
        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado");
        }
        return janelasProibidas;
    }

    public void registrarInfracao(Integer idMaquina, String janelaInfratora){
        con.update("""
                INSERT INTO infracaoJanela(fkMaquinaInfratora, janelaProibidaAberta) 
                VALUES (?, ?)""", idMaquina, janelaInfratora);
    }
}
