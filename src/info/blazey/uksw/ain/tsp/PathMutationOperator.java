package info.blazey.uksw.ain.tsp;

/**
 *
 * @author blazej
 */
public abstract class PathMutationOperator {

  protected double chance;

  public PathMutationOperator(double chance) {
    this.chance = chance;
  }

  public void setMutationChance(double chance) {
    this.chance = chance;
  }

  public abstract void performMutation(Path p);

  public boolean mutate() {
    return TSPGAController.nextDouble() <= chance;
  }

}
