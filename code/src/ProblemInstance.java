import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent a TSP problem. Stores various data read from a .tsp file.
 * @author Stefano Taillefert
 */
public class ProblemInstance {
    public List<String> lines;
    public String name;
    public int bestKnownSolution;
    public int bestComputedSolution = -1;
    public int currentSolution;
    public List<Point> points = new ArrayList<>();
    public int numPoints;

    public ProblemInstance(List<String> data) {
        lines = data;
        name = lines.get(0).split(":")[1].strip();
        bestKnownSolution = Integer.parseInt(lines.get(5).split(":")[1].strip());

        for (int i = 7; i < lines.size() - 1; ++i) {
            String[] line = lines.get(i).split(" ");
            points.add(new Point(Integer.parseInt(line[0]), Double.parseDouble(line[1]), Double.parseDouble(line[2])));
        }

        numPoints = points.size();
    }

    public List<String> solve(int startIndex) {
        List<String> result = new ArrayList<>();

        //TODO actually write the NN algorithm
        currentSolution = startIndex;
        result.add("12");
        result.add("22");
        result.add("1");

        if (bestComputedSolution == -1 || currentSolution < bestComputedSolution) {
            bestComputedSolution = currentSolution;
        }

        return result;
    }
}
