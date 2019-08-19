package polardreams.de.happydrink;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import polardreams.de.happydrink.supportlibs.CircleProgressAnimation;
import polardreams.de.happydrink.supportlibs.CustonAdapter;
import polardreams.de.happydrink.supportlibs.RangeSeekbarAnimation;
import polardreams.de.happydrink.supportlibs.TheCounter;


public class Drinkcounter extends AppCompatActivity {
    //GUI
    private Context c;
    private Activity c_activity;
    private RangeSeekBar seekmenge, seekalkmenge, seekbar_alkabbau;

    private boolean first_step = true;
    private boolean check_permission_storage = false;
    private boolean check_permission_internet = false;
    private boolean overlay_flag = false;
    private boolean isActivityshow = false;
    private boolean rate_flag = false;
    private DonutProgress donut_progress_anz, donut_progress_alkg, donut_progress_promille;
    private ImageButton btn_trinken, btn_kotzen, btn_partyover, btn_save;
    private Button btn_lizenz, btn_time;
    private Handler handler = new Handler();
    private AllAngleExpandableButton drink_btn;
    private ImageView im;
    private RadioButton r_design1, r_design2, r_design3;
    private TextView assi_title_txt;
    private Typeface tf;
    private Toolbar myToolbar;
    private TapTarget t1, t2, t3, t4, t5, t6, t7, t8;
    private TextView his_title_txt, his_starttime_txt, txt_anz, txt_kalorie, txt_promille, txt_prozent, txt_ml;
    private ListView his_lv;
    private TimePicker timepicker;
    private RadioButton time_rad1, time_rad2;
    private AlertDialog alertDialog;
    private AlertDialog.Builder nachricht;
    private CheckBox c0, c1, c2, c3, c4, c5, c6;

    //Preferences
    private int pers_age, pers_weight;
    private boolean pers_sex = false;
    private boolean preferences_status = false;
    private boolean tutorial = false;
    //private boolean promillenachberechnung_flag = false;
    private SharedPreferences info;
    private SharedPreferences.Editor editor;
    private EditText assi_ed_weight, assi_ed_age;
    private RadioButton assi_rad_man, assi_rad_woman;
    private Button assi_btn, partyover_exit, partyover_back, new_party;
    private TextView partyover_txt;
    private int age, weight, sex;
    private float alkabbau;
    private boolean party_status = false;
    private String filename = "";
    private float menge = 0, alkgehalt = 0;
    private Intent i_info;
    private int color_text, color_mark, color_tapsequence_circlecolor, color_tapsequence_textcolor;

