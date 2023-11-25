package Spectra.DAO;

import Spectra.DTO.MemoriaRam;
import Spectra.Log.Log;
import org.springframework.dao.EmptyResultDataAccessException;

import java.io.IOException;

public class MemoriaRamDao extends Dao{
    MemoriaRam memoriaRam = new MemoriaRam();
    Log log = new Log();

    public MemoriaRamDao(){
        super();
    }

    @Override
    public void getFkMaquina(String hostName) throws IOException {
        String sql = "SELECT idMaquina FROM Maquina WHERE hostName = ?";

        try {
            Integer idMaquina = conMySQl.queryForObject(sql, Integer.class, hostName);
            memoriaRam.setFkMaquina(idMaquina);
            getFkComponente();
        }

        catch (EmptyResultDataAccessException e){
            log.setMensagem(String.format("Erro na busca do idMaquina baseado no hostName Memoria Ram %s", e));
            log.gerarLog("erro");
            System.err.println("Nenhum resultado encontrado no fkMaquina Cpu");
        }
    }

    @Override
    public void getFkComponente() throws IOException {
        String sql = "SELECT idComponente FROM Componente WHERE idComponente = 2";

        try {
            Integer idComponente = conMySQl.queryForObject(sql, Integer.class);
            memoriaRam.setFkComponente(idComponente);
            salvarDados();
        }

        catch (EmptyResultDataAccessException e){
            log.setMensagem(String.valueOf(e));
            log.gerarLog("erro");
            System.err.println("Nenhum resultado encontrado no fkComponente Cpu");
        }
    }

    @Override
    public void salvarDados() throws IOException {
        String sql = "INSERT INTO RegistroComponente (idRegistroComponente, consumoAtual, armazenamentoTotal, armazenamentoDisponivel, fkComponente, fkMaquina) VALUES (?, ?, ?, ?, ?, ?)";
        Integer linhasAlteradas = conMySQl.update(sql, memoriaRam.getIdRegistro(), memoriaRam.getConsumoAtual(), memoriaRam.getArmazenamentoTotal(), memoriaRam.getArmazenamentoDisponivel(), memoriaRam.getFkComponente(), memoriaRam.getFkMaquina());

        if (linhasAlteradas > 0){
            System.out.println("Inserção no Mysql MemoriaRam realizada com sucesso!");
        }

        else {
            log.setMensagem("Erro no cadastro dos dados da Memoria ram no MySQL!");
            log.gerarLog("erro");
            System.err.println("Erro no cadastro dos dados da Memoria ram no MySQL!");
        }
    }
}
