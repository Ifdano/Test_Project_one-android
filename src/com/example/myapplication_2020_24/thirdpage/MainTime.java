/*
 * Класс окна c падающими друг на друга кубиками,
 * перемещение которых можно отматывать назад и вперед.
 */

package com.example.myapplication_2020_24.thirdpage;

import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;

import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import android.content.Intent;

//добавляем R и Main, для возврата на главный экран
import com.example.myapplication_2020_24.R;
import com.example.myapplication_2020_24.Main;

public class MainTime extends FragmentActivity implements OnTouchListener, OnSeekBarChangeListener{
	
	//кнопки возврата и генерации новых кубиков
	private Button buttonBack;
	private Button buttonGenerate;
	
	//ползунок для возможности контроля передвижения
	private SeekBar seekTime;
	private int seekProgress;
	
	//панель с падающими кубиками
	private GamePanel gamePanel;
	
	private Intent intent;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.third_page);
		
		init();
	}
	
	//начальная инициализация
	public void init(){
		//находим панель с падающими кубиками
		gamePanel = (GamePanel)findViewById(R.id.gamePanel);
		
		//находим остальные компоненты
		buttonBack = (Button)findViewById(R.id.buttonBack);
		buttonGenerate = (Button)findViewById(R.id.buttonGenerate);
		
		seekTime = (SeekBar)findViewById(R.id.seekTime);
		
		//устанавливаем слушателей
		buttonBack.setOnTouchListener(this);
		buttonGenerate.setOnTouchListener(this);
		
		seekTime.setOnSeekBarChangeListener(this);
	}
	
	//обрабатываем касание
	public boolean onTouch(View view, MotionEvent event){
		
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			//генерация новых квадратов
			if(view == buttonGenerate){
				//заново вызываем методы начальной инициализации 
				gamePanel.init();
				gamePanel.initRects();
				
				//устанавливаем значение ползунка в начальное положение
				seekTime.setProgress(0);
			}
			
			//возврат на главный экран
			if(view == buttonBack){
				intent = new Intent(this, Main.class);
				startActivity(intent);
			}
		}
		
		return false;
	}
	
	//изменение положения ползунка
	public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){
		if(seekbar == seekTime){	
			
			//если изменяем положение ползунка вправо
			if(progress > seekProgress){
				/*
				 * останавливаем автоматическое обновление и 
				 * вызываем метод ручного падения квадратов.
				 * 
				 * если быстро перемещать ползунок, то будут теряться
				 * значение ползунка, а это будет влиять на падение квадратов.
				 * 
				 *  поэтому, будем брать разницу между текущим положением ползунка
				 *  и предыдущим, и делить на условное число, чтобы на падение квадратов
				 *  влияло каждое перемещение ползунка
				 */
				gamePanel.setUpdateNextStep((progress - seekProgress)/5);
				
				seekProgress = progress;
			}
			
			//если изменяем положение ползунка влева
			if(progress < seekProgress){
				/*
				 * останавливаем автоматическое обновление и 
				 * вызываем метод ручного поднятия квадратов
				 */
				gamePanel.setUpdateBackStep((seekProgress - progress)/5);
				
				seekProgress = progress;
			}
		}
	}
	
	//как только коснулись ползунка
	public void onStartTrackingTouch(SeekBar seekbar){
		if(seekbar == seekTime){
			//останавливаем автоматическое обновление
			gamePanel.setUpdateStop();
			//и получаем текущее значение положения
			seekProgress = seekbar.getProgress();
		}
	}
	
	//как только отпусти ползунок
	public void onStopTrackingTouch(SeekBar seekbar){
		/*
		 * запускаем автоматическое обновление,
		 * квадраты продолжат падать автоматически
		 */
		gamePanel.setUpdateStart();
	}
}