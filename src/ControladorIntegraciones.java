// Coordina las acciones de la vista con el catalogo de integraciones.
public class ControladorIntegraciones {
    // Atributos
    private CatalogoIntegraciones catalogo;
    private VistaConsola vista;

    // Constructor
    public ControladorIntegraciones(CatalogoIntegraciones catalogo, VistaConsola vista) {
        if (catalogo == null || vista == null) {
            throw new IllegalArgumentException("El catálogo y la vista no pueden ser nulos");
        }

        this.catalogo = catalogo;
        this.vista = vista;
    }
    // Inicia el sistema y mantiene activo el menu principal.
    public void iniciar() {
        int opcion = -1;

        while (opcion != 0) {
            vista.mostrarMenu();
            opcion = vista.leerOpcion();

            if (opcion != 0) {
                try {
                    ejecutarOpcion(opcion);
                } catch (IllegalArgumentException e) {
                    vista.mostrarMensaje("Error: " + e.getMessage());
                }
            }
        }

        vista.mostrarMensaje("Saliendo del sistema...");
    }

    // Lista todas las integraciones.
    public void listarIntegraciones() {
        vista.mostrarIntegraciones(catalogo.obtenerIntegraciones());
    }

    // Busca una integracion por ID.
    public void buscarIntegracion() {
        String id = vista.leerTexto("Ingrese el ID de la API: ");
        IntegracionAPI api = catalogo.buscarIntegracion(id);

        vista.mostrarDetalle(api);
    }

    // Ordena las integraciones por latencia.
    public void ordenarIntegraciones() {
        catalogo.ordenarPorLatencia();

        vista.mostrarMensaje("Integraciones ordenadas por latencia de menor a mayor:");
        vista.mostrarIntegraciones(catalogo.obtenerIntegraciones());
    }

