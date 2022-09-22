package edu.yu.pl;

import java.util.HashMap;

public class UDFParseTreeVisitor extends UserDefinedFieldLanguageBaseVisitor<Value> {

    public UDFParseTreeVisitor() {
    }


    /********** Visitor Overrides ***************/

    /********************
     numExpr
     ********************/

    @Override
    public Value visitMulDiv(UserDefinedFieldLanguageParser.MulDivContext ctx) {
        Double left = visit(ctx.left).asDouble();
        Double right = visit(ctx.right).asDouble();
        String operator = ctx.op.getText();

        // the operator is either * or /
        Double result = (operator.equals("*")) ? left * right : left / right;
        return new Value(result);
    }

    @Override
    public Value visitAddSub(UserDefinedFieldLanguageParser.AddSubContext ctx) {
        Double left = visit(ctx.left).asDouble();
        Double right = visit(ctx.right).asDouble();
        String operator = ctx.op.getText();

        // The operator is either + or -
        Double result = (operator.equals("+")) ? left + right : left - right;
        return new Value(result);
    }

    @Override
    public Value visitNumConstant(UserDefinedFieldLanguageParser.NumConstantContext ctx) {
        // Convert the token text to type Double
        // We are deciding to treat integers as doubles; the client can convert if necessary
        Double result = Double.parseDouble(ctx.getText());
        return new Value(result);
    }


    /********************
    strExpr
    ********************/

    @Override
    public Value visitStrConstant(UserDefinedFieldLanguageParser.StrConstantContext ctx) {
        // Remove quotes from around the string constant
        String text = ctx.getText().substring(1);
        return new Value(text.substring(0, text.length()-1));
    }

    @Override
    public Value visitConcat(UserDefinedFieldLanguageParser.ConcatContext ctx) {
        String left = visit(ctx.left).asString();
        String right = visit(ctx.right).asString();

        return new Value(left + right);
    }

}
