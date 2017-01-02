package ratcoding.mainactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivityMore extends AppCompatActivity {


    int category = 0;
    final String[] mapsNames = {"Patryk Szczur", "Dominik Żegleń"};

    final String[] aboutApp = {"Litwo! Ojczyzno maja! Ty jesteś jak zdrowie, \n" +
            "Ile cię trzeba cenić, ten tylko się dowie, \n" +
            "Kto cię stracił. Dziś piękność twą w całej ozdobie \n" +
            "Widzę i opisuję, bo tęsknię po tobie\" \n" +
            "Panno święta, co Jasnej bronisz Częstochowy \n" +
            "I w Ostrej świecisz Bramie! Ty, co gród zamkowy \n" +
            "Nowogródzki ochraniasz z jego wiernym ludem! \n" +
            "Jak mnie dziecko do zdrowia powróciłaś cudem, \n" +
            "(Gdy od płaczącej matki pod Twoją opiekę \n" +
            "Ofiarowany, martwą podniosłem powiekę \n" +
            "I zaraz mogłem pieszo do Twych świątyń progu \n" +
            "Iść za wrócone życie podziękować Bogu), \n" +
            "Tak nas powrócisz cudem na Ojczyzny łono. \n" +
            "Tymczasem przenoś moją duszę utęsknioną \n" +
            "Do tych pagórków leśnych, do tych łąk zielonych, \n" +
            "Szeroko nad błękitnym Niemnem rozciągnionych; \n" +
            "Do tych pól malowanych zbożem rozmaitem, \n" +
            "Wyzłacanych pszenicą, posrebrzanych żytem; \n" +
            "Gdzie bursztynowy świerzop, gryka jak śnieg biała, \n" +
            "Gdzie panieńskim rumieńcem dzięcielina pała, \n" +
            "A wszystko przepasane jakby wstęgą, miedzą \n" +
            "Zieloną, na niej z rzadka ciche grusze siedzą."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_more);
    }

    public void openWebsite(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ratcoding.x14.eu/"));
        startActivity(intent);
    }

    public void openInfo(View view) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Made by");
        builder.setItems(mapsNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){

            }
        });
        builder.create();
        builder.show();
    }

    public void ŻegleńInfo(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/dominik.zeglen.hehe?fref=ts"));
        startActivity(intent);

    }

    public void SzczurInfo(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/patryk.szczur?fref=ts"));
        startActivity(intent);

    }

    public void abooutApp (View view) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alkotron");
        builder.setItems(aboutApp , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){

            }
        });
        builder.create();
        builder.show();
    }

    public void openError (View view){

        Intent intent = new Intent(this , Error.class);
        startActivity(intent);

    }
}

