package api;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

public interface Aporte {
    /*======GETTERS=======*/
    int getIDAporte();

    LocalDate getFechaCreacion();

    double getMontoAporte();

    String getDocumento();

    String getSocioAportante();

    /*======CLASS FUNCTIONS=======*/
    boolean calcularVigencia();
}
