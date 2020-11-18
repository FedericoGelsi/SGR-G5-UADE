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
    private String filename = "./src/resources/operacioncontroller.json";
    private JSONObject jsonObject;

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
    public void altaFactura(){

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
}
