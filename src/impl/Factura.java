package impl;

public class Factura implements api.Factura {
    private int IDFatura;
    private float montoFactura;
    private String CUITemisor;
    private String CUITdestinatario;
    private String estado;

    /*======CONSTRUCTOR=======*/

    public Factura(float montoFactura, String CUITemisor, String CUITdestinatario) {
        this.montoFactura = montoFactura;
        this.CUITemisor = CUITemisor;
        this.CUITdestinatario = CUITdestinatario;
    }

    /*======GETTERS=======*/

    @Override
    public int getIDFatura() {
        return IDFatura;
    }

    @Override
    public float getMontoFactura() {
        return montoFactura;
    }

    @Override
    public String getCUITemisor() {
        return CUITemisor;
    }

    @Override
    public String getCUITdestinatario() {
        return CUITdestinatario;
    }

    @Override
    public String getEstado() {
        return estado;
    }
}
