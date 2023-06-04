package pl.edu.pw.dekodowaniehuffmanaostateczne;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public interface HuffmanCodeReader {
    Map<Integer, String> readCodes(FileInputStream input, FileOutputStream output, BitReader8bit inputReader) throws IOException;
}
