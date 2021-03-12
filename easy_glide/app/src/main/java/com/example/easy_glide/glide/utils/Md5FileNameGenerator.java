package com.example.easy_glide.glide.utils;

import android.provider.SyncStateContract;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件名生成器
 */
public class Md5FileNameGenerator {

    private static final  String HASH_ALGORITHM = "MD5";
    private static final int RADIX = 10 + 26;

    public static String generate(String imageUri) {
        byte[] md5 = getMD5(imageUri.getBytes());
        BigInteger bi = new BigInteger(md5).abs();
        return bi.toString();
    }

    private static byte[] getMD5(byte[] data) {
        byte[] hash = null;
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(data);
            hash = digest.digest();
        } catch (NoSuchAlgorithmException e) {
            Log.e("Md5FileNameGenerator",e.getMessage());
        }
        return hash;
    }
}