    //Counter
    private TheCounter counter;
    private float ziel_anz, ziel_alkkalorie, ziel_bak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        #design
         */
        c = this;
        c_activity = this;
        info = PreferenceManager.getDefaultSharedPreferences(c);
        ini_design(info.getInt("design_key",0));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinkcounter);

        int color = Color.parseColor("#FFFFFF");
        getWindow().getDecorView().setBackgroundColor(color);

        check_internetpermission();


        seekmenge = (RangeSeekBar) findViewById(R.id.seekbar_menge);
        seekmenge.setIndicatorTextDecimalFormat("0");
        seekalkmenge = (RangeSeekBar) findViewById(R.id.seekbar_alkgehalt);
        seekalkmenge.setIndicatorTextDecimalFormat("0");
        seekalkmenge.setValue(2.5f);

        seekalkmenge.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                alkgehalt = Math.round(leftValue);

            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });



        seekmenge.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                menge = Math.round(leftValue);

            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });


        donut_progress_anz = (DonutProgress) findViewById(R.id.donut_progress_anz);
        donut_progress_anz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_history();
            }
        });
        donut_progress_alkg = (DonutProgress) findViewById(R.id.donut_progress_alkg);
        donut_progress_alkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSweetDialog(getResources().getString(R.string.sweetDialog_alkg_title), getResources().getString(R.string.sweetDialog_alkg), R.drawable.ic_percent);
            }
        });
        donut_progress_promille = (DonutProgress) findViewById(R.id.donut_progress_promille);
        donut_progress_promille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSweetDialog(getResources().getString(R.string.sweetDialog_promille_title), getResources().getString(R.string.sweetDialog_promille), R.drawable.ic_permille);
            }
        });

        btn_trinken = (ImageButton) findViewById(R.id.btn_drink);
        btn_trinken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_min_value_rangeseekbar();
                counter.add_drink(menge, alkgehalt);
                update_display();
                if (tutorial) {
                    tutorial_magazin();
                }
            }
        });
        btn_kotzen = (ImageButton) findViewById(R.id.btn_kotz);
        btn_kotzen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.del_drink();
                update_display();
            }
        });

        btn_save = (ImageButton) findViewById(R.id.btn_back);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_lexika();
            }
        });

        btn_partyover = (ImageButton) findViewById(R.id.btn_partyover);
        btn_partyover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_session();
            }
        });

        //Prüfung der Voreinstellungen

        preferences_status = info.getBoolean("assi_status", false);
        rate_flag = info.getBoolean("rateflag_key", false);



        String var1 = info.getString("party_pref_menge", "20f");
        String var2 = info.getString("party_pref_alk", "2.5f");

        float m = Float.parseFloat(var1);
        float a = Float.parseFloat(var2);

        if (m == 0) {
            seekmenge.setValue(20f);
        } else {
            seekmenge.setValue(m);
        }

        if (a == 0) {
            seekalkmenge.setValue(2.5f);
        } else {
            seekalkmenge.setValue(a);
        }

        if (!preferences_status) {
            load_assisstent(true);
        } else {
            load_pers_data(info);
        }//if (!preferences_status) {

        //Ziele festlegen
        konsumziel_entscheiden();

        party_status = info.getBoolean("party_status", false);

        if (party_status) {
            counter = new TheCounter(c, sex, weight, alkabbau);
            counter.load_party(info.getString("party_filename", "/"));
            update_display();
        } else {
            if (preferences_status) {
                counter = new TheCounter(c, sex, weight, alkabbau);
                counter.create_party();
            }
        }

        /*
        #design
         */
        create_design(info.getInt("design_key",0));


        //Tutorial

        if (!info.getBoolean("tutorial_flag", false)) {
            //Start Tutorial
            tutorial = true;
            tutorial_minibar();
        }
        //ini_advertise();
    }


    private void update_display() {
        if (counter.get_anz_drink() <= ziel_anz) {
            handler.post(new CircleProgressAnimation(donut_progress_anz, SystemClock.uptimeMillis(), 0, counter.get_anz_drink(), counter.get_anz_drink(), 80f, 80f, 80f, 10, "", ziel_anz, false));
        } else {
            handler.post(new CircleProgressAnimation(donut_progress_anz, SystemClock.uptimeMillis(), 0, ziel_anz, counter.get_anz_drink(), 80f, 80f, 80f, 10, "", ziel_anz, false));
        }//if (counter.get_anz_drink()<=ziel_anz) {

        if (counter.get_kalorien() <= ziel_alkkalorie) {
            handler.post(new CircleProgressAnimation(donut_progress_alkg, SystemClock.uptimeMillis(), 0, counter.get_kalorien(), counter.get_kalorien(), 0, ziel_alkkalorie/2, ziel_alkkalorie, 1, "", ziel_alkkalorie, false));
        } else {
            handler.post(new CircleProgressAnimation(donut_progress_alkg, SystemClock.uptimeMillis(), 0, ziel_alkkalorie, counter.get_kalorien(), 0, ziel_alkkalorie/2, ziel_alkkalorie, 1, "", ziel_alkkalorie, false));
        }//if (counter.get_alkg()<=ziel_alkg) {

        if (counter.get_BAK() <= ziel_bak) {
            handler.post(new CircleProgressAnimation(donut_progress_promille, SystemClock.uptimeMillis(), 0, counter.get_BAK(), counter.get_BAK(), 0.0f, 0.5f, 1.5f, 10, "", ziel_bak, true));
        } else {
            handler.post(new CircleProgressAnimation(donut_progress_promille, SystemClock.uptimeMillis(), 0, ziel_bak, counter.get_BAK(), 0.0f, 0.5f, 1.5f, 10, "", ziel_bak, true));
        }//if (counter.get_BAK()<=ziel_bak) {
    }

    @Override
    public void onBackPressed() {
            save_session();
        return;
    }

    @Override
    protected void onPause() {
        if (!this.isFinishing()) {
            check_stroagepermission();
            if (check_permission_storage == true && overlay_flag==false && isActivityshow==false) {
                save_session();
            } else {
                if (isActivityshow = true) {
                    isActivityshow = false;
                }
            }
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.menu_pers):load_assisstent(false);break;
            case (R.id.menu_lizenz):showSweetDialog_Lizenz("Lizenz", getResources().getString(R.string.menu_lizenz_txt),R.drawable.ic_percent);break;
            case (R.id.menu_update):update_display();break;
            case (R.id.menu_history):load_history();break;
            case (R.id.menu_datenschutz):InfoPersDaten();break;
            // case (R.id.menu_rate):show_ratetheappDialog();break;
            case (R.id.menu_nachtrag): load_assisstent_time();break;
            case (R.id.menu_lexika): open_lexika();break;
            case (R.id.menu_tutorial): tutorial= true; tutorial_minibar();break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    try {
    if (grantResults != null) {
        switch (requestCode) {
            case (1):
                if (grantResults[0] == 0) {
                    check_permission_storage = true;
                    save_session();
                }
                break;
            case (2):
                if (grantResults[0] == 0) {
                    check_permission_internet = true;
                }
                ;
                break;
        }
    }
    overlay_flag=false;
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    private void set_min_value_rangeseekbar() {
        if (menge < 20) {
            menge = 20;
        }
        if (alkgehalt < 2.5f) {
            alkgehalt = 2.5f;
        }
    }

    private void save_session() { //save oder backpress
        check_stroagepermission();
                if (check_permission_storage) {
                counter.save_party(false);
                editor = info.edit();
                editor.putString("party_pref_menge", String.valueOf(menge));
                editor.putString("party_pref_alk", String.valueOf(alkgehalt));
                editor.putBoolean("rateflag_key", rate_flag);
                editor.commit();
                finish();
            }
    }

    private void close_session() { //party_over
            counter.save_party(true);
            editor = info.edit();
            editor.putBoolean("rateflag_key", rate_flag);
            editor.commit();

    }

    private void new_party () {
        if (preferences_status) {
            counter = new TheCounter(c, sex, weight, alkabbau);
            counter.create_party();
            update_display();
        }
    }

    private void new_session () {
        final Dialog menu = new Dialog(c);
        menu.requestWindowFeature(Window.FEATURE_NO_TITLE);
        menu.setContentView(R.layout.layout_dialog_partyover);
        menu.setCancelable(true);
        partyover_txt = (TextView) menu.findViewById(R.id.menu_partyover_text);
        partyover_txt.setText(getResources().getText(R.string.menu_partyover_txt_text));
        partyover_exit = (Button) menu.findViewById(R.id.menu_partyover_btn_close);
        partyover_exit.setText(getResources().getText(R.string.menu_partyover_back));
        partyover_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.cancel();
            }
        });
        new_party = (Button) menu.findViewById(R.id.menu_partyover_cancle);
        new_party.setText(getResources().getText(R.string.menu_partyover_new_btn));
        new_party.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rate_flag) {
                    close_session();
                    new_party();
                }//if (check_permission_storage) {
             else {
                    close_session();
                    new_party();
            }
            menu.cancel();
        }
        });
        menu.show();
    }


    private void showSweetDialog(String txt_title, String txt, int icon) {
        try {
            new SweetAlertDialog(c, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText(txt_title)
                    .setContentText(txt)
                    .setCustomImage(icon)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSweetDialog_Lizenz(String txt_title, String txt, int icon) {
        try {
            final Dialog menu = new Dialog(c);
            menu.requestWindowFeature(Window.FEATURE_NO_TITLE);
            menu.setContentView(R.layout.layout_dialog_lizenz);
            menu.setCancelable(true);
            btn_lizenz = (Button) menu.findViewById(R.id.btn_lizenz);
            btn_lizenz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menu.cancel();
                }
            });

            menu.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load_drink_btn(int design) {
        drink_btn = (AllAngleExpandableButton)findViewById(R.id.button_expandable);
        final List<ButtonData> buttonDatas = new ArrayList<>();

        /*
        #design
         */
        int getraenkebuttonresource = 0;
        switch (design) {
            case 0: getraenkebuttonresource = R.mipmap.getraenkebutton; break;
            case 1: getraenkebuttonresource = R.mipmap.getraenkebutton1; break;
            case 2: getraenkebuttonresource = R.mipmap.getraenkebutton2; break;
        }


        int[] drawable = {getraenkebuttonresource, R.drawable.fab_btn_bier, R.drawable.fab_btn_koelsch, R.drawable.fab_btn_wein, R.drawable.fab_btn_sekt, R.drawable.fab_btn_cocktail, R.drawable.fab_btn_likoer, R.drawable.fab_btn_schnaps, R.drawable.fab_btn_gwein};



        for (int i = 0; i < drawable.length; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(c, drawable[i], 0);
            buttonDatas.add(buttonData);
        }
        drink_btn.setButtonDatas(buttonDatas);
        drink_btn.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int i) {
                if (i == 1) {//Bier
                    handler.post(new RangeSeekbarAnimation(seekmenge, SystemClock.uptimeMillis(), 20f, 500f));
                    handler.post(new RangeSeekbarAnimation(seekalkmenge, SystemClock.uptimeMillis(), 2.5f, 5f));//Startpunkt muss 2.5f sein siehe (Layout)
                }
                if (i == 2) {//kölsch
                    handler.post(new RangeSeekbarAnimation(seekmenge, SystemClock.uptimeMillis(), 20f, 330f));
                    handler.post(new RangeSeekbarAnimation(seekalkmenge, SystemClock.uptimeMillis(), 2.5f, 5f));//Startpunkt muss 2.5f sein siehe (Layout)
                }
                if (i == 3) {//Wein
                    handler.post(new RangeSeekbarAnimation(seekmenge, SystemClock.uptimeMillis(), 20f, 200f));
                    handler.post(new RangeSeekbarAnimation(seekalkmenge, SystemClock.uptimeMillis(), 2.5f, 13f));//Startpunkt muss 2.5f sein siehe (Layout)
                }
                if (i == 4) {//Sekt
                    handler.post(new RangeSeekbarAnimation(seekmenge, SystemClock.uptimeMillis(), 20f, 150f));
                    handler.post(new RangeSeekbarAnimation(seekalkmenge, SystemClock.uptimeMillis(), 2.5f, 11f));//Startpunkt muss 2.5f sein siehe (Layout)
                }
                if (i == 5) {//Cocktail
                    handler.post(new RangeSeekbarAnimation(seekmenge, SystemClock.uptimeMillis(), 20f, 330f));
                    handler.post(new RangeSeekbarAnimation(seekalkmenge, SystemClock.uptimeMillis(), 2.5f, 15f));//Startpunkt muss 2.5f sein siehe (Layout)
                }

                if (i == 6) {//Likör
                    seekmenge.setValue(20f);
                    handler.post(new RangeSeekbarAnimation(seekalkmenge, SystemClock.uptimeMillis(), 2.5f, 18f));//Startpunkt muss 2.5f sein siehe (Layout)
                }

                if (i == 7) {//Schnaps
                    seekmenge.setValue(20f);
                    handler.post(new RangeSeekbarAnimation(seekalkmenge, SystemClock.uptimeMillis(), 2.5f, 40f));//Startpunkt muss 2.5f sein siehe (Layout)
                }

                if (i == 8) {//Glühwein
                    handler.post(new RangeSeekbarAnimation(seekmenge, SystemClock.uptimeMillis(), 20f, 200f));
                    handler.post(new RangeSeekbarAnimation(seekalkmenge, SystemClock.uptimeMillis(), 2.5f, 12f));//Startpunkt muss 2.5f sein siehe (Layout)
                }

                if (tutorial) {
                    tutorial_regler();
                }

            }

            @Override
            public void onExpand() {

            }

            @Override
            public void onCollapse() {

            }
        });
    }

    /*
    #design
     */

    private void tutorial_minibar () {
        t1 = TapTarget.forView(findViewById(R.id.button_expandable), getResources().getString(R.string.rundgang_Minibar_title), getResources().getString(R.string.rundgang_Minibar)).textColor(color_tapsequence_textcolor).textTypeface(tf).targetCircleColor(color_mark).outerCircleColor(color_tapsequence_circlecolor);
        new TapTargetSequence(this)
                .targets(t1)
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {

                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence

                }).start();

    }
    private void tutorial_regler () {
        t2 = TapTarget.forView(findViewById(R.id.seekbar_menge), getResources().getString(R.string.rundgang_Mengenregler_title), getResources().getString(R.string.rundgang_Mengenregler)).textColor(color_tapsequence_textcolor).textTypeface(tf).targetCircleColor(color_mark).outerCircleColor(color_tapsequence_circlecolor);
        new TapTargetSequence(this)
                .targets(t2)
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        tutorial_trinken();
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        tutorial_trinken();
                    }
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence

                }).start();

    }
    private void tutorial_trinken () {
        t3 = TapTarget.forView(findViewById(R.id.btn_drink), getResources().getString(R.string.rundgang_Trinkbutton_title), getResources().getString(R.string.rundgang_Trinkbutten)).textColor(color_tapsequence_textcolor).textTypeface(tf).targetCircleColor(color_mark).outerCircleColor(color_tapsequence_circlecolor);
        new TapTargetSequence(this)
                .targets(t3)
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {

                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence

                }).start();

    }

    private void tutorial_magazin () {
        t4 = TapTarget.forView(findViewById(R.id.btn_back), getResources().getString(R.string.rundgang_Magazin_title), getResources().getString(R.string.rundgang_Magazin)).textColor(color_tapsequence_textcolor).textTypeface(tf).targetCircleColor(color_mark).outerCircleColor(color_tapsequence_circlecolor);
        new TapTargetSequence(this)
                .targets(t4)
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        tutorial = false;
                        editor = info.edit();
                        editor.putBoolean("tutorial_flag", true);
                        editor.commit();
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        tutorial = false;
                        editor = info.edit();
                        editor.putBoolean("tutorial_flag", true);
                        editor.commit();
                    }
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence

                }).start();

    }

    private void InfoPersDaten () {
        try {
            t1 = TapTarget.forView(findViewById(R.id.btn_drink), getResources().getString(R.string.Info_Datenerhebung_title), getResources().getString(R.string.Info_Datenerhebung)).textColor(color_tapsequence_textcolor).textTypeface(tf).targetCircleColor(color_mark).outerCircleColor(color_tapsequence_circlecolor);
            new TapTargetSequence(this)
                    .targets(t1)
                    .listener(new TapTargetSequence.Listener() {
                        // This listener will tell us when interesting(tm) events happen in regards
                        // to the sequence
                        @Override
                        public void onSequenceFinish() {
                            // Yay

                        }

                        @Override
                        public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                            if (lastTarget.equals(t1)) {
                                //analyse_tutorial(1);
                            }
                        }

                        @Override
                        public void onSequenceCanceled(TapTarget lastTarget) {
                            // Boo

                        }
                    }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void check_stroagepermission () {
        int check_read_write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int check_read_read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (check_read_write != PackageManager.PERMISSION_GRANTED
                || check_read_read != PackageManager.PERMISSION_GRANTED) {

            if (overlay_flag==false) {
                nachricht = new AlertDialog.Builder(this);
                nachricht.setMessage(getResources().getString(R.string.check_permission_storage));
                nachricht.setPositiveButton(getResources().getString(R.string.check_permission_btn), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        overlay_flag = true;
                        try {
                            ActivityCompat.requestPermissions(c_activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                            }, 1);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                AlertDialog dialog = nachricht.create();
                dialog.show();
            }
        } else {
            check_permission_storage=true;
        }
    }

    private void check_internetpermission () {
        int check_internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (check_internet != PackageManager.PERMISSION_GRANTED) {

            nachricht =  new AlertDialog.Builder(this);
            nachricht.setMessage(getResources().getString(R.string.check_permission_internet));
            nachricht.setPositiveButton(getResources().getString(R.string.check_permission_btn), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    try {
                        ActivityCompat.requestPermissions(c_activity, new String[]{
                                Manifest.permission.INTERNET
                        }, 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            AlertDialog dialog = nachricht.create();
            dialog.show();
        }else {
            check_permission_internet=true;
        }
    }

    private void load_assisstent_time () {
        final Dialog menu = new Dialog(c);
        menu.requestWindowFeature(Window.FEATURE_NO_TITLE);
        menu.setContentView(R.layout.layout_setlasttimemenu);
        menu.setCancelable(true);

        timepicker = (TimePicker)menu.findViewById(R.id.timepicker);
        timepicker.setIs24HourView(true);
        time_rad1 = (RadioButton)menu.findViewById(R.id.time_rad_1);
        //time_rad2 = (RadioButton)menu.findViewById(R.id.time_rad_2);

        btn_time = (Button)menu.findViewById(R.id.time_btn);
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time_rad1.isChecked()) {
                    Date date = new Date();
                    //Tag
                    SimpleDateFormat day_format = new SimpleDateFormat("dd");
                    String str_today = day_format.format(date);
                    int day = Integer.valueOf(str_today)-1;
                    //Jahr und Monat
                    SimpleDateFormat year_month_format = new SimpleDateFormat("yyyy-MM");
                    String year_month = year_month_format.format(date);
                    int year = Integer.valueOf(year_month.substring(0,year_month.indexOf("-")));
                    int month = Integer.valueOf(year_month.substring(year_month.indexOf("-")+1,year_month.length()))-1;//Januar = 0
                    int houre = timepicker.getCurrentHour();//0-24!!!
                    int minute = timepicker.getCurrentMinute();
                    Calendar c = Calendar.getInstance();
                    c.set(year,month,day,houre,minute);
                    Date result = c.getTime();
                    counter.set_lastTime(result);
                } else {
                    Date date = new Date();
                    //Tag
                    SimpleDateFormat day_format = new SimpleDateFormat("dd");
                    String str_today = day_format.format(date);
                    int day = Integer.valueOf(str_today);
                    //Jahr und Monat
                    SimpleDateFormat year_month_format = new SimpleDateFormat("yyyy-MM");
                    String year_month = year_month_format.format(date);
                    int year = Integer.valueOf(year_month.substring(0,year_month.indexOf("-")));
                    int month = Integer.valueOf(year_month.substring(year_month.indexOf("-")+1,year_month.length()))-1;//Januar = 0
                    int houre = timepicker.getCurrentHour();//0-24!!!
                    int minute = timepicker.getCurrentMinute();
                    Calendar c = Calendar.getInstance();
                    c.set(year,month,day,houre,minute);
                    Date result = c.getTime();
                    counter.set_lastTime(result);
                }//if (time_rad1.isChecked()) {
                update_display();
                menu.cancel();
            }
        });
        menu.show();
    }
    
    private void load_assisstent (final boolean firsttime) {
        final Dialog menu = new Dialog(c);
        menu.requestWindowFeature(Window.FEATURE_NO_TITLE);
        menu.setContentView(R.layout.layout_dialog_assisstent);
        menu.setCancelable(false);
        //Titel
        assi_title_txt = (TextView) menu.findViewById(R.id.assi_title);
        assi_title_txt.setPaintFlags(assi_title_txt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        assi_title_txt.setTextSize(30.0f);

        assi_ed_weight = (EditText) menu.findViewById(R.id.assi_ed_weight);
        assi_ed_age = (EditText) menu.findViewById(R.id.assi_ed_age);
        seekbar_alkabbau = (RangeSeekBar) menu.findViewById(R.id.seekbar_alkabbau);
        seekbar_alkabbau.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                alkabbau = leftValue;
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        /*
        #design
         */
        switch (info.getInt("design_key",0)) {
            case 0: seekbar_alkabbau.setProgressColor(Color.WHITE, getResources().getColor(R.color.colorAccent));break;
            case 1: seekbar_alkabbau.setProgressColor(Color.WHITE, getResources().getColor(R.color.colorAccent1));break;
            case 2: seekbar_alkabbau.setProgressColor(Color.WHITE, getResources().getColor(R.color.colorAccent2));break;
        }

        r_design1 = (RadioButton)menu.findViewById(R.id.radioButton1);
        r_design2 = (RadioButton)menu.findViewById(R.id.radioButton2);
        r_design3 = (RadioButton)menu.findViewById(R.id.radioButton3);

        assi_rad_man = (RadioButton) menu.findViewById(R.id.assi_radio_man);
        assi_rad_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekbar_alkabbau.setValue(0.15f);
            }
        });

        assi_rad_woman = (RadioButton) menu.findViewById(R.id.assi_radio_woman);
        assi_rad_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekbar_alkabbau.setValue(0.10f);
            }
        });

        assi_btn = (Button) menu.findViewById(R.id.assi_btn);
        assi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor = info.edit();
                boolean convert_status_age = true;
                boolean convert_status_weight = true;

                try {
                    weight = Integer.valueOf(assi_ed_weight.getText().toString());
                    convert_status_weight = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    age = Integer.valueOf(assi_ed_age.getText().toString());
                    convert_status_age = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (convert_status_age || convert_status_weight) {
                    if (convert_status_age) {
                        assi_ed_age.setError(getResources().getString(R.string.assisstent_error_age));
                    } else {
                        assi_ed_weight.setError(getResources().getString(R.string.assisstent_error_weight));
                    }
                } else {


                    editor.putString("assi_age", String.valueOf(age));
                    editor.putString("assi_weight", String.valueOf(weight));
                    if (assi_rad_man.isChecked()) {
                        editor.putString("assi_sex", "0");
                    } else {
                        editor.putString("assi_sex", "1");
                    }//if
                    editor.putBoolean("assi_status", true);
                    editor.putString("party_pref_menge", String.valueOf(menge));
                    editor.putString("party_pref_alk", String.valueOf(alkgehalt));
                    editor.putFloat("assi_alk_dec", alkabbau);

                    /*
                    #design
                     */
                    if (r_design1.isChecked()) {
                        editor.putInt("design_key",0);
                    }//if (r_design1.isChecked()) {
                    if (r_design2.isChecked()) {
                        editor.putInt("design_key",1);
                    }//if (r_design1.isChecked()) {
                    if (r_design3.isChecked()) {
                        editor.putInt("design_key",2);
                    }//if (r_design1.isChecked()) {

                    if (firsttime) {
                        counter = new TheCounter(c, sex, weight, alkabbau);
                        counter.create_party();

                        editor.commit();
                        preferences_status=true;
                        menu.cancel();
                    } else {
                        counter.set_sex(sex);
                        counter.set_weight(weight);
                        counter.set_alkabbau(alkabbau);
                        editor.commit();
                        preferences_status=true;
                        menu.cancel();
                        counter.update_party();
                        konsumziel_entscheiden();//Zielsetzung an neue Personendaten anpassen
                        update_display();
                    }
                    /*
                    #design
                     */
                    //hier wird die Activity neugestartet, damit AppTheme angezeigt wird
                    save_session();
                    Intent mIntent = getIntent();
                    finish();
                    startActivity(mIntent);

                }//if (convert_status_age || convert_status_weight) {
            }//public void onClick(View v) {
        });//new View.OnClickListener()

        if (!firsttime) {
            assi_ed_age.setText(String.valueOf(age));
            assi_ed_weight.setText(String.valueOf(weight));
            seekbar_alkabbau.setValue(info.getFloat("assi_alk_dec",0.1f));
            if (sex==0) {
                assi_rad_man.setChecked(true);

            } else {
                assi_rad_woman.setChecked(true);
            }
        } else {
            if (sex==0) {
                assi_rad_man.setChecked(true);
                seekbar_alkabbau.setValue(0.15f);

            } else {
                assi_rad_woman.setChecked(true);
                seekbar_alkabbau.setValue(0.10f);
            }
        }
        menu.show();
    }

    private void load_history () {
        final Dialog menu = new Dialog(c);
        menu.requestWindowFeature(Window.FEATURE_NO_TITLE);
        menu.setContentView(R.layout.layout_dialog_history);
        menu.setCancelable(true);

        //Titel
        his_title_txt = (TextView) menu.findViewById(R.id.title_history);
        his_title_txt.setPaintFlags(his_title_txt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        his_title_txt.setTextSize(30.0f);
        his_title_txt.setText(getResources().getString(R.string.history_title));
        his_starttime_txt = (TextView) menu.findViewById(R.id.starttime_history);


        HashMap<Integer, Pair<Float,Float>> hilf = counter.get_drinks();
        HashMap<Integer, Pair<Integer,String>> hilf_bak_null = counter.get_BAK_NULL();

        his_starttime_txt.setText("Startzeit um "+hilf_bak_null.get(0).second+"\n");
        int hashMapsize = hilf.size()+hilf_bak_null.size()-1;
        String[] textString = new String[hashMapsize];
        int[] drawableIds = new int[hashMapsize+1];
        int za = -1;

        for (int n =0; n<(hilf.size());n++) {
            za++;
            textString[za] ="("+String.valueOf(n+1)+")"+getResources().getString(R.string.history_menge)+" "
                    +String.valueOf(hilf.get(n).first)+" "
                    +getResources().getString(R.string.history_g)+"\n"
                    +getResources().getString(R.string.history_alk)+" "
                    +String.valueOf(hilf.get(n).second)+" "
                    +getResources().getString(R.string.history_prozent);

            if (hilf.get(n).second<=9f) {
                drawableIds[za] = R.drawable.fab_btn_bier;
            }
            if (hilf.get(n).second>=9f && hilf.get(n).second<12f) {
                drawableIds[za] = R.drawable.fab_btn_sekt;
            }
            if (hilf.get(n).second>=12f && hilf.get(n).second<13f) {
                drawableIds[za] = R.drawable.fab_btn_gwein;
            }
            if (hilf.get(n).second>=13f && hilf.get(n).second<14f) {
                drawableIds[za] = R.drawable.fab_btn_wein;
            }
            if (hilf.get(n).second>=14f && hilf.get(n).second<18f) {
                drawableIds[za] = R.drawable.fab_btn_cocktail;
            }
            if (hilf.get(n).second>=18f && hilf.get(n).second<35f) {
                drawableIds[za] = R.drawable.fab_btn_likoer;
            }
            if (hilf.get(n).second>=35f) {
                drawableIds[za] = R.drawable.fab_btn_schnaps;
            }

            int anz = hilf_bak_null.size();
            for (int a = 1; a<anz; a++) {
                if (n == hilf_bak_null.get(a).first) {
                    za++;
                    textString[za]="Ausgenüchtert um "+hilf_bak_null.get(a).second;
                    drawableIds[za] = R.drawable.ic_permille;
                }//if (n == hilf_bak_null.get(a).first) {
            }//for (int a = 0; a<anz; a++) {
        }//for (int n =0; n<(hilf.size());n++) {

        CustonAdapter adapter = new CustonAdapter(this,  textString, drawableIds);


        his_lv = (ListView)menu.findViewById(R.id.lv_history);
        his_lv.setAdapter(adapter);

        menu.show();
    }

    private void set_ziele (float ziel_anz, float ziel_alkkalorie, float ziel_bak) {
        //Wo soll die Anzeige stoppen, diese Variablen müssen übereinstimmen mit den GUI Optionen Dounat.setDounat_max
        this.ziel_anz = ziel_anz;
        this.ziel_alkkalorie = ziel_alkkalorie;
        this.ziel_bak = ziel_bak;
        donut_progress_alkg.setMax((int)ziel_alkkalorie);
    }

    private void konsumziel_entscheiden () {
        //Ziele der Person festlegen (Grenzen des KOnsums)
        if (sex == 0) {
            //Ziele für einen Mann
            set_ziele(8,800f,4f);
        } else {
            //Ziele für eine Frau
            set_ziele(8,600f,4f);
        }
        //Quelle: https://www.tk.de/techniker/gesund-leben/ernaehrung/uebergewicht-und-diaet/wie-viele-kalorien-pro-tag-2006758
        // Altersspanne 25 - 51 Jahren
    }

    private void load_pers_data (SharedPreferences info) {
        age = Integer.valueOf(info.getString("assi_age", "0"));
        weight = Integer.valueOf(info.getString("assi_weight", "0"));
        sex = Integer.valueOf(info.getString("assi_sex", "0"));
        alkabbau = info.getFloat("assi_alk_dec", 0.1f);
    }

    private void open_lexika () {
        isActivityshow = true;
        i_info = new Intent (c, InfoActivity.class);
        i_info.putExtra("design",info.getInt("design_key",0));
        startActivity(i_info);
    }

    private void create_design (int design) {
        /*
        #design
         */
        switch (design) {
            case 0:
                //TypeFace
                tf = ResourcesCompat.getFont(c, R.font.caviardreams);
                color_text = R.color.color_display;
                color_mark = R.color.colorAccent;
                color_tapsequence_circlecolor = R.color.colorPrimary;
                color_tapsequence_textcolor = R.color.color_display;

                        //Titel nach fonts ausrichten
                myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
                myToolbar.setLogo(getResources().getDrawable(R.mipmap.logo));
                myToolbar.setTitle("");
                setSupportActionBar(myToolbar);

                //Hintergrundbild
                im = (ImageView) findViewById(R.id.imageView);
                Glide.with(this).load(R.mipmap.disco_klein).into(im);

                //Buttons
                btn_partyover.setImageResource(R.mipmap.party);
                btn_save.setImageResource(R.mipmap.buch);
                btn_trinken.setImageResource(R.mipmap.prost);
                btn_kotzen.setImageResource(R.mipmap.up);

                //Regler
                seekalkmenge.setProgressColor(Color.WHITE, getResources().getColor(R.color.colorPrimaryDark));
                seekalkmenge.setTickMarkTextColor(getResources().getColor(R.color.color_display));
                seekalkmenge.setTypeface(tf);
                seekmenge.setProgressColor(Color.WHITE, getResources().getColor(R.color.colorPrimaryDark));
                seekmenge.setTickMarkTextColor(getResources().getColor(R.color.color_display));
                seekmenge.setTypeface(tf);

                //Dounat
                donut_progress_anz.setTextColor(getResources().getColor(R.color.color_display));
                donut_progress_alkg.setTextColor(getResources().getColor(R.color.color_display));
                donut_progress_promille.setTextColor(getResources().getColor(R.color.color_display));


                //TextViews
                txt_anz = (TextView) findViewById(R.id.txt_anz);
                txt_anz.setTypeface(ResourcesCompat.getFont(c, R.font.caviardreams));
                txt_anz.setTextColor(getResources().getColor(R.color.color_display));
                txt_kalorie = (TextView) findViewById(R.id.txt_g);
                txt_kalorie.setTypeface(ResourcesCompat.getFont(c, R.font.caviardreams));
                txt_kalorie.setTextColor(getResources().getColor(R.color.color_display));
                txt_promille = (TextView) findViewById(R.id.txt_p);
                txt_promille.setTypeface(ResourcesCompat.getFont(c, R.font.caviardreams));
                txt_promille.setTextColor(getResources().getColor(R.color.color_display));
                txt_ml = (TextView) findViewById(R.id.txt_ml);
                txt_ml.setTypeface(ResourcesCompat.getFont(c, R.font.caviardreams));
                txt_ml.setTextColor(getResources().getColor(R.color.color_display));
                txt_prozent = (TextView) findViewById(R.id.txt_prozent);
                txt_prozent.setTypeface(ResourcesCompat.getFont(c, R.font.caviardreams));
                txt_prozent.setTextColor(getResources().getColor(R.color.color_display));

                load_drink_btn(0);
                break;
            case 1:
                //TypeFace
                tf = ResourcesCompat.getFont(c, R.font.kannada_sangam_mm);
                color_text = R.color.colorText1;
                color_mark = R.color.colorAccent1;

                color_tapsequence_circlecolor = R.color.colorPrimary1;
                color_tapsequence_textcolor = R.color.colorText1;


                //Titel nach fonts ausrichten
                myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
                myToolbar.setLogo(getResources().getDrawable(R.mipmap.logo1));
                myToolbar.setTitle("");
                setSupportActionBar(myToolbar);

                //Hintergrundbild
                im = (ImageView) findViewById(R.id.imageView);

                Glide.with(this).load(R.mipmap.backroundimage1).skipMemoryCache(true).priority(Priority.IMMEDIATE).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(im);

                //Buttons
                btn_partyover.setImageResource(R.mipmap.party1);
                btn_save.setImageResource(R.mipmap.buch1);
                btn_trinken.setImageResource(R.mipmap.prost1);
                btn_kotzen.setImageResource(R.mipmap.up1);

                //Regler
                seekalkmenge.setProgressColor(Color.WHITE, getResources().getColor(R.color.colorPrimaryDark1));
                seekalkmenge.setTickMarkTextColor(getResources().getColor(R.color.colorseekbarText1));
                seekalkmenge.setTypeface(tf);

                seekmenge.setProgressColor(Color.WHITE, getResources().getColor(R.color.colorPrimaryDark1));
                seekmenge.setTickMarkTextColor(getResources().getColor(R.color.colorseekbarText1));
                seekmenge.setTypeface(tf);

                //Dounat
                donut_progress_anz.setTextColor(getResources().getColor(R.color.colorText1));
                donut_progress_alkg.setTextColor(getResources().getColor(R.color.colorText1));
                donut_progress_promille.setTextColor(getResources().getColor(R.color.colorText1));

                //TextViews
                txt_anz = (TextView) findViewById(R.id.txt_anz);
                txt_anz.setTypeface(ResourcesCompat.getFont(c, R.font.kannada_sangam_mm));
                txt_anz.setTextColor(getResources().getColor(R.color.colorText1));
                txt_kalorie = (TextView) findViewById(R.id.txt_g);
                txt_kalorie.setTypeface(ResourcesCompat.getFont(c, R.font.kannada_sangam_mm));
                txt_kalorie.setTextColor(getResources().getColor(R.color.colorText1));
                txt_promille = (TextView) findViewById(R.id.txt_p);
                txt_promille.setTypeface(ResourcesCompat.getFont(c, R.font.kannada_sangam_mm));
                txt_promille.setTextColor(getResources().getColor(R.color.colorText1));
                txt_ml = (TextView) findViewById(R.id.txt_ml);
                txt_ml.setTypeface(ResourcesCompat.getFont(c, R.font.kannada_sangam_mm));
                txt_ml.setTextColor(getResources().getColor(R.color.colorText1));
                txt_prozent = (TextView) findViewById(R.id.txt_prozent);
                txt_prozent.setTypeface(ResourcesCompat.getFont(c, R.font.kannada_sangam_mm));
                txt_prozent.setTextColor(getResources().getColor(R.color.colorText1));

                load_drink_btn(1);
                break;
            case 2:
                //TypeFace
                tf = ResourcesCompat.getFont(c, R.font.presa);

                color_text = R.color.colorText2;
                color_mark = R.color.colorAccent2;
                color_tapsequence_circlecolor = R.color.colorTapSequenzCircle;
                color_tapsequence_textcolor = R.color.colorTapSequenzText;

                //Titel nach fonts ausrichten
                myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
                myToolbar.setLogo(getResources().getDrawable(R.mipmap.logo2));
                myToolbar.setTitle("");
                setSupportActionBar(myToolbar);

                //Donut TextColor
                donut_progress_anz.setTextColor(getResources().getColor(R.color.colordonat));
                donut_progress_alkg.setTextColor(getResources().getColor(R.color.colordonat));
                donut_progress_promille.setTextColor(getResources().getColor(R.color.colordonat));

                //Hintergrundbild
                im = (ImageView) findViewById(R.id.imageView);
                Glide.with(this).load(R.mipmap.backroundimage2).into(im);

                //Buttons
                btn_partyover.setImageResource(R.mipmap.party2);
                btn_save.setImageResource(R.mipmap.buch2);
                btn_trinken.setImageResource(R.mipmap.prost2);
                btn_kotzen.setImageResource(R.mipmap.up2);

                //Regler
                seekalkmenge.setProgressColor(Color.WHITE, getResources().getColor(R.color.colorPrimaryDark2));
                seekalkmenge.setTickMarkTextColor(Color.WHITE);
                seekalkmenge.setTypeface(tf);
                seekalkmenge.setBackground(getDrawable(R.drawable.seekbar_bachround));

                seekmenge.setProgressColor(Color.WHITE, getResources().getColor(R.color.colorPrimaryDark2));
                seekmenge.setTickMarkTextColor(Color.WHITE);
                seekmenge.setTypeface(tf);
                seekmenge.setBackground(getDrawable(R.drawable.seekbar_bachround));

                //TextViews
                txt_anz = (TextView) findViewById(R.id.txt_anz);
                txt_anz.setTypeface(tf, Typeface.BOLD);
                txt_anz.setTextColor(getResources().getColor(R.color.colorText2));
                txt_kalorie = (TextView) findViewById(R.id.txt_g);
                txt_kalorie.setTypeface(tf, Typeface.BOLD);
                txt_kalorie.setTextColor(getResources().getColor(R.color.colorText2));
                txt_promille = (TextView) findViewById(R.id.txt_p);
                txt_promille.setTypeface(tf, Typeface.BOLD);
                txt_promille.setTextColor(getResources().getColor(R.color.colorText2));
                txt_ml = (TextView) findViewById(R.id.txt_ml);
                txt_ml.setTypeface(tf, Typeface.BOLD);
                txt_ml.setTextColor(getResources().getColor(R.color.colorText2));
                txt_prozent = (TextView) findViewById(R.id.txt_prozent);
                txt_prozent.setTypeface(tf, Typeface.BOLD);
                txt_prozent.setTextColor(getResources().getColor(R.color.colorText2));

                load_drink_btn(2);
                break;
        }//switch (design) {
    }

    private void ini_design (int desgin) {
        switch (desgin) {
            case 0: setTheme(R.style.AppTheme); break;
            case 1: setTheme(R.style.AppTheme1); break;
            case 2: setTheme(R.style.AppTheme2); break;
        }
    }

    
}