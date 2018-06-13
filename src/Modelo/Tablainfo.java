package Modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author Sistemas2
 */
public class Tablainfo
{
    private final IntegerProperty NUMEROCLIENTE;
    private final StringProperty NOMBRECLIENTE;
    private final StringProperty VENTA;
    private final StringProperty RUTA;
    private final StringProperty CLAVE;

    public final int getNUMEROCLIENTE() {
        return NUMEROCLIENTE.get();
    }

    public final void setNUMEROCLIENTE(int value) {
        NUMEROCLIENTE.set(value);
    }

    public IntegerProperty NUMEROCLIENTEProperty() {
        return NUMEROCLIENTE;
    }

    public final String getNOMBRECLIENTE() {
        return NOMBRECLIENTE.get();
    }

    public final void setNOMBRECLIENTE(String value) {
        NOMBRECLIENTE.set(value);
    }

    public StringProperty NOMBRECLIENTEProperty() {
        return NOMBRECLIENTE;
    }

    public final String getVENTA() {
        return VENTA.get();
    }

    public final void setVENTA(String value) {
        VENTA.set(value);
    }

    public StringProperty VENTAProperty() {
        return VENTA;
    }

    public final String getRUTA() {
        return RUTA.get();
    }

    public final void setRUTA(String value) {
        RUTA.set(value);
    }

    public StringProperty RUTAProperty() {
        return RUTA;
    }

    public final String getCLAVE() {
        return CLAVE.get();
    }

    public final void setCLAVE(String value) {
        CLAVE.set(value);
    }

    public StringProperty CLAVEProperty() {
        return CLAVE;
    }
    
    public Tablainfo(int nCliente,String nombreC, String Vent, String Ruta, String Clave)
    {
        this.NUMEROCLIENTE=new SimpleIntegerProperty(nCliente);
        this.NOMBRECLIENTE=new SimpleStringProperty(nombreC);
        this.VENTA=new SimpleStringProperty(Vent);
        this.RUTA=new SimpleStringProperty(Ruta);
        this.CLAVE=new SimpleStringProperty(Clave);
    }
}
