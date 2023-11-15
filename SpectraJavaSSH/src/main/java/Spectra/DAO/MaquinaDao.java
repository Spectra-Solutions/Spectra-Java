package Spectra.DAO;

import Spectra.DTO.Maquina;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class MaquinaDao {
    Conexao conexao = new Conexao();
    JdbcTemplate conMysql = conexao.getConexaoDoBancoMySQL();
    JdbcTemplate conSqlServer = conexao.getConexaoDoBancoSQLServer();
    Maquina maquina = new Maquina();

    public void getFkEmpresa(String nome, String secao, String email, String senha){
        String sql = "SELECT fkEmpresa FROM Funcionario WHERE emailFunc = ? and senhaFunc = ?";

        Integer idEmpresa = null;

        try{
            idEmpresa = conMysql.queryForObject(sql, Integer.class, email, senha);
            maquina.setFkEmpresa(idEmpresa);

            try{
                idEmpresa = conSqlServer.queryForObject(sql, Integer.class, email, senha);
                maquina.setFkEmpresaSqlServer(idEmpresa);
                salvarMaquina(nome, secao);
            } catch (EmptyResultDataAccessException e){
                System.out.println("Nenhum resultado encontrado na fkEmpresa!!");
            }

        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado na fkEmpresa!!");
        }
    }

    public Boolean existHostName(String hostName){
        return conMysql.queryForObject("SELECT EXISTS (select 1 FROM Maquina WHERE hostName = ?) as existe",
                new Object[] {hostName}, Boolean.class);
    }

    public Boolean existHostNameSqlServer(String hostName){
        return conSqlServer.queryForObject("SELECT CASE WHEN EXISTS (SELECT 1 FROM Maquina WHERE hostName = ?) THEN 1 ELSE 0 END AS existe",
                new Object[] {hostName}, Boolean.class);
    }

    public void salvarMaquina(String nome, String secao){
        conMysql.update("INSERT INTO Maquina (idMaquina, hostName, nome, sistemaOperacional, secao, qtdDisco, fkEmpresaMaquina) VALUES (?, ?, ?, ?, ?, ?, ?)",
                maquina.getIdMaquina(), maquina.getHostName(), nome, maquina.getSistemaOperacional(), secao, maquina.getQtdDisco(), maquina.getFkEmpresa());

        conSqlServer.update("INSERT INTO Maquina (hostName, nome, sistemaOperacional, secao, qtdDisco, fkEmpresaMaquina) VALUES (?, ?, ?, ?, ?, ?)",
                maquina.getHostName(), nome, maquina.getSistemaOperacional(), secao, maquina.getQtdDisco(), maquina.getFkEmpresaSqlServer());
    }
}
