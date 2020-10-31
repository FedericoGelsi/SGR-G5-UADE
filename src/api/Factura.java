package api;

public interface Factura {
    int getIDFatura();

    float getMontoFactura();

    String getCUITemisor();

    String getCUITdestinatario();

    String getEstado();
}
