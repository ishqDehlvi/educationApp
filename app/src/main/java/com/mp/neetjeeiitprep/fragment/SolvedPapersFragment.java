package com.mp.neetjeeiitprep.fragment;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class SolvedPapersFragment extends Fragment {

    private View rootView;
    static PDFView pdfView;
    static ProgressDialog pd;

    //   String url1 = "https://sherlock-holm.es/stories/pdf/a4/1-sided/advs.pdf";
    private static String TAG = "SolvedPapersFragmentTAG";

    public SolvedPapersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_solved_papers, container, false);

        pd = new ProgressDialog(getContext());
        pd.setMessage("loading..");
        pdfView = rootView.findViewById(R.id.pdfView);
        String url1 = getArguments().getString(Constant.PDF_URL);
        new RetrivePDFStream().execute(url1);

        return rootView;
    }

    static class RetrivePDFStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected void onPreExecute() {
            pd.show();
            Log.d(TAG, "onPreExecute: ");
            super.onPreExecute();
        }

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                Log.d(TAG, "doInBackground: 0 "+strings[0]);
                URL uri = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
                Log.d(TAG, "doInBackground: 1");
            } catch (IOException e) {
                pd.dismiss();
                Log.e(TAG, "doInBackground: error " + e.toString());
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView
                    .fromStream(inputStream)
                    .spacing(10)
                    .onRender(new OnRenderListener() {
                        @Override
                        public void onInitiallyRendered(int nbPages) {
                            pd.dismiss();
                        }
                    })
                    .onError(new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                            pd.dismiss();
                            Log.e(TAG, "pdf error " + t.toString());
                        }
                    })
                    .load();
            Log.d(TAG, "onPostExecute: ");
        }
    }

}
