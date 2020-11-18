package impl;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import api.Desembolso;
import api.OPTipo2;
import api.Socio_Participe;
import org.json.simple.JSONArray;

import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import api.API_JSONHandler;
import impl.JSONHandler;
import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

import static javax.swing.JOptionPane.showMessageDialog;

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

    public String fechavshoytarjeta(YearMonth fecha) {
        LocalDate hoy = LocalDate.now(); //2020-09-13 --> 09/2
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        //LocalDate localDate = LocalDate.parse(hoy, formatter);
        Month MesHoy = hoy.getMonth();
        int AñoHoy = hoy.getYear();

        Month Mes = fecha.getMonth();
        int Año = fecha.getYear();

        if (Año < AñoHoy) {
            return "Menor";
        }
        if (Año == AñoHoy && Mes.compareTo(MesHoy) < 0) {
            return "Menor";
        } else {
            return "Mayor";
        }
    }

    public boolean tarjetavalida(String tarjeta, JLabel tipo) {
        String[] tarjetaseparada = tarjeta.split("-");
        boolean tarjetaValidaFlag = true;
        for (int i = 0; i < tarjetaseparada.length; i++) {
            if (tipo.getText().equals("VISA") || tipo.getText().equals("MASTERCARD")){
                if (tarjetaseparada[i].length() != 4 || !esnumerico(tarjetaseparada[i])) {
                    tarjetaValidaFlag = false;
                }
            }

        }
        if (tipo.getText().equals("AMERICAN")){
            if (tarjetaseparada[0].length() != 4 || !esnumerico(tarjetaseparada[0])) {
                tarjetaValidaFlag = false;
            }
            if (tarjetaseparada[1].length() != 6 || !esnumerico(tarjetaseparada[1])) {
                tarjetaValidaFlag = false;
            }
            if (tarjetaseparada[2].length() != 5 || !esnumerico(tarjetaseparada[2])) {
                tarjetaValidaFlag = false;
            }

        }
        return tarjetaValidaFlag;
    }

    public boolean isMaster(String tarjeta) {
        boolean tarjetaValidaFlag = true;
        if (tarjeta.length() == 19) {
            if (tarjeta.substring(0, 2).equals("55") || tarjeta.substring(0, 2).equals("54") || tarjeta.substring(0, 2).equals("53")
                    || tarjeta.substring(0, 2).equals("52") || tarjeta.substring(0, 2).equals("51")) {
                tarjetaValidaFlag = true;
            }
            else{
                tarjetaValidaFlag = false;
            }
            if(tarjeta.contains("-")){
                String[] tarjetaseparada = tarjeta.split("-");
                for (int i = 0; i < 4; i++) {
                    if (tarjetaseparada[i].length() != 4 || !esnumerico(tarjetaseparada[i])) {
                        tarjetaValidaFlag = false;
                    }
                }
            }

        }
        else {
            tarjetaValidaFlag = false;
        }
        return tarjetaValidaFlag;
    }

    public boolean isVisa (String tarjeta){
        boolean tarjetaValidaFlag = true;
        if (!tarjeta.isEmpty()){
            if (tarjeta.charAt(0) != '4') {
                tarjetaValidaFlag = false;
            }
            if (tarjeta.charAt(0) == '4' && tarjeta.length() != 19){
                tarjetaValidaFlag = false;
            }
            if(tarjeta.contains("-") && tarjeta.length() == 19){
                String[] tarjetaseparada = tarjeta.split("-");
                for (int i = 0; i < 4; i++) {
                    if (tarjetaseparada[i].length() != 4 || !esnumerico(tarjetaseparada[i])) {
                        tarjetaValidaFlag = false;
                    }
                }
            }

        }
        else{
            tarjetaValidaFlag = false;
        }
            return tarjetaValidaFlag;
        }

    public boolean isAmerican (String tarjeta){
        boolean tarjetaValidaFlag = true;
        if (!tarjeta.isEmpty()){
            if (tarjeta.length() != 17){
                tarjetaValidaFlag = false;
            }
            if(tarjeta.contains("-")){
                String[] tarjetaseparada = tarjeta.split("-");
                if (tarjetaseparada[0].length() != 4 || !esnumerico(tarjetaseparada[0])) {
                    tarjetaValidaFlag = false;
                }
                if (tarjetaseparada[1].length() != 6 || !esnumerico(tarjetaseparada[1])) {
                    tarjetaValidaFlag = false;
                }
                if (tarjetaseparada[2].length() != 5 || !esnumerico(tarjetaseparada[2])) {
                    tarjetaValidaFlag = false;
                }
            }
        }
        else{
            tarjetaValidaFlag = false;
        }
        return tarjetaValidaFlag;

    }

    //Chequea que el formato de CUIT ingresado sea valido y que los datos ingresads sean numericos
    @Override
    public boolean CUITValido(String CUIT) {
        String[] cuitseparado = CUIT.split("-");
        boolean CUITValidoflag = true;
        if ( Arrays.stream(cuitseparado).count() == 3) {
            if (cuitseparado[0].length() != 2 ||  esnumerico(cuitseparado[0]) != true) {
                CUITValidoflag = false;
            }
            if (cuitseparado[1].length() != 8 ||  esnumerico(cuitseparado[1]) != true) {
                CUITValidoflag = false;
            }
            if (cuitseparado[2].length() != 1 || esnumerico(cuitseparado[2]) != true) {
                CUITValidoflag = false;
            }
        }else {
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
    public boolean fechavalida(String fechacheck){
        DateTimeFormatter formato = DateTimeFormatter.ISO_LOCAL_DATE;
        try{
            LocalDate.parse(fechacheck, formato);
        }catch(DateTimeParseException excep){
            showMessageDialog(null, "Ingrese una fecha válida.\nEl formato debe ser YYYY-MM-DD");
            return false;
        }
        return true;
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
                    totalimpago = totalimpago + Double.parseDouble(factura.get("montofactura").toString());
                }
            }
        }
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            if (CUITSocio.equalsIgnoreCase(cuit)) {
                JSONObject lineadecredito = (JSONObject) socio.get("lineas-de-credito");
                tope = Double.parseDouble(lineadecredito.get("tope").toString());
            }
        }
        if (totalimpago > (tope * 0.1)) {
            debefacturasflag = true;
        }
        return debefacturasflag;
    }

    public boolean operacionvsfdr(double montototal) throws Exception {
        jsonObjectfdr = (JSONObject) file.readJson(filenamefdr);
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

    public int crearOT1(LocalDate FDV, String Banco, int NDC, String CUITF, float TDD, String CUITS, String tipo, float importetotal, String estado) throws Exception {
        jsonObjectOPC = (JSONObject) file.readJson(filenamefact);
        JSONArray operacionesList = (JSONArray) jsonObjectOPC.get("operaciones");
        int contador = operacionesList.size();

        // Pide el monto de la linea de credito y el tope
        double tope = 0;
        double monto_utilizado = 0;
        double montohabilitado = 0;
        double montodeuda = 0;
        LocalDate hoy = LocalDate.now();
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            if (CUITS.equalsIgnoreCase(cuit)) {
                JSONObject lineadecredito = (JSONObject) socio.get("lineas-de-credito");
                tope = Double.parseDouble(lineadecredito.get("tope").toString());
                monto_utilizado = Double.parseDouble(lineadecredito.get("monto-utilizado").toString());
                montohabilitado = (tope - monto_utilizado);
                if (importetotal > montohabilitado && importetotal <= tope) {
                    lineadecredito.put("monto-utilizado", tope);
                    int contadordeuda = 0;
                    JSONArray deudasList = (JSONArray) socio.get("deudas");

                    montodeuda = importetotal - montohabilitado;
                    Deuda nuevaDeuda = new Deuda(montodeuda, CUITS, deudasList.size(), montodeuda * 0.05, hoy, true);
                    JSONArray desembolsos = (JSONArray) jsonObject.get("desembolsos");
                    Desembolso ndesembolso = new impl.Desembolso(desembolsos.size(), CUITS, socio.get("razon-social").toString(), montodeuda );
                    desembolsos.add(ndesembolso.toJSON());
                    JSONObject deuda1 = nuevaDeuda.toJSON();
                    deudasList.add(deuda1);
                    socio.put("deudas", deudasList);
                    jsonObject.put("socios-participes", socioList);
                    jsonObject.put("desembolsos",desembolsos);
                    file.writeJson(filename, jsonObject);
                } else {
                    lineadecredito.put("monto-utilizado", monto_utilizado + (double) importetotal);
                    file.writeJson(filename, jsonObject);
                }
            }
        }

        api.OPTipo1 nuevaOT1 = new impl.OPTipo1(FDV, Banco, NDC, CUITF, TDD, CUITS, tipo, importetotal, estado, contador);
        JSONObject operacion1 = nuevaOT1.toJSON();
        guardarDatos(operacion1);

        JSONArray certList = (JSONArray) jsonObjectOPC.get("certificado-de-garantia");
        int contadorc = 100;
        if (certList != null) {
            for (Object cert : certList) {
                JSONObject idc = (JSONObject) cert;
                contadorc = 1 + Integer.parseInt(idc.get("idcertificado").toString());
            }
        }

        CertificadoDeGarantia nuevoCDG = new CertificadoDeGarantia(CUITS, contadorc, contador);
        JSONObject CDG = nuevoCDG.toJSON();
        guardarDatosCDG(CDG);

        jsonObjectOPC = (JSONObject) file.readJson(filenamefact);
        JSONArray sociocertestadolist = (JSONArray) jsonObjectOPC.get("operaciones");
        for (Object sce : sociocertestadolist) {
            JSONObject scejo = (JSONObject) sce;
            int nop = Integer.parseInt(scejo.get("numerooperacion").toString());
            if (contador == nop) {
                scejo.put("estado", "Con certificado emitido");
                file.writeJson(filenamefact, jsonObjectOPC);
            }
        }
        return contadorc;
    }

    @Override
    public int crearOT3(String CDC, String Banco, float Importe, float Tasa, String sist, LocalDate FDA, String CUIT, String estado, String tipo) throws Exception {
        jsonObjectOPC = (JSONObject) file.readJson(filenamefact);
        JSONArray operacionesList = (JSONArray) jsonObjectOPC.get("operaciones");
        int contador = operacionesList.size();

        // Pide el monto de la linea de credito y el tope
        double tope = 0;
        double monto_utilizado = 0;
        double montohabilitado = 0;
        double montodeuda = 0;
        LocalDate hoy = LocalDate.now();
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            if (CUIT.equalsIgnoreCase(cuit)) {
                JSONObject lineadecredito = (JSONObject) socio.get("lineas-de-credito");
                tope = Double.parseDouble(lineadecredito.get("tope").toString());
                monto_utilizado = Double.parseDouble(lineadecredito.get("monto-utilizado").toString());
                montohabilitado = (tope - monto_utilizado);
                if (Importe > montohabilitado && Importe <= tope) {
                    lineadecredito.put("monto-utilizado", tope);
                    JSONArray deudasList = (JSONArray) socio.get("deudas");
                    montodeuda = Importe - montohabilitado;
                    Deuda nuevaDeuda = new Deuda(montodeuda, CUIT, deudasList.size(), montodeuda * 0.05, hoy, true);
                    JSONObject deuda1 = nuevaDeuda.toJSON();
                    JSONArray desembolsos = (JSONArray) jsonObject.get("desembolsos");
                    Desembolso ndesembolso = new impl.Desembolso(desembolsos.size(), CUIT, socio.get("razon-social").toString(), montodeuda );
                    desembolsos.add(ndesembolso.toJSON());
                    deudasList.add(deuda1);
                    socio.put("deudas", deudasList);
                    jsonObject.put("socios-participes", socioList);
                    jsonObject.put("desembolsos",desembolsos);
                    file.writeJson(filename, jsonObject);
                } else {

                    lineadecredito.put("monto-utilizado", monto_utilizado + (double) Importe);
                    file.writeJson(filename, jsonObject);
                }
            }
        }

        api.OPTipo3 nuevaOT3 = new impl.OPTipo3(CUIT, CDC, Banco, Importe, Tasa, sist, FDA, estado, tipo, contador);
        JSONObject operacion3 = nuevaOT3.toJSON();
        guardarDatos(operacion3);

        JSONArray certList = (JSONArray) jsonObjectOPC.get("certificado-de-garantia");
        int contadorc = 100;
        if (certList != null) {
            for (Object cert : certList) {
                JSONObject idc = (JSONObject) cert;
                contadorc = 1 + Integer.parseInt(idc.get("idcertificado").toString());
            }
        }

        CertificadoDeGarantia nuevoCDG = new CertificadoDeGarantia(CUIT, contadorc, contador);
        JSONObject CDG = nuevoCDG.toJSON();
        guardarDatosCDG(CDG);

        jsonObjectOPC = (JSONObject) file.readJson(filenamefact);
        JSONArray sociocertestadolist = (JSONArray) jsonObjectOPC.get("operaciones");
        for (Object sce : sociocertestadolist) {
            JSONObject scejo = (JSONObject) sce;
            int nop = Integer.parseInt(scejo.get("numerooperacion").toString());
            if (contador == nop) {
                scejo.put("estado", "Con certificado emitido");
                file.writeJson(filenamefact, jsonObjectOPC);
            }
        }
        return contadorc;
    }

    public int crearOT2(String empresa, double importetotalop2, String fechavencimiento, String CUITSocio, int numerotarjeta, String nombretarjeta, String estado, int codigoseguridad, String tipo, String nombrempresa) throws Exception {
        jsonObjectOPC = (JSONObject) file.readJson(filenamefact);
        JSONArray operacionesList = (JSONArray) jsonObjectOPC.get("operaciones");
        int contador = operacionesList.size();


        // Pide el monto de la linea de credito y el tope
        double tope = 0;
        double monto_utilizado = 0;
        double montohabilitado = 0;
        double montodeuda = 0;
        LocalDate hoy = LocalDate.now();
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            if (CUITSocio.equalsIgnoreCase(cuit)) {
                JSONObject lineadecredito = (JSONObject) socio.get("lineas-de-credito");
                tope = Double.parseDouble(lineadecredito.get("tope").toString());
                monto_utilizado = Double.parseDouble(lineadecredito.get("monto-utilizado").toString());
                montohabilitado = (tope - monto_utilizado);
                if (importetotalop2 > montohabilitado && importetotalop2 <= tope) {
                    lineadecredito.put("monto-utilizado", tope);
                    JSONArray deudasList = (JSONArray) socio.get("deudas");
                    montodeuda = importetotalop2 - montohabilitado;
                    Deuda nuevaDeuda = new Deuda(montodeuda, CUITSocio, deudasList.size(), montodeuda * 0.05, hoy, true);
                    JSONArray desembolsos = (JSONArray) jsonObject.get("desembolsos");
                    Desembolso ndesembolso = new impl.Desembolso(desembolsos.size(), CUITSocio, socio.get("razon-social").toString(), montodeuda );
                    desembolsos.add(ndesembolso.toJSON());
                    deudasList.add(nuevaDeuda.toJSON());
                    socio.put("deudas", deudasList);
                    jsonObject.put("socios-participes", socioList);
                    jsonObject.put("desembolsos",desembolsos);
                    file.writeJson(filename, jsonObject);
                } else {
                    lineadecredito.put("monto-utilizado", monto_utilizado + (double) importetotalop2);
                    file.writeJson(filename, jsonObject);
                }
            }
        }
        OPTipo2 nuevaOT2 = new impl.OPTipo2(fechavencimiento, numerotarjeta, CUITSocio, tipo, importetotalop2, estado, nombretarjeta, contador, nombrempresa, codigoseguridad);
        JSONObject operacion2 = nuevaOT2.toJSON();
        guardarDatos(operacion2);

        JSONArray certList = (JSONArray) jsonObjectOPC.get("certificado-de-garantia");
        int contadorc = 100;
        if (certList != null) {
            for (Object cert : certList) {
                JSONObject idc = (JSONObject) cert;
                contadorc = 1 + Integer.parseInt(idc.get("idcertificado").toString());
            }
        }

        CertificadoDeGarantia nuevoCDG = new CertificadoDeGarantia(CUITSocio, contadorc, contador);
        JSONObject CDG = nuevoCDG.toJSON();
        guardarDatosCDG(CDG);

        jsonObjectOPC = (JSONObject) file.readJson(filenamefact);
        JSONArray sociocertestadolist = (JSONArray) jsonObjectOPC.get("operaciones");
        for (Object sce : sociocertestadolist) {
            JSONObject scejo = (JSONObject) sce;
            int nop = Integer.parseInt(scejo.get("numerooperacion").toString());
            if (contador == nop) {
                scejo.put("estado", "Con certificado emitido");
                file.writeJson(filenamefact, jsonObjectOPC);
            }
        }
        return contadorc;
    }


    public double nuevacomision(int numerocertificado) throws Exception {

        JSONArray certList = (JSONArray) jsonObjectOPC.get("certificado-de-garantia");
        int numerocertaux;
        int numerop = 0;
        String tipoop = "";
        double porcentajecomision = 0;
        double importetotal = 0;

        if (certList != null) {
            for (Object cert : certList) {
                JSONObject idc = (JSONObject) cert;
                numerocertaux = Integer.parseInt(idc.get("idcertificado").toString());
                if (numerocertaux == numerocertificado) {
                    numerop = Integer.parseInt(idc.get("numero-operacion").toString());
                }
            }
            jsonObjectOPC = (JSONObject) file.readJson(filenamefact);
            JSONArray sociocertestadolist = (JSONArray) jsonObjectOPC.get("operaciones");
            for (Object sce : sociocertestadolist) {
                JSONObject scejo = (JSONObject) sce;
                int nop = Integer.parseInt(scejo.get("numerooperacion").toString());
                if (numerop == nop) {
                    scejo.put("estado", "Monetizado");
                    tipoop = scejo.get("tipo").toString();
                    importetotal = (double) scejo.get("importetotal");
                    file.writeJson(filenamefact, jsonObjectOPC);
                }
            }
            if (tipoop == "Cheque Propio" || tipoop == "Cheque de terceros" || tipoop == "Pagare Bursatil" || tipoop == "Cuenta Corriente" || tipoop == "Tarjeta de Credito") {
                porcentajecomision = 3;
            } else {
                porcentajecomision = 4;
            }
        }


        JSONArray comList = (JSONArray) jsonObjectOPC.get("comision");
        double comisiontotal = importetotal * (porcentajecomision / 100);
        Comision nuevoCOM = new Comision(comList.size(), "Calculada", porcentajecomision, numerop, tipoop, comisiontotal);
        JSONObject COM = nuevoCOM.toJSON();
        guardarDatoscomision(COM);

        return comisiontotal;
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

    public void guardarDatosDeuda(JSONObject objeto) throws Exception {
        String filename = "./src/resources/socios.json";
        API_JSONHandler file = new JSONHandler();
        JSONObject jsonObject = (JSONObject) file.readJson(filename);
        JSONArray DeudaList = (JSONArray) jsonObject.get("deudas");
        DeudaList.add(objeto);
        jsonObject.put("deudas", DeudaList);
        file.writeJson(filename, jsonObject);
    }

    public void guardarDatosCDG(JSONObject objeto) throws Exception {
        String filename = "./src/resources/operacioncontroller.json";
        API_JSONHandler file = new JSONHandler();
        JSONObject jsonObject = (JSONObject) file.readJson(filename);
        JSONArray CDGList = (JSONArray) jsonObject.get("certificado-de-garantia");
        CDGList.add(objeto);
        jsonObject.put("certificado-de-garantia", CDGList);
        file.writeJson(filename, jsonObject);
    }

    public void guardarDatoscomision(JSONObject objeto) throws Exception {
        String filename = "./src/resources/operacioncontroller.json";
        API_JSONHandler file = new JSONHandler();
        JSONObject jsonObject = (JSONObject) file.readJson(filename);
        JSONArray ComisionList = (JSONArray) jsonObject.get("comision");
        ComisionList.add(objeto);
        jsonObject.put("comision", ComisionList);
        file.writeJson(filename, jsonObject);
    }


    public boolean check_deuda(String CUIT) {
        double montodeuda = 0;
        boolean existedeuda = false;
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            if (CUIT.equalsIgnoreCase(cuit)) {
                JSONArray deudasList = (JSONArray) socio.get("deudas");
                for (Object mon : deudasList) {
                    JSONObject monto = (JSONObject) mon;
                    montodeuda = Double.parseDouble(monto.get("monto").toString());
                }
                if (montodeuda > 0) {
                    existedeuda = true;
                    break;
                }
            }
        }
        return existedeuda;
    }

    public int getDayNumberNew(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getValue();
    }


    public void altaFacturas() throws Exception {
        int day = getDayNumberNew(LocalDate.now());
        double monto_com = 0;
        if (day == 3) { // Cambiar a 1
            jsonObjectOPC = (JSONObject) file.readJson(filenamefact);
            JSONArray comisionList = (JSONArray) jsonObjectOPC.get("comision");
            JSONArray comisionListAux = new JSONArray();
            ArrayList<Comision> ComisionArrayList = new ArrayList<>();
            JSONArray operacionesList = (JSONArray) jsonObjectOPC.get("operaciones");
            ArrayList<Double> montoList = new ArrayList<>();
            ArrayList<String> CUITList = new ArrayList<>();
            ArrayList<Double> comisionMonto = new ArrayList<>();
            double comisiontotal = 0;
            int index = 0;
            double montooperado = 0;
            for (Object com : comisionList) {
                api.Comision Comision = new Comision((JSONObject) com);
                if (Comision.getEstado().equalsIgnoreCase("Calculada")) {
                    ComisionArrayList.add((impl.Comision) Comision);
                    Comision.setEstado("Facturada");
                    for (Object op : operacionesList) {
                        JSONObject operacion = (JSONObject) op;
                        String cuitsocio = operacion.get("CUITSocio").toString();
                        if (Comision.getNumeroOperacion() == Integer.parseInt(operacion.get("numerooperacion").toString())) {
                            if(!CUITList.contains(cuitsocio)){
                                CUITList.add(cuitsocio);
                                index = CUITList.indexOf(cuitsocio);
                                comisiontotal = Comision.getMontocomisiontotal();
                                montooperado = Double.parseDouble(operacion.get("importetotal").toString());
                            }else{
                                index = CUITList.indexOf(cuitsocio);
                                comisiontotal = comisionMonto.get(index) + Comision.getMontocomisiontotal();
                                montooperado = montoList.get(index)+Double.parseDouble(operacion.get("importetotal").toString());
                            }
                            comisionMonto.add(index,comisiontotal);
                            montoList.add(index, montooperado);
                        }
                    }
                }
                comisionListAux.add(Comision.toJSON());
            }

            jsonObjectOPC.put("comision", comisionListAux);
            JSONArray facturaslist = (JSONArray) jsonObjectOPC.get("facturas");
            for (int i = 0; i < CUITList.size(); i++) {
                Factura nuevaFactura = new Factura(facturaslist.size(), comisionMonto.get(i), "20-11111111-2", CUITList.get(i), "Impaga");
                facturaslist.add(nuevaFactura.toJSON());
            }
            jsonObjectOPC.put("facturas", facturaslist);
            file.writeJson(filenamefact, jsonObjectOPC);

            jsonObject = (JSONObject) file.readJson(filename);
            JSONArray sociosList = (JSONArray) jsonObject.get("socios-participes");
            for (Object sc : sociosList) {
                Socio_Participe socio = new impl.Socio_Participe((JSONObject) sc);
                for (int i = 0; i < CUITList.size(); i++) {
                    if (socio.getCUITSocio().equals(CUITList.get(i))){
                        JSONObject ldc = (JSONObject) sc;
                        JSONObject newmonto = (JSONObject) ldc.get("lineas-de-credito");
                        newmonto.put("monto-utilizado", Double.parseDouble(newmonto.get("monto-utilizado").toString()) - montoList.get(i));
                        ldc.put("lineas-de-credito",newmonto);
                    }
                }
            }
            jsonObject.put("socios-participes", sociosList);
            file.writeJson(filename, jsonObject);
        }
    }


        public ArrayList<String> ListaCUITAC (String CUIT){
            String CUITAccionista = "";
            ArrayList<String> MisAccionistasList = new ArrayList<>();

            String CUITAccionistasEllos = "";
            ArrayList<String> AccionistasOtro = new ArrayList<>();

            ArrayList<String> AccionistaCompartido = new ArrayList<>();

            JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
            for (Object obj : socioList) {
                JSONObject socio = (JSONObject) obj;
                String cuit = socio.get("cuit").toString();
                if (CUIT.equalsIgnoreCase(cuit)) {
                    JSONArray accionistasList = (JSONArray) socio.get("accionistas");
                    for (Object acc : accionistasList) {
                        JSONObject accionista = (JSONObject) acc;
                        CUITAccionista = accionista.get("cuit-accionista").toString();
                        MisAccionistasList.add(CUITAccionista);
                    }
                }
            }

            JSONArray socioListEllos = (JSONArray) jsonObject.get("socios-participes");
            for (Object obj : socioListEllos) {
                JSONObject socio = (JSONObject) obj;
                String cuit = socio.get("cuit").toString();
                if (!CUIT.equalsIgnoreCase(cuit)) {
                    JSONArray accionistasList = (JSONArray) socio.get("accionistas");
                    for (Object acc : accionistasList) {
                        JSONObject accionista = (JSONObject) acc;
                        CUITAccionistasEllos = accionista.get("cuit-accionista").toString();
                        AccionistasOtro.add(CUITAccionistasEllos);
                    }
                    String accionistaCUIT = "";
                    for (int i = 0; i < MisAccionistasList.size(); i++) {
                        accionistaCUIT = MisAccionistasList.get(i);
                        if (AccionistasOtro.contains(accionistaCUIT) && !AccionistaCompartido.contains(cuit)) {
                            AccionistaCompartido.add(cuit);
                        }
                    }

                }

            }
            return AccionistaCompartido;
        }

        public boolean Computar5FDRAc (ArrayList < String > AccionistasCompartidos, String CUIT,double IMPORTEOPERACION)
        {

            double totalcomputado = 0.0;
            String accionistaCUIT = "";
            String operacion_cuit = "";
            String estado_operacion = "";
            LocalDate fecha_vencimiento;
            String fechaAux = "";
            String tipo_operacion = "";
            double importetotal = 0.0;
            String cantidad_cuotas = "";
            boolean flag = false;


            for (int i = 0; i < AccionistasCompartidos.size(); i++) {
                System.out.println("Bokita");
                accionistaCUIT = AccionistasCompartidos.get(i);
                JSONArray operacionesList = (JSONArray) jsonObjectOPC.get("operaciones");
                for (Object ops : operacionesList) {
                    JSONObject operaciones = (JSONObject) ops;
                    operacion_cuit = operaciones.get("CUITSocio").toString();
                    if (accionistaCUIT.equalsIgnoreCase(operacion_cuit)) {
                        if (operaciones.get("tipo").equals("Tarjeta de Credito")){
                            fecha_vencimiento = LocalDate.parse(operaciones.get("fechavencimiento").toString().concat("-20"));
                            fechaAux = fechavshoy(fecha_vencimiento);
                            System.out.println(fechaAux);
                        }
                        else{
                        estado_operacion = operaciones.get("estado").toString();
                        fecha_vencimiento = LocalDate.parse(operaciones.get("fechavencimiento").toString());
                        fechaAux = fechavshoy(fecha_vencimiento);
                            System.out.println(fechaAux);
                        if (fechaAux.equalsIgnoreCase("Mayor") && estado_operacion.equalsIgnoreCase("Monetizada")) {
                            tipo_operacion = operaciones.get("tipo").toString();
                            importetotal = (double) operaciones.get("importetotal");

                            if (tipo_operacion.equalsIgnoreCase("Pagare Bursatil") || tipo_operacion.equalsIgnoreCase("Cheque de terceros") || tipo_operacion.equalsIgnoreCase("Cheque propio")) {
                                totalcomputado = totalcomputado + importetotal;

                            }
                            if (tipo_operacion.equalsIgnoreCase("Cuenta Corriente") || tipo_operacion.equalsIgnoreCase("Tarjeta de Credito")) {
                                totalcomputado = totalcomputado + importetotal;

                            }
                            if (tipo_operacion.equalsIgnoreCase("Prestamo")) {
                                totalcomputado = totalcomputado + importetotal;

                            }
                        }
                        }
                    }
                }

                JSONArray operacionesLista = (JSONArray) jsonObjectOPC.get("operaciones");
                for (Object ops : operacionesLista) {
                    JSONObject operaciones = (JSONObject) ops;
                    operacion_cuit = operaciones.get("CUITSocio").toString();
                    if (CUIT.equalsIgnoreCase(operacion_cuit)) {
                        estado_operacion = operaciones.get("estado").toString();
                        if (operaciones.get("tipo").equals("Tarjeta de Credito")){
                        fecha_vencimiento = LocalDate.parse(operaciones.get("fechavencimiento").toString().concat("-20"));
                        fechaAux = fechavshoy(fecha_vencimiento);
                            System.out.println(fechaAux);
                        estado_operacion = operaciones.get("estado").toString();
                        }
                        else
                        {
                            fecha_vencimiento = LocalDate.parse(operaciones.get("fechavencimiento").toString());
                            fechaAux = fechavshoy(fecha_vencimiento);
                            System.out.println(fechaAux);
                            estado_operacion = operaciones.get("estado").toString();
                        if (fechaAux.equalsIgnoreCase("Mayor") && estado_operacion.equalsIgnoreCase("Monetizada")) {
                            tipo_operacion = operaciones.get("tipo").toString();
                            importetotal = (double) operaciones.get("importetotal");
                            if (tipo_operacion.equalsIgnoreCase("Pagare Bursatil") || tipo_operacion.equalsIgnoreCase("Cheque de terceros") || tipo_operacion.equalsIgnoreCase("Cheque propio")) {
                                totalcomputado = totalcomputado + importetotal;

                            }
                            if (tipo_operacion.equalsIgnoreCase("Cuenta Corriente") || tipo_operacion.equalsIgnoreCase("Tarjeta de Credito")) {
                                totalcomputado = totalcomputado + importetotal;


                            }
                            if (tipo_operacion.equalsIgnoreCase("Prestamo")) {
                                totalcomputado = totalcomputado + importetotal;


                            }
                        }
                        }
                    }
                }
            }
            System.out.println(totalcomputado);
            if (IMPORTEOPERACION > totalcomputado && totalcomputado != 0) {
                flag = true;
                System.out.println(totalcomputado);
            }
            return flag;
        }


        public boolean fechavalidatarjeta (String fechacheck){
            String[] fechaseparada = fechacheck.split("/");
            boolean fechavalidaFlag = true;
            if (fechaseparada[0].length() != 2 || esnumerico(fechaseparada[0]) != true) {
                fechavalidaFlag = false;
            }
            if (fechaseparada[1].length() != 4 || esnumerico(fechaseparada[1]) != true) {
                fechavalidaFlag = false;
            }
            return fechavalidaFlag;
        }
    }




