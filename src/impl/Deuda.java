package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Deuda implements api.Deuda {
    private double monto;
    private String CUITDeudor;
    private String idDeuda;
    private double montoMora;
    private LocalDate fechaDeuda;
    private boolean aplicaMora;

    /*======CONSTRUCTOR=======*/

    public Deuda(double monto, String CUITDeudor, String idDeuda, double montoMora) {
        this.monto = monto;
        this.CUITDeudor = CUITDeudor;
        this.idDeuda = idDeuda;
        this.montoMora = montoMora;
        this.fechaDeuda = LocalDate.now();
    }

    public Deuda(JSONObject jsonDeuda){
        this.monto = Double.parseDouble(jsonDeuda.get("monto").toString());
        this.CUITDeudor = (String) jsonDeuda.get("cuit-deudor");
        this.idDeuda = jsonDeuda.get("id-deuda").toString();
        this.montoMora = Double.parseDouble(jsonDeuda.get("monto-mora").toString());
        this.aplicaMora = (boolean) jsonDeuda.get("aplica-mora");
        this.fechaDeuda = LocalDate.parse(jsonDeuda.get("fecha-deuda").toString());
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
    public String getIdDeuda() {
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
        deuda.put("fecha-deuda", this.fechaDeuda.toString());
        deuda.put("id-deuda", this.idDeuda);
        deuda.put("aplica-mora", this.aplicaMora);
        deuda.put("monto-mora", this.montoMora);
        return deuda;
    }

    public double calcularSubtotal(){
        double subtotal = this.getMonto();
        if (this.aplicaMora) {
            if (this.fechaDeuda.isBefore(LocalDate.now())) {
                subtotal = this.monto + (this.montoMora * ChronoUnit.DAYS.between(this.fechaDeuda,LocalDate.now()));
            }
        }
        return subtotal;
    }
}
