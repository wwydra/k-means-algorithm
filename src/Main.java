import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Reader.readFile(Reader.path);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj wartość k: ");
        int k = scanner.nextInt();

        Kmeans kmeans = new Kmeans(k);
        kmeans.initializeCentroids(Iris.data);
        kmeans.kmeansAlgorithm(Iris.data);
    }
}
