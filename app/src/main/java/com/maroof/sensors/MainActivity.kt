package com.maroof.sensors

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.getSystemService
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : AppCompatActivity(),SensorEventListener {
    //lateinit var sensorEventListner:SensorEventListener
    lateinit var sensorManager:SensorManager
    lateinit var proxSensor:Sensor
    lateinit var accelSensor:Sensor

    val colors = arrayOf(Color.RED,Color.BLUE,Color.CYAN,Color.MAGENTA,Color.GREEN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //sensor system service
         sensorManager = getSystemService<SensorManager>()!!

//        if (sensorManager==null){
//            Toast.makeText(this, "could not get sensors", Toast.LENGTH_LONG).show()
//            finish()
//            return
//        }else{
//            val Allsensors=sensorManager.getSensorList(Sensor.TYPE_ALL)
//            Allsensors.forEach {
//                Log.d("sensorslist","${it.name}, ${it.stringType}, ${it.vendor}")
//            }

         proxSensor= sensorManager?.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        accelSensor=sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        }



    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this,
            proxSensor,
            1000*1000
        )
        sensorManager.registerListener(
            this,
            accelSensor,
            1000*1000
        )
    }

    override fun onPause() {
        sensorManager.unregisterListener(this)
        super.onPause()
    }

//    override fun onSensorChanged(event: SensorEvent?) {
//        if(event!!.sensor.type==Sensor.TYPE_ACCELEROMETER){
//            val bgColor=Color.rgb(
//                accel2color(event!!.values[0]),
//                //accel2color(event!!.values[1]),
//                30,
//                90
//                //  accel2color(event!!.values[2])
//            )
//            flacc.setBackgroundColor(bgColor)
//        }
//
//        Log.d("proximity value","${event!!.values[0]}")
//        val tv=findViewById<TextView>(R.id.tvsensor)
//        tv.text=event.values[0].toString()
//        if (event.values[0]>0){
//            flprox.setBackgroundColor(colors[Random.nextInt(4)])
//        }
//
//
//    }
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//
//    }




    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
           tvx.text="x asis acc"+event!!.values[0].toString()
            tvy.text="y axis acc"+event!!.values[1].toString()
            tvz.text="z axis acc "+event!!.values[2].toString()
            val bgColor=Color.rgb(
                accel2color(event!!.values[0]),
                accel2color(event!!.values[1]),
                  accel2color(event!!.values[2])
            )
            flacc.setBackgroundColor(bgColor)
        }
        if(event.sensor.getType()==Sensor.TYPE_PROXIMITY){
            val tv=findViewById<TextView>(R.id.tvsensor)
        tv.text=event.values[0].toString()
        if (event.values[0]>0){
            flprox.setBackgroundColor(colors[Random.nextInt(4)])
        }
        }


    }


  override  fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


    private fun accel2color(accel:Float)=(((accel+12)/24)*255).roundToInt()
}
