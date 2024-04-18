package com.hd.hdaisdk.client;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.hd.hdaisdk.model.DevChatResponse;
import com.hd.hdaisdk.common.BaseResponse;
import com.hd.hdaisdk.model.DevChatRequest;


import java.util.HashMap;
import java.util.Map;

import static com.hd.hdaisdk.utils.SignUtils.genSign;

/**
 * 调用星火认知大模型（v3.5） 的客户端
 *
 */
public class HdAiClient {

    private static final String HOST = "wss://spark-api.xf-yun.com/v3.5";

    private final String appKey;

    private final String appSecret;

    public HdAiClient(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    /**
     * 对话
     *
     * @param devChatRequest
     * @return
     */
    public BaseResponse<DevChatResponse> doChat(DevChatRequest devChatRequest) {
        String url = HOST + "/chat";
        String json = JSONUtil.toJsonStr(devChatRequest);
        String result = HttpRequest.post(url)
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute()
                .body();
        TypeReference<BaseResponse<DevChatResponse>> typeRef = new TypeReference<BaseResponse<DevChatResponse>>() {
        };
        return JSONUtil.toBean(result, typeRef, false);
    }

    /**
     * 获取请求头
     *
     * @param body 请求参数
     * @return
     */
    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("appKey", appKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        String encodedBody = SecureUtil.md5(body);
        hashMap.put("body", encodedBody);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", genSign(encodedBody, appSecret));
        return hashMap;
    }

    public static void main(String[] args) {
        String appKey = "你的 appKey";
        String appSecret = "你的 appSecret";
        HdAiClient yuCongMingClient = new HdAiClient(appKey, appSecret);
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setAppId("bc612c0e");
        devChatRequest.setMessage("hd");
        BaseResponse<DevChatResponse> devChatResponseBaseResponse = yuCongMingClient.doChat(devChatRequest);
        System.out.println(devChatResponseBaseResponse);
        DevChatResponse data = devChatResponseBaseResponse.getData();
        if (data != null) {
            String content = data.getContent();
            System.out.println(content);
        }
    }
}
