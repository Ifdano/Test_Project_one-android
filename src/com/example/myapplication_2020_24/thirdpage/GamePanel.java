//Окно с падающими кубиками

package com.example.myapplication_2020_24.thirdpage;

import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Canvas;

import android.view.View;
import android.content.Context;

//для возможности совместимости макета и холста
import android.util.AttributeSet;

import java.util.ArrayList;

//для определения разрешения/плотности экрана
import android.util.DisplayMetrics;

public class GamePanel extends View implements Runnable{

	//для установки цикла
	private Thread thread;
	private boolean running;
	
	private int FPS = 30;
	private int targetTime = 1000/FPS;
	
	//для отрисовки
	private Paint paint;
	private int canvasWidth;
	private int canvasHeight;
	
	//хранение квадратов
	private ArrayList<Rect> rects;
	
	//падение текущего кубика
	private int step;
	private boolean rectCrush;
	
	//разрешение на инициализацию кубиков
	private boolean isInitRect;
	
	//для остановки/возобновления автоматического обновления экрана
	private boolean updateStart;
	
	private Context context;
	
	/*
	 * данные кубиков, которые будут подстроены под
	 * разрешение/плотности экрана 
	 */
	private int rectSize;
	private int rectSpeed;
	private int densityDpi;
	
	public GamePanel(Context context){
		super(context);
		this.context = context;
		
		addNotify();
	}
	
	public GamePanel(Context context, AttributeSet attrs){
		super(context, attrs);
		this.context = context;
		
		addNotify();
	}
	
	public GamePanel(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		this.context = context;
		
		addNotify();
	}
	
	//возобновление автоматического падения кубиков
	public void setUpdateStart(){ updateStart = true; }
	//остановка автоматического падения кубиков
	public void setUpdateStop(){ updateStart = false; }
	
	//установка потока
	public void addNotify(){
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	//отрисовка кубиков
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		//устанавливаем белый экран для холста
		canvas.drawColor(Color.WHITE);
		
		//получаем размеры холста
		canvasWidth = canvas.getWidth();
		canvasHeight = canvas.getHeight();
		
		/*
		 * так как поток в первую очередь включает 
		 * обновление экрана, нам нужно дождаться, 
		 * пока мы не инициализируем наши кубики,
		 * чтобы их можно было потом выводить на экран
		 * 
		 */
		if(!isInitRect)
			initRects();
		
		//берем кубики и выводим на экран
		for(int i = 0; i <= step; i++)
			if(i < rects.size()){
				//берем кубик из массива
				Rect rect = rects.get(i);
				
				//получаем его цвет
				int red = rect.getRed();
				int green = rect.getGreen();
				int blue = rect.getBlue();
				
				//устанавливаем цвет
				paint.setARGB(80, red, green, blue);
				
				//получаем координаты и размер
				int x = rect.getX();
				int y = rect.getY();
				int size = rect.getSize();
				
				//выводим на экран
				canvas.drawRect(
						x,
						y,
						x + size,
						y + size,
						paint
						);
			}
	}
	
	//поток
	public void run(){
		init();
		
		long startTime;
		long upgTime;
		long waitTime;
		
		//цикл запущен
		while(running){
			startTime = System.nanoTime();
			
			update();
			postInvalidate();
			
			upgTime = (System.nanoTime() - startTime)/1000000;
			waitTime = targetTime - upgTime;
			
			try{
				thread.sleep(waitTime);
			}catch(Exception ex){}
		}
	}
	
	//начальная инициализация
	public void init(){
		running = true;
		
		paint = new Paint();
		
		//начинаем с нулевого кубика
		step = 0;
		
		/*
		 * подстраиваем скорость и размер кубиков
		 * под различные разрешения экранов
		*/
		setRectSizeSpeed();
		
		//создаем массив для хранения кубиков
		rects = new ArrayList<Rect>();
		
		//разрешение на инициализацию наших кубиков
		isInitRect = false;
		
		//контроль автоматического обновления цикла
		updateStart = true;
	}
	
