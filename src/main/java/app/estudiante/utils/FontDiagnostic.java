
package ni.gob.inss.estudiante.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.GraphicsEnvironment;
import java.util.Arrays;

@Component
public class FontDiagnostic implements CommandLineRunner {

    @Override
    public void run(String... args) {

        System.out.println("========== FUENTES DISPONIBLES EN JAVA ==========");

        String[] fonts = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();

        Arrays.stream(fonts)
                .sorted()
                .forEach(System.out::println);

        System.out.println("========== FIN DEL DIAGNÓSTICO ==========");
    }
}
