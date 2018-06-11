package com.xsx.ncd.ncd_manager.Tools;

import android.annotation.Nullable;
import android.os.FileUtils;

import com.android.internal.util.XmlUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.android.internal.inputmethod.InputMethodUtils.DEBUG;

public class SystemSetSaveBundle {

    private static final String SYSTEM_SET_SAVE_FILE_PATH = "/mnt/sdcard/whnewcando/setting.xml";

    private File file;
    private Map<String, Object> mMap;

    private SystemSetSaveBundle(){

    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final SystemSetSaveBundle INSTANCE = new SystemSetSaveBundle();
    }

    //获取单例
    public static SystemSetSaveBundle getInstance(){
        return SystemSetSaveBundle.SingletonHolder.INSTANCE;
    }

    public void systemSetSaveBundleInit() {
        file = new File(SYSTEM_SET_SAVE_FILE_PATH);
        loadFromDisk();
    }

    private void loadFromDisk()  {

        try{

            if(!file.exists())
                file.createNewFile();

            BufferedInputStream str = new BufferedInputStream(new FileInputStream(file), 16*1024);

            mMap = (Map<String, Object>) XmlUtils.readMapXml(str);

        }catch (Exception e){
            mMap = new HashMap<>();
        }
    }

    private void writeToDisk() throws Exception {
        if(!file.exists())
            file.createNewFile();

        if(mMap == null)
            throw new Exception("can not save null map data");

        FileOutputStream str = new FileOutputStream(file);

        XmlUtils.writeMapXml(mMap, str);

        str.flush();

        str.close();
    }


    public Map<String, ?> getAll() {
        return new HashMap<String, Object>(mMap);
    }

    public String getString(String key) {
        String v = (String)mMap.get(key);
        return v;
    }

    public Set<String> getStringSet(String key) {
        Set<String> v = (Set<String>) mMap.get(key);
        return v;
    }

    public Integer getInt(String key) {
        Integer v = (Integer)mMap.get(key);
        return v;
    }
    public Long getLong(String key) {
        Long v = (Long)mMap.get(key);
        return v;
    }
    public Float getFloat(String key) {
        Float v = (Float)mMap.get(key);
        return v;
    }
    public Boolean getBoolean(String key) {
        Boolean v = (Boolean)mMap.get(key);
        return v;
    }

    public boolean contains(String key) {
        return mMap.containsKey(key);
    }

    public SystemSetSaveBundle putString(String key, @Nullable String value) {
        mMap.put(key, value);
        return this;
    }
    public SystemSetSaveBundle putStringSet(String key, @Nullable Set<String> values) {
        mMap.put(key, (values == null) ? null : new HashSet<String>(values));
        return this;
    }
    public SystemSetSaveBundle putInt(String key, int value) {
        mMap.put(key, value);
        return this;
    }
    public SystemSetSaveBundle putLong(String key, long value) {
        mMap.put(key, value);
        return this;
    }
    public SystemSetSaveBundle putFloat(String key, float value) {
        mMap.put(key, value);
        return this;
    }
    public SystemSetSaveBundle putBoolean(String key, boolean value) {
        mMap.put(key, value);
        return this;
    }

    public SystemSetSaveBundle remove(String key) {
        mMap.remove(key);
        return this;
    }

    public SystemSetSaveBundle clear() {
        mMap.clear();
        return this;
    }

    public void commit() throws Exception {
        writeToDisk();
        loadFromDisk();
    }
}
