package api;

public interface Socio_Participe extends Socio {
    /*======CLASSS FUNCTIONS=======*/
    void addContragarantias();

    void solicitarOperacion();

    void altaLineaCredito();

    void saldarDeuda();

    float CalcularContragarantias(String CUIT);
}
