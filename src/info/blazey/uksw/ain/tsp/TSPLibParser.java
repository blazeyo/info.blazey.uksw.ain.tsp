package info.blazey.uksw.ain.tsp;

import info.blazey.uksw.ain.tsp.Graph.EdgeExistsException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author blazej
 */
public class TSPLibParser {

  private static GraphUndirected graph;
  private static Scanner scanner;

  public static Graph getGraph(String filename) throws FileNotFoundException, EdgeExistsException {
    graph = new GraphUndirected();
    scanner = new Scanner(new FileInputStream(filename));

    try {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.trim().equals("EDGE_WEIGHT_SECTION")) {
          towns = new ArrayList<Node>();
          fillGraph();
        } else {
          String paramName = line.split(" ")[0].trim();
          String paramValue = line.split(" ")[1].trim();

          if (paramName.equals("NAME:")) {
            graph.setName(paramValue);
          }
          if (paramName.equals("COMMENT:")) {
            graph.setComment(line.split("COMMENT: ")[1]);
          }
          if (paramName.equals("DIMENSION:")) {
            graph.setSize(Integer.parseInt(paramValue));
          }
        }
      }
    } finally {
      scanner.close();
    }

    return graph;
  }

  private static ArrayList<Node> towns;

  private static void fillGraph() throws EdgeExistsException {
    Main.log("* Filling graph");
    int row = 0;
    while (scanner.hasNextLine()) {
      // We start from first column as diagonal items are removed from the matrix
      int column = 1 + row;
      Node origin = getTown(row++);
      String line = scanner.nextLine();
      if (line.split(" ").length > 0 && line.equals("EOF") == false) {
        for (String distance : line.split(" ")) {
          Node destination = getTown(column++);
          graph.addEdge(origin, destination, Double.parseDouble(distance));
        }
      }
    }
    Main.log("* Graph filled:");
    Main.log("\tName: " + graph.getName());
    Main.log("\tComment: " + graph.getComment());
    Main.log("\tSize: " + graph.getSize());
  }

  private static Node getTown(int index) {
    // We are adding towns with consequent indexes
    if (towns.size() < index + 1) {
      towns.add(index, new Node(index));
    }
    return towns.get(index);
  }

}
