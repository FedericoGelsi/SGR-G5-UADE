package impl;

import org.json.simple.JSONObject;

public class Accionista implements api.Accionista {

    private String CUITAccionista;
    private String razonsocial;
    private Double porcParticipacion;

    public Accionista(JSONObject accionistasJ) {
        this.CUITAccionista= (String) accionistasJ.get("cuit-accionista");
        this.porcParticipacion= Double.parseDouble(accionistasJ.get("porcentaje-participacion").toString());
        this.razonsocial= (String) accionistasJ.get("razon-social-ac");

    }

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
    public Double getPorcParticipacion() {
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
