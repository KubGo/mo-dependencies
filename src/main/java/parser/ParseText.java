package parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class ParseText {
    public static DefinitionsListener parseText(String text) {
        ModelicaLexer modelicaLexer = new ModelicaLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(modelicaLexer);
        Modelica parser = new Modelica(tokens);
        parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
        DefinitionsListener definitionsListener = new DefinitionsListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(definitionsListener, parser.stored_definition());
        return definitionsListener;
    }
}
