package SpectraSprint02.VIEW;

import SpectraSprint02.DAO.*;
import SpectraSprint02.DTO.Funcionario;
import SpectraSprint02.DTO.Maquina;
import SpectraSprint02.grafico.LoginGui;
import com.mysql.cj.log.Log;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class IniciarSistema {

    public Boolean iniciar = false;
    FuncionarioDao validarFuncionario = new FuncionarioDao();
    MaquinaDao maquinaDao = new MaquinaDao();
    Maquina maquina = new Maquina();
    Funcionario func = new Funcionario();
    MemoriaRamDao memoriaRamDao = new MemoriaRamDao();
    RedeDao redeDao = new RedeDao();
    CPUDao cpuDao = new CPUDao();
    DiscoDao discoDao = new DiscoDao();
    ProcessoDao processoDao = new ProcessoDao();
    public Timer timer = new Timer();

    public String validarLogin(String email, String senha) {

        Integer comprimentoMinino = 8;

        if (email.isEmpty() || senha.isEmpty()) {
            // JOptionPane.showMessageDialog(null, "Não pode deixar o campo vazio");

            return "vazio";
        } else {
            if (email.indexOf("@") >= 0 && email.indexOf(".") >= 0 && senha.length() >= comprimentoMinino) {

                func.setEmail(email);
                func.setSenha(senha);

                System.out.println(func.toString());

                if (validarFuncionario.existEmail(func.getEmail(), func.getSenha())) {

                   // JOptionPane.showMessageDialog(null, "Usuário logado com sucesso!!");
                    return "logado";

                } else {
                    //JOptionPane.showMessageDialog(null, "Usuário não existe no sistema!!");

                    return "desconhecido";
                }

            } else {
                //JOptionPane.showMessageDialog(null, "Falta @ no seu email!! ou falta . no seu email!! ou sua senha está incorreta!!");

                return "erro";
            }

        }

    }



    public Boolean validarMaquina(){


        if (maquinaDao.existHostName(maquina.getHostName())){
            System.out.println("Maquina existe, não precisa fazer um novo cadastro");

            return true;

        } else {

            return false;
        }

    }

    public void iniciarMonitoramentoAposLogin(Boolean iniciar){

        this.iniciar = iniciar;
    }

    public void capturarDados(){

        System.out.println("Esta capturando");

            final  long SEGUNDOS = (1000 * 5);

            TimerTask tarefa = new TimerTask() {
                @Override
                public void run() {
                    cpuDao.getFkComponenteCPU();
                    memoriaRamDao.getFkComponenteRAM();
                    discoDao.getFkComponenteDisco();
                    redeDao.getFkComponenteRede();
                    processoDao.getfkMaquina();

                    System.out.println("dado");
                }
            };

            timer.scheduleAtFixedRate(tarefa, 0, SEGUNDOS);
    }
}
