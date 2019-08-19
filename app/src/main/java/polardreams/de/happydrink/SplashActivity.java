package polardreams.de.happydrink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;


import polardreams.de.happydrink.supportlibs.SecretTextView;


public class SplashActivity extends AppCompatActivity {

    private final long Splash_time = 3000;
    private Handler h = new Handler();
    private ImageView im;
    private SecretTextView txt;
    private Animation slids;
    private boolean party_status;
    private SharedPreferences info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        info = PreferenceManager.getDefaultSharedPreferences(this);
        party_status = info.getBoolean("party_status", false);

        if (!party_status) {
            final BounceInterpolator interpolator = new BounceInterpolator();
            slids = AnimationUtils.loadAnimation(this, R.anim.slide);
            slids.setFillAfter(true);
            slids.setInterpolator(interpolator);

            im = (ImageView) findViewById(R.id.imageView2);

            im.setAnimation(slids);

            txt = (SecretTextView) findViewById(R.id.txt);
            txt.setDuration(2800);
            txt.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent next_intent = new Intent(SplashActivity.this, Drinkcounter.class);
                    SplashActivity.this.startActivity(next_intent);
                    SplashActivity.this.finish();
                }

            }, Splash_time);
        } else {
            Intent next_intent = new Intent(SplashActivity.this, Drinkcounter.class);
            SplashActivity.this.startActivity(next_intent);
            SplashActivity.this.finish();
        }

    }
}
