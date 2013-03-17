/**
 * 
 */
package com.test.geo.optimal.threads;

import android.graphics.Color;
import android.view.View;

/**
 * @author ACPS
 *
 */
public class OnClicTableRow implements Runnable{

	private View v;
	private Thread hilo;
	
	public OnClicTableRow(View v){
		this.v = v;
		hilo = new Thread(this);
	}
	
	public void start(){
		this.hilo.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.v.setBackgroundColor(Color.BLUE);
		try{
			Thread.sleep(1000);
			this.v.setBackgroundColor(Color.WHITE);
		}catch (InterruptedException e) {
			// TODO: handle exception
		}finally{
			this.v.setBackgroundColor(Color.WHITE);
		}
		
		
	}

}
