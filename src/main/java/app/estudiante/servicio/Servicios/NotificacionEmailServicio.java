package app.estudiante.servicio.Servicios;

import app.estudiante.servicio.InterfacesServicios.INotificacionEmailServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacionEmailServicio implements INotificacionEmailServicio {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.host:}")
    private String mailHost;

    @Value("${app.mail.from:}")
    private String fromAddress;

    @Override
    public void enviarOTPDocente(String email, String nombreDocente, String otpCode) {
        if (mailHost.isBlank() || fromAddress.isBlank()) {
            throw new IllegalStateException(
                    "Configure MAIL_HOST y MAIL_FROM para habilitar la recuperación por correo.");
        }

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(fromAddress);
        mensaje.setTo(email);
        mensaje.setSubject("Código para recuperar su contraseña");
        mensaje.setText("""
                Hola %s,

                Su código de verificación para cambiar la contraseña es: %s

                El código vence en 5 minutos. Si usted no solicitó este cambio, ignore este correo.
                """.formatted(nombreDocente, otpCode));

        mailSender.send(mensaje);
    }
}
