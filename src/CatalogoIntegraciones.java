import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// Administra la unica lista polimorfica de integraciones del sistema.
public class CatalogoIntegraciones {
    // Atributos
    private ArrayList<IntegracionAPI> integraciones;

    // Constructor
    public CatalogoIntegraciones() {
        this.integraciones = new ArrayList<IntegracionAPI>();
    }

    // Agrega una integracion y evita identificadores duplicados.
    public void agregarIntegracion(IntegracionAPI integracion) {
        if (integracion == null) {
            throw new IllegalArgumentException(
                    "La integracion no puede ser nula");
        }

        if (integraciones.contains(integracion)) {
            throw new IllegalArgumentException(
                    "Ya existe una integracion con ese ID");
        }

        integraciones.add(integracion);
    }

    // Busca una integracion por su identificador.
    public IntegracionAPI buscarIntegracion(String id) {
        for (IntegracionAPI integracion : this.integraciones) {
            if (integracion.getId().equals(id)) {
                return integracion;
            }
        }

        return null;
    }

    // Devuelve las integraciones registradas para mostrarlas en la vista.
    public List<IntegracionAPI> obtenerIntegraciones() {
        return this.integraciones;
    }

    // Ordena la lista usando la latencia definida por Comparable.
    public void ordenarPorLatencia() {
        Collections.sort(integraciones);
    }

    // Agrega las diez integraciones requeridas al iniciar la aplicacion.
    public void cargarDatosIniciales() {
        // APIs de consulta
        agregarIntegracion(new APIConsulta(
                "API001", "Consulta de Estado",
                "https://api.consulta.com", "KEY001",
                120, "ESTADO"));

        agregarIntegracion(new APIConsulta(
                "API002", "Consulta de Latencia",
                "https://api.latencia.com", "KEY002",
                90, "LATENCIA"));

        // APIs de pago con tarjeta
        agregarIntegracion(new PagoTarjeta(
                "API003", "Visa",
                "https://api.visa.com", "KEY003",
                150, "Visa", "GTQ", 0.05));

        agregarIntegracion(new PagoTarjeta(
                "API004", "Mastercard",
                "https://api.mastercard.com", "KEY004",
                180, "Mastercard", "GTQ", 0.04));

        // Transferencias bancarias
        agregarIntegracion(new TransferenciaBancaria(
                "API005", "Banco Industrial",
                "https://api.bancoindustrial.com", "KEY005",
                200, "Banco Industrial", "GTQ", 0.02));

        agregarIntegracion(new TransferenciaBancaria(
                "API006", "Banrural",
                "https://api.banrural.com", "KEY006",
                170, "Banrural", "GTQ", 0.03));

        // Billeteras digitales
        agregarIntegracion(new BilleteraDigital(
                "API007", "PayPal",
                "https://api.paypal.com", "KEY007",
                130, "PayPal", "USD", 0.05));

        // APIs de logistica
        agregarIntegracion(new APILogistica(
                "API008", "Cargo Expreso",
                "https://api.cargoexpreso.com", "KEY008",
                110, "Guatemala", "GTQ", 25.0));

        agregarIntegracion(new APILogistica(
                "API009", "DHL",
                "https://api.dhl.com", "KEY009",
                160, "Internacional", "USD", 75.0));

        // API de facturacion
        agregarIntegracion(new APIFacturacion(
                "API010", "Certificador FEL",
                "https://api.fel.com", "KEY010",
                140, "1234567-8", "Tienda Central"));
    }

    // Procesa el reintegro solo cuando la integracion cumple el contrato.
    public boolean procesarReintegro(IntegracionAPI integracion, double monto) {
        if (integracion instanceof Transaccional) {
            Transaccional transaccion = (Transaccional) integracion;
            return transaccion.procesarReintegro(monto);
        }

        return false;
    }

    // Usa el formato predeterminado definido por la integracion documentable.
    public String generarDocumento(Documentable api) {
        return api.generarDocumento();
    }

    // Sobrecarga que permite solicitar un formato especifico.
    public String generarDocumento(Documentable api, String formato) {
        return api.generarDocumento(formato);
    }

    // Registra una operacion mediante el contrato Auditable.
    public void auditar(Auditable api, String operacion) {
        api.registrarOperacion(operacion);
    }
}
