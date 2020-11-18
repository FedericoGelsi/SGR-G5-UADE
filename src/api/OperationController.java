package api;

import impl.LineaCredito;
import impl.OPTipo3;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public interface OperationController {
    boolean validarOperatoria();

    void nuevaOperacion(LineaCredito lineaCredito, String tipo, float monto, String CUIT);

    int calcularCuotasImpagas(LineaCredito lineaCredito, OPTipo3 unTipo3);

    void altaFactura();

    void altaDeuda();

    double calcularRiesgoVivo(String CUIT);

    double calcularTotalComputado(String CUIT);

    double comisionesDelDiaPorCheques(Date fecha);

    void operacionesAvaladasPorSocio(String CUITSocio, LocalDate FechaInicio, LocalDate FechaFin, JScrollPane viewport) throws Exception;

    double PromedioTasaDescuentoYTotalOperado(String tipoEmpresa, LocalDate FechaInicio, LocalDate FechaFin);

    ArrayList<Double> ConsultaSaldoMora(JSONObject socio);

    double ConsultaConsolidada(String CUITSocio);

    double comisionDiariaTOP(String TipoOP, LocalDate fecha) throws Exception;

    void agregarAporte(String CUIT, float Cantidad, File documento);
}
