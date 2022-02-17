package com.bam.note_v2.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    public  static Bitmap loadImg(Context context, String filename) throws IOException {
        FileInputStream inputStream = context.getApplicationContext().openFileInput(filename);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();

        return bitmap;
    }

    public static Bitmap crateBitmapFromUri(Context context, Uri uri) throws IOException {


        return MediaStore.Images.Media.getBitmap(context.getApplicationContext().getContentResolver(), uri);
    }

    public  static String saveImg(Context context, Bitmap bitmap) throws IOException {

        int count = 0;


        try {
            while (true)
            {
                loadImg(context, count + ".jpg" );
                count++;
            }

        }
        catch (Exception e)
        {
            FileOutputStream fileOutputStream = context.getApplicationContext().openFileOutput(count + ".jpg", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            bitmap.recycle();
        }




        return count + ".jpg";
    }
}
