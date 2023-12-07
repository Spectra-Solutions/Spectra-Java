package Spectra.DAO;

import Spectra.Connection.ConexaoSQLServer;
import Spectra.DTO.Maquina;
import Spectra.Log.Log;
import Spectra.Slack.Slack;
import org.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.Map;

public class SlackDao {
    ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
    protected JdbcTemplate conSqlServer = conexaoSQLServer.getConexaoSqlServer();
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
            String urlEmpresa = conSqlServer.queryForObject(sql, String.class, email, senha);
            slack.setUrl(urlEmpresa);
        }catch (EmptyResultDataAccessException e){
            log.setMensagem(String.format("Erro na busca da url Slack %s", e));
            log.gerarLog("slack");

            System.err.println("Erro na busca da url Slack");
        }
    }


    public void getSelectCpu(String nome) throws IOException {

        System.out.println(nome);

        String sql = """
                SELECT TOP 1 RegistroAvisos.registroAviso, RegistroAvisos.fkComponente, RegistroAvisos.fkTaxaAviso, RegistroAvisos.fkTipoAviso FROM RegistroAvisos
                                	JOIN Componente ON RegistroAvisos.fkComponente = Componente.idComponente
                                		 JOIN RegistroComponente ON Componente.idComponente = RegistroComponente.fkComponente
                                			JOIN Maquina ON RegistroComponente.fkMaquina = Maquina.idMaquina
                                				WHERE registroAvisos.fkComponente = 1
                                                    AND registroAvisos.fkTipoAviso = 1
                                                        AND Maquina.hostName = ?
                                                            ORDER BY idRegistroAviso
                                                                DESC""";
        try{
            Map<String, Object> resultadoMap = conSqlServer.queryForMap(sql, nome);

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
                        SELECT TOP 1 RegistroAvisos.registroAviso, RegistroAvisos.fkComponente, RegistroAvisos.fkTaxaAviso, RegistroAvisos.fkTipoAviso FROM RegistroAvisos
                                	JOIN Componente ON RegistroAvisos.fkComponente = Componente.idComponente
                                		 JOIN RegistroComponente ON Componente.idComponente = RegistroComponente.fkComponente
                                			JOIN Maquina ON RegistroComponente.fkMaquina = Maquina.idMaquina
                                				WHERE registroAvisos.fkComponente = 1
                                                    AND registroAvisos.fkTipoAviso = 2
                                                        AND Maquina.hostName = ?
                                                            ORDER BY idRegistroAviso
                                                                DESC""";

                try {
                    Map<String, Object> resultadoMap1 = conSqlServer.queryForMap(sql1, nome);
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
                    log.gerarLog("slack");

                    System.err.println("Não existe select na tabela registro avisos, cpu");
                }
            }

        } catch (EmptyResultDataAccessException e){
            log.setMensagem(String.format("Não existe select na tabela registro avisos, cpu %s", e));
            log.gerarLog("slack");

            System.err.println("Não existe select na tabela registro avisos, cpu");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void getSelectRam(String nome) throws IOException {
        String sql = """
                SELECT TOP 1 RegistroAvisos.registroAviso, RegistroAvisos.fkComponente, RegistroAvisos.fkTaxaAviso, RegistroAvisos.fkTipoAviso FROM RegistroAvisos
                                	JOIN Componente ON RegistroAvisos.fkComponente = Componente.idComponente
                                		 JOIN RegistroComponente ON Componente.idComponente = RegistroComponente.fkComponente
                                			JOIN Maquina ON RegistroComponente.fkMaquina = Maquina.idMaquina
                                				WHERE registroAvisos.fkComponente = 2
                                                    AND registroAvisos.fkTipoAviso = 1
                                                        AND Maquina.hostName = ?
                                                            ORDER BY idRegistroAviso
                                                                DESC""";
        try{
            Map<String, Object> resultadoMap = conSqlServer.queryForMap(sql, nome);

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
                SELECT TOP 1 RegistroAvisos.registroAviso, RegistroAvisos.fkComponente, RegistroAvisos.fkTaxaAviso, RegistroAvisos.fkTipoAviso FROM RegistroAvisos
                                	JOIN Componente ON RegistroAvisos.fkComponente = Componente.idComponente
                                		 JOIN RegistroComponente ON Componente.idComponente = RegistroComponente.fkComponente
                                			JOIN Maquina ON RegistroComponente.fkMaquina = Maquina.idMaquina
                                				WHERE registroAvisos.fkComponente = 2
                                                    AND registroAvisos.fkTipoAviso = 2
                                                        AND Maquina.hostName = ?
                                                            ORDER BY idRegistroAviso
                                                                DESC""";

                try {
                    Map<String, Object> resultadoMap1 = conSqlServer.queryForMap(sql1, nome);
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
                    log.gerarLog("slack");

                    System.err.println("Não existe select na tabela registro avisos, ram ");
                }
            }

        } catch (EmptyResultDataAccessException e){
            log.setMensagem(String.format("Não existe select na tabela registro avisos, ram %s", e));
            log.gerarLog("slack");

            System.err.println("Não existe select na tabela registro avisos, ram");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void getSelectDisco(String nome) throws IOException {
        String sql = """
                SELECT TOP 1 RegistroAvisos.registroAviso, RegistroAvisos.fkComponente, RegistroAvisos.fkTaxaAviso, RegistroAvisos.fkTipoAviso FROM RegistroAvisos
                                	JOIN Componente ON RegistroAvisos.fkComponente = Componente.idComponente
                                		 JOIN RegistroComponente ON Componente.idComponente = RegistroComponente.fkComponente
                                			JOIN Maquina ON RegistroComponente.fkMaquina = Maquina.idMaquina
                                				WHERE registroAvisos.fkComponente = 3
                                                    AND registroAvisos.fkTipoAviso = 1
                                                        AND Maquina.hostName = ?
                                                            ORDER BY idRegistroAviso
                                                                DESC""";
        try{
            Map<String, Object> resultadoMap = conSqlServer.queryForMap(sql, nome);

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
                SELECT TOP 1 RegistroAvisos.registroAviso, RegistroAvisos.fkComponente, RegistroAvisos.fkTaxaAviso, RegistroAvisos.fkTipoAviso FROM RegistroAvisos
                                	JOIN Componente ON RegistroAvisos.fkComponente = Componente.idComponente
                                		 JOIN RegistroComponente ON Componente.idComponente = RegistroComponente.fkComponente
                                			JOIN Maquina ON RegistroComponente.fkMaquina = Maquina.idMaquina
                                				WHERE registroAvisos.fkComponente = 3
                                                    AND registroAvisos.fkTipoAviso = 2
                                                        AND Maquina.hostName = ?
                                                            ORDER BY idRegistroAviso
                                                                DESC""";

                try {
                    Map<String, Object> resultadoMap1 = conSqlServer.queryForMap(sql1, nome);
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
                    log.gerarLog("slack");

                    System.err.println("Não existe select na tabela registro avisos, disco");
                }
            }

        } catch (EmptyResultDataAccessException e){
            log.setMensagem(String.format("Não existe select na tabela registro avisos, disco %s", e));
            log.gerarLog("slack");

            System.err.println("Não existe select na tabela registro avisos, disco ");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void desligarMaquina(String nome) throws IOException, InterruptedException {
        slack.setNotificaçãoSlack(String.format("A maquina %s, foi desligada", nome));
        JSONObject json = new JSONObject();
        json.put("text", slack.getNotificaçãoSlack());
        slack.sendMenssage(json);
    }

    public void reiniciarMaquina(String nome) throws IOException, InterruptedException {
        slack.setNotificaçãoSlack(String.format("A maquina %s, foi reiniciada", nome));
        JSONObject json = new JSONObject();
        json.put("text", slack.getNotificaçãoSlack());
        slack.sendMenssage(json);
    }
}
