package parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassesExtractorTest {

    ClassesExtractor listener;

    @BeforeEach
    void setUp(){
        listener = new ClassesExtractor();
    }

    @Test
    void extractClasses_BouncingBall_classesMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources("BouncingBall.mo");
        ModelicaLexer modelicaLexer = new ModelicaLexer(CharStreams.fromString(modelicaText));

        CommonTokenStream tokens = new CommonTokenStream(modelicaLexer);
        ModelicaParser modelicaParser = new ModelicaParser(tokens);
        ParseTree tree = modelicaParser.stored_definition();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);

        assertEquals(
                3,
                listener.classes.size());
        assertEquals(
                List.of("Height", "Real", "Velocity"),
                listener.classes.stream().sorted().toList());
    }

    @Test
    void extractImports_BouncingBall_classesMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources("BouncingBall.mo");
        ModelicaLexer modelicaLexer = new ModelicaLexer(CharStreams.fromString(modelicaText));

        CommonTokenStream tokens = new CommonTokenStream(modelicaLexer);
        ModelicaParser modelicaParser = new ModelicaParser(tokens);
        ParseTree tree = modelicaParser.stored_definition();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);

        assertEquals(
                0,
                listener.imports.size());
    }

    @Test
    void extractImports_ImportsTest_importsMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources("ImportsTest.mo");
        ModelicaLexer modelicaLexer = new ModelicaLexer(CharStreams.fromString(modelicaText));

        CommonTokenStream tokens = new CommonTokenStream(modelicaLexer);
        ModelicaParser modelicaParser = new ModelicaParser(tokens);
        ParseTree tree = modelicaParser.stored_definition();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);

        assertEquals(
                2,
                listener.imports.size());

    }
    @Test
    void extractClasses_ImportsTest_classesMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources("ImportsTest.mo");
        ModelicaLexer modelicaLexer = new ModelicaLexer(CharStreams.fromString(modelicaText));

        CommonTokenStream tokens = new CommonTokenStream(modelicaLexer);
        ModelicaParser modelicaParser = new ModelicaParser(tokens);
        ClassesExtractor listener = new ClassesExtractor();
        ParseTree tree = modelicaParser.stored_definition();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);

        assertEquals(
                4,
                listener.classes.size()
        );
        assertEquals(
                List.of(
                        "SI.Height",
                        "SI.Temperature",
                        "Sources.Ramp",
                        "Sources.Sine"),
                listener.classes.stream().sorted().toList()
        );
    }

}