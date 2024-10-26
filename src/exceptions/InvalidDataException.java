package exceptions;

/**
 * Exception thrown when invalid data is encountered during the file reading.
 * This class extends {@link Exception} to provide a specific type of
 * exception for cases where the data being processed does not meet
 * the required criteria.
 *
 * @author Horánszki Patrik Donát - CJJ14N
 */
public class InvalidDataException extends Exception {
    public InvalidDataException(String message) { super(message); }
}
