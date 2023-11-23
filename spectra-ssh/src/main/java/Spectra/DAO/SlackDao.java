package Spectra.DAO;

import Spectra.Connection.ConexaoMysQl;
import Spectra.DTO.Empresa;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class SlackDao {
    List<Empresa> empresas;
    List<String> empresasCadastradas;
    ConexaoMysQl conexaoMysQl = new ConexaoMysQl();
    protected JdbcTemplate conMySQl = conexaoMysQl.getConexaoMySQl();

    public SlackDao() {
        this.empresas = new ArrayList<>();
    }

    public void getNomeEmpresa(String hostName){
        String sql = "SELECT nomeEmpresa FROM EMPRESA";

        String nomeEmpresa = conMySQl.queryForObject(sql, String.class, hostName);

        for (Empresa e: empresas) {
            if (!nomeEmpresa.equalsIgnoreCase(e.getNomeEmpresa())){
                empresasCadastradas.add(nomeEmpresa);
            }
        }

    }
}
