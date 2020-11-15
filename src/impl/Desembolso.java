package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Desembolso implements api.Desembolso {
    private double monto;
    private String cuit;
    private String razonSocial;
    private LocalDate fechaOp;
    private int id;

    public Desembolso( int id, String cuit, String razonSocial, double monto ){
        this.cuit = cuit;
        this.id  = id;
        this.razonSocial = razonSocial;
        this.monto = monto;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        this.fechaOp = LocalDate.now();
    }

    public Desembolso(JSONObject jsonDesembolso){
        this.monto = (double) jsonDesembolso.get("monto");
        this.cuit = (String) jsonDesembolso.get("cuit-socio");
        this.razonSocial = (String) jsonDesembolso.get("razon-social");
        this.id = Integer.parseInt(jsonDesembolso.get("id").toString());
        this.fechaOp = LocalDate.parse(jsonDesembolso.get("fecha-op").toString());
    }

    @Override
    public double getMonto() {
        return monto;
    }

    @Override
    public String getCuit() {
        return cuit;
    }

    @Override
    public String getRazonSocial() {
        return razonSocial;
    }

    @Override
    public LocalDate getFechaOp() {
        return fechaOp;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public JSONObject toJSON(){
        JSONObject desembolso = new JSONObject();
        desembolso.put("monto", this.monto);
        desembolso.put("cuit-socio", this.cuit);
        desembolso.put("fecha-op", this.fechaOp.toString());
        desembolso.put("id", this.id);
        desembolso.put("razon-social", this.razonSocial);
        return desembolso;
    }

}
