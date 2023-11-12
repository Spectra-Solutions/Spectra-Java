package SpectraSprint02.DAO;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Conexao {
    JdbcTemplate conexaoDoBancoMySQL;
    JdbcTemplate conexaoDoBancoSQLServer;

    public Conexao() {/*
        BasicDataSource dataSource01 = new BasicDataSource();
        BasicDataSource dataSource02 = new BasicDataSource();

        dataSource01.setDriverClassName("com.mysql.jdbc.Driver");

        dataSource01.setUrl("jdbc:mysql://localhost:3306/Spectra");
        dataSource01.setUsername("Spectra");
        dataSource01.setPassword("Spectra123");

        dataSource02.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource02.setUrl("jdbc:sqlserver://52.45.176.88:1433;database=Spectra");
        dataSource02.setUsername("Spectra");
        dataSource02.setPassword("Spectra123");

        conexaoDoBanco = new JdbcTemplate(dataSource01);
        conexaoDoBanco = new JdbcTemplate(dataSource02);*/

        // Configuração para MySQL
        BasicDataSource dataSourceMySQL = new BasicDataSource();
        dataSourceMySQL.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceMySQL.setUrl("jdbc:mysql://localhost:3306/Spectra");
        dataSourceMySQL.setUsername("Spectra");
        dataSourceMySQL.setPassword("Spectra123");
        conexaoDoBancoMySQL = new JdbcTemplate(dataSourceMySQL);

        // Configuração para SQL Server
        BasicDataSource dataSourceSQLServer = new BasicDataSource();
        dataSourceSQLServer.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSourceSQLServer.setUrl("jdbc:sqlserver://52.45.176.88:1433;databaseName=Spectra;encrypt=true;trustServerCertificate=true;");
        dataSourceSQLServer.setUsername("Spectra");
        dataSourceSQLServer.setPassword("Spectra123");
        conexaoDoBancoSQLServer = new JdbcTemplate(dataSourceSQLServer);
    }

    public JdbcTemplate getConexaoDoBancoMySQL() {
        return conexaoDoBancoMySQL;
    }

    public JdbcTemplate getConexaoDoBancoSQLServer() {
        return conexaoDoBancoSQLServer;
    }
}
