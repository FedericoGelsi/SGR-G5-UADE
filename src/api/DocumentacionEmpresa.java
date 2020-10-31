package api;

import java.io.File;
import java.util.Date;

public interface DocumentacionEmpresa {
    String getTipoDocumento();

    Date getFechaRecepcion();

    String getEstado();

    String getUsuario();

    File getArchivo();

    boolean isOpcional();

    int getIdDocumento();

    void setFechaRecepcion(Date fechaRecepcion);

    void setEstado(String estado);

    // ME PARECE QUE NO ES NECESARIO SE INGRESA CON EL CONSTRUCTOR
    void setArchivo(File archivo);
}
