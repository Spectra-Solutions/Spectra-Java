package Spectra.DAO;

import Spectra.Connection.ConexaoMysQl;
import Spectra.DTO.Maquina;
import Spectra.Log.Log;
import Spectra.Slack.Slack;
import org.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class SlackDao {
    ConexaoMysQl conexaoMysQl = new ConexaoMysQl();
    protected JdbcTemplate conMySQl = conexaoMysQl.getConexaoMySQl();
    Slack slack = new Slack();
    Maquina maquina = new Maquina();
    Log log = new Log();

    public void getUrlEmpresa(String email, String senha) throws IOException {
        String sql = """
                SELECT url FROM Empresa JOIN Funcionario
                        	ON Empresa.idEmpresa = Funcionario.fkEmpresa
                        		WHERE Funcionario.EmailFunc = ?
                        			AND Funcionario.SenhaFunc = ?""";
        try{
            String urlEmpresa = conMySQl.queryForObject(sql, String.class, email, senha);
            slack.setUrl(urlEmpresa);
        }catch (EmptyResultDataAccessException e){
            log.setMensagem(String.format("Erro na busca da url Slack %s", e));
            log.gerarLog("error");

            System.err.println("Erro na busca da url Slack");
        }
    }


    public void getSelectCpu() throws IOException {

        String registroAvisos ="";

        String sql = """
                SELECT RegistroAvisos.registroAviso, RegistroAvisos.fkComponente, RegistroAvisos.fkTaxaAviso, RegistroAvisos.fkTipoAviso FROM Componente
                	JOIN RegistroAvisos ON RegistroAvisos.fkComponente = Componente.idComponente
                		JOIN RegistroComponente ON RegistroComponente.fkComponente = Componente.idComponente
                			JOIN Maquina ON RegistroComponente.fkMaquina = Maquina.idMaquina
                				WHERE Maquina.hostName = ?
                					AND RegistroAvisos.fkComponente = 1
                						AND RegistroAvisos.fkTipoAviso = 1
                							LIMIT 1""";
        try{
            Map<String, Object> resultadoMap = conMySQl.queryForMap(sql, maquina.getHostName());

            Integer fkTipoAviso = (Integer) resultadoMap.get("fkTipoAviso");

            if (fkTipoAviso == 1) {
                slack.setFkComponente((Integer) resultadoMap.get("fkComponente"));
                slack.setFkTaxaAviso((Integer) resultadoMap.get("fkTaxaAviso"));
                slack.setFkTipoAviso(fkTipoAviso);
                slack.setNotificaçãoSlack((String) resultadoMap.get("registroAviso"));

                JSONObject json = new JSONObject();
                json.put("text", slack.getNotificaçãoSlack());
                slack.sendMenssage(json);
            } else {
                String sql1 = """
                SELECT RegistroAvisos.registroAviso, RegistroAvisos.fkComponente, RegistroAvisos.fkTaxaAviso, RegistroAvisos.fkTipoAviso FROM Componente
                	JOIN RegistroAvisos ON RegistroAvisos.fkComponente = Componente.idComponente
                		JOIN RegistroComponente ON RegistroComponente.fkComponente = Componente.idComponente
                			JOIN Maquina ON RegistroComponente.fkMaquina = Maquina.idMaquina
                				WHERE Maquina.hostName = ?
                					AND RegistroAvisos.fkComponente = 1
                						AND RegistroAvisos.fkTipoAviso = 2
                							LIMIT 1""";

                try {
                    Map<String, Object> resultadoMap1 = conMySQl.queryForMap(sql1, maquina.getHostName());
                    Integer fkTipoAviso1 = (Integer) resultadoMap1.get("fkTipoAviso");

                    if (fkTipoAviso == 2) {
                        slack.setFkComponente((Integer) resultadoMap1.get("fkComponente"));
                        slack.setFkTaxaAviso((Integer) resultadoMap1.get("fkTaxaAviso"));
                        slack.setFkTipoAviso(fkTipoAviso1);
                        slack.setNotificaçãoSlack((String) resultadoMap1.get("registroAviso"));

                        JSONObject json = new JSONObject();
                        json.put("text", slack.getNotificaçãoSlack());
                        slack.sendMenssage(json);
                    }

                } catch (EmptyResultDataAccessException e){
                    log.setMensagem(String.format("Não existe select na tabela registro avisos, cpu %s", e));
                    log.gerarLog("erro");

                    System.err.println("Não existe select na tabela registro avisos, cpu");
                }
            }

        } catch (EmptyResultDataAccessException e){
            log.setMensagem(String.format("Não existe select na tabela registro avisos, cpu %s", e));
            log.gerarLog("erro");

            System.err.println("Não existe select na tabela registro avisos, cpu");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void getSelectRam() throws IOException {
        String sql = """
                SELECT RegistroAvisos.registroAviso, RegistroAvisos.fkComponente, RegistroAvisos.fkTaxaAviso, RegistroAvisos.fkTipoAviso FROM Componente
                	JOIN RegistroAvisos ON RegistroAvisos.fkComponente = Componente.idComponente
                		JOIN RegistroComponente ON RegistroComponente.fkComponente = Componente.idComponente
                			JOIN Maquina ON RegistroComponente.fkMaquina = Maquina.idMaquina
                				WHERE Maquina.hostName = ?
                					AND RegistroAvisos.fkComponente = 2
                						AND RegistroAvisos.fkTipoAviso = 1
                							LIMIT 1""";
        try{
            Map<String, Object> resultadoMap = conMySQl.queryForMap(sql, maquina.getHostName());

            Integer fkTipoAviso = (Integer) resultadoMap.get("fkTipoAviso");

            if (fkTipoAviso == 1) {
                slack.setFkComponente((Integer) resultadoMap.get("fkComponente"));
                slack.setFkTaxaAviso((Integer) resultadoMap.get("fkTaxaAviso"));
                slack.setFkTipoAviso(fkTipoAviso);
                slack.setNotificaçãoSlack((String) resultadoMap.get("registroAviso"));

                JSONObject json = new JSONObject();
                json.put("text", slack.getNotificaçãoSlack());
                slack.sendMenssage(json);
            } else {
                String sql1 = """
                SELECT RegistroAvisos.registroAviso, RegistroAvisos.fkComponente, RegistroAvisos.fkTaxaAviso, RegistroAvisos.fkTipoAviso FROM Componente
                	JOIN RegistroAvisos ON RegistroAvisos.fkComponente = Componente.idComponente
                		JOIN RegistroComponente ON RegistroComponente.fkComponente = Componente.idComponente
                			JOIN Maquina ON RegistroComponente.fkMaquina = Maquina.idMaquina
                				WHERE Maquina.hostName = ?
                					AND RegistroAvisos.fkComponente = 2
                						AND RegistroAvisos.fkTipoAviso = 2
                							LIMIT 1""";

                try {
                    Map<String, Object> resultadoMap1 = conMySQl.queryForMap(sql1, maquina.getHostName());
                    Integer fkTipoAviso1 = (Integer) resultadoMap1.get("fkTipoAviso");

                    if (fkTipoAviso == 2) {
                        slack.setFkComponente((Integer) resultadoMap1.get("fkComponente"));
                        slack.setFkTaxaAviso((Integer) resultadoMap1.get("fkTaxaAviso"));
                        slack.setFkTipoAviso(fkTipoAviso1);
                        slack.setNotificaçãoSlack((String) resultadoMap1.get("registroAviso"));

                        JSONObject json = new JSONObject();
                        json.put("text", slack.getNotificaçãoSlack());
                        slack.sendMenssage(json);
                    }

                } catch (EmptyResultDataAccessException e){
                    log.setMensagem(String.format("Não existe select na tabela registro avisos, ram %s", e));
                    log.gerarLog("erro");

                    System.err.println("Não existe select na tabela registro avisos, ram ");
                }
            }

        } catch (EmptyResultDataAccessException e){
            log.setMensagem(String.format("Não existe select na tabela registro avisos, ram %s", e));
            log.gerarLog("erro");

            System.err.println("Não existe select na tabela registro avisos, ram");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void getSelectDisco() throws IOException {
        String sql = """
                SELECT RegistroAvisos.registroAviso, RegistroAvisos.fkComponente, RegistroAvisos.fkTaxaAviso, RegistroAvisos.fkTipoAviso FROM Componente
                	JOIN RegistroAvisos ON RegistroAvisos.fkComponente = Componente.idComponente
                		JOIN RegistroComponente ON RegistroComponente.fkComponente = Componente.idComponente
                			JOIN Maquina ON RegistroComponente.fkMaquina = Maquina.idMaquina
                				WHERE Maquina.hostName = ?
                					AND RegistroAvisos.fkComponente = 3
                						AND RegistroAvisos.fkTipoAviso = 1
                							LIMIT 1""";
        try{
            Map<String, Object> resultadoMap = conMySQl.queryForMap(sql, maquina.getHostName());

            Integer fkTipoAviso = (Integer) resultadoMap.get("fkTipoAviso");

            if (fkTipoAviso == 1) {
                slack.setFkComponente((Integer) resultadoMap.get("fkComponente"));
                slack.setFkTaxaAviso((Integer) resultadoMap.get("fkTaxaAviso"));
                slack.setFkTipoAviso(fkTipoAviso);
                slack.setNotificaçãoSlack((String) resultadoMap.get("registroAviso"));

                JSONObject json = new JSONObject();
                json.put("text", slack.getNotificaçãoSlack());
                slack.sendMenssage(json);

            } else {
                String sql1 = """
                SELECT RegistroAvisos.registroAviso, RegistroAvisos.fkComponente, RegistroAvisos.fkTaxaAviso, RegistroAvisos.fkTipoAviso FROM Componente
                	JOIN RegistroAvisos ON RegistroAvisos.fkComponente = Componente.idComponente
                		JOIN RegistroComponente ON RegistroComponente.fkComponente = Componente.idComponente
                			JOIN Maquina ON RegistroComponente.fkMaquina = Maquina.idMaquina
                				WHERE Maquina.hostName = ?
                					AND RegistroAvisos.fkComponente = 3
                						AND RegistroAvisos.fkTipoAviso = 2
                							LIMIT 1""";

                try {
                    Map<String, Object> resultadoMap1 = conMySQl.queryForMap(sql1, maquina.getHostName());
                    Integer fkTipoAviso1 = (Integer) resultadoMap1.get("fkTipoAviso");

                    if (fkTipoAviso == 2) {
                        slack.setFkComponente((Integer) resultadoMap1.get("fkComponente"));
                        slack.setFkTaxaAviso((Integer) resultadoMap1.get("fkTaxaAviso"));
                        slack.setFkTipoAviso(fkTipoAviso1);
                        slack.setNotificaçãoSlack((String) resultadoMap1.get("registroAviso"));

                        JSONObject json = new JSONObject();
                        json.put("text", slack.getNotificaçãoSlack());
                        slack.sendMenssage(json);
                    }

                } catch (EmptyResultDataAccessException e){
                    log.setMensagem(String.format("Não existe select na tabela registro avisos, disco %s", e));
                    log.gerarLog("erro");

                    System.err.println("Não existe select na tabela registro avisos, disco");
                }
            }

        } catch (EmptyResultDataAccessException e){
            log.setMensagem(String.format("Não existe select na tabela registro avisos, disco %s", e));
            log.gerarLog("erro");

            System.err.println("Não existe select na tabela registro avisos, disco ");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
