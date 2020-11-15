package api;

import java.time.LocalDate;

public interface Verificaciones {
    //Compara una fecha entregada por parametro contra la fecha actual y devuelve Menor si la fecha ingresada es en el
    // pasado, Mayor si la fecha ingresada es en el futuro o Hoy si la fecha ingresada es la actual.
    String fechavshoy(LocalDate fecha);

    //Chequea que el formato de CUIT ingresado sea valido y que los datos ingresads sean numericos
    boolean CUITValido(String CUIT);

    //Chequea que un String este compuesto unicamente por numeros
    boolean esnumerico(String datos);

    //Chequea el formato de fecha en el String recibido y que los datos ingresados sean numericos
    boolean fechavalida(String fechacheck);

    //Chequea la vigencia de una linea de credito
    boolean lineacreditovigente(String CUITSocio);

    //Chequea que el monto disponible de una linea de credito sea suficiente
    boolean lineasuficiente(String CUITSocio, float montooperacion);

    //Chequea que el monto total de contragarantias presentadas sea suficiente para operar
    boolean contragarantiassuficientes(String CUITSocio,float montooperacion);

    //Chequea que un socio sea participe
    boolean essocioparticipe(String CUITSocio);

    //Chequea que un socio no deba facturas por mas del 10% del tope de la linea de credito
    boolean debefacturas(String CUITSocio);
}

