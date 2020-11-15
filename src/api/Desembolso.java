package api;

import org.json.simple.JSONObject;

import java.time.LocalDate;

public interface Desembolso {
    double getMonto();

    String getCuit();

    String getRazonSocial();

    LocalDate getFechaOp();

    int getId();

    JSONObject toJSON();
}
