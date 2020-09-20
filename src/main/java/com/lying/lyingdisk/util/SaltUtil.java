package com.lying.lyingdisk.util;

import cn.hutool.core.util.StrUtil;

public class SaltUtil {
    public static String getSalt(){

        return StrUtil.uuid().substring(0,8);

    }
}
