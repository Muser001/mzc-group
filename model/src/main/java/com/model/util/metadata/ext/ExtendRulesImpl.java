package com.model.util.metadata.ext;

import com.model.util.metadata.ValidateResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExtendRulesImpl implements MetadataValidateExt{

    private ValidateResult trueResult = new ValidateResult(true);

    private ValidateResult falseResult = new ValidateResult(false);


    @Override
    public ValidateResult extendedValidate(String extendRules, Object value) {
        ValidateResult falseResult = new ValidateResult(false,value.getClass().getTypeName()+"不支持"+extendRules+"]扩展规则");
        switch (extendRules) {
            case ExtendRules.GTHANZERO:
                return value instanceof BigDecimal ? gthanZero() : falseResult;
            case ExtendRules.GTHANEQUZERO:
                return value instanceof BigDecimal ? gthanequZero() : falseResult;
            default:
                return new ValidateResult(false,"无扩展规则");
        }
    }

    private ValidateResult gthanZero(){
        return null;
    }

    private ValidateResult gthanequZero(){
        return null;
    }
}
