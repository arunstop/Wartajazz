package com.pkl.wartajazz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.pkl.wartajazz.fragment.HomeFragment;
import com.pkl.wartajazz.fragment.BeritaFragment;
import com.pkl.wartajazz.fragment.EventFragment;
import com.pkl.wartajazz.fragment.ProfileFragment;
import com.pkl.wartajazz.fragment.VideoFragment;
import com.pkl.wartajazz.storage.SharedPrefManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String GOOGLE_ACCOUNT = "google_account";
    public static final GoogleSignInAccount googleSignInAccount = null;
    Toolbar toolbar;
    private DrawerLayout drawer;
    private AlertDialog.Builder builder;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView username = (TextView) headerView.findViewById(R.id.user_name);
        TextView useremail = (TextView) headerView.findViewById(R.id.user_email);
        CircularImageView thumbnail = (CircularImageView) headerView.findViewById(R.id.thumbnail);
        RequestOptions option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        //builder logout dialog
        builder = new AlertDialog.Builder(this);

        username.setText(SharedPrefManager.getInstance(this).getUser().getFullname());
        useremail.setText(SharedPrefManager.getInstance(this).getUser().getEmail());
        Glide.with(this).load(SharedPrefManager.getInstance(this).getUser().getThumbnail()).apply(option).into(thumbnail);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_berita:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BeritaFragment()).addToBackStack("fragmentStack").commit();
                toolbar.setTitle("Daftar Berita");
                break;
            case R.id.nav_event:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EventFragment()).addToBackStack("fragmentStack").commit();
                toolbar.setTitle("Daftar Event");
                break;
            case R.id.nav_video:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new VideoFragment()).addToBackStack("fragmentStack").commit();
                toolbar.setTitle("Daftar Video");
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).addToBackStack("fragmentStack").commit();
                toolbar.setTitle("Profile");
                break;
//            case R.id.nav_setting:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new SettingFragment()).commit();
//                break;
            case R.id.nav_logout:

                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah anda ingin keluar dari aplikasi?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        googleSignInClient.signOut();
                        SharedPrefManager.getInstance(MainActivity.this).clear();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
