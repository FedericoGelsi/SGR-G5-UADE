package impl;

import org.json.simple.JSONArray;

import java.lang.reflect.Array;
import java.time.DayOfWeek;
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

    public void crearOT1(LocalDate FDV, String Banco, int NDC, String CUITF, float TDD, String CUITS, String tipo, float importetotal, String estado) throws Exception {
        jsonObjectOPC = (JSONObject) file.readJson(filenamefact);
        JSONArray operacionesList = (JSONArray) jsonObjectOPC.get("operaciones");
        int contador = 0;
        for (Object op : operacionesList) {
            JSONObject id = (JSONObject) op;
            contador = 1 + Integer.parseInt(id.get("numerooperacion").toString());
        }

        // Pide el monto de la linea de credito y el tope
        double tope = 0;
        double monto_utilizado = 0;
        double montohabilitado = 0;
        double montodeuda = 0;
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            if (CUITS.equalsIgnoreCase(cuit)) {
                JSONObject lineadecredito = (JSONObject) socio.get("lineas-de-credito");
                tope = (double) lineadecredito.get("tope");
                monto_utilizado = (double) lineadecredito.get("monto-utilizado");
                montohabilitado = (tope - monto_utilizado);
                if (importetotal > montohabilitado && importetotal <= tope) {
                    lineadecredito.put("monto-utilizado", tope);
                    int contadordeuda = 0;
                    JSONArray deudasList = (JSONArray) jsonObject.get("deudas");
                    montodeuda = importetotal - montohabilitado;
                    if (deudasList != null) {
                        for (Object deu : operacionesList) {
                            JSONObject id = (JSONObject) deu;
                            contadordeuda = 1 + Integer.parseInt(id.get("id-deuda").toString());
                        }
                    }
                    Deuda nuevaDeuda = new Deuda(montodeuda, CUITS, contadordeuda, montodeuda * 0.05);
                    JSONObject deuda1 = nuevaDeuda.toJSON();
                    JSONArray DeudasList = new JSONArray();
                    DeudasList.add(deuda1);
                    socio.put("deudas", DeudasList);
                    jsonObject.put("socios-participes", socioList);
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
    }

    @Override
    public void crearOT3(String CDC, String Banco, float Importe, float Tasa, String sist, LocalDate FDA, String CUIT, String estado, String tipo) throws Exception {
        jsonObjectOPC = (JSONObject) file.readJson(filenamefact);
        JSONArray operacionesList = (JSONArray) jsonObjectOPC.get("operaciones");
        int contador = 0;
        if (operacionesList != null) {
            for (Object op : operacionesList) {
                JSONObject id = (JSONObject) op;
                contador = 1 + Integer.parseInt(id.get("numerooperacion").toString());
            }
        }

        // Pide el monto de la linea de credito y el tope
        double tope = 0;
        double monto_utilizado = 0;
        double montohabilitado = 0;
        double montodeuda = 0;
        JSONArray socioList = (JSONArray) jsonObject.get("socios-participes");
        for (Object obj : socioList) {
            JSONObject socio = (JSONObject) obj;
            String cuit = socio.get("cuit").toString();
            if (CUIT.equalsIgnoreCase(cuit)) {
                JSONObject lineadecredito = (JSONObject) socio.get("lineas-de-credito");
                tope = (double) lineadecredito.get("tope");
                monto_utilizado = (double) lineadecredito.get("monto-utilizado");
                montohabilitado = (tope - monto_utilizado);
                if (Importe > montohabilitado && Importe <= tope) {
                    lineadecredito.put("monto-utilizado", tope);
                    int contadordeuda = 0;
                    JSONArray deudasList = (JSONArray) jsonObject.get("deudas");
                    montodeuda = Importe - montohabilitado;
                    if (deudasList != null) {
                        for (Object deu : operacionesList) {
                            JSONObject id = (JSONObject) deu;
                            contadordeuda = 1 + Integer.parseInt(id.get("id-deuda").toString());
                        }
                    }
                    Deuda nuevaDeuda = new Deuda(montodeuda, CUIT, contadordeuda, montodeuda * 0.05);
                    JSONObject deuda1 = nuevaDeuda.toJSON();
                    JSONArray DeudasList = new JSONArray();
                    DeudasList.add(deuda1);
                    socio.put("deudas", DeudasList);
                    jsonObject.put("socios-participes", socioList);
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
        int contadorco = 1000;
        if (comList != null) {
            for (Object com : comList) {
                JSONObject comision = (JSONObject) com;
                contadorco = 1 + Integer.parseInt(comision.get("IDComision").toString());
            }
        }
        double comisiontotal = importetotal * (porcentajecomision / 100);
        System.out.println(comisiontotal);

        Comision nuevoCOM = new Comision(contadorco, "Calculada", porcentajecomision, numerop, tipoop, comisiontotal);
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
                    montodeuda = (double) monto.get("monto");
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
        System.out.println(day.getValue());
        return day.getValue();
    }



    public void crearFacturas() throws Exception {
        ArrayList<Double> montocomision = new ArrayList<Double>();
        jsonObjectOPC = (JSONObject) file.readJson(filenamefact);
        System.out.println("entro al metodo");
        int contador = 0;
        int contadora = 0;
        int day = getDayNumberNew(LocalDate.now());
        double monto_com=0;
        if (day == 2) { // Cambiar a 1
            System.out.println("entro al dia");
            String com_estado = "";
            JSONArray comisionList = (JSONArray) jsonObjectOPC.get("comision");
            for (Object com : comisionList) {
                System.out.println("entro al for");
                JSONObject comisionObject = (JSONObject) com;
                com_estado = comisionObject.get("Estado").toString();
                System.out.println(com_estado);
                if (com_estado.equalsIgnoreCase("Calculada")){
                    monto_com = (double) comisionObject.get("montocomisiontotal");
                    montocomision.add(monto_com);
                    contador++;
                    System.out.println(contador);
                    comisionObject.put("Estado", "Facturada");
                    file.writeJson(filenamefact, jsonObjectOPC);
                }
            }
            while (contador>0) {
                System.out.println(contador);
                Factura nuevaFactura = new Factura(0, montocomision.get(contadora), "20-11111111-2", "20-1155511-2", "Emitida");
                JSONObject Fact = nuevaFactura.toJSON();
                guardarDatosFactura(Fact);
                contadora++;
                contador--;
            }
        }
    }

    public void guardarDatosFactura(JSONObject objeto) throws Exception {
        String filename = "./src/resources/operacioncontroller.json";
        API_JSONHandler file = new JSONHandler();
        JSONObject jsonObject = (JSONObject) file.readJson(filename);
        JSONArray FactList = (JSONArray) jsonObject.get("facturas");
        FactList.add(objeto);
        jsonObject.put("facturas", FactList);
        file.writeJson(filename, jsonObject);
    }

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




