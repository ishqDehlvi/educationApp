package com.mp.neetjeeiitprep.AppUtils;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.activity.LandingActivity;


/**
 * Created by Mrinmoy on 6/13/2017.
 */

public class AppUtils {

    public static boolean isOnline(Context c) {

        Context con = c;
        ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        /*if (activeNetwork != null) { // check if it's connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                //if connected to wifi it will ping to the address to see if it connected to internet
                Runtime runtime = Runtime.getRuntime();
                try {

                    Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                    int     exitValue = ipProcess.waitFor();
                    isConnected=(exitValue == 0);
                    return isConnected;

                } catch (IOException e)          { e.printStackTrace(); }
                catch (InterruptedException e) { e.printStackTrace(); }

                isConnected=false;
            }
        }*/
        return isConnected;
    }

    public static void startLoadingAnimation(final View View, final ProgressBar pb) {

        View.animate().
                scaleX(0f).
                alpha(0).
                setDuration(200).start();


        View.animate().setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                pb.setVisibility(View.VISIBLE);
                View.setVisibility(View.INVISIBLE);
            }
        });
    }

    public static void stopLoadingAnimation(final View View, final ProgressBar pb) {

        pb.setVisibility(View.GONE);
        View.animate().
                scaleX(1).
                alpha(1).
                setDuration(400).start();

        View.animate().setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                View.setVisibility(View.VISIBLE);
            }
        });
    }

    /*public static void openSignInDialog(final Context context){
        AlertDialog.Builder builder=new AlertDialog.Builder(context, R.style.AppDialog)
        .setTitle("Sign in to access all features")
        .setMessage("Sign in now ?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                context.startActivity(new Intent(context, LandingActivity.class));
                ((Activity)context).finish();


            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }*/

    public static void openSignInDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.ask_signin_popup_layout);


        final TextView tvSgnPopUpHead = dialog.findViewById(R.id.tvSgnPopUpHead);
        final TextView tvSgnMsg = dialog.findViewById(R.id.tvSgnMsg);

        tvSgnPopUpHead.setText("Sign in to access all features");
        tvSgnMsg.setText("Sign in now ?");

        final MaterialButton btnCancelSign = dialog.findViewById(R.id.btnCancelSign);
        final MaterialButton btnGotoSign = dialog.findViewById(R.id.btnGotoSign);

        btnGotoSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                context.startActivity(new Intent(context, LandingActivity.class));
                ((Activity)context).finish();
            }
        });

        btnCancelSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


        dialog.show();

        /***   getting display height and width   ***/
        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        /*****/
    }

}
