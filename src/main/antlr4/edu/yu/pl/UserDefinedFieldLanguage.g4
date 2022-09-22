grammar UserDefinedFieldLanguage;

/* Start Variable */
userField : numExpr  # NumExpression
          | strExpr  # StrExpression
          | EOF      # EndOfInput
          ;

// Possible expressions in order of precedence
numExpr : left=numExpr op=('*'|'/') right=numExpr  # MulDiv
        | left=numExpr op=('+'|'-') right=numExpr  # AddSub
        | '(' numExpr ')'                          # ParensNumExpr
        | NUMBER_CONSTANT                          # NumConstant
        ;

strExpr : left=strExpr '+' right=strExpr  # Concat
        | '(' strExpr ')'                 # ParensStrExpr
        | STRING_CONSTANT                 # StrConstant
        ;


/* Tokens */
NUMBER_CONSTANT: ('-')?[0-9]+ ('.' [0-9]+)? ;
STRING_CONSTANT: '\'' [a-zA-Z0-9_][a-zA-Z0-9_]* '\'';

COMMENT : '//' ~[\r\n]* -> skip;    // skip the rest of the line after comment (//)
WHITE_SPACE: [ \t\n]+ -> skip;      // skip spaces, tabs and newline characters