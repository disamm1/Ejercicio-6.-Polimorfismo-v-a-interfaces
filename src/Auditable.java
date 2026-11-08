// Define las operaciones que pueden registrar y consultar un historial.
public interface Auditable {

    // Declaracion de metodos
    void registrarOperacion(String operacion);

    String obtenerHistorial();
}
