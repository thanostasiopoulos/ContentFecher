package com.bwolf.contentfetcher;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwolf.contentfetcher.fragment.ListCategoryFragment;
import com.bwolf.contentfetcher.helper.CustomDialog;
import com.bwolf.contentfetcher.helper.SharedData;

public class NavActivity extends ActionBarActivity {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */



	private CharSequence mTitle;
	int PROFILE = R.drawable.icon_sidemenu_profile_normal;
	SharedData shared;
	CustomDialog dialog = new CustomDialog();
	NavigationView navigationView;
	String NAME = "Thanos Tasiopoulos";
    String EMAIL = "Thanos.tasiopoulos@gmail.com";
	
    private DrawerLayout drawerLayout; 
    private Toolbar mToolbar;

    ListCategoryFragment fragment;
    ActionBarDrawerToggle mDrawerToggle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nav);
		shared = SharedData.getInstance(this);




        // Set a Toolbar to replace the ActionBar.
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

                
        
      //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        TextView name = (TextView)navigationView.getHeaderView(0).findViewById(R.id.header_name);
        name.setText(NAME);
        TextView email = (TextView)navigationView.getHeaderView(0).findViewById(R.id.header_email);
        email.setText(EMAIL);
        ImageView profile = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.header_imageview);
        profile.setImageResource(R.drawable.ic_launcher3);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.inbox:
                        Toast.makeText(getApplicationContext(), "Inbox Selected", Toast.LENGTH_SHORT).show();
                        ListCategoryFragment fragment = new ListCategoryFragment();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.flContent, fragment);
                        fragmentTransaction.commit();
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.starred:
                        Toast.makeText(getApplicationContext(), "Stared Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.sent_mail:
                        Toast.makeText(getApplicationContext(), "Send Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drafts:
                        Toast.makeText(getApplicationContext(), "Drafts Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.allmail:
                        Toast.makeText(getApplicationContext(), "All Mail Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.trash:
                        Toast.makeText(getApplicationContext(), "Trash Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.spam:
                        Toast.makeText(getApplicationContext(), "Spam Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });
 
        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,mToolbar,R.string.app_name, R.string.app_name){
 
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }
 
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
 
                super.onDrawerOpened(drawerView);
            }
        };
 
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
         
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

	}




	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
	    return true;
	}
	 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

	     // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case R.id.home:
	        	drawerLayout.openDrawer(GravityCompat.START);
	            return true;
	        case R.id.action_settings:
	            return true;   
	     }
		 return super.onOptionsItemSelected(item);
	 }
	
	public void appControl(){
        
        
    	AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle(R.string.app_name).setMessage("Are you sure you wnat to exit the app?")
               .setCancelable(false)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            NavActivity.this.finish();
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
