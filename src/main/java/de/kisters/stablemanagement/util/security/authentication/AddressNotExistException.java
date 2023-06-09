package de.kisters.stablemanagement.util.security.authentication;

public class AddressNotExistException extends Exception {
    private static final long serialVersionUID = 5861310537366287163L;

    public AddressNotExistException() {
        super();
    }

    public AddressNotExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AddressNotExistException(final String message) {
        super(message);
    }

    public AddressNotExistException(final Throwable cause) {
        super(cause);
    }

}
