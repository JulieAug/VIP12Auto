package com.testing.driverself;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname HttpDriver
 * @Description 只负责发包收包操作的driver类，不跟自动化流程产生瓜葛
 * 对于请求四大要素的编辑，并且发送请求得到返回结果。
 * 所以不继承autotools
 * @Date 2022/6/23 21:58
 * @Created by 特斯汀Roy
 */
public class HttpDriver {

    private CloseableHttpClient client;
    /**
     * 管理头域信息用到的map。
     */
    public Map<String, String> headerMap;

    //添加或修改，一个头域到存储的头域池里面
    public void addHeader(String key, String value) {
        headerMap.put(key, value);
        useHeader();
    }

    //删除一个头
    public void removeHeader(String key, String value) {
        headerMap.remove(key, value);
    }

    //清空头域
    public void clearHeader() {
        headerMap.clear();
    }

    /**
     * 管理是否使用存好的头域
     */
    public boolean isUseHeader = true;

    //不使用请求头，为false
    public void notUseHeader() {
        isUseHeader = false;
    }

    //使用请求头就置为true
    public void useHeader() {
        isUseHeader = true;
    }

    //决定是否使用cookie
    public boolean isUseCookie = true;

    public void notUserCookie() {
        isUseCookie = false;
    }

    public void useCookie() {
        isUseCookie = true;
    }

    //成员变量cookieStore用于存储测试请求过程中获取的cookie
    public BasicCookieStore cookieStore = new BasicCookieStore();

    /**
     * 在实例化httpdriver的时候，完成headerMap的实例化。
     */
    public HttpDriver() {
        headerMap = new HashMap<>();
    }


    /**
     * 基于isUseCookie的状态，决定创建时，是否使用cookieStore
     */
    public void createClient() {
        if (isUseCookie) {
            client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        } else {
            client = HttpClients.custom().build();
        }
    }

