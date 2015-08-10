package br.com.alexandrenavarro.ilovegames.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import br.com.alexandrenavarro.ilovegames.model.TopGamesResult;

/**
 * Created by alexandrenavarro on 8/9/15.
 */
public class HttpRequest<T> {

    public void get(Context context, String url, Class<T> clazz, Response.Listener listener, Response.ErrorListener error) {
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(new GsonRequest<T>(url, clazz, null, listener, error));
        Log.i("HttpRequest", "exectute request to url " + url);
    }
}
