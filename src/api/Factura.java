package api;

public interface Factura {
    int getIDFatura();

    double getMontoFactura();

    String getCUITemisor();

    String getCUITdestinatario();

    String getEstado();
}
