package com.model.util;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * SpEl 表达式工具类
 */
public class SpelUtil {

    public static Object eval(String expr, Object contextValue) {
        StandardEvaluationContext context = new StandardEvaluationContext(contextValue);
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(expr).getValue(context);
    }

    public static void evalSet(String expr, Object contextValue, Object value) {
        StandardEvaluationContext context = new StandardEvaluationContext(contextValue);
        ExpressionParser parser = new SpelExpressionParser();
        parser.parseExpression(expr).setValue(context,value);
    }
}
