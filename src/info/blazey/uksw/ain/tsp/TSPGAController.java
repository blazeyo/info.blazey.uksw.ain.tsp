package info.blazey.uksw.ain.tsp;

import info.blazey.uksw.ain.tsp.Graph.EdgeExistsException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author blazej
 */
public class TSPGAController {

  private final int populationSize = 150;
  private final int generationsCount = 150;
  private final double mutationChance = 0.1;
  private final int tournamentGroupSize = 5;
  private final double crossoverChance = 0.95;
  
  private static Random randomGenerator;
  private static int seed;

  static {
    resetSeed();
  }

  static void resetSeed() {
    Date time = new Date();
    seed = (int) time.getTime();
    randomGenerator = new Random(seed);
  }

  public static int nextInt(int range) {
    return randomGenerator.nextInt(range);
  }

  public static double nextDouble() {
    return randomGenerator.nextDouble();
  }

  private static Path shortestPath = null;

  static void saveShortestPath(Path p) {
    if (shortestPath == null || p.getDistance() < shortestPath.getDistance()) {
      shortestPath = p;
    }
  }

  static Path getShortestPath() {
    return shortestPath;
  }

  private Generation generation;

  private JFrame frame;

  public TSPGAController() throws FileNotFoundException, EdgeExistsException, IOException {
    frame = new TSPGAControllerFrame();
  }

  private PathSelectionOperator[] getAvailableSelectionOperators() {
//    PathSelectionOperator[] result = new PathSelectionOperator[2];
//    result[0] = new PathSelectionOperatorRouletteWheel();
    PathSelectionOperator[] result = new PathSelectionOperator[1];
    result[0] = new PathSelectionOperatorTournament(this.tournamentGroupSize);

    return result;
  }

  private PathCrossoverOperator[] getAvailableCrossoverOperators() {
    PathCrossoverOperator[] result = new PathCrossoverOperator[2];
    result[0] = new PathSciezkowaCrossoverOperatorWithEdgeRecombination(crossoverChance);
    result[1] = new PathCrossoverOperatorOX(crossoverChance);

    return result;
  }

  private PathMutationOperator[] getAvailableMutationOperators() {
    PathMutationOperator[] result = new PathMutationOperator[2];
    result[0] = new PathSciezkowaMutationOperatorSwap(mutationChance);
    result[1] = new PathSciezkowaMutationOperatorInversion(mutationChance);

    return result;
  }

  public String[] getAvaiableGraphs() {
    String[] names = {"resources/berlin52.tsp"};
    return names;
//    String path = "info/blazey/uksw/ain/tsp/resources/";
//    Set<String> graphFiles = new HashSet<String>();
//
//    try {
//      String[] resources = ResourceListing.getResourceListing(TSPGAController.class, path);
//      for (String filename : resources) {
//        filename = "resources/" + filename;
//        if (TSPLibParser.graphIsSupported(filename)) {
//          graphFiles.add(filename);
//        }
//      }
//    } catch (URISyntaxException ex) {
//      Logger.getLogger(TSPGAController.class.getName()).log(Level.SEVERE, null, ex);
//    } catch (IOException ex) {
//      Logger.getLogger(TSPGAController.class.getName()).log(Level.SEVERE, null, ex);
//    }
//
//    return graphFiles.toArray(new String[0]);
  }

  private StringBuilder gnuplotFileContent;

  private void logStats(int generationCounter) {
    Main.log(generation.getStatistics().toString());
    gnuplotFileContent.append(generation.getStatistics().getGnuplotRow(generationCounter));
  }

