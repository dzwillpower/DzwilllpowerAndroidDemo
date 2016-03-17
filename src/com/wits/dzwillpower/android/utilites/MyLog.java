package com.wits.dzwillpower.android.utilites;


import android.util.Log;

/**
 *
 * @author wh1107007
 * @time 2012-12-18 封装的日志类
 */
public class MyLog {
    private static final boolean LOGV = true;
    private static final boolean LOGD = true;
    private static final boolean LOGI = true;
    private static final boolean LOGW = true;
    private static final boolean LOGE = true;
    private static final boolean SHOWDETAILINFO = true;
    private static final boolean bLog = /*OuterRefereceUtils.isLogOpened();*/ true;

    public static int v(String tag, String msg) {
        if (LOGV && bLog) {
            if (SHOWDETAILINFO) {
                String name = getFunctionName();
                return Log.v(tag, name + "-" + msg);
            } else {
                return Log.v(tag, msg);
            }
        } else {
            return -1;
        }
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (LOGV && bLog) {
            if (SHOWDETAILINFO) {
                String name = getFunctionName();
                return Log.v(tag, name + "-" + msg, tr);
            } else {
                return Log.v(tag, msg, tr);
            }
        } else {
            return -1;
        }
    }

    public static int v(String tag, boolean debug, String msg) {
        if (debug && bLog) {
            if (SHOWDETAILINFO) {
                String name = getFunctionName();
                return v(tag, name + "-" + msg);
            } else {
                return v(tag, msg);
            }
        } else {
            return -1;
        }
    }

    public static int d(String tag, String msg) {
        if (LOGD && bLog) {
            if (SHOWDETAILINFO) {
                String name = getFunctionName();
                return Log.d(tag, name + "-" + msg);
            } else {
                return Log.d(tag, msg);
            }
        } else {
            return -1;
        }
    }

    public static int d(String tag, boolean debug, String msg) {
        if (debug && bLog) {
            if (SHOWDETAILINFO) {
                String name = getFunctionName();
                return d(tag, name + "-" + msg);
            } else {
                return d(tag, msg);
            }
        } else {
            return -1;
        }
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (LOGD && bLog) {
            if (SHOWDETAILINFO) {
                String name = getFunctionName();
                return Log.d(tag, name + "-" + msg, tr);
            } else {
                return Log.d(tag, msg, tr);
            }
        } else {
            return -1;
        }
    }

    public static int i(String tag, String msg) {
        if (LOGI && bLog) {
            if (SHOWDETAILINFO) {
                String name = getFunctionName();
                return Log.i(tag, name + "-" + msg);
            } else {
                return Log.i(tag, msg);
            }
        } else {
            return -1;
        }
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (LOGI && bLog) {
            if (SHOWDETAILINFO) {
                String name = getFunctionName();
                return Log.i(tag, name + "-" + msg, tr);
            } else {
                return Log.i(tag, msg, tr);
            }
        } else {
            return -1;
        }
    }

    public static int w(String tag, String msg) {
        if (LOGW && bLog) {
            if (SHOWDETAILINFO) {
                String name = getFunctionName();
                return Log.w(tag, name + "-" + msg);
            } else {
                return Log.w(tag, msg);
            }
        } else {
            return -1;
        }
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (LOGW && bLog) {
            if (SHOWDETAILINFO) {
                String name = getFunctionName();
                return Log.w(tag, name + "-" + msg, tr);
            } else {
                return Log.w(tag, msg, tr);
            }
        } else {
            return -1;
        }
    }

    public static int e(String tag, String msg) {
        if (LOGE && bLog) {
            if (SHOWDETAILINFO) {
                String name = getFunctionName();
                return Log.e(tag, name + "-" + msg);
            } else {
                return Log.e(tag, msg);
            }
        } else {
            return -1;
        }
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (LOGE && bLog) {
            if (SHOWDETAILINFO) {
                String name = getFunctionName();
                return Log.e(tag, name + "-" + msg, tr);
            } else {
                return Log.e(tag, msg, tr);
            }
        } else {
            return -1;
        }
    }

    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(MyLog.class.getName())) {
                continue;
            }
            return "[ " + Thread.currentThread().getName() + ": "
                    + st.getLineNumber() + " " + st.getMethodName() + " ]";
        }
        return null;
    }
}
