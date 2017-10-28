package com.robusta.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.robusta.App;
import com.robusta.photoweatherapp.R;

import java.util.List;

/**
 * Created by Eslam Hussein on 10/28/17.
 */

public class ImageUtils {

    public static Bitmap drawTextToBitmap(Bitmap bitmap,
                                    List<String> strings) {
        Resources resources = App.get().getResources();
        float scale = resources.getDisplayMetrics().density;

        android.graphics.Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(App.get().getResources().getColor(R.color.colorAccent));
        paint.setTextSize((int) (TextUtils.getDimension(R.dimen.material_text_title) * scale));
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        int y = 0;
        for (int i = 0; i < strings.size(); i++) {
            String currentString = strings.get(i);

            Rect bounds = new Rect();
            paint.getTextBounds(currentString, 0, currentString.length(), bounds);
            int x = (bitmap.getWidth() - bounds.width()) / 2;
            y = y + (bitmap.getHeight() / 20) + bounds.height();

            canvas.drawText(currentString, x, y, paint);

        }

        return bitmap;
    }

}
