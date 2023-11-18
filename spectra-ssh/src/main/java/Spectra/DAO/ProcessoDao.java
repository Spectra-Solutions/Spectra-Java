package Spectra.DAO;

import Spectra.DTO.ProcessoClass;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processos.Processo;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public class ProcessoDao extends Dao{
    ProcessoClass processoClass = new ProcessoClass();
    Looca looca = new Looca();
    List<Processo> processos = looca.getGrupoDeProcessos().getProcessos();

    public ProcessoDao(){
        super();
    }

    @Override
    public void getFkMaquina(String hostName) {
        String sql = "SELECT idMaquina FROM Maquina WHERE hostName = ?";

        try {
            Integer idMaquina = conMySQl.queryForObject(sql, Integer.class, hostName);
            processoClass.setFkMaquina(idMaquina);
            salvarDados();
        }

        catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado no fkMaquina Rede");
        }
    }
    @Override
    public void salvarDados() {
        Integer linhasAlteradas = 0;

        for (Processo p: processos){
            processoClass.setPidProcesso(p.getPid());
            processoClass.setNomeProcesso(p.getNome());
            processoClass.setUsoCpu(p.getUsoCpu());
            processoClass.setUsoMemoria(p.getUsoMemoria());

            String sql = "INSERT INTO Processo (idProcesso, PidProcesso, nomeProcesso, usoCpu, usoMemoria, fkMaquinaProcesso) VALUES (?, ?, ?, ?, ?, ?)";
            linhasAlteradas = conMySQl.update(sql, processoClass.getIdRegistro(), processoClass.getPidProcesso(), processoClass.getNomeProcesso(), processoClass.getUsoCpu(), processoClass.getUsoMemoria(), processoClass.getFkMaquina());
        }

        if (linhasAlteradas > 0){
            System.out.println("Inserção no Mysql Processo realizada com sucesso!");
        }

        else {
            System.out.println("Erro ao inserir no MySQL Processo!");
        }
    }

    public void getFkComponente() {}
}
