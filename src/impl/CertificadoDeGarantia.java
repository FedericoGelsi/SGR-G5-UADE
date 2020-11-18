package impl;

import org.json.simple.JSONObject;

public class CertificadoDeGarantia implements api.CertificadoDeGarantia {

    private int idcertificado;
    private String CUITSocio;
    private  int numerooperacion;

    //======CONSTRUCTOR=======

    public CertificadoDeGarantia(String CUITSocio,int idcertificado, int numerooperacion)
    {
    this.CUITSocio = CUITSocio;
    this.idcertificado = idcertificado;
    this.numerooperacion = numerooperacion;
    }

    public CertificadoDeGarantia(JSONObject jsonCDG) {
        this.CUITSocio = (String) jsonCDG.get("CUITSocio");
        this.idcertificado = Integer.parseInt(jsonCDG.get("idcertificado").toString());
        this.numerooperacion = Integer.parseInt(jsonCDG.get("numero-operacion").toString());
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

    public int getNumerooperacion() {
        return numerooperacion;
    }

    public JSONObject toJSON() {
        JSONObject certificado = new JSONObject();
        certificado.put("CUITSocio", this.CUITSocio);
        certificado.put("idcertificado",this.idcertificado);
        certificado.put("numero-operacion",this.numerooperacion);
        return certificado;
    }
}

