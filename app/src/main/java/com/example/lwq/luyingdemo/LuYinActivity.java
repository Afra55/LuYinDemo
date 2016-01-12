package com.example.lwq.luyingdemo;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LuYinActivity extends Activity {

	Button button;
	MediaRecorder mediaRecorder;
	boolean isRecorder=false;
	File audioFile;
	
	private static final String FILE_PATH_YUYIN = "/sdcard/yuyin.amr";// 录音文件的绝对路径

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_luyin);
		button = (Button) findViewById(R.id.activity_luyin_start);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isRecorder) {
					stop();
				}else{
					record();
				}
				
			}

		});
	}

	private void stop() {
		// TODO Auto-generated method stub
//		mediaRecorder.stop();
		
		mediaRecorder.stop();
		mediaRecorder.release();
		mediaRecorder = null;
		isRecorder = false;
		
		Intent it = new Intent();
		it.putExtra("path", FILE_PATH_YUYIN);
		setResult(200, it);
		finish();
	}
	
	private void record() {
		button.setText("停止录音");
		/**
		 * mediaRecorder.setAudioSource设置声音来源。
		 * MediaRecorder.AudioSource这个内部类详细的介绍了声音来源。
		 * 该类中有许多音频来源，不过最主要使用的还是手机上的麦克风，MediaRecorder.AudioSource.MIC
		 */
		isRecorder = true;
		mediaRecorder = new MediaRecorder();

		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		/**
		 * mediaRecorder.setOutputFormat代表输出文件的格式。该语句必须在setAudioSource之后，
		 * 在prepare之前。
		 * OutputFormat内部类，定义了音频输出的格式，主要包含MPEG_4、THREE_GPP、RAW_AMR……等。
		 */
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		/**
		 * mediaRecorder.setAddioEncoder()方法可以设置音频的编码
		 * AudioEncoder内部类详细定义了两种编码：AudioEncoder.DEFAULT、AudioEncoder.AMR_NB
		 */
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		/**
		 * 设置录音之后，保存音频文件的位置
		 */
//		File sdcardDir = Environment.getExternalStorageDirectory();
//		String path = sdcardDir.getParent() + sdcardDir.getName();
//		String filePath = path + java.io.File.separator + "Demo";
//		//创建文件，使用自己指定文件名(这里我手动创建好了,我们也可以利用mkdirs的方法来创建)
//		File file = new File(path,"new.amr");
		
		File file = new File(FILE_PATH_YUYIN);
		if (file.exists()) {
			file.delete();
		}

		
//		File file = new File("sdcard/a.3pg");
//		FileDescriptor file
		mediaRecorder.setOutputFile(file.getAbsolutePath());
		Log.e("file",file.getAbsolutePath());

		/**
		 * 调用start开始录音之前，一定要调用prepare方法。
		 */
		try {
			mediaRecorder.prepare();
			mediaRecorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
