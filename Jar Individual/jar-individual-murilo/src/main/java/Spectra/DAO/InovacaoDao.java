package Spectra.DAO;

import Spectra.Connection.ConexaoMysQl;
import Spectra.Connection.ConexaoSQLServer;
import Spectra.DTO.Inovacao;
import Spectra.DTO.Maquina;
import Spectra.Log.Log;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.Map;

public class InovacaoDao {
    Inovacao inovacao = new Inovacao();
    Log log = new Log();
    Maquina maquina = new Maquina();
    SlackDao slackDao = new SlackDao();
    ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
    protected JdbcTemplate conSqlServer = conexaoSQLServer.getConexaoSqlServer();

    public String executarComandoMaquina() throws IOException {

        String sql = """
            SELECT Maquina.hostName, Comando.idComando, Comando.nomeComando, Comando.stattus
                FROM Maquina
                    JOIN Comando 
                        ON Maquina.idMaquina = Comando.fkMaquina
                            WHERE Maquina.hostName = ? 
                                AND Comando.stattus = 1""";

        try {


            Map<String, Object> resultadoMap = conSqlServer.queryForMap(sql, maquina.getHostName());

            Integer idComando = (Integer) resultadoMap.get("idComando");
            String nomeComando = (String) resultadoMap.get("nomeComando");
            String hostName = (String) resultadoMap.get("hostName");
            Boolean status = (Boolean) resultadoMap.get("stattus");

            if (status){

                try {
                    conSqlServer.update("UPDATE Comando SET stattus = 0 WHERE idComando = ?", idComando);

                    if (hostName.equalsIgnoreCase(maquina.getHostName())) {
                        if (maquina.getSistemaOperacional().equalsIgnoreCase("Linux") || maquina.getSistemaOperacional().equalsIgnoreCase("Ubuntu")) {

                            if (nomeComando.equalsIgnoreCase("sudo shutdown -h now")) {
//                                slackDao.desligarMaquina(maquina.getHostName());

                                log.setMensagem(String.format("""
                                        A maquina %s foi desligada""", maquina.getHostName()));
                                log.gerarLog("inovacao");

                                inovacao.desligarMaquinaLinux();
                            }

                            else if (nomeComando.equalsIgnoreCase("sudo shutdown -r now")) {
//                                slackDao.reiniciarMaquina(maquina.getHostName());

                                log.setMensagem(String.format("""
                                        A maquina %s foi reiniciada""", maquina.getHostName()));
                                log.gerarLog("inovacao");

                                inovacao.reiniciarMaquinaLinux();
                            }
                        }

                        else if (maquina.getSistemaOperacional().equalsIgnoreCase("Windows")) {
                            if (nomeComando.equalsIgnoreCase("shutdown /s /f /t 0")) {
//                                slackDao.desligarMaquina(maquina.getHostName());

                                log.setMensagem(String.format("""
                                        A maquina %s foi desligada""", maquina.getHostName()));
                                log.gerarLog("inovacao");

                                inovacao.desligarMaquinaWindows();
                            }

                            else if (nomeComando.equalsIgnoreCase("shutdown /r /f /t 0")) {
//                                slackDao.reiniciarMaquina(maquina.getHostName());

                                log.setMensagem(String.format("""
                                        A maquina %s foi reiniciada""", maquina.getHostName()));
                                log.gerarLog("inovacao");

                                inovacao.reiniciarMaquinaWindows();
                            }
                        }
                    }
                }

                catch (EmptyResultDataAccessException e){
                    log.setMensagem(String.format("Trocar o status do comando deu errado!! %s", e));
                    log.gerarLog("inovacao");

                    System.err.println("Trocar o status do comando deu errado!! %s");
                    return null; // Retorna null em caso de exceção
                }
            }

            else {
                log.setMensagem(String.format("Esse comando nessa máquina ja foi executado!!"));
                log.gerarLog("inovacao");

                System.out.println("Comando ja foi executado!");
            }

            return nomeComando;
        }

        catch (EmptyResultDataAccessException e) {
            log.setMensagem(String.format("O comando de desligar e reiniciar a maquina não foi encontrado!! %s", e));
            log.gerarLog("inovacao");

            System.out.println("O comando de desligar e reiniciar a maquina não foi encontrado!!");
            return null; // Retorna null em caso de exceção
        }
    }
}
