package com.xiao.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;

/**
 * Log帮助类
 * 	主要功能：
 * 		* 在控制台print输出
 * 		* 在LogCat中打印Log
 * 		* 在将Log打印到指定路径文件中
 * 		* 提供一个总开关去控制Log是否打印
 * @author xiao
 *	注意：
 *		* 因为android.util.Log类不能打印""(空的字符出串)。当要打印""时都使用" "(一个空格键代替)
 *		* 因为System.out.print()是使用Log.i()来输出的，但对于较老的Android版本可能不支持，会造成数据丢失。所以最好直接使用android.util.Log替代。
 */
public class LogHelper {

	public final static String STARTUP_TAG = "startup_log";
	// log存放在本地的路径
	private static String path = "/sdcard/myframe/debug.txt";

	// 判断是不是第一次打印，如果不是第一次打印，就不用判断是否有文件存在等一些列的问题
	private static boolean isFirst = true;
	public static boolean isPrintLog = true;
	public static boolean isWriteToFile = true;

	// 设置log文件存储的位置
	// 本软件存储的主文件夹
	private final static String MYFRAME_FILEPATH = "myframe" + File.separator;

	// 存放本软件log的文件夹
	private final static String LOG_FILEPATH = MYFRAME_FILEPATH + "log" + File.separator;
	// log文件名
	private final static String LOG_FILENAME = "myframe";
	// log文件格式
	private final static String LOG_FILEEXT = ".txt";

	private static File mLogFile;

	// 设置log文件最大长度
	private final static long LOGFILE_LIMIT = 1000000L;

	private final static SimpleDateFormat DATEFORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static String mSdcardRootPath = "";

	/**
	 * 加载AspLog的同时，创建存放本软件信息的根目录
	 */
	static {
		// 获取sd卡根目录
		mSdcardRootPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator;
		File upaymentpath = new File(mSdcardRootPath + MYFRAME_FILEPATH);
		if (!upaymentpath.exists()) {
			boolean b=upaymentpath.mkdir();
			System.out.println();
		}
		File logpath = new File(mSdcardRootPath + LOG_FILEPATH);
		if (!logpath.exists()) {
			try {
				boolean b=logpath.mkdir();
				System.out.println();
			} catch (Exception e) {

			}
		}
	}

	public static void setIsPrintLog() {
		try {
			if (new File(path).exists())
				isPrintLog = true;
			isWriteToFile = true;
			isFirst = false;
		} catch (Exception ex) {
		}
	}

	public static void setIsPrintLog(Activity acti) {
		try {
			Intent intent = acti.getIntent();
			// 文件是否存在
			File file = new File(path);
			if (file.exists()) {
				isPrintLog = true;
				isWriteToFile = true;
			}
			// intent内是否有数据
			if (isPrintLog == false) {
				isPrintLog = intent.getBooleanExtra("com.aspire.mm.debug",
						false);

				if (isPrintLog == true) {
					isWriteToFile = true;
					file.createNewFile();
				}
			}

			isFirst = false;

		} catch (Exception ex) {

		}
	}

	/**
	 * 检查是否打印log
	 */
	private static void checkLog() {
		if (isFirst == true) {
			File file = new File(path);
			if (file.exists()) {
				isPrintLog = true;
				isWriteToFile = true;
			}
			isFirst = false;
		}
		createLogFile();
	}

//因为System.out.print()是使用Log.i()来输出的，但对于较老的Android版本可能不支持，会造成数据丢失。所以最好直接使用android.util.Log替代。
/*	public static void print(String msg) {
		checkLog();
		if (isPrintLog) {
			System.out.print(msg == null ? "" : msg);
		}
		writeLogFile("", "", msg);
	}*/

