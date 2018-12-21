package com.lmdsoft.modules.common.validator;



import com.lmdsoft.modules.common.common.RRException;
import com.lmdsoft.modules.common.exception.MyException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 类ValidatorUtils的功能描述:
 * hibernate-validator校验工具类
 * @Auther lmdsoft
 * @Date 2018-08-25 16:15:28
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws RRException  校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws RRException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
        	ConstraintViolation<Object> constraint = (ConstraintViolation<Object>)constraintViolations.iterator().next();
            throw new MyException(constraint.getMessage());
        }
    }
}
