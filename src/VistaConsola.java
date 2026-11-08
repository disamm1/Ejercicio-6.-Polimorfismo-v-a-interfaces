import java.util.List;
import java.util.Scanner;

// Administra la entrada y salida del sistema por medio de la consola.
public class VistaConsola {
    // Atributo
    private Scanner scanner;

    // Constructor
    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    // Metodos
    public void mostrarMenu() {
        System.out.println("\n================================================");
        System.out.println("       SISTEMA DE GESTIÓN DE INTEGRACIONES");
        System.out.println("================================================");
        System.out.println("             ADMINISTRADOR DE APIs");
        System.out.println();
        System.out.println("  1. Listar todas las integraciones");
        System.out.println("  2. Buscar integración por ID");
        System.out.println("  3. Ordenar integraciones por latencia");
        System.out.println();
        System.out.println("  4. Conectar una API");
        System.out.println("  5. Desconectar una API");
        System.out.println("  6. Ejecutar operación de una API");
        System.out.println();
        System.out.println("  7. Procesar cobro");
        System.out.println("  8. Procesar reintegro");
        System.out.println();
        System.out.println("  9. Cotizar envío");
        System.out.println(" 10. Rastrear paquete");
        System.out.println();
        System.out.println(" 11. Generar documento");
        System.out.println(" 12. Certificar factura");
        System.out.println(" 13. Registrar operación de auditoría");
        System.out.println(" 14. Consultar historial de auditoría");
        System.out.println();
        System.out.println("  0. Salir");
        System.out.println("================================================");
    }

    // Leer la opcion seleccionada por el usuario
    public int leerOpcion() {
        System.out.print("Seleccione una opción: ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());
            return opcion;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Leer texto ingresado por el usuario
    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    // Mostrar todas las integraciones registradas
    public void mostrarIntegraciones(List<IntegracionAPI> lista) {
        System.out.println("\n================================================");
        System.out.println("          INTEGRACIONES REGISTRADAS");
        System.out.println("================================================");

        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay integraciones registradas.");
            return;
        }

        System.out.printf("%-10s %-25s %-15s%n", "ID", "NOMBRE", "ESTADO");
        System.out.println("------------------------------------------------");

        for (IntegracionAPI api : lista) {
            System.out.printf("%-10s %-25s %-15s%n",
                    api.getId(),
                    api.getNombre(),
                    api.consultarEstado());
        }

        System.out.println("================================================");
    }

    // Mostrar los detalles de una integracion
    public void mostrarDetalle(IntegracionAPI api) {
        if (api == null) {
            System.out.println("No se encontró la integración solicitada.");
            return;
        }

        System.out.println("\nDETALLES DE LA INTEGRACIÓN");
        System.out.println(api);
    }

    // Mostrar mensajes al usuario
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
