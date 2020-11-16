package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.Date;

public abstract class Operacion implements api.Operacion {
    private int idOperacion;
    private String estadoOperacion;
    private LocalDate fechaCreacion;
    private LocalDate fechaMonetizado;
    private String CUITSolicitante;

    /*======CONSTRUCTOR=======*/

    public Operacion(String CUITSolicitante) {
        this.CUITSolicitante = CUITSolicitante;
    }

    public Operacion(JSONObject jsonOperacion) {
        this.CUITSolicitante = (String) jsonOperacion.get("CUITsolicitante");
        this.idOperacion = Integer.parseInt(jsonOperacion.get("IDoperacion").toString());
        this.estadoOperacion = (String) jsonOperacion.get("estadooperacion");
        this.fechaCreacion = (LocalDate) jsonOperacion.get("fechacreacionop");
        this.fechaMonetizado = (LocalDate) jsonOperacion.get("fechamonetizado");
    }

    /*======GETTERS=======*/
    @Override
    public int getIdOperacion() {
        return idOperacion;
    }

    @Override
    public String getEstadoOperacion() {
        return estadoOperacion;
    }

    @Override
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public LocalDate getFechaMonetizado() {
        return fechaMonetizado;
    }

    @Override
    public String getCUITSolicitante() {
        return CUITSolicitante;
    }

    /*======SETTERS=======*/

    @Override
    public void setEstadoOperacion(String estadoOperacion) {
        this.estadoOperacion = estadoOperacion;
    }

    @Override
    public void setFechaMonetizado(LocalDate fechaMonetizado) {
        this.fechaMonetizado = fechaMonetizado;
    }

    /*======CLASS FUNCIONS=======*/

    @Override
    public float getPorcentajeComisionAsociada(int IdOperacion){

        return 0;
    }
    public JSONObject toJSON(){
        JSONObject Operacion = new JSONObject();
        Operacion.put("CUITsolicitante", this.CUITSolicitante);
        Operacion.put("estadooperacion", this.estadoOperacion);
        Operacion.put("fechacreacionop", this.fechaCreacion);
        Operacion.put("fechamonetizado", this.fechaMonetizado);
        return Operacion;
    }
}
