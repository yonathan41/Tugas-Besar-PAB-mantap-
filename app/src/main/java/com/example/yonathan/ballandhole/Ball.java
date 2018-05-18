package com.example.yonathan.ballandhole;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball {
    float x,y,kecepatanX, kecepatanY;
    int kanan,bawah;
    int color,radius;

    public Ball(float x, float y, int kanan, int bawah,int color) {
        this.x = x;
        this.y = y;
        this.color=color;
        this.kecepatanX=0;
        this.kecepatanY=0;
        this.kanan=kanan;
        this.bawah=bawah;
        radius=100;
    }
    public Ball(){
        this.x = 0;
        this.y = 0;
        this.kanan=0;
        this.bawah=0;
        this.kecepatanX=0;
        this.kecepatanY=0;
        radius=100;
    }
    public void update(float kx,float ky){
        if(kx==0){
            kecepatanX=0;
        }
        else if(ky==0){
            kecepatanY=0;
        }
        this.kecepatanX+=kx;
        this.kecepatanY+=ky;
        float xX=this.x+kecepatanX;
        if(xX<=radius){
            this.x=radius;
            this.kecepatanX/=-1.3;
        }
        else if(xX>=kanan-radius){
            this.x=kanan-radius;
            this.kecepatanX/=-1.3;
        }
        else{
            this.x=xX;
        }
        float yY=this.y-kecepatanY;
        if(yY<=radius){
            this.y=radius;
            this.kecepatanY/=-1.3;
        }
        else if(yY>=bawah-radius){
            this.y=bawah-radius;
            this.kecepatanY/=-1.3;
        }
        else{
            this.y=yY;
        }
    }

    public float getX() {

        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void draw(Canvas mCanvas, Paint strokePaint) {
        strokePaint.setColor(color);
        mCanvas.drawCircle(x,y,radius,strokePaint);
    }

    public boolean ballToOther(Ball other){
        double halfradius= radius*0.75;
        if(other.getX()>this.x-halfradius&&other.getX()<this.x+halfradius&&
           other.getY()>this.y-halfradius&&other.getY()<this.y+halfradius){
            return true;
        }
        else {
            return false;
        }
    }
}
