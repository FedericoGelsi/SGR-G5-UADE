package impl;

import api.API_JSONHandler;
import api.Verificaciones;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class SocioController implements api.SocioController {
    private API_JSONHandler file = new JSONHandler();
    private String filename = "./src/resources/socios.json";
    private JSONObject jsonObject = (JSONObject) file.readJson(filename);

    private String filenamefact = "./src/resources/operacioncontroller.json";
    private JSONObject jsonObjectOPC = (JSONObject) file.readJson(filenamefact);

    private String filenamefdr = "./src/resources/FondodeRiesgo.json";
    private JSONObject jsonObjectfdr = (JSONObject) file.readJson(filenamefdr);
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

    public JSONObject buscarSocioParticipe(String cuit) throws Exception {
        jsonObject = (JSONObject) file.readJson(filename);
        sociosList = (JSONArray) jsonObject.get("socios-participes");
        if (!sociosList.isEmpty()) {
            for (Object sc : sociosList) {
                //api.Socio_Participe socio = new Socio_Participe((JSONObject) sc);
                JSONObject socio = (JSONObject) sc;
                if (socio.get("cuit").equals(cuit) && socio.get("estado").equals("Pleno")) {
                    return socio;
                }
            }
            showMessageDialog(null, "El socio no existe, no es partícipe o no es pleno.");
        }
        return null;
    }

    public JSONObject buscarSocioProtector(String cuit) throws Exception{
        jsonObject = (JSONObject) file.readJson(filename);
        sociosList = (JSONArray) jsonObject.get("socios-protector");
        if (!sociosList.isEmpty()) {
            for (Object sc : sociosList) {
                //api.Socio_Protector socio = new Socio_Protector((JSONObject) sc);
                JSONObject socio = (JSONObject) sc;
                if (socio.get("cuit").equals(cuit) && socio.get("estado").equals("Pleno")) {
                    return socio;
                }
            }
            showMessageDialog(null, "El socio no existe, no es protector o no es pleno.");
        }
        return null;
    }


    public boolean lineacreditovigente(String CUITSocio) {
        String vigencia;
        boolean vigenciaflag = true;
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            if (CUITSocio.equalsIgnoreCase(cuit)) {
                JSONObject lineadecredito = (JSONObject) socio.get("lineas-de-credito");
                vigencia = lineadecredito.get("fecha-vigencia").toString();
                LocalDate vigenciadate = LocalDate.parse(vigencia);
                if (verificar.fechavshoy(vigenciadate) == "Menor") {
                    vigenciaflag = false;
                }
            }
        }
        return vigenciaflag;
    }


    public boolean lineasuficiente(String CUITSocio, float montooperacion) {
        double tope = 0;
        double utilizado = 0;
        double montodisponible = 0;
        boolean lineasuficienteFlag = true;
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            if (CUITSocio.equalsIgnoreCase(cuit)) {
                JSONObject lineadecredito = (JSONObject) socio.get("lineas-de-credito");
                tope = (double) lineadecredito.get("tope");
                utilizado = (double) lineadecredito.get("monto-utilizado");
                montodisponible = tope - utilizado;
            }
        }
        if (montodisponible < (double) montooperacion) {
            lineasuficienteFlag = false;
        }
        return lineasuficienteFlag;
    }


    public boolean contragarantiassuficientes(String CUITSocio, float montooperacion) {
        double monto = 0;
        double montototal = 0;
        boolean contragarantiassuficientesFlag = true;
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            if (CUITSocio.equalsIgnoreCase(cuit)) {
                JSONArray contragarantiasList = (JSONArray) socio.get("contragarantias");
                for (Object contra : contragarantiasList) {
                    JSONObject contragarantia = (JSONObject) contra;
                    montototal = montototal + (double) contragarantia.get("monto");
                }
            }
        }
        if (montooperacion > montototal) {
            contragarantiassuficientesFlag = false;
        }
        return contragarantiassuficientesFlag;
    }




    public boolean essocioparticipe(String CUITSocio) {
        String socioestado;
        boolean essocioparticipeFlag = false;
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            if (CUITSocio.equalsIgnoreCase(cuit)) {
                socioestado = socio.get("estado").toString();
                if (socioestado.equalsIgnoreCase("Pleno")) {
                    essocioparticipeFlag = true;
                }
            }
        }
        return essocioparticipeFlag;
    }


    public boolean lineatope(String CUITSocio, float montooperacion) {
        double tope = 0;
        boolean lineatopeFlag = true;
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            if (CUITSocio.equalsIgnoreCase(cuit)) {
                JSONObject lineadecredito = (JSONObject) socio.get("lineas-de-credito");
                tope = Double.parseDouble(lineadecredito.get("tope").toString());
            }
        }
        if ((double) montooperacion > tope) {
            lineatopeFlag = false;
        }
        return lineatopeFlag;
    }

}
