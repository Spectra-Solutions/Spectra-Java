package SpectraSprint02.DAO;

import SpectraSprint02.DTO.ServicoClass;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.servicos.Servico;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ServicoDao {
    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();
    ServicoClass servicoClass = new ServicoClass();
    Looca looca = new Looca();
    List<Servico> servicos = looca.getGrupoDeServicos().getServicos();

    public void getfkMaquina(){
        String sql = "SELECT idMaquina FROM Maquina";
        Integer idMaquina = null;

        try {
            idMaquina = con.queryForObject(sql, Integer.class);
            servicoClass.setFkMaquina(idMaquina);
            salvarDadosServicos();
        } catch (EmptyResultDataAccessException e){
            System.out.println("Nenhum resultado no idMaquina no processo!");
        }
    }

    public void salvarDadosServicos(){
        for (int i = 0; i < servicos.size(); i++) {
            Servico servicoDaVez = servicos.get(i);

            servicoClass.setPid((long) servicoDaVez.getPid());
            servicoClass.setNomeServico(servicoDaVez.getNome());
            servicoClass.setEstado(servicoDaVez.getEstado());

            con.update("INSERT INTO Servico (idServico, Pid, nomeServico, estado, fkMaquinaServico) VALUES (?, ?, ?, ?, ?)",
                      servicoClass.getIdServico(), servicoClass.getPid(), servicoClass.getNomeServico(), servicoClass.getEstado(), servicoClass.getFkMaquina());
        }
    }
}
