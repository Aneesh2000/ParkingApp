package com.example.myapplication;



import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class QRGenerator extends AppCompatActivity implements View.OnClickListener {

    Button buttonGenQRCode, buttonSaveToFile;
    ImageView imageViewQRCode;
    TextView editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_generator);
        permissionCheck();

        buttonGenQRCode = findViewById(R.id.buttonGenQRCode);
        buttonSaveToFile = findViewById(R.id.buttonSave);
        imageViewQRCode = findViewById(R.id.imageView);
        editTextInput = findViewById(R.id.editTextInput);
        buttonGenQRCode.setOnClickListener(this);
        buttonSaveToFile.setOnClickListener(this);
        String Count = getIntent().getStringExtra("Count");

        String total = "Slots: " + Count;
        editTextInput.setText(total);
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonGenQRCode:
                generateQRCode();
                buttonSaveToFile.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonSave:
                saveToFile();
                break;
        }
    }

    private void generateQRCode(){
        String text = editTextInput.getText().toString();
        if(TextUtils.isEmpty(text)){
            editTextInput.setError("Please Select at least one slot");
            return;
        }

        try {
            QRGEncoder qrgEncoder = new QRGEncoder(text,null, QRGContents.Type.TEXT,150);
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            imageViewQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(){
        String text = editTextInput.getText().toString();
        if(TextUtils.isEmpty(text)){
            editTextInput.setError("Please Select at least one slot");
            return;
        }

        try {
            QRGEncoder qrgEncoder = new QRGEncoder(text,null, QRGContents.Type.TEXT,150);
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            imageViewQRCode.setImageBitmap(bitmap);

            String fileName = "QRCode_"+System.currentTimeMillis()+".jpg";
            File file = new File(Environment.getExternalStorageDirectory(),fileName);
            file.createNewFile();
            String saveLocation = file.getParent()+File.separator ;
            fileName = file.getName().substring(0,file.getName().indexOf("."));
            QRGSaver.save(saveLocation,fileName,bitmap,QRGContents.ImageType.IMAGE_JPEG);
            Toast.makeText(this, "QR Code successfully saved in the external storage!", Toast.LENGTH_LONG).show();
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void permissionCheck(){
        if (ContextCompat.checkSelfPermission(QRGenerator.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(QRGenerator.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 123){
            if(grantResults.length > 0){
                for(int i=0;i<grantResults.length;i++)
                    if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
                        finish();
            }else{
                finish();
            }
        }
    }
}