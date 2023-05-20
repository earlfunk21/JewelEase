package com.shopnow.jewelease.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageHelper {
    public static byte[] convertToByteArray(File imageFile) throws IOException {

        try (FileInputStream fis = new FileInputStream(imageFile); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            return baos.toByteArray();
        }
    }

    public static byte[] convertImageToByteArray(Context context, String imagePath) throws IOException {
        AssetManager assetManager = context.getAssets();

        try (InputStream inputStream = assetManager.open(imagePath); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            return baos.toByteArray();
        }
    }

    public static Bitmap convertByteArrayToBitmap(byte[] imageData){
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        // We ask for the bounds if they have been set as they would be most
        // correct, then we check we are  > 0
        final int width = !drawable.getBounds().isEmpty() ?
                drawable.getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ?
                drawable.getBounds().height() : drawable.getIntrinsicHeight();

        // Now we check we are > 0
        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width, height <= 0 ? 1 : height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private byte[] convertDrawableToBytes(Resources resources, int image) {
        Bitmap bmpDiamond = BitmapFactory.decodeResource(resources, image);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmpDiamond.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
