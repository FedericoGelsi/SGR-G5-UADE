package api;

import org.json.simple.JSONObject;

public interface Accion {
    /*======GETTERS=======*/
    char getTipo();

    String getCUITPropietario();

    String getCUITEmisor();

    JSONObject toJSON();
}
