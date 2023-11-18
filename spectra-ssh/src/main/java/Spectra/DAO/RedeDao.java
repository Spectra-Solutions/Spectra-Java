package Spectra.DAO;

import Spectra.DTO.Rede;
import org.springframework.dao.EmptyResultDataAccessException;

public class RedeDao extends Dao{
    Rede rede = new Rede();

    public RedeDao(){
        super();
    }
    @Override
    public void getFkMaquina(String hostName) {
        String sql = "SELECT idMaquina FROM Maquina WHERE hostName = ?";

        try {
            Integer idMaquina = conMySQl.queryForObject(sql, Integer.class, hostName);
            rede.setFkMaquina(idMaquina);
            getFkComponente();
        }

        catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado no fkMaquina Rede");
        }
    }

    @Override
    public void getFkComponente() {
        String sql = "SELECT idComponente FROM Componente WHERE idComponente = 4";

        try {
            Integer idComponente = conMySQl.queryForObject(sql, Integer.class);
            rede.setFkComponente(idComponente);
            salvarDados();
        }

        catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado encontrado no fkComponente Cpu");
        }
    }

    @Override
    public void salvarDados() {
        String sql = "INSERT INTO RegistroComponente (idRegistroComponente, consumoUpload, consumoDownload, fkComponente, fkMaquina) VALUES (?, ?, ?, ?, ?)";
        Integer linhasAlteradas = conMySQl.update(sql, rede.getIdRegistro(), rede.getConsumoUpload(), rede.getConsumoDownload(), rede.getFkComponente(), rede.getFkMaquina());

        if (linhasAlteradas > 0){
            System.out.println("Inserção no Mysql Rede realizada com sucesso!");
        }

        else {
            System.out.println("Erro ao inserir no MySQL Rede!");
        }
    }

}
