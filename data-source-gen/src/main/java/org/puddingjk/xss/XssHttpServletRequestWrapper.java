package org.puddingjk.xss;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.nio.charset.Charset;

/***
 * @Param XSS过滤处理
 * @description
 * @author LuoHongyu
 * @date 2020-09-03 19:36
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private byte[] requestBody;
    private Charset charSet;
    private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        //缓存请求body
        try {
            String requestBodyStr = getRequestPostStr(request);
            if (StringUtils.isNotBlank(requestBodyStr)) {
                requestBodyStr = filter(requestBodyStr);
                JSONObject resultJson = JSONObject.parseObject(requestBodyStr);
                requestBody = resultJson.toString().getBytes(charSet);
            } else {
                requestBody = new byte[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String filter(String value) {
        if (value != null) {
            value = Jsoup.clean(value, "", Whitelist.relaxed(), outputSettings).trim();
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                // 防xss攻击和过滤前后空格
                escapseValues[i] = Jsoup.clean(values[i], Whitelist.relaxed()).trim();
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }


    public String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        String charSetStr = request.getCharacterEncoding();
        if (charSetStr == null) {
            charSetStr = "UTF-8";
        }
        charSet = Charset.forName(charSetStr);

        return StreamUtils.copyToString(request.getInputStream(), charSet);
    }
}