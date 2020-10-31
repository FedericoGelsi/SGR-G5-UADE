package impl;

import java.util.Date;

public class OPTipo3 extends Operacion implements api.OPTipo3 {
    private int cantCuotas;
    private String banco;
    private float importeTotal;
    private float tasa;
    private Date fechaAcreditacion;
    private String sistema;

    /*======CONSTRUCTOR=======*/

    public OPTipo3(String CUITSolicitante, int cantCuotas, String banco, float importeTotal, float tasa, String sistema) {
        super(CUITSolicitante);
        this.cantCuotas = cantCuotas;
        this.banco = banco;
        this.importeTotal = importeTotal;
        this.tasa = tasa;
        this.sistema = sistema;
    }

    /*======GETTERS=======*/

    @Override
    public int getCantCuotas() {
        return cantCuotas;
    }

    @Override
    public String getBanco() {
        return banco;
    }

    @Override
    public float getImporteTotal() {
        return importeTotal;
    }

    @Override
    public float getTasa() {
        return tasa;
    }

    @Override
    public Date getFechaAcreditacion() {
        return fechaAcreditacion;
    }

    @Override
    public String getSistema() {
        return sistema;
    }

    /*======CLASS FUNCTIONS=======*/

    @Override
    public int getCuotasImpagas(){
        return 0;
    }
}
