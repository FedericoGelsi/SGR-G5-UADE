package api;

import org.json.simple.JSONObject;

public interface Accionista {
    /*======GETTERS=======*/
    String getCUITAccionista();

    String getRazonsocial();

    float getPorcParticipacion();

    JSONObject toJSON();
}
