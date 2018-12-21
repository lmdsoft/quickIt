package com.lmdsoft.modules.common.validator.group;

import javax.validation.GroupSequence;

/**
 * 类Group的功能描述:
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 * @Auther lmdsoft
 * @Date 2018-08-25 16:19:57
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