    /**
     * 请求发送成功，那就获取返回结果
     * 发送失败，把异常作为返回结果。
     *
     * @param url
     * @return
     */
    public String get(String url) {
        try {
            createClient();
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse execute = client.execute(get);
            String result = EntityUtils.toString(execute.getEntity(), "utf-8");
            //判断当前使用头域的状态，添加所有存下来的头域
            if (isUseHeader) {
                //遍历存下来的所有头域
                for (String headerKey : headerMap.keySet()) {
                    get.setHeader(headerKey, headerMap.get(headerKey));
                }
            }
            return result;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public String postUrl(String url, String param) {
        try {
            createClient();
            HttpPost post = new HttpPost(url);
            StringEntity entity = new StringEntity(param);
            entity.setContentType("application/x-www-form-urlencoded");
            post.setEntity(entity);
            if (isUseHeader) {
                //遍历存下来的所有头域
                for (String headerKey : headerMap.keySet()) {
                    post.setHeader(headerKey, headerMap.get(headerKey));
                }
            }
            CloseableHttpResponse execute = client.execute(post);
            String result = EntityUtils.toString(execute.getEntity(), "utf-8");
            return result;

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String postJson(String url, String param) {
        try {
            createClient();
            HttpPost post = new HttpPost(url);
            StringEntity entity = new StringEntity(param);
            entity.setContentType("application/json");
            post.setEntity(entity);
            if (isUseHeader) {
                //遍历存下来的所有头域
                for (String headerKey : headerMap.keySet()) {
                    post.setHeader(headerKey, headerMap.get(headerKey));
                }
            }
            CloseableHttpResponse execute = client.execute(post);
            String result = EntityUtils.toString(execute.getEntity(), "utf-8");
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 用json格式进行参数传递，为了区分文本和文件参数，固定使用{"文件":{},"文本":{}}格式
     *
     * @param url
     * @param param
     * @return
     */
    public String postUpload(String url, String param) {
        try {
            createClient();
            HttpPost post = new HttpPost(url);
            //对参数进行处理
            JSONObject paramJSON = JSON.parseObject(param);
            MultipartEntityBuilder meb = MultipartEntityBuilder.create();
            //解析文件参数，addBinaryBody
            JSONObject fileParam = paramJSON.getJSONObject("文件");
            for (String fileKey : fileParam.keySet()) {
                meb.addBinaryBody(fileKey, new File(fileParam.get(fileKey).toString()));
            }
            //解析文本参数，addTextBody
            JSONObject textParam = paramJSON.getJSONObject("文本");
            for (String textKey : textParam.keySet()) {
                meb.addTextBody(textKey, textParam.get(textKey).toString());
            }
            HttpEntity build = meb.build();
            post.setEntity(build);

            if (isUseHeader) {
                //遍历存下来的所有头域
                for (String headerKey : headerMap.keySet()) {
                    post.setHeader(headerKey, headerMap.get(headerKey));
                }
            }


            CloseableHttpResponse execute = client.execute(post);
            String result = EntityUtils.toString(execute.getEntity(), "utf-8");
            return result;
        } catch (IOException e) {
            return e.getMessage();
        }

    }

    /**
     * 添加类型参数，可以选择不同格式进行请求。
     *
     * @param url
     * @param type
     * @param param
     * @return
     */
    public String post(String url, String type, String param) {
        try {
            createClient();
            HttpPost post = new HttpPost(url);
            HttpEntity entity;
            switch (type) {
                case "json":
                    StringEntity jsonentity = new StringEntity(param,"utf-8");
                    jsonentity.setContentType("application/json");
                    entity = jsonentity;
                    break;
                case "file":
                    JSONObject paramJSON = JSON.parseObject(param);
                    MultipartEntityBuilder meb = MultipartEntityBuilder.create();
                    //解析文件参数，addBinaryBody
                    JSONObject fileParam = paramJSON.getJSONObject("文件");
                    for (String fileKey : fileParam.keySet()) {
                        meb.addBinaryBody(fileKey, new File(fileParam.get(fileKey).toString()));
                    }
                    //解析文本参数，addTextBody
                    JSONObject textParam = paramJSON.getJSONObject("文本");
                    for (String textKey : textParam.keySet()) {
                        meb.addTextBody(textKey, textParam.get(textKey).toString());
                    }
                    entity = meb.build();
                    break;
                case "xml":
                    StringEntity xmlentity=new StringEntity(param);
                    xmlentity.setContentType("text/xml");
                    entity=xmlentity;
                    break;
                case "url":
                default:
                    StringEntity sentity = new StringEntity(param,"utf-8");
                    sentity.setContentType("application/x-www-form-urlencoded");
                    entity = sentity;
                    break;
            }
            post.setEntity(entity);

            if (isUseHeader) {
                //遍历存下来的所有头域
                for (String headerKey : headerMap.keySet()) {
                    post.setHeader(headerKey, headerMap.get(headerKey));
                }
            }

            CloseableHttpResponse execute = client.execute(post);
            String result = EntityUtils.toString(execute.getEntity(), "utf-8");
            return result;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    /**
     * 封装delete方法
     *
     * @param url
     * @return
     */
    public String delete(String url) {
        try {
            createClient();
            HttpDelete delete = new HttpDelete(url);
            CloseableHttpResponse execute = client.execute(delete);
            String result = EntityUtils.toString(execute.getEntity(), "utf-8");
            if (isUseHeader) {
                for (String key : headerMap.keySet()) {
                    delete.setHeader(key, headerMap.get(key));
                }
            }
            return result;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    /**
     * 请求put方法
     * @param url
     * @param type
     * @param param
     * @return
     */
    public String put(String url, String type, String param) {
        try {
            createClient();
            HttpPut httpPut = new HttpPut(url);
            HttpEntity entity;
            switch (type) {
                case "json":
                    StringEntity jsonentity = new StringEntity(param,"utf-8");
                    jsonentity.setContentType("application/json");
                    entity = jsonentity;
                    break;
                case "file":
                    JSONObject paramJSON = JSON.parseObject(param);
                    MultipartEntityBuilder meb = MultipartEntityBuilder.create();
                    //解析文件参数，addBinaryBody
                    JSONObject fileParam = paramJSON.getJSONObject("文件");
                    for (String fileKey : fileParam.keySet()) {
                        meb.addBinaryBody(fileKey, new File(fileParam.get(fileKey).toString()));
                    }
                    //解析文本参数，addTextBody
                    JSONObject textParam = paramJSON.getJSONObject("文本");
                    for (String textKey : textParam.keySet()) {
                        meb.addTextBody(textKey, textParam.get(textKey).toString());
                    }
                    entity = meb.build();
                    break;
                case "url":
                default:
                    StringEntity sentity = new StringEntity(param,"utf-8");
                    sentity.setContentType("application/x-www-form-urlencoded");
                    entity = sentity;
                    break;
            }
            httpPut.setEntity(entity);

            if (isUseHeader) {
                //遍历存下来的所有头域
                for (String headerKey : headerMap.keySet()) {
                    httpPut.setHeader(headerKey, headerMap.get(headerKey));
                }
            }

            CloseableHttpResponse execute = client.execute(httpPut);
            String result = EntityUtils.toString(execute.getEntity(), "utf-8");
            return result;
        } catch (IOException e) {
            return e.getMessage();
        }
    }


}
