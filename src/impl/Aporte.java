package impl;

import org.json.simple.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Aporte implements api.Aporte {
    private int IDAporte;
    private LocalDate fechaCreacion;
    private double montoAporte;
    private String documento;
    private String SocioAportante;

    /*======CONSTRUCTOR=======*/
    public Aporte(double montoAporte, String documentPath, String socioAportante, int idaporte){
        this.montoAporte = montoAporte;
        this.documento = documentPath;
        this.fechaCreacion = LocalDate.now();
        this.SocioAportante = socioAportante;
        this.IDAporte = idaporte;
    }

    public Aporte(JSONObject jsonaporte){
        this.montoAporte = Double.parseDouble(jsonaporte.get("monto").toString());
        this.documento = jsonaporte.get("doc-path").toString();
        this.fechaCreacion = LocalDate.parse(jsonaporte.get("fecha-creacion").toString());
        this.SocioAportante = jsonaporte.get("socio-aportante").toString();
        this.IDAporte = Integer.parseInt(jsonaporte.get("id-aporte").toString());
    }

    /*======GETTERS=======*/
    @Override
    public int getIDAporte() {
        return IDAporte;
    }

    @Override
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public double getMontoAporte() {
        return montoAporte;
    }

    @Override
    public String getDocumento() {
        return documento;
    }

    @Override
    public String getSocioAportante() {
        return SocioAportante;
    }

    /*======CLASS FUNCTIONS=======*/
    @Override
    public boolean calcularVigencia(){
        LocalDate hoy = LocalDate.now();
        System.out.println("Current Date " + hoy);
        // Comparacion
        if (this.fechaCreacion.plusYears(2).isBefore(hoy)) {
            return true;
        }
        else{
            return false;
        }
    }

    public JSONObject toJSON(){
        JSONObject aporte = new JSONObject();
        aporte.put("monto", this.montoAporte);
        aporte.put("doc-path", this.documento);
        aporte.put("socio-aportante", this.SocioAportante);
        aporte.put("id-aporte", this.IDAporte);
        aporte.put("fecha-creacion", this.fechaCreacion.toString());
        return aporte;
    }
}
