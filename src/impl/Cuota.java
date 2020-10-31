package impl;

import java.util.Date;

public class Cuota implements api.Cuota {
    private int numero;
    private String estado;
    private float montoCuota;
    private Date fechaVencimiento;

    /*======CONSTRUCTOR=======*/
    public Cuota(float montoCuota, Date fechaVencimiento) {
        this.montoCuota = montoCuota;
        this.fechaVencimiento = fechaVencimiento;
    }

    /*======GETTERS=======*/

    @Override
    public int getNumero() {
        return numero;
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public float getMontoCuota() {
        return montoCuota;
    }

    @Override
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }
}
