package api;

import java.util.Date;

public interface Contragarantia {
    String getTipo();

    float getMontoContragarantia();

    Date getFechaVigencia();

    String getCUITPropietario();
}
