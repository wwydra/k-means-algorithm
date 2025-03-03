import java.util.ArrayList;
import java.util.List;

public class Iris{

    public enum IrisType {SETOSA, VERSICOLOR, VIRGINICA}

    public static ArrayList<Iris> data = new ArrayList<>();

    private List<Double> attributes;

    private IrisType type;


    public Iris(List<Double> attributes, IrisType type) {
        this.attributes = attributes;
        this.type = type;
    }


    public IrisType getType() {
        return type;
    }


    public List<Double> getAttributes(){
        return attributes;
    }
}
