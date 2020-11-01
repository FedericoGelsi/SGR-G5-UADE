package impl;

import java.util.Date;

public abstract class Operacion implements api.Operacion {
    private int idOperacion;
    private String estadoOperacion;
    private Date fechaCreacion;
    private Date fechaMonetizado;
    private String CUITSolicitante;

    /*======CONSTRUCTOR=======*/

    public Operacion(String CUITSolicitante) {
        this.CUITSolicitante = CUITSolicitante;
    }

    /*======GETTERS=======*/
    @Override
    public int getIdOperacion() {
        return idOperacion;
    }

    @Override
    public String getEstadoOperacion() {
        return estadoOperacion;
    }

    @Override
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public Date getFechaMonetizado() {
        return fechaMonetizado;
    }

    @Override
    public String getCUITSolicitante() {
        return CUITSolicitante;
    }

    /*======SETTERS=======*/

    @Override
    public void setEstadoOperacion(String estadoOperacion) {
        this.estadoOperacion = estadoOperacion;
    }

    @Override
    public void setFechaMonetizado(Date fechaMonetizado) {
        this.fechaMonetizado = fechaMonetizado;
    }

    /*======CLASS FUNCIONS=======*/

    @Override
    public float getPorcentajeComisionAsociada(int IdOperacion){

        return 0;
    }
}
