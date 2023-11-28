package Spectra.DAO;

import Spectra.Connection.ConexaoMysQl;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

public abstract class Dao {
    ConexaoMysQl conexaoMysQl = new ConexaoMysQl();
    protected JdbcTemplate conMySQl = conexaoMysQl.getConexaoMySQl();

    public Dao(JdbcTemplate conMySQl) {
        this.conMySQl = conMySQl;
    }

    public Dao() {}

    public abstract void getFkMaquina(String hostName) throws IOException;
    public abstract void getFkComponente() throws IOException;
    public abstract void salvarDados() throws IOException;
}
