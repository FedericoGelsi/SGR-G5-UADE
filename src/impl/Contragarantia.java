package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;

public class Contragarantia implements api.Contragarantia {
    private String tipo;
    private double montoContragarantia;
    private String CUITPropietario;
    private LocalDate fechaVigencia;
    private LocalDate fechacreacion;


    /*======CONSTRUCTOR=======*/

    public Contragarantia(String tipo, double montoContragarantia, LocalDate fechaVigencia, String CUITPropietario) {
        this.tipo = tipo;
        this.montoContragarantia = montoContragarantia;
        this.CUITPropietario = CUITPropietario;
        this.fechaVigencia = fechaVigencia;
        this.fechacreacion = LocalDate.now();
    }

    public Contragarantia(JSONObject jsonContragarantia){
        this.montoContragarantia= (double) jsonContragarantia.get("monto");
        this.CUITPropietario= (String) jsonContragarantia.get("cuit-socio");
        this.fechaVigencia= LocalDate.parse(jsonContragarantia.get("fecha-vigencia").toString());
        this.tipo= (String) jsonContragarantia.get("tipo");
        this.fechacreacion = LocalDate.parse(jsonContragarantia.get("fecha-creacion").toString());
    }


    /*======GETTERS=======*/

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public double getMontoContragarantia() {
        return montoContragarantia;
    }

    @Override
    public LocalDate getFechaVigencia() {
        return fechaVigencia;
    }

    @Override
    public String getCUITPropietario() {
        return CUITPropietario;
    }

    @Override
    public LocalDate getFechacreacion() {
        return fechacreacion;
    }

    public JSONObject toJSON(){
        JSONObject contragarantia = new JSONObject();
        contragarantia.put("monto", this.montoContragarantia);
        contragarantia.put("cuit-socio", this.CUITPropietario);
        contragarantia.put("fecha-vigencia",this.fechaVigencia.toString());
        contragarantia.put("tipo", this.tipo);
        contragarantia.put("fecha-creacion", this.fechacreacion.toString());
        return contragarantia;
    }
}