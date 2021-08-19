package com.example.parsefactory;

import android.util.Log;

import com.example.base.BaseRet;
import com.example.beans.resultbeans.MoYuHotBean;
import com.example.beans.resultbeans.MoYuHotData;
import com.google.gson.internal.$Gson$Preconditions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoYuHotParse {

    private static final String TAG = "MoYuHotParse";

    public static MoYuHotBean parseMoYuData(String source) throws JSONException {
        List<MoYuHotData> moYuHotDataList = new ArrayList<>();
        try {
            if (source.isEmpty()) return null;
            JSONObject jsonObject = new JSONObject(source);
            boolean success = jsonObject.optBoolean("success", false);
            int code = jsonObject.optInt("code", 0);
            String message = jsonObject.optString("message", "");
            //解析list部分
            JSONArray dataArray = jsonObject.optJSONArray("data");
            if (dataArray.length() > 0 && dataArray != null) {
                for (int i = 0; i < dataArray.length(); i++) {
                    List<String> imgList = new ArrayList<>();
                    List<String> thumbUpList = new ArrayList<>();
                    JSONObject dataObj = dataArray.optJSONObject(i);
                    String id = dataObj.optString("id", "");
                    String userId = dataObj.optString("userId", "");
                    String nickname = dataObj.optString("nickname", "");
                    String avatar = dataObj.optString("avatar", "");
                    String company = dataObj.optString("company", "");
                    String position = dataObj.optString("position", "");
                    String content = dataObj.optString("content", "");
                    String linkCover = dataObj.optString("linkCover", "");
                    String linkTitle = dataObj.optString("linkTitle", "");
                    String linkUrl = dataObj.optString("linkUrl", "");
                    int commentCount = dataObj.optInt("commentCount", 0);
                    int thumbUpCount = dataObj.optInt("thumbUpCount", 0);
                    JSONArray imgArray = dataObj.optJSONArray("images");
                    if (imgArray.length() > 0 && imgArray != null) {
                        for (int imgIndex = 0; imgIndex < imgArray.length(); imgIndex++) {
                            String imgUrl = (String) imgArray.get(imgIndex);
                            imgList.add(imgUrl);
                        }
                    }
                    String topicName = dataObj.optString("topicName", "");
                    String topicId = dataObj.optString("topicId", "");
                    String createTime = dataObj.optString("createTime", "");
                    boolean hasThumbUp = dataObj.optBoolean("hasThumbUp", false);
                    JSONArray thumbUpArray = dataObj.optJSONArray("thumbUpList");
                    if (thumbUpArray.length() > 0 && thumbUpArray != null) {
                        for (int thumbIndex = 0; thumbIndex < thumbUpArray.length(); thumbIndex++) {
                            String thumbStr = (String) thumbUpArray.get(thumbIndex);
                            thumbUpList.add(thumbStr);
                        }
                    }
                    boolean vip = dataObj.optBoolean("vip", false);
                    moYuHotDataList.add(i, new MoYuHotData(avatar, commentCount, company, content
                            , createTime, hasThumbUp, id, imgList, linkCover, linkTitle
                            , linkUrl, nickname, position, thumbUpCount, thumbUpList, topicId
                            , topicName, userId, vip));
                }
            }
            Log.d(TAG, "moYuHotDataList size --> " + moYuHotDataList.size());
            return new MoYuHotBean(code, moYuHotDataList, message, success);
        } catch (Exception e) {
            Log.d(TAG, "hot mo yu 解析失败");
            e.printStackTrace();
        }
        return null;
    }

}
