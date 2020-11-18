package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;

public class OPTipo2 extends Operacion implements api.OPTipo2 {
    private String fechaVencimiento;
    private int numerotarjeta;
    private String CUITSocio;
    private String tipo;
    private double importetotal;
    private String estado;
    private int numerooperacion;
    private String nombreEmpresa;
    private int codigoseguridad;
    private String nombretarjeta;

    /*======CONSTRUCTOR=======*/

    public OPTipo2(String fechaVencimiento, int numerotarjeta, String CUITSocio, String tipo, double importeTotal, String estado, String nombretarjeta, int numerooperacion, String nombreEmpresa, int codigoseguridad) {
        super(CUITSocio);
        this.fechaVencimiento = fechaVencimiento;
        this.numerotarjeta = numerotarjeta;
        this.CUITSocio = CUITSocio;
        this.tipo = tipo;
        this.importetotal = importeTotal;
        this.estado = estado;
        this.numerooperacion = numerooperacion;
        this.nombreEmpresa = nombreEmpresa;
        this.codigoseguridad = codigoseguridad;
        this.nombretarjeta = nombretarjeta;
    }

    public OPTipo2(JSONObject jsonOPT2) {
        super(jsonOPT2);
        this.fechaVencimiento = (String) jsonOPT2.get("fechavencimiento");
        this.nombreEmpresa = (String) jsonOPT2.get("nombreempresa");
        this.importetotal = (double) jsonOPT2.get("importetotal");
        this.tipo = (String) jsonOPT2.get("tipo");
        this.numerotarjeta = Integer.parseInt(jsonOPT2.get("numerotarjeta").toString());
        this.CUITSocio = (String) jsonOPT2.get("CUITSocio").toString();
        this.estado = (String) jsonOPT2.get("estado");
        this.numerooperacion = Integer.parseInt(jsonOPT2.get("numerooperacion").toString());
        this.codigoseguridad = Integer.parseInt(jsonOPT2.get("codigoseguridad").toString());
        this.nombretarjeta = (String) jsonOPT2.get("nombretarjeta").toString();

    }
    /*======GETTERS=======*/

    @Override
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    @Override
    public double getImporteTotal() {
        return importetotal;
    }

    @Override
    public String getFechaVencimiento() {
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
        OP2.put("importetotal", this.importetotal);
        OP2.put("tipo", this.tipo);
        OP2.put("numerotarjeta", this.numerotarjeta);
        OP2.put("CUITSocio", this.CUITSocio);
        OP2.put("estado", this.estado);
        OP2.put("numerooperacion", this.numerooperacion);
        OP2.put("codigoseguridad", this.codigoseguridad);
        OP2.put("nombretarjeta", this.nombretarjeta);
        return OP2;
    }
}
