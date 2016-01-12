package com.example.lwq.luyingdemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {
    private VideoView video=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        video= (VideoView) findViewById(R.id.activity_dmt_media_video);
           findViewById(R.id.lunyin_bt).setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View v) {
                   paiYinpin(1);
               }
           });
        findViewById(R.id.lunyin_my_bt).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                paiYinpin(2);
            }
        });
    }

    //获取音频的数据（录制）
    public void paiYinpin(int flag) {
        switch (flag){
            case 1:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/amr");
                intent.setClassName("com.android.soundrecorder", "com.android.soundrecorder.SoundRecorder");
                Uri uri = saveYinpin();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 5);
                break;
            case 2:
                Intent it = new Intent(MainActivity.this, LuYinActivity.class);
                startActivityForResult(it, 10);
                break;

        }
    }

    //将所录制的音频存储在目标地址（方法二）
    public Uri saveYinpin() {
        String saveDir = Environment.getExternalStorageDirectory() + "/audio";
        File sdir = new File(saveDir);
        //新建目录的文件夹
        if (!sdir.exists()) {
            sdir.mkdir();
        }
        //生成文件名
        SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");
        String filename = "MT" + (t.format(new Date())) + ".amr";
        //创建文件(新建在sdir文件夹下有个这个filename名的文件)
        File file = new File(sdir, filename);
        path = file.getPath();
        Uri uri = Uri.fromFile(file);
        return uri;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                Uri uri1 = Uri.fromFile(new File(data.getStringExtra("path")));
                playViedo(uri1);
                break;
            case 5:
                if (data != null) {
                    Uri uri = data.getData();
                    playViedo(uri);
                    System.out.println("音频的地址是什么" + uri + "......" + uri.getPath());
                }
                break;

        }

    }

    String path;

    private void playViedo(Uri uri) {
        path = MeadUtil.getPath(this, uri);//通过cusor获取本地的路径
        File file = new File(path);
        Log.e("data", uri.getPath());
        video.setVideoPath(file.getAbsolutePath());
        // 为VideoView指定MediaController
        MediaController mController = new MediaController(this);
        video.setMediaController(mController);
        video.requestFocus();

        // 为MediaController指定控制的VideoView
        mController.setMediaPlayer(video);
        // 增加监听上一个和下一个的切换事件，默认这两个按钮是不显示的

        mController.setPrevNextListeners(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "下一个", 1).show();
            }
        }, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "上一个", 1).show();
            }
        });
        video.start();
    }
}
