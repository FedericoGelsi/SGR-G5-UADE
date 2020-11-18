package api;

import org.json.simple.JSONObject;

public interface Accionista {
    /*======GETTERS=======*/
    String getCUITAccionista();

    String getRazonsocial();

    Double getPorcParticipacion();

    JSONObject toJSON();
}
