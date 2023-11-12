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
Bem vindo ao Sistema de monitoramento Spectra!
                    
"  ad88888ba                                                                               "
" d8       8b                                           ,d                                 "
" Y8,                                                   88                                 "
" `Y8aaaaa,    8b,dPPYba,    ,adPPYba,    ,adPPYba,     MM88MMM    8b,dPPYba,  ,adPPYYba,  "
"  `     8b,   88P'     8a   a8P_____88   a8       a    88         88P'    Y8         `Y8  "
"        `8b   88       d8   8PP         8b             88         88          ,adPPPPP88  "
" Y8a     a8P  88b,   ,a8    8b,   ,aa   8a,     ,a     88,        88          88,    ,88  "
"  Y88888P     88`YbbdP '    ` Ybbd8 '    `iYbbd8i      'Y888      88          `88bbdPiY8  "
"              88                                                                          "
"              88                                                                          "
                  
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

                if (validarFuncionario.existEmail(func.getEmail(), func.getSenha()) && validarFuncionario.existEmailSqlServer(func.getEmail(), func.getSenha())) {
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

        if (maquinaDao.existHostName(maquina.getHostName()) && maquinaDao.existHostNameSqlServer(maquina.getHostName())){
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

            capturarDados();
        }
    }

    public void capturarDados(){
        final  long SEGUNDOS = (1000 * 5);

        TimerTask tarefa = new TimerTask() {
            @Override
            public void run() {
                cpuDao.getFkComponenteCPU(maquina.getHostName());
                memoriaRamDao.getFkComponenteRAM(maquina.getHostName());
                discoDao.getFkComponenteDisco(maquina.getHostName());
                redeDao.getFkComponenteRede(maquina.getHostName());
                processoDao.getfkMaquina(maquina.getHostName());
                System.out.println("""
                        Dados inseridos!!
                        """);
            }
        };

        timer.scheduleAtFixedRate(tarefa, 0, SEGUNDOS);

        System.out.println("""
                Para encerrar o monitoramento, digite 'sim'
                """);

        String finalização = inText.nextLine();

        if (finalização.equalsIgnoreCase("sim")){
            System.out.println("Finalizando...");

            timer.cancel();
            System.exit(0);
        }
    }
}
