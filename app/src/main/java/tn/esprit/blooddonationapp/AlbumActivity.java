package tn.esprit.blooddonationapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {
    GridView Grid;
    ImageAdapter adapter;


    Cursor cursor;
    int column_index;
    String path = null,sortOrder;
    ArrayList<String> imageList = new ArrayList<>();
    Button cam = findViewById(R.id.cam);
     @Override
    public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
        super.grantUriPermission(toPackage, uri, modeFlags);
    }

    Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    String[] projection = { MediaStore.MediaColumns.DATA };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
       // Uri uri = Uri.parse("content://media/external/images/media");
          cam.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                 
              }
          });




    }
    public void loadList() {


//DATA is the path to the corresponding image. We only need this for loading //image into a recyclerview

        sortOrder = MediaStore.Images.ImageColumns.DATE_ADDED + " DESC";
//This sorts all images such that recent ones appear first

        cursor = getContentResolver().query(uri, projection, null, null, sortOrder);

        try {
            if (null != cursor) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                while (cursor.moveToNext()) {
                    path = cursor.getString(column_index);
                    imageList.add(path);
                }
                cursor.close();
//imageList gets populated with paths to images by here
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Grid = (GridView)findViewById(R.id.MyGrid);
        adapter = new ImageAdapter(getApplicationContext(), R.layout.grid_item,imageList );

        Grid.setAdapter(adapter);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted and now can proceed
                            loadList();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(AlbumActivity.this,
                            "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // add other cases for more permissions
        }
    }
}

