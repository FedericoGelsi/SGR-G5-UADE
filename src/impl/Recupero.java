package impl;

public class Recupero implements api.Recupero {
    private String tipo;
    private int monto;
    private String idSocioPleno;
    private String idDeuda;

    /*======CONSTRUCTOR=======*/

    public Recupero(String tipo, int monto, String idSocioPleno, String idDeuda) {
        this.tipo = tipo;
        this.monto = monto;
        this.idSocioPleno = idSocioPleno;
        this.idDeuda = idDeuda;
    }

    /*======GETTERS=======*/

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public int getMonto() {
        return monto;
    }

    @Override
    public String getIdSocioPleno() {
        return idSocioPleno;
    }

    @Override
    public String getIdDeuda() {
        return idDeuda;
    }
}
