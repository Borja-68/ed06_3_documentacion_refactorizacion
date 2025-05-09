package org.ed06.model;

import java.time.LocalDate;
import java.util.*;

public class Hotel {
    public static final int reservasParaVip = 3;
    private String nombreHotel;
    private String direccionHotel;
    private String telefonoHotel;

    private final Map<Integer,Cliente> clientes = new HashMap<>();
    private final List<Habitacion> habitaciones = new ArrayList<>();
    private final Map<Integer,List<Reserva>> reservasPorHabitacion = new HashMap<>();

    public Hotel(String nombreHotel, String direccionHotel, String telefonoHotel) {
        this.nombreHotel = nombreHotel;
        this.direccionHotel = direccionHotel;
        this.telefonoHotel = telefonoHotel;
    }

    /** Método para agregar una nueva habitación al hotel
     *
     * @param tipo Tipo de la habitacion
     * @param precioBase Precio de la habitacion
     */
    public void registrarHabitacion(String tipo, double precioBase) {
        Habitacion habitacion = new Habitacion(habitaciones.size() + 1, tipo, precioBase);
        habitaciones.add(habitacion);
        reservasPorHabitacion.put(habitacion.getNumeroHabitacion(), new ArrayList<>());
    }

    /**
     * Registra varias habitaciones en el hotel
     *
     * @param tipos Tipos de las habitaciones
     * @param preciosBase Precios de las habitaciones
     *
     */
    public void registrarHabitaciones(List<String> tipos, List<Double> preciosBase) {
        for(int i = 0; i < tipos.size(); i++) {
            Habitacion habitacion = new Habitacion(habitaciones.size() + 1, tipos.get(i), preciosBase.get(i));
            habitaciones.add(habitacion);
            reservasPorHabitacion.put(habitacion.getNumeroHabitacion(), new ArrayList<>());
        }
    }

    /**
     * Muestra las habitaciones que estan disponibles
     * comprobando si estan disponibles primero
     */
    public void mostrarHabitacionesDisponibles() {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.isHabitacionDisponible()) {
                System.out.println("Habitación #" + habitacion.getNumeroHabitacion() + " - Tipo: " + habitacion.getTipoHabitacion() + " - Precio base: " + habitacion.getPrecioBaseHabitacion());
            }
        }
    }

    /**
     * Busca una habitacion mediante su numero
     *
     * @param numero Numero de la habitacion
     * @return Devuelve la habitacion que se busca
     */
    public Habitacion getHabitacion(int numero) {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.getNumeroHabitacion() == numero) {
                return habitacion;
            }
        }
        return null;
    }

    /**
     * Método para realizar una reserva.
     * comprobando si la habitacion no esta vacia, el cliente existe y la fecha es correcta
     *
     * @param clienteId Id del cliente que quiere reservar
     * @param tipo Indica el tipo de habitacion
     * @param fechaEntrada Indica la fecha de entrada
     * @param fechaSalida Indica la fecha de entrada
     *
     * @return Devuelve el numero de la habitacion y en caso de error devuelve un numero negativo
     */
    public int reservarHabitacion(int clienteId, String tipo, LocalDate fechaEntrada, LocalDate fechaSalida) {
        // Comprobamos si hay habitaciones en el hotel
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones en el hotel");
            return -4;
        }//comprobamos si existe el cliente
        if (this.clientes.get(clienteId) == null) {
            System.out.println("No existe el cliente con id " + clienteId);
            return -3;
        }
        Cliente cliente = this.clientes.get(clienteId);
        // comprobamos si las fechas son coherentes
        if (fechaEntrada.isAfter(fechaSalida)) {
            System.out.println("La fecha de entrada es posterior a la fecha de salida");
            return -2;
        }

        //buscamos una habitación disponible
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getTipoHabitacion().equals(tipo.toUpperCase()) && habitacion.isHabitacionDisponible()) {
                // Comprobamos si el cliente pasa a ser vip tras la nueva reserva
                compruebaClienteVip(cliente);

                // Creamos la reserva
                Reserva reserva = new Reserva(reservasPorHabitacion.size() + 1, habitacion, cliente, fechaEntrada, fechaSalida);
                reservasPorHabitacion.get(habitacion.getNumeroHabitacion()).add(reserva);

                // Marcamos la habitación como no disponible
                habitacion.reservarHabitacionDisponible();

                System.out.println("Reserva realizada con éxito");
                return habitacion.getNumeroHabitacion();
            }
        }
        // si no hay habitaciones disponibles del tipo solicitado, mostramos un mensaje
        System.out.println("No hay habitaciones disponibles del tipo " + tipo);
        return -1;

    }

    /**
     * Comprueba si el cliente puede convertirse en VIP
     * comprobando si ha hecho al menos 3 reservas antes de la fecha actual
     *
     * @param cliente Cliente que se quiere comprovar si puede ser VIP
     */
    private void compruebaClienteVip(Cliente cliente) {
        int numReservas = (int) reservasPorHabitacion.values().stream().flatMap(Collection::stream)
                .filter(reservaCliente -> reservaCliente.getCliente().equals(cliente))
                .filter(reservaCliente -> reservaCliente.getFechaInicio().isAfter(LocalDate.now().minusYears(1))).count();
        if (numReservas > reservasParaVip && !cliente.ClienteEsVip) {
            cliente.ClienteEsVip = true;
            System.out.println("El cliente " + cliente.nombreCliente + " ha pasado a ser VIP");
        }
    }


    public void mostrarReservas() {
        reservasPorHabitacion.forEach((key, value) -> {
            System.out.println("Habitación #" + key);
            value.forEach(reserva -> System.out.println(
                "Reserva #" + reserva.getId() + " - Cliente: " + reserva.getCliente().nombreCliente
                    + " - Fecha de entrada: " + reserva.getFechaInicio()
                    + " - Fecha de salida: " + reserva.getFechaFin()));
        });
    }


    public void mostrarClientes() {
        for(Cliente cliente : clientes.values()) {
            System.out.println("Cliente #" + cliente.idCliente + " - Nombre: " + cliente.nombreCliente + " - DNI: " + cliente.dniCliente + " - VIP: " + cliente.ClienteEsVip);
        }
    }

    /**
     * Registra un nuevo cliente
     *
     * @param nombre Nombre del cliente
     * @param email Email del cliente
     * @param dni Dni del cliente
     * @param esVip Indica si el cliente es VIP o no
     */
    public void registrarCliente(String nombre, String email, String dni, boolean esVip) {
        Cliente cliente = new Cliente(clientes.size() + 1, nombre, dni, email, esVip);
        clientes.put(cliente.idCliente, cliente);
    }
}
