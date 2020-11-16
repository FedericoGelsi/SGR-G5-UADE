package vista;

import api.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.IntelliJTheme;
import impl.JSONHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class FrmConsultasGenerales extends JDialog {
    private JPanel pnlPrincipal;
    private JPanel pnlTitulo;
    private JTabbedPane ConsultasTPane;
    private JPanel pnlTabC1;
    private JPanel pnlTabC2;
    private JPanel pnlTabC3;
    private JPanel pnlTabC4;
    private JPanel pnlTabC5;
    private JPanel pnlTabC6;
    private JTextField FechaTxf;
    private JComboBox TipocomboBox;
    private JTextField TotalComisionesTxf;
    private JButton calcularBtn;
    private JTextField FechaIniTxf;
    private JTextField FechaFinTxf;
    private JTextField RSTxf;
    private JButton buscarOPBtn;
    private JScrollPane pnlSOperacionesMTable;
    private JTextField CUITTxf;
    private JButton buscarSocioBtn;
    private JScrollPane pnlSMoraTable;
    private JTextField moradiariaTxf;
    private JTextField saldomoraTxf;
    private JTextField RSMTxf;
    private JTextField CUITMTxf;
    private JButton BuscarMBtn;
    private JTabbedPane tabbedPane1;
    private JScrollPane pnlSRecuperos;
    private JScrollPane pnlSDeudas;
    private JScrollPane pnlSContragarantias;
    private JScrollPane pnlSAportes;
    private JScrollPane pnlSLDC;
    private JScrollPane pnlSAccionistas;
    private JScrollPane pnlSOP;
    private JButton BuscarSCBtn;
    private JTextField CUITCCTxf;
    private JTextField RSCCTxf;
    private JTextField RVCCTxf;
    private JTextField TUCCTxf;
    private JTextField CUITDSTxf;
    private JTextField FIADSTxf;
    private JTextField DDSTxf;
    private JTextField EDSTxf;
    private JTextField RSDSTxf;
    private JTextField TEDSTxf;
    private JTextField EmailDSTxf;
    private JTextField FPDSTxf;
    private JTextField APDSTxf;

    private FrmConsultasGenerales self;
    private Verificaciones verificar = new impl.Verificaciones();

    private API_JSONHandler file = new JSONHandler();
    private String filename = "./src/resources/socios.json";
    private JSONObject jsonObject = (JSONObject) file.readJson(filename);
    private JSONArray sociosList;
    private JSONObject socioenuso;
    private JSONArray operacionesSocio;


    public FrmConsultasGenerales(Window owner, String Title) throws Exception {
        super(owner, Title);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());

        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        /*
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

         */

        // Define el canvas según swing.
        this.setContentPane(this.pnlPrincipal);
        // Tamaño de la pantalla
        this.setSize(1000, 600);
        this.setResizable(false);
        // No permite volver a la pantalla anterior hasta cerrar esta
        this.setModal(true);
        // Establezco el comportamiento al cerrar la pantalla
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Inicia la pantalla centrada
        this.setLocationRelativeTo(null);
        this.asociareventos();
        this.self = this;
    }

    private void asociareventos(){
        BuscarSCBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscarSocioCC();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void buscarSocioCC() throws Exception {
        sociosList = (JSONArray) jsonObject.get("socios-participes");
        boolean socioEncontrado = false;
        for (Object obj: sociosList){
            socioenuso = (JSONObject) obj;
            String rs = socioenuso.get("razon-social").toString();
            String cuit = socioenuso.get("cuit").toString();
            String estado = socioenuso.get("estado").toString();
            if (verificar.CUITValido(CUITCCTxf.getText())) {
                if (cuit.equals(CUITCCTxf.getText()) && estado.equals("Pleno")) {
                    RSCCTxf.setText(rs);
                    calcularRiesgoVivo();
                    //getoperacionesSocio(cuit);
                    datossocio();
                    crearTablas();
                    socioEncontrado = true;
                    break;
                }
            }else if (CUITCCTxf.getText().isEmpty()){
                showMessageDialog(null, "El campo no puede estar vacío.\nIngrese un CUIT.");
                socioEncontrado = true;
            }else{
                showMessageDialog(null, "CUIT inválido. El mismo debe tener el formato ##-########-#.\nIngreselo nuevamente.");
            }
        }
        if (!socioEncontrado){
            showMessageDialog(null, "El socio no se encuentra en la base de datos o no es Socio Partícipe.\nVerifique los datos e ingreselos nuevamente.");
        }
    }

    private void datossocio(){
        CUITDSTxf.setText((String) socioenuso.get("cuit"));
        RSDSTxf.setText((String) socioenuso.get("razon-social"));
        FIADSTxf.setText((String) socioenuso.get("finic-act"));
        TEDSTxf.setText((String) socioenuso.get("tipo-empresa"));
        DDSTxf.setText((String) socioenuso.get("direccion"));
        EmailDSTxf.setText((String) socioenuso.get("email"));
        EDSTxf.setText((String) socioenuso.get("estado"));
        FPDSTxf.setText((String) socioenuso.get("fecha-pleno"));
        APDSTxf.setText((String) socioenuso.get("actividad-principal"));

    }

    private void getoperacionesSocio(String cuit) throws Exception {
        filename = "./src/resources/operacionescontroller.json";
        jsonObject = (JSONObject) file.readJson(filename);
        JSONArray operaciones = (JSONArray) jsonObject.get("operaciones");
        for( Object obj: operaciones){
            JSONObject op = (JSONObject) obj;
            if (op.get("cuit-solicitante").equals(cuit)){
                operacionesSocio.add(op);
            }
        }
    }

    private void calcularRiesgoVivo(){

    }

    public void crearTablas(){
        crearTablaRecuperos();
        crearTablaDeudas();
        crearTablaContragarantias();
        crearTablaLDC();
        crearTablaAccionistas();
        //crearTablaOperaciones();
    }

    private void crearTablaRecuperos(){
        String [] nombresColumnas = {"CUIT","Tipo", "Monto", "Deudas"};
        DefaultTableModel modelo = new DefaultTableModel();
        for ( String column : nombresColumnas) {
            modelo.addColumn(column);
        }
        for ( Object recupero: (JSONArray) socioenuso.get("recuperos")){
            ArrayList data = new ArrayList<>();
            Recupero recu = new impl.Recupero((JSONObject) recupero);
            data.add(recu.getIdSocioPleno());
            data.add(recu.getTipo());
            data.add(recu.getMonto());
            data.add(recu.getIdDeuda());
            modelo.addRow(data.toArray());
        }
        JTable recuperosTable= new JTable(modelo);

        pnlSRecuperos.setViewportView(recuperosTable);
        pnlSRecuperos.setVisible(true);
    }

    private void crearTablaDeudas(){
        String [] nombresColumnas = {"ID","Monto","Fecha Ven","Mora","Monto Mora","Subtotal"};
        DefaultTableModel modelo = new DefaultTableModel();
        for ( String column : nombresColumnas) {
            modelo.addColumn(column);
        }
        for ( Object deudaJ: (JSONArray) socioenuso.get("deudas")){
            ArrayList data = new ArrayList<>();
            Deuda deuda = new impl.Deuda((JSONObject) deudaJ);
            data.add(deuda.getIdDeuda());
            data.add(deuda.getMonto());
            data.add(deuda.getFechaDeuda().toString());
            data.add(deuda.isAplicaMora());
            data.add(deuda.getMontoMora());
            data.add(deuda.calcularSubtotal());
            modelo.addRow(data.toArray());
        }
        JTable deudasTable = new JTable(modelo);

        pnlSDeudas.setViewportView(deudasTable);
        pnlSDeudas.setVisible(true);
    }

    private void crearTablaAccionistas(){
        String [] nombresColumnas = {"Cuit","Razón Social","Porcentaje Participación"};
        DefaultTableModel modelo = new DefaultTableModel();
        for ( String column : nombresColumnas) {
            modelo.addColumn(column);
        }
        for ( Object ac: (JSONArray) socioenuso.get("accionistas")){
            ArrayList data = new ArrayList<>();
            JSONObject accionista = (JSONObject) ac;
            data.add(accionista.get("cuit-accionista"));
            data.add(accionista.get("razon-social-ac"));
            data.add(accionista.get("porcentaje-participacion"));
            modelo.addRow(data.toArray());
        }
        JTable accionistasTable = new JTable(modelo);

        pnlSAccionistas.setViewportView(accionistasTable);
        pnlSAccionistas.setVisible(true);
    }

    private void crearTablaContragarantias(){
        String [] nombresColumnas = {"tipo","Monto","Fecha Vigencia"};
        DefaultTableModel modelo = new DefaultTableModel();
        for ( String column : nombresColumnas) {
            modelo.addColumn(column);
        }
        for ( Object cg: (JSONArray) socioenuso.get("contragarantias")){
            ArrayList data = new ArrayList<>();
            JSONObject contragarantia = (JSONObject) cg;
            data.add(contragarantia.get("tipo"));
            data.add(contragarantia.get("monto"));
            data.add(contragarantia.get("fecha-vigencia"));
            modelo.addRow(data.toArray());
        }
        JTable contragrantiasTable = new JTable(modelo);

        pnlSContragarantias.setViewportView(contragrantiasTable);
        pnlSContragarantias.setVisible(true);
    }

    private void crearTablaLDC(){
        String [] nombresColumnas = {"ID","CUIT","Tope", "Monto Utilizado", "Fecha Vigencia"};
        DefaultTableModel modelo = new DefaultTableModel();
        for ( String column : nombresColumnas) {
            modelo.addColumn(column);
        }
        for ( Object ldc: (JSONArray) socioenuso.get("lineas-de-credito")){
            ArrayList data = new ArrayList<>();
            JSONObject LDC = (JSONObject) ldc;
            data.add(LDC.get("id"));
            data.add(LDC.get("tope"));
            data.add(LDC.get("monto-utilizado"));
            data.add(LDC.get("fecha-vigencia"));
            modelo.addRow(data.toArray());
        }
        JTable ldcTable = new JTable(modelo);

        pnlSLDC.setViewportView(ldcTable);
        pnlSLDC.setVisible(true);
    }

    private void crearTablaOperaciones(){
        // CAMBIAR POR LOS CAMPOS DEL JSON
        String [] nombresColumnas = {"ID","CUIT","Tope", "Monto Utilizado", "Fecha Vigencia"};
        DefaultTableModel modelo = new DefaultTableModel();
        for ( String column : nombresColumnas) {
            modelo.addColumn(column);
        }
        for ( Object op: operacionesSocio){
            ArrayList data = new ArrayList<>();
            JSONObject operacion = (JSONObject) op;
            data.add(operacion.get(""));
            modelo.addRow(data.toArray());
        }
        JTable accionistasTable = new JTable(modelo);

        pnlSOperacionesMTable.setViewportView(accionistasTable);
        pnlSOperacionesMTable.setVisible(true);
    }
}
