package vista;

import api.API_JSONHandler;
import api.API_SHA256;
import com.formdev.flatlaf.FlatLightLaf;
import impl.JSONHandler;
import impl.SHA256;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class FrmLogin extends JFrame{
    private JPanel pnlPrincipal;
    private JPanel pnlTitulo;
    private JLabel iconLbl;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JPanel pnlLogin;
    private JTextField userField;
    private JLabel errorMsg;
    private JButton RegisBtn;
    private JLabel RegisLbl;


    private FrmLogin self;

    public FrmLogin(String Title){
        super(Title);

        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
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

    private boolean auth() {
        API_JSONHandler file = new JSONHandler();
        try{
            JSONObject jsonObject = (JSONObject) file.readJson("./src/resources/usuarios.json");
            JSONArray usersList = (JSONArray) jsonObject.get("users");
            API_SHA256 hash = new SHA256();
            String pass = new String(this.passwordField1.getPassword());

            Iterator<JSONObject> iterator = usersList.iterator();
            while (iterator.hasNext()) {
                JSONObject user_data = iterator.next();
                if(user_data.get("username").equals(hash.getSHA_Str(this.userField.getText())) && user_data.get("password").equals(hash.getSHA_Str(pass)) ){
                    this.errorMsg.setVisible(false);
                    return true;
                }else if (user_data.get("password").equals(hash.getSHA_Str(pass)) && !user_data.get("username").equals(hash.getSHA_Str(this.userField.getText()))){
                    this.errorMsg.setText("Usuario incorrecto");
                    this.errorMsg.setVisible(true);
                }else if (user_data.get("username").equals(hash.getSHA_Str(this.userField.getText())) && !user_data.get("password").equals(hash.getSHA_Str(pass))){
                    this.errorMsg.setText("Contraseña incorrecta");
                    this.errorMsg.setVisible(true);
                }else{
                    this.errorMsg.setText("El usuario es inexistente");
                    this.errorMsg.setVisible(true);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void asociarEventos() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (auth()) {
                    FrmPrincipal frame = new FrmPrincipal("Sistema de Gestión de Sociedades de Garantías Recíprocas", self.userField.getText());
                    frame.setVisible(true);
                    self.dispose();
                }
            }
        });
        RegisBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (self.RegisBtn.getText().equals("Registrarse")) {
                    self.loginButton.setVisible(false);
                    self.errorMsg.setVisible(false);
                    self.RegisBtn.setText("Confirmar");
                    self.RegisLbl.setVisible(true);
                }else{
                    self.loginButton.setVisible(true);
                    self.RegisBtn.setText("Registrarse");
                    self.RegisLbl.setVisible(false);

                    API_JSONHandler file = new JSONHandler();
                    //Write JSON file

                    JSONArray usersList = new JSONArray();

                    try {
                        JSONObject jsonObject = (JSONObject) file.readJson("./src/resources/usuarios.json");
                        usersList = (JSONArray) jsonObject.get("users");
                    } catch (FileNotFoundException err){
                        System.out.println("El archivo no existe, creando uno nuevo...");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
                    try {
                        API_SHA256 hash = new SHA256();
                        JSONObject user_data = new JSONObject();
                        JSONObject users = new JSONObject();
                        try {
                            user_data.put("username", hash.getSHA_Str(self.userField.getText()));
                            user_data.put("password", hash.getSHA_Str(new String(self.passwordField1.getPassword())));

                            user_data.put("create-dat", LocalDate.now().format(formatter));
                        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                            noSuchAlgorithmException.printStackTrace();
                        }
                        usersList.add(user_data);
                        users.put("last-modified", LocalDate.now().format(formatter));
                        users.put("users", usersList);
                        file.writeJson("./src/resources/usuarios.json", users);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }


                }
            }
        });
    }


    public static void main(String[] args) {
        FrmLogin login = new FrmLogin("Sistema de Gestión de Sociedades de Garantías Recíprocas");
        login.setVisible(true);
    }

}
