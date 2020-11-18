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

    void altaFactura() throws Exception;

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

    boolean operacionvsfdr(double montototal) throws Exception;

    int crearOT3(String CDC, String Banco, float Importe, float Tasa, String sist, LocalDate FDA, String CUIT, String estado, String tipo) throws Exception;

    int crearOT2(String empresa, double importetotalop2, String fechavencimiento, String CUITSocio, int numerotarjeta, String nombretarjeta, String estado, int codigoseguridad, String tipo, String nombrempresa) throws Exception;

    int crearOT1(LocalDate FDV, String Banco, int NDC, String CUITF, float TDD, String CUITS, String tipo, float importetotal, String estado) throws Exception;

    void guardarDatos(JSONObject objeto) throws Exception;

    void guardarDatosDeuda(JSONObject objeto) throws Exception;

    void guardarDatosCDG(JSONObject objeto) throws Exception;

    void guardarDatoscomision(JSONObject objeto) throws Exception;

    boolean check_deuda(String CUIT);

    double nuevacomision(int numerocertificado) throws Exception;

    ArrayList<String> ListaCUITAC (String CUIT);

    boolean Computar5FDRAc (ArrayList < String > AccionistasCompartidos, String CUIT,double IMPORTEOPERACION);

    boolean debefacturas(String CUITSocio);

}
