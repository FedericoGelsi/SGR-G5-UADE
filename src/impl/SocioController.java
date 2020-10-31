package impl;

import java.io.File;
import java.util.ArrayList;

public class SocioController implements api.SocioController {
    private ArrayList<Socio> socios;

    /*======CLASS FUNCTIONS=======*/
    @Override
    public void controlarDocumentacion(){

    }

    @Override
    public void altaSocioProtector(){

    }

    @Override
    public void altaSocioParticipe(){

    }

    @Override
    public ArrayList<String> ComparteAccionistas(String CUITSocio){
        ArrayList<String> accionistas = null;
        return accionistas;
    }

    @Override
    public void ListarCUITSocioPorTipoEmpresa(String tipoEmpresa){

    }

    @Override
    public void aprobarSocioProtector(String CUITsocio){

    }

    @Override
    public void aprobarSocioParticipe(String CUITsocio){

    }

    @Override
    public void crearAporte(String CUIT, int Cantidad, File documento){

    }
}
