package com.mp.neetjeeiitprep.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonElement;
import com.mp.neetjeeiitprep.AppUtils.AppUtils;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.dataModel.ContactUsDataModel;
import com.mp.neetjeeiitprep.dataModel.LoginRegisterResponseDataModel;
import com.mp.neetjeeiitprep.dataModel.QuestionDataModel;
import com.mp.neetjeeiitprep.fragment.StudentForumFragment;
import com.mp.neetjeeiitprep.interfaces.OnSubjectClickListener;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        NavController.OnDestinationChangedListener, OnSubjectClickListener {

    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private TextView tvNavHeadUserNa, tvNavHeadUserEmail;
    private String previousDestinationName, loginUsing;
    public String destinationLabelFromFragment;
    private Pref pref;
    public Toolbar toolbar;
    public TextView tvCustomToolbar;
    public ArrayList<QuestionDataModel> questionItem;
    boolean doubleBackToExitPressedOnce = false;
    boolean isInDashboardFragment;
    int prevDestination=0;
    private String adUrl="https://neetjeeiitprep.com";
    private String adTitle="Prepare for your next big exam";
    private String TAG = "MainActivityTAG";
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });



        AdRequest adRequest = new AdRequest.Builder().build();
//        ca-app-pub-5709221063224134/5815868651
        InterstitialAd.load(this,"ca-app-pub-5709221063224134/5815868651", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });

        pref = new Pref(this);
        uploadUserDataToFirebase();
        // loginUsing = getIntent().getExtras().getString(Constant.LOGIN_USING);
        loginUsing = pref.getString(Constant.LOGIN_USING);
        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvCustomToolbar = findViewById(R.id.tvCustomToolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.navView);
        tvNavHeadUserNa = navView.getHeaderView(0).findViewById(R.id.tvNavHeadUserNa);
        tvNavHeadUserEmail = navView.getHeaderView(0).findViewById(R.id.tvNavHeadUserEmail);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.dashboardFragment)
                .setDrawerLayout(drawerLayout)
                .build();
        navController = Navigation.findNavController(this, R.id.navFragment);
        navController.addOnDestinationChangedListener(this);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navView.setNavigationItemSelectedListener(this);


    }
