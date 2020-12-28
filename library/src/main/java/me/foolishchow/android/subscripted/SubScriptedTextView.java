package me.foolishchow.android.subscripted;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;


public class SubScriptedTextView extends AppCompatTextView {
    public SubScriptedTextView(@NonNull Context context) {
        this(context, null);
    }

    public SubScriptedTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubScriptedTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context, attrs);
    }

    @Nullable
    private SpannableString mTopSubScript;

    @Nullable
    private SpannableString mLeftSubScript;
    @Nullable
    private SpannableString mRightSubScript;
    @Nullable
    private SpannableString mBottomSubScript;

    private void initAttribute(Context context, @Nullable AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SubScriptedTextView);

        String topSubscript = ta.getString(R.styleable.SubScriptedTextView_ss_top_text);
        if (!TextUtils.isEmpty(topSubscript)) {
            mTopSubScript = createSubscript(topSubscript, ta,
                    R.styleable.SubScriptedTextView_ss_top_text_size,
                    R.styleable.SubScriptedTextView_ss_top_text_color,
                    R.styleable.SubScriptedTextView_ss_top_font_family,
                    R.styleable.SubScriptedTextView_ss_top_text_align,
                    SubScriptedVerticalAlign.VERTICAL_ALIGN_BOTTOM
            );
        }


        String leftSubscript = ta.getString(R.styleable.SubScriptedTextView_ss_left_text);
        if (!TextUtils.isEmpty(leftSubscript)) {
            mLeftSubScript = createSubscript(leftSubscript, ta,
                    R.styleable.SubScriptedTextView_ss_left_text_size,
                    R.styleable.SubScriptedTextView_ss_left_text_color,
                    R.styleable.SubScriptedTextView_ss_left_font_family,
                    R.styleable.SubScriptedTextView_ss_left_text_align,
                    SubScriptedVerticalAlign.VERTICAL_ALIGN_BOTTOM
            );
        }

        String rightSubScript = ta.getString(R.styleable.SubScriptedTextView_ss_right_text);
        if (!TextUtils.isEmpty(rightSubScript)) {
            mRightSubScript = createSubscript(rightSubScript, ta,
                    R.styleable.SubScriptedTextView_ss_right_text_size,
                    R.styleable.SubScriptedTextView_ss_right_text_color,
                    R.styleable.SubScriptedTextView_ss_right_font_family,
                    R.styleable.SubScriptedTextView_ss_right_text_align,
                    SubScriptedVerticalAlign.VERTICAL_ALIGN_BOTTOM
            );
        }
        String bottomSubScript = ta.getString(R.styleable.SubScriptedTextView_ss_bottom_text);
        if (!TextUtils.isEmpty(bottomSubScript)) {
            mBottomSubScript = createSubscript(bottomSubScript, ta,
                    R.styleable.SubScriptedTextView_ss_bottom_text_size,
                    R.styleable.SubScriptedTextView_ss_bottom_text_color,
                    R.styleable.SubScriptedTextView_ss_bottom_font_family,
                    R.styleable.SubScriptedTextView_ss_bottom_text_align,
                    SubScriptedVerticalAlign.VERTICAL_ALIGN_TOP
            );
        }
        ta.recycle();
        if (mLeftSubScript != null || mRightSubScript != null) {
            setText(getText());
        }
    }

    private SpannableString createSubscript(
            String subscript,
            TypedArray ta,
            @StyleableRes int textSizeIndex,
            @StyleableRes int textColorIndex,
            @StyleableRes int textFontFamily,
            @StyleableRes int textVerticalAlign,
            @SubScriptedVerticalAlign.VerticalAlign int defaultVerticalAlign
    ) {
        SpannableString spannableString = new SpannableString(subscript);
        int end = subscript.length();

        int fontFamily = -1;
        Typeface font = null;
        try {
            fontFamily = ta.getResourceId(textFontFamily, -1);
            if (fontFamily != -1) {
                font = ResourcesCompat.getFont(getContext(), fontFamily);
                spannableString.setSpan(new SubScriptedTypefaceSpan(font), 0, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        } catch (Exception ignored) {

        }
        float morden = getTextSize();
        int textColor = ta.getColor(textColorIndex, -1);
        float textSize = ta.getDimension(textSizeIndex, -1);

        int align = ta.getInt(textVerticalAlign, defaultVerticalAlign);
        if(textSize == -1){
            textSize = morden;
        }
        spannableString.setSpan(new SubScriptedSpan(
                null,align,textColor,textSize
        ), 0, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (mLeftSubScript != null || mRightSubScript != null || mTopSubScript != null || mBottomSubScript != null) {
            super.setText(createText(text), BufferType.SPANNABLE);
        } else {
            super.setText(text, type);
        }
    }

    private SpannableStringBuilder mTextBuilder;

    private CharSequence createText(CharSequence text) {
        if (mTextBuilder == null) {
            mTextBuilder = new SpannableStringBuilder();
        } else {
            mTextBuilder.clearSpans();
            mTextBuilder.clear();
        }
        if(mTopSubScript != null){
            mTextBuilder.append(mTopSubScript).append("\n");
        }
        if (mLeftSubScript != null) {
            mTextBuilder.append(mLeftSubScript);
        }
        mTextBuilder.append(text);
        if (mRightSubScript != null) {
            mTextBuilder.append(mRightSubScript);
        }
        if (mBottomSubScript != null) {
            mTextBuilder
                    .append("\n").append(mBottomSubScript);
        }
        return mTextBuilder;
    }


}
