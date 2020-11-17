package api;

import java.time.LocalDate;
import java.util.Date;

public interface Contragarantia {
    String getTipo();

    double getMontoContragarantia();

    LocalDate getFechaVigencia();

    String getCUITPropietario();
}
