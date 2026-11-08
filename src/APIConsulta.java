// Representa una API destinada a consultar informacion de un servicio.
public class APIConsulta extends IntegracionAPI {
    // Atributos
    private String tipoConsulta;

    // Constructor
    public APIConsulta(String id, String nombre, String urlBase, String apikey,
            double latenciaPromedio, String tipoConsulta) {
        super(id, nombre, urlBase, apikey, latenciaPromedio);
        if (tipoConsulta == null || tipoConsulta.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de consulta no puede ser nulo o vacío");
        }
        this.tipoConsulta = tipoConsulta;
    }
    // Getter
    public String getTipoConsulta() {
        return tipoConsulta;
    }

    // Setter
    public void setTipoConsulta(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de consulta no puede ser nulo o vacío");
        }
        this.tipoConsulta = tipo;
    }
    // Realiza la consulta configurada si la API se encuentra conectada.
    public String consultarInformacion() {
        if (!conectada) {
            return "No se puede consultar porque la API está desconectada";
        }

        switch (tipoConsulta.toUpperCase()) {
            case "ESTADO":
                return "Estado de la API: " + consultarEstado();

            case "DISPONIBILIDAD":
                return "El servicio " + nombre + " está disponible";

            case "LATENCIA":
                return "Latencia promedio: " + latenciaPromedio + " ms";

            default:
                return "Tipo de consulta no reconocido";
        }
    }

    // Ejecuta la operacion principal de la API.
    @Override
    public String ejecutarOperacion() {
        return consultarInformacion();
    }

    // Muestra la configuracion particular de la API de consulta.
    @Override
    public String toString() {
        return super.toString() +
                "\nTIPO DE CONSULTA: " + tipoConsulta +
                "\n-------------------------";
    }
}
