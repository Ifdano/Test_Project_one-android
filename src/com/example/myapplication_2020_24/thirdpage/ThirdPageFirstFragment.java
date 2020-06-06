//Класс с фрагментом

package com.example.myapplication_2020_24.thirdpage;

import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.support.v4.app.Fragment;

import com.example.myapplication_2020_24.R;

public class ThirdPageFirstFragment extends Fragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saves){
		View view = inflater.inflate(R.layout.thirdpage_first_fragment, container, false);	
		return view;
	}
}
