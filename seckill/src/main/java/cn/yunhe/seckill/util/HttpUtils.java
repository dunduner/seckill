package cn.yunhe.seckill.util;

import cn.yunhe.seckill.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * http 请求url
 *
 * @author minghao.zhang
 */
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * post请求
     *
     * @param param
     * @param url
     * @return java.lang.String
     * @author dxl
     * @date 2020/6/12 11:29
     */
    public static String restPost(String param, String url) {
        try {
            HttpURLConnection httpConn = (HttpURLConnection) new URL(url).openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setReadTimeout(10000);
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            // 提交请求数据
            OutputStreamWriter out = new OutputStreamWriter(httpConn.getOutputStream(), StandardCharsets.UTF_8);
            out.write(param);
            out.flush();
            BufferedReader reader;
            String srcString;
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), StandardCharsets.UTF_8));
            while ((srcString = reader.readLine()) != null) {
                sb.append(srcString).append("\n");
            }
            reader.close();
            return sb.toString();
        } catch (Exception e) {
            logger.error("post请求url异常! url:[{}],param:[{}]", url, param);
            throw new BusinessException(10003, "Http 连接异常! e:" + e.getMessage());
        }
    }

    /**
     * post请求
     * @param request 为了获取头部的token
     * @param url
     * @param param
     * @return
     */
    public static String restPost(HttpServletRequest request, String url, String param) {
        try {
            HttpURLConnection httpConn = (HttpURLConnection) new URL(url).openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setReadTimeout(10000);
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            String token = request.getHeader("token");
            httpConn.setRequestProperty("token", token);
            logger.info("HttpUtils发送restPost请求中req的token={}",token);
            // 提交请求数据
            OutputStreamWriter out = new OutputStreamWriter(httpConn.getOutputStream(), StandardCharsets.UTF_8);
            out.write(param);
            out.flush();
            BufferedReader reader;
            String srcString;
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), StandardCharsets.UTF_8));
            while ((srcString = reader.readLine()) != null) {
                sb.append(srcString).append("\n");
            }
            reader.close();
            return sb.toString();
        } catch (Exception e) {
            logger.error("post请求url异常! url:[{}],param:[{}]", url, param);
            throw new BusinessException(10003, "Http 连接异常! e:" + e.getMessage());
        }
    }

    /**
     * get 请求
     * @param request
     * @param url
     * @param param
     * @return
     */
    public static String restGet(HttpServletRequest request, String url, String param) {
        try {
            HttpURLConnection httpConn = (HttpURLConnection) new URL(url).openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setReadTimeout(10000);
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConn.setRequestProperty("token", request.getHeader("token"));
            // 提交请求数据  get请求不能要
//            OutputStreamWriter out = new OutputStreamWriter(httpConn.getOutputStream(), StandardCharsets.UTF_8);
//            out.write(param);
//            out.flush();
            BufferedReader reader;
            String srcString;
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), StandardCharsets.UTF_8));
            while ((srcString = reader.readLine()) != null) {
                sb.append(srcString).append("\n");
            }
            reader.close();
            return sb.toString();
        } catch (Exception e) {
            logger.error("get请求url异常! url:[{}],param:[{}]", url, param);
            throw new BusinessException(10003, "Http 连接异常! e:" + e.getMessage());
        }
    }

}
