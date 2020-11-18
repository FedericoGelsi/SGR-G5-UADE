package vista;

import api.*;
import com.formdev.flatlaf.FlatLightLaf;
import impl.JSONHandler;
import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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
    private OperationController opController = new impl.OperationController();

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
        // CONSULTA 1
        calcularBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (verificar.fechavalida(FechaTxf.getText())){
                    try {
                        TotalComisionesTxf.setText(String.valueOf(opController.comisionDiariaTOP(TipocomboBox.getSelectedItem().toString(),LocalDate.parse(FechaTxf.getText()))));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
        // CONSULTA 2

        buscarSocioBtn.addActionListener(new ActionListener() {
            @Override
            // BUSCAR SOCIO
            public void actionPerformed(ActionEvent e) {
                try {
                    Socio_Participe socio =new impl.Socio_Participe( socioController.buscarSocioParticipe(CUITTxf.getText()));
                    if (socio != null){
                        RSTxf.setText(socio.getRazonSocial());
                        buscarOPBtn.addActionListener(new ActionListener() {
                            @Override
                            // BUSCAR OP
                            public void actionPerformed(ActionEvent e) {
                                if (verificar.fechavalida(FechaIniTxf.getText()) && verificar.fechavalida(FechaFinTxf.getText())) {
                                    try {
                                        opController.operacionesAvaladasPorSocio(
                                                socio.getCUITSocio(),
                                                LocalDate.parse(FechaIniTxf.getText()),
                                                LocalDate.parse(FechaFinTxf.getText()),
                                                pnlSOperacionesMTable
                                        );
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
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
                    consultaConsolidada();
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
                    consultaConsolidada();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        buscardeudaBTn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Double> datos = opController.ConsultaSaldoMora(socioenuso);
                moradiariaTxf.setText(String.valueOf(datos.get(0)));
                saldomoraTxf.setText(String.valueOf(datos.get(0)));
                crearTablaDeudas(pnlSMoraTable);
            }
        });

        // CONSULTA 6
        BuscarSCBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resetCampos();
                    consultaConsolidada();
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
                double sociocount = 0;
                ArrayList<Double> data;
                for (String cuit: cuitsocios){
                    //getoperacionesSocio(cuit);
                    data = calcularTotalOperadoSocio(cuit);
                    totaloperado += data.get(0);
                    totaltasa += data.get(1);
                    sociocount ++;
                }
                TotalOperadoTxf.setText(String.valueOf(totaloperado));
                System.out.println(totaltasa/sociocount);
                PTDC3Txf.setText(String.valueOf(totaltasa/sociocount));
            }
        }
    }

    private ArrayList<Double> calcularTotalOperadoSocio(String cuit) throws Exception {
        double montooperado = 0;
        double tasadescuento = 0;
        double countop = 1;
        String filenameOP = "./src/resources/operacioncontroller.json";
        JSONObject jsonObjectOP = (JSONObject) file.readJson(filenameOP);
        JSONArray operaciones = (JSONArray) jsonObjectOP.get("operaciones");
        for (Object op : operaciones){
            JSONObject operacion = (JSONObject) op;
            // Cheque Propio
            //Cheque Terceros
            //Pagaré bursátil
            if (operacion.get("CUITSocio").toString().equals(cuit)) {
                if (operacion.get("tipo").equals("Cheque Propio") || operacion.get("tipo").equals("Cheque Terceros") || operacion.get("tipo").equals("Pagare Bursatil")) {
                    montooperado += Double.parseDouble(operacion.get("importetotal").toString());
                    tasadescuento += Double.parseDouble(operacion.get("tasadedescuento").toString());
                    countop += 1;
                }
            }
        }
        ArrayList<Double> data = new ArrayList<>();
        data.add(montooperado);
        System.out.println(tasadescuento/countop);
        data.add(tasadescuento/countop);
        return data;
    }

    private void consultaConsolidada() throws Exception {
        // CONSULTA 6
        String cuit = "";
        if (CUITCCTxf.getText().isEmpty() && CUITMTxf.getText().isEmpty() && CUITC4Txf.getText().isEmpty()) {
            showMessageDialog(null, "El campo no puede estar vacío.\nIngrese un CUIT.");
        } else if (CUITC4Txf.isShowing()){
            cuit = CUITC4Txf.getText();
        }else if(CUITCCTxf.isShowing()) {
            cuit = CUITCCTxf.getText();
        }else if(CUITMTxf.isShowing()) {
            cuit = CUITMTxf.getText();
        }else {
            showMessageDialog(null, "CUIT inválido. El mismo debe tener el formato ##-########-#.\nIngreselo nuevamente.");
        }
        socioenuso = socioController.buscarSocioParticipe(cuit);
        if ( !socioenuso.isEmpty()){
            Socio_Participe socio = new impl.Socio_Participe(socioenuso);
            if (pnlTabC6.isShowing()) {
                RSCCTxf.setText(socioenuso.get("razon-social").toString());
                TSDSTxf.setText("Socio Partícipe");
                calcularRiesgoVivo();
                datossocio( socioenuso );
                crearTablas(1);
                RVCCTxf.setText(String.valueOf(opController.calcularRiesgoVivo(cuit)));

            }else if(pnlTabC5.isShowing()){
                RSMTxf.setText(socio.getRazonSocial());
            }else if (pnlTabC4.isShowing()){
                RSC4Txf.setText(socio.getRazonSocial());
            }
        }else{
            socioenuso = socioController.buscarSocioProtector(cuit);
            RSCCTxf.setText(socioenuso.get("razon-social").toString());
            TSDSTxf.setText("Socio Protector");
            calcularRiesgoVivo();
            getoperacionesSocio(cuit);
            datossocio( socioenuso);
            crearTablas(2);

        }
    }

    // CONSULTA 6
    private void datossocio(JSONObject socio){
        CUITDSTxf.setText((String) socio.get("cuit"));
        RSDSTxf.setText((String) socio.get("razon-social"));
        FIADSTxf.setText((String) socio.get("finic-act"));
        TEDSTxf.setText((String) socio.get("tipo-empresa"));
        DDSTxf.setText((String) socio.get("direccion"));
        EmailDSTxf.setText((String) socio.get("email"));
        EDSTxf.setText((String) socio.get("estado"));
        FPDSTxf.setText((String) socio.get("fecha-pleno"));
        APDSTxf.setText((String) socio.get("actividad-principal"));


    }

    private void getoperacionesSocio(String cuit) throws Exception {
        String filenameOP = "./src/resources/operacioncontroller.json";
        JSONObject jsonObjectOP = (JSONObject) file.readJson(filenameOP);
        JSONArray operaciones = (JSONArray) jsonObjectOP.get("operaciones");
        for( Object obj: operaciones){
            JSONObject op = (JSONObject) obj;
            if (op.get("CUITfirmante").equals(cuit)){
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

    public void crearTablas(int opc) throws Exception {
        if (opc==1) {
            crearTablaRecuperos();
            crearTablaDeudas(pnlSDeudas);
            crearTablaContragarantias();
            crearTablaLDC();
            crearTablaOperaciones();
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
        JSONObject LDC = (JSONObject) socioenuso.get("lineas-de-credito");
        RSLDCTxf.setText(RSCCTxf.getText());
        CUITLDCTxf.setText(CUITCCTxf.getText());
        IDLDCTxf.setText(LDC.get("id").toString());
        TopeLDCTxf.setText(LDC.get("tope").toString());
        MontoLDCTxf.setText(LDC.get("monto-utilizado").toString());
        TUCCTxf.setText(MontoLDCTxf.getText());
        FVLDCTxf.setText(LDC.get("fecha-vigencia").toString());
        pnlSLDC.setVisible(true);

    }

    private void crearTablaOperaciones( ) throws Exception {
        // CAMBIAR POR LOS CAMPOS DEL JSON
        String filenameOP = "./src/resources/operacioncontroller.json";
        JSONObject jsonObjectOP = (JSONObject) file.readJson(filenameOP);
        operacionesSocio = (JSONArray) jsonObjectOP.get("operaciones");
        String [] nombresColumnas = {"ID","CUIT","Tipo","Estado", "Monto","Banco", "Fecha Vencimiento"};
        DefaultTableModel modelo = new DefaultTableModel();
        for ( String column : nombresColumnas) {
            modelo.addColumn(column);
        }
        for ( Object op: operacionesSocio){
            ArrayList data = new ArrayList<>();
            JSONObject operacion = (JSONObject) op;
            data.add(operacion.get("numerooperacion"));
            data.add(operacion.get("CUITSocio"));
            data.add(operacion.get("tipo"));
            data.add(operacion.get("estado"));
            data.add(operacion.get("importetotal"));
            if (operacion.get("banco") == null) {
                data.add("");
            }else{
                data.add(operacion.get("banco"));
            }
            data.add(operacion.get("fechavencimiento"));


            modelo.addRow(data.toArray());
        }
        JTable accionistasTable = new JTable(modelo);

        pnlSOP.setViewportView(accionistasTable);
        pnlSOP.setVisible(true);
    }


}
