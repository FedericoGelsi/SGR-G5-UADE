package api;

public interface Comision {
    int getIDComision();

    String getEstado();

    float getPorcentajeComision();

    int getNumeroOperacion();

    String getTipoOP();

    void setEstado(String estado);
}
