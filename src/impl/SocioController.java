package impl;

import api.API_JSONHandler;
import api.Verificaciones;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class SocioController implements api.SocioController {
    private API_JSONHandler file = new JSONHandler();
    private String filename = "./src/resources/socios.json";
    private JSONObject jsonObject = (JSONObject) file.readJson(filename);
    private JSONArray sociosList;
    private JSONObject socioenuso;
    private Verificaciones verificar = new impl.Verificaciones();

    public SocioController() throws Exception {
    }

    /*======CLASS FUNCTIONS=======*/
    @Override
    public void controlarDocumentacion(){

    }

    @Override
    public void altaSocioProtector(){

    }

    @Override
    public void altaSocioParticipe(){

    }

    @Override
    public ArrayList<String> ComparteAccionistas(String CUITSocio){
        ArrayList<String> accionistas = null;
        return accionistas;
    }

    @Override
    public ArrayList<String> ListarCUITSocioPorTipoEmpresa(String tipoEmpresa) throws Exception {
        jsonObject = (JSONObject) file.readJson(filename);
        sociosList = (JSONArray) jsonObject.get("socios-participes");
        ArrayList<String> cuitsocios = new ArrayList<>();
        for (Object soc : sociosList) {
            socioenuso = (JSONObject) soc;
            String tipoempresa = socioenuso.get("tipo-empresa").toString();
            if (tipoEmpresa.equals(tipoempresa)) {
                cuitsocios.add(socioenuso.get("cuit").toString());
            }
        }
        return cuitsocios;
    }

    @Override
    public void aprobarSocioProtector(String CUITsocio){

    }

    @Override
    public void aprobarSocioParticipe(String CUITsocio){

    }

    @Override
    public void crearAporte(String CUIT, int Cantidad, File documento){

    }

    public JSONArray buscarDesembolsos(){
        return (JSONArray) jsonObject.get("desembolsos");
    }

    public boolean verificarMontoRecupero(double totaldeuda, String monto ){
        if (totaldeuda== 0){
            showMessageDialog(null, "El socio no tiene deudas asociadas.");
            return false;
        }else {
            try{
                Double.parseDouble(monto);
            }catch (Exception er){
                showMessageDialog(null, "El campo debe ser numérico.\nIngrese un monto válido.");
                return false;
            }
            if (Double.parseDouble(monto) <= 0) {
                showMessageDialog(null, "El monto debe ser mayor a 0.\nIngrese un monto válido.");
                return false;
            }
            if (Double.parseDouble(monto) > totaldeuda) {
                showMessageDialog(null, "El monto no puede ser mayor a la deuda contraida.\nIngrese un monto válido.");
                return false;
            }
        }
        return true;
    }



}
