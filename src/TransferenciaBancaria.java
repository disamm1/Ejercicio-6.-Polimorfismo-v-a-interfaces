// Representa una integracion para cobros y reintegros mediante un banco.
public class TransferenciaBancaria extends IntegracionAPI implements Transaccional {
    // Atributos
    private String banco;
    private String moneda;
    private double comision;
    
    // Constructor
    public TransferenciaBancaria(String id, String nombre, String urlBase, String apikey,
            double latenciaPromedio, String banco, String moneda, double comision) {
        super(id, nombre, urlBase, apikey, latenciaPromedio);
        if (banco == null || banco.trim().isEmpty()) {
            throw new IllegalArgumentException("El banco no puede ser nulo o vacío");
        }
        if (moneda == null || moneda.trim().isEmpty()) {
            throw new IllegalArgumentException("La moneda no puede ser nula o vacía");
        }
        if (comision < 0 || comision > 1 || Double.isNaN(comision) || Double.isInfinite(comision)) {
            throw new IllegalArgumentException("La comisión debe estar entre 0 y 1");
        }
        this.banco = banco;
        this.moneda = moneda;
        this.comision = comision;
    }

    // Getters
    public String getBanco() {
        return banco;
    }

    public String getMoneda() {
        return moneda;
    }

    public double getComision() {
        return comision;
    }

    // Setters
    public void setBanco(String banco) {
        if (banco == null || banco.trim().isEmpty()) {
            throw new IllegalArgumentException("El banco no puede ser nulo o vacío");
        }
        this.banco = banco;
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
            throw new IllegalArgumentException("El monto debe ser un número válido y mayor a cero");
        }

        if (!conectada) {
            return false;
        }
        double montoTotal = monto + (monto * comision);
        System.out.println("Procesando cobro de " + montoTotal + " " + moneda
                + " a través del banco " + banco);
        return true;
    }

    @Override
    public boolean procesarReintegro(double monto) {
        if (monto <= 0 || Double.isNaN(monto) || Double.isInfinite(monto)) {
            throw new IllegalArgumentException(
                    "El monto debe ser un número válido y mayor a cero");
        }

        if (!conectada) {
            return false;
        }

        System.out.println("Procesando reintegro de " + monto
                + " " + moneda + " a través del banco " + banco);

        return true;
    }

    // Ejecuta la operacion principal definida por IntegracionAPI.
    @Override
    public String ejecutarOperacion() {
        if (!conectada) {
            return "No se puede ejecutar la operación porque la API está desconectada";
        }

        return "La API " + nombre
                + " está disponible para procesar transferencias bancarias a través del banco " + banco;
    }

    // Muestra la configuracion particular de la transferencia bancaria.
    @Override
    public String toString() {
        return super.toString() +
                "\nBANCO: " + banco +
                "\nMONEDA: " + moneda +
                "\nCOMISIÓN: " + (comision * 100) + "%" +
                "\n-------------------------";
    }

}
