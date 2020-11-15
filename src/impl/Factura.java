package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Factura implements api.Factura {
    private int IDFactura;
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

    public Factura(JSONObject jsonFactura) {
        this.IDFactura= Integer.parseInt(jsonFactura.get("idfactura").toString());
        this.montoFactura = (float) jsonFactura.get("montofactura");
        this.CUITemisor = (String) jsonFactura.get("CUITemisor");
        this.CUITdestinatario = (String) jsonFactura.get("CUITDestinatario");
        this.estado=(String) jsonFactura.get("estado");
    }
    /*======GETTERS=======*/

    @Override
    public int getIDFatura() {
        return IDFactura;
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

    public JSONObject toJSON() {
        JSONObject factura = new JSONObject();
        factura.put("idfactura", this.IDFactura);
        factura.put("montofactura", this.montoFactura);
        factura.put("CUITemisor", this.CUITemisor);
        factura.put("CUITDestinatario", this.CUITdestinatario);
        factura.put("estado",this.estado);
        return factura;
    }
}
