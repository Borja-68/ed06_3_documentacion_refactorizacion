package org.ed06.model;

public class Cliente {

    public static final int longitudNombreMinima = 3;
    public static final String formatoEmailValido = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
    public static final String formatoDNIValido = "[0-9]{8}[A-Z]";
    public int idCliente;
    public String nombreCliente;
    public String dniCliente;
    public String emailCliente;
    public boolean ClienteEsVip;

    public Cliente(int idCliente, String nombreCliente, String dniCliente, String emailCliente, boolean ClienteEsVip) {
        this.idCliente = idCliente;
        if(validarNombre(nombreCliente)) {
            this.nombreCliente = nombreCliente;
        }
        if(validarDni(dniCliente)) {
            this.dniCliente = dniCliente;
        }
        if(validarEmail(emailCliente)) {
            this.emailCliente = emailCliente;
        }
        this.ClienteEsVip = ClienteEsVip;
    }

    /**
     * Comprueba si un nombre es valido
     * viendo si no esta vacio y si el nombre tiene mas de 3 caracteres, sin espacios
     * @param nombre Nombre del cliente
     * @return True o false dependiendo de si el nombre es valido o no
     */
    public static boolean validarNombre(String nombre) {
        // Comprobamos que el nombre no sea nulo, esté vacio y tenga al menos 3 caracteres eliminando espacios inciales y finales
        if (nombre == null || nombre.trim().length() < longitudNombreMinima) {
            throw new IllegalArgumentException("El nombre no es válido");
        }
        return true;
    }

    /**
     * Comprueba si un email es valido
     * comprobando el email escrito con el formato que tiene que ser
     * @param email Email del cliente
     * @return True o false dependiendo de si el nombre es valido o no
     */
    public static boolean validarEmail(String email) {
        if (!email.matches(formatoEmailValido)) {
            throw new IllegalArgumentException("El email no es válido");
        }
        return true;
    }

    /**
     * Comprueba si un Dni es valido
     * comprobando que empiece por 8 numeros y acabe en letra
     * @param dni Dni del cliente
     * @return True o false dependiendo de si el nombre es valido o no
     */
    public static boolean validarDni(String dni) {
        if (!dni.matches(formatoDNIValido)) {
            throw new IllegalArgumentException("El DNI no es válido");
        }
        return true;
    }

}