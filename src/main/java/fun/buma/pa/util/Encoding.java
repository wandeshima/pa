/**
 * Encoding
 * Copyright 2019 ....
 * All rights reserved.
 * Created on 2019/11/25 16:24
 */
package fun.buma.pa.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * 编码工具类.
 * <p><br>
 * @author mmmm 2019/11/25 16:24
 * @version 1.0.0
 */
public class Encoding {

    private static final String[] PAGECODINGS = {"utf-8", "gbk", "gb2312", "unicode"};

    /**
     * GBK2UTF8.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 16:25:13<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 16:25:13<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param gbkStr
     * @return java.lang.String
     */
    public static String getUTF8StringFromGBKString(String gbkStr) {
        try {
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }
    }

    /**
     * getUTF8BytesFromGBKString.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 16:26:13<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 16:26:13<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param gbkStr
     * @return byte[]
     */
    private static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }

    /**
     * 取得页面编码.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 18:08:17<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 18:08:17<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param inputStream
     * @return java.lang.String
     */
    public static String getPageCoding(InputStream inputStream) throws IOException {
        BufferedReader nbf = new BufferedReader(new InputStreamReader(inputStream));
        String singleLine;
        // 默认utf-8
        String charset = "utf-8";
        while ((singleLine = nbf.readLine())!=null) {
            if (singleLine.contains("charset")) {
                for (String coding : PAGECODINGS) {
                    if (singleLine.contains(coding)) {
                        charset = coding;
                    }
                }
                break;
            }
        }
        return charset;
    }
}
