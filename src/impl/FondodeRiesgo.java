package impl;

import java.util.ArrayList;
import java.util.Date;

public class FondodeRiesgo implements api.FondodeRiesgo {
    private int ID;
    private Date fechaCreacion;
    private float montoFDR;
    private ArrayList<Aporte> Aportes;

    /*======CONSTRUCTOR=======*/
    public FondodeRiesgo(float montoFDR) {
        this.montoFDR = montoFDR;
    }

    /*======GETTERS=======*/

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public float getMontoFDR() {
        return montoFDR;
    }
}
