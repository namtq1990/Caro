package com.example.caro;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Board extends Activity implements OnClickListener, OnKeyListener
{
	private static final int X = 11;
	private static final int Y = 11;
	private CButton[][] BOARD;
	private static final String Tag = "Caro";
	private ArrayList<Point> nextMove;
	private ArrayList<Point> result;
	private boolean finish;
	private Point start[], end[];
	private int count[];
	private int dangerous[];
	private Random random;
	private int action;
	private ArrayList<CaroLine> listLineTarget;
	private boolean testMode;
	
	public static final int LINE_90 = 0;
	public static final int LINE_0 = 1;
	public static final int LINE_135 = 2;
	public static final int LINE_45 = 3;
	
	public static final int XXXX_ = 1;
	public static final int _XXXX = 2;
	public static final int X_XXX = 3;
	public static final int XXX_X = 4;
	public static final int _XX_XX = 5;
	public static final int XX_XX_ = 6;
	public static final int _XXX_ = 7;
	public static final int X_XX = 8;
	public static final int XX_X = 9;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.d(Tag, "Started");
		BOARD = new CButton[X][Y];
		nextMove = new ArrayList<Point>();
		result = new ArrayList<Point>();
		start = new Point[4];
		end = new Point[4];
		count = new int[4];
		dangerous = new int[4];
		random = new Random();
		listLineTarget = new ArrayList<CaroLine>();
		
		for (int i = 0;i < 4;i++)
		{
			start[i] = new Point();
			end[i] = new Point();
		}
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.board);
		
		TableLayout board = (TableLayout)findViewById(R.id.board);
		TableRow _row = new TableRow(this);
		for (int i = 0;i < X;i++)
		{
			_row = new TableRow(this);
			
			for (int j = 0;j < Y;j++)
			{
				BOARD[i][j] = new CButton(this);
				BOARD[i][j].setMinimumWidth(70);
				_row.addView(BOARD[i][j]);
				BOARD[i][j].setOnClickListener(this);
				BOARD[i][j].setId(i * Y + j);
			}
			board.addView(_row);
		}
	}
	public void onClick(View v)
	{
		if (v.getId() < 0 || v.getId() >= X * Y) return;
		else
		{
			if (((CButton)v).getValue() != 0) return;
			int x = v.getId() / Y;
			int y = v.getId() % Y;
			((CButton)v).setValue(1);
			modNextMove(x, y);
			alarm(x, y);
			if (finish)
			{
				finish();
			}
			//Log.e(Tag, "" + result.size());
			Point p = result.get(random.nextInt(result.size()));
			BOARD[p.x][p.y].setValue(2);
			action = 0;
			
			modNextMove(p.x, p.y);
		}
	}
	public boolean isOnBoard(int x, int y)
	{
		if (x < 0 || x >= X || y < 0 || y >= Y) return false;
		return true;
	}
	public void modNextMove(int x, int y)
	{
		if (true)
		{
			int i = isOnListPoint(x, y);
			if (i != -1) nextMove.remove(i);
			else Log.d(Tag, "Point is not in the list");
		}
		for (int i = -1;i < 2;i++)
			for (int j = -1;j < 2;j++)
			{
				if (!isOnBoard(x + i, y + j)) continue; 
				if (BOARD[x + i][y + j].getValue() == 0 && 
						isOnListPoint(x + i, y + j) == -1)
					nextMove.add(new Point(x + i, y + j));
			}
	}
 	public int isOnListPoint(int x, int y)
	{
		for (int i = 0;i < nextMove.size();i++)
			if (nextMove.get(i).x == x && nextMove.get(i).y == y) return i;
		return -1;
	}
	public void saveState()
	{
		
	}
	public void restoreState()
	{
		
	}
	protected CButton getNextPointOnLine(int x, int y, int i, int jump)
	{
		switch(i)
		{
		case LINE_90:
			x += jump;
			break;
		case LINE_0:
			y += jump;
			break;
		case LINE_135:
			x += jump;
			y += jump;
			break;
		case LINE_45:
			x -= jump;
			y += jump;
			break;
		}
		if (isOnBoard(x, y)) return BOARD[x][y];
		else return null;
	}
	protected int countOnLine(int x, int y, int index, Point start, Point end)
	{
		int count = 1;
		int value = 0;
		CButton p;
		
		if (isOnBoard(x, y)) value = BOARD[x][y].getValue();
		for (int i = 1;;i++)
		{
			p = getNextPointOnLine(x, y, index, i);
			if (p == null) break;
			if (p.getValue() != value)
			{
				if (end != null)
				{
					end.x = p.getId() / Y;
					end.y = p.getId() % Y;
				}
				break;
			}
			else count++;
		}
		for (int i = -1;;i--)
		{
			p = getNextPointOnLine(x, y, index, i);
			if (p == null) break;
			if (p.getValue() != value)
			{
				if (start != null)
				{
					start.x = p.getId() / Y;
					start.y = p.getId() % Y;
				}
				break;
			}
			else count++;
		}
		return count;
	}/*
	protected CaroLine getCaroline(Point start, Point end)
	{
		
	}*/
	protected void setDangerous(int x, int y)
	{
		for (int i = 0;i < 4;i++)
		{
			switch(count[i])
			{
			
			}
		}
	}
	/*protected void setDangerous(int i, boolean changeAction)
	{
		switch(count[i])
		{
		case 1:
			if (BOARD[start[i].x][start[i].y].getValue() == 2 &&
			BOARD[end[i].x][end[i].y].getValue() == 2)
			{
				dangerous[i] = 0;
				if (changeAction) action = 0;
				return;
			}
			
			Point oldStart = start[i];
			Point oldEnd = end[i];
			CButton p;
			
			if (BOARD[start[i].x][start[i].y].getValue() == 0)
			{
				p = getNextPointOnLine(start[i].x, start[i].y, i, -1);
				if (p == null) Log.e(Tag, "null");
				if (p.getValue() == 1)
				{
					count[i] = countOnLine(p.getId() / Y, p.getId() % Y, i, start[i], end[i]);
					if (count[i] == 1);
					else{
						setDangerous(i, true);
						if (dangerous[i] == 5) return;
					}
					start[i] = oldStart;
					end[i] = oldEnd;
				}
			}
			if (BOARD[end[i].x][end[i].y].getValue() == 0)
			{
				p = getNextPointOnLine(end[i].x, end[i].y, i, 1);
				if (p.getValue() == 1)
				{
					count[i] = countOnLine(p.getId() / Y, p.getId() % Y, i, start[i], end[i]);
					if (count[i] == 1);
					else{
						setDangerous(i, true);
						if (dangerous[i] == 5) return;
					}
					start[i] = oldStart;
					end[i] = oldEnd;
				}
			}
			break;
		case 2:
			if (BOARD[start[i].x][start[i].y].getValue() == 0)
			{
				if (getNextPointOnLine(start[i].x, start[i].y, i, -1).getValue() == 1)
				{
					if (getNextPointOnLine(start[i].x, start[i].y, i, -2).getValue() == 1)
					{
						dangerous[i] = 5;
						if (changeAction) action = _XX_XX;
						return;
					}
					if (getNextPointOnLine(start[i].x, start[i].y, i, -2).getValue() == 2)
					{
						if (BOARD[end[i].x][end[i].y].getValue() == 2)
						{
							dangerous[i] = 0;
							if (changeAction) action = 0;
							return;
						}
						else{
							dangerous[i] = 4;
							if (changeAction) action = X_XX;return;
						}
					}
					else
					{
						if (BOARD[end[i].x][end[i].y].getValue() == 0)
						{
							dangerous[i] = 5;
							if(changeAction) action = X_XX;return;
						}
						else{
							dangerous[i] = 4;
							if (changeAction) action = X_XX;return;
						}
					}
				}
			}
			if (BOARD[end[i].x][end[i].y].getValue() == 0)
			{
				if (getNextPointOnLine(end[i].x, end[i].y, i, 1).getValue() == 1)
				{
					if (getNextPointOnLine(end[i].x, end[i].y, i, 2).getValue() == 1)
					{
						dangerous[i] = 5;
						if (changeAction) action = XX_XX_;
						return;
					}
					if (getNextPointOnLine(end[i].x, end[i].y, i, 2).getValue() == 2)
					{
						if (BOARD[start[i].x][start[i].y].getValue() == 2)
						{
							dangerous[i] = 0;
							if (changeAction) action = 0;
							return;
						}
						else{
							dangerous[i] = 4;
							if (changeAction) action = X_XX;return;
						}
					}
					else
					{
						if (BOARD[start[i].x][start[i].y].getValue() == 0)
						{
							dangerous[i] = 5;
							if(changeAction) action = X_XX;return;
						}
						else{
							dangerous[i] = 4;
							if (changeAction) action = X_XX;return;
						}
					}
				}
			}
			break;
		case 3:
			if (BOARD[start[i].x][start[i].y].getValue() == 0)
			{
				if (getNextPointOnLine(start[i].x, start[i].y, i, -1).getValue() == 1)
				{
					dangerous[i] = 5;
					if (changeAction) action = X_XXX;
					return;
				}
				if (BOARD[end[i].x][end[i].y].getValue() == 0)
				{
					if (getNextPointOnLine(end[i].x, end[i].y, i, 1).getValue() == 1)
					{
						dangerous[i] = 5;
						if (changeAction) action = XXX_X;
						return;
					}
					else
					{
						if (changeAction) action = _XXX_;
						dangerous[i] = 5;
					}
				}
				else
				{
					dangerous[i] = 4;
					if (changeAction) action = _XXX_;
				}
			}
			else
			{
				if (BOARD[end[i].x][end[i].y].getValue() != 0)
				{
					dangerous[i] = 0;
					if (changeAction) action = 0;
					return;
				}
				else
				{
					if (getNextPointOnLine(end[i].x, end[i].y, i, 1).getValue() == 1)
					{
						dangerous[i] = 5;
						if (changeAction) action = XXX_X;
						return;
					}
					else
					{
						dangerous[i] = 4;
						if (changeAction) action = _XXX_;
						return;
					}
				}
			}
			break;
		case 4:
			if (BOARD[start[i].x][start[i].y].getValue() != 0)
			{
				if (BOARD[end[i].x][end[i].y].getValue() != 0)
				{
					dangerous[i] = 0;
					if (changeAction) action = 0;
				}
				else
				{
					dangerous[i] = 5;
					if (changeAction) action = XXXX_;
				}
			}
			else if (BOARD[end[i].x][end[i].y].getValue() != 0)
			{
				dangerous[i] = 5;
				if (changeAction) action = _XXXX;
			}
			break;
			default:
				dangerous[i] = 0;
		}
	}
		*/
	protected void action(int i)
	{
		switch(action)
		{
		case _XXXX:
			result.clear();
			result.add(new Point(start[i].x, start[i].y));
			break;
		case XXXX_:
			result.clear();
			result.add(new Point(end[i].x, end[i].y));
			break;
		case X_XXX:
			result.clear();
			result.add(new Point(start[i].x, start[i].y));
			break;
		case XXX_X:
			result.clear();
			result.add(new Point(end[i].x, end[i].y));
			break;
		case _XX_XX:
			result.clear();
			result.add(new Point(start[i].x, start[i].y));
			break;
		case XX_XX_:
			result.clear();
			result.add(new Point(end[i].x, end[i].y));
			break;
		case X_XX:
			if (dangerous[i] == 5)
			{
				result.clear();
				result.add(new Point(end[i].x, end[i].y));
				result.add(new Point(start[i].x, start[i].y));
				CButton p = getNextPointOnLine(start[i].x, start[i].y, i, -2);
				result.add(new Point(p.getId() / Y, p.getId() % Y));
			}
			break;
		case XX_X:
			if (dangerous[i] == 5)
			{
				result.clear();
				result.add(new Point(end[i].x, end[i].y));
				result.add(new Point(start[i].x, start[i].y));
				CButton p = getNextPointOnLine(start[i].x, start[i].y, i, 2);
				result.add(new Point(p.getId() / Y, p.getId() % Y));
			}
			break;
		case _XXX_:
			if (dangerous[i] == 5)
			{
				result.clear();
				result.add(new Point(start[i].x, start[i].y));
				result.add(new Point(end[i].x, end[i].y));
				return;
			}
			//Xu ly khi dangerous == 4
			break;
		}
	}
	protected void alarm(int x, int y)
	{
		for (int i = 0;i < 4;i++)
		{
			count[i] = countOnLine(x, y, i, start[i], end[i]);
			if (count[i] >= 5)
			{
				finish = true;
				return;
			}
			if (count[i] == 4)
			{
				if (BOARD[start[i].x][start[i].y].getValue() == 0
						&& BOARD[end[i].x][end[i].y].getValue() == 0)
				{
					finish = true;
					return;
				}
			}
		}
		result.clear();
		for (int i = 0;i < nextMove.size();i++)	result.add(nextMove.get(i));
		
		int index = 0;
		int oldAction = 0;
		for (int i = 0;i < 4;i++)
		{
			//setDangerous(i, true);
			if (dangerous[i] < dangerous[index]) action = oldAction;
			else 
			{
				index = i;
				oldAction = action;
			}
		}
		Log.e(Tag, "index: " + index + " dangerous: " + dangerous[index] + " action: " + action);
		//Xu ly hanh dong tuy theo action
		action(index);
	}
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) 
	{
		if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
			testMode = !testMode;
		return false;
	}
}
