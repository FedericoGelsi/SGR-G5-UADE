package api;

import org.json.simple.JSONObject;

import java.time.LocalDate;

public interface Contragarantia {
    String getTipo();

    double getMontoContragarantia();

    LocalDate getFechaVigencia();

    String getCUITPropietario();

    LocalDate getFechacreacion();

    JSONObject toJSON();
}
