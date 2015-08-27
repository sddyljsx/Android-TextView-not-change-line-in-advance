

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by songxun.sx on 2015/7/20.
 */
public class TextDrawView extends View {
    private int textColor = 0xffafafaf;
    private float textSize = 12;
    private String textToDraw = "";
    private List<String> textLines=new ArrayList<String>();
    private float lineMul=1.3f;
    TextPaint textPaint;

    public TextDrawView(Context context) {
        super(context);
        init();
    }

    public TextDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        setTextColor(textColor);
        setTextSize(textSize);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        textPaint.setColor(textColor);
    }

    /**
     * µ¥Î»sp
     */
    public void setTextSize(float textSize) {
        Context context = this.getContext();
        Resources r = Resources.getSystem();
        if (context != null) {
            r = context.getResources();
        }
        this.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, r.getDisplayMetrics());
        textPaint.setTextSize(this.textSize);
    }

    public void setText(String textToDraw) {
        if (textToDraw == null) {
            return;
        }
        this.textToDraw = textToDraw;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        textLines.clear();
        while (textToDraw.length() > 0) {
            int size = textPaint.breakText(textToDraw, true, getMeasuredWidth(),
                    null);
            textLines.add(textToDraw.substring(0, size));
            textToDraw = textToDraw.substring(size);
        }
        int width, height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = getMeasuredWidth();
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = textLines.size()*(int)(textPaint.getTextSize()*lineMul)+1;
        }
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float x=0;
        float y=textPaint.getTextSize();
        for(String text:textLines){
            canvas.drawText(text,x,y,textPaint);
            y=y+textPaint.getTextSize()*lineMul;
        }

    }
}
