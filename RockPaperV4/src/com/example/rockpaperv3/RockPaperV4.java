package com.example.rockpaperv3;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class RockPaperV4 extends Activity implements SensorEventListener {
	
	private SensorManager mSensorManager;
	private Sensor mSensor;
	public int choice=0;
	public ImageView myImage1,myImage2; 
	public TextView myTextView;
	public String louts[]={"rock","paper","scissors"};
	public int i=0;
	public float gravity[]={0,0,0}; 
	public float linear_acceleration[]={0,0,0};
	public boolean withSensor=true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


			mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			//mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
			 if (mSensor != null){
				  // Success! There's an accelerometer sensor		
				setContentView(R.layout.rock_paper_accelerate_with_sensors);
				 withSensor=true;
			
			 }
			 else {
				 	
				 	mSensorManager.unregisterListener(this);
				 	setContentView(R.layout.activity_rock_paper3);
				 	withSensor=false;
				  }		 
		
			
	}
	
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.radio1:
	            if (checked)
	                // player chose rock
	            	choice=1;

	            break;	            
	        case R.id.radio2:
	            if (checked)
	                // player chose paper
	            	choice=2;
	  
	            break;
	        case R.id.radio3:
	            if (checked)
	                // player chose scissors
	            	choice=3;
          
	            break;  
	    }
	    
	    if (withSensor==false){
	    	checkWin(choice);
	    }
	   
	}
	
	
public void checkWin(int choice){
		
		Random generator = new Random();
		int machine = generator.nextInt( 3 )+1;
		setContentView(R.layout.show_results);
		myImage1 = (ImageView) findViewById(R.id.imageView1);
		myImage2 = (ImageView) findViewById(R.id.imageView2);
		myTextView = (TextView) findViewById(R.id.textView1);
		switch (machine){
		case 1: myImage1.setImageResource(R.drawable.rock);
		break;
		case 2: myImage1.setImageResource(R.drawable.paper);
		break;
		case 3: myImage1.setImageResource(R.drawable.scissors);
		break;
		}
		
		switch (choice){
		case 1: myImage2.setImageResource(R.drawable.rock);
		break;
		case 2: myImage2.setImageResource(R.drawable.paper);
		break;
		case 3: myImage2.setImageResource(R.drawable.scissors);
		break;
		}
		
		if (choice==machine){
			myTextView.setText("This is a tie!");
		}
		else if ((choice==3 && machine==2)||(choice==2 && machine==1)||(choice==1 && machine==3)){
			myTextView.setText("You Win!");
		}
		else{
			myTextView.setText("You Lose!");
		}
		Button myButton =  (Button)findViewById(R.id.button1);
		myButton.setOnClickListener(new OnClickListener() {

			@Override
	        public void onClick(View v) {
	        	i=0;
	        	Intent intent= new Intent(v.getContext(), RockPaperV4.class);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        	finish();
	        	startActivity(intent);
	        	
	            
	        }
	    });
		
		
	}
	
	
	

	@Override
	protected void onResume(){
		//mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		//mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
		super.onResume();
	
	}
	
	@Override
	protected void onPause(){
		mSensorManager.unregisterListener(this);
		super.onPause(); 
	}


	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		    
		    
		final float alpha = (float) 0.8;

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
                
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        if(withSensor==true){
        	
        	////ROCK////////////////////
        	
		  if (linear_acceleration[1]<-2.0 && linear_acceleration[0]>2.0){
			
						  mSensorManager.unregisterListener(this);
						  choice=1;
						  checkWin(choice);}

		  
		  ////////////PAPER/////////////////////
		  else if(linear_acceleration[0]<-2.0  && linear_acceleration[1]<-2.0){
			  
			
							  mSensorManager.unregisterListener(this);
							  choice=2;
							  checkWin(choice);}
		  
		  
		  ////////////////////scissors//////////////////////
		  else if (linear_acceleration[1]<-2.0 && linear_acceleration[2]>2.0){
				
			  mSensorManager.unregisterListener(this);
			  choice=3;
			  checkWin(choice);}
        	}
			  
	}
}


