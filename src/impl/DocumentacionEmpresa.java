package impl;

import java.io.File;
import java.util.Date;

public class DocumentacionEmpresa implements api.DocumentacionEmpresa {
    private String tipoDocumento;
    private Date fechaRecepcion;
    private String estado;
    private String usuario;
    private File archivo;
    private boolean opcional;
    private int idDocumento;

    /*======CONSTRUCTOR=======*/

    public DocumentacionEmpresa(String tipoDocumento, Date fechaRecepcion, String usuario, File archivo, boolean opcional) {
        this.tipoDocumento = tipoDocumento;
        this.fechaRecepcion = fechaRecepcion;
        this.usuario = usuario;
        this.archivo = archivo;
        this.opcional = opcional;
    }

    /*======GETTERS=======*/

    @Override
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    @Override
    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public String getUsuario() {
        return usuario;
    }

    @Override
    public File getArchivo() {
        return archivo;
    }

    @Override
    public boolean isOpcional() {
        return opcional;
    }

    @Override
    public int getIdDocumento() {
        return idDocumento;
    }

    /*======SETTERS=======*/

    @Override
    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    @Override
    public void setEstado(String estado) {
        this.estado = estado;
    }

        // ME PARECE QUE NO ES NECESARIO SE INGRESA CON EL CONSTRUCTOR
    @Override
    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }
}
