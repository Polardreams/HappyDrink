package polardreams.de.happydrink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.text.SimpleDateFormat;

import uk.co.deanwild.flowtextview.FlowTextView;

public class InfoBoxActivity extends AppCompatActivity {

    private int design_index;
    private int layout_index;
    private Context c;
    private ImageView im, im_pic1, im_pic2, im_pic3;
    private FlowTextView flowTextView;
    private TextView txt;
    private CardView c1;
    private Typeface typeface, typeface_ue;
    private int ressource_pic_1, ressource_pic_2, ressource_pic_3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = this;
        Intent get_design = getIntent();
        Bundle bundle = get_design.getExtras();
        design_index = (int) bundle.get("design");
        layout_index = (int) bundle.get("layout");
        load_layout();

    }

    @Override
    protected void onResume() {
        load_text(layout_index);
        load_design(design_index);
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void load_layout () {
        switch (layout_index) {
            case 0: setContentView(R.layout.activity_info_substanz);
                im_pic1 = (ImageView)findViewById(R.id.imageView4);
                Glide.with(c).load(R.drawable.pic_gaerprozess).into(im_pic1);
                ressource_pic_1 = R.drawable.pic_gaerprozess;
                im_pic1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i_picture = new Intent (c, PictureScaleActivity.class);
                            i_picture.putExtra("picture", ressource_pic_1);
                            startActivity(i_picture);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 1: setContentView(R.layout.activity_info_abhaengigkeit);
                im_pic1 = (ImageView)findViewById(R.id.imageView4);
                Glide.with(c).load(R.drawable.abhaengigkeit_alkohol_white).into(im_pic1);
                ressource_pic_1 = R.drawable.abhaengigkeit_alkohol_white;
                im_pic1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i_picture = new Intent (c, PictureScaleActivity.class);
                            i_picture.putExtra("picture", ressource_pic_1);
                            startActivity(i_picture);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 2: setContentView(R.layout.activity_info_akutwirkung);
                im_pic1 = (ImageView)findViewById(R.id.imageView4);
                Glide.with(c).load(R.drawable.alkoholauswirkungen).into(im_pic1);
                ressource_pic_1 = R.drawable.alkoholauswirkungen;
                im_pic1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i_picture = new Intent (c, PictureScaleActivity.class);
                            i_picture.putExtra("picture", ressource_pic_1);
                            startActivity(i_picture);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 3: setContentView(R.layout.activity_info_schwanger);
                im_pic1 = (ImageView)findViewById(R.id.imageView4);
                Glide.with(c).load(R.drawable.schwangere_weinglas).into(im_pic1);
                ressource_pic_1 = R.drawable.schwangere_weinglas;
                im_pic1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i_picture = new Intent (c, PictureScaleActivity.class);
                            i_picture.putExtra("picture", ressource_pic_1);
                            startActivity(i_picture);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 4: setContentView(R.layout.activity_info_alkoholeinnahme);
                im_pic1 = (ImageView)findViewById(R.id.imageView4);
                Glide.with(c).load(R.drawable.alkoholeinnahme).into(im_pic1);
                ressource_pic_1 = R.drawable.alkoholeinnahme;
                im_pic1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i_picture = new Intent (c, PictureScaleActivity.class);
                            i_picture.putExtra("picture", ressource_pic_1);
                            startActivity(i_picture);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 5: setContentView(R.layout.activity_info_langzeitwirkung);
                im_pic1 = (ImageView)findViewById(R.id.imageView4);
                Glide.with(c).load(R.drawable.langzeitfolgen_auf_den_koerper).into(im_pic1);
                ressource_pic_1 = R.drawable.langzeitfolgen_auf_den_koerper;
                im_pic1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i_picture = new Intent (c, PictureScaleActivity.class);
                            i_picture.putExtra("picture", ressource_pic_1);
                            startActivity(i_picture);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 6: setContentView(R.layout.activity_info_saferuse);
                im_pic1 = (ImageView)findViewById(R.id.imageView4);
                Glide.with(c).load(R.drawable.langeweile_party).into(im_pic1);
                ressource_pic_1 = R.drawable.langeweile_party;
                im_pic1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i_picture = new Intent (c, PictureScaleActivity.class);
                            i_picture.putExtra("picture", ressource_pic_1);
                            startActivity(i_picture);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                im_pic2 = (ImageView)findViewById(R.id.imageView5);
                Glide.with(c).load(R.drawable.saft).into(im_pic2);
                ressource_pic_2 = R.drawable.saft;
                im_pic2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i_picture = new Intent (c, PictureScaleActivity.class);
                            i_picture.putExtra("picture", ressource_pic_2);
                            startActivity(i_picture);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                im_pic3 = (ImageView)findViewById(R.id.imageView6);
                Glide.with(c).load(R.drawable.paar).into(im_pic3);
                ressource_pic_3 = R.drawable.paar;
                im_pic3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i_picture = new Intent (c, PictureScaleActivity.class);
                            i_picture.putExtra("picture", ressource_pic_3);
                            startActivity(i_picture);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 7: setContentView(R.layout.activity_info_wortherkunft_bier); break;
            case 8: setContentView(R.layout.activity_info_aegypter); break;
            case 9: setContentView(R.layout.activity_info_erfindung); break;
            case 10: setContentView(R.layout.activity_info_sumerer);
                im_pic1 = (ImageView)findViewById(R.id.imageView4);
                Glide.with(c).load(R.drawable.wildermann_dirne).into(im_pic1);
                ressource_pic_1 = R.drawable.wildermann_dirne;
                im_pic1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i_picture = new Intent (c, PictureScaleActivity.class);
                            i_picture.putExtra("picture", ressource_pic_1);
                            startActivity(i_picture);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 11: setContentView(R.layout.activity_info_trinkgewohnheit);
                im_pic1 = (ImageView)findViewById(R.id.imageView4);
                Glide.with(c).load(R.drawable.antiker_krug).into(im_pic1);
                ressource_pic_1 = R.drawable.antiker_krug;
                im_pic1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i_picture = new Intent (c, PictureScaleActivity.class);
                            i_picture.putExtra("picture", ressource_pic_1);
                            startActivity(i_picture);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                im_pic2 = (ImageView)findViewById(R.id.imageView5);
                Glide.with(c).load(R.drawable.trinkgewohnheit_babylon).into(im_pic2);
                ressource_pic_2 = R.drawable.trinkgewohnheit_babylon;
                im_pic2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i_picture = new Intent (c, PictureScaleActivity.class);
                            i_picture.putExtra("picture", ressource_pic_2);
                            startActivity(i_picture);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 12: setContentView(R.layout.activity_info_erstes_reinheitsgebot);
                im_pic1 = (ImageView)findViewById(R.id.imageView4);
                Glide.with(c).load(R.drawable.codex_hammurapi).into(im_pic1);
                ressource_pic_1 = R.drawable.codex_hammurapi;
                im_pic1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i_picture = new Intent (c, PictureScaleActivity.class);
                            i_picture.putExtra("picture", ressource_pic_1);
                            startActivity(i_picture);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 13: setContentView(R.layout.activity_info_reinheitsgebot);
                im_pic1 = (ImageView)findViewById(R.id.imageView4);
                Glide.with(c).load(R.drawable.brauerei_mittelalter).into(im_pic1);
                ressource_pic_1 = R.drawable.brauerei_mittelalter;
                im_pic1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i_picture = new Intent (c, PictureScaleActivity.class);
                            i_picture.putExtra("picture", ressource_pic_1);
                            startActivity(i_picture);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 14: setContentView(R.layout.activity_info_anstossen); break;
            case 15: setContentView(R.layout.activity_info_alkoholrausch); break;
        }//switc
    }

    private void load_design (int design_index) {
        im = (ImageView)findViewById(R.id.imageView);
        try {
            switch (design_index) {
                case 0: Glide.with(c).load(R.mipmap.disco_klein).into(im);
                    typeface_ue = ResourcesCompat.getFont(c,R.font.caviardreams_bold);
                    txt.setTypeface(typeface_ue);
                    txt.setTextColor(getResources().getColor(R.color.color_display));
                    typeface = ResourcesCompat.getFont(c,R.font.caviardreams);
                    flowTextView.setTypeface(typeface);
                    flowTextView.setTextColor(getResources().getColor(R.color.color_display));break;
                case 1: Glide.with(c).load(R.mipmap.backroundimage1).into(im);
                    typeface_ue = ResourcesCompat.getFont(c,R.font.kannada_sangam_mm);
                    txt.setTypeface(typeface_ue);
                    txt.setTextColor(getResources().getColor(R.color.colorText1));
                    typeface = ResourcesCompat.getFont(c,R.font.kannada_sangam_mm);
                    flowTextView.setTypeface(typeface);
                    flowTextView.setTextColor(getResources().getColor(R.color.colorText1));break;
                case 2: Glide.with(c).load(R.mipmap.backroundimage2).into(im);
                    typeface_ue = ResourcesCompat.getFont(c,R.font.presa);
                    txt.setTypeface(typeface_ue);
                    txt.setTextColor(getResources().getColor(R.color.colorTapSequenzText));
                    typeface = ResourcesCompat.getFont(c,R.font.presa);
                    flowTextView.setTypeface(typeface);
                    flowTextView.setTextColor(getResources().getColor(R.color.colorTapSequenzText));break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load_text (int text_nr) {
        try {
            c1 = (CardView)findViewById(R.id.cardview1);
            c1.setForegroundGravity(Gravity.CENTER);
            flowTextView = (FlowTextView) findViewById(R.id.ftv);
            txt = (TextView)findViewById(R.id.textView5);
            txt.setGravity(Gravity.CENTER);
            txt.setTextSize(23.0f);

            String title = "";
            String text = "";
            String autor = "";
            String quelle = "";
            switch (text_nr) {
                case 0: title = getResources().getText(R.string.infobox_texttitle_substanz).toString(); text = getResources().getText(R.string.infobox_text_substanz).toString(); autor =getResources().getText(R.string.infobox_textautor_substanz).toString(); quelle=getResources().getText(R.string.infobox_textquelle_substanz).toString();break;
                case 1: title = getResources().getText(R.string.infobox_texttitle_abhaengigkeit).toString(); text = getResources().getText(R.string.infobox_text_abhaengigkeit).toString(); autor =getResources().getText(R.string.infobox_textautor_abhaengigkeit).toString(); quelle=getResources().getText(R.string.infobox_textquelle_abhaengigkeit).toString();break;
                case 2: title = getResources().getText(R.string.infobox_texttitle_auswirkungen).toString(); text = getResources().getText(R.string.infobox_text_auswirkungen).toString(); autor =getResources().getText(R.string.infobox_textautor_auswirkungen).toString(); quelle=getResources().getText(R.string.infobox_textquelle_auswirkungen).toString();break;
                case 3: title = getResources().getText(R.string.infobox_texttitle_schwanger).toString(); text = getResources().getText(R.string.infobox_text_schwanger).toString(); autor =getResources().getText(R.string.infobox_textautor_schwanger).toString(); quelle=getResources().getText(R.string.infobox_textquelle_schwanger).toString();break;
                case 4: title = getResources().getText(R.string.infobox_texttitle_einnahme).toString(); text = getResources().getText(R.string.infobox_text_einnahme).toString(); autor =getResources().getText(R.string.infobox_textautor_einnahme).toString(); quelle=getResources().getText(R.string.infobox_textquelle_einnahme).toString();break;
                case 5: title = getResources().getText(R.string.infobox_texttitle_langzeit).toString(); text = getResources().getText(R.string.infobox_text_langzeit).toString(); autor =getResources().getText(R.string.infobox_textautor_langzeit).toString(); quelle=getResources().getText(R.string.infobox_textquelle_langzeit).toString();break;
                case 6: title = getResources().getText(R.string.infobox_texttitle_safer_use).toString(); text = getResources().getText(R.string.infobox_text_safer_use).toString(); autor =getResources().getText(R.string.infobox_textautor_safer_use).toString(); quelle=getResources().getText(R.string.infobox_textquelle_safer_use).toString();break;
                case 7: title = getResources().getText(R.string.infobox_texttitle_wort).toString(); text = getResources().getText(R.string.infobox_text_wort).toString(); autor =getResources().getText(R.string.infobox_textautor_wort).toString(); quelle=getResources().getText(R.string.infobox_textquelle_wort).toString();break;
                case 8: title = getResources().getText(R.string.infobox_texttitle_aegypter).toString(); text = getResources().getText(R.string.infobox_text_aegypter).toString(); autor =getResources().getText(R.string.infobox_textautor_aegypter).toString(); quelle=getResources().getText(R.string.infobox_textquelle_aegypter).toString();break;
                case 9: title = getResources().getText(R.string.infobox_texttitle_erfindung).toString(); text = getResources().getText(R.string.infobox_text_erfindung).toString(); autor =getResources().getText(R.string.infobox_textautor_erfindung).toString(); quelle=getResources().getText(R.string.infobox_textquelle_erfindung).toString();break;
                case 10: title = getResources().getText(R.string.infobox_texttitle_sumerer).toString(); text = getResources().getText(R.string.infobox_text_sumerer).toString(); autor =getResources().getText(R.string.infobox_textautor_sumerer).toString(); quelle=getResources().getText(R.string.infobox_textquelle_sumerer).toString();break;
                case 11: title = getResources().getText(R.string.infobox_texttitle_trinkgewohnheit).toString(); text = getResources().getText(R.string.infobox_text_trinkgewohnheit).toString(); autor =getResources().getText(R.string.infobox_textautor_trinkgewohnheit).toString(); quelle=getResources().getText(R.string.infobox_textquelle_trinkgewohnheit).toString();break;
                case 12: title = getResources().getText(R.string.infobox_texttitle_erstes_reinheitsgebot).toString(); text = getResources().getText(R.string.infobox_text_erstes_reinheitsgebot).toString(); autor =getResources().getText(R.string.infobox_textautor_erstes_reinheitsgebot).toString(); quelle=getResources().getText(R.string.infobox_textquelle_erstes_reinheitsgebot).toString();break;
                case 13: title = getResources().getText(R.string.infobox_texttitle_reinheitsgebot).toString(); text = getResources().getText(R.string.infobox_text_reinheitsgebot).toString(); autor =getResources().getText(R.string.infobox_textautor_reinheitsgebot).toString(); quelle=getResources().getText(R.string.infobox_textquelle_reinheitsgebot).toString();break;
                case 14: title = getResources().getText(R.string.infobox_texttitle_anstossen).toString(); text = getResources().getText(R.string.infobox_text_anstossen).toString(); autor =getResources().getText(R.string.infobox_textautor_anstossen).toString(); quelle=getResources().getText(R.string.infobox_textquelle_anstossen).toString();break;
                case 15: title = getResources().getText(R.string.infobox_texttitle_alkoholrausch).toString(); text = getResources().getText(R.string.infobox_text_alkoholrausch).toString(); autor =getResources().getText(R.string.infobox_textautor_alkoholrausch).toString(); quelle=getResources().getText(R.string.infobox_textquelle_alkoholrausch).toString();break;
            }
            Spanned html = Html.fromHtml("<p class=\"justify\">"+text+"</><br>"+" Autor: "+autor+"<br>"+"Quelle: "+quelle+"</p>");

            String finalTitle = title;
            txt.setText(finalTitle);
            flowTextView.setText(html);

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int density = dm.densityDpi;

            if (density<480) { // 480dip = xxhdpi (https://developer.android.com/guide/topics/resources/providing-resources#ScreenSizeQualifier)
                flowTextView.setTextSize(25.0f);
            } else {
                flowTextView.setTextSize(45.0f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
