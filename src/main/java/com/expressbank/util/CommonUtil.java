package com.expressbank.util;


import java.util.UUID;

import static java.util.Objects.isNull;

public class CommonUtil {

    public static String mobile(String mobile) {
        if (isNull(mobile)) return null;
        if (mobile.startsWith("+")) return mobile;
        else if (mobile.startsWith("994")) return "+" + mobile;
        else return "+994" + mobile;
    }


    public static String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public static String checkNull(String value1, String value2, String value3) {
        StringBuilder result = new StringBuilder();
        if (!isNull(value1)) result.append(value1);
        if (!isNull(value1) && !isNull(value2)) result.append(", ");
        if (!isNull(value2)) result.append(value2);
        if ((!isNull(value1) && !isNull(value3)) || (!isNull(value2) && !isNull(value3))) result.append(", ");
        if (!isNull(value3)) result.append(value3);
        return result.toString();
    }
}
