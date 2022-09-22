package edu.yu.pl;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class UserDefinedField {

    final private String udfText;
    final private ParseTree parseTree;

    public UserDefinedField(final String udfText) {
        this.udfText = udfText;

        var lexer = new UserDefinedFieldLanguageLexer(
                CharStreams.fromString(udfText));

        var parser = new UserDefinedFieldLanguageParser(
                new CommonTokenStream(lexer));

        this.parseTree = parser.userField();
    }

    public Value evaluate(UDFParseTreeVisitor visitor) {
        return visitor.visit(this.parseTree);
    }

    public String getUdfText() {
        return udfText;
    }

    @Override
    public String toString() {
        return String.format("udf=%", udfText);
    }
}
