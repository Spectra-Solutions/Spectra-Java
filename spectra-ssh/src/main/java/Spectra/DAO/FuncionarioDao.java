package Spectra.DAO;

import Spectra.Connection.ConexaoMysQl;
import Spectra.Connection.ConexaoSqlServer;
import org.springframework.jdbc.core.JdbcTemplate;

public class FuncionarioDao {
    ConexaoMysQl conexaoMysQl = new ConexaoMysQl();
    ConexaoSqlServer conexaoSqlServer = new ConexaoSqlServer();
    protected JdbcTemplate conMySQl = conexaoMysQl.getConexaoMySQl();
    protected JdbcTemplate conSqlServer = conexaoSqlServer.getConexaoSqlServer();

    public Boolean existEmail(String email, String senha) {
        String query = "SELECT EXISTS (SELECT 2 FROM Funcionario WHERE emailFunc = ? and senhaFunc = ?) as existe";

        Integer resultado = conMySQl.queryForObject(
                query,
                new Object[]{email, senha},
                (rs, rowNum) -> rs.getInt("existe")
        );

        return resultado != null && resultado == 1;
    }
}
