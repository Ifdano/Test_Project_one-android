/*
 * Класс окна с генерацией случайных картинок с цифрами
 */

package com.example.myapplication_2020_24.firstpage;

import android.os.Bundle;

import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

import android.widget.Button;
import android.widget.ImageView;

import android.content.Intent;

import android.support.v4.app.FragmentActivity;

/*
 * так как данный класс находится в другом пакете/папке,
 * нужно отдельно добавить R и класс Main, для возврата
*/
import com.example.myapplication_2020_24.R;
import com.example.myapplication_2020_24.Main;

public class MainPicture extends FragmentActivity implements OnTouchListener{
	
	//кнопки генерации и возврата
	private Button buttonRandom;
	private Button buttonBack;
	
	//изображение цифр
	private ImageView imageNumber;
	
	//случайно число и массив с изображениями цифр
	private int random;
	private int[] ids;
	
	private Intent intent;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_page);
		
		init();
	}
	
	//начальная инициализация
	public void init(){
		//находим кнопки
		buttonRandom = (Button)findViewById(R.id.buttonGotPictureRandom);
		buttonBack = (Button)findViewById(R.id.buttonBack);
		
		imageNumber = (ImageView)findViewById(R.id.imageNumber);
		
		/*
		 * создаем массив для хранения всех изображений
		 * из папки drawable
		*/
		ids = new int[10];
		
		ids[0] = R.drawable.one;
		ids[1] = R.drawable.two;
		ids[2] = R.drawable.three;
		ids[3] = R.drawable.four;
		ids[4] = R.drawable.five;
		ids[5] = R.drawable.six;
		ids[6] = R.drawable.seven;
		ids[7] = R.drawable.eight;
		ids[8] = R.drawable.nine;
		ids[9] = R.drawable.ten;
		
		//устанавливаем слушателей
		buttonRandom.setOnTouchListener(this);
		buttonBack.setOnTouchListener(this);
	}

	public boolean onTouch(View view, MotionEvent event){

		if(event.getAction() == MotionEvent.ACTION_DOWN){
			
			//генерация случайного изображения
			if(view == buttonRandom){
				//генерируем случайное число от 0 до 9
				random = (int)(Math.random()*9);

				//устанавливаем на экран наше изображение с цифрой
				imageNumber.setImageResource(ids[random]);
			}
				
			//кнопка возврата
			if(view == buttonBack){
				intent = new Intent(this, Main.class);
				startActivity(intent);
			}

		}
		
		return false;
	}
}