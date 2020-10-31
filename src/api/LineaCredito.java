package api;

import java.util.Date;

public interface LineaCredito {
    int getID();

    float getTope();

    Date getFechaVigencia();

    String getCUITParticipe();

    float getMontoUtilizado();

    float calcularUtilizadoLinea();
}
