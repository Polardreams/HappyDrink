package polardreams.de.happydrink.supportlibs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import polardreams.de.happydrink.R;

/**
 * Created by David on 16.02.2018.
 */

public class TheCounter {

    private final String filepath = "/Partyprotokoll";
    private final String path = Environment.getExternalStorageDirectory().toString();
    private Date akt_datum;
    private String date_start;
    private int lfnr;
    private float alkg, BAK, alkabbau, kalorien;
    private int geschlecht, gewicht;
    private boolean isBAK_NULL = false;
    private boolean isTimeChange = false;


    private HashMap<Integer, Pair<Float,Float>> drinks = new HashMap<>(); //nr;(menge,alk_gehalt)
    private HashMap<Integer, Pair<Integer,String>> BAK_NULL = new HashMap<>(); //nr;zeitstring


    private final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
    private final SimpleDateFormat time_date = new SimpleDateFormat("yyyy-MM-dd;HH:mm:ss");
    private Context c;
    private String filename;
    private SharedPreferences.Editor editor;
    private SharedPreferences info;


    public TheCounter (Context context, int geschlecht, int gewicht, float alkabbau) {
        this.c = context;
        this.geschlecht = geschlecht;
        this.gewicht = gewicht;
        this.alkabbau = alkabbau;
    }

    public void set_weight (int gewicht) {
        this.gewicht = gewicht;
    }

    public void set_alkabbau (float alkabbau) {
        this.alkabbau = alkabbau;
    }

    public void set_sex (int geschlecht) {
        this.geschlecht = geschlecht;
    }

    public void create_party() {
        akt_datum = new Date();
        filename = date.format(akt_datum) + "_" + time.format(akt_datum)+"_Party.txt";
        filename = filename.replace(":","_");
        date_start = date.format(akt_datum) + ";" + time.format(akt_datum);
        BAK_NULL.put(0,new Pair<Integer, String>(0,date_start));
        lfnr = 0;
        alkg = 0f;
        kalorien=0f;
        BAK = 0f;
    }

    public void set_lastTime (Date datetime) {
        String s_newdate = date.format(datetime) + ";" + time.format(datetime);
        s_newdate = s_newdate.substring(0,s_newdate.length()-2);
        s_newdate = s_newdate.concat("00");

        int anz_drink = BAK_NULL.size()-1;
        int var_drinklistpos =BAK_NULL.get(anz_drink).first;

        for (int a = anz_drink; a>-1; a--) {
        try {
            String s = BAK_NULL.get(a).second;
            s = s.substring(0,s.length()-2);
            s = s.concat("00");
            Date olddate = time_date.parse(s);
            Date newdate = time_date.parse(s_newdate);

            if (newdate.before(olddate) || newdate.equals(olddate)) {
                var_drinklistpos =BAK_NULL.get(a).first;
                BAK_NULL.remove(a);
            }//if (newdate.before(olddate) || newdate.equals(olddate)) {
        } catch (ParseException e) {
            e.printStackTrace();
        }//try {
        }//for (int a = anz_drink; a>0; a--) {

        BAK_NULL.put(BAK_NULL.size(),new Pair<Integer, String>(var_drinklistpos,s_newdate));

        isTimeChange = true;
        calculate_lfnr_BAK_alkg(false);
    }

    /*
    String newdate = date.format(datetime) + ";" + time.format(datetime);
        int anz_drink = BAK_NULL.get(BAK_NULL.size()-1).first;
        BAK_NULL.remove(BAK_NULL.size()-1);
        BAK_NULL.put(BAK_NULL.size(),new Pair<Integer, String>(anz_drink,newdate));
        isTimeChange = true;
        calculate_lfnr_BAK_alkg(false);
     */

