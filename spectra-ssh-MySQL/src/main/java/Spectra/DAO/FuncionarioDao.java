package Spectra.DAO;

import Spectra.Connection.ConexaoMysQl;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

public class FuncionarioDao {
    ConexaoMysQl conexaoMysQl = new ConexaoMysQl();
    protected JdbcTemplate conMySQl = conexaoMysQl.getConexaoMySQl();

    public Boolean existEmail(String email, String senha){
        String query = "SELECT EXISTS (SELECT 2 FROM Funcionario WHERE emailFunc = ? and senhaFunc = ?) as existe";

        Integer resultado = conMySQl.queryForObject(
                query,
                new Object[]{email, senha},
                (rs, rowNum) -> rs.getInt("existe")
        );

        return resultado != null && resultado == 1;
    }
}
