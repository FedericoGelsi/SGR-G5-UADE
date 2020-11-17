package vista;

import api.API_JSONHandler;
import api.Verificaciones;
import impl.JSONHandler;
import impl.Socio_Participe;
import impl.Socio_Protector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;

import static javax.swing.JOptionPane.showMessageDialog;

public class FrmSocios extends JDialog {
    private JPanel pnlPrincipal;
    private JPanel pnlTitulo;
    private JTabbedPane pnlTabPanel;
    private JPanel pnlDocumentacion;
    private JPanel pnlAccionistas;
    private JTextField CUITField;
    private JButton buscarButton;
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
    private JButton borrarCamposButton;
    private JButton borrarButton;
    private JComboBox CBESTADO;
    private JButton agregarDocumentacionButton;
    private JButton validarSocioButton;
    private JButton modificarDatosButton;
    private JButton confirmarDatosButton;
    private JButton borrarSocioButton;
    private JButton cancelarButton;
    private JPasswordField passwordField1;
    private JButton altaSocioParticipeButton;
    private JPanel pnluntitled;
    private FrmSocios self;
    private final String filename = "./src/resources/socios.json";
    private final API_JSONHandler file = new JSONHandler();
    private JSONObject jsonObject;
    private final Verificaciones verificar = new impl.Verificaciones();


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
        this.setSize(1000, 600);
        // No permite volver a la pantalla anterior hasta cerrar esta
        this.setModal(true);
        // Establezco el comportamiento al cerrar la pantalla
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Inicia la pantalla centrada
        this.setLocationRelativeTo(null);
        this.pnlTabPanel.setBackgroundAt(0, Color.red);
        this.pnlTabPanel.setBackgroundAt(1, Color.blue);
        this.events();
        confirmarDatosButton.setVisible(false);
        modificarDatosButton.setVisible(false);
        borrarSocioButton.setVisible(false);
        cancelarButton.setVisible(false);
        this.deshabilitarFields();


        borrarSocioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarDatosButton.setVisible(false);
                confirmarDatosButton.setVisible(false);
                //borrarSocio();
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    buscarSocioXCuit();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });
    }

    private void events() {

        borrarCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearABMFields();
            }
        });
        modificarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                habilitarFields();
                modificarDatosButton.setVisible(false);
                confirmarDatosButton.setVisible(true);
                cancelarButton.setVisible(true);
            }
        });


        confirmarDatosButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (borrarSocioButton.isVisible()) {
                    //modificarSocio()
                } else {
                    altaSocio();
                }
                showMessageDialog(null, "Nuevo Socio Creado");

                modificarDatosButton.setVisible(true);
                confirmarDatosButton.setVisible(false);
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


        validarSocioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cancelarButton.setVisible(false);
                try {
                    buscarSocioXCuit();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });


    }


    private void buscarSocio() throws Exception {

        jsonObject = (JSONObject) file.readJson(filename);
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

        //ABMCUITTextField.setText("");
        razonSocialTextField.setText("");
        actPrincipalTextField.setText("");
        fechaInicActTextField.setText("");
        direccionTextField.setText("");
        telefonoTextField.setText("");
        emailTextField.setText("");
        CBESTADO.setSelectedItem("-");
        CBTIPOEMP.setSelectedItem("-");
        CBTIPOSOCIO.setSelectedItem("-");
    }


 private void altaSocio () {
     LocalDate localDate;
     String tipo = CBTIPOSOCIO.getSelectedItem().toString();
     System.out.println("ALTA SOCIOS");
     System.out.println(tipo);



     if (tipo == "Socio Participe") {
         JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
         Socio_Participe newSocioParticipe = new impl.Socio_Participe(ABMCUITTextField.getText(), razonSocialTextField.getText(), LocalDate.now(), CBTIPOEMP.getSelectedItem().toString(), actPrincipalTextField.getText(), direccionTextField.getText(), emailTextField.getText());
         System.out.println("ALTA SOCIO PARTICIPE");
         socioList.add(newSocioParticipe.toJSON());
         jsonObject.put("socios-participes", socioList);

     } else {

         if (tipo == "Socio Protector") {

             JSONArray socioList = (JSONArray) jsonObject.get("socios-protectores");
             Socio_Protector newSocioProtector = new impl.Socio_Protector(ABMCUITTextField.getText(), razonSocialTextField.getText(), LocalDate.now(), CBTIPOEMP.getSelectedItem().toString(), actPrincipalTextField.getText(), direccionTextField.getText(), emailTextField.getText());
             System.out.println("ALTA SOCIO PROTECTOR");
             socioList.add(newSocioProtector.toJSON());
             jsonObject.put("socios-protectores", socioList);
         }

     }
     try {
         file.writeJson(filename, jsonObject);
     } catch (Exception e) {
         e.printStackTrace();
     }

 }

