package com.david.baseprojectstructure.util;

import android.os.StatFs;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by david on 2017/3/21.
 */

public class FileUtils {

  private final static String TAG = "FileUtil";
  private final static int BUFFER = 8192;

  /**
   * 获取文件夹可用空间大小
   */
  public static long getAvailableStorageSize(File dir) {
    long size = -1;
    if (dir != null && dir.exists() && dir.isDirectory()) {
      StatFs stat = new StatFs(dir.getPath());
      size = (long) stat.getBlockSize() * stat.getAvailableBlocks();
    }
    return size;
  }

  /**
   * 获取文件夹已使用空间大小
   */
  public static long getDirSize(File dir) {
    long size = 0;
    if (dir.exists() && dir.isDirectory()) {
      File[] files = dir.listFiles();
      for (File file : files) {
        if (file.isFile()) {
          size += file.length();
        } else {
          size += getDirSize(file);
        }
      }
    }
    return size;
  }

  /**
   * 获取文件夹已使用空间大小，单位为MB
   */
  public static double getDirSizeToM(File dir) {
    double size = getDirSize(dir) / 1024 / 1024;
    BigDecimal bigDecimal = new BigDecimal(size);
    bigDecimal = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP);
    return bigDecimal.doubleValue();
  }

  /**
   * 复制文件或目录
   */
  public static void copy(File sourceFile, File targetFile) throws IOException {
    if (!sourceFile.exists()) {
      LogUtils.i(TAG, "the source file is not exists: " + sourceFile.getAbsolutePath());
    } else {
      if (sourceFile.isFile()) {
        copyFile(sourceFile, targetFile);
      } else {
        copyDirectory(sourceFile, targetFile);
      }
    }
  }

  /**
   * 复制文件
   */
  public static void copyFile(File sourceFile, File targetFile) throws IOException {
    BufferedInputStream inBuff = null;
    BufferedOutputStream outBuff = null;
    try {
      inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
      outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
      byte[] buffer = new byte[BUFFER];
      int length;
      while ((length = inBuff.read(buffer)) != -1) {
        outBuff.write(buffer, 0, length);
      }
      outBuff.flush();
    } finally {
      if (inBuff != null) {
        inBuff.close();
      }
      if (outBuff != null) {
        outBuff.close();
      }
    }
  }

  /**
   * 复制文件夹
   */
  public static void copyDirectory(File sourceDir, File targetDir) throws IOException {
    //新建目标目录
    targetDir.mkdirs();
    //遍历源目录下所有文件或目录
    File[] file = sourceDir.listFiles();
    for (int i = 0; i < file.length; i++) {
      if (file[i].isFile()) {
        File sourceFile = file[i];
        File targetFile = new File(targetDir.getAbsolutePath() + File.separator + file[i].getName());
        copyFile(sourceFile, targetFile);
      } else if (file[i].isDirectory()) {
        File dir1 = new File(sourceDir, file[i].getName());
        File dir2 = new File(targetDir, file[i].getName());
        copyDirectory(dir1, dir2);
      }
    }
  }

  /**
   * 删除文件或目录
   */
  public static boolean delete(File file) {
    if (!file.exists()) {
      LogUtils.i(TAG, "the file is not exists: " + file.getAbsolutePath());
      return false;
    } else {
      if (file.isFile()) {
        return deleteFile(file);
      } else {
        return deleteDirectory(file, true);
      }
    }
  }

  /**
   * 删除文件
   */
  public static boolean deleteFile(File file) {
    if (file.isFile() && file.exists()) {
      file.delete();
      return true;
    } else {
      LogUtils.i(TAG, "the file is not exists: " + file.getAbsolutePath());
      return false;
    }
  }

  /**
   * 删除目录
   */
  public static boolean deleteDirectory(File dirFile, boolean includeSelf) {
    return deleteDirectory(dirFile, null, includeSelf, false);
  }

  /**
   * 删除目录
   */
  public static boolean deleteDirectory(File dirFile, String extension, boolean includeSelf, boolean onlyFile) {
    if (!dirFile.exists() || !dirFile.isDirectory()) {
      LogUtils.i(TAG, "the directory is not exists: " + dirFile.getAbsolutePath());
      return false;
    }
    boolean flag = true;
    File[] files = dirFile.listFiles();
    for (int i = 0; i < files.length; i++) {
      if (files[i].isFile()) {
        if (extension == null || files[i].getName().toLowerCase().endsWith("." + extension.toLowerCase())) {
          flag = deleteFile(files[i]);
          if (!flag) {
            break;
          }
        }
      } else {
        if (!onlyFile) {
          flag = deleteDirectory(files[i], true);
          if (!flag) {
            break;
          }
        }
      }
    }

    if (!flag) {
      LogUtils.i(TAG, "delete directory fail: " + dirFile.getAbsolutePath());
      return false;
    }

    if (includeSelf) {
      if (dirFile.delete()) {
        return true;
      } else {
        LogUtils.i(TAG, "delete directory fail: " + dirFile.getAbsolutePath());
        return false;
      }
    } else {
      return true;
    }
  }
}
