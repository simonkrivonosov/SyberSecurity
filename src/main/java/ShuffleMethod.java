import java.io.*;

/**
 * шифрование перестановкой
 */
public class ShuffleMethod {
    public static void main(String[] args) throws IOException {
        File input = new File("src/main/resources/input/input_file.txt");
        File encDir = new File("src/main/resources/output_en");
        File decrDir = new File("src/main/resources/output_decr");
        File encrypt = encrypt(input, encDir, new int[]{2, 4, 3, 1, 0});
        File decrypt = decrypt(encrypt, decrDir, new int[]{2, 4, 3, 1, 0});
    }

    public static File encrypt(File input, File outDir, int[] shuffle) throws IOException {
        File outFile = new File(outDir,"shuffle_" + input.getName());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(input), shuffle.length);
        BufferedOutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(outFile), shuffle.length);
        byte[] buf = new byte[shuffle.length];
        byte[] outBuf = new byte[shuffle.length];
        while (inputStream.read(buf) != -1) {
            for (int i = 0; i < buf.length; ++i) {
                outBuf[i] = buf[shuffle[i]];
            }
            outputStream.write(outBuf);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return outFile;
    }

    public static File decrypt(File input, File outDir, int[] shuffle) throws IOException {
        File outFile = new File(outDir,"unshuffle_" + input.getName());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(input), shuffle.length);
        BufferedOutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(outFile), shuffle.length);
        byte[] buf = new byte[shuffle.length];
        byte[] outBuf = new byte[shuffle.length];
        while (inputStream.read(buf) != -1) {
            for (int i = 0; i < buf.length; ++i) {
                outBuf[shuffle[i]] = buf[i];
            }
            outputStream.write(outBuf);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return outFile;
    }
}
