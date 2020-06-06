//Класс для фрагмента

package com.example.myapplication_2020_24.secondpage;

import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.support.v4.app.Fragment;

import com.example.myapplication_2020_24.R;

public class SecondPageFirstFragment extends Fragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saves){
		View view = inflater.inflate(R.layout.secondpage_first_fragment, container, false);
		return view;
	}
}
