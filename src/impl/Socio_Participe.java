package impl;

import api.Contragarantia;
import api.LineaCredito;
import api.Recupero;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Socio_Participe extends Socio implements api.Socio_Participe {

    private ArrayList<Contragarantia> contragarantias;
    private LineaCredito Lineacredito;
    private ArrayList<Recupero> recuperos;

    /*======CONSTRUCTOR=======*/
    public Socio_Participe(String CUITSocio, String razonSocial, Date finicAct, String tipoEmpresa, String actPrincipal, String direccion, String email) {
        super(CUITSocio, razonSocial, finicAct, tipoEmpresa, actPrincipal, direccion, email);
    }

    public Socio_Participe(JSONObject jsonSocio){
        super (jsonSocio);
    }

    /*======CLASSS FUNCTIONS=======*/
    @Override
    public void addContragarantias(){

    }

    @Override
    public void solicitarOperacion(){
        // Ver que recibe
    }

    @Override
    public void altaLineaCredito(){

    }

    @Override
    public void saldarDeuda(){

    }

    @Override
    public float CalcularContragarantias(String CUIT){
        return 0;
    }

}
