# 八、Sling Servlet的使用

这一章介绍如何使用SlingServlet开发AEM的HTTP接口，从AEM后端实现翻译功能。

## 编写Servlet

使用@Component注解声明是一个Servlet服务，sling.servlet.methods参数声明HTTP请求方法，sling.servlet.paths参数声明HTTP请求路径

```java
package com.adobe.aem.guides.wknd.core.servlet;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.osgi.framework.Constants.SERVICE_DESCRIPTION;

@Slf4j
@Component(service = Servlet.class, property = {
        SERVICE_DESCRIPTION + "= Servlet to translate",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/translate"
})
public class TranslateServlet extends SlingAllMethodsServlet {

    private static final String YOUDAO_URL = "https://openapi.youdao.com/api";

    private static final String APP_KEY = "应用ID";

    private static final String APP_SECRET = "应用密钥";

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws IOException {
        String q = request.getRequestParameter("content").toString();
        Map<String, String> params = new HashMap<>();
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("from", "en");
        params.put("to", "zh-CHS");
        params.put("signType", "v3");
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", curtime);
        String signStr = APP_KEY + truncate(q) + salt + curtime + APP_SECRET;
        String sign = getDigest(signStr);
        params.put("appKey", APP_KEY);
        params.put("q", q);
        params.put("salt", salt);
        params.put("sign", sign);
        params.put("vocabId", "");
        /* 处理结果 */
        String result = requestForHttp(YOUDAO_URL, params);
        // 设置返回数据类型为json，编码格式为UTF-8
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(result);
        writer.close();
    }

    public static String requestForHttp(String url, Map<String, String> params) throws IOException {

        /* 创建HttpClient */
        CloseableHttpClient httpClient = HttpClients.createDefault();

        /* httpPost */
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> paramsList = new ArrayList<>();
        for (Map.Entry<String, String> en : params.entrySet()) {
            String key = en.getKey();
            String value = en.getValue();
            paramsList.add(new BasicNameValuePair(key, value));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try {
            Header[] contentType = httpResponse.getHeaders("Content-Type");
            log.info("Content-Type:" + contentType[0].getValue());
            if ("audio/mp3".equals(contentType[0].getValue())) {
                //如果响应是wav
                HttpEntity httpEntity = httpResponse.getEntity();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(baos);
                byte[] result = baos.toByteArray();
                EntityUtils.consume(httpEntity);
                //合成成功
                String file = "合成的音频存储路径" + System.currentTimeMillis() + ".mp3";
                byte2File(result, file);
            } else {
                /* 响应不是音频流，直接显示结果 */
                HttpEntity httpEntity = httpResponse.getEntity();
                String json = EntityUtils.toString(httpEntity, "UTF-8");
                EntityUtils.consume(httpEntity);
                log.info(json);
                return json;
            }
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                log.info("## release resouce error ##" + e);
            }
        }
        return "";
    }

    /**
     * 生成加密字段
     */
    public static String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * @param result 音频字节流
     * @param file   存储路径
     */
    private static void byte2File(byte[] result, String file) {
        File audioFile = new File(file);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(audioFile);
            fos.write(result);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }
}
```

使用postman测试，需要添加认证信息，在postman中接口访问正常，但在AEM页面中会出现下面的问题

![image-20230227192521071](./assets/image-20230227192521071.png)

### 出现的问题及解决办法

#### 接口403问题

![image-20230227190540323](./assets/image-20230227190540323.png)

打开[Adobe Experience Manager Web Console - Configuration](http://localhost:4502/system/console/configMgr)配置页，搜索CSRF，打开配置添加Servlet地址/bin/translate，保存即可

![image-20230227190829627](./assets/image-20230227190829627.png)

#### 中文乱码问题

![image-20230227190353903](./assets/image-20230227190353903.png)

需要在Servlet中设置返回数据的编码格式及数据类型

```java
// 设置返回数据类型为json，编码格式为UTF-8
response.setContentType("application/json;charset=utf-8");
response.getWriter().print(result);
```

## 页面及JS修改

### 页面修改

```html
<sly data-sly-use.model="com.adobe.aem.guides.wknd.core.models.Translate">
    <h1>类名: ${model.className}</h1>
    <div class="cmp-translate" appId="${model.appId}" appKey="${model.appKey}">
        <input id="trans-content" type="text" placeholder="请输入需要翻译的英文内容" >
        <button onclick="transByServlet()">翻译</button><br>
        <span id="result"></span>
    </div>
</sly>
```

### js修改

```javascript
transByServlet = function() {
    var query = $('#trans-content').val();
    $.ajax({
        url: '/bin/translate?content=' + query,
        type: 'get',
        success: function (data) {
            var translation = data.translation
            $('#result').html('翻译结果：' + translation);
        }
    });
}
```

## 完整效果

![image-20230227192836857](./assets/image-20230227192836857.png)