package impl;

import org.json.simple.JSONObject;

import java.io.File;
import java.util.Date;

public class Aporte implements api.Aporte {
    private int IDAporte;
    private Date fechaCreacion;
    private float montoAporte;
    private File documento;
    private int SocioAportante;

    /*======CONSTRUCTOR=======*/
    public Aporte(float montoAporte, File documento, int socioAportante) {
        this.montoAporte = montoAporte;
        this.documento = documento;
        this.SocioAportante = socioAportante;
    }

    /*======GETTERS=======*/
    @Override
    public int getIDAporte() {
        return IDAporte;
    }

    @Override
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public float getMontoAporte() {
        return montoAporte;
    }

    @Override
    public File getDocumento() {
        return documento;
    }

    @Override
    public int getSocioAportante() {
        return SocioAportante;
    }

    /*======CLASS FUNCTIONS=======*/
    @Override
    public boolean calcularVigencia(){
        return true;
    }

    public JSONObject toJSON(){
        JSONObject aporte = new JSONObject();
        aporte.put("monto", this.montoAporte);
        aporte.put("doc-path", this.documento.getAbsolutePath());
        aporte.put("socio-aportante", this.SocioAportante);
        return aporte;
    }
}
