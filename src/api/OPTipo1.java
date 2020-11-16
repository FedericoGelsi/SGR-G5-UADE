package api;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.Date;

public interface OPTipo1 extends Operacion {
    LocalDate getFechaVencimiento();

    String getBanco();

    int getNumeroCheque();

    String getCUITfirmante();

    String getTipo();

    float getTasaDeDescuento();

    JSONObject toJSON();
}
