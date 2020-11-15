package impl;

import org.json.simple.JSONObject;

public class Accionista implements api.Accionista {

    private String CUITAccionista;
    private String razonsocial;
    private float porcParticipacion;


    /*======GETTERS=======*/
    @Override
    public String getCUITAccionista() {
        return CUITAccionista;
    }

    @Override
    public String getRazonsocial() {
        return razonsocial;
    }

    @Override
    public float getPorcParticipacion() {
        return porcParticipacion;
    }

    public JSONObject toJSON(){
        JSONObject accionista = new JSONObject();
        accionista.put("cuit-accionista", this.CUITAccionista);
        accionista.put("razon-social", this.razonsocial);
        accionista.put("porcentaje-participacion", this.porcParticipacion);
        return accionista;
    }
}
