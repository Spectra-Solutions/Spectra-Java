package Spectra.DTO;

public class DiscoClass extends Componente{
    private Double consumoAtual;
    private Double armazenamentoTotal;
    private Double armazenamentoDisponivel;

    public DiscoClass(Double consumoAtual, Double armazenamentoTotal, Double armazenamentoDisponivel) {
        this.consumoAtual = consumoAtual;
        this.armazenamentoTotal = armazenamentoTotal;
        this.armazenamentoDisponivel = armazenamentoDisponivel;
    }

    public DiscoClass() {}

    public Double getConsumoAtual() {
        return consumoAtual;
    }

    public void setConsumoAtual(Double consumoAtual) {
        this.consumoAtual = consumoAtual;
    }

    public Double getArmazenamentoTotal() {
        return armazenamentoTotal;
    }

    public void setArmazenamentoTotal(Double armazenamentoTotal) {
        this.armazenamentoTotal = armazenamentoTotal;
    }

    public Double getArmazenamentoDisponivel() {
        return armazenamentoDisponivel;
    }

    public void setArmazenamentoDisponivel(Double armazenamentoDisponivel) {
        this.armazenamentoDisponivel = armazenamentoDisponivel;
    }

    @Override
    public String toString() {
        return "DiscoClass{}";
    }
}
