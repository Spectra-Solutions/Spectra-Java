package JarIndividual.grafico;

import javax.swing.*;
import java.awt.*;

public abstract class MaquinaGui extends JFrame {

    // Paineis
    protected JPanel pnlForm;
    protected JPanel pnlBtn;

    // Botoes
    protected JButton btnCadastrar;
    protected JButton btnLimpar;

    // Titulos
    protected JLabel tltMaquina;

    // Campos de Login
    // Email
    protected JLabel lblNomeMaq;
    protected JTextField txtNomeMaq;

    // Senha
    protected JLabel lblSecao;
    protected JTextField txtSecao;

    // Imagem
    ImageIcon img;

    // Construtor
    public MaquinaGui(){
        this.inicializar();
    }

    // Metodos
    private void inicializar(){

        this.setTitle("Cadastro Máquina Spectra");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.setResizable(false);

        // Atribuindo paineis
        this.getContentPane().add(getPnlForm(), BorderLayout.CENTER);
        this.getContentPane().add(getPnlBtn(), BorderLayout.PAGE_END);
        this.pack();
    }

    // GetPaineis

    public JPanel getPnlForm() {

        if(pnlForm == null){

            pnlForm = new JPanel();
            pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.PAGE_AXIS));
            pnlForm.setBackground(Color.WHITE);

            pnlForm.setBorder(BorderFactory.createEmptyBorder(100,80,100,80));

            // definindo campos
            tltMaquina = new JLabel("Cadastrar Máquina");

            lblNomeMaq = new JLabel("Email");
            txtNomeMaq = new JTextField();

            lblSecao = new JLabel("Senha");
            txtSecao = new JPasswordField(15);

            // Imagem
            img = new ImageIcon("imagens/LOGO_SPECTRA3.png");
            JLabel label = new JLabel( img, JLabel.CENTER);
            label.setSize(400,300);
            pnlForm.add(label);

            // Atribuindo campos ao painel
            pnlForm.add(tltMaquina);
            pnlForm.add(lblNomeMaq);
            pnlForm.add(txtNomeMaq);
            pnlForm.add(lblSecao);
            pnlForm.add(txtSecao);

            // Edições
            // Fonte - txt
            Font fontTxt = new Font("Poppins", Font.PLAIN, 12);
            txtNomeMaq.setFont(fontTxt);
            txtSecao.setFont(fontTxt);

            // Fonte - lbl
            Font fontLbl = new Font("Poppins", Font.BOLD, 12);
            lblNomeMaq.setFont(fontLbl);
            lblSecao.setFont(fontLbl);
            lblNomeMaq.setForeground(Color.darkGray);
            lblSecao.setForeground(Color.darkGray);

            // Fonte - titulo
            Font fontTlt = new Font("Poppins", Font.BOLD, 18);
            tltMaquina.setFont(fontTlt);

            //pnlForm.add(Box.createVerticalStrut(20));


        }

        return pnlForm;
    }

    public JPanel getPnlBtn() {

        if(pnlBtn == null){

            pnlBtn = new JPanel();
            pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
            pnlBtn.setBackground(Color.WHITE);

            btnCadastrar = new JButton("Login");
            btnLimpar = new JButton("Limpar");

            pnlBtn.add(btnCadastrar);
            pnlBtn.add(btnLimpar);

            // Edições
            Font font = new Font("Poppins", Font.BOLD, 12);
            btnCadastrar.setBackground(Color.blue);
            btnLimpar.setBackground(Color.blue);
            btnCadastrar.setFont(font);
            btnLimpar.setFont(font);
            btnCadastrar.setForeground(Color.white);
            btnLimpar.setForeground(Color.white);
        }

        return pnlBtn;
    }

}


