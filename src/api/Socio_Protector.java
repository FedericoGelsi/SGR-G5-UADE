package api;

import java.io.File;

public interface Socio_Protector extends Socio {
    /*======CLASS FUNCTIONS=======*/
    void agregarAporte(double cantidad, File documento);

    void retirarAporte(double cantidad);

    double getTotalAporte();
}
