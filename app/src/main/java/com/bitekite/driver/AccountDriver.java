package com.bitekite.driver;
import java.io.File;
import java.io.FileNotFoundException;

import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.AccountUser;
import com.bitekite.DriverLocationFinder;
import com.bitekite.GCMIntentService;
import com.bitekite.MainActivity;
import com.bitekite.R;
import com.bitekite.utils.CircleTransform;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.google.analytics.tracking.android.EasyTracker;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

public class AccountDriver extends Activity implements PushUpdater, ImageChooserListener {

	RelativeLayout relativeLayoutMenu, relativeLayoutHeader,
			relativeLayoutMain;

	RelativeLayout relativeLayoutOrder, relativeLayoutDeliveredOrder,
			relativeLayoutPerformance, relativeLayoutAccount,
			relativeLayoutSupport, relativeLayoutEmergency,
			relativeLayoutCheckout, relativeLayoutLogout;

	LinearLayout linearLayoutMenuView;
	TextView textViewOrder, textViewDeliveredOrder, textViewPerformance,
			textViewAccount, textViewSupport, textViewEmergency,
			textViewExtraTextForScroll, textViewCheckout, textViewLogout,
			textViewCancel;

	TextView textViewEdit,textViewUserName,textViewEmail,textViewPhoneNumber;
	EditText editTextUserName, editTextPhoneNumber, editTextEmail;
	Button buttonChangePassword;
	ImageView imageViewProfile;
	String stringUserName, stringPhoneNumber;
	private int takePicture = 1;
	Uri imageUri;
	String picturePathString;
	File photo;
	File file;
	String userImage = "0";
	ProgressBar progressBar;
	
