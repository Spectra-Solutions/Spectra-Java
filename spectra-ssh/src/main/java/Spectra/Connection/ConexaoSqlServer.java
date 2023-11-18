package Spectra.Connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoSqlServer {
//    Configuração para SQL Server

    JdbcTemplate conexaoSqlServer;

    public ConexaoSqlServer() {
        BasicDataSource dataSourceSqlServer = new BasicDataSource();

        dataSourceSqlServer.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSourceSqlServer.setUrl("jdbc:sqlserver://52.45.176.88;databaseName=Spectra;encrypt=true;trustServerCertificate=true");
        dataSourceSqlServer.setUsername("Spectra");
        dataSourceSqlServer.setPassword("Spectra123");
    }

    public JdbcTemplate getConexaoSqlServer() {
        return conexaoSqlServer;
    }
}
