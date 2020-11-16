package api;

import org.json.simple.JSONObject;

import java.time.LocalDate;

public interface Deuda {
    boolean isAplicaMora();

    double getMonto();

    String getCUITDeudor();

    int getIdDeuda();

    double getMontoMora();

    LocalDate getFechaDeuda();

    void retirarAporte();

    JSONObject toJSON();
}