private boolean verificarCampos(){
    System.out.println("VERIFICAR CAMPOS");


    //if (!verificar.CUITValido(ABMCUITTextField.getText())) {
        //JOptionPane.showMessageDialog (null, "El CUIT ingresado es invalido");
    ///   System.out.println("CUIT INVALIDO");
    //}
return (true);

}


    //busqueda en el panel ABM
    private void buscarSocioXCuit() throws Exception {

        System.out.println("BUSCA CUIT SOCIO");
        String tipo = CBTIPOSOCIO.getSelectedItem().toString();
        confirmarDatosButton.setVisible(false);
        modificarDatosButton.setVisible(false);
        cancelarButton.setVisible(false);
        clearABMFields();


        if (tipo == "Socio Participe") {
            JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
            for (Object obj : socioList) {
                JSONObject socio = (JSONObject) obj;
                String rs = socio.get("razon-social").toString();
                String cuit = socio.get("cuit").toString();
                String estado = socio.get("estado").toString();
                String actPrincipal = socio.get("actividad-principal").toString();
                String direccion = socio.get("direccion").toString();
                String email = socio.get("email").toString();
                String tipoEmp = socio.get("tipo-empresa").toString();
                // String telefono = socio.get ("telefono").toString();
                LocalDate finic = (LocalDate) socio.get("finic-act");


                if (cuit.equals(ABMCUITTextField.getText())) {

                    System.out.printf("SOCIO PART. ENCONTRADO %s", cuit);
                    razonSocialTextField.setText(rs);
                    CBESTADO.setSelectedItem(estado);
                    fechaInicActTextField.setText(finic.toString());
                    actPrincipalTextField.setText(actPrincipal);
                    direccionTextField.setText(direccion);
                    emailTextField.setText(email);
                    CBTIPOEMP.setSelectedItem(tipoEmp);

                    //telefonoTextField.setText(telefono);
                    // modificarDatosButton.setVisible(true);
                    if (!borrarButton.isVisible()) showMessageDialog(null, "El Socio Participe YA EXISTE");
                    modificarDatosButton.setVisible(true);
                    borrarSocioButton.setVisible(true);
                    borrarCamposButton.setVisible(false);


                } else {
                    borrarSocioButton.setVisible(false);
                    showMessageDialog(null, "El Socio Participe NO EXISTE, puede crearlo");
                    this.habilitarFields();
                    borrarCamposButton.setVisible(true);
                    confirmarDatosButton.setVisible(true);
                }
            }

        } else if (tipo == "Socio Protector") {

            JSONArray socioList = (JSONArray) jsonObject.get("socios-protectores");
            for (Object obj : socioList) {
                JSONObject socio = (JSONObject) obj;
                String rs = socio.get("razon-social").toString();
                String cuit = socio.get("cuit").toString();
                String estado = socio.get("estado").toString();
                String actPrincipal = socio.get("actividad-principal").toString();
                String direccion = socio.get("direccion").toString();
                String email = socio.get("email").toString();
                String tipoEmp = socio.get("tipo-empresa").toString();
                LocalDate finic = (LocalDate) socio.get("finic-act");
                if (cuit.equals(ABMCUITTextField.getText())) {

                    if (!borrarButton.isVisible()) showMessageDialog(null, "El Socio Protector YA EXISTE");
                    razonSocialTextField.setText(rs);

                    CBESTADO.setSelectedItem(estado);

                    actPrincipalTextField.setText(actPrincipal);
                    direccionTextField.setText(direccion);
                    emailTextField.setText(email);
                    CBTIPOEMP.setSelectedItem(tipoEmp);
                    modificarDatosButton.setVisible(true);
                    borrarSocioButton.setVisible(true);
                    borrarCamposButton.setVisible(false);
                    fechaInicActTextField.setText(finic.toString());

                } else {
                    borrarSocioButton.setVisible(false);
                    borrarCamposButton.setVisible(true);
                    showMessageDialog(null, "El Socio Protector NO EXISTE, puede crearlo");
                    this.habilitarFields();
                    confirmarDatosButton.setVisible(true);

                }



            }

        }
    }

private void deshabilitarFields(){
    razonSocialTextField.setEditable(false);
    actPrincipalTextField.setEditable(false);
    fechaInicActTextField.setEditable(false);
    direccionTextField.setEditable(false);
    telefonoTextField.setEditable(false);
    emailTextField.setEditable(false);
    CBTIPOEMP.setEnabled(false);
    CBESTADO.setEnabled(false);
}
   private void habilitarFields() {
       CBESTADO.setEditable(true);
       razonSocialTextField.setEditable(true);
       actPrincipalTextField.setEditable(true);
       fechaInicActTextField.setEditable(true);
       direccionTextField.setEditable(true);
       telefonoTextField.setEditable(true);
       emailTextField.setEditable(true);
       CBTIPOEMP.setEnabled(true);
       CBESTADO.setEnabled(true);


   }


    private void borrarSocio() {


    }
}
