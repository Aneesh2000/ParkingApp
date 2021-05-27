package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Element adsElement = new Element();
        adsElement.setTitle("Thanks for being our user!");

        View aboutpage = new AboutPage(this)
                .isRTL(false)
                .setDescription("ParKar")
                .addItem(new Element().setTitle("Project by Charitha M, G Aneesh, Keerthana Reddy, K Vivek, Srivatsav Sai "))
                .addItem(adsElement)
                .addGroup("Contact Us")
                .addEmail("k.vivek18@st.niituniversity.in")
                .addFacebook("100007568565030")
                .addInstagram("srivatsav_sai")
                .addItem(createCopyright())
                .create();

        setContentView(aboutpage);

    }

    private Element createCopyright() {
        Element copyright = new Element();
        final String copyrightstring = String.format("Thanks again!", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightstring);
        copyright.setIcon(R.mipmap.ic_launcher);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(About.this,copyrightstring,Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;

    }
}