	//инициализация кубиков
	public void initRects(){
		if(!isInitRect){
			
			//создаем кубик с рандомными значениями
			for(int i = 1; i <= 30; i++){
				Rect rect = new Rect();
				
				int x = (int)(Math.random()*(canvasWidth-200));
				int y = -250; 
				
				rect.setX(x);
				rect.setY(y);
				
				rect.setSpeed(rectSpeed);
				rect.setSize(rectSize);
				
				rect.setDown(false);
				
				int red = (int)(Math.random()*255);
				int green = (int)(Math.random()*255);
				int blue = (int)(Math.random()*255);
				
				rect.setColor(red, green, blue);
				
				//помещаем кубик в массив
				rects.add(rect);
			}
			
			//инициализация окончена
			isInitRect = true;
		}
	}
	
	//автоматическое обновление
	public void update(){
		/*
		 * может случиться так, что при обращении
		 * движения кубиков, наш индекс текущего кубика
		 * станет отрицательным. Вернем его в начальное положение - 0
		 */
		if(step < 0)
			step = 0;
		
		//если разрешена автоматическое падение кубиков
		if(updateStart)
			if(rects.size() > 0 && step < rects.size()){
			
				//берем текущий кубик
				Rect rect = rects.get(step);
			
				//берем его координаты, размер и скорость
				int y = rect.getY();
				int size = rect.getSize();
				int speed = rect.getSpeed();
			
				boolean down = rect.isDown();
			
				/*
				 * проверяем, не упал ли наш кубик на другой кубик, и не лостиг ли 
				 * он нижней границы холста
				 */
				if(!down && y + size < canvasHeight){
				
					/*
					 * если падает самый первый кубик, то нам
					 * не с чем сравнивать его на столкновение.
					 * он просто падает, пока не достигнет низа холста
					 */ 
					if(step == 0){
						y += speed;
						rect.setY(y);
					}else{
						
						/*
						 * для проверки на столкновение текущего/падающего 
						 * кубика с теми, что уже упали
						 */
						rectCrush = false;
						
						/*
						 * пробегаемся по всем упавшим кубикам
						 */
						for(int i = 0; i < step && !rectCrush; i++){
							//берем упавший кубик
							Rect rectCheck = rects.get(i);
						
							//и проверяем на столкновение с текущим/падающим
							rectCrush = checkCollision(rect, rectCheck);
						}
					
						/*
						 * пока наш кубик не столкнулся с другим,
						 * он продолжает падать
						 */
						if(!rectCrush){
							y += speed;
							rect.setY(y);
						}else{
							/*
							 * если же наш текущий куби упал на другой,
							 * то останавливаем его и берем следующий падающий кубик 
							 */
							rect.setDown(true);
							step++;
						}
					}
				}else{
					/*
					 * если кубик достиг низа холста,
					 * то останавливаем его и берем следующий падающий кубик 
					 */
					rect.setDown(true);
					step++;
				}		
		  }
	}
	
	//русное обновление для падения кубиков
	public void setUpdateNextStep(int move){
		/*
		 * может случиться так, что при обращении
		 * движения кубиков, наш индекс текущего кубика
		 * станет отрицательным. Вернем его в начальное положение - 0
		 */
		if(step < 0)
			step = 0;
		
		/* 
		 * если быстро перемещать ползунок, то будут теряться
		 * значение ползунка, а это будет влиять на падение квадратов.
		 * 
		 *  поэтому, будем брать разницу между текущим положением ползунка
		 *  и предыдущим, и делить на условное число, чтобы на падение кубиков
		 *  влияло каждое перемещение ползунка
		 *  
		 *  move - условен, чтобы длина ползунка хватала для того, чтобы
		 *  мы могли полностью прокрутить падение/поднятие кубиков.
		 *  мы как бы ускоряем все, умножая процесс на определенное число
		 */
		for(int j = 1; j <= 5*move; j++)
			if(rects.size() > 0 && step < rects.size()){
				/*
				 * весь код идентичен обычному падению кубика,
				 * который приведен выше 
				 */
				Rect rect = rects.get(step);
			
				int y = rect.getY();
				int size = rect.getSize();
				int speed = rect.getSpeed();
			
				boolean down = rect.isDown();
			
				if(!down && y + size < canvasHeight){
				
					if(step == 0){
						y += speed;
						rect.setY(y);
					}else{
					
						rectCrush = false;
						
						for(int i = 0; i < step && !rectCrush; i++){
							Rect rectCheck = rects.get(i);
						
							rectCrush = checkCollision(rect, rectCheck);
						}
					
						if(!rectCrush){
							y += speed;
							rect.setY(y);
						}else{
							rect.setDown(true);
							step++;
						}
					}
				}else{
					rect.setDown(true);
					step++;
				}		
		}
		
	}
	
