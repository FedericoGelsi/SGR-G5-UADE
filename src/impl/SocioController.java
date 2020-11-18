package impl;

import api.API_JSONHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class SocioController implements api.SocioController {
    private API_JSONHandler file = new JSONHandler();
    private String filename = "./src/resources/socios.json";
    private JSONObject jsonObject = (JSONObject) file.readJson(filename);
    private JSONArray sociosList;
    private JSONObject socioenuso;

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
}
