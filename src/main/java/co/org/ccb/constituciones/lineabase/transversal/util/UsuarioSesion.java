package co.org.ccb.constituciones.lineabase.transversal.util;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
public class UsuarioSesion {
    private String usuario;
    private String service;
    private String token;
}
