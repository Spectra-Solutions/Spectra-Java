package Spectra.DTO;

import java.io.IOException;

public class Inovacao {
    private String comandoDesligar, comandoReiniciar;

    public Inovacao() {
        this.comandoDesligar = "";
        this.comandoReiniciar = "";
    }

    public void desligarMaquinaLinux(){
        comandoDesligar = "sudo shutdown -h now";

        try {
            Process processoDesligar = Runtime.getRuntime().exec(comandoDesligar);
            Integer exitCodeDesligar = processoDesligar.waitFor();
            System.out.println(exitCodeDesligar);

        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public void desligarMaquinaWindows(){
        comandoDesligar = "shutdown /s /f /t 0";

        try {
            Process processoDesligar = Runtime.getRuntime().exec(comandoDesligar);
            Integer exitCodeDesligar = processoDesligar.waitFor();
            System.out.println(exitCodeDesligar);

        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public void reiniciarMaquinaLinux(){
        comandoReiniciar = "sudo shutdown -r now";

        try {
            Process processoDesligar = Runtime.getRuntime().exec(comandoReiniciar);
            Integer exitCodeDesligar = processoDesligar.waitFor();
            System.out.println(exitCodeDesligar);

        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public void reiniciarMaquinaWindows(){
        comandoReiniciar = "shutdown /r /f /t 0";

        try {
            Process processoDesligar = Runtime.getRuntime().exec(comandoReiniciar);
            Integer exitCodeDesligar = processoDesligar.waitFor();
            System.out.println(exitCodeDesligar);

        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
