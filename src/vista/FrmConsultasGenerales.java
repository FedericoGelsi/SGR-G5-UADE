package vista;

import api.*;
import com.formdev.flatlaf.FlatLightLaf;
import impl.JSONHandler;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private JPanel pnlSLDC;
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
    private JButton buscardeudaBTn;
    private JButton BSC4Btn;
    private JButton CComiC4Btn;
    private JTextField CUITC4Txf;
    private JTextField RSC4Txf;
    private JTextField COMIC4Txf;
    private JComboBox TipoOPC4ComboBox;
    private JTextField FechaIniC3Txf;
    private JTextField FechaFinC3Txf;
    private JTextField TotalOperadoTxf;
    private JTextField PTDC3Txf;
    private JButton BuscarOPC3Txf;
    private JComboBox TEC3ComboBox;
    private JTextField CUITLDCTxf;
    private JTextField RSLDCTxf;
    private JTextField IDLDCTxf;
    private JTextField FVLDCTxf;
    private JTextField TopeLDCTxf;
    private JTextField MontoLDCTxf;
    private JTextField TSDSTxf;
    private JPanel CCRecuTab;
    private JPanel CCDeudasTab;
    private JPanel CCContraTab;
    private JPanel CCAccionTab;
    private JPanel CCLDCTab;
    private JPanel CCOPTab;
    private JPanel CCAportesTab;

    private FrmConsultasGenerales self;
    private Verificaciones verificar = new impl.Verificaciones();

    private API_JSONHandler file = new JSONHandler();
    private String filename = "./src/resources/socios.json";
    private JSONObject jsonObject = (JSONObject) file.readJson(filename);
    private JSONArray sociosList;
    private JSONObject socioenuso;
    private JSONArray operacionesSocio;

    private SocioController socioController = new impl.SocioController();


    public FrmConsultasGenerales(Window owner, String Title) throws Exception {
        super(owner, Title);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());

        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
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

        // CONSULTA 3
        BuscarOPC3Txf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    calcularTotalOperado();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        // CONSULTA 4
        BSC4Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscarSocio();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        CComiC4Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TipoOPC4ComboBox.getSelectedItem().equals("Préstamo")){
                    COMIC4Txf.setText("4%");
                }else{
                    COMIC4Txf.setText("3%");
                }
            }
        });

        // CONSULTA 5
        BuscarMBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscarSocio();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        buscardeudaBTn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarMora();
            }
        });

        // CONSULTA 6
        BuscarSCBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resetCampos();
                    buscarSocio();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });



    }

    // CONSULTA 3
    private void calcularTotalOperado() throws Exception {
        if (verificar.fechavalida(FechaIniC3Txf.getText()) && verificar.fechavalida(FechaFinC3Txf.getText())) {
            ArrayList<String> cuitsocios = socioController.ListarCUITSocioPorTipoEmpresa(TEC3ComboBox.getSelectedItem().toString());
            if (cuitsocios.isEmpty()){
                showMessageDialog(null, "No existen socios para ese tipo de empresa.");
            }else{
                double totaloperado = 0;
                double totaltasa = 0;
                int sociocount = 0;
                ArrayList<Double> data;
                for (String cuit: cuitsocios){
                    getoperacionesSocio(cuit);
                    data = calcularTotalOperadoSocio();
                    totaloperado += data.get(0);
                    totaltasa += data.get(1);
                    sociocount ++;
                }
                TotalOperadoTxf.setText(String.valueOf(totaloperado));
                PTDC3Txf.setText(String.valueOf(totaltasa/sociocount));
            }
        }
    }

    private ArrayList<Double> calcularTotalOperadoSocio(){
        double montooperado = 0;
        double tasadescuento = 0;
        int countop = 0;
        for (Object op : operacionesSocio){
            JSONObject operacion = (JSONObject) op;
            // Cheque Propio
            //Cheque Terceros
            //Pagaré bursátil
            if (operacion.get("tipo").equals("Cheque propio") || operacion.get("tipo").equals("Cheque Terceros") || operacion.get("tipo").equals("Pagaré bursátil")) {
                montooperado += Double.parseDouble(operacion.get("importetotal").toString());
                tasadescuento += Double.parseDouble(operacion.get("tasadedescuento").toString());
                countop ++;
            }
        }
        ArrayList<Double> data = new ArrayList<>();
        data.add(montooperado);
        data.add(tasadescuento/countop);
        return data;
    }

    private void buscarSocio() throws Exception {
        jsonObject = (JSONObject) file.readJson(filename);
        sociosList = (JSONArray) jsonObject.get("socios-participes");
        boolean socioEncontrado = false;
        for (Object obj: sociosList){
            socioenuso = (JSONObject) obj;
            String rs = socioenuso.get("razon-social").toString();
            String cuit = socioenuso.get("cuit").toString();
            String estado = socioenuso.get("estado").toString();
            if (pnlTabC6.isShowing()) {
                if (verificar.CUITValido(CUITCCTxf.getText())) {
                    if (cuit.equals(CUITCCTxf.getText())) {
                        RSCCTxf.setText(rs);
                        calcularRiesgoVivo();
                        //getoperacionesSocio(cuit);
                        datossocio();
                        crearTablas(1);
                        TSDSTxf.setText("Socio Partícipe");
                        socioEncontrado = true;
                        break;
                    }
                }
            }else if(pnlTabC5.isShowing()){
                if (verificar.CUITValido(CUITMTxf.getText()) && estado.equals("Pleno")) {
                    if (cuit.equals(CUITMTxf.getText())) {
                        RSMTxf.setText(rs);
                        socioEncontrado = true;
                        break;
                    }
                }
            }else if (pnlTabC4.isShowing()){
                if (verificar.CUITValido(CUITC4Txf.getText()) && estado.equals("Pleno")) {
                    if (cuit.equals(CUITC4Txf.getText())) {
                        RSC4Txf.setText(rs);
                        socioEncontrado = true;
                        break;
                    }
                }
            }
            if (CUITCCTxf.getText().isEmpty() && CUITMTxf.getText().isEmpty() && CUITC4Txf.getText().isEmpty()){
                showMessageDialog(null, "El campo no puede estar vacío.\nIngrese un CUIT.");
                socioEncontrado = true;
            }
        }
        if (!socioEncontrado) {
            sociosList = (JSONArray) jsonObject.get("socios-protectores");
            for (Object obj : sociosList) {
                socioenuso = (JSONObject) obj;
                String rs = socioenuso.get("razon-social").toString();
                String cuit = socioenuso.get("cuit").toString();
                String estado = socioenuso.get("estado").toString();
                if (pnlTabC6.isShowing()) {
                    if (verificar.CUITValido(CUITCCTxf.getText())) {
                        if (cuit.equals(CUITCCTxf.getText())) {
                            RSCCTxf.setText(rs);
                            calcularRiesgoVivo();
                            //getoperacionesSocio(cuit);
                            datossocio();
                            crearTablas(2);
                            TSDSTxf.setText("Socio Protector");
                            socioEncontrado = true;
                            break;
                        }
                    }
                    if (CUITCCTxf.getText().isEmpty()) {
                        showMessageDialog(null, "El campo no puede estar vacío.\nIngrese un CUIT.");
                        socioEncontrado = true;
                    } else {
                        showMessageDialog(null, "CUIT inválido. El mismo debe tener el formato ##-########-#.\nIngreselo nuevamente.");
                    }
                }
            }
        }
        if (!socioEncontrado){
            showMessageDialog(null, "El socio no se encuentra en la base de datos o no es Socio Partícipe.\nVerifique los datos e ingreselos nuevamente.");
        }
    }

    // CONSULTA 5
    private void consultarMora(){
        JSONArray deudas = (JSONArray) socioenuso.get("deudas");
        double moradiaria = 0;
        double moratotal = 0;
        for (Object d: deudas){
            Deuda deuda = new impl.Deuda((JSONObject) d);
            if (deuda.isAplicaMora()) {
                if (deuda.getFechaDeuda().isBefore(LocalDate.now())){
                    moratotal += (deuda.getMontoMora() * ChronoUnit.DAYS.between(deuda.getFechaDeuda(), LocalDate.now()));
                }
                moradiaria += deuda.getMontoMora();
            }
        }
        moradiariaTxf.setText(String.valueOf(moradiaria));
        saldomoraTxf.setText(String.valueOf(moratotal));
        crearTablaDeudas(pnlSMoraTable);
    }

    // CONSULTA 6
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

    private void resetCampos(){
        // RESET DATOS
        CUITDSTxf.setText("");
        RSDSTxf.setText("");
        FIADSTxf.setText("");
        TEDSTxf.setText("");
        DDSTxf.setText("");
        EmailDSTxf.setText("");
        EDSTxf.setText("");
        FPDSTxf.setText("");
        APDSTxf.setText("");

        // RESET TABLAS
        pnlSAccionistas.setVisible(false);
        pnlSContragarantias.setVisible(false);
        pnlSLDC.setVisible(false);
        pnlSOperacionesMTable.setVisible(false);
        pnlSAportes.setVisible(false);
        pnlSDeudas.setVisible(false);
        pnlSRecuperos.setVisible(false);
    }

    private void calcularRiesgoVivo(){

    }

    public void crearTablas(int opc){
        if (opc==1) {
            crearTablaRecuperos();
            crearTablaDeudas(pnlSDeudas);
            crearTablaContragarantias();
            crearTablaLDC();
            //crearTablaOperaciones();
        }else if(opc==2){
            crearTablaAportes();
        }
        crearTablaAccionistas();
    }

    private void crearTablaAportes(){
        String [] nombresColumnas = {"ID","CUIT","Monto", "Fecha creación"};
        DefaultTableModel modelo = new DefaultTableModel();
        for ( String column : nombresColumnas) {
            modelo.addColumn(column);
        }
        for ( Object aport: (JSONArray) socioenuso.get("aportes")){
            ArrayList data = new ArrayList<>();
            Aporte aporte = new impl.Aporte((JSONObject) aport);
            data.add(aporte.getIDAporte());
            data.add(aporte.getSocioAportante());
            data.add(aporte.getMontoAporte());
            data.add(aporte.getFechaCreacion());
            modelo.addRow(data.toArray());
        }
        JTable aportesTable= new JTable(modelo);

        pnlSAportes.setViewportView(aportesTable);
        pnlSAportes.setVisible(true);
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

    private void crearTablaDeudas(JScrollPane pnlEnUso){
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

        pnlEnUso.setViewportView(deudasTable);
        pnlEnUso.setVisible(true);
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
        JSONObject LDC = (JSONObject) socioenuso.get("linea-de-credito");
        RSLDCTxf.setText(RSCCTxf.getText());
        CUITLDCTxf.setText(CUITCCTxf.getText());
        IDLDCTxf.setText(LDC.get("id").toString());
        TopeLDCTxf.setText(LDC.get("tope").toString());
        MontoLDCTxf.setText(LDC.get("monto-utilizado").toString());
        FVLDCTxf.setText(LDC.get("fecha-vigencia").toString());
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

    private void copiarArchivo(String from, String to) throws IOException {
        Path origen = Paths.get(from);
        Path destino = Paths.get(to);
        String ext = FilenameUtils.getExtension(origen.toString());
        if (ext.equals("pdf") || ext.equals("jpg") || ext.equals("png") || ext.equals("jpeg")) {
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
