package Spectra.DTO;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.rede.RedeParametros;
import com.github.britooo.looca.api.group.sistema.Sistema;

public class Maquina {
    private Integer idMaquina;
    private String hostName;
    private String nome;
    private String sistemaOperacional;
    private String secao;
    private String tempoAtividade;
    private Integer qtdDisco;
    private Integer fkEmpresa;
    private Integer fkEmpresaSqlServer;
    private Looca looca = new Looca();
    ConversorSpectra conversorSpectra = new ConversorSpectra();

    public Maquina(String hostName, String nome, String sistemaOperacional, String secao, String tempoAtividade,Integer qtdDisco) {
        this.idMaquina = null;
        this.hostName = hostName;
        this.nome = nome;
        this.sistemaOperacional = sistemaOperacional;
        this.secao = secao;
        this.tempoAtividade = tempoAtividade;
        this.qtdDisco = qtdDisco;
        this.fkEmpresa = null;
        this.fkEmpresaSqlServer = null;
    }

    public Maquina() {
    }

    public Integer getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Integer idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getHostName() {
        RedeParametros redeParametros = looca.getRede().getParametros();
        hostName = redeParametros.getHostName();
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSistemaOperacional() {
        Sistema sistema = looca.getSistema();
        sistemaOperacional = sistema.getSistemaOperacional();
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public String getSecao() {
        return secao;
    }

    public void setSecao(String secao) {
        this.secao = secao;
    }

    public String getTempoAtividade() {
        Sistema sistema = looca.getSistema();
        tempoAtividade = ConversorSpectra.formatarSegundosDecorridos(sistema.getTempoDeAtividade());
        return tempoAtividade;
    }

    public void setTempoAtividade(String tempoAtividade) {
        this.tempoAtividade = tempoAtividade;
    }

    public Integer getQtdDisco() {
        DiscoGrupo discoGrupo = looca.getGrupoDeDiscos();
        qtdDisco = discoGrupo.getQuantidadeDeDiscos();
        return qtdDisco;
    }

    public void setQtdDisco(Integer qtdDisco) {
        this.qtdDisco = qtdDisco;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public Integer getFkEmpresaSqlServer() {
        return fkEmpresaSqlServer;
    }

    public void setFkEmpresaSqlServer(Integer fkEmpresaSqlServer) {
        this.fkEmpresaSqlServer = fkEmpresaSqlServer;
    }
}
