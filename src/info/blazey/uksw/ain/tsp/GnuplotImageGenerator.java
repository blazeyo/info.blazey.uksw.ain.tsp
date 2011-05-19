package info.blazey.uksw.ain.tsp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 *
 * @author blazej
 */
public class GnuplotImageGenerator {

  private File dataSource;
  private File outputFile;
  private StringBuilder configFileContent;

  private ArrayList<String> plots;

  public GnuplotImageGenerator(File dataSource) {
    this.dataSource = dataSource;
    plots = new ArrayList<String>();
    configFileContent = new StringBuilder();
    configFileContent.append("set grid\n");
    configFileContent.append("set terminal png\n");
    configFileContent.append("set output \"").append(dataSource.getName()).append(".png").append("\"\n");
  }

  public GnuplotImageGenerator(File dataSource, String title, String xLabel, String yLabel) {
    this(dataSource);
    configFileContent.append("set title \"").append(title).append("\"\n");
    configFileContent.append("set xlabel \"").append(xLabel).append("\"\n");
    configFileContent.append("set ylabel \"").append(yLabel).append("\"\n");
  }

  public void addPlot(int xColumn, int yColumn, String label) {
    String plot = "'" + dataSource.getName() + "' using " + xColumn + ":" + yColumn + " with lines title '" + label + "'";
    plots.add(plot);
  }

  public void generate() throws FileNotFoundException, IOException {
    StringBuilder plotsCommand = new StringBuilder("plot ");
    for (String plot : plots) {
      plotsCommand.append(plot);
      plotsCommand.append(", ");
    }
    plotsCommand.delete(plotsCommand.length() - 2, plotsCommand.length());
    configFileContent.append(plotsCommand);

    File config = new File("GnuplotImageGeneratorData");
    config.createNewFile();

    Writer out = new OutputStreamWriter(new FileOutputStream(config));
    out.write(configFileContent.toString());
    out.close();

    String[] cmds = { "gnuplot", config.getAbsolutePath() };
    Runtime.getRuntime().exec(cmds);
    System.out.println("success");
  }

}
