package impl;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class OPTipo1 extends Operacion implements api.OPTipo1 {
    private LocalDate fechaVencimiento;
    private String banco;
    private int numeroCheque;
    private String CUITfirmante;
    private String tipo;
    private float tasaDeDescuento;
    private float importetotal;
    private String estado;
    private String CUITSocio;
    private int numerooperacion;

    /*======CONSTRUCTOR=======*/

    public OPTipo1(LocalDate fechaVencimiento, String banco, int numeroCheque, String CUITfirmante, float tasaDeDescuento, String CUITSocio,String tipo,float importetotal,String estado,int numerooperacion) {
        super(CUITSocio);
        this.fechaVencimiento = fechaVencimiento;
        this.banco = banco;
        this.numeroCheque = numeroCheque;
        this.CUITfirmante = CUITfirmante;
        this.tasaDeDescuento = tasaDeDescuento;
        this.tipo = tipo;
        this.importetotal = importetotal;
        this.estado = estado;
        this.CUITSocio = CUITSocio;
        this.numerooperacion = numerooperacion;
    }

    public OPTipo1(JSONObject jsonOPT1){
        super(jsonOPT1);
        this.fechaVencimiento = (LocalDate) jsonOPT1.get("fechavencimiento");
        this.banco = (String) jsonOPT1.get("banco");
        this.numeroCheque = Integer.parseInt(jsonOPT1.get("numerocheque").toString()) ;
        this.CUITfirmante = (String) jsonOPT1.get("CUITfirmante");
        this.tasaDeDescuento = (float) jsonOPT1.get("tasadedescuento");
        this.tipo = (String) jsonOPT1.get("tipo");
        this.importetotal = (float) jsonOPT1.get("importetotal");
        this.estado = (String) jsonOPT1.get("estado");
        this.CUITSocio = (String) jsonOPT1.get("CUITSocio");
        this.numerooperacion = Integer.parseInt(jsonOPT1.get("numerooperacion").toString());
    }

    /*======GETTERS=======*/

    @Override
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public String getBanco() {
        return banco;
    }

    @Override
    public int getNumeroCheque() {
        return numeroCheque;
    }

    @Override
    public String getCUITfirmante() {
        return CUITfirmante;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public float getTasaDeDescuento() {
        return tasaDeDescuento;
    }

    @Override
    public void setFechaMonetizado(LocalDate fechaMonetizado) {

    }
    public JSONObject toJSON(){
        JSONObject OP1 = new JSONObject();
        OP1.put("fechavencimiento", this.fechaVencimiento.toString());
        OP1.put("banco", this.banco);
        OP1.put("numerocheque", this.numeroCheque);
        OP1.put("CUITfirmante", this.CUITfirmante);
        OP1.put("tasadedescuento", this.tasaDeDescuento);
        OP1.put("tipo", this.tipo);
        OP1.put("importetotal",this.importetotal);
        OP1.put("estado",this.estado);
        OP1.put("CUITSocio",this.CUITSocio);
        OP1.put("numerooperacion",this.numerooperacion);
        return OP1;
    }
}
