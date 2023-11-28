package Spectra.DAO;

import Spectra.DTO.ConversorSpectra;
import Spectra.DTO.Rede;
import Spectra.Log.Log;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import org.springframework.dao.EmptyResultDataAccessException;

import java.io.IOException;
import java.util.List;

public class RedeDao extends Dao{
    Rede rede = new Rede();
    Log log = new Log();
    Looca looca = new Looca();
    List<RedeInterface> redeInterfaces = looca.getRede().getGrupoDeInterfaces().getInterfaces();

    public RedeDao(){
        super();
    }
    @Override
    public void getFkMaquina(String hostName) throws IOException {
        String sql = "SELECT idMaquina FROM Maquina WHERE hostName = ?";

        try {
            Integer idMaquina = conSqlServer.queryForObject(sql, Integer.class, hostName);
            rede.setFkMaquina(idMaquina);
            getFkComponente();
        }

        catch (EmptyResultDataAccessException e){
            log.setMensagem(String.format("Erro na busca do idMaquina baseado no hostName rede %s", e));
            log.gerarLog("erro");
            System.err.println("Nenhum resultado encontrado no fkMaquina Rede");
        }
    }

    @Override
    public void getFkComponente() throws IOException {
        String sql = "SELECT idComponente FROM Componente WHERE idComponente = 4";

        try {
            Integer idComponente = conSqlServer.queryForObject(sql, Integer.class);
            rede.setFkComponente(idComponente);
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
        Long variavelAuxiliarConsumoUpload = 0L;
        Long variavelAuxiliarConsumoDownload = 0L;

        for (RedeInterface r: redeInterfaces) {
            variavelAuxiliarConsumoUpload += r.getBytesEnviados();

            variavelAuxiliarConsumoDownload += r.getBytesRecebidos();
        }

        String consumoUploadString = (ConversorSpectra.formatarBytes(variavelAuxiliarConsumoUpload));

        consumoUploadString = consumoUploadString.replaceAll("\\.","");

        consumoUploadString = consumoUploadString.replace("," , ".");

        rede.setConsumoUpload(Double.parseDouble(consumoUploadString));

        System.out.println(rede.getConsumoUpload());

        String consumoDownloadString = (ConversorSpectra.formatarBytes(variavelAuxiliarConsumoDownload));

        consumoDownloadString = consumoDownloadString.replaceAll("\\.","");

        consumoDownloadString = consumoDownloadString.replace("," , ".");

        rede.setConsumoDownload(Double.parseDouble(consumoDownloadString));

        System.out.println(rede.getConsumoDownload());

        String sql = "INSERT INTO RegistroComponente (consumoUpload, consumoDownload, fkComponente, fkMaquina) VALUES (?, ?, ?, ?)";
        Integer linhasAlteradas = conSqlServer.update(sql, rede.getConsumoUpload(), rede.getConsumoDownload(), rede.getFkComponente(), rede.getFkMaquina());

        if (linhasAlteradas > 0){
            System.out.println("Inserção no Mysql Rede realizada com sucesso!");
        }

        else {
            log.setMensagem("Erro no cadastro dos dados da Rede no MySQL!");
            log.gerarLog("erro");
            System.err.println("Erro no cadastro dos dados da Rede no MySQL!");
        }
    }
}