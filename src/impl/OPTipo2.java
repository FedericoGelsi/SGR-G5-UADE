package impl;

import java.util.Date;

public class OPTipo2 extends Operacion implements api.OPTipo2 {
    private String nombreEmpresa;
    private float importeTotal;
    private Date fechaVencimiento;
    private String tipo;

    /*======CONSTRUCTOR=======*/

    public OPTipo2(String CUITSolicitante, String nombreEmpresa, float importeTotal, Date fechaVencimiento, String tipo) {
        super(CUITSolicitante);
        this.nombreEmpresa = nombreEmpresa;
        this.importeTotal = importeTotal;
        this.fechaVencimiento = fechaVencimiento;
        this.tipo = tipo;
    }

    /*======GETTERS=======*/

    @Override
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    @Override
    public float getImporteTotal() {
        return importeTotal;
    }

    @Override
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public String getTipo() {
        return tipo;
    }
}
