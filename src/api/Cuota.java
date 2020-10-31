package api;

import java.util.Date;

public interface Cuota {
    int getNumero();

    String getEstado();

    float getMontoCuota();

    Date getFechaVencimiento();
}
