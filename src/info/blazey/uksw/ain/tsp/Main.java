package info.blazey.uksw.ain.tsp;

import java.io.FileNotFoundException;

/**
 *
 * @author blazej
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      try {
        Graph graph = TSPLibParser.getGraph("data/brazil58.tsp");
        TSPGAController controller = new TSPGAController(graph);
        Generation finalGeneration = controller.getFinalGeneration();

        log(finalGeneration.getBestIndividual().toString());
      } catch (FileNotFoundException ex) {
        log("Graph file not found");
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