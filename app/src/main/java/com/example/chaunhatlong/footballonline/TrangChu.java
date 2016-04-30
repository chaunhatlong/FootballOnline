package com.example.chaunhatlong.footballonline;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;


public class TrangChu extends AppCompatActivity {

    private static final int PROFILE_SETTING = 0 ;

    private DrawerLayout mDrawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    TextView header_nameTv, header_emailTv;
    String header_name, header_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        //set a toolbar to replace the actionbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Football Online");


        //find drawer view
        navigationView = (NavigationView)findViewById(R.id.nvView);
        setupDrawerContent(navigationView);
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);


        drawerToggle = setupDrawerToggle();
        //emergency
        mDrawer.addDrawerListener(drawerToggle);

        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);

        header_nameTv = (TextView)findViewById(R.id.header_username);
        header_emailTv = (TextView)findViewById(R.id.header_email);






    }

    public ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }


    private void setupDrawerContent(final NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                }
        );
    }

    public void selectDrawerItem(MenuItem menuItem){
        try{
            Fragment fragment = null;
            switch (menuItem.getItemId()){
                case R.id.FragmetHome:
                    fragment = new homeFragment();
                    break;
                case R.id.FragmentRegister:
                    fragment = new registerFragment();
                    break;
                case R.id.FragmentInformation:
                    fragment = new infomationFragment();
                    break;
                case R.id.FragmentMaps:
                    fragment = new mapsGoogle();
                    break;
                case R.id.FragmentAbout:
                    fragment = new aboutFragment();
                    break;
                case R.id.FragmentTime:
                    fragment = new timeFragment();
                    break;
                default:
                    fragment = new homeFragment();
                    break;
            }

            if(fragment != null) {
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

                // Highlight the selected item has been done by NavigationView
                menuItem.setChecked(true);
                // Set action bar title
                setTitle(menuItem.getTitle());
                // Close the navigation drawer
                mDrawer.closeDrawers();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

     @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

//         if(drawerToggle.onOptionsItemSelected(item)){
//            return  true;
//         }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



}