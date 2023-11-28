package Spectra.DAO;

import Spectra.Connection.ConexaoMysQl;
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
    ConexaoMysQl conexaoMysQl = new ConexaoMysQl();
    protected JdbcTemplate conMySQl = conexaoMysQl.getConexaoMySQl();
    Maquina maquina = new Maquina();

    public String executarComandoMaquina() throws IOException {

        String sql = """
            SELECT Maquina.hostName, Comando.idComando, Comando.nomeComando, Comando.stattus
                FROM Maquina
                    JOIN Comando 
                        ON Maquina.idMaquina = Comando.fkMaquina
                            WHERE hostName = ?""";

        try {


            Map<String, Object> resultadoMap = conMySQl.queryForMap(sql, maquina.getHostName());

                Integer idComando = (Integer) resultadoMap.get("idComando");

                String nomeComando = (String) resultadoMap.get("nomeComando");
                String hostName = (String) resultadoMap.get("hostName");

                Boolean status = (Boolean) resultadoMap.get("stattus");

                if (status){

                    try {
                        conMySQl.update("UPDATE Comando SET stattus = 0 WHERE idComando = ?", idComando);

                        if (hostName.equalsIgnoreCase(maquina.getHostName())) {
                            if (maquina.getSistemaOperacional().equalsIgnoreCase("Linux") && maquina.getSistemaOperacional().equalsIgnoreCase("Ubuntu")) {

                                if (nomeComando.equalsIgnoreCase("sudo shutdown -h now")) {

                                    inovacao.desligarMaquinaLinux();
                                }

                                else if (nomeComando.equalsIgnoreCase("sudo shutdown -r now")) {


                                    inovacao.reiniciarMaquinaLinux();
                                }
                            }

                            else if (maquina.getSistemaOperacional().equalsIgnoreCase("Windows")) {
                                if (nomeComando.equalsIgnoreCase("shutdown /s /f /t 0")) {


                                    inovacao.desligarMaquinaWindows();
                                }

                                else if (nomeComando.equalsIgnoreCase("shutdown /r /f /t 0")) {


                                    inovacao.reiniciarMaquinaWindows();
                                }
                            }
                        }
                    }

                    catch (EmptyResultDataAccessException e){

                        System.err.println("Trocar o status do comando deu errado!! %s");
                        return null; // Retorna null em caso de exceção
                    }

                }

                else {;

                    System.out.println("Comando ja foi executado!");
                }

                return nomeComando;
        }

        catch (EmptyResultDataAccessException e) {

            System.out.println("O comando de desligar e reiniciar a maquina não foi encontrado!!");
            return null; // Retorna null em caso de exceção
        }
    }
}
