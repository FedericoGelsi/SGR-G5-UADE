package vista;

import com.formdev.flatlaf.FlatLightLaf;
import api.API_JSONHandler;
import api.Accionista;
import api.Deuda;
import api.Verificaciones;
import impl.JSONHandler;
import api.API_JSONHandler;
import api.Verificaciones;
import impl.JSONHandler;
import impl.Socio_Participe;
import impl.Socio_Protector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
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
    private JComboBox estadoTextField;
    private JTextField usuarioCargaTextField;
    private JCheckBox obligatorioCheckBox;
    private JButton confirmarButton;
    private JComboBox CBTIPODOC;
    private JButton adjDocButton;
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

    private String filename = "./src/resources/socios.json";
    private API_JSONHandler file = new JSONHandler();
    private JSONObject jsonObject = (JSONObject) file.readJson(filename);
    private JSONArray accionistasList;
    private DefaultTableModel modelos = new DefaultTableModel();
    private JSONObject socios;
    private JSONArray socioList;

    public FrmSocios(Window owner, String Title) throws Exception {
        super(owner, Title);

        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        // Define el canvas según swing.
        this.setContentPane(this.pnlPrincipal);
        // Tamaño de la pantalla
        this.setSize(1000, 600);
        // No permite volver a la pantalla anterior hasta cerrar esta
        this.setModal(true);
        this.setResizable(false);
        // Establezco el comportamiento al cerrar la pantalla
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Inicia la pantalla centrada
        this.setLocationRelativeTo(null);
        //this.pnlTabPanel.setBackgroundAt(0,Color.red);
        //this.pnlTabPanel.setBackgroundAt(1,Color.blue);

        this.events();
        }

    private void crearTabla(JSONArray accionistas) {
        String[] nombresColumnas = {"Cuit Accionista", "Razon Social", "Porcentaje Participacion"};
        DefaultTableModel modelo = new DefaultTableModel();
        for (String column : nombresColumnas) {
            modelo.addColumn(column);
        }
        for (Object accionistasJ : accionistas) {
            ArrayList data = new ArrayList<>();
            Accionista accionista = new impl.Accionista((JSONObject) accionistasJ);
            data.add(accionista.getCUITAccionista());
            data.add(accionista.getRazonsocial());
            data.add(accionista.getPorcParticipacion());
            modelo.addRow(data.toArray());
        }
        accionistasTabla= new JTable(modelo);
        PnlsAccionistas.setViewportView(accionistasTabla);
        PnlsAccionistas.setVisible(true);
    }

    private void buscarAccionista() throws Exception {
        Boolean flag= false;
        socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj: socioList){
            socios = (JSONObject) obj;
            String rs = socios.get("razon-social").toString();
            String cuit = socios.get("cuit").toString();
                if (cuit.equals(textFieldCuit.getText())){
                    flag= true;
                    estado=true;
                    textFieldRazon.setText(rs);
                    accionistasList= (JSONArray) socios.get("accionistas");
                    break;
                }

        }
        if (flag==false){
            socioList = (JSONArray) jsonObject.get("socios-protectores");
            for (Object obj: socioList){
                socios = (JSONObject) obj;
                String rs = socios.get("razon-social").toString();
                String cuit = socios.get("cuit").toString();
                if (cuit.equals(textFieldCuit.getText())){
                    flag=true;
                    estado=false;
                    textFieldRazon.setText(rs);
                    accionistasList= (JSONArray) socios.get("accionistas");
                }

            }
        }
        if (flag==false){
            showMessageDialog(null,"El socio no existe");
        }
    }

    private void eliminarAccionista(String cuit) throws Exception {
        for (Object ac:accionistasList){
            JSONObject accionis= (JSONObject) ac;
            if(accionis.get("cuit-accionista").equals(cuit)){
                accionistasList.remove(accionistasList.indexOf(ac));
                break;
            }
        }
        socios.put("accionistas",accionistasList);

        if (estado) {
            jsonObject.put("socios-participes", socioList);
        }
        else{
            jsonObject.put("socios-protectores", socioList);
        }
        file.writeJson(filename, jsonObject);
    }

    private void agregarAccionista(String cuit,String cuitac, String razon, Double porcentaje) throws Exception {
        Boolean flag= false;
        socioList = (JSONArray) jsonObject.get("socios-participes");
        total=.0;
        for (Object obj:socioList){
            socios = (JSONObject) obj;
            String cuitsoc = socios.get("cuit").toString();
            if (cuitsoc.equals(textFieldCuitEmpresa.getText())){
                flag=true;
                estado=true;
                accionistasList = (JSONArray) socios.get("accionistas");
                for (Object obje: accionistasList){
                    JSONObject accionis= (JSONObject) obje;
                    String cuitAC= (String) accionis.get("cuit-accionista");
                    if (cuitAC.equals(textFieldCuitAccion.getText())){
                        showMessageDialog(null,"El Accionista ya existe en Socios Participes");
                        break;
                    }
                    else {
                        if (!accionistasList.isEmpty()){
                            for (Object por: accionistasList){
                                accionis= (JSONObject) por;
                                Double porcen= (Double) accionis.get("porcentaje-participacion");
                                total=total+porcen;}
                            if (porcentaje == 0){
                                showMessageDialog(null,"El Porcentaje de Participacion debe ser mayor a 0");
                                break;
                            }else if(total+porcentaje>100){
                                showMessageDialog(null,"El Porcentaje de Participacion debe ser menor o igual que: "+(100-total));
                                break;
                            }
                        }
                        Accionista accionistagregar= new impl.Accionista(cuitac,razon,porcentaje);
                        accionistasList.add(accionistagregar.toJSON());
                        socios.put("accionistas",accionistasList);
                        jsonObject.put("socios-participes",socioList);
                        file.writeJson(filename,jsonObject);
                        showMessageDialog(null,"Se agrego el Accionista en Socios Participes con exito");
                        break;
                    }
                }
            }
        }
        if (!flag){
            socioList = (JSONArray) jsonObject.get("socios-protectores");
            for (Object obj:socioList){
                socios = (JSONObject) obj;
                String cuitsoc = socios.get("cuit").toString();
                if (cuitsoc.equals(textFieldCuitEmpresa.getText())){
                    flag = true;
                    estado=false;
                    accionistasList = (JSONArray) socios.get("accionistas");
                    for (Object obje: accionistasList){
                        JSONObject accionis= (JSONObject) obje;
                        String cuitAC= (String) accionis.get("cuit-accionista");
                        if (cuitAC.equals(textFieldCuitAccion.getText())){
                            showMessageDialog(null,"El Accionista ya existe en Socios Protectores");
                            break;
                        }
                        else {
                            if (!accionistasList.isEmpty()){
                                for (Object por: accionistasList){
                                    accionis= (JSONObject) por;
                                    Double porcen= (Double) accionis.get("porcentaje-participacion");
                                    total=total+porcen;}
                                if (porcentaje == 0){
                                    showMessageDialog(null,"El Porcentaje de Participacion debe ser mayor a 0");
                                    break;
                                }else if(total+porcentaje>100){
                                    showMessageDialog(null,"El Porcentaje de Participacion debe ser menor o igual que: "+(100-total));
                                    break;
                                }

                            }
                            Accionista accionistagregar= new impl.Accionista(cuitac,razon,porcentaje);
                            accionistasList.add(accionistagregar.toJSON());
                            socios.put("accionistas",accionistasList);
                            jsonObject.put("socios-protectores",socioList);
                            file.writeJson(filename,jsonObject);
                            showMessageDialog(null,"Se agrego el Accionista en Socios Protectores con exito");
                            break;
                        }
                    }
                }
            }
        }
        if (!flag){
            showMessageDialog(null,"El socio no existe");
        }
    }
    
    private void events(){
        this.pnlTabPanel.setBackgroundAt(0, Color.red);
        this.pnlTabPanel.setBackgroundAt(1, Color.blue);
        this.events();
        confirmarDatosButton.setVisible(false);
        modificarDatosButton.setVisible(false);
        borrarSocioButton.setVisible(false);
        cancelarButton.setVisible(false);
        this.deshabilitarFields();
        
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
        adjDocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adjuntarFile();

            }
        });

        buscarAccionistaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean datosCorrectosFlag = true;

                //Checkea el CUIT
                String textCuit="";
                textCuit=textFieldCuit.getText();
                if (verif.CUITValido(textCuit)){
                }
                else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    datosCorrectosFlag = false;
                }
                if (datosCorrectosFlag==true) {
                    try {
                        buscarAccionista();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    crearTabla(accionistasList);
                }
            }
        });

        eliminarAccionistaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean datosCorrectosFlag = true;

                //Toma Razon Social y checkea si esta vacio
                String textRazon="";
                textRazon= textFieldRazon.getText();
                if (textRazon.isEmpty()){
                    showMessageDialog(null, "El campo Razon Social es mandatorio, por favor ingrese el dato solicitado");
                    datosCorrectosFlag = false;
                }

                //Checkea el CUIT
                String textCuit="";
                textCuit=textFieldCuit.getText();
                if (verif.CUITValido(textCuit)){
                }
                else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    datosCorrectosFlag = false;
                }
                if(datosCorrectosFlag==true) {
                    modelos = (DefaultTableModel) accionistasTabla.getModel();

                    if (accionistasTabla.getSelectedRow() != -1) {
                        // Elimina la fila seleccionada
                        String cuit = accionistasTabla.getValueAt(accionistasTabla.getSelectedRow(), 0).toString();
                        try {
                            eliminarAccionista(cuit);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        modelos.removeRow(accionistasTabla.getSelectedRow());
                        JOptionPane.showMessageDialog(null, "Se elimino la fila correctamente");
                        PnlsAccionistas.setViewportView(accionistasTabla);
                    }
                }
            }
        });
        crearAccionistaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean datosCorrectosFlag = true;

                //Toma Razon Social y checkea si esta vacio
                String textRazon;
                textRazon = textFieldRazonS.getText();
                if (textRazon.isEmpty()) {
                    showMessageDialog(null, "El campo Razon Social es mandatorio, por favor ingrese el dato solicitado");
                    datosCorrectosFlag = false;
                }

                //Checkea el CUIT
                String textCuitEmp;
                textCuitEmp = textFieldCuitEmpresa.getText();
                if (verif.CUITValido(textCuitEmp)) {
                } else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    datosCorrectosFlag = false;
                }
                //Checkea el CUIT
                String textCuitac;
                textCuitac = textFieldCuitAccion.getText();
                if (verif.CUITValido(textCuitac)) {
                } else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    datosCorrectosFlag = false;
                }

                //Toma el Porcentaje de Participacion desde el Spinner
                Object spinParticipacion;
                spinParticipacion = spinnerParticipacion.getValue();
                int partint;
                partint = (Integer) spinParticipacion;
                if (partint <= 0) {
                    showMessageDialog(null, "El porcentaje de Participacion debe ser mayor a 0");
                    datosCorrectosFlag = false;
                }
                if (partint >= 99) {
                    showMessageDialog(null, "El porcentaje de Participacion no debe superar el 99%");
                    datosCorrectosFlag = false;
                }
                if (datosCorrectosFlag == true) {
                    //Agregar
                    Double participac = Double.parseDouble(spinParticipacion.toString());
                    try {
                        agregarAccionista(textCuitEmp, textCuitac, textRazon, participac);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
            }
        });
    }

    private void events() {

        


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

    private void adjuntarFile() {


        JFileChooser fileChooser = new JFileChooser();
        int selection = fileChooser.showOpenDialog(pnlPrincipal);

        if (selection == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            String path = archivo.getAbsolutePath();
            try {
                copyFile(path, CUITField.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }

    public static void copyFile(String from, String to) throws IOException {
        Path origen = Paths.get(from);
        Path destino = Paths.get("./src/resources/documentacion/" + to + "/test");
        if (origen.toString().endsWith(".pdf") || origen.toString().endsWith(".jpeg") || origen.toString().endsWith(".png")) {
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
        } else {
            //
        }

    }


}

