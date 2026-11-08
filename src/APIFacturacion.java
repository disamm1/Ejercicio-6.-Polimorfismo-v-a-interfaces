// Representa una API de facturacion que genera documentos y registra auditorias.
public class APIFacturacion extends IntegracionAPI implements Documentable, Auditable {
    // Atributos
    private String nitEmisor;
    private String establecimiento;
    private String historial;

    // Constructor
    public APIFacturacion(String id, String nombre, String urlBase, String apikey,
            double latenciaPromedio, String nitEmisor, String establecimiento) {
        super(id, nombre, urlBase, apikey, latenciaPromedio);
        if (nitEmisor == null || nitEmisor.trim().isEmpty()) {
            throw new IllegalArgumentException("El NIT del emisor no puede ser nulo o vacío");
        }
        if (establecimiento == null || establecimiento.trim().isEmpty()) {
            throw new IllegalArgumentException("El establecimiento no puede ser nulo o vacío");
        }
        
        this.nitEmisor = nitEmisor;
        this.establecimiento = establecimiento;
        this.historial = "- Sin historial";
    }

    // Getters
    public String getNitEmisor() {
        return nitEmisor;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    // Setters
    public void setNitEmisor(String nit) {
        if (nit == null || nit.trim().isEmpty()) {
            throw new IllegalArgumentException("El NIT del emisor no puede ser nulo o vacío");
        }
        this.nitEmisor = nit;
    }

    public void setEstablecimiento(String establecimiento) {
        if (establecimiento == null || establecimiento.trim().isEmpty()) {
            throw new IllegalArgumentException("El establecimiento no puede ser nulo o vacío");
        }
        this.establecimiento = establecimiento;
    }

    // Certifica una factura y registra la operacion en el historial.
    public String certificarFactura(String datos) {
        if (datos == null || datos.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Los datos de la factura no pueden ser nulos o vacíos");
        }

        if (!conectada) {
            return "No se puede certificar la factura porque la API está desconectada";
        }

        registrarOperacion("Factura certificada: " + datos);

        return "Factura certificada correctamente";
    }

    // Genera la representacion de una factura en el formato solicitado.
    @Override
    public String generarDocumento(String formato) {
        if (formato == null || formato.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El formato no puede ser nulo o vacío");
        }

        if (!conectada) {
            return "No se puede generar el documento porque la API está desconectada";
        }

        if (formato.equalsIgnoreCase("XML")) {
            return "<Factura>\n" +
                    "    <NITEmisor>" + nitEmisor + "</NITEmisor>\n" +
                    "    <Establecimiento>" + establecimiento + "</Establecimiento>\n" +
                    "    <Estado>Documento simulado</Estado>\n" +
                    "</Factura>";
        }

        if (formato.equalsIgnoreCase("TXT")) {
            return "FACTURA SIMULADA\n" +
                    "NIT Emisor: " + nitEmisor + "\n" +
                    "Establecimiento: " + establecimiento;
        }

        return "Formato no soportado";
    }

    // Genera una factura usando el formato predeterminado.
    @Override
    public String generarDocumento() {
        return generarDocumento("XML");
    }

    // Ejecuta la operacion principal definida por IntegracionAPI.
    @Override
    public String ejecutarOperacion() {
        if (!conectada) {
            return "No se puede ejecutar la operación porque la API está desconectada";
        }

        return "La API " + nombre
                + " está disponible para realizar operaciones de facturación con NIT "
                + nitEmisor + " y establecimiento " + establecimiento;
    }

    // Devuelve el historial de operaciones de facturacion.
    @Override
    public String obtenerHistorial() {
        if (!conectada) {
            return "No se puede obtener el historial porque la API está desconectada";
        }

        return "Historial de facturación: " + historial;
    }

    // Registra una operacion en el historial de auditoria.
    @Override
    public void registrarOperacion(String operacion) {
        if (operacion == null || operacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La operación no puede ser nula o vacía");
        }

        if (!conectada) {
            System.out.println("No se puede registrar la operación porque la API está desconectada");
            return;
        }

        if (historial.equals("- Sin historial")) {
            historial = "\nOperación registrada: " + operacion;
        } else {
            historial += "\nOperación registrada: " + operacion;
        }

        System.out.println("Operación registrada correctamente");
    }

    // Muestra la configuracion particular de la API de facturacion.
    @Override
    public String toString() {
        return super.toString() +
                "\nNIT del emisor: " + nitEmisor +
                "\nEstablecimiento: " + establecimiento +
                "\nHistorial: " +
                "\n" + historial +
                "\n--------------------------";
    }
}
