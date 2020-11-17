package api;

import org.json.simple.JSONObject;

public interface CertificadoDeGarantia {
    int getIdcertificado();
    int getNumerooperacion();

    String getCUITSocio();

    JSONObject toJSON();
}
