package impl;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class LineaCredito implements api.LineaCredito {
    private int ID;
    private float tope;
    private Date fechaVigencia;
    private String CUITParticipe;
    private float montoUtilizado;

    /*======CONSTRUCTOR=======*/

    public LineaCredito(float tope, Date fechaVigencia, String CUITParticipe) {
        this.tope = tope;
        this.fechaVigencia = fechaVigencia;
        this.CUITParticipe = CUITParticipe;
    }

    public LineaCredito(JSONObject jsonLineaCredito){
        this.tope = (float) jsonLineaCredito.get("tope");
        this.fechaVigencia = (Date) jsonLineaCredito.get("fecha-vigencia");
        this.CUITParticipe = (String) jsonLineaCredito.get("CUIT-Participe");
    }
    /*======GETTERS=======*/

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public float getTope() {
        return tope;
    }

    @Override
    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    @Override
    public String getCUITParticipe() {
        return CUITParticipe;
    }

    @Override
    public float getMontoUtilizado() {
        return montoUtilizado;
    }

    /*======CLASS FUNCTIONS=======*/

    @Override
    public float calcularUtilizadoLinea(){
        return 0;
    }
}
