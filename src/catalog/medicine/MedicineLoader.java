package catalog.medicine;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import game.settings.GameSettings;
import game.settings.GameSettings.Language;

public class MedicineLoader {

    public List<MedicineInfo> loadMedicines(List<MedicineInfo> medicineList) {
        if (GameSettings.language == Language.PORTUGUESE) {
           return loadPtContent();
        }
        return loadEngContent();
    }

    private List<MedicineInfo> loadEngContent() {
        return loadMedicineList("resources/medicines/medicineListEng.txt");
    }

    private List<MedicineInfo> loadPtContent() {
        return loadMedicineList("resources/medicines/medicineListPt.txt");
    }

    public List<MedicineInfo> loadMedicineList(String path){
        Path path2 = Paths.get(path);
        String aux[];
        List<MedicineInfo> medicines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path2, Charset.defaultCharset())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                aux = line.split(";");
                MedicineInfo info = new MedicineInfo(aux[0], Integer.parseInt(aux[1]));
                medicines.add(info);
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }
        return medicines;
    }
}
