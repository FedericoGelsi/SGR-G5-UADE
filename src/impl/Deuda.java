package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deuda implements api.Deuda {
    private double monto;
    private String CUITDeudor;
    private int idDeuda;
    private double montoMora;
    private LocalDate fechaDeuda;
    private boolean aplicaMora;

    /*======CONSTRUCTOR=======*/

    public Deuda(double monto, String CUITDeudor, int idDeuda, double montoMora) {
        this.monto = monto;
        this.CUITDeudor = CUITDeudor;
        this.idDeuda = idDeuda;
        this.montoMora = montoMora;
    }

    public Deuda(JSONObject jsonDeuda){
        this.monto = (double) jsonDeuda.get("monto");
        this.CUITDeudor = (String) jsonDeuda.get("cuit-deudor");
        this.idDeuda = Integer.parseInt(jsonDeuda.get("id-deuda").toString()) ;
        this.montoMora = (double) jsonDeuda.get("monto-mora");
    }

    /*======GETTERS=======*/

    @Override
    public boolean isAplicaMora() {
        return aplicaMora;
    }

    @Override
    public double getMonto() {
        return monto;
    }

    @Override
    public String getCUITDeudor() {
        return CUITDeudor;
    }

    @Override
    public int getIdDeuda() {
        return idDeuda;
    }

    @Override
    public double getMontoMora() {
        return montoMora;
    }

    @Override
    public LocalDate getFechaDeuda() {
        return fechaDeuda;
    }

    /*======CLASS FUNCTIONS=======*/

    @Override
    public void retirarAporte(){

    }

    public JSONObject toJSON(){
        JSONObject deuda = new JSONObject();
        deuda.put("monto", this.monto);
        deuda.put("cuit-deudor", this.CUITDeudor);
        deuda.put("id-deuda", this.idDeuda);
        deuda.put("monto-mora", this.montoMora);
        return deuda;
    }

}
