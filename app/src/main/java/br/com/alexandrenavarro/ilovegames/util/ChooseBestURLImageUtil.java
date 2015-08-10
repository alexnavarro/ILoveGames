package br.com.alexandrenavarro.ilovegames.util;

import android.content.Context;
import android.util.DisplayMetrics;

import br.com.alexandrenavarro.ilovegames.model.Game;

/**
 * Created by alexandrenavarro on 8/9/15.
 */
public class ChooseBestURLImageUtil {

    public static String choose(Context context, Game.GameImage gameImage) {
        int density= context.getResources().getDisplayMetrics().densityDpi;

        switch(density) {
            case DisplayMetrics.DENSITY_LOW:
                return gameImage.getSmall();
            case DisplayMetrics.DENSITY_MEDIUM:
                return gameImage.getMedium();
            case DisplayMetrics.DENSITY_HIGH:
                return gameImage.getLarge();
            case DisplayMetrics.DENSITY_XHIGH:
                return gameImage.getLarge();
            default:
                return gameImage.getLarge();
        }
    }
}