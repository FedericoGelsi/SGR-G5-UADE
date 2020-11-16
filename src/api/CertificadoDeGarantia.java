package api;

import org.json.simple.JSONObject;

public interface CertificadoDeGarantia {
    int getIdcertificado();

    String getCUITSocio();

    JSONObject toJSON();
}
