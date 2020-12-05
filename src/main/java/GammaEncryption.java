import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Гамма шифрование
 */
public class GammaEncryption {

    static List<byte[]> statesToDecr = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        File input = new File("src/main/resources/input/input_file.txt");
        File encDir = new File("src/main/resources/output_en");
        File decrDir = new File("src/main/resources/output_decr");
        File encrypt = encrypt(input, encDir);
        File decrypt = decrypt(encrypt, decrDir);
    }

    private static Integer getPseudoRandomGamma(int initialState) {
        HashMap<Integer,Integer> states = new HashMap<>();
        states.put(0, 231);
        states.put(1, 12);
        return states.get(initialState);
    }

    public static File encrypt(File input, File outDir) throws IOException {
        File outFile = new File(outDir,"gamma_" + input.getName());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(input));
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outFile));

        int turn = 0;
        while (turn != -1) {
            byte[] gamma = Integer.toBinaryString(getPseudoRandomGamma((int) (System.currentTimeMillis() % 2))).getBytes();
            statesToDecr.add(gamma);
            byte[] buf = new byte[gamma.length];
            byte[] outBuf = new byte[gamma.length];
            turn = inputStream.read(buf);
            for (int i = 0; i < buf.length; ++i) {
                outBuf[i] = (byte) (buf[i] ^ gamma[i]);
            }
            outputStream.write(outBuf, 0, outBuf.length );
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return outFile;
    }

    public static File decrypt(File input, File outDir) throws IOException {
        File outFile = new File(outDir,"degamma_" + input.getName());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(input));
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outFile));

        int turn = 0;
        while (turn != -1) {
            if(statesToDecr.isEmpty()) {
                turn = inputStream.read();
                continue;
            }
            byte[] gamma =  statesToDecr.remove(statesToDecr.size() - 1);
            byte[] buf = new byte[gamma.length];
            byte[] outBuf = new byte[gamma.length];
            turn = inputStream.read(buf);
            for (int i = 0; i < buf.length; ++i) {
                outBuf[i] = (byte) (buf[i] ^ gamma[i]);
            }
            outputStream.write(outBuf);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return outFile;
    }
}
