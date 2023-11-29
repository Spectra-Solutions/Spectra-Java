package Spectra.DAO;

import Spectra.Connection.ConexaoMysQl;
import Spectra.DTO.Maquina;
import Spectra.Log.Log;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

public class MaquinaDao{
    ConexaoMysQl conexaoMysQl = new ConexaoMysQl();
    protected JdbcTemplate conMySQl = conexaoMysQl.getConexaoMySQl();
    Maquina maquina = new Maquina();
    Log log = new Log();

    public void getFkEmpresa(String nome, String secao, String email, String senha) throws IOException {
        String sql = "SELECT fkEmpresa FROM Funcionario WHERE emailFunc = ? and senhaFunc = ?";

        try {
            Integer idEmpresa = conMySQl.queryForObject(sql, Integer.class, email, senha);
                maquina.setFkEmpresa(idEmpresa);
                salvarMaquina(nome, secao);
        }

        catch (EmptyResultDataAccessException e){
            log.setMensagem(String.format("Erro na busca da fkEmpresa", e));
            log.gerarLog("erro");
            System.err.println("Nenhum resultado encontrado na fkEmpresa maquina");
        }
    }

    public Boolean existHostName(String hostName) {
        String query = "SELECT EXISTS (SELECT 1 FROM Maquina WHERE hostName = ?) as existe";

        Integer resultado = conMySQl.queryForObject(
                query,
                new Object[]{hostName},
                (rs, rowNum) -> rs.getInt("existe")
        );

        return resultado != null && resultado == 1;
    }

    public void salvarMaquina(String nome, String secao) throws IOException {
        String sql1 = "INSERT INTO Maquina (idMaquina, hostName, nome, sistemaOperacional, secao, qtdDisco, tempoAtividade,fkEmpresaMaquina) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Integer linhasAlteradas = conMySQl.update(sql1, maquina.getIdMaquina(), maquina.getHostName(), nome, maquina.getSistemaOperacional(), secao, maquina.getQtdDisco(), maquina.getTempoAtividade(), maquina.getFkEmpresa());

        if (linhasAlteradas > 0){
            System.out.println("""
                    Maquina cadastrada!
                    
                    O monitoramento ja foi iniciado!
                    Acesse a dashboard para visualizar: http://44.216.221.58/home
                    """);
        }

        else {
            log.setMensagem("Erro no cadastro de uma máquina no MySQL!");
            log.gerarLog("erro");

            System.err.println("Erro no cadastro de uma máquina no MySQL!");
        }
    }

    public void atualizarTempoAtividade() throws IOException {

        String sql = "SELECT idMaquina FROM Maquina WHERE hostName = ?";

        try {
            Integer idMaquina1 = conMySQl.queryForObject(sql, Integer.class, maquina.getHostName());
            maquina.setIdMaquina(idMaquina1);
        }

        catch (EmptyResultDataAccessException e){
            log.setMensagem(String.format("Erro na busca do idMaquina baseado no hostName Maquina %s", e));
            log.gerarLog("erro");
            System.err.println("Nenhum resultado encontrado no fkMaquina Maquina");
        }

        conMySQl.update("UPDATE Maquina SET tempoAtividade = ? WHERE idMaquina = ?", maquina.getTempoAtividade(), maquina.getIdMaquina());

        try {
        } catch (EmptyResultDataAccessException e){
            log.setMensagem(String.format("Erro em atualizar o tempo da atividade %s", e));
            log.gerarLog("erro");

            System.err.println("Erro em atualizar o tempo da atividade %s");
        }
    }

}
