package com.owl.sharecard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView mPhoto;
    private EditText mWord;
    private Button mShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();
    }

    private void initView() {
        mPhoto = (ImageView) findViewById(R.id.id_main_photo);
        mWord = (EditText) findViewById(R.id.id_main_word);
        mShare = (Button) findViewById(R.id.id_main_share);
        mWord.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/familiar_style.ttf"));
    }

    private void setListener() {
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 100);
            }
        });
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhoto.setImageBitmap(generateSpringCard());
                mShare.setVisibility(View.VISIBLE);
                String text = mWord.getText().toString();
                if (!text.equals("")) {
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Bitmap generateSpringCard() {
        mShare.setVisibility(View.INVISIBLE);
        View view = getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            if (data != null) {
                mPhoto.setImageURI(data.getData());
            }
        }
    }
}
