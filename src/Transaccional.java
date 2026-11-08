// Define las operaciones financieras comunes de las integraciones de pago.
public interface Transaccional {

    // Declaracion de metodos
    boolean procesarCobro(double monto);

    boolean procesarReintegro(double monto);
}
