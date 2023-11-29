package Spectra.DAO;

import Spectra.DTO.ConversorSpectra;
import Spectra.DTO.DiscoClass;
import Spectra.Log.Log;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import org.springframework.dao.EmptyResultDataAccessException;

import java.io.IOException;
import java.util.List;

public class DiscoDao extends Dao{
    DiscoClass disco = new DiscoClass();
    Log log = new Log();
    Looca looca = new Looca();
    List<Volume> volumes = looca.getGrupoDeDiscos().getVolumes();
    public DiscoDao(){
        super();
    }

    @Override
    public void getFkMaquina(String hostName) throws IOException {
        String sql = "SELECT idMaquina FROM Maquina WHERE hostName = ?";

        try {
            Integer idMaquina = conMySQl.queryForObject(sql, Integer.class, hostName);
            disco.setFkMaquina(idMaquina);
            getFkComponente();
        }

        catch (EmptyResultDataAccessException e){
            log.setMensagem(String.format("Erro na busca do idMaquina baseado no hostName Disco %s", e));
            log.gerarLog("erro");
            System.err.println("Nenhum resultado encontrado no fkMaquina Disco");
        }
    }

    @Override
    public void getFkComponente() throws IOException {
        String sql = "SELECT idComponente FROM Componente WHERE idComponente = 3";

        try {
            Integer idComponente = conMySQl.queryForObject(sql, Integer.class);
            disco.setFkComponente(idComponente);
            salvarDados();
        }

        catch (EmptyResultDataAccessException e){
            log.setMensagem(String.valueOf(e));
            log.gerarLog("erro");
            System.err.println("Nenhum resultado encontrado no fkComponente Disco");
        }
    }

    @Override
    public void salvarDados() throws IOException {
        Integer linhasAlteradas = 0;

        for (Volume v: volumes){

            String armazenamentoTotalString = (ConversorSpectra.formatarBytes(v.getTotal()));

            armazenamentoTotalString = armazenamentoTotalString.replaceAll("\\.","");

            armazenamentoTotalString = armazenamentoTotalString.replace("," , ".");

            disco.setArmazenamentoTotal(Double.parseDouble(armazenamentoTotalString));

            /*==================================================================================================================================================================================================================================================================================================*/

            String armazentamentoDisponivelString = (ConversorSpectra.formatarBytes(v.getDisponivel()));

            armazentamentoDisponivelString = armazentamentoDisponivelString.replaceAll("\\.","");

            armazentamentoDisponivelString = armazentamentoDisponivelString.replace("," , ".");

            disco.setArmazenamentoDisponivel(Double.parseDouble(armazentamentoDisponivelString));

            /*==================================================================================================================================================================================================================================================================================================*/

            String consumoAtualString = (ConversorSpectra.formatarBytes(v.getTotal() - v.getDisponivel()) );

            consumoAtualString = consumoAtualString.replaceAll("\\.","");

            consumoAtualString = consumoAtualString.replace("," , ".");

            disco.setConsumoAtual(Double.parseDouble(consumoAtualString) / disco.getArmazenamentoTotal()  * 100);

            disco.setConsumoAtual(Math.rint(disco.getConsumoAtual() * 100.) / 100.);

            String sql = "INSERT INTO RegistroComponente (idRegistroComponente, consumoAtual, armazenamentoTotal, armazenamentoDisponivel, fkComponente, fkMaquina) VALUES (?, ?, ?, ?, ?, ?)";
            linhasAlteradas = conMySQl.update(sql , disco.getIdRegistro(), disco.getConsumoAtual(), disco.getArmazenamentoTotal(), disco.getArmazenamentoDisponivel(), disco.getFkComponente(), disco.getFkMaquina());
        }

        if(linhasAlteradas > 0){
            System.out.println("Inserção no Mysql Disco realizada com sucesso!");
        }

        else {
            log.setMensagem("Erro no cadastro dos dados do disco no MySQL!");
            log.gerarLog("erro");
            System.err.println("Erro no cadastro dos dados do disco no MySQL!");
        }
    }
}
