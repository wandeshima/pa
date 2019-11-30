/**
 * Main
 * Copyright 2019 ....
 * All rights reserved.
 * Created on 2019/11/25 17:02
 */
package fun.buma.pa;

import fun.buma.pa.service.data.PoemService;

/**
 * 入口.
 * <p><br>
 * @author mmmm 2019/11/25 17:02
 * @version 1.0.0
 */
public class Main {
    public static void main(String[] agrs) {
        PoemService poemService = new PoemService();

        //测试
        String url = "https://so.gushiwen.org/gushi/tangshi.aspx";
        String regex = ".*shiwenv_.*";
        String func = "shici";
        Integer limit = 9;
        poemService.getPoems(url, func, regex, limit);

    }
}
