package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;

/**
 *
 * @author blazej
 */
public interface PathCrossoverOperator {

  public ArrayList<Path> getChildren(Path parent1, Path parent2);

}