public void reloadAds() {
    AdRequest adRequest = new AdRequest.Builder().build();
//    ca-app-pub-5709221063224134/5815868651
    InterstitialAd.load(this, "ca-app-pub-5709221063224134/5815868651", adRequest,
            new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd;
                    Log.i(TAG, "onAdLoaded");
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    // Handle the error
                    Log.i(TAG, loadAdError.getMessage());
                    mInterstitialAd = null;
                }
            });
}

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        menuItem.setChecked(true);

        drawerLayout.closeDrawers();
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.mnProfile:
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                    reloadAds();

                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }

                if (!loginUsing.contentEquals(Constant.LOGIN_TYPE_GUEST)) {
                    navController.navigate(R.id.action_dashboardFragment_to_profileFragment);

                } else {
                    // Toast.makeText(this, "login to access profile info", Toast.LENGTH_SHORT).show();
                    AppUtils.openSignInDialog(this);
                }
                break;
            case R.id.mnAboutUs:

                navController.navigate(R.id.action_dashboardFragment_to_aboutUsFragment);
                break;
            case R.id.mnPPolicy:
                navController.navigate(R.id.action_dashboardFragment_to_privacyAndTermsFragment);
                break;
            case R.id.mnHistory:
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                    reloadAds();

                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
                if (!loginUsing.contentEquals(Constant.LOGIN_TYPE_GUEST)) {
                    navController.navigate(R.id.action_dashboardFragment_to_historyFragment);
                } else {
                    //Toast.makeText(this, "login to access history", Toast.LENGTH_SHORT).show();
                    AppUtils.openSignInDialog(this);
                }
                break;
            case R.id.mnMockTest:
                navController.navigate(R.id.action_dashboardFragment_to_mockTestNavFragment);
                break;
            case R.id.mnHResults:

                if (!loginUsing.contentEquals(Constant.LOGIN_TYPE_GUEST)) {
                    navController.navigate(R.id.action_dashboardFragment_to_resultListingFragment);
                } else {
                    AppUtils.openSignInDialog(this);
                }
                break;
            case R.id.mnStudentForum:
                navController.navigate(R.id.action_dashboardFragment_to_studentForumFragment);
                break;
            case R.id.mnContactUs:
                navController.navigate(R.id.action_dashboardFragment_to_contactUsFragment);
                break;
            case R.id.mnLogout:
                logoutPopup();
                //navController.navigate(R.id.action_dashboardFragment_to_landingActivity);
                break;
        }
        return true;
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        if (destination.getId() == R.id.dashboardFragment) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
        isInDashboardFragment=false;
        switch (destination.getId()) {
            case R.id.dashboardFragment: {
                isInDashboardFragment=true;
                loadUserInfo();
                tvCustomToolbar.setText(getString(R.string.dash_page_head));
                break;
            }
            case R.id.profileFragment: {
                tvCustomToolbar.setText("Profile");
                break;
            }
            case R.id.studyMetFragment: {
                tvCustomToolbar.setText(getString(R.string.study_page_head));
                break;
            }
            case R.id.prevYearQFragment: {
                tvCustomToolbar.setText(getString(R.string.prev_page_head));
                break;
            }
            case R.id.practiceTestFragment: {
                tvCustomToolbar.setText(getString(R.string.test_page_head));
                break;
            }
            case R.id.suggestionFragment: {
                tvCustomToolbar.setText(getString(R.string.sug_page_head));
                break;
            }
            case R.id.mcqTestFragment: {
                tvCustomToolbar.setText("Study Material");
                break;
            }
            case R.id.aboutUsFragment: {
                tvCustomToolbar.setText("About Us");
                break;
            }
            case R.id.privacyAndTermsFragment: {
                tvCustomToolbar.setText("Privacy Policy");
                break;
            }
            case R.id.historyFragment: {
                tvCustomToolbar.setText("History");
                break;
            }
            case R.id.mockTestNavFragment:{
                tvCustomToolbar.setText("Mock Test");
                break;
            }
            case R.id.studentForumFragment:{
                tvCustomToolbar.setText("Students Forum");
                break;
            }
            case R.id.contactUsFragment: {
                tvCustomToolbar.setText("Contact Us");
                break;
            }
           /* case R.id.solvedPapersFragment: {
                //  tvCustomToolbar.setText(previousDestinationName);
                tvCustomToolbar.setText("Solved Question Papers");
                break;
            }*/
            case R.id.chapterListFragment: {
                tvCustomToolbar.setText(previousDestinationName);
                // tvCustomToolbar.setText("Solved Question Papers");
                if(prevDestination==R.id.mcqTestFragment)

                break;
            }
            case R.id.previousYearsListFragment: {
                tvCustomToolbar.setText(previousDestinationName);
                // tvCustomToolbar.setText("Solved Question Papers");
                if(prevDestination==R.id.mcqTestFragment)

                break;
            }
            case R.id.resultFragment: {
                //  tvCustomToolbar.setText(previousDestinationName);
                tvCustomToolbar.setText("Result");
                break;
            }
            case R.id.resultListingFragment: {
                //  tvCustomToolbar.setText(previousDestinationName);
                tvCustomToolbar.setText("Previous Result");
                break;
            }case R.id.videoClassesFragment: {
                tvCustomToolbar.setText(getString(R.string.video_class_page_head));
                break;
            }
            case R.id.mockTestLiveFragment: {
                tvCustomToolbar.setText("Mock Test Live");
                break;
            }
            case R.id.videoChapterListFragment: {
                tvCustomToolbar.setText(previousDestinationName);
                if(prevDestination==R.id.videoListFragment)

                break;
            }
            case R.id.pdfListingFragment: {
                if(prevDestination==R.id.solvedPapersFragment)
                break;
            }
            /*case R.id.chapterListFragment: {
                tvCustomToolbar.setText(previousDestinationName);
                // tvCustomToolbar.setText("Solved Question Papers");
                break;
            }*/

        }
        prevDestination=destination.getId();
    }

    @Override
    public void subjectClicked(String subjectName) {
        //  tvCustomToolbar.setText(subjectName);
        previousDestinationName = subjectName;
    }

    private void loadUserInfo() {
        if (!loginUsing.contentEquals(Constant.LOGIN_TYPE_GUEST)) {
            tvNavHeadUserNa.setText(pref.getString(Constant.USER_NAME));
            tvNavHeadUserEmail.setText(pref.getString(Constant.USER_EMAIL));
            tvNavHeadUserEmail.setVisibility(View.VISIBLE);
        } else {
            tvNavHeadUserEmail.setVisibility(View.GONE);
        }

    }

    private void logoutPopup() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Logout");
        alertDialogBuilder.setTitle("Do you want to logout ?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isFbLoggedIn()) {
                    LoginManager.getInstance().logOut();
                    Log.d(TAG, "logout: fb");
                } else {

                    logoutGoogle();
                    FirebaseAuth.getInstance().signOut();

                    Log.d(TAG, "logout: google");
                }
                pref.clearAll();
                pref.putString(Constant.APP_LOGIN_STATES, Constant.NOT_FIRST_TIME);
                dialog.dismiss();
               // navController.navigate(R.id.action_dashboardFragment_to_landingActivity);
                Intent intent = new Intent(MainActivity.this, LandingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }



    private void openUrl(String url){
        if(!url.isEmpty()) {
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }/*else{
            Toast.makeText(this, "Ad url not found", Toast.LENGTH_SHORT).show();
        }*/
    }

    private boolean isFbLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && !accessToken.isExpired();
    }

    public void logoutGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("941623854657-pekddno97ib7bco44sfv3ourvapodk49.apps.googleusercontent.com")
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            mGoogleSignInClient.signOut();
        }
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce || !isInDashboardFragment) {
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce=true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        },2000);
    }
    private void uploadUserDataToFirebase(){

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Users").child(pref.getString(Constant.USER_ID));
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    ref1.child(Constant.USER_ID).setValue(pref.getString(Constant.USER_ID));
                    ref1.child(Constant.USER_NAME).setValue(pref.getString(Constant.USER_NAME));
                    ref1.child(Constant.USER_EMAIL).setValue(pref.getString(Constant.USER_EMAIL));
                    ref1.child(Constant.USER_ADDRESS).setValue(pref.getString(Constant.USER_ADDRESS));
                    ref1.child(Constant.USER_PH).setValue(pref.getString(Constant.USER_PH));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "User data update to Firebase: Failed");

            }
        });
    }

}
