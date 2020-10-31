package impl;

public class CertificadoDeGarantia implements api.CertificadoDeGarantia {
    private int idcertificado;
    private String CUITSocio;

    /*======CONSTRUCTOR=======*/

    public CertificadoDeGarantia(String CUITSocio) {
        this.CUITSocio = CUITSocio;
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

}
