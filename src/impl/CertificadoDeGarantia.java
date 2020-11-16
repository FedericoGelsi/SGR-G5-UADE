package impl;

import org.json.simple.JSONObject;

public class CertificadoDeGarantia implements api.CertificadoDeGarantia {
    private int idcertificado;
    private String CUITSocio;

    //======CONSTRUCTOR=======

    public CertificadoDeGarantia(String CUITSocio) {
        this.CUITSocio = CUITSocio;
    }

    public CertificadoDeGarantia(JSONObject jsonCDG) {
        this.CUITSocio = (String) jsonCDG.get("CUITSocio");
    }


    /*======GETTERS=======*/

    @Override
    public int getIdcertificado() {
        return idcertificado;
    }

    @Override
    public String getCUITSocio() {
        return CUITSocio;
    }

    public JSONObject toJSON() {
        JSONObject certificado = new JSONObject();
        certificado.put("CUITSocio", this.CUITSocio);
        return certificado;
    }
}

