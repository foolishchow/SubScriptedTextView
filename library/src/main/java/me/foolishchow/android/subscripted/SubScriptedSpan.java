package me.foolishchow.android.subscripted;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.style.ReplacementSpan;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static me.foolishchow.android.subscripted.SubScriptedVerticalAlign.VERTICAL_ALIGN_BOTTOM;
import static me.foolishchow.android.subscripted.SubScriptedVerticalAlign.VERTICAL_ALIGN_CENTER;
import static me.foolishchow.android.subscripted.SubScriptedVerticalAlign.VERTICAL_ALIGN_TOP;

/**
 * Description:
 * Author: foolishchow
 * Date: 24/12/2020 3:44 PM
 */
public class SubScriptedSpan extends ReplacementSpan {
    //字体大小px
    private float mFontSize;

    @SubScriptedVerticalAlign.VerticalAlign
    private int mAlign;
    @Nullable
    private Typeface mTypeface;
    @ColorInt
    private int mColor;

    private Paint mLinePaint = new Paint();

    public SubScriptedSpan(
            @Nullable Typeface typeface,
            @SubScriptedVerticalAlign.VerticalAlign int align,
            @ColorInt int color,
            float fontSize
    ) {
        mTypeface = typeface;
        mAlign = align;
        mFontSize = fontSize;
        mColor = color;

    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end,
                       Paint.FontMetricsInt fm) {
        text = text.subSequence(start, end);
        Paint p = getCustomTextPaint(paint);
        return (int) p.measureText(text.toString());
    }

    /**
     * Draws the span into the canvas.
     *
     * @param canvas Canvas into which the span should be rendered.
     * @param text   Current text.
     * @param start  Start character index for span.
     * @param end    End character index for span.
     * @param x      Edge of the replacement closest to the leading margin.
     * @param top    Top of the line.
     * @param y      Baseline.
     * @param bottom Bottom of the line.
     * @param paint  Paint instance.
     */
    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text,
                     int start, int end, float x, int top, int y, int bottom,
                     @NonNull Paint paint) {
        text = text.subSequence(start, end);

        //Paint.FontMetricsInt ofm = paint.getFontMetricsInt();
        //drawLine(canvas, x,y, 100, Color.YELLOW, 0);
        //drawLine(canvas, x,y, 50, Color.BLUE,  ofm.descent);
        //drawLine(canvas, x,y, 50, Color.GREEN,  ofm.ascent);
        //drawLine(canvas, x,y, 50, Color.MAGENTA, ofm.descent + ofm.ascent);
        Paint p = getCustomTextPaint(paint);
        //Paint.FontMetricsInt fm = p.getFontMetricsInt();
        //drawLine(canvas, x,y, 20, Color.YELLOW, 0);
        //drawLine(canvas, x,y, 30, Color.BLUE,  fm.descent);
        //drawLine(canvas, x,y, 30, Color.GREEN,  fm.ascent);
        //drawLine(canvas, x,y, 30, Color.MAGENTA, fm.descent + fm.ascent);
        canvas.drawText(text.toString(), x, y + getTranslateY(paint, p), p);

        //此处重新计算y坐标，使字体居中
    }

    private int getTranslateY(
            @NonNull Paint originPaint,
            @NonNull Paint paint
    ) {
        if (VERTICAL_ALIGN_BOTTOM == mAlign) {
            return 0;
        }
        Paint.FontMetricsInt ofm = originPaint.getFontMetricsInt();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        if (mAlign == VERTICAL_ALIGN_TOP) {
            return (ofm.ascent + ofm.descent) - (fm.ascent + fm.descent);
        }
        if (mAlign == VERTICAL_ALIGN_CENTER) {
            return (ofm.ascent + ofm.descent)/2 - (fm.ascent + fm.descent) / 2;
        }
        //switch (mAlign) {
        //    case VERTICAL_ALIGN_TOP:
        //
        //        canvas.drawText(text.toString(), x,
        //                y + (ofm.ascent + ofm.descent) -(fm.ascent + fm.descent), p);
        //        break;
        //    case VERTICAL_ALIGN_CENTER:
        //        canvas.drawText(text.toString(), x, y , p);
        //        break;
        //    case VERTICAL_ALIGN_BOTTOM:
        //    default:
        //        canvas.drawText(text.toString(), x, y, p);
        //        break;
        //}
        return 0;

    }

    //private void drawLine(Canvas canvas, float x, int y, int width, int color, int transition) {
    //    mLinePaint.setColor(color);
    //    canvas.drawLine(x, y + transition, x + width, y + transition, mLinePaint);
    //}

    private Paint getCustomTextPaint(Paint srcPaint) {
        Paint paint = new Paint(srcPaint);
        if (mColor != -1) {
            paint.setColor(mColor);
        }
        paint.setTextSize(mFontSize);   //设定字体大小
        if (mTypeface != null) {
            paint.setTypeface(mTypeface);
        }
        return paint;
    }
}
