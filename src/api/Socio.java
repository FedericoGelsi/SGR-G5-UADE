package api;

import org.json.simple.JSONObject;

import java.time.LocalDate;

public interface Socio {
    /*======GETTERS=======*/
    String getCUITSocio();

    String getRazonSocial();

    LocalDate getFinicAct();

    String getTipoEmpresa();

    String getActPrincipal();

    String getDireccion();

    String getEmail();

    String getEstado();

    LocalDate getFechaPleno();

    /*======SETTERS=======*/

    void setEstado(String estado);

    void setFechaPleno(LocalDate fechaPleno);

    /*======CLASS FUNCTIONS=======*/
    void suscribirAccion();

    void venderAccion();

    void altaDocumentacion();

    void mostrarDocumento(String tipoDocumento);

    void getCUITAccionistas();

    JSONObject toJSON();
}
