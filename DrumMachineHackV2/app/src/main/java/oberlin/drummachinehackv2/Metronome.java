package oberlin.drummachinehackv2;

/**
 * Created by Zarkdion on 9/23/17.
 */

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import java.util.concurrent.locks.LockSupport;

import java.util.Timer;

public class Metronome {

    private int bpm;
    private int interval;
    private Timer tmr;
    private int isPlaying;
    private int lownote;

    private SoundPool.Builder sbuilder;
    private SoundPool sounds;
    private int snare;
    private int base;
    private int hihat;

    public Metronome(Context context, int bpmattr, int ln) {
        bpm = bpmattr;
        isPlaying = 0;
        lownote = ln;
        CalcInterval();
        AudioAttributes aAttrib = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        sbuilder = new SoundPool.Builder();
        sbuilder.setAudioAttributes(aAttrib);
        sounds = sbuilder.build();
        base = sounds.load(context, R.raw.k10, 1);
        snare = sounds.load(context, R.raw.s11, 1);
        hihat = sounds.load(context, R.raw.hihat4, 1);
        System.out.print(base);
        System.out.print(snare);
        System.out.print(hihat);
    }

    public void CalcInterval() {

        interval = 60000/bpm/lownote;
        interval = interval*4000000;
    }

    public void doTheThing(int[][] directions) {
        if (isPlaying == 1) {
            isPlaying = 0;
            Stop();
        } else {
            tmr = new Timer();
            isPlaying = 1;
            Play(directions);
        }
    }

    private void Play(int[][] directions) {
        int sound;

        while (true) {
            for (int i = 0; i < directions.length; i++) {
                for (int j = 0; j < 2; j++) {
                    sound = directions[i][j];
                    System.out.print(sound);
                    if (sound != 0) {
                        sounds.play(sound, 1, 1, 1, 0, 1);
                    }
                }
                LockSupport.parkNanos(interval);
            }
        }

    }

    private void Stop() {
        for (int i=1; i<10; i++) {
            sounds.stop(i);
        }
    }




}