    // Ejecuta la opcion seleccionada y delega cada tarea al objeto adecuado.
    public void ejecutarOpcion(int opcion) {

        if (opcion == 1) {
            listarIntegraciones();

        } else if (opcion == 2) {
            buscarIntegracion();

        } else if (opcion == 3) {
            ordenarIntegraciones();

        } else if (opcion == 4) {

            String id = vista.leerTexto("Ingrese el ID de la API que desea conectar: ");
            IntegracionAPI api = catalogo.buscarIntegracion(id);

            if (api == null) {
                vista.mostrarMensaje("No se encontró la API.");
                return;
            }

            api.conectar();
            vista.mostrarMensaje("API conectada correctamente.");

        } else if (opcion == 5) {

            String id = vista.leerTexto("Ingrese el ID de la API que desea desconectar: ");
            IntegracionAPI api = catalogo.buscarIntegracion(id);

            if (api == null) {
                vista.mostrarMensaje("No se encontró la API.");
                return;
            }

            api.desconectar();
            vista.mostrarMensaje("API desconectada correctamente.");

        } else if (opcion == 6) {

            String id = vista.leerTexto("Ingrese el ID de la API: ");
            IntegracionAPI api = catalogo.buscarIntegracion(id);

            if (api == null) {
                vista.mostrarMensaje("No se encontró la API.");
                return;
            }

            vista.mostrarMensaje(api.ejecutarOperacion());

        } else if (opcion == 7) {

            String id = vista.leerTexto("Ingrese el ID de la API de pago: ");
            IntegracionAPI api = catalogo.buscarIntegracion(id);

            if (!(api instanceof Transaccional)) {
                vista.mostrarMensaje("No se encontró una API que permita procesar cobros.");
                return;
            }

            double monto = leerNumeroPositivo("Ingrese el monto del cobro: ");

            Transaccional transaccion = (Transaccional) api;
            boolean resultado = transaccion.procesarCobro(monto);

            if (resultado) {
                vista.mostrarMensaje("Cobro procesado correctamente.");
            } else {
                vista.mostrarMensaje("No se pudo procesar el cobro. Verifique la conexión.");
            }

        } else if (opcion == 8) {

            String id = vista.leerTexto("Ingrese el ID de la API de pago: ");
            IntegracionAPI api = catalogo.buscarIntegracion(id);

            if (!(api instanceof Transaccional)) {
                vista.mostrarMensaje("Esta API no permite procesar reintegros o no existe.");
                return;
            }

            double monto = leerNumeroPositivo("Ingrese el monto del reintegro: ");

            boolean resultado = catalogo.procesarReintegro(api, monto);

            if (resultado) {
                vista.mostrarMensaje("Reintegro procesado correctamente.");
            } else {
                vista.mostrarMensaje("No se pudo procesar el reintegro. Verifique la conexión.");
            }

        } else if (opcion == 9) {

            String id = vista.leerTexto("Ingrese el ID de la API logística: ");
            IntegracionAPI api = catalogo.buscarIntegracion(id);

            if (!(api instanceof APILogistica)) {
                vista.mostrarMensaje("No se encontró una API logística con ese ID.");
                return;
            }

            APILogistica logistica = (APILogistica) api;

            if (!logistica.isConectada()) {
                vista.mostrarMensaje("La API logística está desconectada.");
                return;
            }

            double peso = leerNumeroPositivo("Ingrese el peso del paquete en kg: ");
            double costo = logistica.cotizarEnvio(peso);

            if (Double.isNaN(costo)) {
                vista.mostrarMensaje("No se pudo obtener la cotización.");
            } else {
                vista.mostrarMensaje("Costo estimado del envío: " + costo + " "
                        + logistica.getMoneda());
            }

        } else if (opcion == 10) {

            String id = vista.leerTexto("Ingrese el ID de la API logística: ");
            IntegracionAPI api = catalogo.buscarIntegracion(id);

            if (!(api instanceof APILogistica)) {
                vista.mostrarMensaje("No se encontró una API logística con ese ID.");
                return;
            }

            String guia = vista.leerTexto("Ingrese el número de guía: ");

            APILogistica logistica = (APILogistica) api;
            vista.mostrarMensaje(logistica.rastrearPaquete(guia));

        } else if (opcion == 11) {

            String id = vista.leerTexto("Ingrese el ID de la API: ");
            IntegracionAPI api = catalogo.buscarIntegracion(id);

            if (!(api instanceof Documentable)) {
                vista.mostrarMensaje("Esta API no permite generar documentos o no existe.");
                return;
            }

            String formato = vista.leerTexto(
                    "Ingrese el formato (XML, TXT o deje vacío para el predeterminado): ");

            Documentable documentable = (Documentable) api;
            String documento;

            if (formato.isEmpty()) {
                documento = catalogo.generarDocumento(documentable);
            } else {
                documento = catalogo.generarDocumento(documentable, formato);
            }

            vista.mostrarMensaje(documento);

        } else if (opcion == 12) {

            String id = vista.leerTexto("Ingrese el ID de la API de facturación: ");
            IntegracionAPI api = catalogo.buscarIntegracion(id);

            if (!(api instanceof APIFacturacion)) {
                vista.mostrarMensaje("No se encontró una API de facturación con ese ID.");
                return;
            }

            String datos = vista.leerTexto("Ingrese los datos de la factura: ");

            APIFacturacion facturacion = (APIFacturacion) api;
            vista.mostrarMensaje(facturacion.certificarFactura(datos));

        } else if (opcion == 13) {

            String id = vista.leerTexto("Ingrese el ID de la API: ");
            IntegracionAPI api = catalogo.buscarIntegracion(id);

            if (!(api instanceof Auditable)) {
                vista.mostrarMensaje("Esta API no permite registrar auditorías o no existe.");
                return;
            }

            String operacion = vista.leerTexto("Describa la operación: ");

            catalogo.auditar((Auditable) api, operacion);

        } else if (opcion == 14) {

            String id = vista.leerTexto("Ingrese el ID de la API: ");
            IntegracionAPI api = catalogo.buscarIntegracion(id);

            if (!(api instanceof Auditable)) {
                vista.mostrarMensaje("Esta API no permite consultar auditorías o no existe.");
                return;
            }

            Auditable auditable = (Auditable) api;
            vista.mostrarMensaje(auditable.obtenerHistorial());

        } else {
            vista.mostrarMensaje("Opción inválida. Intente nuevamente.");
        }
    }

    // Lee y valida los valores numericos positivos solicitados en el menu.
    private double leerNumeroPositivo(String mensaje) {

        String texto = vista.leerTexto(mensaje);
        double numero;

        try {
            numero = Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Debe ingresar un número válido.");
        }

        if (numero <= 0 || Double.isNaN(numero) || Double.isInfinite(numero)) {
            throw new IllegalArgumentException(
                    "El valor debe ser un número finito y mayor a cero.");
        }

        return numero;
    }
}
