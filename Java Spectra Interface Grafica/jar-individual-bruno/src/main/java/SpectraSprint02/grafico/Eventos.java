package SpectraSprint02.grafico;

import SpectraSprint02.VIEW.IniciarSistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Eventos extends JFrame {

    private JPanel telas;

    public Eventos(){

        this.setBounds(0, 0, 800, 500);
        CardLayout ctrlTela = new CardLayout();
        telas = new JPanel(ctrlTela);

        IniciarSistema novoSistema = new IniciarSistema();

        LoginGui loginGui = new LoginGui(telas, this, novoSistema);
        CadMaquinaGui cadMaquinaGui = new CadMaquinaGui(telas, this, novoSistema);
        MonitoramentoGui monitoramentoGui  = new MonitoramentoGui(telas, this, novoSistema);

        telas.add(loginGui, "Tela Login");
        telas.add(cadMaquinaGui, "Tela Maquina");
        telas.add(monitoramentoGui, "Tela Monitoramento");

        this.add(telas);

        // Config
        ImageIcon icon = new ImageIcon(getClass().getResource("/LOGO_SPECTRA2-quadrado.png"));
        setIconImage(icon.getImage());
        this.setTitle("Spectra");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    public void abrirTelaCadastroMaquina(String email, String senha) {
        CardLayout ctrlTela = (CardLayout) telas.getLayout();
        CadMaquinaGui cadMaquinaGui = (CadMaquinaGui) telas.getComponent(1); // Assumindo que "CadMaquinaGui" está na posição 1
        cadMaquinaGui.setCredenciais(email, senha);
        ctrlTela.show(telas, "Tela Maquina");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Eventos();
        });
    }

}
