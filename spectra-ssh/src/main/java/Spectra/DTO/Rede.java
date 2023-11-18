package Spectra.DTO;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;

import java.util.List;

public class Rede extends Componente{
    private String latencia;
    private Double consumoUpload;
    private Double consumoDownload;
    private Looca looca = new Looca();
    private List<RedeInterface> redeInterfaces = looca.getRede().getGrupoDeInterfaces().getInterfaces();

    public Rede(String latencia, Double consumoUpload, Double consumoDownload) {
        this.latencia = latencia;
        this.consumoUpload = consumoUpload;
        this.consumoDownload = consumoDownload;
    }

    public Rede() {}

    public String getLatencia() {
        long tempoEnvio = System.currentTimeMillis();

        long tempoRecepcao = System.currentTimeMillis();

        long latenciaLong = tempoRecepcao - tempoEnvio;

        latencia = String.format("%d ", latenciaLong);

        System.out.println(latencia);

        return latencia;
    }

    public void setLatencia(String latencia) {
        this.latencia = latencia;
    }

    public Double getConsumoUpload() {
        for (RedeInterface r: redeInterfaces){
            consumoUpload = ((double) r.getBytesEnviados()) / (1024 * 1024);
        }
        consumoUpload = Math.rint(consumoUpload * 100.) / 100.0;

        return consumoUpload;
    }

    public void setConsumoUpload(Double consumoUpload) {
        this.consumoUpload = consumoUpload;
    }

    public Double getConsumoDownload() {
        for (RedeInterface r: redeInterfaces){
            consumoDownload = ((double) r.getBytesRecebidos()) / (1024 * 1024);
        }
        consumoDownload = Math.rint(consumoDownload * 100.) / 100.;

        return consumoDownload;
    }

    public void setConsumoDownload(Double consumoDownload) {
        this.consumoDownload = consumoDownload;
    }

    @Override
    public String toString() {
        return "Rede{}";
    }
}
