package Spectra.DTO;

public class ProcessoClass extends Componente{
    private Integer pidProcesso;
    private String nomeProcesso;
    private Double usoCpu;
    private Double usoMemoria;

    public ProcessoClass(Integer pidProcesso, String nomeProcesso, Double usoCpu, Double usoMemoria) {
        this.pidProcesso = pidProcesso;
        this.nomeProcesso = nomeProcesso;
        this.usoCpu = usoCpu;
        this.usoMemoria = usoMemoria;
    }

    public ProcessoClass() {
    }

    public Integer getPidProcesso() {
        return pidProcesso;
    }

    public void setPidProcesso(Integer pidProcesso) {
        this.pidProcesso = pidProcesso;
    }

    public String getNomeProcesso() {
        return nomeProcesso;
    }

    public void setNomeProcesso(String nomeProcesso) {
        this.nomeProcesso = nomeProcesso;
    }

    public Double getUsoCpu() {
        return usoCpu;
    }

    public void setUsoCpu(Double usoCpu) {
        this.usoCpu = usoCpu;
    }

    public Double getUsoMemoria() {
        return usoMemoria;
    }

    public void setUsoMemoria(Double usoMemoria) {
        this.usoMemoria = usoMemoria;
    }
}
