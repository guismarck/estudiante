package app.estudiante.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.GraphicsEnvironment;
import java.util.Arrays;

@Component
public class FontDiagnostic implements CommandLineRunner {
 private static final Logger logger =
            LoggerFactory.getLogger(FontDiagnostic.class);

    @PostConstruct
    public void verificarFuentes() {

        logger.info("========== INICIO DIAGNOSTICO DE FUENTES ==========");

        try {
            GraphicsEnvironment environment =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();

            String[] fonts = environment.getAvailableFontFamilyNames();

            logger.info("Total de fuentes encontradas: {}", fonts.length);

            Arrays.stream(fonts)
                    .sorted()
                    .forEach(font -> logger.info("FUENTE DISPONIBLE: {}", font));

        } catch (Exception e) {
            logger.error("ERROR AL OBTENER LAS FUENTES", e);
        }

        logger.info("========== FIN DIAGNOSTICO DE FUENTES ==========");
    }
}
