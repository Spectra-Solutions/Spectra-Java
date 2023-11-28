package SpectraSprint02.DAO;

import SpectraSprint02.DTO.Funcionario;
import SpectraSprint02.DTO.Maquina;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class FuncionarioDao {
    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();

    public Boolean existEmail(String email, String senha){

        System.out.println("validando");

        return con.queryForObject("SELECT EXISTS(select 2 FROM Funcionario WHERE emailFunc = ? and senhaFunc = ?) as existe",
        new Object[] {email, senha}, Boolean.class);
    }
}