	Dialog popup;
	Button buttonCamera,buttonGallery,buttonOK;
	TextView textViewTitle;
	ImageChooserManager imageChooserManager,imageChooserManager2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_driver);
		new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);

		imageViewProfile = (ImageView) findViewById(R.id.ProfileImg);

		textViewExtraTextForScroll = (TextView) findViewById(R.id.extraTextForScroll);
		textViewEdit = (TextView) findViewById(R.id.EDIT);
		buttonChangePassword = (Button) findViewById(R.id.ChangePassword);

		editTextUserName = (EditText) findViewById(R.id.UserNameEdt);
		editTextPhoneNumber = (EditText) findViewById(R.id.PhoneEdt);
		editTextEmail = (EditText) findViewById(R.id.EmailEdt);

		relativeLayoutMenu = (RelativeLayout) findViewById(R.id.menuBtnLayout);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		relativeLayoutHeader = (RelativeLayout) findViewById(R.id.header);
		relativeLayoutMain = (RelativeLayout) findViewById(R.id.mainlayout);
		relativeLayoutLogout = (RelativeLayout) findViewById(R.id.LogoutLayout);
		relativeLayoutOrder = (RelativeLayout) findViewById(R.id.TodayOrderLayout);
		relativeLayoutDeliveredOrder = (RelativeLayout) findViewById(R.id.DeliveredOrderLayout);
		relativeLayoutPerformance = (RelativeLayout) findViewById(R.id.PerformanceLayout);
		relativeLayoutAccount = (RelativeLayout) findViewById(R.id.AccountLayout);
		relativeLayoutSupport = (RelativeLayout) findViewById(R.id.SupportLayout);
		relativeLayoutEmergency = (RelativeLayout) findViewById(R.id.EmergencyLayout);
		linearLayoutMenuView = (LinearLayout) findViewById(R.id.menuView);
		relativeLayoutCheckout = (RelativeLayout) findViewById(R.id.CheckOutLayout);

		textViewCheckout = (TextView) findViewById(R.id.CheckOutTxt);
		textViewCancel = (TextView) findViewById(R.id.CancelTxt);
		textViewOrder = (TextView) findViewById(R.id.TodayOrderTxt);
		textViewDeliveredOrder = (TextView) findViewById(R.id.DeliveredOrderTxt);
		textViewPerformance = (TextView) findViewById(R.id.PerformanceTxt);
		textViewAccount = (TextView) findViewById(R.id.AccountTxt);
		textViewSupport = (TextView) findViewById(R.id.SupportTxt);
		textViewEmergency = (TextView) findViewById(R.id.EmergencyTxt);
		textViewLogout = (TextView) findViewById(R.id.LogoutTxt);
		
		textViewUserName = (TextView) findViewById(R.id.userNameTxt);
		textViewEmail = (TextView) findViewById(R.id.EmailTxt);
		textViewPhoneNumber = (TextView) findViewById(R.id.PhoneTxt);
		
		
		textViewEdit.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewCancel.setTypeface(Data.bariol_regular(getApplicationContext()));
		buttonChangePassword.setTypeface(Data.bariol_regular(getApplicationContext()));
		
		textViewUserName.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewEmail.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewPhoneNumber.setTypeface(Data.bariol_bold(getApplicationContext()));
		
		editTextUserName.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextPhoneNumber.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextEmail.setTypeface(Data.bariol_regular(getApplicationContext()));
		
		textViewLogout.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewCheckout.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewOrder.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewDeliveredOrder.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewPerformance.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewAccount.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewSupport.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewEmergency.setTypeface(Data.bariol_bold(getApplicationContext()));

		if (OrderScreen.orderList.size() > 0) {
			relativeLayoutLogout.setVisibility(View.GONE);
			relativeLayoutCheckout.setVisibility(View.GONE);
		}
		else{
		if (Data.getInfoOf(getApplicationContext(), Data.CHECKINVALUE)
				.equalsIgnoreCase("0")) {
			relativeLayoutLogout.setVisibility(View.VISIBLE);
			relativeLayoutCheckout.setVisibility(View.GONE);
		} else {
			relativeLayoutLogout.setVisibility(View.GONE);
			relativeLayoutCheckout.setVisibility(View.VISIBLE);
		}
		}
		

		
		Picasso.with(this)
				.load(Data.getInfoOf(getApplicationContext(),
						Data.USER_IMAGE_URL)).placeholder(R.drawable.user_def).transform(new CircleTransform()).skipMemoryCache()
				.into(imageViewProfile);

		editTextEmail.setText(Data.getInfoOf(getApplicationContext(),
				Data.EMAIL));
		editTextPhoneNumber.setText(Data.getInfoOf(getApplicationContext(),
				Data.PH_NO));
		editTextUserName.setText(Data.getInfoOf(getApplicationContext(),
				Data.USER_NAME));

		imageChooserManager = new ImageChooserManager(AccountDriver.this,ChooserType.REQUEST_PICK_PICTURE);
		imageChooserManager.setImageChooserListener(this);
		
		imageChooserManager2 = new ImageChooserManager(AccountDriver.this,ChooserType.REQUEST_CAPTURE_PICTURE);
		imageChooserManager2.setImageChooserListener(this);
		
		
		
		
		imageViewProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(textViewEdit.getText().toString().equals("Save"))
				{
////					Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
////					 photo = new File(
////							Environment.getExternalStorageDirectory(), "BitKite"
////									+ ".jpg");
////					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
////					imageUri = Uri.fromFile(photo);
////					picturePathString = photo.toString();
////					Log.e("imageUri", "===" +picturePathString);
////					startActivityForResult(intent, takePicture);
//					
//					
//					popup = new Dialog(AccountDriver.this,
//							android.R.style.Theme_Translucent_NoTitleBar);
//
//					popup.setContentView(R.layout.custom_image_dialog);
//
//					FrameLayout fl = (FrameLayout) popup
//							.findViewById(R.id.rv);
//
//					buttonCamera = (Button) popup.findViewById(R.id.btnCamera);
//					buttonGallery = (Button) popup.findViewById(R.id.btnGallery);							
//					textViewTitle = (TextView) popup
//							.findViewById(R.id.textHead);
//					buttonCamera.setTypeface(Data
//							.bariol_regular(getApplicationContext()));
//					buttonGallery.setTypeface(Data.bariol_regular(getApplicationContext()));				
//					textViewTitle.setTypeface(Data.bariol_regular(getApplicationContext()));
//					
//					new ASSL(AccountDriver.this, fl, 1134, 720,false);
//					
//					fl.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							if (popup.isShowing()) {
//								popup.dismiss();
//								// overridePendingTransition(R.anim.slide_in_bottom,
//								// R.anim.slide_out_to_top);
//							}
//						}
//					});
//					
//				buttonCamera.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						try {
//							imageChooserManager2.choose();
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						popup.dismiss();
//					}
//				});
//				
//				buttonGallery.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
						try {
							imageChooserManager.choose();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						popup.dismiss();
//					}
//				});
//
//				popup.show();
//					
//					
				}
				
			}
		});
		
		  InputFilter filter = new InputFilter() {

	            @Override
	            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
	                if (source.length() > 0) {

	                    if (!Character.isDigit(source.charAt(0)))
	                    {
	                    	Log.e("start","start");
	                        return "";
	                    }
	                    	
	                    else {
	                        if (dstart == 3) {
	                            return source + ") ";
	                        } else if (dstart == 0) {
	                            return "(" + source;
	                        } else if ((dstart == 5) || (dstart == 9))
	                            return "-" + source;
	                        else if (dstart >= 14)
	                            return "";
	                    }

	                } else {

	                }
	                
	                return null;

	            }
	        };

	        editTextPhoneNumber.setFilters(new InputFilter[] { filter });


		textViewEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (textViewEdit.getText().toString().equals("Edit")) {
					textViewEdit.setText("Save");
					editTextUserName.setEnabled(true);
					editTextPhoneNumber.setEnabled(true);
					textViewCancel.setVisibility(View.VISIBLE);
					relativeLayoutMenu.setVisibility(View.INVISIBLE);
					editTextEmail.setTextColor(Color.parseColor("#8e8e8e"));
//					editTextUserName.setText("");
//					editTextPhoneNumber.setText("");

				} else {

					stringUserName = editTextUserName.getText().toString()
							.trim();
					stringPhoneNumber = editTextPhoneNumber.getText()
							.toString().trim();

					if (stringUserName.isEmpty()) {
						new DialogPopup().alertPopup(AccountDriver.this, "",
								"Enter User Name.");
					} else {
						if (stringPhoneNumber.isEmpty()) {
							new DialogPopup().alertPopup(AccountDriver.this,
									"", "Enter Phone Number.");

						} else {
							
							if (stringPhoneNumber.trim()
									.length() < 14) {
								new DialogPopup().alertPopup(
										AccountDriver.this, "",
										"Phone number less than 10 digits");
							} else {
							
							if (InternetCheck.getInstance(getApplicationContext()).isOnline(
									getApplicationContext())) {
								changeProfileInfoServerCall();
							} else {
								new DialogPopup().alertPopup(AccountDriver.this,
										"", "Check your internet connection");
							}
							}
							
						}
					}

//					textViewEdit.setText("Edit");
//					editTextUserName.setEnabled(false);
//					editTextPhoneNumber.setEnabled(false);
//					textViewCancel.setVisibility(View.INVISIBLE);
//					relativeLayoutMenu.setVisibility(View.VISIBLE);
//					editTextEmail.setTextColor(Color.parseColor("#202020"));
				}

			}
		});

		textViewCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				textViewEdit.setText("Edit");
				editTextUserName.setEnabled(false);
				editTextPhoneNumber.setEnabled(false);
				textViewCancel.setVisibility(View.INVISIBLE);
				relativeLayoutMenu.setVisibility(View.VISIBLE);
				editTextEmail.setTextColor(Color.parseColor("#202020"));
				editTextPhoneNumber.setText(Data.getInfoOf(getApplicationContext(),
						Data.PH_NO));
				editTextUserName.setText(Data.getInfoOf(getApplicationContext(),
						Data.USER_NAME));
			}
		});

		buttonChangePassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (textViewEdit.getText().toString().equals("Edit")) {
					Intent intent = new Intent(AccountDriver.this,
							ChangePassword.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.slide_in_right,
							R.anim.slide_out_left);
				} else {
					new DialogPopup()
							.alertPopup(AccountDriver.this, "",
									"Please Save this information then you can change password.");
				}

			}
		});

		relativeLayoutMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openAnim();

			}
		});

		linearLayoutMenuView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeAnim();

			}
		});

		relativeLayoutOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AccountDriver.this,
						OrderScreen.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
			}

		});
		relativeLayoutDeliveredOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AccountDriver.this, History.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
			}
		});

		relativeLayoutPerformance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AccountDriver.this,
						PerformanceDriver.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
			}
		});

		relativeLayoutAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeAnim();
			}
		});
		relativeLayoutEmergency.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AccountDriver.this,
						EmergencyDriver.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);

			}
		});

		relativeLayoutSupport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String htmlString = "&#8226;";
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.putExtra(Intent.EXTRA_EMAIL,
				new String[] { "support@bitekite.com" });
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Bite"+Html.fromHtml(htmlString)+"Kite Support");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
				emailIntent.setType("plain/text");
				try {
					startActivity(Intent.createChooser(emailIntent,"Send mail..."));
					Log.i("Finished sending email...", "");
				} catch (android.content.ActivityNotFoundException ex) {

//					Toast.makeText(getApplicationContext(),
//
//					"There is no email client installed.",
//
//					Toast.LENGTH_SHORT).show();

				}
