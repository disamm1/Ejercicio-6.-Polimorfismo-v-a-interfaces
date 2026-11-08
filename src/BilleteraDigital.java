// Representa una integracion para pagos mediante una billetera digital.
public class BilleteraDigital extends IntegracionAPI implements Transaccional {
    // Atributos
    private String proveedorBilletera;
    private String moneda;
    private double comision;

    // Constructor
    public BilleteraDigital(String id, String nombre, String urlBase, String apikey,
            double latenciaPromedio, String proveedorBilletera, String moneda, double comision) {
        super(id, nombre, urlBase, apikey, latenciaPromedio);
        if (proveedorBilletera == null || proveedorBilletera.trim().isEmpty()) {
            throw new IllegalArgumentException("El proveedor de billetera no puede ser nulo o vacío");
        }
        if (moneda == null || moneda.trim().isEmpty()) {
            throw new IllegalArgumentException("La moneda no puede ser nula o vacía");
        }
        if (comision < 0 || comision > 1 || Double.isNaN(comision) || Double.isInfinite(comision)) {
            throw new IllegalArgumentException("La comisión no puede ser negativa ni mayor a 1");
        }
        this.proveedorBilletera = proveedorBilletera;
        this.moneda = moneda;
        this.comision = comision;
    }

    // Getters
    public String getProveedorBilletera() {
        return proveedorBilletera;
    }

    public String getMoneda() {
        return moneda;
    }

    public double getComision() {
        return comision;
    }

    // Setters
    public void setProveedorBilletera(String proveedor) {
        if (proveedor == null || proveedor.trim().isEmpty()) {
            throw new IllegalArgumentException("El proveedor no puede ser nulo o vacío");
        }
        this.proveedorBilletera = proveedor;
    
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
                + " a través de la billetera digital " + proveedorBilletera);
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
                + " " + moneda + " a través de la billetera digital " + proveedorBilletera);

        return true;
    }

    // Ejecuta la operacion principal definida por IntegracionAPI.
    @Override
    public String ejecutarOperacion() {
        if (!conectada) {
            return "No se puede ejecutar la operación porque la API está desconectada";
        }

        return "La API " + nombre
                + " está disponible para procesar pagos a través de la billetera digital "
                + proveedorBilletera;
    }

    // Muestra la configuracion particular de la billetera digital.
    @Override
    public String toString() {
        return super.toString() +
                "\nPROVEEDOR DE LA BILLETERA: " + proveedorBilletera +
                "\nMONEDA: " + moneda +
                "\nCOMISIÓN: " + (comision * 100) + "%" +
                "\n-------------------------";
    }
}
