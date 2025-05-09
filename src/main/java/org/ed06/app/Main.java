package org.ed06.app;

import org.ed06.model.Cliente;
import org.ed06.model.Habitacion;
import org.ed06.model.Hotel;

import java.time.LocalDate;
import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);

    private static final int REGISTRAR_HABITACION = 1;
    private static final int LISTAR_HABITACIONES_DISPONIBLES = 2;
    private static final int RESERVAR_HABITACION = 11;
    private static final int LISTAR_RESERVAS = 12;
    private static final int LISTAR_CLIENTES = 21;
    private static final int REGISTRAR_CLIENTE = 22;
    private static final int SALIR = 0;
    /**
     * Constructor por defecto de la clase Main.
     * No realiza ninguna acción.
     */
    public Main(){}
    /**
     * Menu de la aplicacion
     * @param args Argumentos
     */
    public static void main(String[] args) {
        Hotel hotel = iniciaHotel();

        while (true) {
            // Mostramos el menú
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case REGISTRAR_HABITACION:
                    registrarHabitacion(hotel);
                    break;
                case LISTAR_HABITACIONES_DISPONIBLES:
                    hotel.mostrarHabitacionesDisponibles();
                    break;
                case RESERVAR_HABITACION:
                    reservaHabitacion(hotel);
                    break;
                case LISTAR_RESERVAS:
                    hotel.mostrarReservas();
                    break;
                case LISTAR_CLIENTES:
                    hotel.mostrarClientes();
                    break;
                case REGISTRAR_CLIENTE:
                    registrarCliente(hotel);
                    break;
                case SALIR:
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
 }

    /**
     * inicia un hotel
     * y crea unos clientes y habitaciones
     *
     * @return el hotel creado con unas habitaciones y clientes
     */

    private static Hotel iniciaHotel() {
        // Creamos un menú para el administrador con las diferentes opciones proporcionadas
        Hotel hotel = new Hotel("El mirador", "Calle Entornos de Desarrollo 6", "123456789");

        // Registramos algunas habitaciones
        hotel.registrarHabitacion("SIMPLE", 50);
        hotel.registrarHabitacion("DOBLE", 80);
        hotel.registrarHabitacion("SUITE", 120);
        hotel.registrarHabitacion("LITERAS", 200);
        hotel.registrarHabitacion("SIMPLE", 65);
        hotel.registrarHabitacion("DOBLE", 100);
        hotel.registrarHabitacion("SUITE", 150);
        hotel.registrarHabitacion("LITERAS", 250);

        // Registramos algunos clientes
        hotel.registrarCliente("Daniel", "daniel@daniel.com", "12345678A", true);
        hotel.registrarCliente("Adrián", "adrian@adrian.es", "87654321B", false);
        return hotel;
    }

    /**
     *reserva una habitacion
     * pidiendo la id del cliente
     * su tipo de habitacion
     * fecha de entrada
     * y fecha de salida
     * @param hotel Hotel donde se reserva
     */
    private static void reservaHabitacion(Hotel hotel) {
        String tipoHabitacion;
        System.out.println("Introduce el id del cliente: ");
        int clienteId = scanner.nextInt();
        tipoHabitacion = pedirTipoHabitacion();
        LocalDate fechaEntrada = pedirFecha("Introduce la fecha de entrada (año): ", "Introduce la fecha de entrada (mes): ", "Introduce la fecha de entrada (día): ");
        LocalDate fechaSalida = pedirFecha("Introduce la fecha de salida (año): ", "Introduce la fecha de salida (mes): ", "Introduce la fecha de salida (día): ");
        int numeroHabitacion = hotel.reservarHabitacion(clienteId, tipoHabitacion, fechaEntrada,
            fechaSalida);
        System.out.println("Datos de la habitacion");
        Habitacion habitacion = hotel.getHabitacion(numeroHabitacion);
        System.out.println(
            "Habitación #" + habitacion.getNumeroHabitacion() + " - Tipo: " + habitacion.getTipoHabitacion()
                + " - Precio base: " + habitacion.getPrecioBaseHabitacion());
        System.out.println("Número de habitación reservada: " + numeroHabitacion);
    }

    /**
     * pide al usuario una fecha
     *
     * @param x texto para el anio
     * @param x1 texto para el mes
     * @param x2 texto para el dia
     * @return Fecha introducida por el usuario
     */
    private static LocalDate pedirFecha(String x, String x1, String x2) {
        System.out.println(x);
        int anioEntrada = scanner.nextInt();
        scanner.nextLine();
        System.out.println(x1);
        int mesEntrada = scanner.nextInt();
        scanner.nextLine();
        System.out.println(x2);
        int diaEntrada = scanner.nextInt();
        scanner.nextLine();
        return LocalDate.of(anioEntrada, mesEntrada, diaEntrada);
    }

    /**
     * registra una habitacion en el hotel
     * pidiendo su tipo y precio
     *
     * @param hotel Hotel en el que se registra la habitacion
     */
    private static void registrarHabitacion(Hotel hotel) {
        String tipoHabitacion;
        tipoHabitacion = pedirTipoHabitacion();
        System.out.println("Introduce el precio base de la habitación: ");
        double precioBase = scanner.nextDouble();
        scanner.nextLine();
        hotel.registrarHabitacion(tipoHabitacion, precioBase);
        System.out.println("Habitación registrada: " + tipoHabitacion + " - Precio base: " + precioBase);
    }

    /**
     * Hace que el usuario elija el tipo de habitacion
     *
     * @return Devuelve el tipo introducido
     */
    private static String pedirTipoHabitacion() {
        String tipo;
        System.out.println("Introduce el tipo de habitación (SIMPLE, DOBLE, SUITE): ");
        tipo = scanner.nextLine();
        return tipo;
    }

    /**
     * registra un cliente pidiendo el nombre,email,dni y si es VIP
     *
     * @param hotel Hotel en el que se realiza el registro
     */
    private static void registrarCliente(Hotel hotel) {
        String nombreCliente;
        String emailCliente;
        String dniCliente;

        nombreCliente = pedirNombreCliente();
        emailCliente = pedirEmailCliente();
        dniCliente = pedirDniCliente();
        System.out.println("¿Es VIP? (true/false): ");
        boolean esVip = scanner.nextBoolean();
        hotel.registrarCliente(nombreCliente, emailCliente, dniCliente, esVip);
    }

    /**
     * Pide el dni del usuario
     * no dejando salir hasta que se introduzca uno valido 
     *
     * @return Un dni valido
     */
    private static String pedirDniCliente() {
        String dniCliente;
        while (true) {
            try {
                System.out.println("Introduce el DNI del cliente: ");
                dniCliente = scanner.next();
                Cliente.validarDni(dniCliente);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("DNI no válido. Inténtalo de nuevo.");
            }
        }
        return dniCliente;
    }

    /**
     * Pide el email del usuario
     * no dejando salir hasta que se introduzca uno valido 
     * 
     * @return Un email valido
     */
    private static String pedirEmailCliente() {
        String emailCliente;
        while (true) {
            try {
                System.out.println("Introduce el emailCliente del cliente: ");
                emailCliente = scanner.next();
                Cliente.validarEmail(emailCliente);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Email no válido. Inténtalo de nuevo.");
            }
        }
        return emailCliente;
    }

    /**
     * Pide el nombre del usuario
     * no dejando salir hasta que se introduzca uno valido 
     * 
     * @return Un nombre valido
     */
    private static String pedirNombreCliente() {
        String nombreCliente;
        while(true) {
            try {
                System.out.println("Introduce el nombreCliente del cliente: ");
                nombreCliente = scanner.next();
                Cliente.validarNombre(nombreCliente);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Nombre no válido. Inténtalo de nuevo.");
            }
        }
        return nombreCliente;
    }

    private static void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1. Registrar habitación");
        System.out.println("2. Listar habitaciones disponibles");
        System.out.println("11. Reservar habitación");
        System.out.println("12. Listar reservas");
        System.out.println("21. Listar clientes");
        System.out.println("22. Registrar cliente");
        System.out.println("0. Salir");
    }
}