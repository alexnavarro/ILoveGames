package br.com.alexandrenavarro.ilovegames.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alexandrenavarro on 8/9/15.
 */
public class TopGamesResult implements Serializable{

    private Links _links;
    private List<TopGames> top;

    public Links get_links() {
        return _links;
    }

    public List<TopGames> getTop() {
        return top;
    }
}
