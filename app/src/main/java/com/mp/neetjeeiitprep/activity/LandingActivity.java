package com.mp.neetjeeiitprep.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mp.neetjeeiitprep.AppUtils.AppUtils;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.dataModel.LoginRegisterResponseDataModel;
import com.mp.neetjeeiitprep.fragment.AppIntroFragment;
import com.mp.neetjeeiitprep.fragment.LandingFragment;
import com.mp.neetjeeiitprep.fragment.LoginFragment;
import com.mp.neetjeeiitprep.fragment.RegisterFragment;
import com.mp.neetjeeiitprep.interfaces.CallbackToLandingListener;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingActivity extends AppCompatActivity implements CallbackToLandingListener {

    private static String TAG;
    private Pref pref;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 169;
    private CallbackManager callbackManager;
    public int regType;
    public HashMap<String, String> socialData;
    boolean doubleBackToExitPressedOnce = false;
    private WebApi webApi;
    private ProgressDialog pd;
    private LoginManager loginManager;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        setTheme(R.style.AppTheme);
        pd = new ProgressDialog(LandingActivity.this);
        pd.setMessage("Loading...");

        mAuth = FirebaseAuth.getInstance();
        webApi = ApiClient.getApiInstance().create(WebApi.class);
        initFB();
        initGoogleSign();
        pref = new Pref(this);
        if (!pref.getString(Constant.APP_LOGIN_STATES).contentEquals(Constant.NOT_FIRST_TIME)) {
            loadFragment(new AppIntroFragment());
            pref.putString(Constant.APP_LOGIN_STATES, Constant.NOT_FIRST_TIME);
        } else {
           // loadFragment(new LandingFragment());
            loadLandingFragment(new LandingFragment());
        }

      //  throw new RuntimeException("Test Crash"); // Force a crash
    }

    @Override
    public void changeFragment(int loadFragment) {

        /*Intent intent=new Intent(LandingActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);*/

        switch (loadFragment) {
            case Constant.FRAGMENT_LANDING: {
               // loadFragment(new LandingFragment());

                loadLandingFragment(new LandingFragment());
                break;
            }
            case Constant.FRAGMENT_LOGIN: {
                loadFragment(new LoginFragment());
                break;
            }
            case Constant.FRAGMENT_REGISTER: {
                regType = 0;
                loadFragment(new RegisterFragment());
                break;
            }

        }
    }

    public void loadFragment(Fragment frag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.landingFragContainer, frag);
        ft.addToBackStack(null);
        ft.commit();

    }
    public void loadLandingFragment(Fragment frag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.landingFragContainer, frag);
        //ft.addToBackStack(null);
        ft.commit();

    }

    public void initGoogleSign() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("941623854657-pekddno97ib7bco44sfv3ourvapodk49.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
    }


    private boolean isLoggedIn() {

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }


    public void signIn(String socialPlatform) {
        pd.show();
        if (socialPlatform.contentEquals("google")) {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } else {
            LoginManager.getInstance().logInWithReadPermissions(LandingActivity.this, Arrays.asList("public_profile", "email")); // starting the login process.
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        if (!completedTask.isSuccessful()) return;
        Log.d("signin", String.valueOf(completedTask.getResult()));

        try {

                GoogleSignInAccount account = completedTask.getResult(ApiException.class);
                // Signed in successfully, show authenticated UI.
                 Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                if (account != null) {
                    System.out.println("Email ->" + account.getEmail());
                    System.out.println("DisplayName ->" + account.getDisplayName());
                    System.out.println("GivenName ->" + account.getGivenName());
                    System.out.println("FamilyName ->" + account.getFamilyName());
                    System.out.println("Profile Pic ->" + account.getPhotoUrl());

                    sendSocialData(account.getDisplayName(), account.getEmail(), Constant.LOGIN_TYPE_GP,null);
                }

            } catch(ApiException e){
                Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                Toast.makeText(this, e.getStatusCode(), Toast.LENGTH_SHORT).show();
            }

    }

    /**
     * Login using Your Owen Custom Button. ===================================================================================
     **/

    private void initFB() {
        pd.dismiss();

        callbackManager = CallbackManager.Factory.create();
        loginManager
                = LoginManager.getInstance();
        callbackManager
                = CallbackManager.Factory.create();

        loginManager
                .registerCallback(
                        callbackManager,
                        new FacebookCallback<LoginResult>() {

                            @Override
                            public void onSuccess(LoginResult loginResult)
                            {

                                GraphRequest request = GraphRequest.newMeRequest(

                                        loginResult.getAccessToken(),

                                        new GraphRequest.GraphJSONObjectCallback() {

                                            @Override
                                            public void onCompleted(JSONObject object,
                                                                    GraphResponse response)
                                            {

                                                if (object != null) {
                                                    try {
                                                        String name = object.getString("name");
                                                        Log.d("aaa",name);
                                                        String fbUserID = object.getString("id");
                                                        Log.d("aaa",object.toString());
//                                                        String email = object.getString("email");

                                                        sendSocialData(object.getString("name"), "N/A", Constant.LOGIN_TYPE_FB,fbUserID);
                                                        Log.d("login",fbUserID);
//                                                        disconnectFromFacebook();
                                                        // do action after Facebook login success
                                                        // or call your API
                                                    }
                                                    catch (JSONException | NullPointerException e) {
                                                        e.printStackTrace();
                                                        Log.d("aaa",e.getMessage());
                                                    }
                                                }
                                            }
                                        });

                                Bundle parameters = new Bundle();
                                parameters.putString(
                                        "fields",
                                        "id, name, email, gender, birthday");
                                request.setParameters(parameters);
                                request.executeAsync();
                            }

                            @Override
                            public void onCancel()
                            {
                                Log.v("LoginScreen", "---onCancel");
                            }

                            @Override
                            public void onError(FacebookException error)
                            {
                                // here write code when get error
                                Log.v("LoginScreen", "----onError: "
                                        + error.getMessage());
                            }
                        });

//        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "onSuccess: " + loginResult.toString());
//                showProfileInfo();
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "onCancel: ");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG, "onError: " + error);
//                Log.d("aaaaa","aaa");
//
//            }
//        });

    }


    /**
     * Get profile info using Graph api ==============================================================================
     **/
    private void showProfileInfo() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                System.out.println("Graph response ->" + object.toString());
                try {
                    sendSocialData(object.getString("name"), object.getString("email"), Constant.LOGIN_TYPE_FB,object.getString("id"));
                } catch (JSONException e) {
                    Log.e(TAG, "onCompleted: " + e.toString());
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,first_name,picture,link"); //  add fields to get the info of that field.
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    private void sendSocialData(String name, String email, String loginType,String fb_id) {
        socialData = new HashMap<>();
        socialData.put("Email", email);
        socialData.put("DisplayName", name);
        socialData.put("loginType", loginType);
        socialData.put("fb_id",fb_id);
        regType = 1;
        startSocialLogin(name,email,loginType,fb_id);
    }

    private void startSocialLogin(String na, String email, final String loginType,String fb_id) {

        HashMap<String, String> params = new HashMap<>();
        params.put("name", na);
        params.put("email", email);
        if (fb_id==null){
            params.put("fb_id","na");

        }else {
            params.put("fb_id", fb_id);
        }
        params.put("device_type", "android");
        params.put("login_using", loginType);
        params.put("firebase_token", "na");
        params.put("device_unique_id", "na");
        Call<LoginRegisterResponseDataModel> callApi = webApi.socialLoginForUser(params);
        callApi.enqueue(new Callback<LoginRegisterResponseDataModel>() {
            @Override
            public void onResponse(Call<LoginRegisterResponseDataModel> call, Response<LoginRegisterResponseDataModel> response) {
             //   Log.d(TAG, "onResponse: " + response.body().toString());
                saveSocialData(response,loginType);
            }

            @Override
            public void onFailure(Call<LoginRegisterResponseDataModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());

            }
        });
    }

    private void saveSocialData(Response<LoginRegisterResponseDataModel> response,String loginType) {

        LoginRegisterResponseDataModel resp = response.body();
        if (resp.getCode().contentEquals(Constant.CODE_SUCCESS)) {

            pref.putString(Constant.USER_ID, resp.getData().getUserId());
            pref.putString(Constant.USER_NAME, resp.getData().getName());
            pref.putString(Constant.USER_EMAIL, resp.getData().getEmail());
            pref.putString(Constant.USER_FB_ID, resp.getData().getFbId());

            pref.putString(Constant.USER_ADDRESS, "");
            pref.putString(Constant.USER_PH, resp.getData().getPhone());
            pref.putString(Constant.LOGIN_USING, loginType);

            Intent intent = new Intent(LandingActivity.this, MainActivity.class);
            // intent.putExtra(Constant.LOGIN_USING,Constant.LOGIN_TYPE_EMAIL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LandingActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN) {
//            super.onActivityResult(requestCode, resultCode, data);
//            if ( data != null) {
//
//                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//                handleSignInResult(task);
//            }
//        } else {
//            callbackManager.onActivityResult(requestCode, resultCode, data);
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//
//
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(
                requestCode,
                resultCode,
                data);
        super.onActivityResult(requestCode, resultCode, data);
        pd.dismiss();

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed"+e.getMessage(), e);
            }
        }
    }


    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();

        if (doubleBackToExitPressedOnce || fm.getBackStackEntryCount()!=0) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
        if(currentUser!=null){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        }

        Log.d("signin", String.valueOf(currentUser));
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            sendSocialData(user.getDisplayName(), user.getEmail(), Constant.LOGIN_TYPE_GP,null);


                            Log.d("signinfromFAWG", String.valueOf(user));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }


    public void disconnectFromFacebook()
    {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse)
                    {
                        LoginManager.getInstance().logOut();
                    }
                })
                .executeAsync();
    }
}
