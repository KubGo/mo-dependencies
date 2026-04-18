package utils;

import files.ModelicaFileReader;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.ModelicaLexer;
import parser.ModelicaListener;
import parser.ModelicaParser;

import java.io.IOException;
import java.util.Objects;

public class Utils {
    public final static String BouncingBall = "BouncingBall.mo";
    public final static String ImportsTest = "ImportsTest.mo";
    public final static String ComplexExample = "ComplexExample.mo";
    public static String getPathAsString(String resourceName){
        return Objects.requireNonNull(
                        Utils.class.getClassLoader().getResource(resourceName))
                .getPath();
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
