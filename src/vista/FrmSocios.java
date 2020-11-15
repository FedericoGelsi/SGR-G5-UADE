package vista;

import api.Verificaciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;


public class FrmSocios extends JDialog{
    private JPanel pnlPrincipal;
    private JPanel pnlTitulo;
    private JTabbedPane pnlTabPanel;
    private JPanel pnlDocumentacion;
    private JPanel pnlAccionistas;
    private JTextField ingreseElCUITConTextField;
    private JButton buscarAccionistaButton;
    private JButton crearAccionistaButton;
    private JButton eliminarAccionistaButton;
    private JTextField textFieldCuit;
    private JTextField textFieldRazon;
    private JSpinner spinnerParticipacion;
    private JPanel pnluntitled;
    private JButton cambiarColorButton;
    private Verificaciones verif = new impl.Verificaciones();

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
        buscarAccionistaButton.addActionListener(new ActionListener() {
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

                //Toma el Porcentaje de Participacion desde el Spinner
                Object participacion;
                participacion = spinnerParticipacion.getValue();
                int partint;
                partint= (Integer) participacion;
                if(partint <= 0){
                    showMessageDialog(null, "El porcentaje de Participacion debe ser menor o igual a 0");
                    datosCorrectosFlag = false;
                }
                if(partint >=100){
                    showMessageDialog(null,"El porcentaje de Participacion debe ser superior al 99%");
                    datosCorrectosFlag = false;
                }
                if (datosCorrectosFlag==true){

                }
            }
        });
        crearAccionistaButton.addActionListener(new ActionListener() {
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

                //Toma el Porcentaje de Participacion desde el Spinner
                Object participacion;
                participacion = spinnerParticipacion.getValue();
                int partint;
                partint= (Integer) participacion;
                if(partint <= 0){
                    showMessageDialog(null, "El porcentaje de Participacion debe ser menor o igual a 0");
                    datosCorrectosFlag = false;
                }
                if(partint >=100){
                    showMessageDialog(null,"El porcentaje de Participacion debe ser superior al 99%");
                    datosCorrectosFlag = false;
                }
                if (datosCorrectosFlag==true){

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

                //Toma el Porcentaje de Participacion desde el Spinner
                Object participacion;
                participacion = spinnerParticipacion.getValue();
                int partint;
                partint= (Integer) participacion;
                if(partint <= 0){
                    showMessageDialog(null, "El porcentaje de Participacion debe ser menor o igual a 0");
                    datosCorrectosFlag = false;
                }
                if(partint >=100){
                    showMessageDialog(null,"El porcentaje de Participacion debe ser superior al 99%");
                    datosCorrectosFlag = false;
                }
                if (datosCorrectosFlag==true){

                }
            }
        });
    }

    private void events(){

    }

}
