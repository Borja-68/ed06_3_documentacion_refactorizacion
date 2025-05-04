package org.ed06.app;

import org.ed06.model.Cliente;
import org.ed06.model.Habitacion;
import org.ed06.model.Hotel;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Aplicacion
 */
public class Main {
    static Scanner scanner = new Scanner(System.in);
/**
 * Opciones de el menu
 */
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

        // Mostramos el menú
        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case REGISTRAR_HABITACION:
                    registrarHabitacion(hotel);
                    break;
                case LISTAR_HABITACIONES_DISPONIBLES:
                    hotel.listarHabitacionesDisponibles();
                    break;
                case RESERVAR_HABITACION:
                    reservaHabitacion(hotel);
                    break;
                case LISTAR_RESERVAS:
                    hotel.listarReservas();
                    break;
                case LISTAR_CLIENTES:
                    hotel.listarClientes();
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
     *reserva una habitacion
     * pidiendo la id del cliente
     * su tipo de habitacion
     * fecha de entrada
     * y fecha de salida
     * @param hotel Hotel donde se reserva
     */
    private static void reservaHabitacion(Hotel hotel) {
        String tipo;
        System.out.println("Introduce el id del cliente: ");
        int clienteId = scanner.nextInt();
        tipo =tipoHabitacion();
        LocalDate fechaEntrada = getFecha("Introduce la fecha de entrada (año): ", "Introduce la fecha de entrada (mes): ", "Introduce la fecha de entrada (día): ");
        LocalDate fechaSalida = getFecha("Introduce la fecha de salida (año): ", "Introduce la fecha de salida (mes): ", "Introduce la fecha de salida (día): ");
        int numeroHabitacion = hotel.reservarHabitacion(clienteId, tipo, fechaEntrada,
            fechaSalida);
        System.out.println("Datos de la habitacion");
        Habitacion habitacion = hotel.getHabitacion(numeroHabitacion);
        System.out.println(
            "Habitación #" + habitacion.getNumero() + " - Tipo: " + habitacion.getTipo()
                + " - Precio base: " + habitacion.getPrecioBase());
        System.out.println("Número de habitación reservada: " + numeroHabitacion);
    }

    /**
     * pide al usuario una fecha
     * @param x texto para el anio
     * @param x1 texto para el mes
     * @param x2 texto para el dia
     * @return Fecha introducida por el usuario
     */
    private static LocalDate getFecha(String x, String x1, String x2) {
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
        String tipo;
        tipo = tipoHabitacion();
        System.out.println("Introduce el precio base de la habitación: ");
        double precioBase = scanner.nextDouble();
        scanner.nextLine();
        hotel.registrarHabitacion(tipo, precioBase);
        System.out.println("Habitación registrada: " + tipo + " - Precio base: " + precioBase);
    }

    /**
     * Hace que el usuario elija el tipo de habitacion
     *
     * @return Devuelve el tipo introducido
     */
    private static String tipoHabitacion() {
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
        String nombre;
        String email;
        String dni;

        nombre = nombreCliente();
        email = emailCliente();
        dni = dniCliente();
        System.out.println("¿Es VIP? (true/false): ");
        boolean esVip = scanner.nextBoolean();
        hotel.registrarCliente(nombre, email, dni, esVip);
    }

    /**
     * Pide el dni del usuario
     *
     * @return Un dni valido
     */
    private static String dniCliente() {
        String dni;
        while (true) {
            try {
                System.out.println("Introduce el DNI del cliente: ");
                dni = scanner.next();
                Cliente.validarDni(dni);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("DNI no válido. Inténtalo de nuevo.");
            }
        }
        return dni;
    }

    /**
     * Pide el email del usuario
     *
     * @return Un email valido
     */
    private static String emailCliente() {
        String email;
        while (true) {
            try {
                System.out.println("Introduce el email del cliente: ");
                email = scanner.next();
                Cliente.validarEmail(email);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Email no válido. Inténtalo de nuevo.");
            }
        }
        return email;
    }

    /**
     * Pide el nombre del usuario
     *
     * @return Un nombre valido
     */
    private static String nombreCliente() {
        String nombre;
        while(true) {
            try {
                System.out.println("Introduce el nombre del cliente: ");
                nombre = scanner.next();
                Cliente.validarNombre(nombre);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Nombre no válido. Inténtalo de nuevo.");
            }
        }
        return nombre;
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