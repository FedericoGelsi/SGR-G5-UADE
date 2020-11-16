package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.Date;

public class OPTipo3 extends Operacion implements api.OPTipo3 {
    private int cantCuotas;
    private String banco;
    private float importeTotal;
    private float tasa;
    private LocalDate fechaAcreditacion;
    private String sistema;

    /*======CONSTRUCTOR=======*/

    public OPTipo3(String CUITSolicitante, int cantCuotas, String banco, float importeTotal, float tasa, String sistema) {
        super(CUITSolicitante);
        this.cantCuotas = cantCuotas;
        this.banco = banco;
        this.importeTotal = importeTotal;
        this.tasa = tasa;
        this.sistema = sistema;
    }

    public OPTipo3(JSONObject jsonOPT3) {
        super(jsonOPT3);
        this.cantCuotas = Integer.parseInt(jsonOPT3.get("cantcuotas").toString());
        this.banco = (String) jsonOPT3.get("banco");
        this.importeTotal = Integer.parseInt(jsonOPT3.get("importetotal").toString());
        this.tasa = (float) jsonOPT3.get("tasa");
        this.sistema = (String) jsonOPT3.get("sistema");
    }
    /*======GETTERS=======*/

    @Override
    public int getCantCuotas() {
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
    public JSONObject toJSON(){
        JSONObject OP3 = new JSONObject();
        OP3.put("cantcuotas", this.cantCuotas);
        OP3.put("banco", this.banco);
        OP3.put("importetotal", this.importeTotal);
        OP3.put("tasa", this.tasa);
        OP3.put("sistema", this.sistema);
        return OP3;
    }
}