//				Intent intent = new Intent(AccountDriver.this,
//						SupportDriver.class);
//				startActivity(intent);
//				finish();
//				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);

			}
		});
		relativeLayoutCheckout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AccountDriver.this,
						OrderScreen.class);
				intent.putExtra("checkout", true);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_out_bottom_back,
						R.anim.hold);

			}
		});
		relativeLayoutLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.logout();
			}
		});

		final View activityRootView = findViewById(R.id.mainLinear);

		activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {

						Rect r = new Rect();

						// r will be populated with the coordinates of your view

						// that area still visible.

						activityRootView.getWindowVisibleDisplayFrame(r);

						int heightDiff = activityRootView.getRootView()

						.getHeight() - (r.bottom - r.top);

						if (heightDiff > 100) { // if more than 100 pixels, its

							// probably a keyboard...

							/************** Adapter for the parent List *************/

							ViewGroup.LayoutParams params_12 = textViewExtraTextForScroll

							.getLayoutParams();

							params_12.height = (int) (heightDiff);

							textViewExtraTextForScroll
									.setLayoutParams(params_12);

							textViewExtraTextForScroll.requestLayout();

						} else {

							ViewGroup.LayoutParams params = textViewExtraTextForScroll

							.getLayoutParams();

							params.height = 0;

							textViewExtraTextForScroll.setLayoutParams(params);

							textViewExtraTextForScroll.requestLayout();

						}

					}

				});
		
		
      
	}

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//
//		if (requestCode == takePicture && resultCode == Activity.RESULT_OK) {
//			Log.e("onActivityResult", "onActivityResult");
//			userImage ="1";
//			String iconsStoragePath = Environment
//					.getExternalStorageDirectory()+"/BitKite"
//							+ ".jpg";
//			 file = new File(iconsStoragePath);
//				
//				Picasso.with(this)
//				.load(file).transform(new CircleTransform()).skipMemoryCache()
//				.into(imageViewProfile);
//
//		
//		}
//	}

	public void changeProfileInfoServerCall() {
		RequestParams params = new RequestParams();
		progressBar.setVisibility(View.VISIBLE);
		params.put("access_token",
				Data.getInfoOf(getApplicationContext(), Data.ACCESS_TOKEN));
		params.put("username", stringUserName);
		params.put("phone_no", stringPhoneNumber);
		
		if(userImage.equals("1"))
		{
			try {
				params.put("user_image", file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			Log.e("user_image",""+file);
		}
		
		
		params.put("user_pic", userImage);

		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.EDIT_PROFILE, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						Log.e("resp val", "=" + response);
						progressBar.setVisibility(View.INVISIBLE);
						try {
							JSONObject job = new JSONObject(response);
							
							if(job.getString("status").equals("2"))
							{
//								Data.alertMessageBox(SplashScreen.this,popup.getString("title"),popup.getString("text"),Data.getInfoOf(getApplicationContext(), Data.loginFrom));
							Intent intent = new Intent(AccountDriver.this,MainActivity.class);
							startActivity(intent);
							finish();
							}
							else
							{
							if (response.contains("error")) {
								String error = job.getString("error");
								Log.i("Error ", "-----------" + error);
								new DialogPopup()
								.alertPopup(AccountDriver.this, "",
										error);
							} else {
								if(userImage.equals("1"))
								{
									Data.saveInfoOf(getApplicationContext(),Data.USER_IMAGE_URL,job.getString("user_pic"));
								}
								
								userImage="0";
								textViewEdit.setText("Edit");
								editTextUserName.setEnabled(false);
								editTextPhoneNumber.setEnabled(false);
								textViewCancel.setVisibility(View.INVISIBLE);
								relativeLayoutMenu.setVisibility(View.VISIBLE);
								editTextEmail.setTextColor(Color.parseColor("#202020"));
							}
							}
						} catch (Exception e) {
							Log.v("Login Data Error", "=" + e.toString());
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						// TODO Auto-generated method stub
						progressBar.setVisibility(View.INVISIBLE);
						Log.v("Login Server Error", "fail: " + arg0);
						new DialogPopup()
						.alertPopup(AccountDriver.this, "",
								"Server not responding\nTry again.");
						
					}
				});
	}

	@Override
	public void onBackPressed() {
		if (linearLayoutMenuView.isShown()) {
			closeAnim();
		} else {
			super.onBackPressed();
			overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
			finish();
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		Data.activity = this;
		OrderScreen.pushUpdater = this;
		
		if(Data.getInfoOf(getApplicationContext(), "OD").equals("19"))
		{
			pushUpdater();
			
		}
		DriverLocationFinder.cancelNotification(getApplicationContext(), 0);
	}

	void openAnim() {
		if (Data.startAnim) {
			Data.startAnim = false;
			relativeLayoutHeader.clearAnimation();
			relativeLayoutMain.clearAnimation();
			linearLayoutMenuView.setVisibility(View.VISIBLE);
			relativeLayoutHeader.startAnimation(Data
					.getSlideOutUp(getApplicationContext()));
			relativeLayoutMain.startAnimation(Data
					.getslideOutBottom(getApplicationContext()));
			relativeLayoutHeader.setVisibility(View.INVISIBLE);
			relativeLayoutMain.setVisibility(View.INVISIBLE);
		}
	}

	void closeAnim() {
		if (Data.startAnim) {
			relativeLayoutHeader.clearAnimation();
			relativeLayoutMain.clearAnimation();
			relativeLayoutHeader.startAnimation(Data
					.getslideOutUpBack(getApplicationContext()));
			relativeLayoutMain.startAnimation(Data
					.getslideOutBottomBack(getApplicationContext()));
			relativeLayoutHeader.setVisibility(View.VISIBLE);
			relativeLayoutMain.setVisibility(View.VISIBLE);
			Handler mHandler = new Handler();
			mHandler.postDelayed(new Runnable() {
				public void run() {
					linearLayoutMenuView.setVisibility(View.INVISIBLE);
				}
			}, 700);
		}
	}

	@Override
	public void pushUpdater() {
		Log.e("pushUpdater", "=");
		new Thread(new Runnable() {

			@Override
			public void run() {

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
						if(GCMIntentService.flag.equals("15"))
						{
							new DialogPopup().alertPopup(AccountDriver.this, "",
									GCMIntentService.stringMessage);
						}
						else if((Data.getInfoOf(getApplicationContext(), "OD").equals("19")))
						{
							Data.saveInfoOf(Data.activity,
									Data.CHECKINVALUE, "0");
							Intent intent = new Intent(AccountDriver.this,
									OrderScreen.class);
							intent.putExtra("checkout_admin", true);
							startActivity(intent);
							finish();
							overridePendingTransition(R.anim.slide_in_left,
									R.anim.hold);
							stopService(new Intent(Data.activity,
									MyService.class));
						}
						else if(GCMIntentService.flag.equals("25"))
						{
							Data.saveInfoOf(Data.activity,
									Data.CHECKINVALUE, "1");
							new DialogPopup().alertPopup(AccountDriver.this, "",
									GCMIntentService.stringMessage);
						}
						else if(GCMIntentService.flag.equals("16"))
						{
							new DialogPopup().alertPopup(AccountDriver.this, "",
									GCMIntentService.stringMessage);
						}
						 Data.saveInfoOf(getApplicationContext(), "OD", "");
						
					}
				});
			}
		}).start();

	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("onPause", "onPause");
		OrderScreen.pushUpdater = null;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("onDestroy", "onDestroy");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK
				&& (requestCode == ChooserType.REQUEST_PICK_PICTURE ))
			{
			imageChooserManager.submit(requestCode, data);
		}
		else if(resultCode == RESULT_OK
						&&requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)
		{
			imageChooserManager2.submit(requestCode, data);
		}
	}

	@Override
	public void onImageChosen(final ChosenImage image) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (image != null) {
					// Use the image
					picturePathString =  image.getFilePathOriginal();
					Log.d("---------Image Path----------","---------Image Path----------"+picturePathString);
					if (picturePathString.equalsIgnoreCase("null")) {
						
					} else {
											
//						Bitmap bitmap = null;
						try {
							
							 file = new File(picturePathString);
							 Log.d("file","file"+file);
							 Picasso.with(AccountDriver.this)
								.load(file).placeholder(R.drawable.user_def).transform(new CircleTransform()).skipMemoryCache()
								.into(imageViewProfile);
//							imageView.setImageBitmap(bitmap);
							userImage="1";

						} catch (Exception e) {
//							Toast.makeText(AccountUser.this, "Failed to load", Toast.LENGTH_SHORT)
//									.show();
							Log.e("Camera", e.toString());
						}
					}
					// image.getFileThumbnail();
					// image.getFileThumbnailSmall();
				}
			}
		});
	}

	@Override
	public void onError(final String reason) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Show error message
				Log.d("---------Image Path 2----------","---------Image Path 2 ----------"+picturePathString);
			}
		});
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(AccountDriver.this);
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(AccountDriver.this).activityStart(this);
//		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(AccountDriver.this).activityStop(this);
//		super.onStart();
	}
}
