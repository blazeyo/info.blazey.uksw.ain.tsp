package info.blazey.uksw.ain.tsp;

import info.blazey.uksw.ain.tsp.Graph.EdgeExistsException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author blazej
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, EdgeExistsException, URISyntaxException {
//      Graph graph = TSPLibParser.getGraph("resources/berlin52.tsp");
//      Main.log(graph.getShortestTour().getDistance());
//      Main.log(graph.getShortestTour().toString());
//      try {
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

      
//      Main.log(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).toString());

        TSPGAController controller = new TSPGAController();

//        log(finalGeneration.getBestIndividual().toString());
//      } catch (Graph.EdgeExistsException ex) {
//        log("Edge already exists");
//      }
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