  private File saveGnuplotFile() {
    String content =
              "# Nature inspired algorithms\n"
            + "# Solving symetric travelling salesman problem with GA.\n"
            + "# Błażej Owczarczyk\n"
            + "#\n"
            + "# generations count: " + generationsCount + "\n"
            + "# population size: " + populationSize + "\n"
            + "# mutation chance: " + mutationChance + "\n"
            + "# tournament group size: " + tournamentGroupSize + "\n"
            + "#\n"
            + "# seed: " + seed + "\n"
            + "#\n"
            + "# generation-number best-individual average-individual worst-individual\n"
            + gnuplotFileContent.toString();

    Writer out = null;
    try {
      File outputFile = getGnuplotFileHandler();
      out = new OutputStreamWriter(new FileOutputStream(outputFile));

      out.write(content);


      GnuplotImageGenerator g = new GnuplotImageGenerator(outputFile, "TSP GA Best path length: " + TSPGAController.getShortestPath().getDistance(), "Generations", "Path Length");
      g.addPlot(1, 2, "Best");
      g.addPlot(1, 3, "Average");
//      g.addPlot(1, 4, "Worst");
      g.addPlot(1, 5, "Optimal");
      g.generate();

      return outputFile;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        out.close();
      } catch (IOException ex) {
        Logger.getLogger(TSPGAController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return null;
  }

  private File getGnuplotFileHandler() throws IOException {
    File dataFile = new File("TSP-GA");
    if (dataFile.exists()) {
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
      String newFileName = "TSP-GA-" + format.format(new Date());

      File dataFileCopy = new File(newFileName);
      dataFile.renameTo(dataFileCopy);
      dataFile = new File("TSP-GA");

      File imageFile = new File(dataFile.getPath() + ".png");
      if (imageFile.exists()) {
        File imageFileCopy = new File(newFileName + ".png");
        imageFile.renameTo(imageFileCopy);
      }
    } else {
      dataFile.createNewFile();
    }
    return dataFile;
  }

  class TSPGAControllerFrame extends JFrame {

    public TSPGAControllerFrame() {
      super("Travelling salesman problem");
      JPanel contentPanel = new JPanel();
      setContentPane(contentPanel);

      BoxLayout b = new BoxLayout(contentPanel, BoxLayout.X_AXIS);
      contentPanel.setLayout(b);

      JPanel settingsPanel = new JPanel();
      settingsPanel.add(getGeneralSettingsPanel());
      settingsPanel.add(getSelectionOperatorPanel());
      settingsPanel.add(getCrossoverOperatorPanel());
      settingsPanel.add(getMutationOperatorPanel());
      settingsPanel.add(getGraphPanel());
      BoxLayout settingsPanelLayout = new BoxLayout(settingsPanel, BoxLayout.Y_AXIS);
      settingsPanel.setLayout(settingsPanelLayout);
      contentPanel.add(settingsPanel);

      contentPanel.add(getResultsPanel());

      JButton submit = new JButton(new SubmitAction());
      contentPanel.add(submit);

      pack();
      setVisible(true);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JComboBox selectionOperatorCombo;
    private JComboBox crossoverOperatorCombo;
    private JComboBox mutationOperatorCombo;
    private JComboBox graphCombo;

    private JPanel resultsPanel;
    private JLabel resultImageLabel;
    
    private JTextField populationSizeTextField;
    private JTextField generationsCountTextField;
    private JTextField crossoverChanceTextField;
    private JTextField mutationChanceTextField;

    private JPanel getGeneralSettingsPanel() {
      JPanel settingsPanel = new JPanel();

      settingsPanel.add(new JLabel("Population size"));
      populationSizeTextField = new JTextField(Integer.toString(populationSize));
      settingsPanel.add(populationSizeTextField);

      settingsPanel.add(new JLabel("Generations count"));
      generationsCountTextField = new JTextField(Integer.toString(generationsCount));
      settingsPanel.add(generationsCountTextField);

      settingsPanel.add(new JLabel("Crossover chance"));
      crossoverChanceTextField = new JTextField(Double.toString(crossoverChance));
      settingsPanel.add(crossoverChanceTextField);

      settingsPanel.add(new JLabel("Mutation chance"));
      mutationChanceTextField = new JTextField(Double.toString(mutationChance));
      settingsPanel.add(mutationChanceTextField);

      BoxLayout l = new BoxLayout(settingsPanel, BoxLayout.Y_AXIS);
      settingsPanel.setLayout(l);

      return settingsPanel;
    }

    private JPanel getSelectionOperatorPanel() {
      JPanel selectionOperatorsPanel = new JPanel();
      selectionOperatorsPanel.add(new JLabel("Selection operator"));
      selectionOperatorCombo = new JComboBox(getAvailableSelectionOperators());
      selectionOperatorsPanel.add(selectionOperatorCombo);

      return selectionOperatorsPanel;
    }

    private JPanel getCrossoverOperatorPanel() {
      JPanel crossoverOperatorsPanel = new JPanel();
      crossoverOperatorsPanel.add(new JLabel("Crossover operator"));
      crossoverOperatorCombo = new JComboBox(getAvailableCrossoverOperators());
      crossoverOperatorsPanel.add(crossoverOperatorCombo);

      return crossoverOperatorsPanel;
    }

    private JPanel getMutationOperatorPanel() {
      JPanel mutationOperatorsPanel = new JPanel();
      mutationOperatorsPanel.add(new JLabel("Mutation operator"));
      mutationOperatorCombo = new JComboBox(getAvailableMutationOperators());
      mutationOperatorsPanel.add(mutationOperatorCombo);

      return mutationOperatorsPanel;
    }

    private JPanel getGraphPanel() {
      JPanel graphPanel = new JPanel();
      graphPanel.add(new JLabel("TSPLIB graph"));
      graphCombo = new JComboBox(getAvaiableGraphs());
      graphPanel.add(graphCombo);

      return graphPanel;
    }

    private JPanel getResultsPanel() {
      resultsPanel = new JPanel();
      resultsPanel.add(new JLabel("Result"));
      resultImageLabel = new JLabel(new ImageIcon("TSP-GA.png"));
      resultsPanel.add(resultImageLabel);

      return resultsPanel;
    }

    private class SubmitAction extends AbstractAction {

      public SubmitAction() {
        super("Run");
      }

      public void actionPerformed(ActionEvent e) {
        resetSeed();
        shortestPath = null;

        Graph graph = TSPLibParser.getGraph((String) graphCombo.getSelectedItem());
        PathSelectionOperator selectionOperator = (PathSelectionOperator) selectionOperatorCombo.getSelectedItem();
        PathCrossoverOperator crossoverOperator = (PathCrossoverOperator) crossoverOperatorCombo.getSelectedItem();
        PathMutationOperator mutationOperator = (PathMutationOperator) mutationOperatorCombo.getSelectedItem();

        crossoverOperator.setCrossoverChance(Double.parseDouble(crossoverChanceTextField.getText()));
        mutationOperator.setMutationChance(Double.parseDouble(mutationChanceTextField.getText()));

        generation = new GenerationTSPSciezkowa(graph, selectionOperator, crossoverOperator, mutationOperator);
        generation.initialize(Integer.parseInt(populationSizeTextField.getText()));
        gnuplotFileContent = new StringBuilder();

        int generationsCount = Integer.parseInt(generationsCountTextField.getText());

        logStats(1);
        for (int i = 0; i < generationsCount; i++) {
          generation = generation.getNext();
          TSPGAController.saveShortestPath(generation.getBestIndividual());
          logStats(2+i);
        }
        File output = saveGnuplotFile();

        File imageFile = new File(output.getPath() + ".png");
        try {
          while (!imageFile.exists()) {
            Thread.sleep(1000);
            Main.log("waiting for gnuplot to generate file");
          }
          resultImageLabel.setIcon(new ImageIcon(ImageIO.read(imageFile)));
        } catch (Exception ex) {
          Logger.getLogger(TSPGAController.class.getName()).log(Level.SEVERE, null, ex);
        }
        resultImageLabel.repaint();
        repaint();
        pack();
      }

    }

  }

}
