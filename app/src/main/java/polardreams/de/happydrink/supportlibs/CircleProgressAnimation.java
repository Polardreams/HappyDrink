package polardreams.de.happydrink.supportlibs;

import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.BounceInterpolator;

import com.github.lzyzsd.circleprogress.DonutProgress;

/**
 * Created by David on 16.02.2018.
 */

public class CircleProgressAnimation implements Runnable {


    private long starttime;
    private int duration =3000;
    private float startpunkt, endpunkt;
    private DonutProgress progress;
    public boolean flag_isanimat, iskomma;
    private Handler handler = new Handler();
    private float start, mitte, ende, dev, anzeige, zielgrenze;
    private String suffix;

   public CircleProgressAnimation (DonutProgress progress, long starttime, float startpunkt, float endpunkt, float anzeige, float start, float mitte, float ende, float dev, String suffix, float zielgrenze, boolean iskomma) {
        this.starttime = starttime;
        this.startpunkt = startpunkt*dev;
        this.endpunkt = endpunkt*dev;
        this.progress = progress;
        this.start = start;
        this.mitte = mitte;
        this.ende = ende;
        this.dev = dev;
        this.suffix = suffix;
        this.anzeige = anzeige;
        this.zielgrenze = zielgrenze*dev;
        flag_isanimat=false;
        this.iskomma = iskomma;
    }

    @Override
    public void run() {

            final BounceInterpolator interpolator = new BounceInterpolator(); // Das Objekt bewegt sich geradlinig

            long elaspedtime = SystemClock.uptimeMillis() - starttime;
            float faktor = interpolator.getInterpolation((float) elaspedtime / duration);//Dauer der Bewegung mit dem Verhältnis zw. ges. Dauer und momentaner verstrichener Zeit
            float inc = faktor * endpunkt + (1 - faktor) * startpunkt;

            if (inc < start * dev ) {
                progress.setFinishedStrokeColor(Color.BLUE);
                //progress.setFinishedStrokeColor(Color.GREEN);
            }
            if (inc > start * dev && inc < mitte * dev) {
                progress.setFinishedStrokeColor(Color.GREEN);
            }
            if (inc > mitte * dev && inc < ende * dev) {
                progress.setFinishedStrokeColor(Color.YELLOW);
            }
            if (inc > ende * dev) {
                progress.setFinishedStrokeColor(Color.RED);
            }

            if (faktor < 1.0 && inc < zielgrenze) {
                handler.postDelayed(this, 16);//Auflösung bzw. präzision der Animation
                progress.setProgress((int) inc);
                if (iskomma) {
                    progress.setText(String.format("%.2f", (float) (inc / dev)) + suffix);
                } else {
                    progress.setText(String.format("%.0f", (float) (inc / dev)) + suffix);
                }

            } else {
                flag_isanimat = false;
                if (iskomma) {
                    progress.setText(String.format("%.2f", (float) (anzeige)) + suffix);
                } else {
                    progress.setText(String.format("%.0f", (float) (anzeige)) + suffix);
                }
            }


    }
}
