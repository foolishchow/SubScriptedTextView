package me.foolishchow.android.subscripted;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description:
 * Author: foolishchow
 * Date: 24/12/2020 4:35 PM
 */
public class SubScriptedVerticalAlign {
    public static final int VERTICAL_ALIGN_TOP = 2;
    public static final int VERTICAL_ALIGN_CENTER = 1;
    public static final int VERTICAL_ALIGN_BOTTOM = 0;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({VERTICAL_ALIGN_BOTTOM, VERTICAL_ALIGN_CENTER, VERTICAL_ALIGN_TOP})
    public @interface VerticalAlign {
    }
}
