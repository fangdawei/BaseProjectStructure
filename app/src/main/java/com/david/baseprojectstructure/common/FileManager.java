package com.david.baseprojectstructure.common;

import android.content.Context;
import android.os.Environment;
import com.david.baseprojectstructure.R;
import java.io.File;

/**
 * Created by david on 2017/3/21.
 */

public class FileManager {

  public static final String TAG = "FileManager";
  public static final long MAX_CACHE_SIZE = 1024 * 1024 * 64;//64M

  private static FileManager instance;
  private static Context context;
  private static String baseDirName;

  private File baseDir;
  private File sysCacheDir;

  public static void init(Context context){
    FileManager.context = context;
    FileManager.baseDirName = context.getResources().getString(R.string.app_name);
  }

  private FileManager() {
    boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
    if(sdCardExist) {
      File sdDir = Environment.getExternalStorageDirectory(); //获取根目录
      baseDir = new File(sdDir, baseDirName);
      baseDir.mkdirs();
    } else {
      File filesDir = context.getFilesDir();
      baseDir = new File(filesDir, baseDirName);
      baseDir.mkdirs();
    }
    sysCacheDir = context.getCacheDir();
  }

  public FileManager getInstance(){
    if(instance == null){
      instance = new FileManager();
    }
    return instance;
  }

  public File getBaseDir() {
    return baseDir;
  }

  public File getSysCacheDir() {
    return sysCacheDir;
  }
}
