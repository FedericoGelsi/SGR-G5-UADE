package impl;

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
}