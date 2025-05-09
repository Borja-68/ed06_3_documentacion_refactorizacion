package org.ed06.model;

public class Habitacion {
    public static final int habitacionSimple = 1;
    public static final int habitacionDoble = 3;
    public static final int habitacionSuite = 4;
    public static final int habitacionLiteras = 8;
    public static final int habitacionBase = 1;
    private int numeroHabitacion;
    private String tipoHabitacion; // "SIMPLE", "DOBLE", "SUITE"
    private double precioBaseHabitacion;

    /**Todo pendiente cambiar la forma de gestionar la
     * disponibilidad en base a las fechas de las reservas
     */
    private boolean habitacionDisponible;

    public Habitacion(int numeroHabitacion, String tipoHabitacion, double precioBaseHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
        this.tipoHabitacion = tipoHabitacion;
        this.precioBaseHabitacion = precioBaseHabitacion;
        this.habitacionDisponible = true;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public double getPrecioBaseHabitacion() {
        return precioBaseHabitacion;
    }

    public boolean isHabitacionDisponible() {
        return habitacionDisponible;
    }

    /**
     * Obtiene el numero de huespedes máximos
     * depepensiendo del tipo de habitacion
     *
     * @return Un numero dependiendo del tipo de Huespedes maximos
     */
    public double obtenerNumMaxHuespedes() {
        return switch (tipoHabitacion) {
            case "SIMPLE" -> habitacionSimple;
            case "DOBLE" -> habitacionDoble;
            case "SUITE" -> habitacionSuite;
            case "LITERAS" -> habitacionLiteras;
            default -> habitacionBase;
        };
    }


    public void reservarHabitacionDisponible() {
        if (habitacionDisponible) {
            System.out.println("Habitación #" + numeroHabitacion + " ya reservada");
        }
        habitacionDisponible = true;
    }
}
