package utils;

import files.ModelicaFileReader;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.ModelicaLexer;
import parser.ModelicaListener;
import parser.ModelicaParser;

import java.io.File;
import java.io.IOException;

public class Utils {
    public final static String BouncingBall = "BuildingsLite/Tests/BouncingBall.mo";
    public final static String ImportsTest = "BuildingsLite/Tests/ImportsTest.mo";
    public final static String ComplexExample = "BuildingsLite/Tests/ComplexExample.mo";
    public final static String BuildingsLite = "BuildingsLite";
    public final static String SimpleModel = "BuildingsLite/Tests/SimpleModel.mo";
    public final static String PartialSimpleModel = "BuildingsLite/Tests/PartialSimpleModel.mo";
    public final static String TestLibrary = "TestLibrary";
    public final static String ConductorStepResponse = "BuildingsLite/HeatTransfer/Examples/ConductorStepResponse.mo";
    public final static String ReverseBuoyancy3Zone = "BuildingsLite/Airflow/Multizone/Examples/ReverseBuoyancy3Zones.mo";

    public static String getPathAsString(String resourceName){
        File file = new File("src/test/resources/" + resourceName);
        return file.getAbsolutePath();
    }
    public static String getModelicaTextFromResources(String resourceName) throws IOException {
        ModelicaFileReader modelicaFileReader = new ModelicaFileReader();
        String filePath = getPathAsString(resourceName);

        return modelicaFileReader.readFile(
                filePath);
    }
    public static <T extends ModelicaListener> T getParsedListenerFromText(String text, T listener){
        ModelicaLexer modelicaLexer = new ModelicaLexer(CharStreams.fromString(text));

        CommonTokenStream tokens = new CommonTokenStream(modelicaLexer);
        ModelicaParser modelicaParser = new ModelicaParser(tokens);
        ParseTree tree = modelicaParser.stored_definition();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
        return listener;
    }
}
