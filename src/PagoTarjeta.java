// Representa una pasarela de pago que procesa operaciones con tarjeta.
public class PagoTarjeta extends IntegracionAPI implements Transaccional {
    // Atributos
    private String marcaTarjeta;
    private String moneda;
    private double comision;

    // Constructor
    public PagoTarjeta(String id, String nombre, String urlBase, String apikey,
            double latenciaPromedio, String marcaTarjeta, String moneda, double comision) {
        super(id, nombre, urlBase, apikey, latenciaPromedio);
        if (marcaTarjeta == null || marcaTarjeta.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca de la tarjeta no puede ser nula o vacía");
        }
        if (moneda == null || moneda.trim().isEmpty()) {
            throw new IllegalArgumentException("La moneda no puede ser nula o vacía");
        }
        if (comision < 0 || comision > 1 || Double.isNaN(comision) || Double.isInfinite(comision)) {
            throw new IllegalArgumentException("La comisión debe estar entre 0 y 1");
        }
        this.marcaTarjeta = marcaTarjeta;
        this.moneda = moneda;
        this.comision = comision;
    }

    // Getters
    public String getMarcaTarjeta() {
        return marcaTarjeta;
    }

    public String getMoneda() {
        return moneda;
    }

    public double getComision() {
        return comision;
    }

    // Setters
    public void setMarcaTarjeta(String marcaTarjeta) {
        if (marcaTarjeta == null || marcaTarjeta.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca de la tarjeta no puede ser nula o vacía");
        }
        this.marcaTarjeta = marcaTarjeta;
    }

    public void setMoneda(String moneda) {
        if (moneda == null || moneda.trim().isEmpty()) {
            throw new IllegalArgumentException("La moneda no puede ser nula o vacía");
        }
        this.moneda = moneda;
    }

    public void setComision(double comision) {
        if (comision < 0 || comision > 1 || Double.isNaN(comision) || Double.isInfinite(comision)) {
            throw new IllegalArgumentException("La comisión debe estar entre 0 y 1");
        }
        this.comision = comision;
    }

    // Implementacion de los metodos de la interfaz Transaccional.
    @Override
    public boolean procesarCobro(double monto) {
        if (monto <= 0 || Double.isNaN(monto) || Double.isInfinite(monto)) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }

        if (!conectada) {
            return false;
        }
        double montoTotal = monto + (monto * comision);
        System.out.println("Procesando cobro de " + montoTotal + " " + moneda
                + " con tarjeta " + marcaTarjeta);
        return true;
    }

    @Override
    public boolean procesarReintegro(double monto) {
        if (monto <= 0 || Double.isNaN(monto) || Double.isInfinite(monto)) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }

        if (!conectada) {
            return false;
        }

        System.out.println("Procesando reintegro de " + monto
                + " " + moneda + " con tarjeta " + marcaTarjeta);

        return true;
    }

    // Ejecuta la operacion principal definida por IntegracionAPI.
    @Override
    public String ejecutarOperacion() {
        if (!conectada) {
            return "No se puede ejecutar la operación porque la API está desconectada";
        }

        return "La API " + nombre
                + " está disponible para procesar pagos con tarjeta "
                + marcaTarjeta;
    }

    // Muestra la configuracion particular del pago con tarjeta.
    @Override
    public String toString() {
        return super.toString() +
                "\nMARCA DE LA TARJETA: " + marcaTarjeta +
                "\nMONEDA: " + moneda +
                "\nCOMISIÓN: " + (comision * 100) + "%" +
                "\n-------------------------";
    }
}
