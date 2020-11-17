package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Contragarantia implements api.Contragarantia {
    private String tipo;
    private double montoContragarantia;
    private String CUITPropietario;
    private LocalDate fechaVigencia;


    /*======CONSTRUCTOR=======*/
    // FALTA EN EL DDC
    public Contragarantia(String tipo, float montoContragarantia, Date fechaVigencia, String CUITPropietario) {
        this.tipo = tipo;
        this.montoContragarantia = montoContragarantia;
        this.CUITPropietario = CUITPropietario;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        this.fechaVigencia = LocalDate.now();

    }
    public Contragarantia(JSONObject jsonContragarantia){
        this.montoContragarantia= (double) jsonContragarantia.get("montoContragarantia");
        this.CUITPropietario= (String) jsonContragarantia.get("cuit-socio");
        this.fechaVigencia= LocalDate.parse(jsonContragarantia.get("fecha-vigencia").toString());
        this.tipo= (String) jsonContragarantia.get("tipo");

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

    public JSONObject toJSON(){
        JSONObject contragarantia = new JSONObject();
        contragarantia.put("montoContragarantia", this.montoContragarantia);
        contragarantia.put("cuit-socio", this.CUITPropietario);
        contragarantia.put("fecha-vigencia",this.fechaVigencia.toString());
        contragarantia.put("tipo", this.tipo);
        return contragarantia;


    }
}