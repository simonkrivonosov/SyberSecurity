import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * расшифровка методом протяжного слова
 */
public class StringingWordEcr {
    public static void main(String[] args) throws IOException {
        File input = new File("src/main/resources/input/input_file.txt");
        File encDir = new File("src/main/resources/output_en");
        File decrDir = new File("src/main/resources/output_decr");
        File encryptShift = ShiftEncryption.encrypt(input, encDir, 5, Charset.forName("UTF-8"));
        File encryptShuffle = ShuffleMethod.encrypt(input, encDir, new int[]{2, 4, 3, 1, 0});

        List<int[]> ints = encryptByStringingWordShaffle(encryptShuffle, decrDir, 5, "asdf.");
        for(int[] var : ints) {
            StringBuilder stringBuilder = new StringBuilder();
            for(int v : var) {
                stringBuilder.append(v);
            }
            System.out.println(stringBuilder);
        }

        System.out.println();

        List<Integer> integers = encrByWordShift(encryptShift, decrDir, 6, "asdf.");
        integers.forEach(System.out::println);

    }

    public static List<int []> encryptByStringingWordShaffle(File input, File outPut, int lengthKey, String word) throws IOException {

        List<int []> result = new ArrayList<>();

        ArrayList<Integer> ints = new ArrayList<>();
        for(int i = 0; i < lengthKey; i++) {
            ints.add(i);
        }

        List<int[]> variants = variant(new int[lengthKey],ints);
        for (int[] variant : variants) {
            File decrypt = ShuffleMethod.decrypt(input, outPut, variant);
            List<String> strings = Files.readAllLines(Paths.get(decrypt.getPath()));
            for (String s : strings) {
                if(s.contains(word)) {
                    result.add(variant);
                }
            }
        }
        return result;
    }

    public static List<Integer> encrByWordShift(File input, File outDir, int maxTry, String word) throws IOException {
        ArrayList<Integer> results = new ArrayList<>();
        for(int i = 1; i <= maxTry; i++) {
            File decrypt = ShiftEncryption.decrypt(input, outDir, i, Charset.forName("UTF-8"));
            List<String> strings = Files.readAllLines(Paths.get(decrypt.getPath()));
            for (String s : strings) {
                if (s.contains(word)) {
                    results.add(i);
                }
            }
        }
        return results;
    }

    /**
     * Генерирует все перестановки
     * @param previous
     * @param rest
     * @return
     */
    public static List<int[]> variant(int[] previous, List<Integer> rest) {
        List<int[]> ret = new ArrayList<>();
        if(rest.isEmpty()) {
            return Collections.singletonList(previous);
        }
        for(int i = 0; i < rest.size(); i++) {
            List<Integer> copy = new ArrayList<>(List.copyOf(rest));
            int[] current = previous.clone();
            Integer remove = copy.remove(i);
            current[current.length - rest.size()] = remove;
            ret.addAll(variant(current, copy));
        }
        return ret;

    }
}
