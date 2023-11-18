package Spectra.VIEW;

import Spectra.DAO.*;
import Spectra.DTO.Funcionario;
import Spectra.DTO.Maquina;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class IniciarSistema {
    Funcionario func = new Funcionario();
    FuncionarioDao validarFuncionario = new FuncionarioDao();
    Maquina maqui = new Maquina();
    MaquinaDao validarMaquina = new MaquinaDao();
    CpuDao cpuDao = new CpuDao();
    MemoriaRamDao memoriaRamDao = new MemoriaRamDao();
    DiscoDao discoDao = new DiscoDao();
    RedeDao redeDao = new RedeDao();
    ProcessoDao processoDao = new ProcessoDao();
    Timer timer = new Timer();
    Scanner inText = new Scanner(System.in);

    public void validarLogin(){
        String emailDigitado, senhaDigitada;
        Integer comprimentoMinimo = 8;

        System.out.println("""
                Bem vindo ao Sistema de monitoramento Spectra!

                Para iniciar o monitoramento, insira suas credenciais!
                """);

        System.out.println("Digite seu email:" +
                "");
        emailDigitado = inText.nextLine();
        func.setEmail(emailDigitado);

        System.out.println("Digite sua senha:" +
                "");
        senhaDigitada = inText.nextLine();
        func.setSenha(senhaDigitada);

        if (emailDigitado.equals("") && senhaDigitada.equals("")){
            System.out.println("Preencha os campos!");
        }

        else {
            if (emailDigitado.indexOf("@") >= 0 && emailDigitado.indexOf(".") >=0){
                if (senhaDigitada.length() >= comprimentoMinimo){
                    if (validarFuncionario.existEmail(func.getEmail(), func.getSenha())){
                        System.out.println("""
                                Usuario logado com sucesso
                                
                                O monitoramento já foi iniciado!
                                Acesse a dashboard para visualizar: http://34.234.237.115:3333
                                """);
                        validarMaquina();
                    } else {
                        System.out.println("""
                                
                                Desculpe, não encontramos nenhum usuário correspondente a esse e-mail.
                                """);
                        validarLogin();
                    }
                }
                else {
                    System.out.println("A senha deve conter no mínimo 8 caracteres. Por favor, insira uma senha com pelo menos 8 caracteres para garantir a segurança da sua conta.");
                }
            }
            else {
                System.out.println("O e-mail inserido parece estar incompleto ou inválido. Certifique-se de incluir '@' e '.' para formar um endereço de e-mail válido.");
            }
        }
    }

    public void validarMaquina(){
        String nomeDigitado, secaoDigitada;

        if (validarMaquina.existHostName(maqui.getHostName())){
            System.out.println("Esta máquina já está registrada em nosso sistema.");
            capturarDados();
        }

        else {
            System.out.println("""
                    Não encontramos nenhum registro dessa máquina em nosso sistema. Por favor, proceda com o cadastro para que possamos incluir as informações dela em nossa base de dados.
                    """);

            System.out.println("Por favor, insira o nome da máquina:");
            nomeDigitado = inText.nextLine();
            maqui.setNome(nomeDigitado);

            System.out.println("A seção a qual ela pertence:");
            secaoDigitada = inText.nextLine();
            maqui.setSecao(secaoDigitada);

            validarMaquina.getFkEmpresa(nomeDigitado, secaoDigitada, func.getEmail(), func.getSenha());
            capturarDados();
        }
    }

    public void capturarDados(){
        final long segundos = (1000 * 5);

        TimerTask tarefa = new TimerTask() {
            @Override
            public void run() {
                cpuDao.getFkMaquina(maqui.getHostName());

                memoriaRamDao.getFkMaquina(maqui.getHostName());

                discoDao.getFkMaquina(maqui.getHostName());

                redeDao.getFkMaquina(maqui.getHostName());

                processoDao.getFkMaquina(maqui.getHostName());

                System.out.println("""
                        Dados inseridos!!
                        """);
            }
        };

        timer.scheduleAtFixedRate(tarefa, 0, segundos);

        System.out.println("""
                Para encerrar o monitoramento, digite 'sim'
                """);

        String finalizacao = inText.nextLine();
        if (finalizacao.equalsIgnoreCase("sim")){
            System.out.println("Finalizando...");

            timer.cancel();
            System.exit(0);
        }
    }
}
