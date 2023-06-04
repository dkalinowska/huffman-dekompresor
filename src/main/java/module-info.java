module pl.edu.pw.dekodowaniehuffmanaostateczne {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.edu.pw.dekodowaniehuffmanaostateczne to javafx.fxml;
    exports pl.edu.pw.dekodowaniehuffmanaostateczne;
}