package com.zerovoid.lib.util;

import android.content.Context;

/**
 * Created by Administrator on 2015/12/3.
 * 资源Id工具类
 */
public class ResourcesUtil {
    private static ResourcesUtil resourcesUtil;
    private Context context;
    public ResourcesUtil(Context context){
        this.context = context.getApplicationContext();
    }
    public static ResourcesUtil getInstance(Context context){
       if(resourcesUtil==null){
           resourcesUtil = new ResourcesUtil(context);
       }
       return resourcesUtil;
    }
    /**
     * 根据mipmap文件的名字取得id
     */
     public int getMipmapId(String name){
         return context.getResources().getIdentifier(name,"mipmap",context.getPackageName());
     }
    /**
     * 根据layout文件的名字取得id
     */
    public int getLayoutId(String name){
        return context.getResources().getIdentifier(name,"layout",context.getPackageName());
    }
    /**
     * 根据string的名字取得id
     */
    public int getStringId(String name){
        return context.getResources().getIdentifier(name,"string",context.getPackageName());
    }
    /**
     * 根据drawable文件的名字取得id
     */
    public int getDrawableId(String name){
        return context.getResources().getIdentifier(name,"drawable",context.getPackageName());
    }
    /**
     * 根据style的名字取得id
     */
    public int getStyleId(String name){
        return context.getResources().getIdentifier(name,"style",context.getPackageName());
    }
    /**
     * 根据id的名字取得id
     */
    public int getId(String name){
        return context.getResources().getIdentifier(name,"id",context.getPackageName());
    }
    /**
     * 根据color文件的名字取得id
     */
    public int getColorId(String name){
        return context.getResources().getIdentifier(name,"color",context.getPackageName());
    }
    /**
     * 根据array的名字取得id
     */
    public int getArrayId(String name){
        return context.getResources().getIdentifier(name,"array",context.getPackageName());
    }
}
