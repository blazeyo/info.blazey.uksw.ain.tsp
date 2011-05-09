package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;

/**
 *
 * @author blazej
 */
public interface PathSelectionOperator {

  public ArrayList<Path> filter(ArrayList<Path> paths);

}
