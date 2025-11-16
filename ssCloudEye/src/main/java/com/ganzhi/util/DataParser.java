package com.ganzhi.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataParser {
    /**
     * 将数据字符串解析为Map
     * @param geoData 格式如："经度:100,高程:130,图片:a/b/ss.png"
     * @return 有序Map集合（保持原始顺序）
     */
    public static Map<String, String> parseToMap(String geoData) {
        Map<String, String> result = new LinkedHashMap<>();
        if (geoData == null || geoData.trim().isEmpty()) {
            return result;
        }

        String[] pairs = geoData.split(","); // 分割逗号
        for (String pair : pairs) {
            int idx = pair.indexOf(":");
            if (idx == -1) continue; // 跳过无冒号项
            String key = pair.substring(0, idx).trim();
            String value = pair.substring(idx + 1).trim();
            result.put(key, value);
        }

        return result;
    }

    /**
     * 将Map转换为数据字符串
     * @param map 包含数据的Map
     * @return 格式如："经度:100,高程:130,图片:a/b/ss.png"
     */
    public static String formatToString(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(entry.getKey()).append(":").append(entry.getValue());
        }

        return sb.toString();
    }
}
