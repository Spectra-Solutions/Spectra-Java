package Spectra.DAO;

import Spectra.Connection.ConexaoMysQl;
import Spectra.Connection.ConexaoSQLServer;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

public abstract class Dao {
    ConexaoMysQl conexaoMysQl = new ConexaoMysQl();
    ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
    JdbcTemplate conMysql = conexaoMysQl.getConexaoMysql();
    JdbcTemplate conSqlServer = conexaoSQLServer.getConexaoSqlServer();

    public Dao(ConexaoMysQl conexaoMysQl, ConexaoSQLServer conexaoSQLServer, JdbcTemplate conMysql, JdbcTemplate conSqlServer) {
        this.conexaoMysQl = conexaoMysQl;
        this.conexaoSQLServer = conexaoSQLServer;
        this.conMysql = conMysql;
        this.conSqlServer = conSqlServer;
    }

    public Dao() {
    }

    public abstract void getFkMaquina (String hostName) throws IOException;
    public abstract void getFkComponente() throws IOException;
    public abstract void salvarDados() throws IOException;
}
