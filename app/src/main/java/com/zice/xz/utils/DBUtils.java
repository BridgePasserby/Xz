package com.zice.xz.utils;

import android.os.Environment;
import android.util.Log;

import com.zice.xz.App;
import com.zice.xz.database.DataBaseTable;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by Zkai
 * on 2016/11/27.
 */

public class DBUtils {
    private static final String TAG = "DBUtils";
    /**
     * 获取数据库的默认路径，data/data/报名/database/xxx.db
     *
     * @param table 数据库名
     * @return
     */
    public static String getDefaultDBPath(String table) {
        return App.getAppContext().getDatabasePath(DataBaseTable.DB_NAME).getPath();
    }

    /**
     * 获取安装包的路径
     *
     * @return
     */
    public static String getPackageNamePath() {
        return App.getAppContext().getApplicationContext().getPackageResourcePath();

    }

    /**
     * 获取app的绝对路径
     *
     * @return
     */
    public static String getAppPath() {
        return App.getAppContext().getFilesDir().getAbsolutePath();
    }

    /**
     * 获取sd卡路径
     *
     * @return
     */
    public static String getSDPath() {
        if (!hasSDCard()) {
            return null;
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 判断是否有sd卡
     *
     * @return
     */
    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static void syncDataBase() {
        if (!DBUtils.hasSDCard()) {
            return;
        }
        String sdDataBasePath = DBUtils.getSDPath() + File.separator + "wz" + File.separator + DataBaseTable.DB_NAME;
        Log.i(TAG, "syncDataBase() sdDataBasePath -> " + sdDataBasePath);
        String dataBasePath = DBUtils.getDefaultDBPath(DataBaseTable.DB_NAME);
        Log.i(TAG, "syncDataBase() dataBasePath -> " + dataBasePath);
        File file = getFile(dataBasePath);
        File sdFile = getFile(sdDataBasePath);
        if (file != null && sdFile != null) {
            int fileLength = 0;
            int sdFileLength = 0;
            try {
                FileInputStream inputStream1 = new FileInputStream(file);
                FileInputStream inputStream2 = new FileInputStream(sdFile);
                fileLength = inputStream1.available();
                sdFileLength= inputStream2.available();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fileLength == sdFileLength) {
                if (getFileMD5(file).equals(getFileMD5(sdFile))) {
                    Log.i(TAG, "syncDataBase: 两个文件相等");
                    return;
                }
            }
            if (fileLength > sdFileLength) {// file是最新的
                if (sdFile.delete()) {
                    Log.i(TAG, "syncDataBase app database is last : copy to " + sdDataBasePath);
                    copyFile(dataBasePath, sdDataBasePath);
                }
            } else {
                if (file.delete()) {
                    Log.i(TAG, "syncDataBase sd card database is last  : copy to " + dataBasePath);
                    copyFile(sdDataBasePath, dataBasePath);
                }
            }
        } else if (file != null && sdFile == null) {
            Log.i(TAG, "syncDataBase sd card database is null : copy to " + sdDataBasePath);
            copyFile(dataBasePath, sdDataBasePath);
        } else if (sdFile != null && file == null) {
            Log.i(TAG, "syncDataBase app database is null : copy to " + dataBasePath);
            copyFile(sdDataBasePath, dataBasePath);
        } else if (sdFile == null && file == null) {
            Log.i(TAG, "syncDataBase: copy to 新建数据库" );
            // TODO: 2016/11/27 新建数据库 
        }
    }

    private static File getFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file;
        } else {
            return null;
        }
    }

    /**
     * @param path       原文件路径 如：c:/wz.db
     * @param targetPath 目标路径 如：f:/wz.db
     * @return
     */
    private static boolean copyFile(String path, String targetPath) {
        Log.i(TAG,"System.currentTimeMillis() ------> " + System.currentTimeMillis());
        File file = new File(path);
        File fileTarget = new File(targetPath);
        try {
            if (!fileTarget.exists()) {
                File parentFile = fileTarget.getParentFile();
                if (!parentFile.exists() && parentFile.mkdirs() && fileTarget.createNewFile()) {
                    Log.d(TAG, "create targetPath's parent not exist,create directory success");
                } else if (parentFile.exists() && fileTarget.createNewFile()) {
                    Log.d(TAG, "create targetPath file success");
                } else {
                    Log.d(TAG, "create targetPath file failed");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.exists() && file.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(targetPath);
                byte[] buffer = new byte[512];
                while (fis.read(buffer) != -1) {
                    fos.write(buffer, 0, buffer.length);
                }
                closeStream(fis, fos);
                Log.i(TAG, "copyFile success, targetPath -> " + targetPath);
                Log.i(TAG, "System.currentTimeMillis() ------> " + System.currentTimeMillis());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "copyFile: error -> " + e.getMessage());
            }
        }
        Log.i(TAG, "copyFile filed, path -> " + targetPath);
        Log.i(TAG, "System.currentTimeMillis() ------> " + System.currentTimeMillis());
        return false;
    }

    private static void closeStream(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // 计算文件的 MD5 值
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream fin = null;
        byte buffer[] = new byte[2048];
        int len;
        try {
            digest =MessageDigest.getInstance("MD5");
            fin = new FileInputStream(file);
            while ((len = fin.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeStream(fin);
        }

    }
}
