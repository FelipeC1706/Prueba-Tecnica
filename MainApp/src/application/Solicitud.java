package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Solicitud {
    private final StringProperty tipo;
    private final StringProperty descripcion;
    private final StringProperty fecha;

    public Solicitud(String tipo, String descripcion, String fecha) {
        this.tipo = new SimpleStringProperty(tipo);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.fecha = new SimpleStringProperty(fecha);
    }

    public StringProperty tipoProperty() {
        return tipo;
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public StringProperty fechaProperty() {
        return fecha;
    }
}
