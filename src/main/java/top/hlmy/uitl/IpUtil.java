package top.hlmy.uitl;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;


public class IpUtil {

    public static final String COMMA = ",";
    public static final String UNKNOWN = "unknown";
    private static final String REMOTE_IP_HEADER_FLAG = "x-forwarded-for";

    public static String getRemoteIpByRequest(HttpServletRequest request) {
        String requestIp = request.getHeader(REMOTE_IP_HEADER_FLAG);

        if (StringUtils.isBlank(requestIp) || UNKNOWN.equalsIgnoreCase(requestIp)) {
            return request.getRemoteAddr();
        }

        int lastIndex = requestIp.lastIndexOf(COMMA);
        if (lastIndex < 0) {
            return requestIp;
        }
        return requestIp.substring(lastIndex + 1);
    }

    public static Object getIp(String ip) {
        //接口连接地址
        String urlStr = "http://whois.pconline.com.cn/ipJson.jsp?ip=" + ip + "&json=true";
        //调用getResult获取接口返回值
        String result = getResult(urlStr, "gbk");
        //解析json获取地址
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    private static String getResult(String urlStr, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(20000); // 设置连接超时时间
            connection.setReadTimeout(20000); // 设置读取数据超时时间
            connection.setDoOutput(true); // 是否打开输出流 true|false
            connection.setDoInput(true); // 是否打开输入流true|false
            connection.setRequestMethod("GET"); // 提交方法POST|GET
            connection.setUseCaches(false); // 是否缓存true|false
            connection.connect(); // 打开连接端口
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "gbk"));// 往对端写完数据对端服务器返回数据// ,以BufferedReader流来读取
            String text = "";
            String line = "";
            while ((line = reader.readLine()) != null) {
                text += line + "\n";
            }
            reader.close();
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
}
