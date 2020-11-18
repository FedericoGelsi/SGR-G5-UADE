package impl;

import api.Aporte;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;


public class Socio_Protector extends Socio implements api.Socio_Protector {
    private ArrayList<Aporte> Aportes;


    /*======CONSTRUCTOR=======*/

    public Socio_Protector(String CUIT, String RazonSocial, LocalDate FinicAct, String tipoEmpresa, String actPrincipal, String direccion, String email) {
        super(CUIT, RazonSocial, FinicAct, tipoEmpresa, actPrincipal, direccion, email);
    }

    public Socio_Protector(JSONObject jsonSocio) {
        super(jsonSocio);
    }

    /*======CLASS FUNCTIONS=======*/
    @Override
    public void agregarAporte(double cantidad, File documento) {

    }

    @Override
    public void retirarAporte(double cantidad) {

    }

    @Override
    public double getTotalAporte(){
        return 0.0;
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
        socio.put("aportes", new JSONArray());
        socio.put("accionistas", new JSONArray());

        return  socio;
    }

}
