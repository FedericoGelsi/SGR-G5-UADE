package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.Date;

public class OPTipo2 extends Operacion implements api.OPTipo2 {
    private String nombreEmpresa;
    private float importeTotal;
    private LocalDate fechaVencimiento;
    private String tipo;

    /*======CONSTRUCTOR=======*/

    public OPTipo2(String CUITSolicitante, String nombreEmpresa, float importeTotal, LocalDate fechaVencimiento, String tipo) {
        super(CUITSolicitante);
        this.nombreEmpresa = nombreEmpresa;
        this.importeTotal = importeTotal;
        this.fechaVencimiento = fechaVencimiento;
        this.tipo = tipo;
    }

    public OPTipo2(JSONObject jsonOPT2) {
        super(jsonOPT2);
        this.fechaVencimiento = (LocalDate) jsonOPT2.get("fechavencimiento");
        this.nombreEmpresa = (String) jsonOPT2.get("nombreempresa");
        this.importeTotal = Integer.parseInt(jsonOPT2.get("importetotal").toString());
        this.tipo = (String) jsonOPT2.get("tipo");
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
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public void setFechaMonetizado(LocalDate fechaMonetizado) {

    }
    public JSONObject toJSON(){
        JSONObject OP2 = new JSONObject();
        OP2.put("fechavencimiento", this.fechaVencimiento);
        OP2.put("nombreempresa", this.nombreEmpresa);
        OP2.put("importetotal", this.importeTotal);
        OP2.put("tipo", this.tipo);
        return OP2;
    }
}
