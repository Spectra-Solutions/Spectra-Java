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

            log.setMensagem("O usuario não preencheu todos os campos");
            log.gerarLog("acesso");

            System.err.println("Preencha os campos!");
        }

        else {
            if (emailDigitado.indexOf("@") >= 0 && emailDigitado.indexOf(".") >=0){
                if (senhaDigitada.length() >= comprimentoMinimo){
                    if (validarFuncionario.existEmail(func.getEmail(), func.getSenha())){

                        log.setMensagem(String.format("""
                                O %s foi logado com sucesso
                                """, func.getEmail()));
                        log.gerarLog("acesso");

                        System.out.println("""
                                Usuario logado com sucesso
                                """);

                        slackDao.getUrlEmpresa(func.getEmail(), func.getSenha());

                        validarMaquina();
                    } else {
                        log.setMensagem(String.format("""
                                Desculpe, não encontramos nenhum usuário correspondente a esse e-mail %s
                                """, func.getEmail()));
                        log.gerarLog("acesso");

                        System.err.println("""
                                Desculpe, não encontramos nenhum usuário correspondente a esse e-mail.
                                """);
                        validarLogin();
                    }
                }
                else {
                    log.setMensagem(String.format("""
                            O usuario com o e-mail %s, tentou inserir uma senha com menos de 8 caracteres.
                            """, emailDigitado));
                    log.gerarLog("acesso");

                    System.err.println("A senha deve conter no mínimo 8 caracteres. Por favor, insira uma senha com pelo menos 8 caracteres para garantir a segurança da sua conta.");
                    validarLogin();
                }
            }
            else {
                log.setMensagem(String.format("""
                            O usuario com o e-mail %s, tentou inserir um e-mail incompleto.
                            """, emailDigitado));
                log.gerarLog("acesso");

                System.err.println("O e-mail inserido parece estar incompleto ou inválido. Certifique-se de incluir '@' e '.' para formar um endereço de e-mail válido.");
                validarLogin();
            }
        }
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
                    
                    Acesse a dashboard para visualizar: http://34.234.237.115:3333""");
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
                    processoDao.getFkMaquina(maqui.getHostName());
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

                try {
                    verificarComando.executarComandoMaquina();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

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