	private static void createLogFile() {
		if (isWriteToFile) {
			synchronized (LOG_FILENAME) {
				if (mLogFile == null) {
					try {
						if (!Environment.getExternalStorageState().equals(
								Environment.MEDIA_MOUNTED)) {
							return;
						}

						mLogFile = new File(mSdcardRootPath + LOG_FILEPATH
								+ LOG_FILENAME + LOG_FILEEXT);
						if (!mLogFile.exists()) {
							mLogFile.createNewFile();
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					if (mLogFile.isFile()) {
						if (mLogFile.length() > LOGFILE_LIMIT) {
							StringBuffer sb = new StringBuffer(mSdcardRootPath);
							sb.append(LOG_FILEPATH);
							sb.append(LOG_FILENAME);
							sb.append(DATEFORMAT.format(new Date()));
							sb.append(LOG_FILEEXT);
							mLogFile.renameTo(new File(sb.toString()));
							sb = null;
							sb = new StringBuffer(mSdcardRootPath);
							sb.append(LOG_FILEPATH);
							sb.append(LOG_FILENAME);
							sb.append(LOG_FILEEXT);
							mLogFile = new File(sb.toString());
							sb = null;
							if (!mLogFile.exists()) {
								// 写入文件创建时间
								LogHelper.d("TestFile", "Create the file:"
										+ LOG_FILENAME + LOG_FILEEXT);
								try {
									mLogFile.createNewFile();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}

	private static void writeLogFile(String level, String tag, String msg) {
		if (isWriteToFile) {
			synchronized (LOG_FILENAME) {
				if (mLogFile != null) {

					StringBuffer sb = new StringBuffer();
					sb.append(DATEFORMAT.format(new Date()));
					sb.append(": ");
					sb.append(level);
					sb.append(": ");
					sb.append(tag);
					sb.append(": ");
					sb.append(msg);
					sb.append("\n");
					RandomAccessFile raf = null;
					try {
						raf = new RandomAccessFile(mLogFile, "rw");
						raf.seek(mLogFile.length());
						raf.write(sb.toString().getBytes("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();

					} catch (IOException e) {
						e.printStackTrace();

					} finally {
						sb = null;
						if (raf != null) {
							try {
								raf.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

					}
				}
			}
		}
	}

//* 因为System.out.print()是使用Log.i()来输出的，但对于较老的Android版本可能不支持，会造成数据丢失。所以最好直接使用android.util.Log替代。
//	public static void println(String msg) {
//		checkLog();
//		if (isPrintLog) {
//			System.out.println(msg == null||msg=="" ? " " : msg);
//		}
//		writeLogFile("", "", msg);
//	}

	public static void i(String tag, String msg) {
		checkLog();
		if (isPrintLog) {
			android.util.Log.i(tag, msg == null||msg=="" ? " " : msg);
		}

		writeLogFile("INFO", tag, msg);
	}

	public static void i(String tag, String msg, Throwable tr) {
		checkLog();
		if (isPrintLog) {
			android.util.Log.i(tag, msg == null||msg=="" ? " " : msg, tr);
		}
		writeLogFile("INFO", tag, msg);
	}

	public static void d(String tag, String msg) {
		checkLog();
		if (isPrintLog) {
			android.util.Log.d(tag, msg == null||msg=="" ? " " : msg);
		}
		writeLogFile("DEBUG", tag, msg);
	}

	public static void d(String tag, String msg, Throwable tr) {
		checkLog();
		if (isPrintLog) {
			android.util.Log.d(tag, msg == null||msg=="" ? " " : msg, tr);
		}
		writeLogFile("DEBUG", tag, msg);
	}

	public static void e(String tag, String msg) {
		checkLog();
		if (isPrintLog) {
			android.util.Log.e(tag, msg == null||msg=="" ? " " : msg);
		}
		writeLogFile("ERROR", tag, msg);
	}

	public static void e(String tag, String msg, Throwable tr) {
		checkLog();
		if (isPrintLog) {
			android.util.Log.e(tag, msg == null||msg=="" ? " " : msg, tr);
		}
		writeLogFile("ERROR", tag, msg);
	}

	public static void v(String tag, String msg) {
		checkLog();
		if (isPrintLog) {
			android.util.Log.v(tag, msg == null||msg=="" ? " " : msg);
		}
		writeLogFile("VERBOSE", tag, msg);
	}

	public static void v(String tag, String msg, Throwable tr) {
		checkLog();
		if (isPrintLog) {
			android.util.Log.v(tag, msg == null||msg=="" ? " " : msg, tr);
		}
		writeLogFile("VERBOSE", tag, msg);
	}

	public static void w(String tag, String msg) {
		checkLog();
		if (isPrintLog) {
			android.util.Log.w(tag, msg == null||msg=="" ? " " : msg);
		}
		writeLogFile("WARN", tag, msg);
	}

	public static void w(String tag, String msg, Throwable tr) {
		checkLog();
		if (isPrintLog) {
			android.util.Log.w(tag, msg == null ||msg=="" ? " " : msg, tr);
		}
		writeLogFile("WARN", tag, msg);
	}

//	public static void save2sd(String filename, String data) {
//
//		File file = new File("/sdcard/topdata");
//		if (!file.isDirectory()) {
//			if (file.exists())
//				file.delete();
//			file.mkdir();
//		}
//		file = new File("/sdcard/topdata/" + filename);
//		int index = -1;
//		index = filename.lastIndexOf('/');
//		if (index > 0) {
//			filename = filename.substring(index + 1);
//		}
//		index = filename.lastIndexOf('.');
//		String basename, extname;
//		if (index > 0) {
//			basename = filename.substring(0, index);
//			extname = filename.substring(index);
//		} else {
//			basename = filename;
//			extname = "";
//		}
//		index = 0;
//		while (file.exists()) {
//			file = null;
//			file = new File("/sdcard/topdata/" + basename + (index++) + extname);
//		}
//		FileOutputStream fos = null;
//		try {
//			file.createNewFile();
//			fos = new FileOutputStream(file, true);
//			fos.write(data.getBytes());
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (fos != null) {
//				try {
//					fos.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				fos = null;
//			}
//		}
//	}
//
//	public static void h(String tag, String hintmsg, Header[] headers) {
//		checkLog();
//		if (isPrintLog && headers != null) {
//			// HeaderElement he[] = null;
//			int k = 0;
//			StringBuilder sb = new StringBuilder();
//			for (Header h : headers) {
//				sb.setLength(0);
//				sb.append(hintmsg + " H" + (k++) + " " + h.getName() + " : "
//						+ h.getValue());
//				// he = h.getElements();
//				// if (he != null){
//				// sb.append(" he-> ");
//				// i = 0;
//				// for (HeaderElement e:he){
//				// if (i > 0)
//				// sb.append(",");
//				// sb.append(e.getName()+"="+e.getValue());
//				// i ++ ;
//				// }
//				// }
//				AspLog.v(tag, sb.toString());
//			}
//		}
//	}
}
