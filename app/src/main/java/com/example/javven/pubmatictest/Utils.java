package com.example.javven.pubmatictest;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {

  public static int dpToPx(Context context, int dp) {
    Resources resources = context.getResources();
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) dp, resources.getDisplayMetrics());
  }

  public static int pxToDp(Context context, int px) {
    Resources resources = context.getResources();
    return Math.round(px / resources.getDisplayMetrics().densityDpi / 160f);
  }
}
