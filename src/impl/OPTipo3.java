package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.Date;

public class OPTipo3 extends Operacion implements api.OPTipo3 {
    private String cantCuotas;
    private String banco;
    private float importeTotal;
    private float tasa;
    private LocalDate fechaAcreditacion;
    private String sistema;
    private String CUITSolicitante;
    private String estado;
    private String tipo;
    private int numerooperacion;

    /*======CONSTRUCTOR=======*/

    public OPTipo3(String CUITSolicitante, String cantCuotas, String banco, float importeTotal, float tasa, String sistema, LocalDate fechaAcreditacion, String estado, String tipo,int numerooperacion) {
        super(CUITSolicitante);
        this.cantCuotas = cantCuotas;
        this.banco = banco;
        this.importeTotal = importeTotal;
        this.tasa = tasa;
        this.sistema = sistema;
        this.fechaAcreditacion = fechaAcreditacion;
        this.CUITSolicitante = CUITSolicitante;
        this.estado = estado;
        this.tipo = tipo;
        this.numerooperacion = numerooperacion;
    }

    public OPTipo3(JSONObject jsonOPT3) {
        super(jsonOPT3);
        this.cantCuotas = (String) jsonOPT3.get("cantcuotas");
        this.banco = (String) jsonOPT3.get("banco");
        this.importeTotal = Integer.parseInt(jsonOPT3.get("importetotal").toString());
        this.tasa = (float) jsonOPT3.get("tasa");
        this.sistema = (String) jsonOPT3.get("sistema");
        this.fechaAcreditacion = (LocalDate) jsonOPT3.get("fecha-acreditación");
        this.CUITSolicitante = (String) jsonOPT3.get("CUITSocio");
        this.estado = (String) jsonOPT3.get("estado");
        this.tipo = (String) jsonOPT3.get("tipo");
        this.numerooperacion = Integer.parseInt(jsonOPT3.get("numerooperacion").toString());
    }
    /*======GETTERS=======*/

    @Override
    public String getCantCuotas() {
        return cantCuotas;
    }

    @Override
    public String getBanco() {
        return banco;
    }

    @Override
    public float getImporteTotal() {
        return importeTotal;
    }

    @Override
    public float getTasa() {
        return tasa;
    }

    @Override
    public LocalDate getFechaAcreditacion() {
        return fechaAcreditacion;
    }

    @Override
    public String getSistema() {
        return sistema;
    }

    /*======CLASS FUNCTIONS=======*/

    @Override
    public int getCuotasImpagas(){
        return 0;
    }

    @Override
    public void setFechaMonetizado(LocalDate fechaMonetizado) {
    }

    @Override
    public String getCUITSolicitante() {
        return CUITSolicitante;
    }

    public String getEstado() {
        return estado;
    }

    public String getTipo() {
        return tipo;
    }

    public int getNumerooperacion() {
        return numerooperacion;
    }

    public JSONObject toJSON(){
        JSONObject OP3 = new JSONObject();
        OP3.put("cantcuotas", this.cantCuotas);
        OP3.put("banco", this.banco);
        OP3.put("importetotal", this.importeTotal);
        OP3.put("tasa", this.tasa);
        OP3.put("sistema", this.sistema);
        OP3.put("fecha-acreditacion", this.fechaAcreditacion.toString());
        OP3.put("CUITSocio",this.CUITSolicitante);
        OP3.put("estado", this.estado);
        OP3.put("tipo", this.tipo);
        OP3.put("numerooperacion",this.numerooperacion);
        return OP3;
    }
}
