package api;

import java.util.Date;

public interface Deuda {
    boolean isAplicaMora();

    float getMonto();

    String getCUITDeudor();

    String getIdDeuda();

    float getMontoMora();

    Date getFechaDeuda();

    void retirarAporte();
}