    public void load_party(String filename) {
        this.filename = filename;
    //Laderoutine
        char[] inputBuffer = new char[999999];
        File add = new File(path+filepath+"/"+filename);//entries[Index des Ausgewählten Item]
        String lade_str="";
        try {
            FileInputStream fileinputstream = new FileInputStream(add);
            InputStreamReader inputstreamreader = new InputStreamReader(fileinputstream);//Zeiger melden an den leser
            inputstreamreader.read(inputBuffer);//Leser liest in einen Puffer (Char Typ )
            lade_str = new String(inputBuffer);//der Puffer wird der Textvariablen übergeben
            inputstreamreader.close();//Datei geschlossen

            //Auslesen der Daten
            int index =0;
            index=lade_str.indexOf('\n')+1;
            date_start = lade_str.substring(0, index-1);
            BAK_NULL.put(0,new Pair<Integer, String>(0,date_start));

            boolean flag_readfile = true;
            while (flag_readfile) {
                int index2 = lade_str.indexOf( '\n', index);
                if (index2 != -1 ) {
                    String str = lade_str.substring(index, index2);

                    if (str.contains("NULL ")) {
                        BAK_NULL.put(BAK_NULL.size(), new Pair<Integer, String>(drinks.size()-1, str.substring(5, str.length())));
                        index = index2+1;
                    } else {
                        int i1=str.indexOf(';');
                        int i2 =str.indexOf(';', i1+1);
                        drinks.put(Integer.valueOf(str.substring(0,i1)),new Pair<Float, Float>(Float.valueOf(str.substring(i1+1,i2)),Float.valueOf(str.substring(i2+1,str.length()))));
                        index = index2+1;
                    }
                } else {
                    flag_readfile = false;
                }//if (index2 < lade_str.length()) {
            }//while (flag_readfile) {
/**
            if (drinks.size()-1 == BAK_NULL.get(BAK_NULL.size()-1).first) {
                isBAK_NULL = true;
            }
**/
            calculate_lfnr_BAK_alkg(false);

        } catch(Exception e){
            Toast.makeText(c, c.getResources().getText(R.string.counter_load_error), Toast.LENGTH_LONG).show();
            create_party();
            e.printStackTrace();
        }//Try
    }

    public void update_party () {
        calculate_lfnr_BAK_alkg(false);
    }

