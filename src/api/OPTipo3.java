package api;

import java.util.Date;

public interface OPTipo3 extends Operacion {
    int getCantCuotas();

    String getBanco();

    float getImporteTotal();

    float getTasa();

    Date getFechaAcreditacion();

    String getSistema();

    int getCuotasImpagas();
}
