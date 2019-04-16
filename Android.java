package io.noties.android;

import android.os.Build;

@SuppressWarnings("unused")
public abstract class Android {

    public static final int VERSION = Build.VERSION.SDK_INT;

    public static final int JELLY_BEAN = Build.VERSION_CODES.JELLY_BEAN;
    public static final int JELLY_BEAN_MR1 = Build.VERSION_CODES.JELLY_BEAN_MR1;
    public static final int JELLY_BEAN_MR2 = Build.VERSION_CODES.JELLY_BEAN_MR2;
    public static final int KITKAT = Build.VERSION_CODES.KITKAT;
    public static final int KITKAT_WATCH = Build.VERSION_CODES.KITKAT_WATCH;
    public static final int LOLLIPOP = Build.VERSION_CODES.LOLLIPOP;
    public static final int LOLLIPOP_MR1 = Build.VERSION_CODES.LOLLIPOP_MR1;
    public static final int M = Build.VERSION_CODES.M;
    public static final int N = Build.VERSION_CODES.N;
    public static final int N_MR1 = Build.VERSION_CODES.N_MR1;
    public static final int O = Build.VERSION_CODES.O;
    public static final int P = Build.VERSION_CODES.P;

    private Android() {
    }
}
