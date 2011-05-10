package info.blazey.uksw.ain.tsp;

import java.io.IOException;

/**
 *
 * @author blazej
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
      try {
        Graph graph = TSPLibParser.getGraph("data/brazil58.tsp");

//        PathCrossoverOperator pco = new PathSciezkowaCrossoverOperatorWithEdgeRecombination();

//        for (int i = 0; i < 50; i++) {
//          PathSciezkowa p1 = new PathSciezkowa(graph);
//          p1.initializeWithRandomValue();
//          Main.log("Parent 1: " + p1.toString());
//
//          PathSciezkowa p2 = new PathSciezkowa(graph);
//          p2.initializeWithRandomValue();
//          Main.log("Parent 2: " + p1.toString());
//
//          Main.log("Children:");
//          for (Path c : pco.getChildren(p1, p2)) {
//            Main.log(c.toString());
//          }
//        }

        TSPGAController controller = new TSPGAController(graph);
        Generation finalGeneration = controller.getFinalGeneration();

        log(finalGeneration.getBestIndividual().toString());
      } catch (Graph.EdgeExistsException ex) {
        log("Edge already exists");
      }
    }

    public static void log(String message) {
      System.out.println(message);
    }

    public static void log(int number) {
      log(Integer.toString(number));
    }

    public static void log(double number) {
      log(Double.toString(number));
    }

}
