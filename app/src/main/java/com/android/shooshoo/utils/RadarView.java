package com.android.shooshoo.utils;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.android.shooshoo.models.CircleEntity;

import java.util.ArrayList;
import java.util.List;

public class RadarView extends View {
    Paint paint;
    Paint marker;
    List<CircleEntity> circleEntities=new ArrayList<CircleEntity>();
    int markerSize=18;
    int lines=6;
    OnRadarListener onRadarListener=null;
    public RadarView(Context context) {
        super(context);
        init();


    }

    private void init() {
        paint=new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.parseColor("#FFFFFF"));
        marker=new Paint();
        marker.setFlags(Paint.ANTI_ALIAS_FLAG);
        marker.setStyle(Paint.Style.FILL);
        marker.setMaskFilter(new BlurMaskFilter(25, BlurMaskFilter.Blur.OUTER));
        marker.setColor(Color.parseColor("#00FFFF"));

    }

    public RadarView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public List<CircleEntity> getPointFS() {
        return circleEntities;
    }

    public void setPointFS(List<CircleEntity> pointFS) {
        this.circleEntities = pointFS;
        invalidate();
    }

    int width,height,gap,radius,centerX,centerY;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;height=h;
        centerX=width/2;
        centerY=height/2;
        radius=Math.min(width,height)/2;
       gap=(radius)/lines;
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    int scale=1;
    public void setScale(int scale){
        if(scale>0)
        this.scale=scale;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int circle_index=1;circle_index<=lines;circle_index++)
        canvas.drawCircle(centerX,centerY,circle_index*gap,paint);
        for (CircleEntity  entity:circleEntities) {
             float xPoint=centerX+entity.getPointF().x*scale;
             float yPoint=centerY+entity.getPointF().y*scale;
             float distance= getDistance(xPoint,yPoint);
             if(distance<=radius)
            if(Math.abs(xPoint)<=centerX+radius&&Math.abs(xPoint)>=centerX-radius)
                if(Math.abs(yPoint)<=centerY+radius&&Math.abs(yPoint)>=centerY-radius)
            {
                   if(entity.getType()==0)
                   {
                       marker.setColor(Color.parseColor("#E01E62"));
//                       marker.setShadowLayer(markerSize,xPoint,yPoint,Color.parseColor("#E01E62"));
                       canvas.drawCircle(xPoint,yPoint,markerSize,marker);
                   }
                   else
                   {
                       marker.setColor(Color.parseColor("#FFFFFF"));
//                       marker.setShadowLayer(markerSize,xPoint,yPoint,Color.parseColor("#E01E62"));
                       canvas.drawCircle(xPoint,yPoint,markerSize,marker);
                   }
            }

        }

    }

    public void setOnRadarListener(OnRadarListener onRadarListener) {
        this.onRadarListener = onRadarListener;
    }

    /**calculation distance between to center and point
     *
     * @param xPoint
     * @param yPoint
     * @return distance from the center of the Radar View
     */
    private float getDistance(float xPoint, float yPoint) {
        float distance;
                float difx=centerX-xPoint;
                float dify=centerY-yPoint;
               distance=(float) Math.sqrt(difx*difx+dify*dify);
        return distance;
    }

    boolean isDialog=false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(onRadarListener!=null)
                if(isDialog)
                    onRadarListener.dismissPopup();

                break;
            case MotionEvent.ACTION_UP:
                for(CircleEntity entity:circleEntities){
                    float xPoint=centerX+entity.getPointF().x*scale;
                    float yPoint=centerY+entity.getPointF().y*scale;
                    if(isTouched(xPoint,yPoint,x,y)){
//                        Log.e("Point  is","X : "+pointF.x+" Y :"+pointF.y);
                        if(onRadarListener!=null)
                        {
                            onRadarListener.onItemClicked(x,y,entity);
                            isDialog=true;
                            return false;
                        }
                    }
                }
                break;

        }

        return true;

    }

    /**
     * Check your is touched on the one of the given points
     *
     * @param xval value of x position of point.
     * @param yval value of y position of point.
     * @param x x coordinate value of user touch point.
     * @param y y coordinate  value of user touch point.
     * @return true if clicked on reference point.
     */
    private boolean isTouched(float xval, float yval, float x, float y) {
        if(xval<=x+markerSize&&xval>=x-markerSize&&yval<=y+markerSize&&yval>=y-markerSize){
            return true;
        }
        return false;
    }
}
