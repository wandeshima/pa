/**
 * PoemService
 * Copyright 2019 ....
 * All rights reserved.
 * Created on 2019/11/25 23:28
 */
package fun.buma.pa.service.data;

import fun.buma.pa.bean.data.Poem;
import fun.buma.pa.util.Encoding;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * //TODO.
 * <p><br>
 * @author mmmm 2019/11/25 23:28
 * @version 1.0.0
 */
public class PoemService {
    private static final String CORE_CONFIG = "core-config.xml";

    public static void main(String[] args) {
        System.out.println("hello poem");
    }


    //搞个诗
    public void getPoem(String introUrl) {
        URL url = null;
        try {
            url = new URL(introUrl);
            try(InputStream inputStream = url.openStream()) {
                String pageCoding = Encoding.getPageCoding(inputStream);
                Document doc = Jsoup.parse(inputStream, pageCoding, introUrl);
                Element content = doc.select("div.main3 div.left div textarea").first();
                Whitelist wl = new Whitelist();
                wl.addTags("img","a","p","b","br","table","tr","td");
                if (null != content) {
                    String c = Jsoup.clean(content.outerHtml(), wl);
                    String cUrl = c.substring(c.lastIndexOf("http"));
                    String cTitle = c.substring(c.lastIndexOf("《")+1, c.lastIndexOf("》"));
                    String cAuthor = c.substring(c.lastIndexOf("—")+1, c.lastIndexOf("《"));
                    String cContent = c.substring(0, c.lastIndexOf("—"));

                    Poem poem = new Poem();
                    poem.setTitle(cTitle);
                    poem.setAuthor(cAuthor);
                    poem.setContent(cContent);
                    poem.setId(UUID.randomUUID().toString().replaceAll("-",""));
                    poem.setUrl(cUrl);

                    insertPoem(poem);

                    System.out.println("搞到：" + cTitle + " @ " + cUrl);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


    /**
     * 插入新记录.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 20:18:40<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 20:18:40<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param poemList
     */
    public void insertPoemList(List<Poem> poemList) {
        try (InputStream inputStream = Resources.getResourceAsStream(CORE_CONFIG)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = sqlSessionFactory.openSession();
            int i = 0;
            for (Poem p : poemList) {
                i++;
                session.insert("insertPoem", p);
                if (i % 1000 == 0 || i == poemList.size()) {
                    session.commit();
                    session.clearCache();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertPoem(Poem poem) {
        try (InputStream inputStream = Resources.getResourceAsStream(CORE_CONFIG)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = sqlSessionFactory.openSession();
            session.insert("insertPoem", poem);
            session.commit();
            session.clearCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
