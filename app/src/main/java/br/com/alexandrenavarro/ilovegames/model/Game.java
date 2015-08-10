package br.com.alexandrenavarro.ilovegames.model;

import java.io.Serializable;

/**
 * Created by alexandrenavarro on 8/9/15.
 */
public class Game implements Serializable{

    private String name;
    private GameImage box;
    private GameImage logo;


    public GameImage getBox() {
        return box;
    }

    public GameImage getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }

    public class GameImage implements Serializable{
        private String large;
        private String medium;
        private String small;

        public String getLarge() {
            return large;
        }

        public String getMedium() {
            return medium;
        }

        public String getSmall() {
            return small;
        }
    }
}