package api;

import impl.Socio_Participe;
import impl.Socio_Protector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;

public interface SocioController {
    /*======CLASS FUNCTIONS=======*/
    void controlarDocumentacion();

    void altaSocioProtector(String CUITSocio, String razonSocial, String finicAct, String tipoEmpresa, String actPrincipal, String direccion, String email) throws Exception;

    void altaSocioParticipe(String CUITSocio, String razonSocial, String finicAct, String tipoEmpresa, String actPrincipal, String direccion, String email) throws Exception;

    ArrayList<String> ComparteAccionistas(String CUITSocio);

    ArrayList<String> ListarCUITSocioPorTipoEmpresa(String tipoEmpresa) throws Exception;

    void aprobarSocioProtector(String CUITsocio);

    void aprobarSocioParticipe(String CUITsocio);

    void crearAporte(String CUIT, int Cantidad, File documento);

    JSONArray buscarDesembolsos();

    boolean verificarMontoRecupero(double totaldeuda, String monto);

    JSONObject buscarSocioParticipe(String cuit) throws Exception;

    JSONObject buscarSocioProtector(String cuit) throws Exception;

    boolean lineacreditovigente(String CUITSocio);

    boolean lineasuficiente(String CUITSocio, float montooperacion);

    boolean contragarantiassuficientes(String CUITSocio, float montooperacion);

    boolean essocioparticipe(String CUITSocio);

    boolean lineatope(String CUITSocio, float montooperacion);



}
