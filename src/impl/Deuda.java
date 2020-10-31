package impl;

import java.util.Date;

public class Deuda implements api.Deuda {
    private float monto;
    private String CUITDeudor;
    private String idDeuda;
    private float montoMora;
    private Date fechaDeuda;
    private boolean aplicaMora;

    /*======CONSTRUCTOR=======*/

    public Deuda(float monto, String CUITDeudor, String idDeuda, float montoMora) {
        this.monto = monto;
        this.CUITDeudor = CUITDeudor;
        this.idDeuda = idDeuda;
        this.montoMora = montoMora;
    }

    /*======GETTERS=======*/

    @Override
    public boolean isAplicaMora() {
        return aplicaMora;
    }

    @Override
    public float getMonto() {
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
    public float getMontoMora() {
        return montoMora;
    }

    @Override
    public Date getFechaDeuda() {
        return fechaDeuda;
    }

    /*======CLASS FUNCTIONS=======*/

    @Override
    public void retirarAporte(){

    }

}
