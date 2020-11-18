package vista;

import api.*;
import com.formdev.flatlaf.FlatLightLaf;
import impl.JSONHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class FrmDyR extends JDialog {
    private JPanel pnlPrincipal;
    private JPanel pnlTitulo;
    private JComboBox TipoBusqueda;
    private JTextField textFieldSocio;
    private JPanel pnlSearch;
    private JTabbedPane tabbedPaneDyR;
    private JPanel tabRecuperos;
    private JTabbedPane tabbedPaneR;
    private JPanel pnlNR;
    private JPanel pnlHR;
    private JPanel tabDesembolsos;
    private JTextField RSTextField;
    private JComboBox TipocomboBox;
    private JTextField TotalDeudaTxField;
    private JButton buscarButton;
    private JButton confirmBtn;
    private JTextField MontoTxField;
    private JTextField CUITTxField;
    private JScrollPane pnlSTable;
    private JScrollPane pnlSHTable;
    private JScrollPane pnlSDesembolsosTable;
    private JButton LDButton;
    private JTable deudasTable;
    private JTable recuperosTable;
    private JTable desembolsosTable;

    private Verificaciones verificar = new impl.Verificaciones();
    private FrmDyR self;

    private String filename = "./src/resources/socios.json";
    private API_JSONHandler file = new JSONHandler();
    private JSONObject jsonObject = (JSONObject) file.readJson(filename);

    public FrmDyR(Window owner, String Title) throws Exception {
        super(owner, Title);


        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
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
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if( verificarMonto() ) {
                        crearRecupero();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscarSocio();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });
        TipocomboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TipocomboBox.getSelectedItem().toString().equals("Parcial")){
                    MontoTxField.setEditable(true);
                    MontoTxField.setText("0.0");
                }else{
                    MontoTxField.setEditable(false);
                    MontoTxField.setText(TotalDeudaTxField.getText());
                }
            }
        });

        LDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarDesembolsos();
            }
        });
    }

    private void buscarSocio() throws Exception {
        jsonObject = (JSONObject) file.readJson(filename);
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        boolean socioEncontrado = false;
        for (Object obj: socioList){
            JSONObject socio = (JSONObject) obj;
            String rs = socio.get("razon-social").toString();
            String cuit = socio.get("cuit").toString();
            String estado = socio.get("estado").toString();
            if (TipoBusqueda.getSelectedItem().toString().equals("CUIT")){
                if (verificar.CUITValido(textFieldSocio.getText())) {
                    if (cuit.equals(textFieldSocio.getText()) && estado.equals("Pleno")) {
                        if (pnlHR.isShowing()) {
                            crearTablaHistorialRecuperos((JSONArray) socio.get("recuperos"));
                        } else {
                            CUITTxField.setText(cuit);
                            RSTextField.setText(rs);
                            crearTablaDeudas((JSONArray) socio.get("deudas"));
                        }
                        socioEncontrado = true;
                        break;
                    }
                }else if (textFieldSocio.getText().isEmpty()){
                    showMessageDialog(null, "El campo no puede estar vacío.\nIngrese un CUIT.");
                    socioEncontrado = true;
                }else{
                    showMessageDialog(null, "CUIT inválido. El mismo debe tener el formato ##-########-#.\nIngreselo nuevamente.");
                }
            }else if (TipoBusqueda.getSelectedItem().toString().equals("Razon Social")){
                if (rs.equals(textFieldSocio.getText()) && estado.equals("Pleno")){
                    if ( pnlHR.isShowing()){
                        crearTablaHistorialRecuperos((JSONArray) socio.get("recuperos"));
                        socioEncontrado = true;
                    }else {
                        CUITTxField.setText(cuit);
                        RSTextField.setText(rs);
                        crearTablaDeudas((JSONArray) socio.get("deudas"));
                        socioEncontrado = true;
                    }

                    break;
                }else if (textFieldSocio.getText().isEmpty()){
                    showMessageDialog(null, "El campo no puede estar vacío.\nIngrese una razón social.");
                    socioEncontrado = true;
                }
            }
        }
        if (!socioEncontrado){
            showMessageDialog(null, "El socio no se encuentra en la base de datos o no es Socio Partícipe.\nVerifique los datos e ingreselos nuevamente.");
        }
    }

    private void crearTablaDeudas(JSONArray deudas){
        String [] nombresColumnas = {"ID","Monto","Fecha Ven","Mora","Monto Mora","Subtotal"};
        DefaultTableModel modelo = new DefaultTableModel();
        for ( String column : nombresColumnas) {
            modelo.addColumn(column);
        }
        double total = 0;
        if (!deudas.isEmpty()) {
            for (Object deudaJ : deudas) {
                ArrayList data = new ArrayList<>();
                Deuda deuda = new impl.Deuda((JSONObject) deudaJ);
                data.add(deuda.getIdDeuda());
                data.add(deuda.getMonto());
                data.add(deuda.getFechaDeuda());
                data.add(deuda.isAplicaMora());
                data.add(deuda.getMontoMora());
                data.add(deuda.calcularSubtotal());
                modelo.addRow(data.toArray());
                total += deuda.calcularSubtotal();
            }
        }
        TotalDeudaTxField.setText(Double.toString(total));
        if (TipocomboBox.getSelectedItem().equals("Total")){
            MontoTxField.setText(Double.toString(total));
        }
        deudasTable = new JTable(modelo);

        pnlSTable.setViewportView(deudasTable);
        pnlSTable.setVisible(true);
    }



    private void crearRecupero() throws Exception {
        cancelarDeudas();
    }

    private void cancelarDeudas() throws Exception {
        ArrayList<String> idDeudas = new ArrayList<>();
        String filename = "./src/resources/socios.json";
        API_JSONHandler file = new JSONHandler();
        JSONObject jsonObject = (JSONObject) file.readJson(filename);
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        double monto = Double.parseDouble(MontoTxField.getText());
        for (Object s : socioList){
            JSONObject socio = (JSONObject) s;
            if (socio.get("cuit").equals(CUITTxField.getText())){
                JSONArray deudas = (JSONArray) socio.get("deudas");
                JSONArray nuevasdeudas = new JSONArray();
                int size = deudas.size();
                int index = 0;
                if (!deudas.isEmpty()) {
                    while (monto >= 0) {
                        if (monto == 0 || index == size) {
                            monto = Double.parseDouble(MontoTxField.getText()) - monto;
                            showMessageDialog(null, "Se creó un recupero por un monto de "+monto);
                            break;
                        }else{
                            Deuda deuda = new impl.Deuda((JSONObject) deudas.get(index));
                            if (monto - deuda.calcularSubtotal() >= 0) {
                                monto -= deuda.calcularSubtotal();
                                idDeudas.add(deuda.getIdDeuda());
                            } else {
                                nuevasdeudas.add(deuda.toJSON());
                            }
                            index++;
                        }

                    }
                }
                Recupero nuevoR = new impl.Recupero(
                        TipocomboBox.getSelectedItem().toString(),
                        monto,
                        CUITTxField.getText(),
                        idDeudas
                );
                JSONObject recupero = nuevoR.toJSON();
                socio.put("deudas", nuevasdeudas);
                JSONArray recuperos = (JSONArray) socio.get("recuperos");
                recuperos.add(recupero);
                socio.put("recuperos", recuperos);
                break;
            }
        }
        jsonObject.put("socios-participes", socioList);
        file.writeJson(filename, jsonObject);
    }

    private void crearTablaHistorialRecuperos(JSONArray recuperos){
        String [] nombresColumnas = {"CUIT","Tipo", "Monto", "Deudas"};
        DefaultTableModel modelo = new DefaultTableModel();
        for ( String column : nombresColumnas) {
            modelo.addColumn(column);
        }
        for ( Object recupero: recuperos){
            ArrayList data = new ArrayList<>();
            Recupero recu = new impl.Recupero((JSONObject) recupero);
            data.add(recu.getIdSocioPleno());
            data.add(recu.getTipo());
            data.add(recu.getMonto());
            data.add(recu.getIdDeuda());
            modelo.addRow(data.toArray());
        }

        recuperosTable = new JTable(modelo);

        pnlSHTable.setViewportView(recuperosTable);
        pnlSHTable.setVisible(true);

    }

    private void guardarDatos(JSONObject recupero) throws Exception {
        String filename = "./src/resources/socios.json";
        API_JSONHandler file = new JSONHandler();
        JSONObject jsonObject = (JSONObject) file.readJson(filename);
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object s : socioList){
            JSONObject socio = (JSONObject) s;
            if (socio.get("cuit").equals(recupero.get("id-socio-pleno"))){
                JSONArray recuperos = (JSONArray) socio.get("recuperos");
                recuperos.add(recupero);
                socio.put("recuperos", recuperos);
                break;
            }
        }
        jsonObject.put("socios-participes", socioList);
        file.writeJson(filename, jsonObject);

    }

    private void buscarDesembolsos(){
        JSONArray desembolsos = (JSONArray) jsonObject.get("desembolsos");
        crearTablaDesembolsos(desembolsos);
    }

    private void crearTablaDesembolsos(JSONArray desembolsos){
        String [] nombresColumnas = {"id", "CUIT", "Razón social", "Monto", "Fecha Operado"};
        DefaultTableModel modelo = new DefaultTableModel();
        for ( String column : nombresColumnas) {
            modelo.addColumn(column);
        }
        for (Object des: desembolsos) {
            ArrayList data = new ArrayList<>();
            Desembolso desembolso = new impl.Desembolso((JSONObject) des);
            data.add(desembolso.getId());
            data.add(desembolso.getCuit());
            data.add(desembolso.getRazonSocial());
            data.add(desembolso.getMonto());
            data.add(desembolso.getFechaOp().toString());
            modelo.addRow(data.toArray());
        }
        desembolsosTable = new JTable(modelo);
        pnlSDesembolsosTable.setViewportView(desembolsosTable);
        pnlSDesembolsosTable.setVisible(true);
    }

    private boolean verificarMonto(){
        if (Double.parseDouble(TotalDeudaTxField.getText()) == 0){
            showMessageDialog(null, "El socio no tiene deudas asociadas.");
            return false;
        }else {
            if (!verificar.esnumerico(MontoTxField.getText())) {
                showMessageDialog(null, "El campo debe ser numérico.\nIngrese un monto válido.");
                return false;
            } else {
                if (Double.parseDouble(MontoTxField.getText()) <= 0) {
                    showMessageDialog(null, "El monto debe ser mayor a 0.\nIngrese un monto válido.");
                    return false;
                }
                if (Double.parseDouble(MontoTxField.getText()) > Double.parseDouble(TotalDeudaTxField.getText())) {
                    showMessageDialog(null, "El monto no puede ser mayor a la deuda contraida.\nIngrese un monto válido.");
                    return false;
                }
            }
        }
        return true;
    }
}
