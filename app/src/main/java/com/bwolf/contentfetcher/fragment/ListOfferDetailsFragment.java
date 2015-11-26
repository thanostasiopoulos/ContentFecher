package com.bwolf.contentfetcher.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.bwolf.contentfetcher.MainActivity;
import com.bwolf.contentfetcher.R;
import com.bwolf.contentfetcher.controller.AppController;
import com.bwolf.contentfetcher.helper.SharedData;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListOfferDetailsFragment extends Fragment{

	
	Activity myActivity;
	SharedData shared;
	JSONObject responces= null;
	ListOfferDetailsFragment fragment;
	FragmentManager fragmentManager;
	String serverAnswer = "";
	JSONArray data = null;
	public ProgressDialog pDialog;
	ArrayList<HashMap<String, String>> mylist = null; 
	public static final String TAG = MainActivity.class.getSimpleName();
	private static String url = "http://192.168.0.11:8080/contentfetcher/api/api.php";
	TextView tv_title,tv_description,tv_discount;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.listofferdetailsfragment, container, false);
		
		
		shared = SharedData.getInstance(myActivity);
		shared.currentFragment = "listofferdetails";
		
		 tv_title = (TextView) rootView.findViewById(R.id.tvtitle);
		 tv_description = (TextView) rootView.findViewById(R.id.tvdescription);
		 tv_discount = (TextView) rootView.findViewById(R.id.tvdiscount);
		if (mylist == null)
			makeJsonObjectRequest(url);
		return rootView;
		
	}
	
	private String makeJsonObjectRequest(String url) {
		pDialog = new ProgressDialog (myActivity, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
        pDialog.setMessage("Loading offer Data...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
		
        StringRequest jsonObjReq = new StringRequest(Method.POST,
				url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				serverAnswer = response;
				Log.d("serverAnswer", serverAnswer);
				pDialog.dismiss();
				displayData(serverAnswer);
				
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: " + error.getMessage());

			}
		}){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("cmd", "fetchoffersingle");
                params.put("offer", shared.current_offer_id);   
                return params;
            }};
		
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
		Log.d("serverAnswerCategories", serverAnswer);
		return serverAnswer;
	}

	private void displayData(String str){

		try {
			data = new JSONArray(str);
			
			if(data!=null)
				for(int i=0; i<data.length(); i++){
					JSONObject entry = data.getJSONObject(i);
					String offerId = entry.getString("offer_id");
					String shopId = entry.getString("shop_id");
					String title = entry.getString("title");
					String description = entry.getString("description");
					int discount = entry.getInt("discount");
					tv_title.setText(title);
					tv_description.setText(description);
					tv_discount.setText(""+discount);
					
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		myActivity = activity;
	}

}
