package SpectraSprint02.VIEW;

import SpectraSprint02.DAO.*;
import SpectraSprint02.DTO.Funcionario;
import SpectraSprint02.DTO.Maquina;
import SpectraSprint02.DTO.ServicoClass;


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
    ServicoDao servicoDao = new ServicoDao();
    ServicoClass servicoClass = new ServicoClass();
    Timer timer = new Timer();
    Scanner inText = new Scanner(System.in);

    public void validarLogin(){
        String emailDigitado, senhaDigitada;
        Integer comprimentoMinino = 8;

        System.out.println("""
                  Bem vindo ao Sistema de monitoramento Spectra!
                
              Para iniciar o monitoramento, insira suas credenciais!
              """);


        System.out.println("""
                Digite seu email:""");
        emailDigitado = inText.nextLine();
        func.setEmail(emailDigitado);

        System.out.println("""
                Digite sua senha:""");
        senhaDigitada = inText.nextLine();
        func.setSenha(senhaDigitada);

        if (emailDigitado.equals("") && senhaDigitada.equals("")){
            System.out.println("Nao pode deixar o campo vazio!");
        }

        else
        {
            if (emailDigitado.indexOf("@") >= 0 && emailDigitado.indexOf(".") >= 0 && senhaDigitada.length() >= comprimentoMinino) {

                if (validarFuncionario.existEmail(func.getEmail(), func.getSenha())) {
                    System.out.println("""
                            Usuario logado com sucesso!
                            
                           O monitoramento ja foi iniciado!
                Acesse a dashboard para visualizar: http://34.234.237.115:3333
                            """);
                    validarMaquina();
                } else {
                    System.out.println("Usuario nao existe no sistema!!");
                    validarLogin();
                }
            }
            else {
                System.out.println("Falta @ no seu email ou falta . no seu email!! ou sua senha está incorreta!!");
            }
        }
    }

    public void validarMaquina(){
        String nomeDigitado, secaoDigitada;

        if (maquinaDao.existHostName(maquina.getHostName())){
            System.out.println("Maquina ja cadastrada no sistema!");
            capturarDados();
        } else {

            System.out.println("""
                                  Maquina ainda não cadastrada!
                   """);

            System.out.println("Digite o nome que você quer atribuir a maquina:");
            nomeDigitado = inText.nextLine();
            maquina.setNome(nomeDigitado);


            System.out.println("Digite a seção que a máquina se encontra: ");
            secaoDigitada = inText.nextLine();
            maquina.setSecao(secaoDigitada);

            maquinaDao.getFkEmpresa(maquina.getNome(), maquina.getSecao(),func.getEmail(), func.getSenha());

            System.out.println("""
                                  Maquina cadastrada!
                            
                           O monitoramento ja foi iniciado!
                Acesse a dashboard para visualizar: http://34.234.237.115:3333
                   """);

            exibirServico();
        }
    }

    public void exibirServico(){
        System.out.println(String.format("""
                        Olá os serviços que estão online no momento: 
                        %s
                        """, servicoClass.toString()));

        capturarDados();
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
                servicoDao.getfkMaquina();

                System.out.println("""
                        Dados inseridos!!""");
            }
        };

        timer.scheduleAtFixedRate(tarefa, 0, SEGUNDOS);

        System.out.println("""
                Para encerrar o monitoramento, digite 'sim'""");

        String finalização = inText.nextLine();

        if (finalização.equalsIgnoreCase("sim")){
            System.out.println("Finalizando...");

            timer.cancel();
            System.exit(0);
        }
    }
}
