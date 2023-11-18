package Spectra.DAO;

import Spectra.DTO.Cpu;
import org.springframework.dao.EmptyResultDataAccessException;

public class CpuDao extends Dao{
    Cpu cpu = new Cpu();

    public CpuDao(){
        super();
    }

    @Override
    public void getFkMaquina(String hostName) {
        String sql = "SELECT idMaquina FROM Maquina WHERE hostName = ?";

        try {
            Integer idMaquina = conMySQl.queryForObject(sql, Integer.class, hostName);
            cpu.setFkMaquina(idMaquina);
            getFkComponente();
        }

        catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado no fkMaquina Cpu");
        }
    }

    @Override
    public void getFkComponente() {
        String sql = "SELECT idComponente FROM Componente WHERE idComponente = 1";

        try {
            Integer idComponente = conMySQl.queryForObject(sql, Integer.class);
            cpu.setFkComponente(idComponente);
            salvarDados();
        }

        catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado no fkComponente Cpu");
        }
    }

    @Override
    public void salvarDados() {
        String sql = "INSERT INTO RegistroComponente (idRegistroComponente, especificacao, consumoAtual, fkComponente, fkMaquina) VALUES (?, ?, ?, ?, ?)";
        Integer linhasAlteradas = conMySQl.update(sql, cpu.getIdRegistro(), cpu.getEspecificacao(), cpu.getConsumoAtual(), cpu.getFkComponente(), cpu.getFkMaquina());

        if (linhasAlteradas > 0){
            System.out.println("Inserção no Mysql cpu realizada com sucesso!");
        }

        else {
            System.out.println("Erro ao inserir no MySQL cpu!");
        }
    }
}