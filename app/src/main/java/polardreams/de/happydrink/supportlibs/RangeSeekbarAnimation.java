package polardreams.de.happydrink.supportlibs;

import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.BounceInterpolator;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.jaygoo.widget.RangeSeekBar;

/**
 * Created by David on 09.03.2018.
 */

public class RangeSeekbarAnimation implements Runnable{

    private long starttime;
    private int duration =1500;
    private float startpunkt, endpunkt;
    private RangeSeekBar progress;
    public boolean flag_isanimat;
    private Handler handler = new Handler();

   public RangeSeekbarAnimation (RangeSeekBar progress, long starttime, float startpunkt, float endpunkt) {
        this.starttime = starttime;
        this.startpunkt = startpunkt;
        this.endpunkt = endpunkt;
        this.progress = progress;
        flag_isanimat=false;
    }

    @Override
    public void run() {

        final BounceInterpolator interpolator = new BounceInterpolator(); // Das Objekt bewegt sich geradlinig

        long elaspedtime = SystemClock.uptimeMillis() - starttime;
        float faktor = interpolator.getInterpolation((float) elaspedtime / duration);//Dauer der Bewegung mit dem Verhältnis zw. ges. Dauer und momentaner verstrichener Zeit
        float inc = faktor * endpunkt + (1 - faktor) * startpunkt;


        if (faktor < 1.0) {
            handler.postDelayed(this, 16);//Auflösung bzw. präzision der Animation
            progress.setValue(inc);
        } else {
            flag_isanimat = false;
        }


    }

}
