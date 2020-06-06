/*
 * Класс окна c получением цветов RGB.
 * Состояние автоматически сохраняется с помощью SharedPreferences
 */

package com.example.myapplication_2020_24.secondpage;

import android.os.Bundle;

import android.support.v4.app.FragmentActivity;

import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import android.graphics.Color;

//для возможности использования R и перехода на Main
import com.example.myapplication_2020_24.R;
import com.example.myapplication_2020_24.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;

public class MainColor extends FragmentActivity implements OnTouchListener, OnSeekBarChangeListener{
	//условное название файла для сохранения данных
	public static final String SAVE = "save";
	
	//сохраняем данные по цветам
	public static final String RED = "red";
	public static final String GREEN = "green";
	public static final String BLUE = "blue";
	
	//сохраняем данные по позиции ползунков
	public static final String RED_PRO = "redPro";
	public static final String GREEN_PRO = "greeenPro";
	public static final String BLUE_PRO = "bluePro";
	
	private Button buttonBack;
	
	//цвет всего окна
	private LinearLayout colorMain;
	
	//цвета квадратов
	private LinearLayout colorFirst;
	private LinearLayout colorSecond;
	private LinearLayout colorThird;
	
	//цвета ползунков
	private LinearLayout colorRed;
	private LinearLayout colorGreen;
	private LinearLayout colorBlue;
	
	//ползунки для получения цветов
	private SeekBar seekRed;
	private SeekBar seekGreen;
	private SeekBar seekBlue;
	
	//позиции ползунков
	private int redProgress;
	private int greenProgress;
	private int blueProgress;
	
	//итоговые коды цветов каждого ползунка
	private String redSeekBarProgress;
	private String greenSeekBarProgress;
	private String blueSeekBarProgress;
	
	//итоговой код цвета всего окна 
	private String finalColor;
	
	private Intent intent;
	
	//для сохранения данных
	private SharedPreferences mSettings;
	
	protected void onCreate(Bundle savedInstanseState){
		super.onCreate(savedInstanseState);
		setContentView(R.layout.second_page);
		
		init();
	}
	
	protected void onStart(){ super.onStart(); }
	
	//восстановление данных и помещение их на экран
	protected void onResume(){
		super.onResume();
		
		//получаем состояние ползунков и устанавливаем значения
		if(mSettings.contains(RED_PRO)){
			redProgress = mSettings.getInt(RED_PRO, 0);
			seekRed.setProgress(redProgress);
		}
		
		if(mSettings.contains(GREEN_PRO)){
			greenProgress = mSettings.getInt(GREEN_PRO, 0);
			seekGreen.setProgress(greenProgress);
		}
		
		if(mSettings.contains(BLUE_PRO)){
			blueProgress = mSettings.getInt(BLUE_PRO, 0);
			seekBlue.setProgress(blueProgress);
		}
		
		//получаем коды цветов
		if(mSettings.contains(RED))
			redSeekBarProgress = mSettings.getString(RED, "00");
		
		if(mSettings.contains(GREEN))
			greenSeekBarProgress = mSettings.getString(GREEN, "00");
		
		if(mSettings.contains(BLUE))
			blueSeekBarProgress = mSettings.getString(BLUE, "00");
		
		//устанавливаем цвета каждого ползунка
		colorRed.setBackgroundColor(Color.parseColor("#" + redSeekBarProgress + "0000"));
		colorGreen.setBackgroundColor(Color.parseColor("#00" + greenSeekBarProgress + "00" ));
		colorBlue.setBackgroundColor(Color.parseColor("#0000" + blueSeekBarProgress));
		
		//усстанавливаем цвета каждого квадрата
		colorFirst.setBackgroundColor(Color.parseColor("#" + redSeekBarProgress + greenSeekBarProgress + "00"));
		colorSecond.setBackgroundColor(Color.parseColor("#00" + greenSeekBarProgress + blueSeekBarProgress));
		colorThird.setBackgroundColor(Color.parseColor("#" + redSeekBarProgress + "00" + blueSeekBarProgress));
		
		//устанавливаем цвет основного окна
		finalColor = redSeekBarProgress + greenSeekBarProgress + blueSeekBarProgress;
		
		colorMain.setBackgroundColor(Color.parseColor("#" + finalColor));
	}
	
	//сохраняем данных
	protected void onPause(){
		super.onPause();
		
		SharedPreferences.Editor editor = mSettings.edit();
		
		//сохраняем коды цветов
		editor.putString(RED, redSeekBarProgress);
		editor.putString(GREEN, greenSeekBarProgress);
		editor.putString(BLUE, blueSeekBarProgress);
		
		//сохраняем состояние ползунков
		editor.putInt(RED_PRO, redProgress);
		editor.putInt(GREEN_PRO, greenProgress);
		editor.putInt(BLUE_PRO, blueProgress);
		
		editor.apply();
	}
	
