import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File input = new File("src/main/resources/input/input_file.txt");
        File encDir = new File("src/main/resources/output_en");
        File decrDir = new File("src/main/resources/output_decr");
        File shiftEncrypt = ShiftEncryption.encrypt(input, encDir, 5, StandardCharsets.UTF_8); // можно задать размер сдвига
        File shiftDecrypt = ShiftEncryption.decrypt(shiftEncrypt, decrDir, 5, StandardCharsets.UTF_8);
        File gammaEncrypt = GammaEncryption.encrypt(input, encDir);
        File gammaDecrypt = GammaEncryption.decrypt(gammaEncrypt, decrDir);
        File shuffleEncrypt = ShuffleMethod.encrypt(input, encDir, new int[]{2, 4, 3, 1, 0, 5});
        File shuffleDecrypt = ShuffleMethod.decrypt(shuffleEncrypt, decrDir, new int[]{2, 4, 3, 1, 0, 5});

        //расшифровка методом протяжного слова для метода перестановок

//        List<int[]> ints = StringingWordEcr.encryptByStringingWordShaffle(shuffleEncrypt, decrDir, 8, "testte");
//        for(int[] var : ints) {
//            StringBuilder stringBuilder = new StringBuilder();
//            for(int v : var) {
//                stringBuilder.append(v);
//            }
//            System.out.println(stringBuilder);
//        }

        // расшифрока длы метода сдвига
        System.out.println();

        List<Integer> integers = StringingWordEcr.encrByWordShift(shiftEncrypt, decrDir, 6, "testte");
        integers.forEach(System.out::println); // значение сдвига
    }
}
