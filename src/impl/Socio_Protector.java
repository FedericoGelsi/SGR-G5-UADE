package impl;

import api.Aporte;
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

}
