/**
 * PaUrlService
 * Copyright 2019 ....
 * All rights reserved.
 * Created on 2019/11/25 17:04
 */
package fun.buma.pa.service.core;

import fun.buma.pa.bean.core.PaUrl;
import fun.buma.pa.service.data.PoemService;
import fun.buma.pa.util.Url;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 链接处理.
 * <p><br>
 * @author mmmm 2019/11/25 17:04
 * @version 1.0.0
 */
public class PaUrlService {
    private static final String CORE_CONFIG = "core-config.xml";

    /**
     * 查询地址列表.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 17:16:11<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 17:16:11<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     */
    public List<PaUrl> getPaUrlList(PaUrl paUrl){
        List<PaUrl> result = new ArrayList<>();
        try (InputStream inputStream = Resources.getResourceAsStream(CORE_CONFIG)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = sqlSessionFactory.openSession();
            result = session.selectList("getPaUrlList", paUrl);
            session.clearCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<PaUrl> getPaUrlList(String regex, String func, Integer done) {
        PaUrl paUrl = new PaUrl();
        paUrl.setFunc(func);
        paUrl.setUrl(regex);
        paUrl.setDone(done);
        return getPaUrlList(paUrl);
    }

    /**
     * 取得url列表.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 22:27:55<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 22:27:55<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param paUrl
     * @return java.util.List<java.lang.String>
     */
    public List<String> getUrlList(PaUrl paUrl) {
        List<String> result = new ArrayList<>();
        try (InputStream inputStream = Resources.getResourceAsStream(CORE_CONFIG)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = sqlSessionFactory.openSession();
            result = session.selectList("getUrlList", paUrl);
            session.clearCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public List<String> getUrlList(String regex, String func, Integer done) {
        PaUrl paUrl = new PaUrl();
        paUrl.setFunc(func);
        paUrl.setUrl(regex);
        paUrl.setDone(done);
        return getUrlList(paUrl);
    }

    /**
     * 插入新记录.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 20:18:40<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 20:18:40<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param paUrlList
     */
    public void insertUrlList(List<PaUrl> paUrlList) {
        try (InputStream inputStream = Resources.getResourceAsStream(CORE_CONFIG)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = sqlSessionFactory.openSession();
            int i = 0;
            for (PaUrl u : paUrlList) {
                i++;
                session.insert("insertNewUrl", u);
                if (i % 1000 == 0 || i == paUrlList.size()) {
                    session.commit();
                    session.clearCache();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得当前页面所有链接.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 19:05:03<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 19:05:03<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param url
     * @return java.util.HashSet<java.lang.String>
     */
    public HashSet<String> getUrlSetFromPage(String url, String regex) {
        return Url.getUrlSetFromPage(url, regex);
    }

    /**
     * 取当前页面所有链接，并去掉数据库中已有的链接.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 20:06:01<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 20:06:01<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param url
     * @param regex
     * @param func
     * @return java.util.List<java.lang.String>
     */
    public List<String> trimUrlSet(String url, String regex, String func) {
        HashSet<String> newUrlSet = Url.getUrlSetFromPage(url, regex);
        PaUrl paUrl = new PaUrl();
        paUrl.setFunc(func);
        List<String> result = new ArrayList<>();
        List<String> oldUrlList = getUrlList(regex, func, 0);
        PoemService poemService = new PoemService();
        newUrlSet.stream().filter(u -> !oldUrlList.contains(u)).forEach(result::add);
        for (String u : result) {
            poemService.getPoem(u);
        }
        return result;
    }

    /**
     * 建对象.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 21:11:32<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 21:11:32<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param urlList
     * @return java.util.List<fun.buma.pa.bean.core.PaUrl>
     */
    public List<PaUrl> initPaUrl(List<String> urlList, String func) {
        List<PaUrl> newPaUrlList = urlList.stream().map(u -> {
            PaUrl x = new PaUrl();
            x.setFunc(func);
            x.setDone(0);
            x.setUrl(u);
            x.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            return x;
        }).collect(Collectors.toList());
        return newPaUrlList;
    }

    /**
     * 网址插.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 21:04:41<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 21:04:41<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param baseUrl
     * @param regex
     * @param func
     * @param level
     * @return void
     */
    public void insertAllByUrl(String baseUrl, String regex, String func, Integer level) {
        List<String> currentUrl = trimUrlSet(baseUrl, regex, func);
        if (currentUrl.size() > 0) {
            List<PaUrl> paUrlList = initPaUrl(currentUrl, func);
            insertUrlList(paUrlList);
            System.out.println();
            currentUrl.forEach(System.out::println);
            System.out.print("#### 搞到：" + currentUrl.size() + "条。 #### @ " + baseUrl.substring(baseUrl.lastIndexOf("/")) + "\n");
            if (level > 0) {
                for (String u : currentUrl) {
                    insertAllByUrl(u, regex, func, level - 1);
                }
            }
        } else {
            System.out.print(".");
        }
    }

    /**
     * 数据库插.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月26日 07:39:02<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月26日 07:39:02<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param regex
     * @param func
     * @param level
     * @return void
     */
    public void insertAllByDateBase(String regex, String func, Integer level) {
        List<String> urlList = getUrlList(regex, func, 0);
        for (String s : urlList) {
            insertAllByUrl(s, regex, func, level);
        }
    }

}
