package SpectraSprint02.grafico;

import SpectraSprint02.DTO.Funcionario;
import SpectraSprint02.VIEW.IniciarSistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGui extends TelaPanel {

    IniciarSistema novoSistema;

    String email;
    String senha;

    // Botoes
    private JButton btnLogin;

    // Campos de Login
    // Email
    private JLabel lblEmail;
    private JTextField txtEmail;

    // Senha
    private JLabel lblSenha;
    private JTextField txtSenha;

    // Texto
    private JLabel lblInfo;

    // Imagem
    private JLabel lblImagem;
    ImageIcon img;

    // campos de validação
    private JLabel lblAviso;

    public LoginGui(JPanel telas, JFrame janela, IniciarSistema novoSistema ) {

        super(telas, janela);
        this.novoSistema = novoSistema;

        // Painel
        setLayout(new GridBagLayout());
        setBackground(Color.decode("#ffffff"));

        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets = new Insets(5, 5, 5, 5);
        setBackground(Color.decode("#ffffff"));

        // Campos de texto
        txtEmail = new JTextField();
        txtSenha = new JTextField();

        // Edição Senha
        txtSenha = new JPasswordField();

        // Ajuste tamanho
        Dimension maxDimTxt = new Dimension(300, 30);
        txtEmail.setPreferredSize(maxDimTxt);
        txtSenha.setPreferredSize(maxDimTxt);

        // Campos de label
        lblEmail = new JLabel("Email");
        lblSenha = new JLabel("Senha");
        lblInfo = new JLabel("Informe seu dados para iniciar o monitoramento");
        lblAviso = new JLabel("");

        // Edição fonte
        Font fontTxt = new Font("Poppins", Font.PLAIN, 12);
        Font fontLbl = new Font("Poppins", Font.BOLD, 15);
        lblEmail.setFont(fontLbl);
        lblEmail.setForeground(Color.decode("#052767"));
        lblSenha.setFont(fontLbl);
        lblSenha.setForeground(Color.decode("#052767"));
        lblInfo.setFont(fontLbl);
        lblInfo.setForeground(Color.decode("#202020"));
        txtEmail.setFont(fontTxt);
        txtSenha.setFont(fontTxt);
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

        // Botao
        btnLogin = new JButton("Logar");
        // Tamanho
        Dimension maxDimBtn = new Dimension(300, 40);
        btnLogin.setPreferredSize(maxDimBtn);
        // Edicao
        Font fontBtn = new Font("Poppins", Font.BOLD, 13);
        btnLogin.setBackground(Color.decode("#052767"));
        btnLogin.setFont(fontBtn);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.addActionListener(this);

        // Adicionando componentes
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.gridwidth = 2;
        constraint.anchor = GridBagConstraints.CENTER;
        add(lblImagem, constraint);

        constraint.gridx = 0;
        constraint.gridy = 1;
        add(lblInfo, constraint);

        constraint.gridy = 2;
        add(lblEmail, constraint);

        constraint.gridy = 3;
        add(txtEmail, constraint);

        constraint.gridy = 4;
        add(lblSenha, constraint);

        constraint.gridy = 5;
        add(txtSenha, constraint);

        constraint.gridy = 6;
        add(btnLogin, constraint);

        constraint.gridy = 7;
        add(lblAviso, constraint);
        lblAviso.setVisible(false);

    }

    public String logar() {

        email = txtEmail.getText();
        senha = txtSenha.getText();

       return novoSistema.validarLogin(email, senha);
    }

    public void mensagemAviso(String texto, String codigo){

        lblAviso.setText(texto);
        lblAviso.setForeground(Color.decode(codigo));
        lblAviso.setVisible(true);

    }


    public void executarBotao(ActionEvent e) {

        this.novoSistema = novoSistema;

        if (logar().equals("logado")) {

            mensagemAviso("Usuário Logado com sucesso!","#008000" );

            Timer timer = new Timer(2000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                txtSenha.setText("");
                txtEmail.setText("");
                lblAviso.setText("");

                Boolean maqcadastrada = novoSistema.validarMaquina();

                if(maqcadastrada){

                    novoSistema.iniciarMonitoramentoAposLogin(true);

                    TelaPanel.trocarTela("Tela Monitoramento");

                }else{

                    Eventos eventos = (Eventos) SwingUtilities.getWindowAncestor(LoginGui.this);

                    eventos.abrirTelaCadastroMaquina(email, senha);

                }

                }

            });

            timer.setRepeats(false);
            timer.start();

        } else if(logar().equals("vazio")) {

            mensagemAviso("Preencha todos os campos!","#FF0000" );

        }else if(logar().equals("desconhecido")) {

            mensagemAviso("Usuário não cadastrado!","#FF0000" );

        }else if(logar().equals("erro")) {

            mensagemAviso("Certifique-se que o Email possua '@' e '.', além de 8 ou mais caracteres na senha","#FF0000" );

        }

    }
}

