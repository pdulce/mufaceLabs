package api.gateway.soa;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Enumeration;

@SpringBootApplication
@RestController
public class SoaProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoaProxyApplication.class, args);
    }

    // Endpoint para manejar todas las solicitudes entrantes
    @RequestMapping(value = "/{path}", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<?> proxyRequest(@PathVariable String path, @RequestBody(required = false) Object body, HttpMethod method, HttpServletRequest request) {
        // Verificar token de autenticación
        String token = request.getHeader("Authorization");
        if (token == null || !validarToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso no autorizado");
        }

        // Registrar evento en Elasticsearch
        registrarEvento(path, method, request, body);

        // Procesar la solicitud reenviándola al servicio correspondiente
        String targetUrl = "http://servidor:puerto/" + path; // Cambiar por la URL del servicio correspondiente
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.add(headerName, request.getHeader(headerName));
        }
        HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(targetUrl, method, httpEntity, String.class);
    }

    // Método para validar el token de autenticación
    private boolean validarToken(String token) {
        // Aquí deberías implementar la lógica de validación del token
        // Devuelve true si el token es válido, false en caso contrario
        return true;
    }

    // Método para registrar eventos en Elasticsearch
    private void registrarEvento(String path, HttpMethod method, HttpServletRequest request, Object body) {
        // Aquí deberías implementar la lógica para enviar el evento a Elasticsearch
        // Puedes utilizar una biblioteca como RestTemplate para hacer una solicitud POST al endpoint de Elasticsearch
        // Ejemplo:
        // RestTemplate restTemplate = new RestTemplate();
        // restTemplate.postForObject("http://localhost:9200/eventos/_doc", evento, Void.class);
    }
}