	//русное обновление для поднятия кубиков
	public void setUpdateBackStep(int move){
		/*
		 * может случиться так, что при падении
		 * кубиков, наш индекс текущего кубика
		 * станет больше, чем количество кубиков в самом массиве. 
		 * 
		 * Вернем его в позицию последнего кубика в массиве
		 */
		if(step >= 30)
			step = 29;

		//здесь мы тоже умножаем процесс на число, чтобы все ускорить
		for(int j = 1; j <= 5*move; j++)
			if(step >= 0 && step < rects.size()){
				/*
				 * берем последний падающий кубик.
				 * 
				 *  step как раз будет иметь текущий индекс падающего кубика
				 */
				Rect rect = rects.get(step);
			
				int y = rect.getY();
				int size = rect.getSize();
				int speed = rect.getSpeed();
			
				/*
				 * проверяем, пока кубик не поднялся за потолок холста -
				 * поднимаем его выше и выше, сразу установив, что он не упавший
				 */
				if(y + size > -50){
					y -= speed;
					rect.setY(y);
					rect.setDown(false);
				}else{
					//кубик поднялся за потолок холста, берем предыдущий упавший
					rect.setDown(false);
					step--;
				}
			}
	}
	
	//проверка на столкновение двух кубиков
	public boolean checkCollision(Rect rectUp, Rect rectDown){
		boolean crush;
		
		crush = false;
		
		//берем координаты и размер кубиков
		int rectUpX = rectUp.getX();
		int rectUpY = rectUp.getY();
		
		int rectDownX = rectDown.getX();
		int rectDownY = rectDown.getY();
		
		int rectSize = rectUp.getSize();
		
		//если верхний/падающий кубик упал на нижний
		if((rectUpX < rectDownX + rectSize) &&
		   (rectUpX + rectSize > rectDownX) && 
		   rectUpY + rectSize >= rectDownY)
				crush = true;
		
		return crush;
	}
	
	//подстройка размеров и скорости кубиков под разные разрешения экрана
	public void setRectSizeSpeed(){
		
		//получаем разрешение/плотность экрана
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		densityDpi = (int)(displayMetrics.density * 160f);
		
		//в зависимости от разрешения устанавливаем значения
		
		//4.0x
		if(600 <= densityDpi){
			rectSize = 170;
			rectSpeed = 34;
		}
		
		//3.5x
		if(520 <= densityDpi && densityDpi < 600){
			rectSize = 150;
			rectSpeed = 30;
		}
		
		//3.0x
		if(460 <= densityDpi && densityDpi < 520){
			rectSize = 126;
			rectSpeed = 25;
		}
		
		//2.6x
		if(380 <= densityDpi && densityDpi < 460){
			rectSize = 110;
			rectSpeed = 22;
		}
		
		//2.2x
		if(330 <= densityDpi && densityDpi < 380){
			rectSize = 95;
			rectSpeed = 19;
		}
		
		//2.0x
		if(270 <= densityDpi && densityDpi < 330){
			rectSize = 85;
			rectSpeed = 17;
		}
		
		//1.5x
		if(200 <= densityDpi && densityDpi < 270){
			rectSize = 65;
			rectSpeed = 13;
		}
		
		//1.0x
		if(140 <= densityDpi && densityDpi < 200){
			rectSize = 45;
			rectSpeed = 9;
		}
		
		//0.75x
		if(densityDpi < 140){
			rectSize = 30;
			rectSpeed = 7;
		}
	}
}
