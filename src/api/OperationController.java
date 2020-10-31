package api;

import impl.LineaCredito;
import impl.OPTipo3;

import java.io.File;
import java.util.Date;

public interface OperationController {
    boolean validarOperatoria();

    void nuevaOperacion(LineaCredito lineaCredito, String tipo, float monto, String CUIT);

    int calcularCuotasImpagas(LineaCredito lineaCredito, OPTipo3 unTipo3);

    void altaFactura();

    void altaDeuda();

    float calcularRiesgoVivo(String CUIT);

    float calcularTotalComputado(String CUIT);

    float comisionesDelDiaPorCheques(Date fecha);

    void operacionesAvaladasPorSocio(String CUITSocio, Date FechaInicio, Date FechaFin);

    float PromedioTasaDescuentoYTotalOperado(String tipoEmpresa, Date FechaInicio, Date FechaFin);

    float ConsultaSaldoMora(String CUITSocio);

    float ConsultaConsolidada(String CUITSocio);

    float comisiónSocioTipoOperacion(String CUIT, String TipoOP);

    void agregarAporte(String CUIT, float Cantidad, File documento);
}
