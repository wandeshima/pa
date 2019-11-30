/**
 * PoemService
 * Copyright 2019 ....
 * All rights reserved.
 * Created on 2019/11/25 23:28
 */
package fun.buma.pa.service.data;

import fun.buma.pa.bean.data.Poem;
import fun.buma.pa.service.core.PaUrlService;
import fun.buma.pa.util.UrlUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 搞个诗.
 * <p><br>
 * @author mmmm 2019/11/25 23:28
 * @version 1.0.0
 */
public class PoemService {
    private static final String CORE_CONFIG = "core-config.xml";

    public static void main(String[] args) {
        System.out.println("hello poem");
    }

    private Integer poemLimit = 0;

    private PaUrlService paUrlService = new PaUrlService();


    /**
     * 搞个诗.gushiwen.org
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月30日 15:21:30<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月30日 15:21:30<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param introUrl  地址
     * @param func  分类
     */
    private void getOnePoem(String introUrl, String func, String regex) {
        Document doc = UrlUtil.getDocByUrl(introUrl);

        // 取网址
        UrlUtil.getUrlFromDoc(doc);
        Set<String> urlFromDoc = UrlUtil.getUrlFromDoc(doc);
        // 自身地址加入set
        urlFromDoc.add(introUrl);
        // 本域名
        String domain = UrlUtil.getDomain(introUrl);
        // 符合本域名正则
        String domainRex = ".*" + domain.replaceAll("\\.", "\\\\.") + ".*";
        String reg = "(^/.*)|(" + domainRex + ")";
        UrlUtil.urlSetFilteredByRegex(urlFromDoc, reg);
        // 给定的正则
        UrlUtil.urlSetFilteredByRegex(urlFromDoc, regex);
        // 数据库中本域名数据
        List<String> urlListFromDataBase = paUrlService.getUrlListFromDataBase(domain);
        // 去除数据库中已存在网址
        urlFromDoc.removeAll(urlListFromDataBase);
        // 剩下的url插入数据库
        paUrlService.insertUrlSet(urlFromDoc, func);

        // 取诗
        Element content = doc.select("div.main3 div.left div textarea").first();
        Whitelist wl = new Whitelist();
        wl.addTags("img","a","p","b","br","table","tr","td");
        if (null != content) {
            String c = Jsoup.clean(content.outerHtml(), wl);
            String cUrl = c.substring(c.lastIndexOf("http"));
            String cTitle = c.substring(c.lastIndexOf("《")+1, c.lastIndexOf("》"));
            String cAuthor = c.substring(c.lastIndexOf("——")+1, c.lastIndexOf("《"));
            String cContent = c.substring(0, c.lastIndexOf("——"));

            Poem poem = new Poem();
            poem.setTitle(cTitle);
            poem.setAuthor(cAuthor);
            poem.setContent(cContent);
            poem.setId(UUID.randomUUID().toString().replaceAll("-",""));
            poem.setUrl(cUrl);

            // 存诗
            insertPoem(poem);
        }
        // 标记网址done
        paUrlService.setUrlDone(introUrl);
    }

    /**
     * 一直搞诗.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月30日 15:40:37<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月30日 15:40:37<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param introUrl  地址
     * @param func  分类
     */
    public void getPoems(String introUrl, String func, String regex, Integer limit) {
        // 先整一首诗，地址就有了。
        getOnePoem(introUrl, func, regex);
        // 接着搞
        while (poemLimit < limit) {
            List<String> urlNotDone = paUrlService.getUrlNotDone(func);
            if (null == urlNotDone || urlNotDone.isEmpty()) {
                break;
            }
            for (String u : urlNotDone) {
                getOnePoem(u, func, regex);
                if (poemLimit >= limit) {
                    break;
                }
            }
        }
        poemLimit = 0;
    }

    /**
     * 存诗.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月30日 15:48:04<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月30日 15:48:04<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param poem  诗
     */
    private void insertPoem(Poem poem) {
        try (InputStream inputStream = Resources.getResourceAsStream(CORE_CONFIG)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = sqlSessionFactory.openSession();
            session.insert("insertPoem", poem);
            session.commit();
            session.clearCache();
            // 计数
            poemLimit++;
            // 打印
            System.out.println("#" + poemLimit + " 搞到：" + poem.getTitle() + " @ " + poem.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
