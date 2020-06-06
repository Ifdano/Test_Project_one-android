/*
 * Тестовое приложение для практики.
 * Состоит из трех окон.
 * 
 * Первое: генерация случайных картинок с цифрами.
 * Второе: игра с цветами RGB.
 * Третье: падающие друг на друга кубики,
 * перемещение которых можно отматывать назад и вперед.
 * 
 */

package com.example.myapplication_2020_24;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

import android.widget.Button;

import android.content.Intent;

//добавление классов с окнами, которые находятся в других пакетах
import com.example.myapplication_2020_24.firstpage.MainPicture;
import com.example.myapplication_2020_24.secondpage.MainColor;
import com.example.myapplication_2020_24.thirdpage.MainTime;

public class Main extends Activity implements OnTouchListener{
	
	//кнопки для перехода в окна
	private Button buttonPicture;
	private Button buttonColor;
	private Button buttonTime;
	
	private Intent intent;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
	}
	
	//начальная инициализация
	public void init(){
		//находим кнопки
		buttonPicture = (Button)findViewById(R.id.buttonGotPicture);
		buttonColor = (Button)findViewById(R.id.buttonGotColor);
		buttonTime = (Button)findViewById(R.id.buttonGotTime);
		
		//устанавливаем слушателей для кнопок
		buttonPicture.setOnTouchListener(this);
		buttonColor.setOnTouchListener(this);
		buttonTime.setOnTouchListener(this);
	}
	
	//обрабатываем касание
	public boolean onTouch(View view, MotionEvent event){
		
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			
			//переходим в первое окно
			if(view == buttonPicture){
				intent = new Intent(this, MainPicture.class);
				startActivity(intent);
			}
			
			//переходим во второе окно
			if(view == buttonColor){
				intent = new Intent(this, MainColor.class);
				startActivity(intent);
			}
			
			//переходим в третье окно
			if(view == buttonTime){
				intent = new Intent(this, MainTime.class);
				startActivity(intent);
			}
		}
		
		return false;
	}
}