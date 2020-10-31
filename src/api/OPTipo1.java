package api;

import java.util.Date;

public interface OPTipo1 extends Operacion {
    Date getFechaVencimiento();

    String getBanco();

    int getNumeroCheque();

    String getCUITfirmante();

    String getTipo();

    float getTasaDeDescuento();
}
