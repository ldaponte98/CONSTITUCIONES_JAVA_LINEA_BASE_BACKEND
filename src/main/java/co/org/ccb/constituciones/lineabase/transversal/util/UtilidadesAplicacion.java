package co.org.ccb.constituciones.lineabase.transversal.util;

public class UtilidadesAplicacion {
    public static RespuestaBase responder(String mensaje, Object detalle){
        return RespuestaBase.builder()
                .exito(Boolean.TRUE)
                .mensaje(mensaje)
                .detalle(detalle)
                .build();
    }
}
