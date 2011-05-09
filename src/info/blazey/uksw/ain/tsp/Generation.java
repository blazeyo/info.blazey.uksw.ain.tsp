package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;

/**
 *
 * @author blazej
 */
public interface Generation {

  public void initialize(int parentsCount);

  public Generation getNext();

  public GenerationStatistics getStatistics();

  public ArrayList<Path> getPopulation();

  public Path getBestIndividual();

}
