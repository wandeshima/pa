/**
 * Main
 * Copyright 2019 ....
 * All rights reserved.
 * Created on 2019/11/25 17:02
 */
package fun.buma.pa;

import fun.buma.pa.service.core.PaUrlService;

/**
 * 入口.
 * <p><br>
 * @author mmmm 2019/11/25 17:02
 * @version 1.0.0
 */
public class Main {
    public static void main(String[] agrs) {
        PaUrlService paUrlService = new PaUrlService();

        //测试
        String url = "https://so.gushiwen.org/gushi/tangshi.aspx";
        String regex = "shiwenv";
        String func = "shici";
        Integer level = 0;
//        paUrlService.insertAllByUrl(url, regex, func, level);
        paUrlService.insertAllByDateBase(null, func, level);
    }
}
