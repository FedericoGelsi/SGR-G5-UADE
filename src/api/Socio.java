package api;

import java.util.Date;

public interface Socio {
    /*======GETTERS=======*/
    String getCUITSocio();

    String getRazonSocial();

    Date getFinicAct();

    String getTipoEmpresa();

    String getActPrincipal();

    String getDireccion();

    String getEmail();

    String getEstado();

    Date getFechaPleno();

    /*======SETTERS=======*/

    void setEstado(String estado);

    void setFechaPleno(Date fechaPleno);

    /*======CLASS FUNCTIONS=======*/
    void suscribirAccion();

    void venderAccion();

    void altaDocumentacion();

    void mostrarDocumento(String tipoDocumento);

    void getCUITAccionistas();
}
