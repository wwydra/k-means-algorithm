import java.util.*;

public class Kmeans {

    private final int k;
    private Map<Integer, List<Double>> centroids;
    private Map<Integer, ArrayList<Iris>> groups;

    public Kmeans(int k) {
        this.k = k;
        this.groups = new HashMap<>();
        for (int i = 1; i <= k; i++) {
            groups.put(i, new ArrayList<>());
        }
    }

    public void initializeCentroids(List<Iris> data){
        Map<Integer, List<Double>> centroids = new HashMap<>();
        Random random = new Random();
        for (int i = 1; i <= k; i++) {
            int randomIndex = random.nextInt(data.size());
            List<Double> centroid = data.get(randomIndex).getAttributes();
            centroids.put(i, centroid);
        }
        this.centroids = centroids;
    }

    public void kmeansAlgorithm(ArrayList<Iris> data){
        int maxIterations = 100;
        int iterations = 0;
        boolean change = true;

        while (change && iterations <= maxIterations){
            if (iterations != 0)
                this.centroids = calculateCentroids();

            change = assign(data);
            double sumOfSquaredDistances = calculateSumOfSquaredDistances(data);
            System.out.println("Iteration " + iterations +
                    ": Sum of squares of distances from centroids: " + sumOfSquaredDistances);

            iterations++;
        }

        displayGroupsWithEntropy();
    }

    public boolean assign(ArrayList<Iris> data){
        boolean change = false;
        for (Iris iris : data){
            double minDistance = Double.MAX_VALUE;
            int closestCentroid = -1;

            for (int i = 1; i <= k; i++){
                double distance = calculateDistance(iris.getAttributes(), centroids.get(i));
                if (distance < minDistance){
                    minDistance = distance;
                    closestCentroid = i;
                }
            }

            if (!groups.get(closestCentroid).contains(iris)){
                for (Map.Entry<Integer, ArrayList<Iris>> entry : groups.entrySet()){
                    if (entry.getValue().contains(iris)){
                        entry.getValue().remove(iris);
                        break;
                    }
                }
                groups.get(closestCentroid).add(iris);
                change = true;
            }
        }
        return change;
    }

    public Map<Integer, List<Double>> calculateCentroids(){
        Map<Integer, List<Double>> newCentroids = new HashMap<>();
        for (int i = 1; i <= k ; i++) {
            List<Double> centroid = new ArrayList<>();
            if (!groups.get(i).isEmpty()){
                for (int j = 0; j < groups.get(i).get(0).getAttributes().size(); j++){
                    double sum = 0;
                    for (Iris iris : groups.get(i)){
                        sum += iris.getAttributes().get(j);
                    }
                    centroid.add(sum / groups.get(i).size());
                }
            }
            newCentroids.put(i, centroid);
        }
        return newCentroids;
    }

    public double calculateDistance(List<Double> point1, List<Double> point2){
        if (point1.size() != point2.size()) {
            throw new IllegalArgumentException("Lists point1 and point2 must have the same length");
        }

        double sum = 0;
        for (int i = 0; i < point1.size(); i++) {
            sum += Math.pow(point1.get(i) - point2.get(i), 2);
        }
        return Math.sqrt(sum);
    }

    private double calculateSumOfSquaredDistances(ArrayList<Iris> data){
        double sum = 0;
        for (Iris iris : data){
            double minDistance = Double.MAX_VALUE;
            for (int i = 1; i <= k; i++){
                double distance = calculateDistance(iris.getAttributes(), centroids.get(i));
                minDistance = Math.min(minDistance, distance);
            }
            sum += Math.pow(minDistance, 2);
        }
        return sum;
    }

    private void printSumOfSquaredDistancesForEachCentroid(ArrayList<Iris> data) {
        for (int i = 1; i <= k; i++) {
            double sum = 0;
            for (Iris iris : data) {
                double distance = calculateDistance(iris.getAttributes(), centroids.get(i));
                sum += Math.pow(distance, 2);
            }
            System.out.println("Sum of squared distances for centroid " + i + ": " + sum);
        }
    }

    public void displayGroupsWithEntropy() {
        for (int i = 1; i <= k; i++) {
            ArrayList<Iris> cluster = groups.get(i);
            System.out.println("Group " + i + " composition:");
            for (Iris iris : cluster) {
                System.out.println(iris.getType());
            }
            System.out.println("Entropy of group " + i + ": " + calculateEntropy(cluster));
        }
    }

    private double calculateEntropy(ArrayList<Iris> cluster) {
        Map<Iris.IrisType, Integer> counts = new HashMap<>();
        for (Iris iris : cluster) {
            counts.put(iris.getType(), counts.getOrDefault(iris.getType(), 0) + 1);
        }
        double entropy = 0;
        int total = cluster.size();
        for (Map.Entry<Iris.IrisType, Integer> entry : counts.entrySet()) {
            double probability = (double) entry.getValue() / total;
            entropy -= probability * Math.log(probability);
        }
        return entropy;
    }
}
