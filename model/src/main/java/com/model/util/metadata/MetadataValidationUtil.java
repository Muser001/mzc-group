package com.model.util.metadata;

import com.model.chain.request.MetadataValidateResult;
import com.model.chain.request.ValidateInfo;
import com.model.dto.BaseInputDTO;
import com.model.init.dict.MetadataManager;
import com.model.util.ContextUtil;
import com.model.util.metadata.ext.MetadataValidateExt;
import io.netty.util.internal.StringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.*;

@Slf4j
public class MetadataValidationUtil {

    private static MetadataManager metadataManager = MetadataManager.getInstance();

    private static String time6 = "HHMmSs";

    private static DateTimeFormatter time6Formatter = DateTimeFormatter.ofPattern("HHMmSs");

    private static String date4 = "YYYY";

    private static DateTimeFormatter date4Formatter = DateTimeFormatter.ofPattern("uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    private static String date6 = "YYYYMM";
    private static DateTimeFormatter date6Formatter = DateTimeFormatter.ofPattern("uuuuMM")
            .withResolverStyle(ResolverStyle.STRICT);
    private static String date8 = "YYYYMMDD";

    private static DateTimeFormatter date8Formatter = DateTimeFormatter.ofPattern("uuuuMMdd")
            .withResolverStyle(ResolverStyle.STRICT);
    private static String date14 = "YYYYMMDDHHMmSs";

    private static DateTimeFormatter date14Formatter = DateTimeFormatter.ofPattern("uuuuMMddHHmmss")
            .withResolverStyle(ResolverStyle.STRICT);

    private static String date17 = "YYYYMMDDHHMmSsNNN";

    private static DateTimeFormatter date17Formatter;
    static {
        DateTimeFormatterBuilder df = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR,4)
                .appendValue(ChronoField.MONTH_OF_YEAR,2)
                .appendValue(ChronoField.DAY_OF_MONTH,2)
                .appendValue(ChronoField.CLOCK_HOUR_OF_DAY,2)
                .appendValue(ChronoField.MINUTE_OF_HOUR,2)
                .appendValue(ChronoField.SECOND_OF_MINUTE,2)
                .appendValue(ChronoField.MICRO_OF_SECOND,3);
        date17Formatter = df.toFormatter();
    }

    private static String date20 = "YYYYMMDDHHMmSsNNNNNN";

