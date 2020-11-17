package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Factura implements api.Factura {
    private int IDFactura;
    private double montoFactura;
    private String CUITemisor;
    private String CUITdestinatario;
    private String estado;

    /*======CONSTRUCTOR=======*/

    public Factura(int IDFactura,double montoFactura, String CUITemisor, String CUITdestinatario, String estado) {
        this.montoFactura = montoFactura;
        this.CUITemisor = CUITemisor;
        this.CUITdestinatario = CUITdestinatario;
        this.IDFactura = IDFactura;
        this.estado = estado;
    }

    public Factura(JSONObject jsonFactura) {
        this.IDFactura= Integer.parseInt(jsonFactura.get("idfactura").toString());
        this.montoFactura = (double) jsonFactura.get("montofactura");
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
    public double getMontoFactura() {
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
        JSONObject facturas = new JSONObject();
        facturas.put("idfactura", this.IDFactura);
        facturas.put("montofactura", this.montoFactura);
        facturas.put("CUITemisor", this.CUITemisor);
        facturas.put("CUITDestinatario", this.CUITdestinatario);
        facturas.put("estado",this.estado);
        return facturas;
    }
}
