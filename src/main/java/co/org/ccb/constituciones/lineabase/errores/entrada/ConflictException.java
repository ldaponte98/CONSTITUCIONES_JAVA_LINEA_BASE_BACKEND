package co.org.ccb.constituciones.lineabase.errores.entrada;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {

    private final transient Object error;

    public ConflictException(Object error, String message) {
        super(message);
        this.error = error;
    }
}
