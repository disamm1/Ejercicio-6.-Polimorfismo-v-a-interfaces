// Punto de entrada de la aplicacion.
public class Main {
    public static void main(String[] args) {

        // Crear el catalogo
        CatalogoIntegraciones catalogo = new CatalogoIntegraciones();

        // Cargar las 10 integraciones iniciales
        catalogo.cargarDatosIniciales();

        // Crear la vista
        VistaConsola vista = new VistaConsola();

        // Crear el controlador
        ControladorIntegraciones controlador =
            new ControladorIntegraciones(catalogo, vista);

        // Iniciar el sistema
        controlador.iniciar();
    }
}
