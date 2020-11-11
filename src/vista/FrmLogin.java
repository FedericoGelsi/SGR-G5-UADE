package vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmLogin extends JFrame{
    private JPanel pnlPrincipal;
    private JPanel pnlTitulo;
    private JLabel iconLbl;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JPanel pnlLogin;
    private JTextField userField;
    private JLabel errorMsg;

    private String user = "admin";
    private String password = "admin";

    private FrmLogin self;

    public FrmLogin(String Title){
        super(Title);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Define el canvas según swing.
        this.setContentPane(this.pnlPrincipal);
        // Tamaño de la pantalla
        this.setSize(1000,600);
        // Evita que se modifique el tamaño de la pantalla
        this.setResizable(false);
        // Establezco el comportamiento al cerrar la pantalla
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Inicia la pantalla centrada
        this.setLocationRelativeTo(null);

        this.asociarEventos();
        this.self = this;

    }

    private boolean auth(){
        String pass = "";
        pass = pass.valueOf(this.passwordField1.getPassword());

        System.out.println("User: " + this.userField.getText());
        System.out.println("Pass: " + pass);
        if (!this.user.equals(this.userField.getText()) || !this.password.equals(pass)){
            this.errorMsg.setVisible(true);

        }else if(this.user.equals(this.userField.getText()) && this.password.equals(pass) ){
            this.errorMsg.setVisible(false);
            return true;
        }
        return false;
    }

    public void asociarEventos() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (auth() == true) {
                    FrmPrincipal frame = new FrmPrincipal("Sistema de Gestión de Sociedades de Garantías Recíprocas", self.userField.getText());
                    frame.setVisible(true);
                    self.dispose();
                }
            }
        });
    }
    public static void main(String[] args) {
        FrmLogin login = new FrmLogin("Sistema de Gestión de Sociedades de Garantías Recíprocas");
        login.setVisible(true);
    }

}
