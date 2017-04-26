package com.exmo.exmo_test1.Parent;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.exmo.exmo_test1.ExploreActivity;
import com.exmo.exmo_test1.LiveStreamActivity;
import com.exmo.exmo_test1.MapActivity;
import com.exmo.exmo_test1.R;
import com.exmo.exmo_test1.ScheduleActivity;


public class NavBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);

//
//
//    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        Intent d=new Intent(NavBar.this,ExploreActivity.class);
        startActivity(d);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent d=new Intent(NavBar.this,ScheduleActivity.class);
            startActivity(d);
        } else if (id == R.id.nav_gallery) {
            Intent d=new Intent(NavBar.this,MapActivity.class);
            startActivity(d);
        } else if (id == R.id.nav_slideshow) {
            Intent d=new Intent(NavBar.this,ExploreActivity.class);
            startActivity(d);
        } else if (id == R.id.nav_manage) {
            Intent d=new Intent(NavBar.this,LiveStreamActivity.class);
            startActivity(d);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
