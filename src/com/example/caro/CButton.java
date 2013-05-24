package com.example.caro;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

public class CButton extends Button
{
	private int value = 0;
	private String Text[] = {"", "X", "Y"};
	
	public CButton(Context context)
	{
		super(context);
	}
	public CButton(Context context, AttributeSet attr)
	{
		super(context, attr);
	}
	public CButton(Context context, AttributeSet attr, int defStyle)
	{
		super(context, attr, defStyle);
	}
	public int getValue()
	{
		return value;
	}
	public void setValue(int value)
	{
		this.value = value;
	}
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		this.setText(Text[value]);
	}
}
