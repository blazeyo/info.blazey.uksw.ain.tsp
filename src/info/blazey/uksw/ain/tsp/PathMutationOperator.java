package info.blazey.uksw.ain.tsp;

/**
 *
 * @author blazej
 */
public interface PathMutationOperator {

  public void performMutation(Path p);

  public boolean mutate();

}
