package com.mp.neetjeeiitprep.activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VideoPlayerActivity extends AppCompatActivity {
    static ProgressDialog pd;
    TextView  VideoPlayerTitle;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);


//
        Intent i = getIntent();
        String url1 = i.getStringExtra(Constant.VIDEO_URL);
        String[] url = url1.split("=", 2);
        String videoId = url[1];
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading Data...");
//        progressDialog.setCancelable(false);
//        WebView web_view = findViewById(R.id.web_view);
//        web_view.requestFocus();
//        web_view.getSettings().setLightTouchEnabled(true);
//        web_view.getSettings().setJavaScriptEnabled(true);
//        web_view.getSettings().setGeolocationEnabled(true);
//        web_view.setSoundEffectsEnabled(true);
//        web_view.loadUrl("https://www.youtube.com/watch?v="+videoId);
//        web_view.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                if (progress < 100) {
//                    progressDialog.show();
//                }
//                if (progress == 100) {
//                    progressDialog.dismiss();
//                }
//            }
//        });
        webView = findViewById(R.id.web_view);

        // on below line setting web view client.
        webView.setWebViewClient(new WebClient());

        // on below line setting web chrome client for web view.
        webView.setWebChromeClient(new WebChromeClient());

        // on below line getting web settings.
        WebSettings webSettings = webView.getSettings();

        // on below line setting java script enabled to true.
        webSettings.setJavaScriptEnabled(true);

        // on below line setting file access to true.
        webSettings.setAllowFileAccess(true);

        // on below line setting url for the web page which we have to load in our web view.
        webSettings.setMediaPlaybackRequiresUserGesture(true);

        webView.loadUrl("https://www.youtube.com/watch?v="+videoId+"&lc=#comment");
//        openYouTubeComments(videoId);



//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                webView.loadUrl("javascript:(function() { " +
//                        "'use strict';\n" +
//                        "\n" +
//                        "let request = require('request');\n" +
//                        "\n" +
//                        "const sourceId = '< youtube video id>';\n" +
//                        "const comment_id = 'the comment id';\n" +
//                        "const comment = 'actual comment';\n" +
//                        "\n" +
//                        "new Promise((resolve, reject) => {\n" +
//                        "  request({\n" +
//                        "    method: 'POST',\n" +
//                        "    url: 'https://www.googleapis.com/youtube/v3/commentThreads',\n" +
//                        "    headers: {\n" +
//                        "      'User-Agent': 'Request-Promise'\n" +
//                        "    },\n" +
//                        "    body: {\n" +
//                        "      \"snippet\": {\n" +
//                        "        \"videoId\": sourceId,\n" +
//                        "        \"channelId\": comment_id,\n" +
//                        "        \"topLevelComment\": {\n" +
//                        "          \"snippet\": {\n" +
//                        "            \"textOriginal\": comment\n" +
//                        "          }\n" +
//                        "        }\n" +
//                        "      }\n" +
//                        "    },\n" +
//                        "    qs: {\n" +
//                        "      part: 'snippet',\n" +
//                        "      access_token: token\n" +
//                        "    },\n" +
//                        "    json: true\n" +
//                        "  }, function (error, response, body) {\n" +
//                        "    if (error) {\n" +
//                        "      console.log('body', body);\n" +
//                        "      console.log('error in when posting comment ', error.stack);\n" +
//                        "      return reject(error);\n" +
//                        "    }\n" +
//                        "    return resolve(body);\n" +
//                        "  });\n" +
//                        "});");
//            }
//        });


//    ===YoutubePlayer by Devansh====
//        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
//        getLifecycle().addObserver(youTubePlayerView);
//
//        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer){
//                youTubePlayer.loadVideo("https://www.youtube.com/watch?v="+videoId+"#Comments", 0);
//            }
//        });



//
//
//        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
//        youTubePlayerView.getPlayerUiController().showVideoTitle(false);
//        youTubePlayerView.getPlayerUiController().showMenuButton(false);
//        getLifecycle().addObserver(youTubePlayerView);
//        pd = new ProgressDialog(VideoPlayerActivity.this);
//        pd.setMessage("Loading...");
//
//        pd.show();
//        Intent i = getIntent();
//        String url1 = i.getStringExtra(Constant.VIDEO_URL);
//        String title = i.getStringExtra("title");
//        VideoPlayerTitle = (TextView) findViewById(R.id.videoPlayerTitle);
//        VideoPlayerTitle.setText(title);
//
//        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                pd.dismiss();
//                String[] url = url1.split("=", 2);
//                String videoId = url[1];
////                System.out.println(url[1]);
//                youTubePlayer.loadVideo(videoId, 0);
//            }
//        });
    }
    // on below line creating a class for web chrome client.
    class WebChromeClient extends android.webkit.WebChromeClient {
        // on below line creating variables.
        private View customView;
        private android.webkit.WebChromeClient.CustomViewCallback customViewCallback;
        private int originalOrientation;
        private int originalSystemVisibility;
        private FrameLayout FrameLayout;

        WebChromeClient() {
        }

        @Nullable
        @Override
        public Bitmap getDefaultVideoPoster() {

            // on below line returning our resource from bitmap factory.
            if (customView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        @Override
        public void onHideCustomView() {

            // on below line removing our custom view and setting it to null.
            ((FrameLayout) getWindow().getDecorView()).removeView(customView);
            this.customView = null;

            // on below line setting system ui visibility to original one and setting orientation for it.
            getWindow().getDecorView().setSystemUiVisibility(this.originalSystemVisibility);
            setRequestedOrientation(this.originalOrientation);

            // on below line setting custom view call back to null.
            this.customViewCallback.onCustomViewHidden();
            this.customViewCallback = null;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (this.customView != null) {
                onHideCustomView();
                return;
            }
            // on below line initializing all variables.
            this.customView = view;
            this.originalSystemVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.originalOrientation = getRequestedOrientation();
            this.customViewCallback = callback;
            getWindow().getWindowManager().addView(this.customView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }
    // on below line creating a class for Web Client.
    class WebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

//    public void openYouTubeComments(String videoId) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + videoId + "/comments"));
//        startActivity(intent);
//    }
//    public class OpenYoutubeComments {
//        public  void main(String[] args) throws IOException, URISyntaxException {
//            String videoId = "VIDEO_ID_HERE";
//            String commentsUrl = "https://www.youtube.com/all_comments?v=6uN3qIT59i4" + videoId;
//            Desktop.getDesktop().browse(new URI(commentsUrl));
//        }
//    }
}