package com.zuzush.zuzush.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liujun on 2017/7/30 0030.
 */
public class CommonUtil {
    public static int getVersion(Context context){
        try {
            if (context != null) {
                PackageManager manager = context.getPackageManager();
                PackageInfo packages = manager.getPackageInfo(context.getPackageName(), 0);
                return packages.versionCode;
            }
        }catch (Exception e){}
        return 0;
    }
    public static void toastTip(Context context,String hint){
        if (context != null) Toast.makeText(context,hint,Toast.LENGTH_SHORT).show();
    }
    public static String getVersonName(Context context){
        PackageManager manager = context.getPackageManager();
        String name;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName() , 0);
            name = info.versionName;
            return name;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    public static int[] getScreenInfo(Activity activity){
        int[] info;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        info = new int[]{width ,height};
        return info;
    }
    public static String getRealFilePath( final Context context, final Uri uri ) {
        if(uri == null) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );/**返回-1表示不存在*/
                if (index !=-1) data = cursor.getString( index );
                cursor.close();
            }
        }
        return data;
    }
    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
    public static Map<String,String> setOccop(){
        Map<String,String> map = new HashMap<>();
        map.put("A","计算机/互联网/通信");
        map.put("B","生产/工艺/制造");
        map.put("C","医疗/护理/制药");
        map.put("D","金融/银行/投资/保险");
        map.put("E","商业/服务业/个体经营");
        map.put("F","文化/广告/传媒");
        map.put("G","娱乐/艺术/表演");
        map.put("H","律师/法务");
        map.put("I","教育/培训");
        map.put("J","公务员/行政/事业单位");
        map.put("K","模特");
        map.put("L","空姐");
        map.put("M","学生");
        map.put("N","其他");
        return map;
    }
    /**
     * 替换手机号为****
     * @param str
     * @return
     */
    public static String makePhoneNunToStar(String str){
        String result = "";
        if (str != null && str.length() >7) {
            result = str.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return  result;
    }
    /**
     * @param data 数字
     * @return 三位一个,并保留两位小数
     */
    public static String formatTosepara(double data) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(data);
    }
}
