package com.lmdsoft.modules.common.validator;

import com.lmdsoft.modules.common.common.RRException;
import org.apache.commons.lang.StringUtils;

/**
 * 类Assert的功能描述:
 * 数据校验
 * @Auther lmdsoft
 * @Date 2018-08-25 16:18:34
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }
}
