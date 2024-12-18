package co.org.ccb.constituciones.lineabase.transversal.util;

import co.org.ccb.constituciones.lineabase.errores.entrada.UnauthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Map;

public class UtilidadesApi {
    public static UsuarioSesion session;
    public static void initSession(HttpServletRequest request){
        var autorizacion = request.getHeader("Authorization");
        if (Strings.isEmpty(autorizacion)) throw new UnauthorizedException("Usuario no autenticado");
        var respuesta = decodificarJwt(autorizacion.split("Bearer ")[1]);

        long exp = Long.parseLong(respuesta.get("exp").toString());
        LocalDateTime expirationDateTime = Instant.ofEpochSecond(exp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        //if (expirationDateTime.isBefore(LocalDateTime.now(ZoneId.systemDefault()))) throw new UnauthorizedException("Token expirado");
        UsuarioSesion usuario = UsuarioSesion.builder()
                .service(request.getRequestURI())
                .usuario(respuesta.get("user_name").toString())
                .token(autorizacion.split("Bearer ")[1])
                .build();
        UtilidadesApi.session = usuario;
    }

    public static Map<String, Object> decodificarJwt(String token) {
        try{
            var mapper = new ObjectMapper();
            String[] chunks = token.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();

            String payload = new String(decoder.decode(chunks[1]));
            Map<String, Object> map = mapper.readValue(payload, Map.class);
            return map;
        }catch (Exception e) {
            throw new UnauthorizedException("Usuario no valido: " + e.getMessage());
        }
    }
}
