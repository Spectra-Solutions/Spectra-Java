package Spectra.VIEW;

import Spectra.DAO.*;
import Spectra.DTO.Funcionario;
import Spectra.DTO.Maquina;
import Spectra.Log.Log;

import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class IniciarSistema {
    Log log = new Log();
    Funcionario func = new Funcionario();
    FuncionarioDao validarFuncionario = new FuncionarioDao();
    Maquina maqui = new Maquina();
    MaquinaDao validarMaquina = new MaquinaDao();
    SlackDao slackDao = new SlackDao();
    CpuDao cpuDao = new CpuDao();
    MemoriaRamDao memoriaRamDao = new MemoriaRamDao();
    DiscoDao discoDao = new DiscoDao();
    RedeDao redeDao = new RedeDao();
    ProcessoDao processoDao = new ProcessoDao();
    InovacaoDao verificarComando = new InovacaoDao();
    Timer timer = new Timer();
    Scanner inText = new Scanner(System.in);

    public void validarLogin() throws IOException {
        Integer comprimentoMinimo = 8;

        System.out.println("""
                Bem vindo ao Sistema de monitoramento Spectra!
                
                                  ad88888ba
                                 d8       8b                                        ,d                            
                                 S8,                                                88                            
                                  S8aaaaa,    8b,dPPSba,    ,adPPYba,  ,adPPSba,   MM88MMM  8b,dPPSba,  ,adPPSSba,
                                         8b,  88P      8a  a8P_____88  a8    ,aa    88      88P     S8          S8
                                          8b  88       d8  8PP         8b           88      88          ,adPPPPP88
                                 S8a     a8P  88b,   q,a8   8b,   ,aa  8a,   ,aa    88,     88          88,    ,88
                                   S88888P    88 SbbdP        Sbbd8    ,adPPSba,    S888    88          088bbdPiS8
                                              88                                                                  
                                              88                                                                  
                                                                                       
                                Para iniciar o monitoramento, insira suas credenciais!
                """);

        for (int tentativas = 1; tentativas <= 3; tentativas++) {
            System.out.println("Digite seu email:");
            String emailDigitado = inText.nextLine();
            func.setEmail(emailDigitado);

            System.out.println("Digite sua senha:");
            String senhaDigitada = inText.nextLine();
            func.setSenha(senhaDigitada);

            if (emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {
                log.setMensagem("O usuário não preencheu todos os campos");
                log.gerarLog("acesso");

                System.err.println("Ops! Parece que você esqueceu de preencher alguns campos. Por favor, preencha todos os campos obrigatórios e tente novamente.");
            } else if (!emailDigitado.contains("@") || !emailDigitado.contains(".")) {
                log.setMensagem(String.format("O usuário com o e-mail %s tentou inserir um e-mail incompleto.", emailDigitado));
                log.gerarLog("acesso");

                System.err.println("O e-mail inserido parece estar incompleto ou inválido. Certifique-se de incluir '@' e '.' para formar um endereço de e-mail válido.");
            } else if (senhaDigitada.length() < comprimentoMinimo) {
                log.setMensagem(String.format("O usuário com o e-mail %s tentou inserir uma senha com menos de 8 caracteres.", emailDigitado));
                log.gerarLog("acesso");

                System.err.println("A senha deve conter no mínimo 8 caracteres. Por favor, insira uma senha com pelo menos 8 caracteres para garantir a segurança da sua conta.");
            }

            else {
                if (validarFuncionario.existEmailSqlServer(func.getEmail(), func.getSenha())) {
                    log.setMensagem(String.format("O %s foi logado com sucesso.", func.getEmail()));
                    log.gerarLog("acesso");

                    System.out.println("""
                            Usuário logado com sucesso.
                            """);

                    slackDao.getUrlEmpresa(func.getEmail(), func.getSenha());
                    validarMaquina();
                } else {
                    log.setMensagem(String.format("Desculpe, não encontramos nenhum usuário correspondente ao e-mail %s.", func.getEmail()));
                    log.gerarLog("acesso");

                    System.err.println("Desculpe, não encontramos nenhum usuário correspondente a esse e-mail.");
                }
            }
        }

        System.out.println("Número máximo de tentativas alcançado. Encerrando...");
        System.exit(0);
    }

    public void validarMaquina() throws IOException {
        String nomeDigitado, secaoDigitada;

        if (validarMaquina.existHostName(maqui.getHostName())){
            log.setMensagem(String.format("""
                    O %s começou o monitoramento da maquina %s  
                    """, func.getEmail(), maqui.getHostName()));
            log.gerarLog("acesso");

            System.out.println("""
                    Esta máquina já está registrada em nosso sistema.
                    
                    O monitoramento ja foi iniciado!
                    Acesse a dashboard para visualizar: http://44.216.221.58/home""");
            capturarDados();
        }

        else {
            log.setMensagem(String.format("""
                    O %s está cadastrando uma nova máquina...  
                    """, func.getEmail()));
            log.gerarLog("acesso");

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

        try {
            processoDao.getFkMaquina(maqui.getHostName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final long segundos = (1000 * 5);

        TimerTask tarefa = new TimerTask() {
            @Override
            public void run() {
                try {
                    validarMaquina.atualizarTempoAtividade();
                } catch (IOException e){
                    throw new RuntimeException(e);
                }

                try {
                    cpuDao.getFkMaquina(maqui.getHostName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    memoriaRamDao.getFkMaquina(maqui.getHostName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    discoDao.getFkMaquina(maqui.getHostName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    redeDao.getFkMaquina(maqui.getHostName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    verificarComando.executarComandoMaquina();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    slackDao.getSelectCpu();
                } catch (IOException e){
                    throw new RuntimeException(e);
                }

                try {
                    slackDao.getSelectRam();
                } catch (IOException e){
                    throw new RuntimeException(e);
                }

                try {
                    slackDao.getSelectDisco();
                } catch (IOException e){
                    throw new RuntimeException(e);
                }

                System.out.println("""
                        Inserção bem-sucedida dos dados na base de dados
                        """);
            }
        };

        timer.scheduleAtFixedRate(tarefa, 0, segundos);

        String finalizacao = "" ;

        while (!finalizacao.equalsIgnoreCase("sim")){
            System.out.println("""
                    Para encerrar o monitoramento, digite 'sim'
                    """);
            finalizacao = inText.nextLine();

            if (finalizacao.equalsIgnoreCase("sim")){
                System.out.println("Finalizando...");

                timer.cancel();
                System.exit(0);
            }
        }
    }
}
