package Spectra.DAO;

import Spectra.Connection.ConexaoMysQl;
import Spectra.Connection.ConexaoSQLServer;
import Spectra.DTO.Maquina;
import Spectra.Log.Log;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

public class MaquinaDao {
    ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
    protected JdbcTemplate conSqlServer = conexaoSQLServer.getConexaoSqlServer();
    Maquina maquina = new Maquina();

    public void getFkEmpresa(String nome, String secao, String email, String senha) throws IOException {
        String sql = "SELECT fkEmpresa FROM Funcionario WHERE emailFunc = ? and senhaFunc = ?";

        try {
            Integer idEmpresa = conSqlServer.queryForObject(sql, Integer.class, email, senha);
            maquina.setFkEmpresa(idEmpresa);
            salvarMaquina(nome, secao);
        }

        catch (EmptyResultDataAccessException e){
            System.err.println("Nenhum resultado encontrado na fkEmpresa maquina");
        }
    }

    public Boolean existHostName(String hostName) {
        String query = "SELECT CASE WHEN EXISTS (SELECT 1 FROM Maquina WHERE hostName = ?) THEN 1 ELSE 0 END AS existe;";

        Integer resultado = conSqlServer.queryForObject(
                query,
                new Object[]{hostName},
                (rs, rowNum) -> rs.getInt("existe")
        );

        return resultado != null && resultado == 1;
    }

    public void salvarMaquina(String nome, String secao) throws IOException {
        String sql1 = "INSERT INTO Maquina (hostName, nome, sistemaOperacional, secao, qtdDisco, tempoAtividade,fkEmpresaMaquina) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Integer linhasAlteradas = conSqlServer.update(sql1, maquina.getHostName(), nome, maquina.getSistemaOperacional(), secao, maquina.getQtdDisco(), maquina.getTempoAtividade(), maquina.getFkEmpresa());

        if (linhasAlteradas > 0){
            System.out.println("""
                    Maquina cadastrada!
                    
                    O monitoramento ja foi iniciado!
                    Acesse a dashboard para visualizar: http://44.216.221.58/home
                    """);
        }

        else {
            System.err.println("Erro no cadastro de uma máquina no SqlServer!");
        }
    }

    public void atualizarTempoAtividade() throws IOException {

        String sql = "SELECT idMaquina FROM Maquina WHERE hostName = ?";

        try {
            Integer idMaquina1 = conSqlServer.queryForObject(sql, Integer.class, maquina.getHostName());
            maquina.setIdMaquina(idMaquina1);
        }

        catch (EmptyResultDataAccessException e){
            System.err.println("Nenhum resultado encontrado no fkMaquina Maquina");
        }

        conSqlServer.update("UPDATE Maquina SET tempoAtividade = ? WHERE idMaquina = ?", maquina.getTempoAtividade(), maquina.getIdMaquina());

        try {
        } catch (EmptyResultDataAccessException e){
            System.err.println("Erro em atualizar o tempo da atividade %s");
        }
    }
}
