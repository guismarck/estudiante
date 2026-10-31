package app.estudiante.servicio.Servicios;

import app.estudiante.servicio.InterfacesServicios.ITokenStatelessServicio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class TokenStatelessServicioImpl implements ITokenStatelessServicio {

    @Value("${jwt.secret:}")
    private String secretKey;

    @Override
    public String generarTokenRecuperacion(Long docenteId, String otpCode, long expiracionEpochMilli) {
        String payload = docenteId + ":" + expiracionEpochMilli;
        String firma = calcularHmac(payload + ":" + otpCode);
        String tokenCompleto = payload + ":" + firma;
        return Base64.getUrlEncoder().withoutPadding().encodeToString(
                tokenCompleto.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean validarTokenYOTP(String tokenRecuperacion, Long docenteId, String otpIngresado) {
        try {
            if (tokenRecuperacion == null || docenteId == null || otpIngresado == null) {
                return false;
            }
            String decodificado = new String(Base64.getUrlDecoder().decode(tokenRecuperacion), StandardCharsets.UTF_8);
            String[] partes = decodificado.split(":", -1);

            if (partes.length != 3) {
                return false;
            }

            Long idToken = Long.parseLong(partes[0]);
            long expiracionEpochMilli = Long.parseLong(partes[1]);
            String firmaRecibida = partes[2];

            if (!idToken.equals(docenteId)) {
                return false;
            }

            if (System.currentTimeMillis() > expiracionEpochMilli) {
                throw new ResponseStatusException(
                        BAD_REQUEST,
                        "El código de seguridad ha expirado. Solicite uno nuevo.");
            }

            String payloadEsperado = idToken + ":" + expiracionEpochMilli;
            String firmaEsperada = calcularHmac(payloadEsperado + ":" + otpIngresado.trim());

            return MessageDigest.isEqual(
                    firmaRecibida.getBytes(StandardCharsets.UTF_8),
                    firmaEsperada.getBytes(StandardCharsets.UTF_8)
            );
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private String calcularHmac(String data) {
        if (secretKey == null || secretKey.isBlank()) {
            throw new IllegalStateException("Configure JWT_SECRET para habilitar la recuperación.");
        }

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Error al generar firma criptográfica de seguridad", e);
        }
    }
}