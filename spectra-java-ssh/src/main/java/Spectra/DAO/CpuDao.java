package Spectra.DAO;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import Spectra.DTO.Cpu;

public class CpuDao {
    Conexao conexao = new Conexao();
    JdbcTemplate conMysql = conexao.getConexaoDoBancoMysql();
    JdbcTemplate conSqlServer = conexao.getConexaoDoBancoSqlServer();
    Cpu cpu = new Cpu();

    public void getFkComponenteCPU(String hostname){
        String sql = "SELECT idComponente FROM Componente WHERE idComponente = 1";

        Integer idComponenteCpu = null;

        try{
            idComponenteCpu = conMysql.queryForObject(sql, Integer.class);
            cpu.setFkComponenteCPU(idComponenteCpu);

            try{
                idComponenteCpu = conSqlServer.queryForObject(sql, Integer.class);
                cpu.setFkComponenteCPU(idComponenteCpu);

                getfkMaquina(hostname);
            } catch (EmptyResultDataAccessException e){
                System.out.println("Nenhum resultado encontrado na CPU!");
            }

        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado na CPU!");
        }

    }

    public void getfkMaquina(String hostname){
        String sql = "SELECT idMaquina FROM Maquina WHERE hostname = ?";
        Integer idMaquina = null;

        try {
            idMaquina = conMysql.queryForObject(sql, Integer.class, hostname);
            cpu.setFkMaquina(idMaquina);

            try {
                idMaquina = conSqlServer.queryForObject(sql, Integer.class, hostname);
                cpu.setFkMaquinaSqlServer(idMaquina);
                salvarDadosCPU();

            } catch (EmptyResultDataAccessException e){
                System.out.println("Nenhum resultado no idMaquina na CPU!");
            }

        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado no idMaquina na CPU!");
        }
    }

    public void salvarDadosCPU(){
        conMysql.update("INSERT INTO RegistroComponente (idRegistroComponente, especificacao, consumoAtual, fkComponente, fkMaquina) VALUES (?, ?, ?, ?, ?)",
                cpu.getIdRegistroCPU(), cpu.getEspecificacao(), cpu.getConsumoAtual(), cpu.getFkComponenteCPU(), cpu.getFkMaquina());

        conSqlServer.update("INSERT INTO RegistroComponente (especificacao, consumoAtual, fkComponente, fkMaquina) VALUES (?, ?, ?, ?)",
                cpu.getEspecificacao(), cpu.getConsumoAtual(), cpu.getFkComponenteCPU(), cpu.getFkMaquinaSqlServer());
    }
}
