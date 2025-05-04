package org.ed06.model;

public class Cliente {
    /**
     * Datos de el cliente
     */
    public int id;
    public String nombre;
    public String dni;
    public String email;
    public boolean esVip;

    public Cliente(int id, String nombre, String dni, String email, boolean esVip) {
        this.id = id;
        if(validarNombre(nombre)) {
            this.nombre = nombre;
        }
        if(validarDni(dni)) {
            this.dni = dni;
        }
        if(validarEmail(email)) {
            this.email = email;
        }
        this.esVip = esVip;
    }

    /**
     * Comprueba si un nombre es valido
     *
     * @param nombre Nombre del cliente
     * @return True o false dependiendo de si el nombre es valido o no
     */
    public static boolean validarNombre(String nombre) {
        // Comprobamos que el nombre no sea nulo, esté vacio y tenga al menos 3 caracteres eliminando espacios inciales y finales
        if (nombre == null || nombre.trim().length() < 3) {
            throw new IllegalArgumentException("El nombre no es válido");
        }
        return true;
    }

    /**
     * Comprueba si un email es valido
     *
     * @param email Email del cliente
     * @return True o false dependiendo de si el nombre es valido o no
     */
    public static boolean validarEmail(String email) {
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            throw new IllegalArgumentException("El email no es válido");
        }
        return true;
    }

    /**
     * Comprueba si un Dni es valido
     *
     * @param dni Dni del cliente
     * @return True o false dependiendo de si el nombre es valido o no
     */
    public static boolean validarDni(String dni) {
        if (!dni.matches("[0-9]{8}[A-Z]")) {
            throw new IllegalArgumentException("El DNI no es válido");
        }
        return true;
    }

}