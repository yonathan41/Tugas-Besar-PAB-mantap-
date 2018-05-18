package com.example.yonathan.ballandhole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import static android.content.Context.WINDOW_SERVICE;


public class FirstFragment extends Fragment implements SensorEventListener, View.OnClickListener {
    int scoreGame=0;
    TextView timer,score;
    ImageView ivArena;
    Canvas mCanvas;
    Bitmap mBitmap;
    Paint strokePaint;
    Ball ball, hole;
    Random rand ;
    SensorManager mSensorManager;
    Sensor mSensorAccelerometer, mSensorMagnetometer;
    Display mDisplay;
    float[]mAccelerometer, mMagnetometer;
    FragmentListener listener;
    Button back, reset;
    int batasKanan, batasBawah;
    int colorHole;
    int colorBall;
    public FirstFragment() {
        // Required empty public constructor
    }
    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_first, container, false);
        this.ivArena= view.findViewById(R.id.iv_arena);
        colorHole= ResourcesCompat.getColor(getResources(),R.color.Hole,null);
        colorBall= ResourcesCompat.getColor(getResources(),R.color.ball,null);
        ivArena.post(new Runnable() {
            @Override
            public void run() {
                batasKanan=ivArena.getWidth();
                batasBawah= ivArena.getHeight();
                mBitmap= Bitmap.createBitmap(batasKanan, batasBawah,Bitmap.Config.ARGB_8888);
                ivArena.setImageBitmap(mBitmap);
                mCanvas= new Canvas(mBitmap);
                ball = new Ball(randomX(ivArena.getWidth()),randomY(ivArena.getHeight()),ivArena.getWidth(),ivArena.getHeight(),colorBall);
                hole = new Ball(randomX(ivArena.getWidth()),randomY(ivArena.getHeight()),ivArena.getWidth(),ivArena.getHeight(),colorHole);
                initiateCanvas();
            }
        }

        );
        this.timer= view.findViewById(R.id.timer);
        this.score= view.findViewById(R.id.Score);
        ball= new Ball();
        this.reset=view.findViewById(R.id.main_reset);
        this.back= view.findViewById(R.id.main_menu);
        reset.setOnClickListener(this);
        back.setOnClickListener(this);
        rand= new Random();
        mSensorManager=(SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensorAccelerometer= mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorMagnetometer= mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        mAccelerometer= new float[9];
        mMagnetometer= new float[9];

        WindowManager wm = (WindowManager)getActivity().getSystemService(WINDOW_SERVICE);
        mDisplay= wm.getDefaultDisplay();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof  FragmentListener){
            this.listener=(FragmentListener)context;
        }
        else{
            throw new ClassCastException(context.toString()+"must implements FragmentListener");
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType= event.sensor.getType();

        switch(sensorType){
            case Sensor.TYPE_ACCELEROMETER:
                mAccelerometer= event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mMagnetometer= event.values.clone();
                break;
            default:return;
        }

        float[] rotationMatrix= new float[9];
        boolean rotationOK= SensorManager.getRotationMatrix(rotationMatrix,null,mAccelerometer,mMagnetometer);

        float[]rotationMatrixAdjusted= new float[9];
        switch (mDisplay.getRotation()){
            case Surface.ROTATION_0:
                rotationMatrixAdjusted= rotationMatrix.clone();
                break;
            case Surface.ROTATION_90:
                SensorManager.remapCoordinateSystem(rotationMatrix,SensorManager.AXIS_Y,SensorManager.AXIS_MINUS_X,rotationMatrixAdjusted);
                break;
            case Surface.ROTATION_180:
                SensorManager.remapCoordinateSystem(rotationMatrix,SensorManager.AXIS_MINUS_X,SensorManager.AXIS_MINUS_Y,rotationMatrixAdjusted);
                break;
            case Surface.ROTATION_270:
                SensorManager.remapCoordinateSystem(rotationMatrix,SensorManager.AXIS_MINUS_Y,SensorManager.AXIS_X,rotationMatrixAdjusted);
                break;

        }

        float orientationValue[]= new float[3];
        if(rotationOK){
            SensorManager.getOrientation(rotationMatrixAdjusted,orientationValue);

        }
        float pitch= orientationValue[1];
        float roll= orientationValue[2];
        ball.update((roll*ball.radius*0.001f),(pitch*ball.radius*0.001f));
        if(ball!=null&& hole!=null){
            if(ball.ballToOther(hole)){
            scoreGame+=1;
            this.score.setText(scoreGame+"");
            create();
            }
        }
        initiateCanvas();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private float randomY(int height) {
        return ball.radius+rand.nextInt(height-(2*ball.radius));
    }

    private float randomX(int width) {
        return ball.radius+rand.nextInt(width-(2*ball.radius));
    }

    private void initiateCanvas() {
        int color= ResourcesCompat.getColor(getResources(),R.color.White,null);
        mCanvas.drawColor(color);
        strokePaint= new Paint();
        strokePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        strokePaint.setStrokeWidth(4);

        hole.draw(mCanvas,strokePaint);

        ball.draw(mCanvas,strokePaint);
        this.ivArena.invalidate();



    }
    @Override
    public void onResume() {
        super.onResume();
        if(mSensorAccelerometer!= null ){
            mSensorManager.registerListener(this,mSensorAccelerometer,SensorManager.SENSOR_DELAY_GAME);
        }
        if(mSensorMagnetometer!=null){
            mSensorManager.registerListener(this,mSensorMagnetometer,SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onClick(View v) {
        if(v==back){
            listener.changePage(2);
            mSensorManager.unregisterListener(this);

        }
        else{
            create();
            this.scoreGame=0;
            this.score.setText(scoreGame+"");
        }
    }

    public void create(){
        ball = new Ball(randomX(ivArena.getWidth()),randomY(ivArena.getHeight()),ivArena.getWidth(),ivArena.getHeight(),colorBall);
        hole = new Ball(randomX(ivArena.getWidth()),randomY(ivArena.getHeight()),ivArena.getWidth(),ivArena.getHeight(),colorHole);
    }
}
