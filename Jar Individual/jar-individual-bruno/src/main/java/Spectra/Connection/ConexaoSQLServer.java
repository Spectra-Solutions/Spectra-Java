package Spectra.Connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoSQLServer {
    JdbcTemplate conexaoSqlServer;
    public ConexaoSQLServer() {
        BasicDataSource dataSourceSqlServer = new BasicDataSource();

        dataSourceSqlServer.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSourceSqlServer.setUrl("jdbc:sqlserver://44.216.221.58:1433;databaseName=Spectra;encrypt=true;trustServerCertificate=true");
        dataSourceSqlServer.setUsername("Spectra");
        dataSourceSqlServer.setPassword("Spectra123");

        conexaoSqlServer = new JdbcTemplate(dataSourceSqlServer);
    }

    public JdbcTemplate getConexaoSqlServer() {
        return conexaoSqlServer;
    }
}
