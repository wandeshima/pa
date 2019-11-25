/**
 * ToString
 * Copyright 2019 ....
 * All rights reserved.
 * Created on 2019/11/25 17:11
 */
package fun.buma.pa.util;

/**
 * 字符串工具类.
 * <p><br>
 * @author mmmm 2019/11/25 17:11
 * @version 1.0.0
 */
public class ToString {
    public static void append(StringBuffer sb, Object obj, String objName) {
        if (null == sb) {
            return;
        }
        if (null != obj) {
            sb.append(",");
            sb.append(objName);
            sb.append(":");
            if (obj instanceof String) {
                sb.append(obj);
            } else {
                sb.append(obj.toString());
            }
        }
    }
}
