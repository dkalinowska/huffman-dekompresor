package pl.edu.pw.dekodowaniehuffmanaostateczne;

import java.io.FileInputStream;
import java.io.IOException;

public class BitReader8bit extends Reader{
    private final FileInputStream inputStream;
    private int lastByte = 0;
    private int bitPosition = 7;
    private int currentByte;

    public BitReader8bit(FileInputStream inputStream) {
        this.inputStream = inputStream;
    }
    public char readBit() throws IOException {
        if (bitPosition == 7)
            if ((currentByte = inputStream.read()) == -1)
                return '2';
        // if it is the last byte in the input file - compute the number of redundant bits
        if (inputStream.available() == 0) {
            for (int i = 0; i <= 7; i++) {
                if ((currentByte & (1 << bitPosition)) == 0)
                    lastByte++;
                else if ((currentByte & (1 << bitPosition)) != 0) {
                    lastByte++;
                    break;
                }
            }
        }

        if (bitPosition == lastByte && lastByte != 0)
            return '2';

        char read = (currentByte & (1 << bitPosition)) != 0 ? '1' : '0';

        bitPosition--;

        if (bitPosition < 0)
            bitPosition = 7;

        return read;
    }
}
