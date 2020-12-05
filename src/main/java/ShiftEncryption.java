import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Шифрование сдвигом
 */
public class ShiftEncryption {

    private static final int BUF_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        File input = new File("src/main/resources/input/input_file.txt");
        File encDir = new File("src/main/resources/output_en");
        File decrDir = new File("src/main/resources/output_decr");
        File encrypt = encrypt(input, encDir, 5, StandardCharsets.UTF_8); // можно задать размер сдвига
        File decrypt = decrypt(encrypt, decrDir, 5, StandardCharsets.UTF_8);
    }

    public static File encrypt(File input, File outDir, int shift, Charset charset) throws IOException {
        File outFile = new File(outDir, shift + "shift_" + input.getName());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(input), BUF_SIZE);
        BufferedOutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(outFile), BUF_SIZE);
        byte a = "a".getBytes(charset)[0];
        byte z = "z".getBytes(charset)[0];
        byte[] buf = new byte[BUF_SIZE];
        byte[] outBuf = new byte[BUF_SIZE];
        while (inputStream.read(buf) != -1) {
            for (int i = 0; i < buf.length; i++) {
                if (buf[i] >= (int) a && buf[i] <= (int) z) {
                    outBuf[i] = (byte) ((buf[i] + shift - (int) a) % 26 + (int) a);
                } else {
                    outBuf[i] = buf[i];
                }
            }
            outputStream.write(outBuf);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return outFile;
    }

    public static File decrypt(File input, File outDir, int shift, Charset charset) throws IOException {
        return encrypt(input, outDir, -shift, charset);

    }
}
