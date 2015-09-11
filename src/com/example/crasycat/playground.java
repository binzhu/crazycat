package com.example.crasycat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class playground extends SurfaceView{

	public playground(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(callback);
	}
	
	Callback callback = new Callback(){

		

		@Override
		public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
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
		c.drawColor(Color.CYAN);
		getHolder().unlockCanvasAndPost(c);
	}
	
	private Dot getDote(int x, int y){
		Dot dot = new Dot(x, y);
		return dot;
	}

}
