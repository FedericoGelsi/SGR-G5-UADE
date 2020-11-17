package vista;

import api.API_JSONHandler;
import api.Verificaciones;
import impl.Contragarantia;
import impl.JSONHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FrmLytiposOP extends JDialog{
    private JPanel pnlPrincipal;
    private JPanel pnlTitulo;
    private JComboBox comboBoxCUIT;
    private JButton buscarButton1;
    private JTextField textFieldCODCUIT;
    private JTabbedPane tabbedAgregar;
    private JPanel tabAgregarC;
    private JTabbedPane tabbedPaneC;
    private JPanel pnlNC;
    private JButton confirmarAgregar;
    private JTextField MontoTxField;
    private JTextField textFieldCUIT;
    private JTextField MontoField;
    private JComboBox comboBoxTIPO;
    private JPanel pnlHR;
    private JScrollPane pnlHCTable;
    private JPanel PnlHistorial;
    private JScrollPane pnlH;
    private JButton listarButton;
    private JTable HcontraTable;

    private Verificaciones verificar = new impl.Verificaciones();
    private FrmLytiposOP self;

    private String filename = "./src/resources/socios.json";
    private API_JSONHandler file = new JSONHandler();
    private JSONObject jsonObject = (JSONObject) file.readJson(filename);

    public FrmLytiposOP(Window owner, String Title) throws Exception {
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
    }
    private void buscarsocio() throws Exception {
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            String estado = socio.get("estado").toString();
            if (comboBoxCUIT.getSelectedItem().toString().equals("CUIT")) {
                if (verificar.CUITValido(textFieldCODCUIT.getText())) {
                    if (cuit.equals(textFieldCODCUIT.getText()) && estado.equals("Pleno")) {
                        textFieldCUIT.setText(cuit);
                        if (pnlNC.isShowing()) {
                            crearTablaHistorialContragarantias((JSONArray) socio.get("Hcontragarantias"));
                        }
                    }
                }
            }
        }
    }

}
    private void guardarDatos(JSONObject contragarantia) throws Exception {
        String filename = "./src/resources/socios.json";
        API_JSONHandler file = new JSONHandler();
        JSONObject jsonObject = (JSONObject) file.readJson(filename);
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object s : socioList){
            JSONObject socio = (JSONObject) s;
            if (socio.get("cuit").equals(contragarantia.get("id-socio-pleno"))){
                JSONArray contragarantias = (JSONArray) socio.get("contragarantias");
                contragarantias.add(contragarantia);
                socio.put("contragarantias", contragarantias);
                break;
            }
        }
        jsonObject.put("socios-participes", socioList);
        file.writeJson(filename, jsonObject);

    }
    private void crearTablaHistorialContragarantias(JSONArray contragarantias){
        String [] nombresColumnas = {"CUIT","Tipo", "Monto", "Vigencia"};
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
            modelo.addRow(data.toArray());
        }

        HcontraTable = new JTable(modelo);

        pnlHCTable.setViewportView(HcontraTable);

    }
}
private void crearContragarantia() throws Exception{
        ArrayList<String> idContrcarantias = new ArrayList<>();
        for (int i= 0 ; )

        }

}
