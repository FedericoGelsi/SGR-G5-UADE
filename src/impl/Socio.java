package impl;

import api.DocumentacionEmpresa;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public abstract class Socio implements api.Socio {
    private final String CUITSocio;
    private final String RazonSocial;
    private final LocalDate FinicAct;
    private final String tipoEmpresa;
    private final String actPrincipal;
    private final String direccion;
    private final String email;
    private String estado;
    private LocalDate FechaPleno;

    private ArrayList<Accionista> accionistas;
    private ArrayList<DocumentacionEmpresa> documentacion;

    /*======CONSTRUCTOR=======*/

    public Socio(String CUITSocio, String razonSocial, LocalDate finicAct, String tipoEmpresa, String actPrincipal, String direccion, String email) {
        this.CUITSocio = CUITSocio;
        this.RazonSocial = razonSocial;
        this.FinicAct = finicAct;
        this.tipoEmpresa = tipoEmpresa;
        this.actPrincipal = actPrincipal;
        this.direccion = direccion;
        this.email = email;
        this.estado = "Postulante";
    }

    public Socio(JSONObject jsonSocio) {

        this.CUITSocio = jsonSocio.get("cuit").toString();
        this.RazonSocial = jsonSocio.get("razon-social").toString();
        this.FinicAct = LocalDate.parse(jsonSocio.get("finic-act").toString());
        this.tipoEmpresa = jsonSocio.get("tipo-empresa").toString();
        this.actPrincipal = jsonSocio.get("actividad-principal").toString();
        this.direccion = jsonSocio.get("direccion").toString();
        this.email = jsonSocio.get("email").toString();
        this.estado = jsonSocio.get("estado").toString();
        this.FechaPleno = LocalDate.parse(jsonSocio.get("fecha-pleno").toString());

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
    public LocalDate getFinicAct() {
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
    public LocalDate getFechaPleno() {
        return FechaPleno;
    }

    /*======SETTERS=======*/

    @Override
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public void setFechaPleno(LocalDate fechaPleno) {
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

    public JSONObject toJSON(){
        JSONObject socio = new JSONObject();
        socio.put("cuit", this.CUITSocio);
        socio.put("razon-social", this.RazonSocial);
        socio.put("finic-act", this.FinicAct.toString());
        socio.put("tipo-empresa", this.tipoEmpresa);
        socio.put("actividad-principal", this.actPrincipal);
        socio.put("monto-mora", this.direccion);
        socio.put("email", this.email);
        socio.put("estado", this.estado);
        if (this.FechaPleno == null){
            socio.put("fecha-pleno","");
        }else{
            socio.put("fecha-pleno",this.FechaPleno.toString());
        }
        return socio;
    }
}
