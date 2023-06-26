package com.example.jsf.Bean.util;

import java.util.ArrayList;
import java.util.List;

public class GetAsList {
    public static List<String> getAsList(String string) {
    List<String> result = new ArrayList<>();
    for (String s : string.split(",")) {
        if (!s.trim().isEmpty()) {
            result.add(s.trim());
        }
    }
    return result;
    }
}
