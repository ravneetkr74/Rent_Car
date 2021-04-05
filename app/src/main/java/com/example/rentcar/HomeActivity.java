package com.example.rentcar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rentcar.ui.gallery.GalleryFragment;
import com.example.rentcar.ui.home.HomeFragment;
import com.example.rentcar.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.lst_menu_items)
    RecyclerView mDrawerList;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_user_name)
    TextView txtName;
    @BindView(R.id.image_user)
    ImageView imgUser;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.img_drawer)
    ImageView imgNavgation;

    List<DrawerModel> mList;

    String title[] = {"Home", "Profile", "About Us",
            "Bookings", "Share", "Logout"};

    int image[] = {R.drawable.home, R.drawable.profile, R.drawable.info,
            R.drawable.schedule, R.drawable.share, R.drawable.logout};

    @BindViews({R.id.home, R.id.info, R.id.setting})
    List<RelativeLayout> mRelative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        txtTitle.setText("Rent Car");

        mDrawerList.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        HomeFragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();

        setListData();
//

    }

    private void setListData() {

        mList = new ArrayList<>();

        for (int i = 0; i < title.length; i++) {

            DrawerModel mObj = new DrawerModel();
            mObj.setName(title[i]);
            mObj.setIcon(image[i]);
            if (i == 0) {
                mObj.setSelected(true);
            }
            mList.add(mObj);

        }

        DrawerAdapter adapter = new DrawerAdapter(this, mList) {
            @Override
            protected void onClickView(int pos) {
                selectItem(pos);
            }
        };

        DividerItemDecoration itemDecorator = new DividerItemDecoration(HomeActivity.this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(HomeActivity.this, R.drawable.divider));
        mDrawerList.addItemDecoration(itemDecorator);

        mDrawerList.setAdapter(adapter);


        imgNavgation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });


    }
    private void selectItem(int position) {

        Fragment fragment = null;
        View view = this.getCurrentFocus();
        switch (position) {

            case 0:

                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                fragment = new HomeFragment();

                break;

            case 1:
                view = this.getCurrentFocus();


                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                 fragment = new GalleryFragment();

                break;

            case 2:

                // fragment = new OutstandingTransactionFragment();

                view = this.getCurrentFocus();

                fragment = new GalleryFragment();

                break;


            case 3:
                view = this.getCurrentFocus();


                fragment = new SlideshowFragment();

                break;

            case 4:
                view = this.getCurrentFocus();

                fragment = new GalleryFragment();

                break;

            case 5:
                view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                drawer.closeDrawer(GravityCompat.START);
                final AlertDialog.Builder mainDialog = new AlertDialog.Builder(HomeActivity.this);
                LayoutInflater inflater = (LayoutInflater)HomeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.alert_dialog, null);
                mainDialog.setView(dialogView);

                final Button cancel = (Button) dialogView.findViewById(R.id.cancel);
                final Button save = (Button) dialogView.findViewById(R.id.save);
                final TextView act_name=(TextView)dialogView.findViewById(R.id.cat_name);
                final TextView title=(TextView)dialogView.findViewById(R.id.title);
                final AlertDialog alertDialog = mainDialog.create();
                alertDialog.show();
                title.setText("LOGOUT");
                act_name.setText("Are you sure you want to logout?");

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();

                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //CommonMethod.showProgress(getApplicationContext());
                        //  Logout();
                       // helper.onLogout();
                        Intent it = new Intent(HomeActivity.this,login.class);
                        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(it);
                        alertDialog.dismiss();
                    }
                });

                break;
        }



        if(fragment!=null)
            changeFragment(fragment);

        drawer.closeDrawer(GravityCompat.START);
    }

    private void changeFragment(Fragment fragment) {


        if (fragment != null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).
                    addToBackStack(fragment.getClass().getName()).commit();

        }
    }



    @Override
    public void onBackPressed() {
//        if (getFragmentManager().getBackStackEntryCount() > 0) {
//            getFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }

        if(getCurrentFrag() instanceof HomeFragment){
            finish();
        }
        else {
            changeFragment(new HomeFragment());
        }
    }

    public Fragment getCurrentFrag(){
        return getSupportFragmentManager().findFragmentById(R.id.frame_container);
    }
}