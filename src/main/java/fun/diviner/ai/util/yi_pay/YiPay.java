package fun.diviner.ai.util.yi_pay;

import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.HashMap;
import java.io.IOException;
import java.time.Instant;

import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.core.codec.Base64;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.OkHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;

public class YiPay {
    private static String getSignData(Map<String, String> data) {
        StringJoiner result = new StringJoiner("&");
        Map<String, String> sort = new TreeMap<>(data);
        for (Map.Entry<String, String> item: sort.entrySet()) {
            if (!item.getValue().isEmpty()) {
                result.add(item.getKey() + "=" + item.getValue());
            }
        }
        return result.toString();
    }

    public static YiPayResponse pay(int platformId, String merchantPrivateKey, String notifyUrl, String returnUrl, String tradeId, String name, double money, YiPayType type, String ipAddress) throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("pid", String.valueOf(platformId));
        data.put("method", "jump");
        data.put("type", type.getValue());
        data.put("out_trade_no", tradeId);
        data.put("notify_url", notifyUrl);
        data.put("return_url", returnUrl);
        data.put("name", name);
        data.put("money", String.valueOf(money));
        data.put("clientip", ipAddress);
        data.put("timestamp",  String.valueOf(Instant.now().getEpochSecond()));

        String signData = YiPay.getSignData(data);
        Sign signUtil = SecureUtil.sign(SignAlgorithm.SHA256withRSA, merchantPrivateKey, null);
        data.put("sign", Base64.encode(signUtil.sign(signData)));
        data.put("sign_type", "RSA");
        FormBody.Builder postData = new FormBody.Builder();
        data.forEach(postData::add);

        Request request = new Request.Builder().url("https://yi-pay.com/api/pay/create").post(postData.build()).build();
        try (Response response = new OkHttpClient().newCall(request).execute()) {
            return new ObjectMapper().readValue(response.body().string(), YiPayResponse.class);
        }
    }

    public static boolean verify(String platformPublicKey, Map<String, String> data) {
        Sign signUtil = SecureUtil.sign(SignAlgorithm.SHA256withRSA, null, platformPublicKey);
        String sign = data.remove("sign");
        data.remove("sign_type");
        return signUtil.verify(YiPay.getSignData(data).getBytes(), Base64.decode(sign));
    }
}