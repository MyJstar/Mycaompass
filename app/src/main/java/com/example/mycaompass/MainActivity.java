package com.example.mycaompass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

class MyCompass extends View {

    float azimuth = 0; float pitch = 0; float roll = 0;

    // 프라이빗 멤버라면 매우 중요함. setter 함수로 해줘야 함.

    public void setAzimuth(float azimuth) {
        this.azimuth = azimuth;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public MyCompass(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.save();

        paint.setColor(Color.BLACK);
        paint.setTextSize(50f);
        canvas.drawText("방향센서 값" ,100,600,paint);
        canvas.drawText("방위각: " + azimuth, 100, 675, paint);
        canvas.drawText("피치: " + pitch, 100, 750, paint);
        canvas.drawText("롤: " + roll, 100, 825, paint);
        paint.setColor(Color.YELLOW);
        canvas.rotate(-azimuth,250,250);
        // 검은색 박스를 가만히 두고 캔버스 자체를 돌려버린다.

        canvas.drawCircle(250,250,200,paint);
        paint.setColor(Color.BLACK);
        //박스 그리기
        canvas.drawText("N", 235, 80, paint);
        canvas.drawText("S", 235, 460, paint);
        canvas.drawRect(240, 80, 260, 420, paint);
        canvas.restore();

    }



}


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    MyCompass myView;
    SensorManager sm;
    Sensor orientation;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = new MyCompass(this);
        setContentView(myView);


        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        orientation = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this,orientation,SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        //방향센서때문에 호출된것인지 확인
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION)    {
            myView.setAzimuth(sensorEvent.values[0]);
            myView.setPitch(sensorEvent.values[1]);
            myView.setRoll(sensorEvent.values[2]);
            myView.invalidate();
        }

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }




}