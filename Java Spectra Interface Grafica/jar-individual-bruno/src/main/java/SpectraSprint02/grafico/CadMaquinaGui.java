package SpectraSprint02.grafico;

import SpectraSprint02.DAO.MaquinaDao;
import SpectraSprint02.DTO.Funcionario;
import SpectraSprint02.DTO.Maquina;
import SpectraSprint02.VIEW.IniciarSistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadMaquinaGui extends TelaPanel{

    // Funcionario
    private String emailFunc;
    private String senhaFunc;

    private IniciarSistema novoSistema;

    // validacao maquina
    Maquina maquina = new Maquina();
    MaquinaDao maquinaDao = new MaquinaDao();

    // Botoes
    private JButton btnCad;

    private JButton btnDarkMode;
    private JButton btnLightMode;

    // Campos de cadastro
    // Email
    private JLabel lblNome;
    private JTextField txtNome;

    // Senha
    private JLabel lblSecao;
    private JTextField txtSecao;

    // Texto
    private JLabel lblInfo;
    private JLabel lblInfo1;

    // Imagem
    private JLabel lblImagem;
    private JLabel lblImagemDark;

    // campos de validação
    private JLabel lblAviso;

    public CadMaquinaGui(JPanel telas, JFrame janela, IniciarSistema novoSistema) {

        super(telas, janela);
        this.novoSistema = novoSistema;

        // Painel
        setLayout(new GridBagLayout());
        setBackground(Color.decode("#ffffff"));

        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets = new Insets(5, 5, 5, 5);
        setBackground(Color.decode("#ffffff"));

        // Campos de texto
        txtNome = new JTextField();
        txtSecao = new JTextField();

        // Ajuste tamanho
        Dimension maxDimTxt = new Dimension(300, 30);
        txtNome.setPreferredSize(maxDimTxt);
        txtSecao.setPreferredSize(maxDimTxt);

        // Campos de label
        lblNome = new JLabel("Nome Máquina");
        lblSecao = new JLabel("Seção");
        lblInfo = new JLabel("Informe os dados para a identificação da máquina");
        lblInfo1 = new JLabel("Máquina ainda não cadastrada");
        lblAviso = new JLabel("");

        // Edição fonte
        Font fontTxt = new Font("Poppins", Font.PLAIN, 12);
        Font fontLbl = new Font("Poppins", Font.BOLD, 15);
        lblNome.setFont(fontLbl);
        lblNome.setForeground(Color.decode("#052767"));
        lblSecao.setFont(fontLbl);
        lblSecao.setForeground(Color.decode("#052767"));
        lblInfo.setFont(fontLbl);
        lblInfo.setForeground(Color.decode("#202020"));
        lblInfo1.setFont(fontLbl);
        lblInfo1.setForeground(Color.decode("#202020"));
        txtNome.setFont(fontTxt);
        txtSecao.setFont(fontTxt);
        lblAviso.setFont(fontLbl);
        lblAviso.setForeground(Color.black);

        // Imagem
        ImageIcon img = new ImageIcon("imagens/LOGO_SPECTRA3.png");
        // Definir a dimensão
        Dimension maxDimImg = new Dimension(300, 70);
        // Redimensionar a imagem
        Image scaledImage = img.getImage().getScaledInstance(maxDimImg.width, maxDimImg.height, Image.SCALE_SMOOTH);
        img = new ImageIcon(scaledImage);

        // Definir o JLabel para exibir a imagem
        lblImagem = new JLabel(img, JLabel.CENTER);
        lblImagem.setPreferredSize(maxDimImg);

        // Imagem
        ImageIcon imgDark = new ImageIcon(getClass().getResource("/LOGO_SPECTRA2.jpg"));
        // Definir a dimensão
        Dimension maxDimImgDark = new Dimension(300, 70);
        // Redimensionar a imagem
        Image scaledImageDark = imgDark.getImage().getScaledInstance(maxDimImgDark.width, maxDimImgDark.height, Image.SCALE_SMOOTH);
        imgDark = new ImageIcon(scaledImageDark);

        lblImagemDark = new JLabel(imgDark, JLabel.CENTER);
        lblImagemDark.setPreferredSize(maxDimImgDark);

        // Botao
        btnCad = new JButton("Cadastrar");
        // Tamanho
        Dimension maxDimBtn = new Dimension(300, 40);
        btnCad.setPreferredSize(maxDimBtn);
        // Edicao
        Font fontBtn = new Font("Poppins", Font.BOLD, 13);
        btnCad.setBackground(Color.decode("#052767"));
        btnCad.setFont(fontBtn);
        btnCad.setForeground(Color.WHITE);
        btnCad.addActionListener(this);

        btnDarkMode = new JButton("Dark");
        Dimension maxDimBtnDark = new Dimension(100,20);
        btnDarkMode.setPreferredSize(maxDimBtnDark);
        Font fontBtnDark = new Font("Poppins", Font.BOLD, 13);
        btnDarkMode.setBackground(Color.decode("#202020"));
        btnDarkMode.setFont(fontBtnDark);
        btnDarkMode.setForeground(Color.WHITE);
        btnDarkMode.addActionListener(this);

        btnLightMode = new JButton("Light");
        Dimension maxDimBtnLight = new Dimension(100,20);
        btnLightMode.setPreferredSize(maxDimBtnLight);
        Font fontBtnLight = new Font("Poppins", Font.BOLD, 13);
        btnLightMode.setBackground(Color.decode("#ffffff"));
        btnLightMode.setFont(fontBtnLight);
        btnLightMode.setForeground(Color.BLACK);
        btnLightMode.addActionListener(this);


        // Adicionando componentes
        // Configuração de alinhamento para os componentes
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.gridwidth = 2;
        constraint.anchor = GridBagConstraints.CENTER;
        add(lblImagem, constraint);

        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.gridwidth = 2;
        constraint.anchor = GridBagConstraints.CENTER;
        add(lblImagemDark, constraint);
        lblImagemDark.setVisible(false);

        constraint.gridx = 0;
        constraint.gridy = 1;
        add(lblInfo1, constraint);

        constraint.gridy = 2;
        add(lblInfo, constraint);

        constraint.gridy = 3;
        add(lblNome, constraint);

        constraint.gridy = 4;
        add(txtNome, constraint);

        constraint.gridy = 5;
        add(lblSecao, constraint);

        constraint.gridy = 6;
        add(txtSecao, constraint);

        constraint.gridy = 7;
        add(btnCad, constraint);

        constraint.gridy = 8;
        add(lblAviso, constraint);
        lblAviso.setVisible(false);

        constraint.gridy = 9;
        add(btnDarkMode, constraint);

        constraint.gridy = 10;
        add(btnLightMode, constraint);
        btnLightMode.setVisible(false);

    }

    public void setCredenciais(String emailFunc, String senhaFunc) {
        this.emailFunc = emailFunc;
        this.senhaFunc = senhaFunc;
    }

    public void mensagemAviso(String texto, String codigo){

        lblAviso.setText(texto);
        lblAviso.setForeground(Color.decode(codigo));
        lblAviso.setVisible(true);

    }

    public Boolean cadastrarMaquina() {

        String nome = this.txtNome.getText();
        String secao = this.txtSecao.getText();

        if(nome.isEmpty() || secao.isEmpty()){

            mensagemAviso("Preencha todos os campos!","#FF0000" );

            return false;
        }else{

            mensagemAviso("Máquina cadastrada com sucesso!","#008000" );

            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    maquina.setNome(nome);
                    maquina.setSecao(secao);
                    maquinaDao.getFkEmpresa(maquina.getNome(), maquina.getSecao(), emailFunc, senhaFunc);

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            trocarTela("Tela Monitoramento"); // Move a troca de tela para cá
                        }
                    });
                }
            });

            timer.setRepeats(false);
            timer.start();


            return true;

        }

    }

    public void executarBotao(ActionEvent e) {

        if (e.getSource() == btnDarkMode) {
            trocarCores(e);
        }

        if (e.getSource() == btnLightMode){
            trocarCores2(e);
        }

        if (e.getSource() == btnCad) {
            if (this.cadastrarMaquina()) {

                novoSistema.iniciarMonitoramentoAposLogin(true);

            }
        }

    }
    public void trocarCores(ActionEvent e) {
        System.out.println("Método trocarCores foi chamado");

        System.out.println("Modo escuro ativado");

        lblImagem.setVisible(true);
        lblImagemDark.setVisible(false);

        // Defina aqui as novas cores para os componentes
        Color novaCorFundo = Color.decode("#202020");
        Color novaCorTexto = Color.WHITE;

        // Atualize as cores dos componentes
        setBackground(novaCorFundo);
        lblInfo1.setForeground(novaCorTexto);
        lblInfo.setForeground(novaCorTexto);
        lblNome.setForeground(novaCorTexto);
        lblSecao.setForeground(novaCorTexto);


        btnLightMode.setVisible(true);
        btnDarkMode.setVisible(false);
    }

    public void trocarCores2(ActionEvent e){
        System.out.println("Método trocarCores2 foi chamado");

        System.out.println("Modo claro ativado");

        lblImagem.setVisible(true);
        lblImagemDark.setVisible(false);

        Color novaCorFundo = Color.decode("#ffffff");
        Color novaCorTexto = Color.BLACK;

        setBackground(novaCorFundo);
        lblInfo1.setForeground(novaCorTexto);
        lblInfo.setForeground(novaCorTexto);
        lblNome.setForeground(novaCorTexto);
        lblSecao.setForeground(novaCorFundo);


        btnDarkMode.setVisible(true);
        btnLightMode.setVisible(false);


    }
}
