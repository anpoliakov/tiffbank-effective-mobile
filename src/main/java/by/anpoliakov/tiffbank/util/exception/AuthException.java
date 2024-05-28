package by.anpoliakov.tiffbank.util.exception;

/**
 * Исключение используется для ошибок аутентификации и авторизации.
 */
public class AuthException extends RuntimeException {
    public AuthException(String msg) {
        super(msg);
    }
}
