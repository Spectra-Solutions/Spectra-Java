package SpectraSprint02.DTO;

public class ServicoClass {
    private Integer idServico;
    private Long pid;
    private String nomeServico;
    private String estado;
    private Integer fkMaquina;

    public ServicoClass(Long pid, String nomeServico, String estado) {
        this.idServico = null;
        this.pid = pid;
        this.nomeServico = nomeServico;
        this.estado = estado;
        this.fkMaquina = null;
    }

    public ServicoClass() {
    }

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getFkMaquina() {
        return fkMaquina;
    }

    public void setFkMaquina(Integer fkMaquina) {
        this.fkMaquina = fkMaquina;
    }

    @Override
    public String toString() {
        return """
                Pid: %d
                Nome: %s
                Estado: %s  
                """.formatted(pid, nomeServico, estado);
    }
}
