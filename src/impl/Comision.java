package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;

public class Comision implements api.Comision {
    private final int IDComision;



    public void setMontocomisiontotal(double montocomisiontotal) {
        this.montocomisiontotal = montocomisiontotal;
    }

    private String estado;
    private final double porcentajeComision;
    private final int numeroOperacion;
    private final String tipoOP;
    private double montocomisiontotal;
    private LocalDate fechaCreacion;

    /*======CONSTRUCTOR=======*/

    public Comision(int IDComision, String estado, double porcentajeComision, int numeroOperacion, String tipoOP,double montocomisiontotal) {
        this.IDComision = IDComision;
        this.estado = estado;
        this.porcentajeComision = porcentajeComision;
        this.numeroOperacion = numeroOperacion;
        this.tipoOP = tipoOP;
        this.montocomisiontotal=montocomisiontotal;
        this.fechaCreacion = LocalDate.now();
    }

    public Comision(JSONObject jsonCMS){
        this.estado = (String) jsonCMS.get("Estado");
        this.porcentajeComision = Double.parseDouble(jsonCMS.get("Porcentaje-Comision").toString());
        this.numeroOperacion = Integer.parseInt(jsonCMS.get("Numero-Operacion").toString());
        this.tipoOP = (String) jsonCMS.get("Tipo-Operacion");
        this.IDComision = Integer.parseInt(jsonCMS.get("IDComision").toString());
        this.montocomisiontotal = Double.parseDouble(jsonCMS.get("montocomisiontotal").toString()) ;
        this.fechaCreacion = LocalDate.parse(jsonCMS.get("fecha-creacion").toString());
    }


    /*======GETTERS=======*/
    public double getMontocomisiontotal() {
        return montocomisiontotal;
    }

    @Override
    public int getIDComision() {
        return IDComision;
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public double getPorcentajeComision() {
        return porcentajeComision;
    }

    @Override
    public int getNumeroOperacion() {
        return numeroOperacion;
    }

    @Override
    public String getTipoOP() {
        return tipoOP;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    /*======SETTERS=======*/

    @Override
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public JSONObject toJSON() {
        JSONObject comision = new JSONObject();
        comision.put("Estado", this.estado);
        comision.put("Porcentaje-Comision", this.porcentajeComision);
        comision.put("Numero-Operacion", this.numeroOperacion);
        comision.put("Tipo-Operacion", this.tipoOP);
        comision.put("IDComision", this.IDComision);
        comision.put("montocomisiontotal",this.montocomisiontotal);
        comision.put("fecha-creacion", this.fechaCreacion.toString());
        return comision;
    }


}
