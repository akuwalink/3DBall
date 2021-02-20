package com.akuwalink.ball.ui.mainview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.akuwalink.ball.R;

public class CircleView extends View {
    Paint paint;
    Paint edge_paint;
    private int resId;
    private int edge_color;
    private float scale_x;
    private float scale_y;
    private Matrix matrix;
    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        resId=array.getResourceId(R.styleable.CircleView_background,R.drawable.main_headimage);
        edge_color=array.getColor(R.styleable.CircleView_edge_color,Color.BLACK);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        paint=new Paint();
        edge_paint=new Paint();
        edge_paint.setAntiAlias(true);
        edge_paint.setColor(edge_color);
        edge_paint.setStrokeWidth(20);
        edge_paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        matrix=new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft=getPaddingLeft();
        int paddingRight=getPaddingRight();
        int paddingBottom=getPaddingBottom();
        int paddingTop=getPaddingTop();
        int width=getWidth()-paddingLeft;
        int height=getHeight()-paddingBottom;
        float center_w=getWidth();
        float center_h=getHeight();
        Drawable drawable= getResources().getDrawable(resId,null);
        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();
        if(bitmap==null) return;
        float min_view=Math.min(width,height);
        float radius=min_view/2;
        float c_min_view=Math.min(center_w,center_h)/2;
        matrix.setScale(center_w/bitmap.getWidth(),center_h/bitmap.getHeight());
        BitmapShader shader=new BitmapShader(bitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
        canvas.drawCircle(c_min_view,c_min_view,radius,edge_paint);
        canvas.drawCircle(c_min_view,c_min_view,radius,paint);
    }
}
