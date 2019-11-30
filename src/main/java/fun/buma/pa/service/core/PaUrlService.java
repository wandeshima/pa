/**
 * PaUrlService
 * Copyright 2019 ....
 * All rights reserved.
 * Created on 2019/11/25 17:04
 */
package fun.buma.pa.service.core;

import fun.buma.pa.bean.core.PaUrl;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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
     * 地址插入到数据库指定分类.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月30日 15:16:29<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月30日 15:16:29<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param urlSet    地址set
     * @param func  分类
     */
    public void insertUrlSet(Set<String> urlSet, String func) {
        ArrayList<String> urlList = new ArrayList<>(urlSet);
        List<PaUrl> paUrlList = initPaUrl(urlList, func);
        insertPaUrlList(paUrlList);
    }

    /**
     * 建PaUrl对象.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 21:11:32<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 21:11:32<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param urlList   地址list
     * @return java.util.List<fun.buma.pa.bean.core.PaUrl>
     */
    private List<PaUrl> initPaUrl(Collection<String> urlList, String func) {
        return urlList.stream().map(u -> {
            PaUrl x = new PaUrl();
            x.setFunc(func);
            x.setDone(0);
            x.setUrl(u);
            x.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            return x;
        }).collect(Collectors.toList());
    }

    /**
     * 插入PaUrlList.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 20:18:40<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 20:18:40<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param paUrlList 对象list
     */
    private void insertPaUrlList(List<PaUrl> paUrlList) {
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
     * 标记已完成.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月29日 08:05:20<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月29日 08:05:20<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param url   地址
     */
    public void setUrlDone(String url) {
        try (InputStream inputStream = Resources.getResourceAsStream(CORE_CONFIG)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = sqlSessionFactory.openSession();
            session.update("updateUrlStatus", url);
            session.commit();
            session.clearCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库取域名下网址.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月30日 14:57:57<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月30日 14:57:57<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param domain
     * @return java.util.List<java.lang.String>
     */
    public List<String> getUrlListFromDataBase(String domain) {
        List<String> result = new ArrayList<>();
        try (InputStream inputStream = Resources.getResourceAsStream(CORE_CONFIG)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = sqlSessionFactory.openSession();
            result = session.selectList("getUrlListFromDataBase", domain);
            session.commit();
            session.clearCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 取某分类下未完成的地址.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月30日 15:32:56<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月30日 15:32:56<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param func  分类
     * @return java.util.List<java.lang.String>
     */
    public List<String> getUrlNotDone(String func) {
        if (null == func) {
            return null;
        }
        List<String> result = new ArrayList<>();
        try (InputStream inputStream = Resources.getResourceAsStream(CORE_CONFIG)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = sqlSessionFactory.openSession();
            result = session.selectList("getUrlNotDone", func);
            session.commit();
            session.clearCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
