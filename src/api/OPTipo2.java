package api;

import java.util.Date;

public interface OPTipo2 extends Operacion {
    String getNombreEmpresa();

    float getImporteTotal();

    Date getFechaVencimiento();

    String getTipo();
}
