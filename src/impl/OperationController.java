package impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class OperationController implements api.OperationController {
    private ArrayList<Factura> facturas;
    private ArrayList<Deuda> deudas;
    private ArrayList<Operacion> operaciones;

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
    public float calcularRiesgoVivo(String CUIT){
        return 0;
    }

    @Override
    public float calcularTotalComputado(String CUIT){
        return 0;
    }

    @Override
    public float comisionesDelDiaPorCheques(Date fecha){
        return 0;
    }

    @Override
    public void operacionesAvaladasPorSocio(String CUITSocio, Date FechaInicio, Date FechaFin){

    }

    @Override
    public float PromedioTasaDescuentoYTotalOperado(String tipoEmpresa, Date FechaInicio, Date FechaFin){
        return 0;
    }

    @Override
    public float ConsultaSaldoMora(String CUITSocio){
        return 0;
    }

    @Override
    public float ConsultaConsolidada(String CUITSocio){
        return 0;
    }

    @Override
    public float comisiónSocioTipoOperacion(String CUIT, String TipoOP){
        return 0;
    }

    @Override
    public void agregarAporte(String CUIT, float Cantidad, File documento){

    }
}
