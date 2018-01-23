package com.example.customrecycle.frame.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;


import com.example.customrecycle.activitys.HomeActivity;
import com.example.customrecycle.base.BaseActivity;
import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.base.DaoTools;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.entity.VideoEntity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by U
 * <p/>
 * on 2017/8/28
 * <p/>
 * QQ:1347414707
 * <p/>
 * For:
 */
public class FileUtils {

    //查询本地
    @Nullable
    public static List<VideoEntity> getSpecificTypeOfFile(Context context, String[] extension) {
        //从外存中获取
        Uri fileUri = MediaStore.Files.getContentUri("external");
        //筛选列，这里只筛选了：文件路径和不含后缀的文件名
        String[] projection = new String[]{
                MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE
        };
        //构造筛选语句
        String selection = "";
        for (int i = 0; i < extension.length; i++) {
            if (i != 0) {
                selection = selection + " OR ";
            }
            selection = selection + MediaStore.Files.FileColumns.DATA + " LIKE '%" + extension[i] + "'";
        }
        //按时间递增顺序对结果进行排序;待会从后往前移动游标就可实现时间递减
        String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED;
        //获取内容解析器对象
        ContentResolver resolver = context.getContentResolver();
        //获取游标
        Cursor cursor = resolver.query(fileUri, projection, selection, null, sortOrder);
        if (cursor == null)
            return null;
        //游标从最后开始往前递减，以此实现时间递减顺序（最近访问的文件，优先显示）
        List<VideoEntity> mList = new ArrayList<>();
        DaoTools.deleteAll();
        if (cursor.moveToLast()) {
            do {
                //输出文件的完整路径
                String data = cursor.getString(0);
                VideoEntity entity = new VideoEntity(data, getFileName(data), (long) 0);
                mList.add(entity);
                //存到本地数据库中
                DaoTools.insertLove(entity);
                Log.d("Mr.U", data);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        Log.d("Mr.U", "getSpecificTypeOfFile: " + mList.size());
        return mList;
    }

    //通过文件路径获取文件的名字
    public static String getFileName(String path) {
        return DemoUtils.getFilename(path);
    }

    /*
* Java文件操作 获取不带扩展名的文件名
* */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 获取本地视频的第一帧
     *
     * @param localPath
     * @return
     */
    public static Bitmap getLocalVideoBitmap(String localPath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据文件路径获取缩略图
            retriever.setDataSource(localPath);
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    //扫描移动存储设备
    //String[] args = {".mp4", ".wmv", ".rmvb", ".mkv", ".avi", ".flv", ".3gp", ".mov", ".mpg", ".webm", ".wob"};
    public static void getAllFiles(final File path) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                File files[] = path.listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (f.isDirectory()) {
                            getAllFiles(f);
                        } else if (f.length()/1048576>0){//文件大于1M
                            String s = f.toString().toLowerCase();
                            if (!s.contains("$RECYCLE.BIN".toLowerCase())){//屏蔽移动硬盘的回收站缓存文件
                                if (s.endsWith(".mp4")||s.endsWith(".wmv")||s.endsWith(".rmvb")||s.endsWith(".mkv")||s.endsWith(".avi")
                                        ||s.endsWith(".flv")||s.endsWith(".flv")||s.endsWith(".3gp")||s.endsWith(".mov")||s.endsWith(".mpg")
                                        ||s.endsWith(".webm")||s.endsWith(".wob")){
                                    DaoTools.insertLove(new VideoEntity(f.toString(),getFileName(f.toString()),(long)0));
                                    String fileName = getFileName(f.toString());
                                    HomeActivity.videoList.add(new VideoEntity(f.toString(), getFileNameNoEx(fileName),(long)0));
                                    Log.d("Mr.U", "getAllFiles: "+s);
                                    Log.d("Mr.U", "getAllFiles: length        "+f.length()/1048576+"Mb");
                                }
                            }

                        }
                    }
                }
//            }
//        }).start();

    }
}