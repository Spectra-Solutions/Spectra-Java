package Spectra.DAO;

import Spectra.Connection.ConexaoMysQl;
import Spectra.Connection.ConexaoSqlServer;
import Spectra.DTO.Maquina;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class MaquinaDao{
    ConexaoMysQl conexaoMysQl = new ConexaoMysQl();
    ConexaoSqlServer conexaoSqlServer = new ConexaoSqlServer();
    protected JdbcTemplate conMySQl = conexaoMysQl.getConexaoMySQl();
    protected JdbcTemplate conSqlServer = conexaoSqlServer.getConexaoSqlServer();
    Maquina maquina = new Maquina();

    public void getFkEmpresa(String nome, String secao, String email, String senha){
        String sql = "SELECT fkEmpresa FROM Funcionario WHERE emailFunc = ? and senhaFunc = ?";

        try {
            Integer idEmpresa = conMySQl.queryForObject(sql, Integer.class, email, senha);
            maquina.setFkEmpresa(idEmpresa);
            salvarMaquina(nome, secao);
        }

        catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado na fkEmpresa maquina");
        }
    }

    public Boolean existHostName(String hostName) {
        String query = "SELECT EXISTS (SELECT 1 FROM Maquina WHERE hostName = ?) as existe";

        Integer resultado = conMySQl.queryForObject(
                query,
                new Object[]{hostName},
                (rs, rowNum) -> rs.getInt("existe")
        );

        return resultado != null && resultado == 1;
    }

    public void salvarMaquina(String nome, String secao){
        String sql = "INSERT INTO Maquina (idMaquina, hostName, nome, sistemaOperacional, secao, qtdDisco, tempoAtividade,fkEmpresaMaquina) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Integer linhasAlteradas = conMySQl.update(sql, maquina.getIdMaquina(), maquina.getHostName(), nome, maquina.getSistemaOperacional(), secao, maquina.getQtdDisco(), maquina.getTempoAtividade(), maquina.getFkEmpresa());

        if (linhasAlteradas > 0){
            System.out.println("""
                    Maquina cadastrada!
                            
                    O monitoramento ja foi iniciado!
                    Acesse a dashboard para visualizar: http://34.234.237.115:3333
                    """);
        }

        else {
            System.out.println("Erro ao inserir no MySQL Maquina!");
        }
    }

}
