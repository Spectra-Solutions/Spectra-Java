package JarIndividual.grafico;

import JarIndividual.VIEW.IniciarSistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonitoramentoGui extends TelaPanel{

    // monitoramento
    private IniciarSistema novoSistema;

    // Botao
    JButton btnEncerrar;
    JButton btnIniciar;
    private JButton btnIdioma;

    // Texto
    private JLabel lblInfo1;
    private JLabel lblInfo2;
    private JLabel lblInfo3;

    // Imagem
    private JLabel lblImagem;
    ImageIcon img;

    //verificação do ingles
    private boolean estaIngles = false;
    public MonitoramentoGui(JPanel telas, JFrame janela, IniciarSistema novoSistema) {

        super(telas, janela);
        this.novoSistema = novoSistema;

        // Painel
        setLayout(new GridBagLayout());
        setBackground(Color.decode("#ffffff"));

        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets = new Insets(5, 5, 5, 5);
        setBackground(Color.decode("#052767"));

        // Campos de label
        lblInfo1 = new JLabel("Para iniciar o monitoramento, clique em 'Iniciar Monitoramento' abaixo ");
        lblInfo2 = new JLabel("Acesse http://34.234.237.115:3333 para visualizar");
        lblInfo3 = new JLabel("Para encerrar o monitoramento, clique no botão abaixo!");

        // Edição fonte
        Font fontLbl = new Font("Poppins", Font.BOLD, 15);
        lblInfo1.setFont(fontLbl);
        lblInfo1.setForeground(Color.decode("#ffffff"));
        lblInfo2.setFont(fontLbl);
        lblInfo2.setForeground(Color.decode("#ffffff"));
        lblInfo3.setFont(fontLbl);
        lblInfo3.setForeground(Color.decode("#ffffff"));

        // Imagem
        ImageIcon img = new ImageIcon("imagens/LOGO_SPECTRA2.jpg");
        // Definir a dimensão
        Dimension maxDimImg = new Dimension(300, 70);
        // Redimensionar a imagem
        Image scaledImage = img.getImage().getScaledInstance(maxDimImg.width, maxDimImg.height, Image.SCALE_SMOOTH);
        img = new ImageIcon(scaledImage);

        // Definir o JLabel para exibir a imagem
        lblImagem = new JLabel(img, JLabel.CENTER);
        lblImagem.setPreferredSize(maxDimImg);

        // Botao
        btnEncerrar = new JButton("Encerrar Monitoramento");
        // Tamanho
        Dimension maxDimBtn = new Dimension(300, 40);
        btnEncerrar.setPreferredSize(maxDimBtn);
        // Edicao
        Font fontBtn = new Font("Poppins", Font.BOLD, 13);
        btnEncerrar.setBackground(Color.red);
        btnEncerrar.setFont(fontBtn);
        btnEncerrar.setForeground(Color.WHITE);
        btnEncerrar.addActionListener(this);

        btnIniciar = new JButton("Iniciar Monitoramento");
        // Tamanho
        btnIniciar.setPreferredSize(maxDimBtn);
        // Edicao
        btnIniciar.setBackground(Color.decode("#01C38E"));
        btnIniciar.setFont(fontBtn);
        btnIniciar.setForeground(Color.WHITE);

        btnIniciar.addActionListener(e -> {
            iniciarMonitoramento();
        });

        // Botao Idioma
        btnIdioma = new JButton("Change Language");
        // Tamanho
        Dimension maxDimBtnIdioma = new Dimension(200, 40);
        btnIdioma.setPreferredSize(maxDimBtnIdioma);
        // Edicao
        Font fontBtnIdioma = new Font("Poppins", Font.BOLD, 13);
        btnIdioma.setBackground(Color.decode("#ffffff"));
        btnIdioma.setFont(fontBtnIdioma);
        btnIdioma.setForeground(Color.BLACK);

        // Adicionando componentes
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.gridwidth = 2;
        constraint.anchor = GridBagConstraints.CENTER;
        add(lblImagem, constraint);

        constraint.gridx = 0;
        constraint.gridy = 1;
        add(lblInfo1, constraint);

        constraint.gridy = 2;
        add(lblInfo2, constraint);
        lblInfo2.setVisible(false);

        constraint.gridy = 3;
        add(btnIniciar, constraint);

        constraint.gridy = 4;
        add(new JLabel(" "), constraint);

        constraint.gridy = 5;
        add(lblInfo3, constraint);

        constraint.gridy = 6;
        add(btnEncerrar, constraint);

        constraint.gridy = 7;
        add(btnIdioma, constraint);


        btnIdioma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (estaIngles) {
                    btnIdioma.setText("Change Language");

                    lblInfo1.setText("Para iniciar o monitoramento, clique em 'Iniciar Monitoramento' abaixo ");
                    btnIniciar.setText("Iniciar Monitoramento");
                    lblInfo2.setText("Acesse http://34.234.237.115:3333 para visualizar");
                    lblInfo3.setText("Para encerrar o monitoramento, clique no botão abaixo!");
                    btnEncerrar.setText("Encerrar Monitoramento");

                    estaIngles = false;
                } else {
                    btnIdioma.setText("Mudar Idioma");

                    lblInfo1.setText("To start monitoring, click 'Start Monitoring' below");
                    btnIniciar.setText("Start Monitoring");
                    lblInfo2.setText("Visit http://34.234.237.115:3333 to view");
                    lblInfo3.setText("To end monitoring, click the button below!");
                    btnEncerrar.setText("End Monitoring");

                    estaIngles = true;
                }
            }
        });

    }

    public void executarBotao(ActionEvent e) {

        if (this.encerrarMonitoramento()) {

            btnIniciar.setEnabled(true);
            lblInfo1.setText("Para iniciar o monitoramento, clique em 'Iniciar Monitoramento' abaixo ");
            lblInfo2.setVisible(false);

            novoSistema.timer.cancel();
            System.exit(0);

        }

    }

    public void iniciarMonitoramento(){

        if(novoSistema.iniciar){

            btnIniciar.setEnabled(false);
            lblInfo1.setText("ATENÇÃO O MONITORAMENTO FOI INICIADO");
            lblInfo2.setVisible(true);

            novoSistema.capturarDados();
        }
    }

    public Boolean encerrarMonitoramento() {

        return true;
    }


}
