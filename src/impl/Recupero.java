package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Recupero implements api.Recupero {
    private String tipo;
    private double monto;
    private String idSocioPleno;
    private ArrayList<String> idDeudas;

    /*======CONSTRUCTOR=======*/

    public Recupero(String tipo, double monto, String idSocioPleno, ArrayList<String> idDeudas) {
        this.tipo = tipo;
        this.monto = monto;
        this.idSocioPleno = idSocioPleno;
        this.idDeudas = idDeudas;
    }

    public Recupero(JSONObject jsonRecupero){
        this.monto = (double) jsonRecupero.get("monto");
        this.tipo = (String) jsonRecupero.get("tipo");
        this.idSocioPleno = (String) jsonRecupero.get("id-socio-pleno");
        this.idDeudas = (ArrayList<String>) jsonRecupero.get("id-deudas");
    }

    /*======GETTERS=======*/

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public double getMonto() {
        return monto;
    }

    @Override
    public String getIdSocioPleno() {
        return idSocioPleno;
    }

    @Override
    public ArrayList<String> getIdDeuda() {
        return idDeudas;
    }

    public JSONObject toJSON(){
        JSONObject recupero = new JSONObject();
        recupero.put("tipo", this.tipo);
        recupero.put("monto", this.monto);
        recupero.put("id-socio-pleno", this.idSocioPleno);
        recupero.put("id-deudas", this.idDeudas);
        return recupero;
    }
}
