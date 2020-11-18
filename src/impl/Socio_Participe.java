package impl;

import api.Contragarantia;
import api.LineaCredito;
import api.Recupero;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Socio_Participe extends Socio implements api.Socio_Participe {

    private ArrayList<Contragarantia> contragarantias;
    private LineaCredito Lineacredito;
    private ArrayList<Recupero> recuperos;

    /*======CONSTRUCTOR=======*/
    public Socio_Participe(String CUITSocio, String razonSocial, LocalDate finicAct, String tipoEmpresa, String actPrincipal, String direccion, String email) {
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

    public JSONObject toJSON(){
        JSONObject socio = new JSONObject();
        socio.put("cuit", this.getCUITSocio());
        socio.put("razon-social", this.getRazonSocial());
        socio.put("finic-act", this.getFinicAct().toString());
        socio.put("tipo-empresa", this.getTipoEmpresa());
        socio.put("actividad-principal", this.getActPrincipal());
        socio.put("email", this.getEmail());
        socio.put("estado", this.getEstado());
        socio.put("direccion", this.getDireccion());
        if (this.getFechaPleno() == null){
            socio.put("fecha-pleno","");
        }else{
            socio.put("fecha-pleno",this.getFechaPleno().toString());
        }
        socio.put("accionistas", new JSONArray());
        socio.put("contragarantias", new JSONArray());
        socio.put("deudas", new JSONArray());
        socio.put("recuperos", new JSONArray());
        socio.put("lineas-de-crédito", new JSONObject());

        return  socio;
    }

}
