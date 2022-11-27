package com.greensoft.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.ScrollBar;

public class helpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);

        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        ScrollBar scrollBar = (ScrollBar) findViewById(R.id.scrollBar);
        pdfView.setScrollBar(scrollBar);
       // pdfView.fromFile(Res)
       // pdfView.fromAsset("helpthing.pdf").load();
        pdfView.fromAsset("helpthing.pdf")
                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true)
                .enableDoubletap(true)
                .swipeVertical(false)
                .defaultPage(1)
                .showMinimap(true)
                .enableAnnotationRendering(true)
                .password(null)
                .showPageWithAnimation(true)
                .load();


    }

    public void close_window(View view) {
        Intent openActivity = new Intent(helpPage.this, MainActivity.class);
        startActivity(openActivity);
        finish();
    }
    int back_add=0;
    @Override
    public void onBackPressed() {
        // Example of logic
        if ( back_add < 1 ) {

            //Toast.makeText(this, "The second time will close the window", Toast.LENGTH_SHORT).show();
            Intent openActivity = new Intent(helpPage.this, MainActivity.class);
            startActivity(openActivity);
            finish();
        }
        else {
            super.onBackPressed();
        }
    }
}