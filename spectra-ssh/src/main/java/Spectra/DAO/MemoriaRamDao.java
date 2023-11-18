package Spectra.DAO;

import Spectra.DTO.MemoriaRam;
import org.springframework.dao.EmptyResultDataAccessException;

public class MemoriaRamDao extends Dao{
    MemoriaRam memoriaRam = new MemoriaRam();

    public MemoriaRamDao(){
        super();
    }

    @Override
    public void getFkMaquina(String hostName) {
        String sql = "SELECT idMaquina FROM Maquina WHERE hostName = ?";

        try {
            Integer idMaquina = conMySQl.queryForObject(sql, Integer.class, hostName);
            memoriaRam.setFkMaquina(idMaquina);
            getFkComponente();
        }

        catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado no fkMaquina Cpu");
        }
    }

    @Override
    public void getFkComponente() {
        String sql = "SELECT idComponente FROM Componente WHERE idComponente = 2";

        try {
            Integer idComponente = conMySQl.queryForObject(sql, Integer.class);
            memoriaRam.setFkComponente(idComponente);
            salvarDados();
        }

        catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado no fkComponente Cpu");
        }
    }

    @Override
    public void salvarDados() {
        String sql = "INSERT INTO RegistroComponente (idRegistroComponente, consumoAtual, armazenamentoTotal, armazenamentoDisponivel, fkComponente, fkMaquina) VALUES (?, ?, ?, ?, ?, ?)";
        Integer linhasAlteradas = conMySQl.update(sql, memoriaRam.getIdRegistro(), memoriaRam.getConsumoAtual(), memoriaRam.getArmazenamentoTotal(), memoriaRam.getArmazenamentoDisponivel(), memoriaRam.getFkComponente(), memoriaRam.getFkMaquina());

        if (linhasAlteradas > 0){
            System.out.println("Inserção no Mysql MemoriaRam realizada com sucesso!");
        }

        else {
            System.out.println("Erro ao inserir no MySQL Memoria ram!");
        }
    }
}
