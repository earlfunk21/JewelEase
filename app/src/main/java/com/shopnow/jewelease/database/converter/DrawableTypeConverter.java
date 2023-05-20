package com.shopnow.jewelease.database.converter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.room.TypeConverter;

import com.shopnow.jewelease.MyApp;

public class DrawableTypeConverter {
    @TypeConverter
    public static Drawable fromInt(int value) {
        return ContextCompat.getDrawable(MyApp.getInstance(), value);
    }

    @TypeConverter
    public static int toInt(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            return MyApp.getInstance().getResources().getIdentifier(
                    String.valueOf(bitmap), null, MyApp.getInstance().getPackageName());
        } else {
            return 0;
        }
    }
}