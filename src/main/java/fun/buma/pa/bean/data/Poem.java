/**
 * Poem
 * Created on 2019/11/23 17:34
 */
package fun.buma.pa.bean.data;

import fun.buma.pa.util.ToString;

/**
 * 诗.
 * <p><br>
 * @author mmmm 2019/11/23 17:34
 * @version 1.0.0
 */
public class Poem {
    private String id;
    private String title;
    private String subTitle;
    private String preface;
    private String author;
    private String content;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getPreface() {
        return preface;
    }

    public void setPreface(String preface) {
        this.preface = preface;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        ToString.append(sb, this.id, "id");
        ToString.append(sb, this.title, "标题");
        ToString.append(sb, this.subTitle, "副标题");
        ToString.append(sb, this.preface, "序");
        ToString.append(sb, this.author, "作者");
        ToString.append(sb, this.content, "内容");
        sb.append("]");

        return sb.toString();
    }
}
