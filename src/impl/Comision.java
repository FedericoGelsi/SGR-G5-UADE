package impl;

import org.json.simple.JSONObject;

public class Comision implements api.Comision {
    private final int IDComision;
    private String estado;
    private final float porcentajeComision;
    private final int numeroOperacion;
    private final String tipoOP;

    /*======CONSTRUCTOR=======*/

    public Comision(int IDComision, String estado, float porcentajeComision, int numeroOperacion, String tipoOP) {
        this.IDComision = IDComision;
        this.estado = estado;
        this.porcentajeComision = porcentajeComision;
        this.numeroOperacion = numeroOperacion;
        this.tipoOP = tipoOP;
    }

    public Comision(JSONObject jsonCMS){
        this.estado = (String) jsonCMS.get("Estado");
        this.porcentajeComision = (float) jsonCMS.get("Porcentaje-Comision");
        this.numeroOperacion = Integer.parseInt(jsonCMS.get("Numero-Operacion").toString());
        this.tipoOP = (String) jsonCMS.get("Tipo-Operacion");
        this.IDComision = Integer.parseInt(jsonCMS.get("ID-Comision").toString());
    }


    /*======GETTERS=======*/

    @Override
    public int getIDComision() {
        return IDComision;
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public float getPorcentajeComision() {
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
        return comision;
    }


}
