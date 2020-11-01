package impl;

import api.DocumentacionEmpresa;

import java.util.ArrayList;
import java.util.Date;

public abstract class Socio implements api.Socio {
    private String CUITSocio;
    private String RazonSocial;
    private Date FinicAct;
    private String tipoEmpresa;
    private String actPrincipal;
    private String direccion;
    private String email;
    private String estado;
    private Date FechaPleno;

    private ArrayList<Accionista> accionistas;
    private ArrayList<DocumentacionEmpresa> documentacion;

    /*======CONSTRUCTOR=======*/

    public Socio(String CUITSocio, String razonSocial, Date finicAct, String tipoEmpresa, String actPrincipal, String direccion, String email) {
        this.CUITSocio = CUITSocio;
        RazonSocial = razonSocial;
        FinicAct = finicAct;
        this.tipoEmpresa = tipoEmpresa;
        this.actPrincipal = actPrincipal;
        this.direccion = direccion;
        this.email = email;
    }

    /*======GETTERS=======*/
    @Override
    public String getCUITSocio() {
        return CUITSocio;
    }

    @Override
    public String getRazonSocial() {
        return RazonSocial;
    }

    @Override
    public Date getFinicAct() {
        return FinicAct;
    }

    @Override
    public String getTipoEmpresa() {
        return tipoEmpresa;
    }

    @Override
    public String getActPrincipal() {
        return actPrincipal;
    }

    @Override
    public String getDireccion() {
        return direccion;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public Date getFechaPleno() {
        return FechaPleno;
    }

    /*======SETTERS=======*/

    @Override
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public void setFechaPleno(Date fechaPleno) {
        FechaPleno = fechaPleno;
    }

    /*======CLASS FUNCTIONS=======*/
    @Override
    public void suscribirAccion(){
        // DEFINIR PARÁMETROS DE ENTRADA
    }

    @Override
    public void venderAccion(){
        // DEFINIR PARÁMETROS DE ENTRADA
    }

    @Override
    public void altaDocumentacion(){
        // DEFINIR PARÁMETROS DE ENTRADA
    }

    @Override
    public void mostrarDocumento(String tipoDocumento){
        // DEFINIR PARÁMETROS DE ENTRADA
    }

    @Override
    public void getCUITAccionistas(){
        // DEFINIR PARÁMETROS DE ENTRADA
    }
}
