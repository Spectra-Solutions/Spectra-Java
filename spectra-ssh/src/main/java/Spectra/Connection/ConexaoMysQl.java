package Spectra.Connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoMysQl {
//    Configuração para MySQl

    JdbcTemplate conexaoMySQl;

    public ConexaoMysQl() {
        BasicDataSource dataSourceMySQl = new BasicDataSource();

        dataSourceMySQl.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceMySQl.setUrl("jdbc:mysql://localhost:3306/Spectra");
        dataSourceMySQl.setUsername("Spectra");
        dataSourceMySQl.setPassword("Spectra123");

        conexaoMySQl = new JdbcTemplate(dataSourceMySQl);
    }

    public JdbcTemplate getConexaoMySQl() {
        return conexaoMySQl;
    }
}
