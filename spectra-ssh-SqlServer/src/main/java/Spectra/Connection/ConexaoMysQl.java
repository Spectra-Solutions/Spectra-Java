package Spectra.Connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoMysQl {
    JdbcTemplate conexaoMysql;
    
    public ConexaoMysQl() {
        BasicDataSource dataSourceMySQl = new BasicDataSource();

        dataSourceMySQl.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceMySQl.setUrl("jdbc:mysql://localhost:3306/Spectra");
        dataSourceMySQl.setUsername("Spectra");
        dataSourceMySQl.setPassword("Spectra123");

        conexaoMysql = new JdbcTemplate(dataSourceMySQl);
    }

    public JdbcTemplate getConexaoMysql() {
        return conexaoMysql;
    }
}
