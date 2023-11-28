package Spectra.DTO;

public abstract class Componente {
    protected Integer idRegistro;
    protected Integer fkComponente;
    protected Integer fkMaquina;
    protected Integer fkMaquinaSQlServer;

    public Componente() {
        this.idRegistro = null;
        this.fkComponente = null;
        this.fkMaquina = null;
        this.fkMaquinaSQlServer = null;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getFkComponente() {
        return fkComponente;
    }

    public void setFkComponente(Integer fkComponente) {
        this.fkComponente = fkComponente;
    }

    public Integer getFkMaquina() {
        return fkMaquina;
    }

    public void setFkMaquina(Integer fkMaquina) {
        this.fkMaquina = fkMaquina;
    }

    public Integer getFkMaquinaSQlServer() {
        return fkMaquinaSQlServer;
    }

    public void setFkMaquinaSQlServer(Integer fkMaquinaSQlServer) {
        this.fkMaquinaSQlServer = fkMaquinaSQlServer;
    }

    @Override
    public String toString() {
        return "Componente{}";
    }
}
