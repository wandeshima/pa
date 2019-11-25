/**
 * PaUrl
 * Copyright 2019 ....
 * All rights reserved.
 * Created on 2019/11/25 16:42
 */
package fun.buma.pa.bean.core;

import fun.buma.pa.util.ToString;

/**
 * 网址.
 * <p><br>
 * @author mmmm 2019/11/25 16:42
 * @version 1.0.0
 */
public class PaUrl {
    private String id;
    private String func;
    private String url;
    private Integer done;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        ToString.append(sb, this.id, "id");
        ToString.append(sb, this.func, "爬哪个");
        ToString.append(sb, this.url, "地址");
        ToString.append(sb, this.done, "是否完成");
        sb.append("]");
        return sb.toString();
    }
}
