package com.daxueoo.shopnc.scan;

import java.io.IOException;
import java.util.Vector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.ui.activity.BaseActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

public class CaptureActivity extends BaseActivity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private Button btncancle;

	// private Button btnflash;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);
		// ｳｼｻｯ CameraManager
		CameraManager.init(getApplication());

		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		btncancle = (Button) findViewById(R.id.CancelBtn);
		// btnflash=(Button)findViewById(R.id.FlashBtn);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		btncancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent result = new Intent();
				setResult(RESULT_CANCELED, result);

				finish();
			}

		});

		// btnflash.setOnClickListener(new OnClickListener(){
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// });

	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		// initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		viewfinderView.drawResultBitmap(barcode);
		// playBeepSoundAndVibrate();
		Toast.makeText(CaptureActivity.this, obj.getText().toString(),Toast.LENGTH_LONG).show();
		Log.e("ts", obj.getText().toString());
		Intent result = new Intent();
		result.putExtra("SCAN_RESULT", obj.getText().toString());
		setResult(RESULT_OK, result);
//		if (Var.Scanflg.equals("0")) {
//			if (TextUtils.isDigitsOnly(obj.getText().toString()) == false) {
//				Toast.makeText(this, getString(R.string.Dialogmessage),
//						Toast.LENGTH_SHORT).show();
//			} else {
//				SearchActivity.et_findexpressrule_order_number.setText(obj
//						.getText().toString());
//			}
//		}

		finish();
	}

	// private void initBeepSound() {
	// if (playBeep && mediaPlayer == null) {
	// // The volume on STREAM_SYSTEM is not adjustable, and users found it
	// // too loud,
	// // so we now play on the music stream.
	// setVolumeControlStream(AudioManager.STREAM_MUSIC);
	// mediaPlayer = new MediaPlayer();
	// mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	// mediaPlayer.setOnCompletionListener(beepListener);
	//
	// AssetFileDescriptor file = getResources().openRawResourceFd(
	// R.raw.beep);
	// try {
	// mediaPlayer.setDataSource(file.getFileDescriptor(),
	// file.getStartOffset(), file.getLength());
	// file.close();
	// mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
	// mediaPlayer.prepare();
	// } catch (IOException e) {
	// mediaPlayer = null;
	// }
	// }
	// }

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent result = new Intent();
			setResult(RESULT_CANCELED, result);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

}