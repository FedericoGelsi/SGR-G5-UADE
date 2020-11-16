package vista;

import api.API_JSONHandler;
import api.Verificaciones;
import impl.JSONHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class FrmSocios extends JDialog{
    private JPanel pnlPrincipal;
    private JPanel pnlTitulo;
    private JTabbedPane pnlTabPanel;
    private JPanel pnlDocumentacion;
    private JPanel pnlAccionistas;
    private JTextField CUITField;
    private JButton buscarButton;
    private JButton borrarButton;
    private JTextField rznSocialField;
    private JLabel rznSocialLabel;
    private JComboBox CBTIPOSOCIO;
    private JComboBox CBTIPOEMP;
    private JTextField ABMCUITTextField;
    private JTextField razonSocialTextField;
    private JTextField fechaInicActTextField;
    private JTextField actPrincipalTextField;
    private JTextField direccionTextField;
    private JTextField telefonoTextField;
    private JTextField emailTextField;
    private JButton confirmarButton;
    private JButton borrarCamposButton;
    private JPasswordField passwordField1;
    private JButton altaSocioParticipeButton;
    private JPanel pnluntitled;
    private JButton cambiarColorButton;
    private FrmSocios self;
    private String filename = "./src/resources/socios.json";
    private API_JSONHandler file = new JSONHandler();
    private JSONObject jsonObject;
    private Verificaciones verificar = new impl.Verificaciones();


    {
        try {
            jsonObject = (JSONObject) file.readJson(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FrmSocios(Window owner, String Title){
        super(owner, Title);
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
        // No permite volver a la pantalla anterior hasta cerrar esta
        this.setModal(true);
        // Establezco el comportamiento al cerrar la pantalla
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Inicia la pantalla centrada
        this.setLocationRelativeTo(null);
        this.pnlTabPanel.setBackgroundAt(0,Color.red);
        this.pnlTabPanel.setBackgroundAt(1,Color.blue);

        this.events();



    }

    private void events(){


        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CONFIRMAR NUEVO SOCIO");

               altaSocio();

            }
        });

        borrarCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("BORRAR CAMPOS SOCIO");
                clearABMFields();
            }
        });


        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Buscar");

                try {
                    buscarSocio();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                CUITField.requestFocus();
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            clearFields();
            }
        });
    }


    private void buscarSocio() throws Exception {
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj: socioList){
            JSONObject socio = (JSONObject) obj;
            String rs = socio.get("razon-social").toString();
            String cuit = socio.get("cuit").toString();
            String estado = socio.get("estado").toString();

            if (cuit.equals(CUITField.getText())){

            System.out.printf("SOCIO ENCONTRADO %s", cuit);
            rznSocialField.setText(rs);

        } else
                System.out.printf("SOCIO NO ENCONTRADO", cuit);
    }

}

 private void clearFields () {

     rznSocialField.setText("");
     CUITField.setText("");
     CUITField.requestFocus();
 }

    private void clearABMFields () {

        ABMCUITTextField.setText("");
        razonSocialTextField.setText("");
        actPrincipalTextField.setText("");
        fechaInicActTextField.setText("");
        direccionTextField.setText("");
        telefonoTextField.setText("");
        emailTextField.setText("");
    }

 private void altaSocio () {

    String tipo = CBTIPOSOCIO.getSelectedItem().toString();
     System.out.println("ALTA SOCIOS");
     System.out.println(tipo);

     if (tipo == "Socio Participe") {
         JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
         //altaSocioParticipeButton.setVisible(false);
         System.out.println("ALTA SOCIO PARTICIPE");


     } else {

         if (tipo == "Socio Protector") {
             JSONArray socioList = (JSONArray) jsonObject.get("socios-protectores");
             //altaSocioParticipeButton.setVisible(false);
             System.out.println("ALTA SOCIO PROTECTOR");
         }
            }
    }

private void verificarCampos(){
    System.out.println("VERIFICAR CAMPOS");
    if (!verificar.CUITValido(ABMCUITTextField.getText())) {
        ABMCUITTextField.setBackground(Color.red);
    }


}


}
