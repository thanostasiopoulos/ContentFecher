package com.bwolf.contentfetcher.fragment;



import java.util.HashMap;
import java.util.Map;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;
import com.bwolf.contentfetcher.helper.JSONParser;
import com.bwolf.contentfetcher.MainActivity;
import com.bwolf.contentfetcher.R;
import com.bwolf.contentfetcher.controller.AppController;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request.Method;
import com.bwolf.contentfetcher.helper.SharedData;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
	

	EditText et_email, et_pass;
	Button btn_login, btn_register;
	String password="", email="";
	Activity myActivity;
	SharedData shared;
	FragmentManager fragmentManager;
	public ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	public static final String TAG = MainActivity.class.getSimpleName();
	String serverAnswer="";
	ListCategoryFragment fragment;
    private static String url = "http://192.168.0.11:8080/contentfetcher/api/api.php";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.login_fragment, container, false);
		
		myActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		shared = SharedData.getInstance(myActivity);
		shared.currentFragment = "Login";

		
		et_email  		= (EditText) rootView.findViewById(R.id.et_email);
		et_email.clearFocus();
		et_pass  		= (EditText) rootView.findViewById(R.id.et_password);
		et_pass.clearFocus();
		btn_login 		= (Button) rootView.findViewById(R.id.btn_login);
		//btn_register	= (Button) rootView.findViewById(R.id.btn_register);

		
       
       et_email.addTextChangedListener(new TextWatcher() {
		
    	   @Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				email = et_email.getText().toString();		
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub		
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
       
       
       et_pass.addTextChangedListener(new TextWatcher() {
   		
	   		@Override
	   		public void onTextChanged(CharSequence s, int start, int before, int count) {
	   			password = et_pass.getText().toString();
	   			
	   		}	   		
	   		@Override
	   		public void beforeTextChanged(CharSequence s, int start, int count,
	   				int after) {
	   			// TODO Auto-generated method stub	
	   		}
	   		
	   		@Override
	   		public void afterTextChanged(Editable s) {
	   			// TODO Auto-generated method stub
	   			
	   		}
	   	});
       
       btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//regex gia email
		       if(email.equals("")){
		    	   if(password.equals("")){
		    		   Toast.makeText(myActivity, "Please fill all feilds",Toast.LENGTH_SHORT).show();
		    	   }else { 
		    		   Toast.makeText(myActivity, "Email field cannot be empty",Toast.LENGTH_SHORT).show();
		    	   }
		       }else if(password.equals("")){
		        	Toast.makeText(myActivity, "Password field cannot be empty",Toast.LENGTH_SHORT).show();
		        } else{
		        	//check internet connection
		        	//test serrver connection
				   if(shared.NetworkExists(getActivity()))
		        		makeJsonObjectRequest(url);
				   else
					   Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();
		        	
		        }
			}
		});
       
       return rootView;
	}
	

	
	//email check control
	public boolean isEmailValid(CharSequence email) {
		   return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	private String makeJsonObjectRequest(String url) {
		
        StringRequest jsonObjReq = new StringRequest(Method.POST,
				url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				serverAnswer = response;
				Log.d("serverAnswer", serverAnswer);
				if(!serverAnswer.equals("wrong")){
					try{
						shared.userID = Integer.parseInt(serverAnswer);
					}
					catch (Exception e){
						//to do
					}
					Intent next = new Intent("com.bwolf.contentfetcher.NAVACTIVITY");
		        	startActivity(next);
		        	getActivity().finish();
				}
				else{
					Toast.makeText(getActivity(),R.string.badpassword,Toast.LENGTH_SHORT).show();
				}

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getActivity(),R.string.badServer,Toast.LENGTH_SHORT).show();
				VolleyLog.d(TAG, "Error: " + error.getMessage());


			}
		}){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("cmd", "login");
                params.put("username", "thanos");
                params.put("password", "thanos");
                
                return params;
            }}
		;

		int socketTimeout = 10000;//30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		jsonObjReq.setRetryPolicy(policy);
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
		Log.d("serverAnswer", "user id: "+serverAnswer);
		return serverAnswer;
	}

	
	


	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		myActivity = activity;
	}

}
