package Spectra.DTO;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processador.Processador;

public class Cpu extends Componente{
    private String especificacao;
    private Double consumoAtual;
    private Looca looca = new Looca();
    private Processador processador = looca.getProcessador();

    public Cpu(String especificacao, Double consumoAtual) {
        this.especificacao = especificacao;
        this.consumoAtual = consumoAtual;
    }

    public Cpu() {
    }

    public String getEspecificacao() {
        especificacao = processador.getNome();
        return especificacao;
    }

    public void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }

    public Double getConsumoAtual() {
        consumoAtual = processador.getUso();
        consumoAtual = Math.rint(consumoAtual * 100.) / 100.;
        return consumoAtual;
    }

    @Override
    public String toString() {
        return "Cpu{}";
    }
}
