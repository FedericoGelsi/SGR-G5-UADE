package api;

import java.io.File;
import java.util.Date;

public interface Aporte {
    /*======GETTERS=======*/
    int getIDAporte();

    Date getFechaCreacion();

    float getMontoAporte();

    File getDocumento();

    int getSocioAportante();

    /*======CLASS FUNCTIONS=======*/
    boolean calcularVigencia();
}
