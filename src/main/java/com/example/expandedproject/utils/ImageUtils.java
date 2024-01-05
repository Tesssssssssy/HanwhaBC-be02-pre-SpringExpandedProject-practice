package com.example.expandedproject.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ImageUtils {
    public static String makeProductImagePath(String originalName) {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str;
        String type = "product";
        String uuid = UUID.randomUUID().toString();

        return type + "/" + folderPath + "/" + uuid + "_" + originalName;
    }

    public static String makeMemberImagePath(String originalName) {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str;
        String type = "member";
        String uuid = UUID.randomUUID().toString();

        return type + "/" + folderPath + "/" + uuid + "_" + originalName;
    }
}
