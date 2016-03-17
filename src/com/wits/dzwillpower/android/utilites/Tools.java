package com.wits.dzwillpower.android.utilites;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 *
 * @author dzwillpower
 * @2013-5-20下午10:07:07
 */
public class Tools {
	/**
	 * 精确获取屏幕尺寸 如：3.5 4.0
	 *
	 * @param ctx
	 * @return
	 */
	public static double getScreenPhysicalSize(Activity ctx) {
		DisplayMetrics dm = new DisplayMetrics();
		ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
		double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2)
				+ Math.pow(dm.heightPixels, 2));
		return diagonalPixels / (160 * dm.density);
	}

	/**
	 * 判断是否是平板 官方做法
	 *
	 * @param context
	 * @return
	 */
	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	/**
	 * 启动apk的默认activity
	 *
	 * @param ctx
	 * @param packageName
	 */
	public static void startApkActivity(final Context ctx, String packageName) {
		PackageManager pm = ctx.getPackageManager();
		PackageInfo pi;
		try {
			pi = pm.getPackageInfo(packageName, 0);
			Intent intent = new Intent(Intent.ACTION_MAIN, null);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.setPackage(pi.packageName);

			List<ResolveInfo> apps = pm.queryIntentActivities(intent, 0);

			ResolveInfo ri = apps.iterator().next();
			if (ri != null) {
				String className = ri.activityInfo.name;
				intent.setComponent(new ComponentName(packageName, className));
				ctx.startActivity(intent);
			}
		} catch (NameNotFoundException e) {
			Log.e("startActivity", e.getMessage());
		}
	}

	/**
	 * 计算字宽 注意如果设置了textStyle，还需要进一步设置TextPaint。
	 *
	 * @param text
	 * @param Size
	 * @return
	 */
	public static float GetTextWidth(String text, float Size) {
		TextPaint FontPaint = new TextPaint();
		FontPaint.setTextSize(Size);
		return FontPaint.measureText(text);
	}

	/**
	 * 获取应用程序下所有的activity
	 *
	 * @param ctx
	 * @return
	 */
	public static ArrayList<String> getActivities(Context ctx) {
		ArrayList<String> result = new ArrayList<String>();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.setPackage(ctx.getPackageName());
		for (ResolveInfo info : ctx.getPackageManager().queryIntentActivities(
				intent, 0)) {
			result.add(info.activityInfo.name);
		}
		return result;
	}

	/**
	 * 检查字符串是否包含汉字
	 *
	 * @param sequence
	 * @return
	 */
	public static boolean checkChinese(String sequence) {
		final String format = "[\\u4E00-\\u9FA5\\uF900-\\uFA2D]";
		boolean result = false;
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(sequence);
		result = matcher.find();
		return result;
	}

	/**
	 * 检测字符串中只能包含：中文、数字、下划线(_)、横线(-)
	 *
	 * @param sequence
	 * @return
	 */
	public static boolean checkNickname(String sequence) {
		final String format = "[^\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w-_]";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(sequence);
		return !matcher.find();
	}

	/**
	 * 检查有没有应用程序接受处理你发出的intent
	 *
	 * @param context
	 * @param action
	 * @return
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	/**
	 * 使用TransitionDrawable实现渐变效果
	 *
	 * @param imageView
	 * @param bitmap
	 */
	private void setImageBitmap(Context mContext, ImageView imageView,
			Bitmap bitmap) {
		// Use TransitionDrawable to fade in.
		final TransitionDrawable td = new TransitionDrawable(new Drawable[] {
				new ColorDrawable(android.R.color.transparent),
				new BitmapDrawable(mContext.getResources(), bitmap) });
		// noinspection deprecation
		imageView.setBackgroundDrawable(imageView.getDrawable());
		imageView.setImageDrawable(td);
		td.startTransition(200);
	}

	/**
	 * dip 转px
	 *
	 * @param ctx
	 * @param dip
	 * @return
	 */
	public static int dipToPX(final Context ctx, float dip) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dip, ctx.getResources().getDisplayMetrics());
	}

	/**
	 * 多进程Preferences数据共享
	 *
	 * @param ctx
	 * @param key
	 * @param value
	 */
	public static void putStringProcess(Context ctx, String key, String value) {
		SharedPreferences sharedPreferences = ctx.getSharedPreferences(
				"preference_mu", Context.MODE_MULTI_PROCESS);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getStringProcess(Context ctx, String key,
			String defValue) {
		SharedPreferences sharedPreferences = ctx.getSharedPreferences(
				"preference_mu", Context.MODE_MULTI_PROCESS);
		return sharedPreferences.getString(key, defValue);
	}

	/**
	 * 将字节数组转化为16进制字符串
	 *
	 * @param bytes
	 * @return
	 */
	private static String bytesToHexString(byte[] bytes) {
		// http://stackoverflow.com/questions/332079
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	/**
	 * 16进制的字符串表示转成字节数组
	 *
	 * @param hexString
	 *            16进制格式的字符串
	 * @return 转换后的字节数组
	 **/
	public static byte[] toByteArray(String hexString) {
		if (TextUtils.isEmpty(hexString))
			throw new IllegalArgumentException(
					"this hexString must not be empty");

		hexString = hexString.toLowerCase();
		final byte[] byteArray = new byte[hexString.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
			byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}

	/**
	 * 获取WIFI的IP地址
	 *
	 * @param context
	 * @return
	 */
	public static String getWifiIpAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		String ip = LongToIp(ipAddress);
		return ip;
	}

	/**
	 * 将LONG长整型转成String
	 *
	 * @param longIp
	 * @return
	 */
	public static String LongToIp(long longIp) {
		// linux long是低位在前，高位在后
		StringBuffer sb = new StringBuffer("");
		sb.append(String.valueOf((longIp & 0xFF)));
		sb.append(".");
		sb.append(String.valueOf((longIp >> 8) & 0xFF));
		sb.append(".");
		sb.append(String.valueOf((longIp >> 16) & 0xFF));
		sb.append(".");
		sb.append(String.valueOf((longIp >> 24) & 0xFF));

		return sb.toString();
	}

	/**
	 * 返回当前的时间
	 *
	 * @return
	 */
	public static String returnCurrentTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String datenow = dateFormat.format(date);
		return datenow;
	}

	/**
	 * 判断是否有中文
	 *
	 * @param chineseStr
	 * @return
	 */
	public static final boolean isChineseCharacter(String chineseStr) {
		char[] charArray = chineseStr.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 读取指定的字节数
	 *
	 * @param is
	 * @param size
	 * @return
	 */
	public static String readIs2(InputStream is, int size) {
		byte[] b = new byte[size];
		int readed = 0;
		while (size > 0) {
			try {
				readed = is.read(b);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (readed != -1) {
				size = size - readed;
			}
		}
		String str = null;
		try {
			str = new String(b, "gb2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 返回文件大小，并取合适的单位
	 *
	 * @param f
	 * @return
	 */
	public static String getFormetFileSize(File f) {
		return FormetFileSize(getFileSize(f));
	}

	/**
	 * 返回文件大小
	 *
	 * @param f
	 * @return
	 */
	public static long getFileSize(File f) {
		long size = 0;
		if (f.exists()) {
			if (!f.isDirectory()) {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(f);
					size = fis.available();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return size;
	}

	/**
	 * 获得值 合适的单位
	 *
	 * @param filesize
	 * @return
	 */
	public static String FormetFileSize(long filesize) {
		DecimalFormat df = new DecimalFormat("#.00");
		String filesizeStr = "";
		if (filesize < 1024) {
			filesizeStr = df.format((double) filesize) + "B";
		} else if (filesize < 1048576) {
			filesizeStr = df.format((double) filesize / 1024) + "K";
		} else if (filesize < 1073741824) {
			filesizeStr = df.format((double) filesize / 1048576) + "M";
		} else {
			filesizeStr = df.format((double) filesize / 1073741824) + "G";
		}
		return filesizeStr;

	}

	/**
	 * 获取字符串长度
	 *
	 * @param value
	 * @return
	 */
	public static int getLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;

	}

	/**
	 * 复制文件
	 *
	 * @param fromFile
	 * @param isWrite
	 * @param context
	 * @param path
	 */
	public static void copyFile(File fromFile, boolean isWrite,
			Context context, String path) {
		if (!fromFile.exists()) {
			return;
		}
		if (!fromFile.isFile()) {
			return;
		}
		if (!fromFile.canRead()) {
			return;
		}
		File toFile = new File(path + "/" + fromFile.getName());
		if (!toFile.getParentFile().exists()) {
			toFile.getParentFile().mkdirs();
		}
		if (toFile.exists() && isWrite) {
			toFile = new File(path + "/" + fromFile.getName()
					+ System.currentTimeMillis());
		}

		try {
			FileInputStream fisFrom = new FileInputStream(fromFile);
			FileOutputStream fosTo = new FileOutputStream(toFile);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fisFrom.read(bt)) > 0) {
				fosTo.write(bt, 0, c);
			}
			fisFrom.close();
			fosTo.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	 /**
	  * 检查某个目录剩余的空间
	  * @return
	  */
	private long checkRemainStorage() {
		// Check available space only if we are writable
		String storageDirectory = Environment
				.getExternalStorageDirectory().toString();
		StatFs stat = new StatFs(storageDirectory);
		long remaining = (long) stat.getAvailableBlocks()
				* (long) stat.getBlockSize();
		return remaining;

	}
	/**
	 * 将输入流 转换为字符串
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}
	/**
	 * 发送不重复的通知
	 * @param context
	 * @param title
	 * @param message
	 * @param extras
	 */
//	public static void sendNotification(Context context, String title,
//            String message, Bundle extras) {
//        Intent mIntent = new Intent(context, FragmentTabsActivity.class);
//        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        mIntent.putExtras(extras);
//
//        int requestCode = (int) System.currentTimeMillis();
//
//        PendingIntent mContentIntent = PendingIntent.getActivity(context,
//                requestCode, mIntent, 0);
//
//        Notification mNotification = new NotificationCompat.Builder(context)
//                .setContentTitle(title).setSmallIcon(R.drawable.app_icon)
//                .setContentIntent(mContentIntent).setContentText(message)
//                .build();
//        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
//        mNotification.defaults = Notification.DEFAULT_ALL;
//
//        NotificationManager mNotificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//
//        mNotificationManager.notify(requestCode, mNotification);
//    }

	/** ip转16进制 */
    public static String ipToHex(String ips) {
        StringBuffer result = new StringBuffer();
        if (ips != null) {
            StringTokenizer st = new StringTokenizer(ips, ".");
            while (st.hasMoreTokens()) {
                String token = Integer.toHexString(Integer.parseInt(st.nextToken()));
                if (token.length() == 1)
                    token = "0" + token;
                result.append(token);
            }
        }
        return result.toString();
    }

    /** 16进制转ip */
    public static String texToIp(String ips) {
        try {
            StringBuffer result = new StringBuffer();
            if (ips != null && ips.length() == 8) {
                for (int i = 0; i < 8; i += 2) {
                    if (i != 0)
                        result.append('.');
                    result.append(Integer.parseInt(ips.substring(i, i + 2), 16));
                }
            }
            return result.toString();
        } catch (NumberFormatException ex) {
        }
        return "";
    }
    /**
     * 获取网络类型名称
     * @param context
     * @return
     */
    public static String getNetworkTypeName(Context context) {
        if (context != null) {
            ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectMgr != null) {
                NetworkInfo info = connectMgr.getActiveNetworkInfo();
                if (info != null) {
                    switch (info.getType()) {
                    case ConnectivityManager.TYPE_WIFI:
                        return "WIFI";
                    case ConnectivityManager.TYPE_MOBILE:
                        return getNetworkTypeName(info.getSubtype());
                    }
                }
            }
        }
        return getNetworkTypeName(TelephonyManager.NETWORK_TYPE_UNKNOWN);
    }

    public static String getNetworkTypeName(int type) {
        switch (type) {
        case TelephonyManager.NETWORK_TYPE_GPRS:
            return "GPRS";
        case TelephonyManager.NETWORK_TYPE_EDGE:
            return "EDGE";
        case TelephonyManager.NETWORK_TYPE_UMTS:
            return "UMTS";
        case TelephonyManager.NETWORK_TYPE_HSDPA:
            return "HSDPA";
        case TelephonyManager.NETWORK_TYPE_HSUPA:
            return "HSUPA";
        case TelephonyManager.NETWORK_TYPE_HSPA:
            return "HSPA";
        case TelephonyManager.NETWORK_TYPE_CDMA:
            return "CDMA";
        case TelephonyManager.NETWORK_TYPE_EVDO_0:
            return "CDMA - EvDo rev. 0";
        case TelephonyManager.NETWORK_TYPE_EVDO_A:
            return "CDMA - EvDo rev. A";
        case TelephonyManager.NETWORK_TYPE_EVDO_B:
            return "CDMA - EvDo rev. B";
        case TelephonyManager.NETWORK_TYPE_1xRTT:
            return "CDMA - 1xRTT";
        case TelephonyManager.NETWORK_TYPE_LTE:
            return "LTE";
        case TelephonyManager.NETWORK_TYPE_EHRPD:
            return "CDMA - eHRPD";
        case TelephonyManager.NETWORK_TYPE_IDEN:
            return "iDEN";
        case TelephonyManager.NETWORK_TYPE_HSPAP:
            return "HSPA+";
        default:
            return "UNKNOWN";
        }
    }

    /**
     * 解压一个压缩文档 到指定位置
     *
     * @param zipFileString 压缩包的名字
     * @param outPathString 指定的路径
     * @throws Exception
     */
    public static void UnZipFolder(String zipFileString, String outPathString) throws Exception {
        java.util.zip.ZipInputStream inZip = new java.util.zip.ZipInputStream(new java.io.FileInputStream(zipFileString));
        java.util.zip.ZipEntry zipEntry;
        String szName = "";

        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();

            if (zipEntry.isDirectory()) {

                // get the folder name of the widget
                szName = szName.substring(0, szName.length() - 1);
                java.io.File folder = new java.io.File(outPathString + java.io.File.separator + szName);
                folder.mkdirs();

            } else {

                java.io.File file = new java.io.File(outPathString + java.io.File.separator + szName);
                file.createNewFile();
                // get the output stream of the file
                java.io.FileOutputStream out = new java.io.FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // read (len) bytes into buffer
                while ((len = inZip.read(buffer)) != -1) {
                    // write (len) byte from buffer at the position 0
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }//end of while

        inZip.close();

    }//end of func

    /** 从assets 文件夹中读取文本数据 */
    public static String getTextFromAssets(final Context context, String fileName) {
        String result = "";
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            // 获取文件的字节数
            int lenght = in.available();
            // 创建byte数组
            byte[] buffer = new byte[lenght];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            result = EncodingUtils.getString(buffer, "UTF-8");
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /** 从assets 文件夹中读取图片 */
    public static Drawable loadImageFromAsserts(final Context ctx, String fileName) {
        try {
            InputStream is = ctx.getResources().getAssets().open(fileName);
            return Drawable.createFromStream(is, null);
        } catch (IOException e) {
            if (e != null) {
                e.printStackTrace();
            }
        } catch (OutOfMemoryError e) {
            if (e != null) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
