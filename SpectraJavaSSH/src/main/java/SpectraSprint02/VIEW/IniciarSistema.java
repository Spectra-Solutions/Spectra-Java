package SpectraSprint02.VIEW;

import SpectraSprint02.DAO.*;
import SpectraSprint02.DTO.Funcionario;
import SpectraSprint02.DTO.Maquina;

import javax.swing.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class IniciarSistema {
    FuncionarioDao validarFuncionario = new FuncionarioDao();
    MaquinaDao maquinaDao = new MaquinaDao();
    Maquina maquina = new Maquina();
    Funcionario func = new Funcionario();
    MemoriaRamDao memoriaRamDao = new MemoriaRamDao();
    RedeDao redeDao = new RedeDao();
    CPUDao cpuDao = new CPUDao();
    DiscoDao discoDao = new DiscoDao();
    ProcessoDao processoDao = new ProcessoDao();
    Timer timer = new Timer();
    Scanner inText = new Scanner(System.in);

    public void validarLogin(){
        String emailDigitado, senhaDigitada;
        Integer comprimentoMinino = 8;

        System.out.println("""
                Digite seu email:""");
        emailDigitado = inText.nextLine();

        func.setEmail(emailDigitado);

        System.out.println("""
                Digite sua senha:""");
        senhaDigitada = inText.nextLine();

        func.setSenha(senhaDigitada);

        if (emailDigitado.equals("") && senhaDigitada.equals("")){
            System.out.println("Não pode deixar o campo vazio!");
        }

        else
        {
            if (emailDigitado.indexOf("@") >= 0 && emailDigitado.indexOf(".") >= 0 && senhaDigitada.length() >= comprimentoMinino) {

                if (validarFuncionario.existEmail(func.getEmail(), func.getSenha())) {
                    System.out.println("Usuário logado com sucesso!!");
                    validarMaquina();
                } else {
                    System.out.println("Usuário não existe no sistema!!");
                    validarLogin();
                }
            }
            else {
                System.out.println("Falta @ no seu email!! ou falta . no seu email!! ou sua senha está incorreta!!");
            }
        }
    }

    public void validarMaquina(){
        String nomeDigitado, secaoDigitada;

        if (maquinaDao.existHostName(maquina.getHostName())){
            System.out.println("Maquina existe, não precisa fazer um novo cadastro");
            capturarDados();
        } else {

            System.out.println("Digite o nome que você quer atribuir a maquina:");
            nomeDigitado = inText.nextLine();
            maquina.setNome(nomeDigitado);


            System.out.println("Digite a seção que a máquina se encontra: ");
            secaoDigitada = inText.nextLine();
            maquina.setSecao(secaoDigitada);

            maquinaDao.getFkEmpresa(maquina.getNome(), maquina.getSecao(),func.getEmail(), func.getSenha());

            System.out.println("""
                    Cadastrando nova maquina...""");

            System.out.println("""
                    Adicionando dados a maquina....""");

            capturarDados();
        }
    }

    public void capturarDados(){
        final  long SEGUNDOS = (1000 * 5);

        TimerTask tarefa = new TimerTask() {
            @Override
            public void run() {
                cpuDao.getFkComponenteCPU();
                memoriaRamDao.getFkComponenteRAM();
                discoDao.getFkComponenteDisco();
                redeDao.getFkComponenteRede();
                processoDao.getfkMaquina();
            }
        };

        timer.scheduleAtFixedRate(tarefa, 0, SEGUNDOS);

        System.out.println("Quer terminar a inserção?");
        String finalização = inText.nextLine();

        if (finalização.equals("sim")){
            timer.cancel();
            System.exit(0);
        }
    }
}
