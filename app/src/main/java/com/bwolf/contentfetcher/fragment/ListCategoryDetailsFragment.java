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
import com.bwolf.contentfetcher.adapter.CustomArrayAdapter;
import com.bwolf.contentfetcher.controller.AppController;
import com.bwolf.contentfetcher.helper.SharedData;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListCategoryDetailsFragment extends ListFragment{

	
	Activity myActivity;
	SharedData shared;
	JSONObject responces= null;
	ListCategoryDetailsFragment fragment;
	FragmentManager fragmentManager;
	String serverAnswer = "";
	JSONArray data = null;
	public ProgressDialog pDialog;
	ArrayList<HashMap<String, String>> mylist = null; 
	public static final String TAG = MainActivity.class.getSimpleName();
	private static String url = "http://192.168.0.11:8080/contentfetcher/api/api.php";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.listviewdetailsfragment, container, false);
		
		
		shared = SharedData.getInstance(myActivity);
		shared.currentFragment = "listcategorydetails";
		if (mylist == null)
			makeJsonObjectRequest(url);
		return rootView;
		
	}
	
	private String makeJsonObjectRequest(String url) {
		pDialog = new ProgressDialog (myActivity, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
        pDialog.setMessage("Loading Category Data...");
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
				createList(serverAnswer);
				
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
                params.put("cmd", "fetchOffers");
                params.put("category", shared.current_category_id);   
                return params;
            }};
		
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
		Log.d("serverAnswerCategories", serverAnswer);
		return serverAnswer;
	}

	private void createList(String str){
		mylist= new ArrayList<HashMap<String, String>>();
		try {
			data = new JSONArray(str);
			
			
			if(data!=null)
				for(int i=0; i<data.length(); i++){
					JSONObject entry = data.getJSONObject(i);
					String offerId = entry.getString("offer_id");
					shared.current_offer_id = offerId;
					String shopId = entry.getString("shop_id");
					String title = entry.getString("title");
					String description = entry.getString("description");
					HashMap<String, String> listData = new HashMap<String, String>();
					listData.put("id", title);
					listData.put("name", description);
					mylist.add(listData);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayAdapter<HashMap<String, String>> adapter = new CustomArrayAdapter(getActivity(), mylist);
	    setListAdapter(adapter);
	}
	
	@Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // TODO implement some logic 
		 HashMap<String, String> ll = new HashMap<String, String>();
		 ll= (HashMap<String, String>) getListAdapter().getItem(position);
		 ListOfferDetailsFragment fragment = new ListOfferDetailsFragment();
       FragmentTransaction ft = getFragmentManager().beginTransaction();
       ft.addToBackStack(this.getTag());
       ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
       ft.replace(R.id.flContent, fragment).commit();
	     Toast.makeText(getActivity(), ll.get("id") + " selected", Toast.LENGTH_LONG).show();
	  }
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		myActivity = activity;
	}

}
