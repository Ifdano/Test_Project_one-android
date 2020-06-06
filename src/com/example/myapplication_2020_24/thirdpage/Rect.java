//Класс дл¤ создания кубиков

package com.example.myapplication_2020_24.thirdpage;

public class Rect {

	//размеры, положение и прочие характеристики
	private int x;
	private int  y;
	
	private int size;
	private int speed;
	
	private int red;
	private int green;
	private int blue;
	
	//достиг дна и остановился
	private boolean down;
	
	//сеттеры для установки значений
	public void setX(int x){ this.x = x; }
	public void setY(int y){ this.y = y; }
	
	public void setSize(int size){ this.size = size; }
	public void setSpeed(int speed){ this.speed = speed; }
	
	public void setDown(boolean b){ down = b; }
	
	public void setColor(int red, int green, int blue){
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	//геттеры для получени¤ значений
	public int getX(){ return x; }
	public int getY(){ return y; }
	
	public int getSize(){ return size; }
	public int getSpeed(){ return speed; }
	
	public boolean isDown(){ return down; }
	
	public int getRed(){ return red; }
	public int getGreen(){ return green; }
	public int getBlue(){ return blue; }
}
