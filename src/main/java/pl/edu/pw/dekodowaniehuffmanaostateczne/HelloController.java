package pl.edu.pw.dekodowaniehuffmanaostateczne;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Paths;

public class HelloController {
    private Stage stage;
    private HostServices hostServices;

    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;

    @FXML
    private TextField inputFilenameTextField;

    @FXML
    private TextField outputDirectoryNameTextField;

    @FXML
    private TextField outputFilenameTextField;

    @FXML
    private ScrollPane treeVisualizationPane;

    private HuffmanTreePane treePane;


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    public void displayTree(Tree tree) {
        treePane.displayTree(tree);
    }

    @FXML
    protected void initialize() {
        fileChooser = new FileChooser();
        directoryChooser = new DirectoryChooser();
        treePane = new HuffmanTreePane();

        treeVisualizationPane.setContent(treePane);
    }


    @FXML
    protected void onInputFileChooserButtonClick() {
        fileChooser.setTitle("Otwórz plik wejściowy...");

        File inputFile = fileChooser.showOpenDialog(stage);

        if (inputFile != null) {
            inputFilenameTextField.setText(inputFile.getAbsolutePath());
        } else {
            displayError("Błąd", "Nieprawidłowa ścieżka pliku wejściowego!");
        }
    }

    private void displayError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Pobierz główny Stage z kontrolera FXML
        Stage stage = (Stage) inputFilenameTextField.getScene().getWindow();
        alert.initOwner(stage);

        alert.showAndWait();
    }

    @FXML
    protected void onOutputDirectoryChooserButtonClick() {
        directoryChooser.setTitle("Wybierz katalog wyjściowy...");

        File directory = directoryChooser.showDialog(stage);

        if (directory != null)
            outputDirectoryNameTextField.setText(directory.getAbsolutePath());
    }

    @FXML
    protected void onDecodeButtonClick() {
        String inputFilename = inputFilenameTextField.getText();
        String outputDirectoryName = outputDirectoryNameTextField.getText();
        String outputFilename = outputFilenameTextField.getText();

        if (inputFilename.isBlank()) {
            displayError("Błąd", "Ścieżka pliku wejściowego jest pusta!");
            return;
        }

        if (outputDirectoryName.isBlank()) {
            displayError("Błąd", "Ścieżka katalogu wyjściowego jest pusta!");
            return;
        }

        if (outputFilename.isBlank()) {
            displayError("Błąd", "Nazwa pliku wyjściowego jest pusta!");
            return;
        }

        File inputFile = new File(inputFilename);

        if (!inputFile.exists()) {
            displayError("Błąd", "Plik wejściowy nie istnieje!");
            return;
        }

        if (!inputFile.isFile()) {
            displayError("Błąd", "Ścieżka wejściowa nie wskazuje na plik!");
            return;
        }

        File outputDirectory = new File(outputDirectoryName);

        if (!outputDirectory.exists()) {
            displayError("Błąd", "Katalog wyjściowy nie istnieje!");
            return;
        }

        if (!outputDirectory.isDirectory()) {
            displayError("Błąd", "Ścieżka wyjściowa nie jest katalogiem!");
            return;
        }

        String completeOutputPath = Paths.get(outputDirectoryName, outputFilename).toString();

        File outputFile = new File(completeOutputPath);

        if (outputFile.exists()) {
            displayError("Błąd", "Plik wyjściowy już istnieje!");
            return;
        }

        try (FileInputStream inputFileStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {

            // Sprawdź rozszerzenie pliku wejściowego
            String extension = getFileExtension(inputFilename);
            if (!extension.equalsIgnoreCase("txt")) {
                displayError("Błąd", "Nieprawidłowy format pliku wejściowego. Obsługiwane są tylko pliki .txt.");
                return;
            }

            // Sprawdź czy plik wejściowy nie jest pusty
            if (inputFile.length() == 0) {
                displayError("Błąd", "Pusty plik wejściowy.");
                return;
            }

            HuffmanDecoder.decode(inputFileStream, outputStream, this);
            if (outputFile.length() == 0) {
                outputFile.deleteOnExit();
            }


        } catch (FileNotFoundException e) {
            displayError("Błąd", "Plik nie znaleziony: " + e.getMessage());
        } catch (IOException e) {
            displayError("Błąd", "Wystąpił błąd podczas dekodowania: " + e.getMessage());
        } catch (NumberFormatException e) {
            displayError("Błąd", "Błędny plik.");
        }
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == 0 || dotIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(dotIndex + 1);
    }
}
