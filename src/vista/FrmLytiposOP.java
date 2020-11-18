package vista;

import com.formdev.flatlaf.FlatLightLaf;
import api.API_JSONHandler;
import api.Verificaciones;
import impl.Contragarantia;
import impl.JSONHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class FrmLytiposOP extends JDialog{
    private JPanel pnlPrincipal;
    private JPanel pnlTitulo;
    private JComboBox comboBoxCUIT;
    private JButton buscarButton1;
    private JTextField textFieldCODCUIT;
    private JPanel pnlNC;
    private JTextField textFieldCUIT;
    private JComboBox comboBoxTIPO;
    private JScrollPane pnlHCTable;
    private JButton buscarButton;
    private JPanel tabAgregarC;
    private JTabbedPane tabbedPaneC;
    private JTextField MontoField;
    private JButton confirmBtn;
    private JPanel pnlHC;
    private JButton listarButton;
    private JTable HcontraTable;
    private JTextField VigenciaTxf;

    private Verificaciones verificar = new impl.Verificaciones();
    private FrmLytiposOP self;

    private String filename = "./src/resources/socios.json";
    private API_JSONHandler file = new JSONHandler();
    private JSONObject jsonObject = (JSONObject) file.readJson(filename);

    public FrmLytiposOP(Window owner, String Title) throws Exception {
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

        this.asociarEventos();
        this.self= this;
    }
    private void asociarEventos(){
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    crearContragarantia();
                } catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    buscarsocio();
                } catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });

    }
    private void buscarsocio() throws Exception {
        jsonObject = (JSONObject) file.readJson(filename);
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            String rs = socio.get("razon-social").toString();
            String estado = socio.get("estado").toString();
            if (comboBoxCUIT.getSelectedItem().toString().equals("CUIT")) {
                if (verificar.CUITValido(textFieldCODCUIT.getText())) {
                    if (cuit.equals(textFieldCODCUIT.getText()) && estado.equals("Pleno")) {
                        textFieldCUIT.setText(cuit);
                        if (pnlHC.isShowing()) {
                            crearTablaHistorialContragarantias((JSONArray) socio.get("contragarantias"));
                        }
                    }
                }else{
                    showMessageDialog(null, "Ingrese un CUIT válido");
                    break;
                }
            }else if (comboBoxCUIT.getSelectedItem().toString().equals("Razón Social")) {
                if (rs.equals(textFieldCODCUIT.getText()) && estado.equals("Pleno")) {
                    textFieldCUIT.setText(cuit);
                    if (pnlHC.isShowing()) {
                        crearTablaHistorialContragarantias((JSONArray) socio.get("contragarantias"));
                    }
                }else{
                    showMessageDialog(null, "No se encuenta el socio o no es pleno.\nIngrese un CUIT válido");
                    break;
                }
            }
        }
    }


    private void guardarDatos(JSONObject contragarantia) throws Exception {
        JSONObject jsonObject = (JSONObject) file.readJson(filename);
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object s : socioList){
            JSONObject socio = (JSONObject) s;
            if (socio.get("cuit").equals(contragarantia.get("cuit-socio"))){
                JSONArray contragarantias = (JSONArray) socio.get("contragarantias");
                contragarantias.add(contragarantia);
                socio.put("contragarantias", contragarantias);
                break;
            }
        }
        jsonObject.put("socios-participes", socioList);
        file.writeJson(filename, jsonObject);
        showMessageDialog(null, "Contragarantía creada con éxito");
    }

    private void crearTablaHistorialContragarantias(JSONArray contragarantias){
        String [] nombresColumnas = {"CUIT","Tipo", "Monto", "Vigencia", "Fecha creación"};
        DefaultTableModel modelo = new DefaultTableModel();
        for ( String column : nombresColumnas) {
            modelo.addColumn(column);
        }
        for ( Object contraJ: contragarantias){
            ArrayList data = new ArrayList<>();
            Contragarantia contra = new impl.Contragarantia((JSONObject) contraJ);
            data.add(contra.getCUITPropietario());
            data.add(contra.getTipo());
            data.add(contra.getMontoContragarantia());
            data.add(contra.getFechaVigencia());
            data.add(contra.getFechacreacion());
            modelo.addRow(data.toArray());
        }

        HcontraTable = new JTable(modelo);
        pnlHCTable.setViewportView(HcontraTable);
        pnlHCTable.setVisible(true);
    }

    private void crearContragarantia() throws Exception {
        if (verificar.fechavalida(VigenciaTxf.getText())){
            DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate fechavigencia = LocalDate.parse(VigenciaTxf.getText(),dtf);
            if (verificar.CUITValido(textFieldCUIT.getText())){
                if (verificar.esnumerico(MontoField.getText())) {
                    api.Contragarantia contragarantia = new Contragarantia(
                            comboBoxTIPO.getSelectedItem().toString(),
                            Double.parseDouble(MontoField.getText()),
                            fechavigencia,
                            textFieldCUIT.getText()
                    );
                    guardarDatos(contragarantia.toJSON());
                }else{
                    showMessageDialog(null, "El monto debe ser numérico.");
                }
            }
        }
    }

}


