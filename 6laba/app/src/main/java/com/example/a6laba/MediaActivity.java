package com.example.a6laba;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MediaActivity extends AppCompatActivity
{
    com.example.a6laba.FragmentGallery fg;
    com.example.a6laba.FragmentMusic fm;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        fg = new com.example.a6laba.FragmentGallery();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //когда музыка готова к запуску - подготовка
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                if (mp.isPlaying())
                { //если хотим запустить песню которая играет
                    mp.stop();
                    mp.reset();
                } else
                {
                    mp.start();//запускаем
                }
            }
        });
        fm = new com.example.a6laba.FragmentMusic();
        fm.mediaPlayer = mediaPlayer;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fg);
        ft.commit();
    }


    public void onClickGallery(View view)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fg);
        ft.commit();
    }

    public void onClickPlayer(View view)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fm);
        ft.commit();
    }
}