package co.org.ccb.constituciones.lineabase.errores.entrada;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralErrorResponse {
    private String message;
    private Object error;
    private String path;
    private Integer status;

    public GeneralErrorResponse(String message, String error, String path, Integer status) {
        this.message = message;
        this.error = error;
        this.path = path;
        this.status = status;
    }


}
