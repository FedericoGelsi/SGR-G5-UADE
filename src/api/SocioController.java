package api;

import org.json.simple.JSONArray;

import java.io.File;
import java.util.ArrayList;

public interface SocioController {
    /*======CLASS FUNCTIONS=======*/
    void controlarDocumentacion();

    void altaSocioProtector();

    void altaSocioParticipe();

    ArrayList<String> ComparteAccionistas(String CUITSocio);

    ArrayList<String> ListarCUITSocioPorTipoEmpresa(String tipoEmpresa) throws Exception;

    void aprobarSocioProtector(String CUITsocio);

    void aprobarSocioParticipe(String CUITsocio);

    void crearAporte(String CUIT, int Cantidad, File documento);

    JSONArray buscarDesembolsos();

    boolean verificarMontoRecupero(double totaldeuda, String monto );
}
