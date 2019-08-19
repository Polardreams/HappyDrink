package polardreams.de.happydrink;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import polardreams.de.happydrink.gestureimageview.GestureImageView;

public class PictureScaleActivity extends AppCompatActivity {

    private int ressourcename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_scale);

        Intent get_design = getIntent();
        Bundle bundle = get_design.getExtras();
        ressourcename = (int) bundle.get("picture");

        try {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            GestureImageView view = new GestureImageView(this);
            ViewGroup layout = (ViewGroup) findViewById(R.id.layout);

            Glide.with(this).load(ressourcename).into(view);
            view.setLayoutParams(params);
            layout.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
