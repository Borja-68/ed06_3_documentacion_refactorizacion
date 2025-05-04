package org.ed06.model;

import java.time.LocalDate;

public class Reserva {
    public static final double descuentoVip = 0.9;
    public static final double descuentoAdicional = 0.95;
    private int id;
    private Habitacion habitacion;
    private Cliente cliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double precioTotal;

    public Reserva(int id, Habitacion habitacion, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = calcularPrecioFinal();
    }

    public int getId() {
        return id;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    /**
     * Calcula el precio final, Reduciendolo si el cliente es vip
     * y si el intervalo de fechas es mayor a 7
     *
     * @return Precio final
     */
    public double calcularPrecioFinal() {
        //calculamos los días de la reserva
        int n = fechaFin.getDayOfYear() - fechaInicio.getDayOfYear();
        // Calculamos el precio base de la habitación por el número de noches de la reserva
        double pb = habitacion.getPrecioBase() * n;
        // Declaramos la variable para almacenar el precio final
        double pf = pb;

        // Si el cliente es VIP, aplicamos un descuento del 10%
        if (cliente.esVip) {
            pf *= descuentoVip;
        }


        // Si el intervalo de fechas es mayor a 7 días, aplicamos un descuento adicional del 5%
        if (n > 7) {
            pf *= descuentoAdicional;
        }

        // Devolvemos el precio final
        return pf;
    }

    public void mostrarReserva() {
        System.out.println("Reserva #" + id);
        System.out.println("Habitación #" + habitacion.getNumero() + " - Tipo: " + habitacion.getTipo() + " - Precio base: " + habitacion.getPrecioBase());
        System.out.println("Cliente: " + cliente.nombre);
        System.out.println("Fecha de inicio: " + fechaInicio.toString());
        System.out.println("Fecha de fin: " + fechaFin.toString());
        System.out.printf("Precio total: %.2f €\n", precioTotal);
    }
}
