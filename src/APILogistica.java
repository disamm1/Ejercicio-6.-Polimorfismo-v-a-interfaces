// Representa una API de logistica capaz de cotizar, rastrear y generar guias.
public class APILogistica extends IntegracionAPI implements Documentable {
    // Atributos
    private String zonaCobertura;
    private String moneda;
    private double costoBase;

    // Constructor
    public APILogistica(String id, String nombre, String urlBase, String apikey,
            double latenciaPromedio, String zonaCobertura, String moneda, double costoBase) {
        super(id, nombre, urlBase, apikey, latenciaPromedio);
        if (zonaCobertura == null || zonaCobertura.trim().isEmpty()) {
            throw new IllegalArgumentException("La zona de cobertura no puede ser nula o vacía");
        }
        if (moneda == null || moneda.trim().isEmpty()) {
            throw new IllegalArgumentException("La moneda no puede ser nula o vacía");
        }
        if (costoBase < 0  || Double.isNaN(costoBase) || Double.isInfinite(costoBase) ) {
            throw new IllegalArgumentException("El costo base no puede ser negativo");
        }
        this.zonaCobertura = zonaCobertura;
        this.moneda = moneda;
        this.costoBase = costoBase;
    }

    // Getters
    public String getZonaCobertura() {
        return zonaCobertura;
    }

    public double getCostoBase() {
        return costoBase;
    }

    public String getMoneda() {
        return moneda;
    }

    // Setters
    public void setZonaCobertura(String zona) {
        if (zona == null || zona.trim().isEmpty()) {
            throw new IllegalArgumentException("La zona de cobertura no puede ser nula o vacía");
        }
        this.zonaCobertura = zona;
    }

    public void setCostoBase(double costo) {
        if (costo < 0  || Double.isNaN(costo) || Double.isInfinite(costo) ) {
            throw new IllegalArgumentException("El costo base no puede ser negativo");
        }
        this.costoBase = costo;
    }

    public void setMoneda(String moneda) {
        if (moneda == null || moneda.trim().isEmpty()) {
            throw new IllegalArgumentException("La moneda no puede ser nula o vacía");
        }
        this.moneda = moneda;
    }

    // Calcula el costo del envio con una tarifa de 5 por cada kilo.
    public double cotizarEnvio(double peso) {
        if (peso <= 0 || Double.isNaN(peso) || Double.isInfinite(peso)) {
            throw new IllegalArgumentException(
                    "El peso debe ser un número válido y mayor a cero");
        }

        if (!conectada) {
            return Double.NaN;
        }

        return costoBase + (peso * 5);
    }

    // Consulta el estado simulado de un paquete a partir de su guia.
    public String rastrearPaquete(String guia) {
        if (guia == null || guia.trim().isEmpty()) {
            throw new IllegalArgumentException("La guía no puede ser nula o vacía");
        }
        if (!conectada) {
            return "No se puede rastrear porque la API está desconectada";
        }
        return "El paquete con guía " + guia + " está en tránsito";
    }

    // Genera una guia de envio en el formato solicitado.
    @Override
    public String generarDocumento(String formato) {
        if (formato == null || formato.trim().isEmpty()) {
            throw new IllegalArgumentException("El formato no puede ser nulo o vacío");
        }

        if (!conectada) {
            return "No se puede generar el documento porque la API está desconectada";
        }

        return "Guía de envío generada en formato " + formato;
    }

    // Genera una guia usando el formato predeterminado.
    @Override
    public String generarDocumento() {
        return generarDocumento("TXT");
    }

    // Ejecuta la operacion principal definida por IntegracionAPI.
    @Override
    public String ejecutarOperacion() {
        if (!conectada) {
            return "No se puede ejecutar la operación porque la API está desconectada";
        }

        return "La API " + nombre
                + " está disponible para realizar operaciones logísticas en la zona "
                + zonaCobertura;
    }

    // Muestra la configuracion particular de la API de logistica.
    @Override
    public String toString() {
        return super.toString() +
                "\nZona de cobertura: " + zonaCobertura +
                "\nMoneda: " + moneda +
                "\nCosto base: " + costoBase +
                "\nTarifa por kilo: 5" +
                "\n--------------------------";
    }

}
