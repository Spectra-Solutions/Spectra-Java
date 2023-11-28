package SpectraSprint02.grafico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPanel extends JPanel implements ActionListener {

    private static JPanel telas;
    private static CardLayout controleTela;
    private JFrame janela;

    public TelaPanel(JPanel telas, JFrame janela) {
        this.telas = telas;
        this.controleTela = (CardLayout) telas.getLayout();
        this.janela = janela;
        this.setBackground(Color.decode("#fffff"));

        this.setLayout(null);
    }

    public TelaPanel() {

    }


    public void actionPerformed(ActionEvent evento){
        executarBotao(evento);
    }

    protected void trocarCores(ActionEvent evento) {
    }

    protected void trocarCores2(ActionEvent evento) {
    }

    protected void executarBotao(ActionEvent evento){}

    protected void executarBotaoIniciar(ActionEvent evento){}

    protected static void trocarTela(String identificador){
        controleTela.show(telas,identificador);
    }

}
