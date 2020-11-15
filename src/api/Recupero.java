package api;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public interface Recupero {
    String getTipo();

    double getMonto();

    String getIdSocioPleno();

    ArrayList<String> getIdDeuda();

    JSONObject toJSON();
}