    public void save_party(boolean partyover) {
        try {
            if (get_anz_drink()>0) {
                File meinspeicherort_ex = new File(path + filepath);
                if (!meinspeicherort_ex.canRead()) {
                    meinspeicherort_ex.mkdir();
                    Toast.makeText(c, c.getResources().getText(R.string.counter_create_file), Toast.LENGTH_LONG).show();
                }//if (meinspeicherort_ex.canRead() == false) {


                //String s = "Das ist ein Beispieltext zum schreiben einer Datei."+'\n'+"Die nächste Zeile ist auch ein Beispieltext.";

                StringBuilder strbuild = new StringBuilder();
                strbuild.append(BAK_NULL.get(0).second.toString() + '\n');

                boolean flag_NULL = false;
                for (int n = 0; n < drinks.size(); n++) {
                    flag_NULL = false;
                    for (int m = 0; m < BAK_NULL.size() - 1; m++) {
                        if (BAK_NULL.get(m + 1).first == n) {
                            strbuild.append(n + ";" + drinks.get(n).first + ";" + drinks.get(n).second + '\n');
                            strbuild.append("NULL " + BAK_NULL.get(m + 1).second.toString() + '\n');
                            flag_NULL = true;
                        }//if (BAK_NULL.get(n)!=null) {
                    }
                    if (flag_NULL == false) {
                        strbuild.append(n + ";" + drinks.get(n).first + ";" + drinks.get(n).second + '\n');
                    }
                }//for (int n = 0; n<drinks.size();n++) {

                String save_meldung = "";
                if (partyover) {
                    strbuild.append("END " + date.format(new Date()) + ";" + time.format(new Date()));
                    //Speichern und beenden der Preference
                    info = PreferenceManager.getDefaultSharedPreferences(c);
                    editor = info.edit();
                    editor.putBoolean("party_status", false);
                    editor.putString("party_filename", "/");
                    editor.commit();
                    save_meldung = c.getResources().getText(R.string.counter_save_successful_end).toString();
                } else {
                    //Speichern der Preference
                    info = PreferenceManager.getDefaultSharedPreferences(c);
                    editor = info.edit();
                    editor.putBoolean("party_status", true);
                    editor.putString("party_filename", filename);
                    editor.commit();
                    save_meldung = c.getResources().getText(R.string.counter_save_successful).toString();
                }//if (partyover) {

                String text = strbuild.toString();
                File file = new File(meinspeicherort_ex, filename);
                FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                //Einbetten des String in eine Textdatei
                osw.write(text);
                osw.flush();
                Toast.makeText(c, save_meldung, Toast.LENGTH_SHORT).show();
            } else {
                //Speichern der Preference
                info = PreferenceManager.getDefaultSharedPreferences(c);
                editor = info.edit();
                editor.putBoolean("party_status", false);
                editor.putString("party_filename", "/");
                editor.commit();
            }
        } catch (IOException e) {
            Toast.makeText(c, c.getResources().getText(R.string.counter_save_error), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }//Try
    }

    public void add_drink (float menge, float gehalt) {
        drinks.put(drinks.size(), new Pair<Float, Float>(menge,gehalt));
        calculate_lfnr_BAK_alkg(true);
    }

    public void del_drink () {
        if (drinks.size()>0) {
            drinks.remove(drinks.size()-1);//letzten Eintrag löschen
            calculate_lfnr_BAK_alkg(false );
        }//if (drinks.size()>0) {

    }

    public float get_alkg () {
        return alkg;
    }

    public float get_kalorien () {
        return kalorien;
    }


    public float get_BAK () {
        return BAK;
    }

    public float get_anz_drink () {
        return drinks.size();
    }

    private void calculate_lfnr_BAK_alkg (boolean isadd) {
        //Berechnen der Daten
        lfnr = drinks.size()-1;
        if (!isadd) {
            if (drinks.size()!=0) {
                alkg = 0f;
                for (int n = 0; n < drinks.size(); n++) {
                    alkg = alkg + drinks.get(n).first * (drinks.get(n).second / 100) * 0.8f;
                }//for (int n = 0; n<drinks.size()-1;n++) {
            } else {
                alkg=0;
            }
        } else {
            alkg = alkg + drinks.get(drinks.size()-1).first * (drinks.get(drinks.size()-1).second / 100) * 0.8f;
        }//if (!isadd) {

        //Kalorien berechnen
        kalorien = alkg * 7.1f;

        float alkg_temp =0f;
        if (BAK_NULL.size()>1) {
            if (drinks.size()!=0) {
                alkg_temp = 0f;
                for (int n = BAK_NULL.get(BAK_NULL.size()-1).first+1; n < drinks.size(); n++) {
                    alkg_temp = alkg_temp + drinks.get(n).first * (drinks.get(n).second / 100) * 0.8f;
                }//for (int n = 0; n<drinks.size()-1;n++) {
            }//if (drinks.size()!=0) {
        } else {
            if (drinks.size()!=0) {
                alkg_temp = 0f;
                for (int n = 0; n < drinks.size(); n++) {
                    alkg_temp = alkg_temp + drinks.get(n).first * (drinks.get(n).second / 100) * 0.8f;
                }//for (int n = 0; n<drinks.size()-1;n++) {

            }//if (drinks.size()!=0) {
        }

        //Stundendifferenz
        Date starttime = null;
        try {
            starttime = time_date.parse(BAK_NULL.get(BAK_NULL.size()-1).second);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long duration = 0;
        try {
            duration = time_date.parse(time_date.format(new Date())).getTime()-starttime.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long min = TimeUnit.MILLISECONDS.toMinutes(duration);
        float hours = (float) min / 60;


        if (geschlecht == 0) {
            if (alkg_temp>0) {
                BAK = ((alkg_temp) / (gewicht * 0.68f)) - ((float) hours * alkabbau);
            } else {
                BAK =0;
            }
        } else {
            if (alkg_temp>0) {
                BAK = ((alkg_temp) / (gewicht * 0.55f)) - ((float) hours * alkabbau);
            } else {
                BAK =0;
            }
        }//if (geschlecht == 0) {

        //if ((BAK <0 || BAK == 0) && isBAK_NULL==false) {
        if (BAK <0) {
            BAK = 0;
            //Null-BAK erreicht, neuer Protokollabschnitt
            if (isTimeChange!=true) {
                akt_datum = new Date();
                date_start = date.format(akt_datum) + ";" + time.format(akt_datum);
                BAK_NULL.put(BAK_NULL.size(), new Pair<Integer, String>(drinks.size() - 1, date_start));
            }//if (isTimeChange!=true) {
        }//if (BAK <0) {
    }

    public HashMap<Integer, Pair<Float,Float>> get_drinks() {
        return drinks;
    }

    public HashMap<Integer, Pair<Integer,String>> get_BAK_NULL() {
        return BAK_NULL;
    }

}
