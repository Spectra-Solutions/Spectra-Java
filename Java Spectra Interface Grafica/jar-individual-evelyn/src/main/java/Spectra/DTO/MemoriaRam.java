package Spectra.DTO;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.memoria.Memoria;

public class MemoriaRam extends Componente{
    private Double consumoAtual;
    private Double armazenamentoTotal;
    private Double armazenamentoDisponivel;
    private Looca looca = new Looca();
    private Memoria memoriaRam = looca.getMemoria();

    public MemoriaRam(Double consumoAtual, Double armazenamentoTotal, Double armazenamentoDisponivel) {
        this.consumoAtual = consumoAtual;
        this.armazenamentoTotal = armazenamentoTotal;
        this.armazenamentoDisponivel = armazenamentoDisponivel;
    }

    public MemoriaRam() {
    }

    public Double getConsumoAtual() {
        String consumoAtualString = (ConversorSpectra.formatarBytes(memoriaRam.getEmUso()) );

        consumoAtualString = consumoAtualString.replaceAll("\\.","");

        consumoAtualString = consumoAtualString.replace("," , ".");

        consumoAtual = Double.parseDouble(consumoAtualString);

        return consumoAtual;
    }

    public void setConsumoAtual(Double consumoAtual) {
        this.consumoAtual = consumoAtual;
    }

    public Double getArmazenamentoTotal() {
        String armazenamentoTotalString = (ConversorSpectra.formatarBytes(memoriaRam.getTotal()) );

        armazenamentoTotalString = armazenamentoTotalString.replaceAll("\\.","");

        armazenamentoTotalString = armazenamentoTotalString.replace("," , ".");

        armazenamentoTotal = Double.parseDouble(armazenamentoTotalString);

        return armazenamentoTotal;
    }

    public void setArmazenamentoTotal(Double armazenamentoTotal) {
        this.armazenamentoTotal = armazenamentoTotal;
    }

    public Double getArmazenamentoDisponivel() {
        String armazenamentoDisponivelString = (ConversorSpectra.formatarBytes(memoriaRam.getDisponivel()) );

        armazenamentoDisponivelString = armazenamentoDisponivelString.replaceAll("\\.","");

        armazenamentoDisponivelString = armazenamentoDisponivelString.replace("," , ".");

        armazenamentoDisponivel = Double.parseDouble(armazenamentoDisponivelString);

        return armazenamentoDisponivel;
    }

    public void setArmazenamentoDisponivel(Double armazenamentoDisponivel) {
        this.armazenamentoDisponivel = armazenamentoDisponivel;
    }

    @Override
    public String toString() {
        return "MemoriaRam{}";
    }
}
