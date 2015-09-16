package com.example.crasycat;

import java.util.HashMap;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class playground extends SurfaceView implements OnTouchListener{

	private static final int COL = 10;
	private static final int ROW = 10;
	private static int WIDETH = 100;
	private static final int LEFT = 1;
	private static final int LEFT_UP = 2;
	private static final int RIGHT_UP = 3;
	private static final int RIGHT = 4;
	private static final int RIGHT_DOWN = 5;
	private static final int LEFT_DOWN = 6;
	private Dot Matrix[][];
	private Dot cat;
	public playground(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(callback);
		Matrix = new Dot[COL+1][ROW+1];
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				Matrix[j][i] = new Dot(j, i);
			}
		}
		setOnTouchListener(this);
		initgame();
	}
	
	Callback callback = new Callback(){

		

		@Override
		public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			WIDETH = arg2/(COL+1);
			redraw();
		}
	

		@Override
		public void surfaceCreated(SurfaceHolder arg0) {
			// TODO Auto-generated method stub

			redraw();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void redraw(){
		Canvas c = getHolder().lockCanvas();
		c.drawColor(Color.BLACK);
		Paint paint = new Paint();
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		int offset = 0;
		for(int i = 0; i < ROW; i++){
			if(i%2 != 0){
				offset = WIDETH/2;
			}else{
				offset = 0;
			}
			for(int j = 0; j < COL; j++){
				Dot one = getDote(j, i);
				switch(one.getStatus()){
				case Dot.STATUS_ON:
					paint.setColor(0xFFFFAA00);
					break;
				case Dot.STATUS_OFF:
					paint.setColor(0xFFEEEEEE);
					break;
				case Dot.STATUS_IN:
					paint.setColor(0xFFFF0000);
					break;
				default:
					break;
				}
				c.drawOval(new RectF(one.getX()*WIDETH + offset, one.getY()*WIDETH, (one.getX()+1)*WIDETH + offset, (one.getY()+1)*WIDETH), paint);
			}
		}
		getHolder().unlockCanvasAndPost(c);
	}
	
	private Dot getDote(int x, int y){
		return Matrix[x][y];
	}
	
	private void initgame(){
		for(int i = 0; i < COL; i++){
			for(int j = 0; j < ROW; j++){
				Matrix[i][j].setStatus(Dot.STATUS_OFF);
			}
		}
		for(int i = 0; i<10;){
			int x = (int) ((Math.random()*1000)%COL);
			int y = (int) ((Math.random()*1000)%ROW);
			Dot dot = getDote(x, y);
			if(dot.getStatus() == Dot.STATUS_OFF){
				dot.setStatus(Dot.STATUS_ON);
				i++;
			}
		}
		cat = getDote(5, 5);
		cat.setStatus(Dot.STATUS_IN);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent e) {
		// TODO Auto-generated method stub
		if(e.getAction() == MotionEvent.ACTION_UP){
			int y = (int) (e.getY()/WIDETH);
			int x;
			if(y/2 != 0){
				x = (int) ((e.getX()-WIDETH/2)/WIDETH);
			}else{
				x = (int) (e.getX()/WIDETH);
			}
			if(x + 1 > COL || y + 1 > ROW){
				initgame();
			}
			else if(getDote(x, y).getStatus() == Dot.STATUS_OFF){
				getDote(x, y).setStatus(Dot.STATUS_ON);
				move();
			}
			redraw();
		}
		return true;
	}
	
	
	private void move(){
		Vector<Dot> avilable = new Vector<Dot>();
		Vector<Dot> positive = new Vector<Dot>();
		HashMap<Dot, Integer> al = new HashMap<Dot, Integer>();
		if(isAtadge(cat)){
			lose();
			return;
			}
		for(int i = 1; i < 7; i++){
			if(getNeighbor(cat, i).getStatus() == Dot.STATUS_OFF){
				avilable.add(getNeighbor(cat, i));
				al.put(getNeighbor(cat, i), i);
				if(getDistance(getNeighbor(cat, i), i) > 0){
					positive.add(getNeighbor(cat, i));
				}
			}
			
		}
		if(avilable.size() == 0){
			win();
		}else if(avilable.size() == 1){
			moveTo(avilable.get(0));
		}
		else{
			//HashMap<Integer, Integer> D_D = new HashMap<Integer, Integer>();
			Dot best = null;
			if(positive.size() == 0){
				int max = 0;
				
				for(int i = 0; i < avilable.size(); i++){
					int k = getDistance(avilable.get(i), al.get(avilable.get(i)));
					Log.d("zhengk1", "k:"+k);
					if(k < max){
						max = k;
						//D_D.put(max, i);

						best = avilable.get(i);
					}
				}
				//int direction = D_D.get(max);
				Log.d("zhengk1", ""+best);
				if(best != null)
				moveTo(best);
				//D_D.clear();
			}else{
				int min = 999;
				for(int i = 0; i < positive.size(); i++){
					int k = getDistance(positive.get(i), al.get(positive.get(i)));
					if(false){
					//	moveTo(i);
					}
					else{
						if((k < min)){
							min = k;
							//D_D.put(min, i);
							//Log.d("zhengk",""+D_D);
							best = positive.get(i);
						}
					}
				}
				//int direction = D_D.get(min);
				Log.d("zhengk2", ""+best);
				moveTo(best);
				//D_D.clear();
			}
		}
	}
	
	private void moveTo(Dot one){
//		Dot next = getNeighbor(cat, direct);

			one.setStatus(Dot.STATUS_IN);
			getDote(cat.getX(), cat.getY()).setStatus(Dot.STATUS_OFF);
			cat = getDote(one.getX(), one.getY());


	}
	private void lose(){
		Toast.makeText(getContext(), "you lose", Toast.LENGTH_LONG).show();
		initgame();
	}
	private void win(){
		Toast.makeText(getContext(), "you win", Toast.LENGTH_LONG).show();
	}
	private Dot getNeighbor(Dot one, int i){
		int x = one.getX();
		int y = one.getY();
		Dot next = null;
		switch(i){
		case LEFT:
			next = getDote(x-1, y);
			break;
		case LEFT_UP:
			if(y%2 == 0)
				next = getDote(x-1, y-1);
			else
				next = getDote(x, y-1);
			break;
		case RIGHT_UP:
			if(y%2 != 0)
				next = getDote(x+1, y-1);
			else
				next = getDote(x, y-1);
			break;
		case RIGHT:
			next = getDote(x+1, y);
			break;
		case RIGHT_DOWN:
			if(y%2 != 0)
				next = getDote(x+1, y+1);
			else
				next = getDote(x, y+1);
			break;
		case LEFT_DOWN:
			if(y%2 != 0)
				next = getDote(x, y+1);
			else
				next = getDote(x-1, y+1);
			break;
		}
		return next;
	}
	
	private boolean isAtadge(Dot one){
		int x = one.getX();
		int y = one.getY();
		if((x == COL-1) || (y == ROW-1) || (x == 0) || (y == 0)){
			return true;
		}else{
			return false;
		}
	}
	
	private int getDistance(Dot one, int direct){
		int distance = 0;
		Dot dot_now = one;
		Dot next;
		if(isAtadge(dot_now)){
			return 1;
		}
		while(true){
			next = getNeighbor(dot_now, direct);
			if(next.getStatus() == Dot.STATUS_ON){
				return distance*-1;
			}
			if(isAtadge(next)){
				distance++;
				return distance;
			}
			distance++;
			dot_now = next;
		}		
	}

}
