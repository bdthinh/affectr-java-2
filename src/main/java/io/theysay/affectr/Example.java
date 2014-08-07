package io.theysay.affectr;

import java.util.Arrays;

import com.mashape.unirest.http.Unirest;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Example {
  public static void main(String[] args) {
    OptionParser op = new OptionParser();

    op.acceptsAll(Arrays.asList("b", "baseurl")).withRequiredArg().ofType(String.class).describedAs("TheySay PreCeive API base URL");
    op.acceptsAll(Arrays.asList("h", "help")).withOptionalArg().ofType(String.class).describedAs("Arguments and usage");
    op.acceptsAll(Arrays.asList("p", "password")).withRequiredArg().ofType(String.class).describedAs("TheySay PreCeive API password");
    op.acceptsAll(Arrays.asList("u", "username")).withRequiredArg().ofType(String.class).describedAs("TheySay PreCeive API user name");

    OptionSet options = op.parse(args);

    if (options.has("help")) printHelp(op);

    if (!options.has("baseurl")) { System.err.print("No base URL provided."); System.exit(-1); }
    if (!options.has("username")) { System.err.print("No API user name provided."); System.exit(-1); }
    if (!options.has("password")) { System.err.print("No API password provided."); System.exit(-1); }

    String baseUrl = (String) options.valueOf("baseurl");
    String userName = (String) options.valueOf("username");
    String passWord = (String) options.valueOf("password");

    AffectRClient apiClient = new AffectRClient(baseUrl, userName, passWord);
    Texts demoTexts = new Texts();

    apiClient.demo(demoTexts);

    try {
      Unirest.shutdown();

      System.out.println("Finished.");

      System.exit(0);
    } catch (Exception e) {
      e.printStackTrace();

      System.exit(-1);
    }
  }

  private static void printHelp(OptionParser op) {
    try {
      op.printHelpOn(System.err);

      System.exit(0);
    } catch (Exception e) {
      System.exit(-1);
    }
  }
}
