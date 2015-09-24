package com.daxueoo.shopnc.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guodont on 15-8-30.
 */
public class ImageUtils {

    /**
     * 获取Content中的第一个Img url
     * @param content
     * @return
     */
    public static String getContentFirstImgUrl(String content){
        String firstImg = null;
        String pattern1 = "[\\[IMG\\]][a-zA-z]+://[^\\s]*[\\[IMG\\]]";
        String pattern2 = "[^\\[IMG\\]][a-zA-z]+://[^\\s\\[IMG\\]]*";

        Pattern p = Pattern.compile(pattern1);
        Matcher m = p.matcher(content);

        if(m.find()){
            Pattern p2 = Pattern.compile(pattern2);
            Log.e("pattern", m.group());
            Matcher m2 = p2.matcher(m.group());
            if (m2.find()){
                Log.e("pattern", m2.group());
                firstImg = m2.group();
            }else{
                firstImg = null;
            }

        } else {
            firstImg = null;
        }

        return firstImg;
    }

    /**
     * 替换内容中匹配的IMG标签为[图片]，以在话题描述中显示
     * @param content
     * @return
     */
    public static String HideImageTag(String content) {

        String pattern = "[\\[IMG\\]][a-zA-z]+://[^\\s]*[\\[IMG\\]]";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);

        Log.e("replace",m.replaceAll("[图片]"));
        return m.replaceAll("[图片]");
    }
}
