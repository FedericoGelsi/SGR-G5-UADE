package impl;

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
