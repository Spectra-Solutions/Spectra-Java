package Spectra.DAO;

import Spectra.Connection.ConexaoSQLServer;
import org.springframework.jdbc.core.JdbcTemplate;

public class FuncionarioDao {
    ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
    protected JdbcTemplate conSqlServer = conexaoSQLServer.getConexaoSqlServer();

    public Boolean existEmailSqlServer(String email, String senha) {
        String query = "SELECT CASE WHEN EXISTS (SELECT 2 FROM Funcionario WHERE EmailFunc = ? and SenhaFunc = ?) THEN 1 ELSE 0 END AS existe;";

        Integer resultado = conSqlServer.queryForObject(
                query,
                new Object[]{email, senha},
                (rs, rowNum) -> rs.getInt("existe")
        );

        return resultado != null && resultado == 1;
    }
}
