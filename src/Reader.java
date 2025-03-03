import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    static List<String> decisionAttributes;
    static final String path = "iris.txt";
    public static void readFile(String path) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {

            Iris iris;
            Iris.IrisType type;
            List<Double> conditionalAttributes;

            String line;
            String[] parts;

            decisionAttributes = new ArrayList<>();

            for(Iris.IrisType value : Iris.IrisType.values())
                decisionAttributes.add(value.name());

            while ((line = br.readLine()) != null) {
                parts = line.replace(',','.').trim().split("[\\t\\s]+");


                if (parts[parts.length-1].toLowerCase().contains("setosa"))
                    type = Iris.IrisType.SETOSA;
                else if (parts[parts.length-1].toLowerCase().contains("versicolor"))
                    type = Iris.IrisType.VERSICOLOR;
                else if (parts[parts.length-1].toLowerCase().contains("virginica"))
                    type = Iris.IrisType.VIRGINICA;
                else
                    type = null;



                conditionalAttributes = new ArrayList<>();

                for(int i = 0; i < parts.length-1; i++){
                    conditionalAttributes.add(Double.parseDouble(parts[i]));
                }

                iris = new Iris(conditionalAttributes, type);
                Iris.data.add(iris);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
