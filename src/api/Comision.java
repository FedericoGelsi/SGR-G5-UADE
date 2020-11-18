package api;

import org.json.simple.JSONObject;

public interface Comision {
    int getIDComision();

    double getMontocomisiontotal();

    String getEstado();

    double getPorcentajeComision();

    int getNumeroOperacion();

    String getTipoOP();

    void setEstado(String estado);

    JSONObject toJSON();
}
