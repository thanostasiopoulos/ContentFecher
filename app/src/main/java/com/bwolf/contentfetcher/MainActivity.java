package com.bwolf.contentfetcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwolf.contentfetcher.fragment.LoginFragment;
import com.bwolf.contentfetcher.helper.CustomDialog;
import com.bwolf.contentfetcher.helper.SharedData;

public class MainActivity extends Activity {

    SharedData shared;
    FrameLayout frame_container;
    RelativeLayout rel_home, rel_map, rel_settings, rel_contacts;
    View view_home, view_settings, view_contacts;
    TextView tv_icon_home, tv_map, tv_icon_settings, tv_icon_contacts;
    FragmentManager fragmentManager;
    CustomDialog dialog = new CustomDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        shared = SharedData.getInstance(this);
        fragmentManager = getFragmentManager();
        frame_container = (FrameLayout) findViewById(R.id.frame_container);

        
        if(shared.is_logged){

        	Intent next = new Intent("com.bwolf.contentfetcher.NAVACTIVITY");
        	startActivity(next);
        	this.finish();
        	/*
        	ListCategoryFragment fragment = new ListCategoryFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
            ft.replace(R.id.frame_container, fragment).commit();*/
        }
        else{
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            LoginFragment fragment = new LoginFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
            ft.replace(R.id.frame_container, fragment).commit();
        }
    }

    public void appControl(){
              
           
    	AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle(R.string.app_name).setMessage("Are you sure you wnat to exit the app?")
               .setCancelable(false)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            shared.back = 0;
                            dialog.cancel();
                        }
                    });
        AlertDialog alert = builder.create();
        alert.show();
        dialog.ChangeDialogUI(alert);
        
    }

    @Override
    public void onBackPressed() {
        //appControl();
    	if(getFragmentManager().getBackStackEntryCount()>0){
    	Log.e("FROM FRAGMENT", "" + shared.currentFragment);
    		if(getFragmentManager().popBackStackImmediate())
    			Log.e("TO FRAGMENT", "" + shared.currentFragment);
    	}
    	else{
    		appControl();
    	}
    }
}
