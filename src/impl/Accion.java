package impl;

import org.json.simple.JSONObject;

public class Accion implements api.Accion {

    private char tipo;
    private String CUITPropietario;
    private String CUITEmisor;

    /*======GETTERS=======*/
    @Override
    public char getTipo() {
        return tipo;
    }

    @Override
    public String getCUITPropietario() {
        return CUITPropietario;
    }

    @Override
    public String getCUITEmisor() {
        return CUITEmisor;
    }

    public JSONObject toJSON(){
        JSONObject accion = new JSONObject();
        accion.put("tipo", this.tipo);
        accion.put("cuit-emisor", this.CUITEmisor);
        accion.put("cuit-propietario", this.CUITPropietario);
        return accion;
    }
}
