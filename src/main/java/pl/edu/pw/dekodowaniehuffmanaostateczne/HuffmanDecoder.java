package pl.edu.pw.dekodowaniehuffmanaostateczne;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HuffmanDecoder implements HuffmanCodeReader {
    @Override
    public  Map<Integer, String> readCodes(FileInputStream input, FileOutputStream output, BitReader8bit inputReader) throws IOException {
        Map<Integer, String> codes = new HashMap<>();

        int codeLength;
        String code;
        StringBuilder buffer = new StringBuilder();

        // load codes
        for (int i = 0; i < 256; i++) {
            // read code length
            for (int j = 0; j < 8; j++)
                buffer.append(inputReader.readBit());

            codeLength = Integer.parseInt(buffer.toString(), 2);
            buffer.setLength(0);

            // read code
            for (int j = 0; j < codeLength; j++)
                buffer.append(inputReader.readBit());

            code = buffer.toString();
            buffer.setLength(0);

            // store code
            if (codeLength != 0)
                codes.put(i, code);
        }

        return codes;
    }

    public static void decode(FileInputStream input, FileOutputStream output, HelloController controller) throws IOException {
        BitReader8bit inputReader = new BitReader8bit(input);
        HuffmanDecoder decoder = new HuffmanDecoder();

        Map<Integer, String> codes = decoder.readCodes(input, output, inputReader);

        if (codes.isEmpty()) {
            throw new IOException("Unable to decode the input file. No Huffman codes found.");
        }

        Tree tree = new Tree(codes);
        HuffmanNode pointer = tree.getRoot();

        char charBuffer;

        controller.displayTree(tree);

        while ((charBuffer = inputReader.readBit()) != '2') {
            pointer = pointer.traverse(charBuffer);

            if (pointer.isLeaf()) {
                output.write(pointer.data().byteValue());

                pointer = tree.getRoot();
            }
        }
    }

}
