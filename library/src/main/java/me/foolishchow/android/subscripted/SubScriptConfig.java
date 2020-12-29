package me.foolishchow.android.subscripted;

import android.graphics.Typeface;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description:
 * Author: foolishchow
 * Date: 29/12/2020 9:14 AM
 */
public class SubScriptConfig {
    @Nullable
    private Typeface fontFamily = null;
    private float textSize = 0;
    @NonNull
    private String text;
    @ColorInt
    private int textColor = -1;
    @SubScriptedVerticalAlign.VerticalAlign
    private int align = SubScriptedVerticalAlign.VERTICAL_ALIGN_BOTTOM;

    public SubScriptConfig(
            String text,
            @Nullable Typeface fontFamily,
            float textSize, int textColor,
            @SubScriptedVerticalAlign.VerticalAlign int align) {
        this.fontFamily = fontFamily;
        this.textSize = textSize;
        this.text = text;
        this.textColor = textColor;
        this.align = align;
    }

    public SubScriptConfig(String text, float textSize) {
        this.text = text;
    }


    @Nullable
    public Typeface getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(@Nullable Typeface fontFamily) {
        this.fontFamily = fontFamily;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
    }
}
