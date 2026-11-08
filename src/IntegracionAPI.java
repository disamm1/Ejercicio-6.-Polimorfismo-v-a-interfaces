// Clase base para todas las integraciones externas administradas por el sistema.
public abstract class IntegracionAPI implements Comparable<IntegracionAPI> {

    // Atributos
    protected String id;
    protected String nombre;
    protected String urlBase;
    protected String apiKey;
    protected boolean conectada;
    protected double latenciaPromedio;

    // Constructor
    public IntegracionAPI(String id, String nombre, String urlBase,String apikey, double latenciaPromedio) {

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser nulo o vacío");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }

        if (urlBase == null || urlBase.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL base no puede ser nula o vacía");
        }

        if (apikey == null || apikey.trim().isEmpty()) {
            throw new IllegalArgumentException("La API key no puede ser nula o vacía");
        }

        if (latenciaPromedio < 0 || Double.isNaN(latenciaPromedio)
                || Double.isInfinite(latenciaPromedio)) {
            throw new IllegalArgumentException(
                "La latencia debe ser un número válido y no negativo"
            );
        }

        this.id = id;
        this.nombre = nombre;
        this.urlBase = urlBase;
        this.apiKey = apikey;
        this.conectada = false;
        this.latenciaPromedio = latenciaPromedio;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public String getApiKey() {
        return apiKey;
    }

    public boolean isConectada() {
        return conectada;
    }

    public double getLatenciaPromedio() {
        return latenciaPromedio;
    }

    // Setters
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }

        this.nombre = nombre;
    }

    public void setUrlBase(String urlbase) {
        if (urlbase == null || urlbase.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL base no puede ser nula o vacía");
        }

        this.urlBase = urlbase;
    }

    public void setApiKey(String apikey) {
        if (apikey == null || apikey.trim().isEmpty()) {
            throw new IllegalArgumentException("La API key no puede ser nula o vacía");
        }

        this.apiKey = apikey;
    }

    public void setLatenciaPromedio(double promedio) {
        if (promedio < 0 || Double.isNaN(promedio)
                || Double.isInfinite(promedio)) {
            throw new IllegalArgumentException(
                "La latencia debe ser un número válido y no negativo"
            );
        }

        this.latenciaPromedio = promedio;
    }

    // Operacion principal que cada tipo de API debe implementar.
    public abstract String ejecutarOperacion();

    // Metodos para administrar y consultar el estado de la conexion.
    public void desconectar() {
        this.conectada = false;
    }

    public void conectar() {
        this.conectada = true;
    }

    public String consultarEstado() {
        return conectada ? "Conectada" : "Desconectada";
    }

    // Comparamos la latencia promedio de una integracion con otra
    // para determinar cual es mayor, menor o igual
    @Override
    public int compareTo(IntegracionAPI otra) {
        return Double.compare(this.latenciaPromedio,
                              otra.latenciaPromedio);
    }

    // Sobreescribimos equals y hashCode para que dos integraciones
    // sean iguales si tienen el mismo id
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof IntegracionAPI)) {
            return false;
        }

        IntegracionAPI otra = (IntegracionAPI) obj;

        return this.id.equals(otra.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        String conexion;

        if (conectada) {
            conexion = "Sí";
        } else {
            conexion = "No";
        }

        return "-------------------------" +
                "\nID: " + id +
                "\nNOMBRE: " + nombre +
                "\nURL BASE: " + urlBase +
                "\nAPI KEY: ***************" +
                "\nCONECTADA: " + conexion +
                "\nLATENCIA PROMEDIO: " + latenciaPromedio + " ms";
    }
}
