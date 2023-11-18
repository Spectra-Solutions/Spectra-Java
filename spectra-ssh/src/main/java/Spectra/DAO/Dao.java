package Spectra.DAO;

import Spectra.Connection.ConexaoMysQl;
import Spectra.Connection.ConexaoSqlServer;
import Spectra.DTO.Maquina;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class Dao {
    ConexaoMysQl conexaoMysQl = new ConexaoMysQl();
    ConexaoSqlServer conexaoSqlServer = new ConexaoSqlServer();
    protected JdbcTemplate conMySQl = conexaoMysQl.getConexaoMySQl();
    protected JdbcTemplate conSqlServer = conexaoSqlServer.getConexaoSqlServer();

    public Dao(JdbcTemplate conMySQl, JdbcTemplate conSqlServer) {
        this.conMySQl = conMySQl;
        this.conSqlServer = conSqlServer;
    }

    public Dao() {}

    public abstract void getFkMaquina(String hostName);
    public abstract void getFkComponente();
    public abstract void salvarDados();
}
