package api;

import java.io.File;
import java.util.ArrayList;

public interface SocioController {
    /*======CLASS FUNCTIONS=======*/
    void controlarDocumentacion();

    void altaSocioProtector();

    void altaSocioParticipe();

    ArrayList<String> ComparteAccionistas(String CUITSocio);

    void ListarCUITSocioPorTipoEmpresa(String tipoEmpresa);

    void aprobarSocioProtector(String CUITsocio);

    void aprobarSocioParticipe(String CUITsocio);

    void crearAporte(String CUIT, int Cantidad, File documento);
}
