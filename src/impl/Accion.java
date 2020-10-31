package impl;

public class Accion implements api.Accion {

    private char tipo;
    private String CUITPropietario;
    private String CUITEmisor;

    /*======GETTERS=======*/
    @Override
    public char getTipo() {
        return tipo;
    }

    @Override
    public String getCUITPropietario() {
        return CUITPropietario;
    }

    @Override
    public String getCUITEmisor() {
        return CUITEmisor;
    }

}
