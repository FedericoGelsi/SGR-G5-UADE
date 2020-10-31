package impl;

import java.util.Date;

public class Contragarantia implements api.Contragarantia {
    private String tipo;
    private float montoContragarantia;
    private Date fechaVigencia;
    private String CUITPropietario;

    /*======CONSTRUCTOR=======*/
            // FALTA EN EL DDC
    public Contragarantia(String tipo, float montoContragarantia, Date fechaVigencia, String CUITPropietario) {
        this.tipo = tipo;
        this.montoContragarantia = montoContragarantia;
        this.fechaVigencia = fechaVigencia;
        this.CUITPropietario = CUITPropietario;
    }

    /*======GETTERS=======*/

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public float getMontoContragarantia() {
        return montoContragarantia;
    }

    @Override
    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    @Override
    public String getCUITPropietario() {
        return CUITPropietario;
    }
}
