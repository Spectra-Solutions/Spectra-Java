package Spectra.DAO;

import org.springframework.jdbc.core.JdbcTemplate;

public class FuncionarioDao {
    Conexao conexao = new Conexao();
    JdbcTemplate conMysql = conexao.getConexaoDoBancoMysql();
    JdbcTemplate conSqlServer = conexao.getConexaoDoBancoSqlServer();
    public Boolean existEmail(String email, String senha){
        return conMysql.queryForObject("SELECT EXISTS(select 2 FROM Funcionario WHERE emailFunc = ? and senhaFunc = ?) as existe",
                new Object[] {email, senha}, Boolean.class);
    }

    public Boolean existEmailSqlServer(String email, String senha){
        return conSqlServer.queryForObject("SELECT CASE WHEN EXISTS (SELECT 2 FROM Funcionario WHERE emailFunc = ? and senhaFunc = ?) THEN 1 ELSE 0 END AS existe;\n",
                new Object[] {email, senha}, Boolean.class);
    }
}
