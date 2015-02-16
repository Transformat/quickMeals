package com.bitekite;

/**
 * Picasso's(ImageLoaderLibrary) class to crop bitmap in circular shape
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.squareup.picasso.Transformation;

public class TraingleTransform implements Transformation {
	 Context context;
	private Bitmap result;
	
	public TraingleTransform(Context c)
	{
		context=c;
	}
	
	@Override
	public Bitmap transform(Bitmap source) {
		try{
		Log.d("here", "here");
		 Bitmap mask = BitmapFactory.decodeResource(context.getResources(),R.drawable.mask_profile);
		  result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
		 Canvas mCanvas = new Canvas(result);
		 Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		 paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		 mCanvas.drawBitmap(source, 0, 0, null);
		// mCanvas.drawBitmap(mask, 0, 0, paint);
		 paint.setXfermode(null);
		
		 
		 
		 
		
		
		 
		 
		}
		catch(Exception e)
	
		{
			 Log.d("hereeeeee", "here hereeeee");
		}
		return result;
	}

	public void setContext(Context c)
	{
		
	}
	@Override
	public String key() {
		return "circle";
	}
}