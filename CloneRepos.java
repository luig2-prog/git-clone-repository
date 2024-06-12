import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CloneRepos {


    public static void main(String[] args) {
        // Definir la ruta del directorio de destino

//        Scanner scanner = new Scanner(System.in);

//        Si decide pedir el directorio
//        System.out.println("PATH: ");
//        String targetDirectoryPath = scanner.next();

        String targetDirectoryPath = "C:\\dllo\\weblogic\\change-to-8080";

        // Repositorios a clonar
        List<String> repositories = Arrays.asList(
                "https://github.com/user/repo.git",
                "https://github.com/user/repo-v2.git"
        );

        // Crear el directorio de destino si no existe
        File targetDirectory = new File(targetDirectoryPath);
        if (!targetDirectory.exists()) {
            if (targetDirectory.mkdirs()) {
                System.out.println("Directorio de destino creado: " + targetDirectoryPath);
            } else {
                System.err.println("No se pudo crear el directorio de destino: " + targetDirectoryPath);
                return;
            }
        } else {
            System.out.println("Directorio de destino ya existe: " + targetDirectoryPath);
        }

        // Clonar cada repositorio
        for (String repoUrl : repositories) {
            System.out.println("***************************************************************************************************************************************************************************************************");
            String repoName = repoUrl.substring(repoUrl.lastIndexOf('/') + 1);
            File repoDir = new File(targetDirectory, repoName);
            System.out.println("Clonando " + repoUrl + " en " + repoDir.getAbsolutePath());

            try {
                ProcessBuilder builder = new ProcessBuilder(
                        "git", "clone", repoUrl, repoDir.getAbsolutePath()
                );
                builder.redirectErrorStream(true);
                Process process = builder.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                process.waitFor();
                if (process.exitValue() == 0) {
                    System.out.println("Repositorio clonado: " + repoUrl);
                } else {
                    System.err.println("Error al clonar el repositorio " + repoUrl);
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("Error al clonar el repositorio " + repoUrl + ": " + e.getMessage());
            }
            System.out.println("***************************************************************************************************************************************************************************************************");
        }

        System.out.println("Todos los repositorios han sido clonados en " + targetDirectoryPath);
    }
}
