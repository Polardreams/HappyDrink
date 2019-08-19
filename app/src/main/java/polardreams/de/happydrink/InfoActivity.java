package polardreams.de.happydrink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.library.banner.BannerLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import polardreams.de.happydrink.adapter.WebBannerAdapter;

public class InfoActivity extends AppCompatActivity {

    private Context c;
    private ImageView im;
    private int design_index = 0;
    private Intent i_infobox;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        c = this;
        title = (TextView)findViewById(R.id.textView5);
        title.setText(getResources().getString(R.string.infobox_title));
        title.setTextSize(25.0f);

        BannerLayout recyclerBanner = (BannerLayout) findViewById(R.id.recycler);
        List<Integer> list = new ArrayList<>();
        try {
            list.add(R.drawable.thema1);
            list.add(R.drawable.thema2);
            list.add(R.drawable.thema3);
            list.add(R.drawable.thema4);
            list.add(R.drawable.thema5);
            list.add(R.drawable.thema6);
            list.add(R.drawable.thema7);
            list.add(R.drawable.thema8);
            list.add(R.drawable.thema9);
            list.add(R.drawable.thema10);
            list.add(R.drawable.thema11);
            list.add(R.drawable.thema12);
            list.add(R.drawable.thema13);
            list.add(R.drawable.thema14);
            list.add(R.drawable.thema15);
            list.add(R.drawable.thema16);
        } catch (Exception e) {
            e.printStackTrace();
        }

        WebBannerAdapter webBannerAdapter=new WebBannerAdapter(c,list);
        webBannerAdapter.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {

                switch (position) {
                    case 0: openInfoBox(position); analyse_magazin(position);break;
                    case 1: openInfoBox(position); analyse_magazin(position);break;
                    case 2: openInfoBox(position); analyse_magazin(position);break;
                    case 3: openInfoBox(position); analyse_magazin(position);break;
                    case 4: openInfoBox(position); analyse_magazin(position);break;
                    case 5: openInfoBox(position); analyse_magazin(position);break;
                    case 6: openInfoBox(position); analyse_magazin(position);break;
                    case 7: openInfoBox(position); analyse_magazin(position);break;
                    case 8: openInfoBox(position); analyse_magazin(position);break;
                    case 9: openInfoBox(position); analyse_magazin(position);break;
                    case 10: openInfoBox(position); analyse_magazin(position);break;
                    case 11: openInfoBox(position); analyse_magazin(position);break;
                    case 12: openInfoBox(position); analyse_magazin(position);break;
                    case 13: openInfoBox(position); analyse_magazin(position);break;
                    case 14: openInfoBox(position); analyse_magazin(position);break;
                    case 15: openInfoBox(position); analyse_magazin(position);break;
                    case 16: openInfoBox(position); analyse_magazin(position);break;
                }

            }
        });
        recyclerBanner.setAdapter(webBannerAdapter);

        Intent get_design = getIntent();
        Bundle bundle = get_design.getExtras();
        design_index = (int) bundle.get("design");

        load_design(design_index);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void openInfoBox (int layout_index) {
        i_infobox = new Intent (c, InfoBoxActivity.class);
        i_infobox.putExtra("design",design_index);
        i_infobox.putExtra("layout",layout_index);
        startActivity(i_infobox);
    }

    private  void analyse_magazin (int layout_index) {
        String id = "";
        String type = "";
        String event = "FELHER";

        switch (layout_index) {
            case 0 : id = "happydrink_39"; event = "magazin_substanz"; type = "substanz"; break;
            case 1 : id = "happydrink_40"; event = "magazin_abhaengigkeit"; type = "abhaengigkeit"; break;
            case 2 : id = "happydrink_41"; event = "magazin_akutwirkung"; type = "akutwirkung"; break;
            case 3 : id = "happydrink_42"; event = "magazin_schwangerschaft"; type = "schwangerschaft"; break;
            case 4 : id = "happydrink_43"; event = "magazin_alkoholaufnahme"; type = "alkoholaufnahme"; break;
            case 5 : id = "happydrink_44"; event = "magazin_langzeitfolgen"; type = "langzeitfolgen"; break;
            case 6 : id = "happydrink_45"; event = "magazin_safer_use"; type = "safer_use"; break;
            case 7 : id = "happydrink_46"; event = "magazin_wort_bier"; type = "wort_bier"; break;
            case 8 : id = "happydrink_47"; event = "magazin_bier_bei_aegypter"; type = "bier_bei_aegypter"; break;
            case 9 : id = "happydrink_48"; event = "magazin_biererfindung"; type = "biererfindung"; break;
            case 10 : id = "happydrink_49"; event = "magazin_kulturgut_bier_bei_sumerer"; type = "kulturgut_bier_bei_sumerer"; break;
            case 11 : id = "happydrink_50"; event = "magazin_geschichte_trinkgewohnheiten"; type = "geschichte_trinkgewohnheiten"; break;
            case 12 : id = "happydrink_51"; event = "magazin_erstes_reinheitsgebot"; type = "erstes_reinheitsgebot"; break;
            case 13 : id = "happydrink_52"; event = "magazin_bedeutung_reinheitsgebot"; type = "bedeutung_reinheitsgebot"; break;
            case 14 : id = "happydrink_53"; event = "magazin_das_anstossen"; type = "das_anstossen"; break;
            case 15 : id = "happydrink_54"; event = "magazin_alkoholrausch"; type = "alkoholrausch"; break;
        }
    }

    private void load_design (int design_index) {
        im = (ImageView)findViewById(R.id.imageView);
        try {
            switch (design_index) {
                case 0:
                    Glide.with(c).load(R.mipmap.disco_klein).into(im);
                    title.setTextColor(getResources().getColor(R.color.color_display));
                    title.setTypeface(ResourcesCompat.getFont(c,R.font.caviardreams_bold));break;
                case 1:
                    Glide.with(c).load(R.mipmap.backroundimage1).into(im);
                    title.setTextColor(getResources().getColor(R.color.colorText1));
                    title.setTypeface(ResourcesCompat.getFont(c,R.font.kannada_sangam_mm));break;
                case 2:
                    Glide.with(c).load(R.mipmap.backroundimage2).into(im);
                    title.setTextColor(getResources().getColor(R.color.colorTapSequenzText));
                    title.setTypeface(ResourcesCompat.getFont(c,R.font.presa));break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
