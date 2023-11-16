package Spectra.DAO;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import Spectra.DTO.MemoriaRam;

public class MemoriaRamDao {
    Conexao conexao = new Conexao();
    JdbcTemplate conMysql = conexao.getConexaoDoBancoMysql();
    JdbcTemplate conSqlServer = conexao.getConexaoDoBancoSqlServer();
    MemoriaRam memoriaRam = new MemoriaRam();

    public void getFkComponenteRAM(String hostname){
        String sql = "SELECT idComponente FROM Componente WHERE idComponente = 2";

        Integer idComponenteRAM = null;

        try{
            idComponenteRAM = conMysql.queryForObject(sql, Integer.class);
            memoriaRam.setFkComponenteRAM(idComponenteRAM);

            try{
                idComponenteRAM = conSqlServer.queryForObject(sql, Integer.class);
                memoriaRam.setFkComponenteRAM(idComponenteRAM);
                getfkMaquina(hostname);
            } catch (EmptyResultDataAccessException e){
                System.out.println("Nenhum resultado encontrado na RAM!");
            }

        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado na RAM!");
        }
    }

    public void getfkMaquina(String hostname){
        String sql = "SELECT idMaquina FROM Maquina WHERE hostname = ?";
        Integer idMaquina = null;

        try {
            idMaquina = conMysql.queryForObject(sql, Integer.class, hostname);
            memoriaRam.setFkMaquina(idMaquina);

            try {
                idMaquina = conSqlServer.queryForObject(sql, Integer.class, hostname);
                memoriaRam.setFkMaquinaSqlServer(idMaquina);
                salvarDadosRam();
            } catch (EmptyResultDataAccessException e){
                System.out.println("Nenhum resultado no idMaquina na ram!");
            }

        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado no idMaquina na ram!");
        }
    }

    public void salvarDadosRam(){
        conMysql.update("INSERT INTO RegistroComponente (idRegistroComponente, consumoAtual, armazenamentoTotal, armazenamentoDisponivel, fkComponente, fkMaquina) VALUES (?, ?, ?, ?, ?, ?)",
                memoriaRam.getIdRegistroMemoriaRAM(), memoriaRam.getConsumoAtual(), memoriaRam.getArmazenamentoTotal(), memoriaRam.getArmazenamentoDisponivel(), memoriaRam.getFkComponenteRAM(), memoriaRam.getFkMaquina());

        conSqlServer.update("INSERT INTO RegistroComponente (consumoAtual, armazenamentoTotal, armazenamentoDisponivel, fkComponente, fkMaquina) VALUES (?, ?, ?, ?, ?)" ,
                memoriaRam.getConsumoAtual(), memoriaRam.getArmazenamentoTotal(), memoriaRam.getArmazenamentoDisponivel(), memoriaRam.getFkComponenteRAM(), memoriaRam.getFkMaquinaSqlServer());
    }
}
