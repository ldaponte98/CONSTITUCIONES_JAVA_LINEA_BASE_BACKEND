package co.org.ccb.constituciones.lineabase.infraestructura.interceptores;

import co.org.ccb.constituciones.lineabase.transversal.util.UtilidadesApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ValidarAutenticacionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        UtilidadesApi.initSession(request);
        return true; // true permite que la solicitud continúe, false la detiene
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        // Se ejecuta después de que la respuesta ha sido enviada al cliente
        System.out.println("Respuesta enviada con estado: " + response.getStatus());
    }
}
