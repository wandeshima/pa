/**
 * UrlUtil
 * Copyright 2019 ....
 * All rights reserved.
 * Created on 2019/11/25 16:28
 */
package fun.buma.pa.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

/**
 * 网址池.
 * <p><br>
 * @author mmmm 2019/11/25 16:28
 * @version 1.0.0
 */
public class UrlUtil {

    /**
     * 取得域名.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 16:37:11<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 16:37:11<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * 域名正则 | IP正则
     * Pattern p1 = Pattern.compile("(?<=http(s)?://|\b)([1]?[0-9]?[0-9]|[2]?([0-4]?[0-9]|[5]?[0-5]))\\.([1]?[0-9]?[0-9]|[2]?([0-4]?[0-9]|[5]?[0-5]))\\.([1]?[0-9]?[0-9]|[2]?([0-4]?[0-9]|[5]?[0-5]))\\.([1]?[0-9]?[0-9]|[2]?([0-4]?[0-9]|[5]?[0-5]))(:[0-9]{4})?(\b|(?=/))",Pattern.CASE_INSENSITIVE);
     * Pattern p2 = Pattern.compile("(?<=http(s)?://|\\.)[^.&?]*?\\.(com|cn|net|org|biz|info|cc|tv|fun|me|us|com.cn|edu|gov|la)(\b|(?=/))",Pattern.CASE_INSENSITIVE);
     * @param url
     * @return java.lang.String
     */
    public static String getDomain(String url) {
        String domain = null;
        if (null != url && !url.isEmpty()) {
            Pattern p = compile("(?<=http(s)?://|\\.)[^.&?]*?\\.(com|cn|net|org|biz|info|cc|tv|fun|me|us|com.cn|edu|la|gov.cn)(\\b|(?=/))|(?<=http(s)?://|\\b)([1]?[0-9]?[0-9]|[2]?([0-4]?[0-9]|[5]?[0-5]))\\.([1]?[0-9]?[0-9]|[2]?([0-4]?[0-9]|[5]?[0-5]))\\.([1]?[0-9]?[0-9]|[2]?([0-4]?[0-9]|[5]?[0-5]))\\.([1]?[0-9]?[0-9]|[2]?([0-4]?[0-9]|[5]?[0-5]))(:[0-9]{4})?(\\b|(?=/))", CASE_INSENSITIVE);
            Matcher matcher = p.matcher(url);
            while(matcher.find()){
                domain = matcher.group();
            }
        }
        return domain;
    }
    private static String getBaseUrl(String url) {
        String domain = null;
        if (null != url && !url.isEmpty()) {
            Pattern p = compile("(http(s)?://)([^.&?].*\\.)?[^.&?]*?\\.(com|cn|net|org|biz|info|cc|tv|fun|me|us|com.cn|edu|la|gov.cn)(\\b|(?=/))|(http(s)?://)([1]?[0-9]?[0-9]|[2]?([0-4]?[0-9]|[5]?[0-5]))\\.([1]?[0-9]?[0-9]|[2]?([0-4]?[0-9]|[5]?[0-5]))\\.([1]?[0-9]?[0-9]|[2]?([0-4]?[0-9]|[5]?[0-5]))\\.([1]?[0-9]?[0-9]|[2]?([0-4]?[0-9]|[5]?[0-5]))(:[0-9]{4})?(\\b|(?=/))", CASE_INSENSITIVE);
            Matcher matcher = p.matcher(url);
            while(matcher.find()){
                domain = matcher.group();
            }
        }
        return domain;
    }
//
//    /**
//     * 取得当前页面所有本域名的链接.
//     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月25日 17:57:02<br>
//     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月25日 17:57:02<br>
//     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
//     * @param baseUrl
//     * @return java.lang.String
//     */
//    public static HashSet<String> getUrlSetFromPage(String baseUrl, String regex) {
//        HashSet<String> result = new HashSet<>();
//        URL url;
//        try {
//            url = new URL(baseUrl);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return result;
//        }
//        String domain = getDomain(baseUrl);
//        if (regex == null) {
//            regex = domain;
//        }
//        String pre = baseUrl.substring(0, baseUrl.indexOf(domain));
//        try (InputStream inputStream = url.openStream()) {
//            Document doc = Jsoup.parse(inputStream, Encoding.getPageCoding(inputStream), baseUrl);
//            Elements links = doc.getElementsByTag("a");
//            for (Element link : links) {
//                String l = link.attr("href");
//                String pattern = ".*" + regex.replaceAll("\\.", "\\\\.") + ".*";
//                if (!Pattern.matches(pattern, l)) {
//                    continue;
//                }
//                if (l.contains("javascript")) {
//                    continue;
//                }
//                if (l.contains(domain)) {
//                    result.add(l);
//                } else if (l.startsWith("/") && !l.contains(domain)) {
//                    result.add(pre + domain + l);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return result;
//        }
//        return result;
//    }

    // 以上都没用

    ///////////

    /**
     * 取页面内容document.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月30日 14:11:49<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月30日 14:11:49<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param baseUrl   地址
     * @return org.jsoup.nodes.Document
     */
    public static Document getDocByUrl(String baseUrl) {
        URL url;
        Document doc = null;
        try {
            url = new URL(baseUrl);
            try (InputStream inputStream = url.openStream()) {
                doc = Jsoup.parse(inputStream, Encoding.getPageCoding(inputStream), baseUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 内容中取所有链接.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月30日 14:23:08<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月30日 14:23:08<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param doc
     * @return java.util.HashSet<java.lang.String>
     */
    public static Set<String> getUrlFromDoc(Document doc) {
        HashSet<String> result = new HashSet<>();
        String baseUrl = getBaseUrl(doc.location());
        Elements links = doc.getElementsByTag("a");
        for (Element link : links) {
            String l = link.attr("href");
            if (l.startsWith("/")) {
                l = baseUrl + l;
            }
            result.add(l);
        }
        return result;
    }

    /**
     * 正则过滤.
     * <p><b>创建人：</b><br>&nbsp;&nbsp; mmmm 2019年11月30日 14:32:29<br>
     * <p><b>修改人：</b><br>&nbsp;&nbsp; 2019年11月30日 14:32:29<br>
     * <p><b>修改说明：</b><br>&nbsp;&nbsp;<br>
     * @param urlSet
     * @param regex
     */
    public static void urlSetFilteredByRegex(Set<String> urlSet, String regex) {
        urlSet.removeIf(u -> !Pattern.matches(regex, u));
    }

}
