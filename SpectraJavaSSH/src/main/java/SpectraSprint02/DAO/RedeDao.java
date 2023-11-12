package SpectraSprint02.DAO;

import SpectraSprint02.DTO.Rede;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class RedeDao {
    Conexao conexao = new Conexao();
    JdbcTemplate conMysql = conexao.getConexaoDoBancoMySQL();
    JdbcTemplate conSqlServer = conexao.getConexaoDoBancoSQLServer();
    Rede rede = new Rede();

    public void getFkComponenteRede(String hostname){
        String sql = "SELECT idComponente FROM Componente WHERE idComponente = 4";
        Integer idComponenteRede = null;

        try{
            idComponenteRede = conMysql.queryForObject(sql, Integer.class);
            rede.setFkComponenteRede(idComponenteRede);

            try{
                idComponenteRede = conSqlServer.queryForObject(sql, Integer.class);
                rede.setFkComponenteRede(idComponenteRede);
                getfkMaquina(hostname);
            } catch (EmptyResultDataAccessException e){
                System.out.println("Nenhum resultado encontrado na Rede!");
            }

        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado na Rede!");
        }
    }

    public void getfkMaquina(String hostname){
        String sql = "SELECT idMaquina FROM Maquina WHERE hostname = ?";
        Integer idMaquina = null;

        try {
            idMaquina = conMysql.queryForObject(sql, Integer.class, hostname);
            rede.setFkMaquina(idMaquina);

            try {
                idMaquina = conSqlServer.queryForObject(sql, Integer.class, hostname);
                rede.setFkMaquinaSqlServer(idMaquina);
                salvarDadosRede();
            } catch (EmptyResultDataAccessException e){
                System.out.println("Nenhum resultado no idMaquina na rede!");
            }

        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado no idMaquina na rede!");
        }
    }

    public void salvarDadosRede(){
        conMysql.update("INSERT INTO RegistroComponente (idRegistroComponente, consumoUpload, consumoDownload, fkComponente, fkMaquina) VALUES (?, ?, ?, ?, ?)",
                rede.getIdRegistroRede(), rede.getConsumoUpload(), rede.getConsumoDownload(), rede.getFkComponenteRede(), rede.getFkMaquina());

        conSqlServer.update("INSERT INTO RegistroComponente (consumoUpload, consumoDownload, fkComponente, fkMaquina) VALUES (?, ?, ?, ?)",
                rede.getConsumoUpload(), rede.getConsumoDownload(), rede.getFkComponenteRede(), rede.getFkMaquinaSqlServer());
    }
}