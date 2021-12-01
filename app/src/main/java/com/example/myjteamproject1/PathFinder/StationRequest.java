package com.example.myjteamproject1.PathFinder;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StationRequest extends StringRequest {
    final static private String URL = "http://ljh15952.ivyro.net/StationData.php";
    private Map<String, String> map;

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

    public StationRequest(String startData, String endData, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("startData", startData + "");
        map.put("endData", endData + "");
    }

}
