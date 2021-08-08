package com.example.utils;

import com.tencent.mmkv.MMKV;

public
/**
 * 作者：zzx on 2021/8/8 20:42
 *  作用： xxxx
 */
class MmkvUtil {

    private static final MMKV sMMKv = MMKV.defaultMMKV();

    public static void saveString(String key,String ret){
        sMMKv.encode(key,ret);
    }

    public static String getString(String key){
        return sMMKv.decodeString(key);
    }

    public static void saveOtherFile(String fileName){
        MMKV mmkv = MMKV.mmkvWithID(fileName);

    }
}

