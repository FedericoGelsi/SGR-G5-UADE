package impl;

import java.util.Date;

public class OPTipo1 extends Operacion implements api.OPTipo1 {
    private Date fechaVencimiento;
    private String banco;
    private int numeroCheque;
    private String CUITfirmante;
    private String tipo;
    private float tasaDeDescuento;

    /*======CONSTRUCTOR=======*/

    public OPTipo1(Date fechaVencimiento, String banco, int numeroCheque, String CUITfirmante, float tasaDeDescuento, String CUITSolicitante) {
        super(CUITSolicitante);
        this.fechaVencimiento = fechaVencimiento;
        this.banco = banco;
        this.numeroCheque = numeroCheque;
        this.CUITfirmante = CUITfirmante;
        this.tasaDeDescuento = tasaDeDescuento;
    }

    /*======GETTERS=======*/

    @Override
    public Date getFechaVencimiento() {
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

}