    private static DateTimeFormatter date20Formatter;
    static {
        DateTimeFormatterBuilder df = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR,4)
                .appendValue(ChronoField.MONTH_OF_YEAR,2)
                .appendValue(ChronoField.DAY_OF_MONTH,2)
                .appendValue(ChronoField.CLOCK_HOUR_OF_DAY,2)
                .appendValue(ChronoField.MINUTE_OF_HOUR,2)
                .appendValue(ChronoField.SECOND_OF_MINUTE,2)
                .appendValue(ChronoField.MICRO_OF_SECOND,6);
        date20Formatter = df.toFormatter();
    }

    /**
     * 属性校验深度，默认校验两层
     */
    private static final int VALIDATE_DEEP = 2 ;

    /**
     * 校验起始层级
     */
    private static final int VALIDATE_START_LEVEL = 1;

    /**
     * 校验dto的字段是否符合规范
     */
    public static MetadataValidateResult validate(Object obj) {
        MetadataValidateResult ret = new MetadataValidateResult();
        List<ValidateInfo> validateInfoList = new ArrayList<>();
        validate(obj,validateInfoList,VALIDATE_START_LEVEL);
        ret.setError(!validateInfoList.isEmpty());
        ret.setMsgList(validateInfoList);
        return ret;
    }

    /**
     * 元数据校验处理
     * @param obj 校验对象
     * @param validateInfoList 校验结果集
     * @param deep 当前遍历深度
     */
    @SneakyThrows
    private static void validate(Object obj, List<ValidateInfo> validateInfoList, int deep){
        if (deep > VALIDATE_DEEP) {
            return;
        }

        List<Field> fields = FieldUtils.getAllFieldsList(obj.getClass());
        for (Field currentField : fields) {
            if(isSimpleType(currentField.getType())) {
                //当前属性是日期、字符串、数字等简单类型
                validateSimpleField(currentField, obj, validateInfoList);
            } else if (Collection.class.isAssignableFrom(currentField.getType())){
                //当前属性是集合类型，且集合元素是BaseInputDTO类型
                Collection fieldValue = (Collection) FieldUtils.readField(currentField, obj, true);
                if (fieldValue != null){
                    Class<?> clazz = CollectionUtils.findCommonElementType(fieldValue);
                    if(clazz != null && BaseInputDTO.class.isAssignableFrom(clazz)){
                        fieldValue.forEach(v -> validate(v, validateInfoList, deep + 1));
                    }
                }
            } else if (currentField.getType().isArray()) {
                //当前属性是数据，且数组元素是BaseInputDTO类型
                Class elementType = currentField.getType().getComponentType();
                if (elementType != null && BaseInputDTO.class.isAssignableFrom(elementType)){
                    Object fieldValue = FieldUtils.readField(currentField, obj, true);
                    if (fieldValue != null) {
                        CollectionUtils.arrayToList(fieldValue).forEach(
                                v -> validate(v, validateInfoList, deep + 1));
                    }
                }
            } else {
                //当前属性是普通对象类型，如果对象值不空，则进一步校验其属性
                Object currentFieldValue = FieldUtils.readField(currentField, obj, true);
                if (currentFieldValue != null) {
                    validate(currentFieldValue, validateInfoList, deep + 1);
                }
            }
        }
    }
    @SneakyThrows
    private static boolean isSimpleType(Class<?> clazz) {
        if(clazz.isPrimitive()
                || clazz == String.class
                || clazz == Character.class
                || Number.class.isAssignableFrom(clazz)
                || Date.class.isAssignableFrom(clazz)){
            return true;
        }
        return false;
    }

    @SneakyThrows
    private static void validateSimpleField(Field currentField, Object obj,
                                            List<ValidateInfo> validateInfoList) {
        //当前属性是j基本类型，可以直接校验
        com.model.annotation.Field fAnnotation = currentField.getAnnotation(com.model.annotation.Field.class);
        if(fAnnotation !=  null) {
            com.model.util.metadata.Field fieldModel = metadataManager.getField(fAnnotation.id());
            if (fieldModel == null) {
                //有@Field注解但是字典xml里找不到
                String errMsg = MessageFormat.format("找不到字典中的数据[{0},{1}]，无法校验！",
                        fAnnotation.id(), fAnnotation.name());
                validateInfoList.add(new ValidateInfo(currentField,"E00099",errMsg));
            }else {
                Object fieldValue = FieldUtils.readField(currentField, obj, true);

                //如果允许为空切值为null则跳过
                if(fAnnotation.isNull() && ObjectUtils.isEmpty(fieldValue)){
                    return;
                }
                // 不允许空值或值不空
                ValidateInfo notNullRes = validateNotNull(currentField, fieldValue, fAnnotation.isNull(), fieldModel);
                if(notNullRes != null) {
                    //有错，非空情况下出现空值则停止校验后面规则
                    validateInfoList.add(notNullRes);
                }else {
                    // TODO: 2024/1/8 校验未完善
//                    boolean metadateFormatValidate = (boolean) ServiceContextHandler.getTxCommon().getEngineInfo().get("没想好");
                    boolean metadateFormatValidate = false;

                    //数据格式校验
                    if(metadateFormatValidate && fieldModel.getFormat() != null &&
                    !fieldModel.getFormat().isEmpty()){
                        //ValidateInfo formatValidateRes = validateFormat(currentField, fieldValue, fieldModel);
                        ValidateInfo formatValidateRes = null;
                        if(formatValidateRes != null) {
                            validateInfoList.add(formatValidateRes);
                        }
                    }

                    //长度校验(数据格式为null时进行长度校验)
                    if(!metadateFormatValidate || fieldModel.getFormat() == null ||
                            fieldModel.getFormat().isEmpty()){
                        ValidateInfo lengthValidateRes = validateLength(currentField, fieldValue, fieldModel.getLength(),
                                                                fieldModel.getName(), fieldModel.getDesc());
                        if(lengthValidateRes != null){
                            validateInfoList.add(lengthValidateRes);
                        }
                    }

                    //精度校验（数据格式不为D（x，y）时进行单独的精度校验）
                    if(StringUtil.isNullOrEmpty(fieldModel.getFormat()) ||
                                !fieldModel.getFormat().startsWith("D(")) {
                        ValidateInfo digitValidateRes = validateDigit(currentField, fieldValue,
                                        fieldModel.getDigits(),fieldModel.getName(), fieldModel.getDesc());
                        if(digitValidateRes != null) {
                            validateInfoList.add(digitValidateRes);
                        }
                    }

                    //当模式不是日期时 进行模式校验（含日期格式）
                    if(!fieldModel.getPattern().equals(fieldModel.getFormat())) {
                        ValidateInfo patternValiddateRes = validatePattern(currentField, fieldValue,
                                    fieldModel.getPattern(), fieldModel.getName(), fieldModel.getDesc());
                        if(patternValiddateRes != null){
                            validateInfoList.add(patternValiddateRes);
                        }
                    }

                    // TODO: 2024/1/8 开关，没想好 
                    //枚举校验
//                    boolean metadataEnumValidate = (boolean) ServiceContextHandler.getTxCommon().getEngineInfo()
//                            .get("没想好");
                    boolean metadataEnumValidate = false;
                    if(metadataEnumValidate){
                        ValidateInfo enumValidateRes = validateEnum(currentField, fieldValue, fieldModel);
                        if(enumValidateRes != null) {
                            validateInfoList.add(enumValidateRes);
                        }
                    }

                    //应用扩展校验
                    //没有扩展校验规则返回
                    if(StringUtil.isNullOrEmpty(fAnnotation.extendRules())) {
                        return;
                    }
                    //支持多个扩展规则，每一个扩展规则以逗号分隔
                    String[] extendedRules = fAnnotation.extendRules().split(",");
                    for(String extendedRule : extendedRules) {
                        ValidateInfo extValidateRes = validateExtend(currentField, fieldValue, extendedRule);
                        if (extValidateRes != null) {
                            validateInfoList.add(extValidateRes);
                        }
                    }

                }

            }
        }
    }

    /**
     * 非空校验
     * @param f 字段属性
     * @param fieldObj 字段值
     * @param isNullAllowed 是否允许为空
     * @param field
     * @return
     */
    private static ValidateInfo validateNotNull(Field f, Object fieldObj, boolean isNullAllowed,
                                                com.model.util.metadata.Field field){
        if (!isNullAllowed && ObjectUtils.isEmpty(fieldObj)) {
            String errMsg = MessageFormat.format("[{0},{1}]",field.getName(),field.getDesc());
            return new ValidateInfo(f,"E00001",errMsg);
        }
        return null;
    }

    /**
     * 长度校验
     * @param f 字段属性
     * @param fieldObj 字段值
     * @param length 最大长度
     * @param annotationName
     * @param annotationDesc
     * @return
     */
    private static ValidateInfo validateLength(Field f, Object fieldObj, int length,
                                               String annotationName, String annotationDesc){
        if(length != 0 && fieldObj != null && fieldObj.toString().length() > length) {
            String errorMsg = MessageFormat.format("[{0},{1}]长度不能超过{2}",
                    annotationName, annotationDesc, length);
            ValidateInfo vi = new ValidateInfo(f, "E00002", errorMsg);
            return vi;
        }
        return null;
    }

    /**
     * 精度校验
     * @param f 字段属性
     * @param fieldObj 字段值
     * @param digits 精度，标识小数位数，0表示不被小数限制
     * @param annotationName
     * @param annotationDesc
     * @return
     */
    private static ValidateInfo validateDigit(Field f, Object fieldObj, int digits,
                                              String annotationName, String annotationDesc){
        BigDecimal fieldValue = BigDecimal.ZERO;
        if (digits != 0 && fieldObj != null) {
            if(!(fieldObj instanceof BigDecimal)) {
                if(fieldObj instanceof String || fieldObj instanceof Long ||fieldObj instanceof Integer
                        || fieldObj instanceof Double) {
                    fieldValue = new BigDecimal(fieldObj + "");
                } else {
                    String errMsg = MessageFormat.format("[{0},{1}校验失败，请检查上送的数据类型]",
                            annotationName,annotationDesc);
                    return new ValidateInfo(f, "E00002",errMsg);
                }
            } else {
                fieldValue = (BigDecimal) fieldObj;
            }
            if (fieldValue.scale() > digits) {
                String errMsg = MessageFormat.format("[{0},{1}精度不能超过{2}]",annotationName, annotationDesc,digits);
                return new ValidateInfo(f, "E00002", errMsg);
            }
        }
        return null;
    }

    /**
     * 表达式校验
     * @param f 字段
     * @param fieldObj 字段值
     * @param pattern 表达式
     * @param annotationName
     * @param annotationDesc
     * @return
     */
    private static ValidateInfo validatePattern(Field f, Object fieldObj, String pattern,
                                                String annotationName, String annotationDesc) {
        if (null == fieldObj || StringUtil.isNullOrEmpty(pattern)) {
            return null;
        } else if (isDatePattern(pattern) && validateDate(fieldObj.toString(), pattern)) {
            return null;
        } else if (fieldObj.toString().matches(pattern)) {
            return null;
        } else {
            String errMsg = MessageFormat.format("[{0}，{1}]格式校验失败,不满足[{2}]的要求",
                    annotationName, annotationDesc, pattern);
            ValidateInfo vi = new ValidateInfo(f, "E00002", errMsg);
            return vi;
        }
    }

    /**
     * 判断字符串是否符合日期格式
     * @param s 字符串
     * @param pattern 匹配日期模式
     * @return
     */
    private static boolean validateDate(String s, String pattern) {
        try {
            if (time6.equals(pattern)) {
                time6Formatter.parse(s);
            } else if (date8.equals(pattern)) {
                date8Formatter.parse(s);
            } else if (date14.equals(pattern)) {
                date14Formatter.parse(s);
            } else if (date17.equals(pattern)) {
                date17Formatter.parse(s);
            } else if (date20.equals(pattern)) {
                date20Formatter.parse(s);
            } else if (date4.equals(pattern)) {
                date4Formatter.parse(s);
            } else if (date6.equals(pattern)) {
                date6Formatter.parse(s);
            }
            return true;
        } catch (RuntimeException e) {
           log.error("字符串不符合日期格式",e);
           return false;
        }
    }

    /**
     * 枚举校验
     * @param f
     * @param fieldObj
     * @param fieldModel
     * @return
     */
    private static ValidateInfo validateEnum(Field f, Object fieldObj, com.model.util.metadata.Field fieldModel) {
        List<Enumeration> enums = fieldModel.getEnumerations();
        Boolean existFlag = false;
        if (null == enums || enums.isEmpty()) {
            return null;
        }else {
            for (Enumeration eachEnum : enums) {
                if(eachEnum.getCode().equals(fieldObj)) {
                    existFlag = true;
                    break;
                }
            }
            if (!existFlag) {
                String errMsg = MessageFormat.format("属性【{0}】上送的值【{1}】不在数据字典id=【{2}】，" +
                        "name = 【{3}】，desc = 【{4}】的枚举取值范围内",f.getName(), fieldObj,
                        fieldModel.getId(), fieldModel.getName(), fieldModel.getDesc());
            }
        }
        return null;
    }


    private static ValidateInfo validateExtend(Field f, Object fieldObj, String extednRule) {
        ValidateResult validateResult;
        //公共规则，自己写
        if (isCommonExtendRule((extednRule))) {
            validateResult = null;
        }else {
            MetadataValidateExt metadataValidateExt = ContextUtil.getBeanNullable(MetadataValidateExt.class);
            if(metadataValidateExt == null) {
                log.warn("元数据扩展校验没有接口");
                return null;
            }
            validateResult = metadataValidateExt.extendedValidate(extednRule,fieldObj);
        }

        if(validateResult.isResultFlag()) {
            return null;
        } else {
            String errMsg = MessageFormat.format("属性【{0}】上送的值【{1}】，元数据校验扩展规则【{2}】，" +
                            "检查结果不通过，原因【{3}】",f.getName(), fieldObj, extednRule, validateResult.getErrorMsg());
            return new ValidateInfo(f,"E00002",errMsg);
        }
    }

    private static boolean validateFormat(){
        return false;
    }

    /**
     * 判断表达式是否为日期格式
     * @param pattern
     * @return
     */
    private static boolean isDatePattern(String pattern) {
        if(time6.equals(pattern) || date8.equals(pattern) || date14.equals(pattern)
                || date17.equals(pattern) || date20.equals(pattern) || date4.equals(pattern)
                || date6.equals(pattern)) {
            return true;
        }
        return false;
    }

    private static boolean isCommonExtendRule(String extendRules) {
        boolean isCommonExtendRule = false;
        if (MetadataConstants.commonRules.contains(extendRules)) {
            isCommonExtendRule = true;
        }
        return  isCommonExtendRule;
    }
}
