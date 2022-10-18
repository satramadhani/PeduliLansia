package edu.upi.cs.mobileapp.techi.library;

import android.graphics.Path;

public class Wave
{
    Path path;
    int width;
    int wave;
    float offsetX;
    float offsetY;
    float velocity;
    private float scaleX;
    private float scaleY;
    private int curWave;

    Wave(int offsetX, int offsetY, int velocity, float scaleX, float scaleY, int wave)
    {
        this.wave = wave;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.velocity = velocity;
        this.path = new Path();
    }

    protected void updateWavePath(int w, int h, int waveHeight, boolean fullScreen, float progress)
    {
        this.wave = waveHeight;
        this.width = (int) (2* scaleX * w);
        this.path = buildWavePath(width, h, fullScreen, progress);
    }

    protected void updateWavePath(int w, int h, float progress)
    {
        int wave = (int) (scaleY * this.wave);
        float maxWave = h * Math.max(0, (1 - progress));

        if (wave > maxWave)
        {
            wave = (int)maxWave;
        }

        if (curWave != wave)
        {
            this.width = (int) (2 * scaleX * w);
            this.path = buildWavePath(width, h, true, progress);
        }
    }

    protected Path buildWavePath(int width, int height, boolean fullScreen, float progress)
    {
        int DP = Util.dp2px(1);
        if (DP < 1)
        {
            DP = 1;
        }

        int wave = (int) (scaleY * this.wave);

        if (fullScreen)
        {
            float maxWave = height * Math.max(0, (1 - progress));
            if (wave > maxWave)
            {
                wave = (int) maxWave;
            }
        }

        this.curWave = wave;

//        Path path = new Path();
        path.reset();

        path.moveTo(0, 0);
        path.lineTo(0, height - wave);

        if (wave > 0)
        {
            for (int x = DP; x < width; x += DP)
            {
                path.lineTo(x, height - wave - wave * (float) Math.sin(4.0 * Math.PI * x / width));
            }
        }

        path.lineTo(width, height - wave);
        path.lineTo(width, 0);
        path.close();
        return path;
    }
}
