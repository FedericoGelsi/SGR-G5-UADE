package impl;

import org.json.simple.JSONArray;
import java.time.LocalDate;
import api.API_JSONHandler;
import impl.JSONHandler;
import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Date;

public class Verificaciones implements api.Verificaciones {

    private String filename = "./src/resources/socios.json";
    private API_JSONHandler file = new JSONHandler();
    private JSONObject jsonObject = (JSONObject) file.readJson(filename);

    private String filenamefact = "./src/resources/operacioncontroller.json";
    private JSONObject jsonObjectOPC = (JSONObject) file.readJson(filenamefact);

    private String filenamefdr = "./src/resources/FondodeRiesgo.json";
    private JSONObject jsonObjectfdr = (JSONObject) file.readJson(filenamefdr);

    public Verificaciones() throws Exception {
    }

    //Compara una fecha entregada por parametro contra la fecha actual y devuelve Menor si la fecha ingresada es en el
    // pasado, Mayor si la fecha ingresada es en el futuro o Hoy si la fecha ingresada es la actual.
    @Override
    public String fechavshoy(LocalDate fecha) {
        LocalDate hoy = LocalDate.now();
        int comparacion = fecha.compareTo(hoy);
        if (comparacion < 0) {
            return "Menor";
        }
        if (comparacion > 0) {
            return "Mayor";
        } else {
            return "Hoy";
        }
    }

    //Chequea que el formato de CUIT ingresado sea valido y que los datos ingresads sean numericos
    @Override
    public boolean CUITValido(String CUIT) {
        String[] cuitseparado = CUIT.split("-");
        boolean CUITValidoflag = true;
        if (cuitseparado[0].length() != 2 || esnumerico(cuitseparado[0]) != true) {
            CUITValidoflag = false;
        }
        if (cuitseparado[1].length() != 8 || esnumerico(cuitseparado[1]) != true) {
            CUITValidoflag = false;
        }
        if (cuitseparado[2].length() != 1 || esnumerico(cuitseparado[2]) != true) {
            CUITValidoflag = false;
        }
        return CUITValidoflag;
    }

    //Chequea que un String este compuesto unicamente por numeros
    @Override
    public boolean esnumerico(String datos) {
        String regex = "[0-9]+";
        boolean numerico = datos.matches(regex);
        return numerico;
    }

    //Chequea el formato de fecha en el String recibido y que los datos ingresados sean numericos
    @Override
    public boolean fechavalida(String fechacheck) {
        String[] fechaseparada = fechacheck.split("/");
        boolean fechavalidaFlag = true;
        if (fechaseparada[0].length() != 2 || esnumerico(fechaseparada[0]) != true) {
            fechavalidaFlag = false;
        }
        if (fechaseparada[1].length() != 2 || esnumerico(fechaseparada[1]) != true) {
            fechavalidaFlag = false;
        }
        if (fechaseparada[2].length() != 4 || esnumerico(fechaseparada[2]) != true) {
            fechavalidaFlag = false;
        }
        return fechavalidaFlag;
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
                if (fechavshoy(vigenciadate) == "Menor") {
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

    public boolean debefacturas(String CUITSocio) {
        double tope = 0;
        String estado;
        boolean debefacturasflag = false;
        double totalimpago = 0;
        String factcuit;
        JSONArray facturalist = (JSONArray) jsonObjectOPC.get("facturas");
        for (Object fact : facturalist) {
            JSONObject factura = (JSONObject) fact;
            factcuit = factura.get("CUITDestinatario").toString();
            if (CUITSocio.equalsIgnoreCase(factcuit)) {
                estado = factura.get("estado").toString();
                if (estado.equalsIgnoreCase("impago")) {
                    totalimpago = totalimpago + (double) factura.get("montofactura");
                }
            }
        }
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            if (CUITSocio.equalsIgnoreCase(cuit)) {
                JSONObject lineadecredito = (JSONObject) socio.get("lineas-de-credito");
                tope = (double) lineadecredito.get("tope");
            }
        }
        if (totalimpago > (tope * 0.1)) {
            debefacturasflag = true;
        }
        return debefacturasflag;
    }

    public boolean operacionvsfdr(double montototal) {
        double montofdr = 0;
        boolean operacionmenorfdr = true;
        JSONArray aporteslist = (JSONArray) jsonObjectfdr.get("aportes");
        for (Object ap : aporteslist) {
            JSONObject aporte = (JSONObject) ap;
            montofdr = montofdr + (double) aporte.get("montoaporte");
        }
        if (montototal > (montofdr * 0.05)) {
            operacionmenorfdr = false;
        }
        return operacionmenorfdr;
    }

    public void crearOT1(LocalDate FDV, String Banco, int NDC, String CUITF, float TDD, String CUITS, String tipo,float importetotal, String estado) throws Exception {
        api.OPTipo1 nuevaOT1 = new impl.OPTipo1(FDV, Banco, NDC, CUITF, TDD, CUITS, tipo,importetotal,estado);

        JSONObject operacion1 = nuevaOT1.toJSON();
        guardarDatos(operacion1);
    }

    public void guardarDatos(JSONObject objeto) throws Exception {
        String filename = "./src/resources/operacioncontroller.json";
        API_JSONHandler file = new JSONHandler();
        JSONObject jsonObject = (JSONObject) file.readJson(filename);
        JSONArray operacionesList = (JSONArray) jsonObject.get("operaciones");
        operacionesList.add(objeto);
        jsonObject.put("operaciones", operacionesList);
        file.writeJson(filename, jsonObject);
    }
    /* public void crearOT2(String nombreempresa, float importetotal, LocalDate fechavencimiento, String tipo) throws Exception {
        api.OPTipo2 nuevaOT2 = new impl.OPTipo2(nombreempresa, importetotal, fechavencimiento, tipo);

        JSONObject operacion1 = nuevaOT1.toJSON();
        guardarDatos(operacion1);
    }
    public void guardarDatos(JSONObject objeto) throws Exception {
        String filename = "./src/resources/operacioncontroller.json";
        API_JSONHandler file = new JSONHandler();
        JSONObject jsonObject = (JSONObject) file.readJson(filename);
        JSONArray operacionesList = (JSONArray) jsonObject.get("operaciones");
        operacionesList.add(objeto);
        jsonObject.put("operaciones", operacionesList);
        file.writeJson(filename, jsonObject);
    }*/
}



