package impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class Socio_Protector extends Socio implements api.Socio_Protector {
    private ArrayList<Aporte> Aportes;


    /*======CONSTRUCTOR=======*/

    public Socio_Protector(String CUIT, String RazonSocial, Date FinicAct, String tipoEmpresa, String actPrincipal, String direccion, String email) {
        super(CUIT, RazonSocial, FinicAct, tipoEmpresa, actPrincipal, direccion, email);
    }

    /*======CLASS FUNCTIONS=======*/
    @Override
    public void agregarAporte(double cantidad, File documento){

    }

    @Override
    public void retirarAporte(double cantidad){

    }

    @Override
    public double getTotalAporte(){
        return 0.0;
    }

}