	//сохраняем данных
	protected void onStop(){
		super.onStop();
		
		SharedPreferences.Editor editor = mSettings.edit();
		
		//сохраняем коды цветов
		editor.putString(RED, redSeekBarProgress);
		editor.putString(GREEN, greenSeekBarProgress);
		editor.putString(BLUE, blueSeekBarProgress);
		
		//сохраняем состояние ползунков
		editor.putInt(RED_PRO, redProgress);
		editor.putInt(GREEN_PRO, greenProgress);
		editor.putInt(BLUE_PRO, blueProgress);
		
		editor.apply();
	}
	
	protected void onDestroy(){ super.onDestroy(); }
	
	//начальная инициализация
	public void init(){
		//для сохранения данных
		mSettings = getSharedPreferences(SAVE, Context.MODE_PRIVATE);
		
		//находим компоненты окна
		buttonBack = (Button)findViewById(R.id.buttonBack);
		
		colorMain = (LinearLayout)findViewById(R.id.colorMain);
		
		colorFirst = (LinearLayout)findViewById(R.id.colorFirst);
		colorSecond = (LinearLayout)findViewById(R.id.colorSecond);
		colorThird = (LinearLayout)findViewById(R.id.colorThird);
		
		colorRed = (LinearLayout)findViewById(R.id.colorRed);
		colorGreen = (LinearLayout)findViewById(R.id.colorGreen);
		colorBlue = (LinearLayout)findViewById(R.id.colorBlue);
		
		seekRed = (SeekBar)findViewById(R.id.seekRed);
		seekGreen = (SeekBar)findViewById(R.id.seekGreen);
		seekBlue = (SeekBar)findViewById(R.id.seekBlue);
		
		//устанавливаем слушателей для кнопки и ползунков
		buttonBack.setOnTouchListener(this);
		
		seekRed.setOnSeekBarChangeListener(this);
		seekGreen.setOnSeekBarChangeListener(this);
		seekBlue.setOnSeekBarChangeListener(this);
		
		//начальные значения кодов цветов
		finalColor = "";
		redSeekBarProgress = "00";
		greenSeekBarProgress = "00";
		blueSeekBarProgress = "00";
	}
	
	public boolean onTouch(View view, MotionEvent event){
		
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			//возврат на главный экран
			if(view == buttonBack){
				intent = new Intent(this, Main.class);
				startActivity(intent);
			}
		}
		
		return false;
	}
	
	//при изменении положения ползунка
	public void onProgressChanged(SeekBar seekbar, int progress, boolean fromuser){
		//изменения первого ползунка
		if(seekbar == seekRed){
			/*
			 * получаем состояние ползунк и переводим
			 * полученное значение в 16-ю систему счисления
			 */
			redSeekBarProgress = getHeximal(progress);
			redProgress = progress;
			
			//устанавливаем цвет для первого ползунка
			colorRed.setBackgroundColor(Color.parseColor("#" + redSeekBarProgress + "0000"));
			
			//устанавливаем цвет верхнего и нижнего квадратов
			colorFirst.setBackgroundColor(Color.parseColor("#" + redSeekBarProgress + greenSeekBarProgress + "00"));
			colorThird.setBackgroundColor(Color.parseColor("#" + redSeekBarProgress + "00" + blueSeekBarProgress));
		}
		
		//изменения второго ползунка
		if(seekbar == seekGreen){
			greenSeekBarProgress = getHeximal(progress);
			greenProgress = progress;
			
			//устанавливаем цвет для второго ползунка
			colorGreen.setBackgroundColor(Color.parseColor("#00" + greenSeekBarProgress + "00" ));
			
			//устанавливаем цвет верхнего и среднего квадратов
			colorFirst.setBackgroundColor(Color.parseColor("#" + redSeekBarProgress + greenSeekBarProgress + "00"));
			colorSecond.setBackgroundColor(Color.parseColor("#00" + greenSeekBarProgress + blueSeekBarProgress));
		}
		
		//изменения третьего ползунка
		if(seekbar == seekBlue){
			blueSeekBarProgress = getHeximal(progress);
			blueProgress = progress;
			
			//устанавливаем цвет для третьего ползунка
			colorBlue.setBackgroundColor(Color.parseColor("#0000" + blueSeekBarProgress));
			
			//устанавливаем цвет среднего и нижнего квадратов
			colorSecond.setBackgroundColor(Color.parseColor("#00" + greenSeekBarProgress + blueSeekBarProgress));
			colorThird.setBackgroundColor(Color.parseColor("#" + redSeekBarProgress + "00" + blueSeekBarProgress));
		}
		
		//устанавливаем цвет основного окна
		finalColor = redSeekBarProgress + greenSeekBarProgress + blueSeekBarProgress;
		
		colorMain.setBackgroundColor(Color.parseColor("#" + finalColor));
	}
	
	public void onStartTrackingTouch(SeekBar seekbar){}
	
	public void onStopTrackingTouch(SeekBar seekbar){}
	
	//перевод числа в 16-ю систему счисления
	public static String getHeximal(int numberN){
		String temp;
		String[] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
		
		temp = "";
		
		if(numberN < 16)
			temp += "0" + digits[numberN];
		else
			temp += digits[numberN/16] + digits[numberN%16];
		
		return temp;
	}
}
