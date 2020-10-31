package impl;

public class Comision implements api.Comision {
    private int IDComision;
    private String estado;
    private float porcentajeComision;
    private int numeroOperacion;
    private String tipoOP;

    /*======CONSTRUCTOR=======*/

    public Comision(String estado, float porcentajeComision, int numeroOperacion, String tipoOP) {
        this.estado = estado;
        this.porcentajeComision = porcentajeComision;
        this.numeroOperacion = numeroOperacion;
        this.tipoOP = tipoOP;
    }

    /*======GETTERS=======*/

    @Override
    public int getIDComision() {
        return IDComision;
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public float getPorcentajeComision() {
        return porcentajeComision;
    }

    @Override
    public int getNumeroOperacion() {
        return numeroOperacion;
    }

    @Override
    public String getTipoOP() {
        return tipoOP;
    }

    /*======SETTERS=======*/

    @Override
    public void setEstado(String estado) {
        this.estado = estado;
    }


}
