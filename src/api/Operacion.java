package api;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.Date;

public interface Operacion {
    /*======GETTERS=======*/
    int getIdOperacion();

    String getEstadoOperacion();

    LocalDate getFechaCreacion();

    LocalDate getFechaMonetizado();

    String getCUITSolicitante();

    void setEstadoOperacion(String estadoOperacion);

    void setFechaMonetizado(LocalDate fechaMonetizado);

    float getPorcentajeComisionAsociada(int IdOperacion);

    JSONObject toJSON();
}
