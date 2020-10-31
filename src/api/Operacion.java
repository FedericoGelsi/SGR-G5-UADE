package api;

import java.util.Date;

public interface Operacion {
    /*======GETTERS=======*/
    int getIdOperacion();

    String getEstadoOperacion();

    Date getFechaCreacion();

    Date getFechaMonetizado();

    String getCUITSolicitante();

    void setEstadoOperacion(String estadoOperacion);

    void setFechaMonetizado(Date fechaMonetizado);

    float getPorcentajeComisionAsociada(int IdOperacion);
}
