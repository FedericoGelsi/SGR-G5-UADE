package impl;

import api.API_JSONHandler;
import api.Comision;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

import static javax.swing.JOptionPane.showMessageDialog;

public class OperationController implements api.OperationController {
    private ArrayList<Factura> facturas;
    private ArrayList<Deuda> deudas;
    private ArrayList<Operacion> operaciones;
    private API_JSONHandler file = new JSONHandler();
    private String filename = "./src/resources/socios.json";

    private String filenamefact = "./src/resources/operacioncontroller.json";
    private JSONObject jsonObjectOPC = (JSONObject) file.readJson(filenamefact);

    private String filenamefdr = "./src/resources/FondodeRiesgo.json";
    private JSONObject jsonObjectfdr = (JSONObject) file.readJson(filenamefdr);
    private JSONObject jsonObject;

    private api.Verificaciones verificar = new impl.Verificaciones();

    public OperationController() throws Exception {
    }

    @Override
    public boolean validarOperatoria(){
        return true;
    }

    @Override
    public void nuevaOperacion(LineaCredito lineaCredito, String tipo, float monto, String CUIT){

    }

    @Override
    public int calcularCuotasImpagas(LineaCredito lineaCredito, OPTipo3 unTipo3){
        return 0;
    }

    @Override

    public void altaFactura() throws Exception {
        int day = verificar.getDayNumberNew(LocalDate.now());
        double monto_com = 0;
        if (day == 3) { // Cambiar a 1
            jsonObjectOPC = (JSONObject) file.readJson(filenamefact);
            JSONArray comisionList = (JSONArray) jsonObjectOPC.get("comision");
            JSONArray comisionListAux = new JSONArray();
            ArrayList<impl.Comision> ComisionArrayList = new ArrayList<>();
            JSONArray operacionesList = (JSONArray) jsonObjectOPC.get("operaciones");
            ArrayList<Double> montoList = new ArrayList<>();
            ArrayList<String> CUITList = new ArrayList<>();
            ArrayList<Double> comisionMonto = new ArrayList<>();
            double comisiontotal = 0;
            int index = 0;
            double montooperado = 0;
            for (Object com : comisionList) {
                api.Comision Comision = new impl.Comision((JSONObject) com);
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
                api.Socio_Participe socio = new impl.Socio_Participe((JSONObject) sc);
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

    @Override
    public void altaDeuda(){

    }

    @Override
    public double calcularRiesgoVivo(String CUIT){
        return 0;
    }

    @Override
    public double calcularTotalComputado(String CUIT){
        return 0;
    }

    @Override
    public double comisionesDelDiaPorCheques(Date fecha){
        return 0;
    }

    @Override
    public void operacionesAvaladasPorSocio(String CUITSocio, LocalDate FechaInicio, LocalDate FechaFin, JScrollPane viewport) throws Exception {
        jsonObject = (JSONObject) file.readJson(filename);
        JSONArray opList = (JSONArray) jsonObject.get("operaciones");
        String [] nombresColumnas = {"ID","CUIT","Tipo","Estado", "Monto","Banco", "Fecha Vencimiento"};
        DefaultTableModel modelo = new DefaultTableModel();
        for ( String column : nombresColumnas) {
            modelo.addColumn(column);
        }
        for (Object op : opList){
            JSONObject operacion = (JSONObject) op;
            LocalDate fechaop = LocalDate.parse(operacion.get("fechavencimiento").toString());
            String estadoop = operacion.get("estado").toString();
            if (fechaop.isBefore(FechaFin) && fechaop.isAfter(FechaInicio)){
                if (estadoop.equals("Monetizado")){
                    ArrayList data = new ArrayList<>();
                    data.add(operacion.get("numerooperacion"));
                    data.add(operacion.get("CUITSocio"));
                    data.add(operacion.get("tipo"));
                    data.add(operacion.get("estado"));
                    data.add(operacion.get("importetotal"));
                    if (operacion.get("banco").toString().isEmpty()) {
                        data.add("");
                    }else{
                        data.add(operacion.get("banco"));
                    }
                    data.add(operacion.get("fechavencimiento"));
                    modelo.addRow(data.toArray());
                }
            }
        }
        JTable accionistasTable = new JTable(modelo);
        viewport.setViewportView(accionistasTable);
        viewport.setVisible(true);

    }

    @Override
    public double PromedioTasaDescuentoYTotalOperado(String tipoEmpresa, LocalDate FechaInicio, LocalDate FechaFin){
        return 0;
    }

    @Override
    public ArrayList<Double> ConsultaSaldoMora(JSONObject socio){
        JSONArray deudas = (JSONArray) socio.get("deudas");
        ArrayList<Double> datos = new ArrayList<>();
        double moradiaria = 0;
        double moratotal = 0;
        for (Object d: deudas){
            api.Deuda deuda = new impl.Deuda((JSONObject) d);
            if (deuda.isAplicaMora()) {
                if (deuda.getFechaDeuda().isBefore(LocalDate.now())){
                    moratotal += (deuda.getMontoMora() * ChronoUnit.DAYS.between(deuda.getFechaDeuda(), LocalDate.now()));
                }
                moradiaria += deuda.getMontoMora();
            }
        }
        datos.add(moradiaria);
        datos.add(moratotal);
        return datos;
    }

    @Override
    public double ConsultaConsolidada(String CUITSocio){
        return 0;
    }

    @Override
    public double comisionDiariaTOP(String TipoOP, LocalDate fecha) throws Exception {
        jsonObject = (JSONObject) file.readJson(filename);
        JSONArray comisionList = (JSONArray) jsonObject.get("comision");
        double montocomisiones = 0;
        if (!comisionList.isEmpty()) {
            for (Object cm : comisionList) {
                Comision comision = new impl.Comision((JSONObject) cm);
                if (comision.getTipoOP().equals(TipoOP) && comision.getEstado().equals("Calculada")) {
                    if (fecha.equals(comision.getFechaCreacion())) {
                        montocomisiones += comision.getMontocomisiontotal();
                    }
                }
            }
            if (montocomisiones == 0){
                showMessageDialog(null, "No existen comisiones para ese tipo de operación en esta fecha.");
            }
        }else{
            showMessageDialog(null, "No existen comisiones.");
        }
        return montocomisiones;
    }

    @Override
    public void agregarAporte(String CUIT, float Cantidad, File documento){

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
                    api.Desembolso ndesembolso = new impl.Desembolso(desembolsos.size(), CUITS, socio.get("razon-social").toString(), montodeuda );
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
                    api.Desembolso ndesembolso = new impl.Desembolso(desembolsos.size(), CUIT, socio.get("razon-social").toString(), montodeuda );
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
                    api.Desembolso ndesembolso = new impl.Desembolso(desembolsos.size(), CUITSocio, socio.get("razon-social").toString(), montodeuda );
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
        api.OPTipo2 nuevaOT2 = new impl.OPTipo2(fechavencimiento, numerotarjeta, CUITSocio, tipo, importetotalop2, estado, nombretarjeta, contador, nombrempresa, codigoseguridad);
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
        impl.Comision nuevoCOM = new impl.Comision(comList.size(), "Calculada", porcentajecomision, numerop, tipoop, comisiontotal);
        JSONObject COM = nuevoCOM.toJSON();
        guardarDatoscomision(COM);

        return comisiontotal;
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
                        System.out.println(cuit);
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
            accionistaCUIT = AccionistasCompartidos.get(i);
            JSONArray operacionesList = (JSONArray) jsonObjectOPC.get("operaciones");
            for (Object ops : operacionesList) {
                JSONObject operaciones = (JSONObject) ops;
                operacion_cuit = operaciones.get("CUITSocio").toString();
                if (accionistaCUIT.equalsIgnoreCase(operacion_cuit)) {
                    estado_operacion = operaciones.get("estado").toString();
                    fecha_vencimiento = LocalDate.parse(operaciones.get("fechavencimiento").toString());
                    fechaAux = verificar.fechavshoy(fecha_vencimiento);
                    System.out.println(fechaAux);
                    System.out.println(estado_operacion);
                    if (fechaAux.equalsIgnoreCase("Mayor") && estado_operacion.equalsIgnoreCase("Monetizada")) {
                        tipo_operacion = operaciones.get("tipo").toString();
                        importetotal = (double) operaciones.get("importetotal");
                        System.out.println(importetotal);
                        if (tipo_operacion.equalsIgnoreCase("Pagare Bursatil") || tipo_operacion.equalsIgnoreCase("Cheque de terceros") || tipo_operacion.equalsIgnoreCase("Cheque propio")) {
                            totalcomputado = totalcomputado + importetotal;
                            System.out.println(totalcomputado);

                        }
                        if (tipo_operacion.equalsIgnoreCase("Cuenta Corriente") || tipo_operacion.equalsIgnoreCase("Tarjeta de Credito")) {
                            totalcomputado = totalcomputado + importetotal;
                            System.out.println(totalcomputado);

                        }
                        if (tipo_operacion.equalsIgnoreCase("Prestamo")) {
                            totalcomputado = totalcomputado + importetotal;
                            System.out.println(totalcomputado);

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
                    fecha_vencimiento = LocalDate.parse(operaciones.get("fechavencimiento").toString());
                    fechaAux = verificar.fechavshoy(fecha_vencimiento);
                    System.out.println(fechaAux);
                    System.out.println(estado_operacion);
                    if (fechaAux.equalsIgnoreCase("Mayor") && estado_operacion.equalsIgnoreCase("Monetizada")) {
                        tipo_operacion = operaciones.get("tipo").toString();
                        importetotal = (double) operaciones.get("importetotal");
                        System.out.println(importetotal);
                        if (tipo_operacion.equalsIgnoreCase("Pagare Bursatil") || tipo_operacion.equalsIgnoreCase("Cheque de terceros") || tipo_operacion.equalsIgnoreCase("Cheque propio")) {
                            totalcomputado = totalcomputado + importetotal;
                            System.out.println(totalcomputado);

                        }
                        if (tipo_operacion.equalsIgnoreCase("Cuenta Corriente") || tipo_operacion.equalsIgnoreCase("Tarjeta de Credito")) {
                            totalcomputado = totalcomputado + importetotal;
                            System.out.println(totalcomputado);

                        }
                        if (tipo_operacion.equalsIgnoreCase("Prestamo")) {
                            totalcomputado = totalcomputado + importetotal;
                            System.out.println(totalcomputado);

                        }
                    }
                }
            }
        }
        System.out.println(totalcomputado);
        if (IMPORTEOPERACION > totalcomputado && totalcomputado != 0) {
            flag = true;
        }
        return flag;
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
}
