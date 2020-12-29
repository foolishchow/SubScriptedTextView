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
    private SubScriptConfig mTopConfig;
    @Nullable
    private SpannableString mLeftSubScript;
    @Nullable
    private SubScriptConfig mLeftConfig;
    @Nullable
    private SpannableString mRightSubScript;
    @Nullable
    private SubScriptConfig mRightConfig;
    @Nullable
    private SpannableString mBottomSubScript;
    @Nullable
    private SubScriptConfig mBottomConfig;

    private void initAttribute(Context context, @Nullable AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SubScriptedTextView);

        String topSubscript = ta.getString(R.styleable.SubScriptedTextView_ss_top_text);
        if (!TextUtils.isEmpty(topSubscript)) {
            mTopConfig = createConfig(topSubscript, ta,
                    R.styleable.SubScriptedTextView_ss_top_text_size,
                    R.styleable.SubScriptedTextView_ss_top_text_color,
                    R.styleable.SubScriptedTextView_ss_top_font_family,
                    R.styleable.SubScriptedTextView_ss_top_text_align,
                    SubScriptedVerticalAlign.VERTICAL_ALIGN_BOTTOM
            );
            mTopSubScript = createSubscript(mTopConfig);
        }

        String leftSubscript = ta.getString(R.styleable.SubScriptedTextView_ss_left_text);
        if (!TextUtils.isEmpty(leftSubscript)) {
            mLeftConfig = createConfig(leftSubscript, ta,
                    R.styleable.SubScriptedTextView_ss_left_text_size,
                    R.styleable.SubScriptedTextView_ss_left_text_color,
                    R.styleable.SubScriptedTextView_ss_left_font_family,
                    R.styleable.SubScriptedTextView_ss_left_text_align,
                    SubScriptedVerticalAlign.VERTICAL_ALIGN_BOTTOM
            );
            mLeftSubScript = createSubscript(mLeftConfig);
        }

        String rightSubScript = ta.getString(R.styleable.SubScriptedTextView_ss_right_text);
        if (!TextUtils.isEmpty(rightSubScript)) {
            mRightConfig = createConfig(rightSubScript, ta,
                    R.styleable.SubScriptedTextView_ss_right_text_size,
                    R.styleable.SubScriptedTextView_ss_right_text_color,
                    R.styleable.SubScriptedTextView_ss_right_font_family,
                    R.styleable.SubScriptedTextView_ss_right_text_align,
                    SubScriptedVerticalAlign.VERTICAL_ALIGN_BOTTOM
            );
            mRightSubScript = createSubscript(mRightConfig);
        }

        String bottomSubScript = ta.getString(R.styleable.SubScriptedTextView_ss_bottom_text);
        if (!TextUtils.isEmpty(bottomSubScript)) {
            mBottomConfig = createConfig(bottomSubScript, ta,
                    R.styleable.SubScriptedTextView_ss_bottom_text_size,
                    R.styleable.SubScriptedTextView_ss_bottom_text_color,
                    R.styleable.SubScriptedTextView_ss_bottom_font_family,
                    R.styleable.SubScriptedTextView_ss_bottom_text_align,
                    SubScriptedVerticalAlign.VERTICAL_ALIGN_TOP
            );
            mBottomSubScript = createSubscript(mBottomConfig);
        }
        ta.recycle();
        if (hasSubScripted()) {
            setText(getText());
        }
    }

    private SubScriptConfig createConfig(
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
        if (textSize == -1) {
            textSize = morden;
        }
        return new SubScriptConfig(subscript, font, textSize, textColor, align);
    }

    private SpannableString createSubscript(SubScriptConfig config) {
        SpannableString spannableString = new SpannableString(config.getText());
        int end = config.getText().length();

        Typeface font = config.getFontFamily();

        float morden = getTextSize();
        int textColor = config.getTextColor();
        float textSize = config.getTextSize();

        int align = config.getAlign();
        if (textSize == -1) {
            textSize = morden;
        }
        spannableString.setSpan(new SubScriptedSpan(
                null, align, textColor, textSize
        ), 0, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    private CharSequence mOriginText = null;

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (hasSubScripted()) {
            mOriginText = text;
            super.setText(createText(text), BufferType.SPANNABLE);
        } else {
            super.setText(text, type);
        }
    }

    private boolean hasSubScripted() {
        return mLeftSubScript != null || mRightSubScript != null || mTopSubScript != null || mBottomSubScript != null;
    }

    private SpannableStringBuilder mTextBuilder;

    private CharSequence createText(CharSequence text) {
        if (mTextBuilder == null) {
            mTextBuilder = new SpannableStringBuilder();
        } else {
            mTextBuilder.clearSpans();
            mTextBuilder.clear();
        }
        if (mTopSubScript != null) {
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


    public void setBottomSubScript(String text) {
        if (mBottomConfig == null) {
            mBottomConfig = new SubScriptConfig(text, getTextSize());
        } else {
            mBottomConfig.setText(text);
        }
        mBottomSubScript = createSubscript(mBottomConfig);
        setText(mOriginText);
    }

    public void setLeftSubScript(String text) {
        if (mLeftConfig == null) {
            mLeftConfig = new SubScriptConfig(text, getTextSize());
        } else {
            mLeftConfig.setText(text);
        }
        mLeftSubScript = createSubscript(mLeftConfig);
        setText(mOriginText);
    }

    public void setRightSubScript(String text) {
        if (mRightConfig == null) {
            mRightConfig = new SubScriptConfig(text, getTextSize());
        } else {
            mRightConfig.setText(text);
        }
        mRightSubScript = createSubscript(mRightConfig);
        setText(mOriginText);
    }

    public void setTopSubScript(String text) {
        if (mTopConfig == null) {
            mTopConfig = new SubScriptConfig(text, getTextSize());
        } else {
            mTopConfig.setText(text);
        }
        mTopSubScript = createSubscript(mTopConfig);
        setText(mOriginText);
    }

}
