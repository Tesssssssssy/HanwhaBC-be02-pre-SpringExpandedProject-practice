package com.example.expandedproject.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ImageUtils {
    /*
        product, member 모두 이미지를 저장해야 한다.
        => AWS S3에 저장될 때 member, product 폴더로 구분되기 위해 메소드 분리.
    */
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
