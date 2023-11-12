package SpectraSprint02.DAO;

import SpectraSprint02.DTO.MemoriaRam;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class MemoriaRamDao {
    Conexao conexao = new Conexao();
    JdbcTemplate conMysql = conexao.getConexaoDoBancoMySQL();
    JdbcTemplate conSqlServer = conexao.getConexaoDoBancoSQLServer();
    MemoriaRam memoriaRam = new MemoriaRam();

    public void getFkComponenteRAM(){
        String sql = "SELECT idComponente FROM Componente WHERE idComponente = 2";

        Integer idComponenteRAM = null;

        try{
            idComponenteRAM = conMysql.queryForObject(sql, Integer.class);
            memoriaRam.setFkComponenteRAM(idComponenteRAM);

            try{
                idComponenteRAM = conSqlServer.queryForObject(sql, Integer.class);
                memoriaRam.setFkComponenteRAM(idComponenteRAM);
                getfkMaquina();
            } catch (EmptyResultDataAccessException e){
                System.out.println("Nenhum resultado encontrado na RAM!");
            }

        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado na RAM!");
        }
    }

    public void getfkMaquina(){
        String sql = "SELECT idMaquina FROM Maquina";
        Integer idMaquina = null;

        try {
            idMaquina = conMysql.queryForObject(sql, Integer.class);
            memoriaRam.setFkMaquina(idMaquina);

            try {
                idMaquina = conSqlServer.queryForObject(sql, Integer.class);
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