package com.bitekite.utils;
import com.bitekite.CheckOutCustomer;
import com.bitekite.R;
import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.app.Dialog;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class DialogPopup {

	public DialogPopup() {

	}

	public void alertPopup(Activity activity, String title, String message) {
		try {
//			if ("".equalsIgnoreCase(title)) {
//				title = "Alert";
//			}
			if(title.toString().equals("1"))
			{
				CheckOutCustomer.ConfirmAddressFlag=1;
				title = "Alert";
			}
			else
			{
			title = "OOPS!";
			}
			final Dialog dialog = new Dialog(activity,
					android.R.style.Theme_Translucent_NoTitleBar);
			dialog.getWindow().getAttributes().windowAnimations = R.style.Animations_progressFadeInOut;
			dialog.setContentView(R.layout.custom_message_dialog);

			FrameLayout frameLayout = (FrameLayout) dialog
					.findViewById(R.id.rv);
			new ASSL(activity, frameLayout, 1134, 720, true);

			WindowManager.LayoutParams layoutParams = dialog.getWindow()
					.getAttributes();
			layoutParams.dimAmount = 0.6f;
			dialog.getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);

			TextView textHead = (TextView) dialog.findViewById(R.id.textHead);
			textHead.setTypeface(Data.omnes_Bold(activity));
			TextView textMessage = (TextView) dialog
					.findViewById(R.id.textMessage);
			textMessage.setTypeface(Data.omnes_Regular(activity));

			textMessage.setMovementMethod(new ScrollingMovementMethod());
			textMessage.setMaxHeight((int) (800.0f * ASSL.Yscale()));

			textHead.setText(title);
			textMessage.setText(message);
			textHead.setVisibility(View.GONE);

			Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
			btnOk.setTypeface(Data.omnes_Regular(activity));

			btnOk.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					dialog.dismiss();
				}

			});

			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
