package Spectra.DTO;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;

import java.util.List;

public class Rede extends Componente{
    private String latencia;
    private Double consumoUpload;
    private Double consumoDownload;

    public Rede(String latencia, Double consumoUpload, Double consumoDownload) {
        this.latencia = latencia;
        this.consumoUpload = consumoUpload;
        this.consumoDownload = consumoDownload;
    }

    public Rede() {}

    public String getLatencia() {
        return latencia;
    }

    public void setLatencia(String latencia) {
        this.latencia = latencia;
    }

    public Double getConsumoUpload() {
        return consumoUpload;
    }

    public void setConsumoUpload(Double consumoUpload) {
        this.consumoUpload = consumoUpload;
    }

    public Double getConsumoDownload() {
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
