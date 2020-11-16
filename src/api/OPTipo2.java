package api;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.Date;

public interface OPTipo2 extends Operacion {
    String getNombreEmpresa();

    float getImporteTotal();

    LocalDate getFechaVencimiento();

    String getTipo();

    JSONObject toJSON();
}
