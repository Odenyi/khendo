package com.streaming.khendo;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.khendo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;



public class Home extends AppCompatActivity  {
    private Boolean isSwitchPlayPause=false;
    //    private Button playButton, stopButton;
    private long backPressedTime;
    private Toast backToast;

    private boolean isOnline=false;

    //Settings
    Settings settings = new Settings();
    
    private FloatingActionButton playButton;



    // Progress dialogue and broadcast receiver variables
    boolean mBufferBroadcastIsRegistered;
    private ProgressDialog pdBuff = null;


//imageslider
    SliderView sliderView;
    int[] images = {R.drawable.khendohome,
            R.drawable.khendo10,
            R.drawable.khendo11,
            R.drawable.khendo13,
            };
    /**
     * Done upon opening the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.home);

        //SLIDDER
        sliderView = findViewById(R.id.image_slider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        //checks comnectivityy first
        checkConnectivity();
        ImageView shows = findViewById(R.id.shows);
        ImageView presenters = findViewById(R.id.presenters);
        ImageView youtube = findViewById(R.id.youtube);
        ImageView about = findViewById(R.id.about);
        //click events
        shows.setOnClickListener(v -> {
            startActivity(new Intent(this,Shows.class));

        });
        presenters.setOnClickListener(v -> {
            startActivity(new Intent(this,Presenters.class));

        });
        about.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(settings.getWebsiteURL())));

        });
        youtube.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(settings.getYoutube())));

        });

        // bottom tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0) {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(settings.getFacebook())); //URL
                    startActivity(facebookIntent);
                }
                if (tab.getPosition()==1) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(settings.getInstagram())); //URL
                    startActivity(browserIntent);
                }
                if (tab.getPosition()==2) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(settings.getTwitter())));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition()==0) {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(settings.getFacebook())); //URL
                    startActivity(facebookIntent);
                }
                if (tab.getPosition()==1) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(settings.getInstagram())); //URL
                    startActivity(browserIntent);
                }
                if (tab.getPosition()==2) {

                }

            }
        });


        playButton =(FloatingActionButton) findViewById(R.id.playpause);

        playButton.setOnClickListener(v -> {
            checkConnectivity();
            if(isOnline) {

                if (isSwitchPlayPause) {

                    Intent intent = new Intent(getApplicationContext(), RadioMediaPlayerService.class);
                    intent.putExtra(RadioMediaPlayerService.START_PLAY, true);
                    getApplicationContext().startService(intent);
                    playButton.setImageResource(R.drawable.paus);

                }else{
                    playButton.setImageResource(R.drawable.play);
                    Toast.makeText(Home.this, "Stopping Radio Khendo", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),
                            RadioMediaPlayerService.class);
                    stopService(intent);

                }
                isSwitchPlayPause=!isSwitchPlayPause;

            }
            else{
                playButton.setImageResource(R.drawable.play);

                AlertDialog alertDialog = new AlertDialog.Builder(Home.this).create();
                alertDialog.setTitle("Network Not Connected...");
                alertDialog.setMessage("Please connect to a network and try again");
                alertDialog.setButton("OK", (dialog, which) -> {
                    // here you can add functions
                });

                alertDialog.show();


            }
        });

        if(isOnline) {
            playButton.setImageResource(R.drawable.paus);

            Intent intent = new Intent(getApplicationContext(), RadioMediaPlayerService.class);
            intent.putExtra(RadioMediaPlayerService.START_PLAY, true);
            getApplicationContext().startService(intent);


        }
        else {
            playButton.setImageResource(R.drawable.play);
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Network Not Connected...");
            alertDialog.setMessage("Please connect to a network and try again");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // here you can add functions
                }
            });

            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawableResource(R.color.purple_500);
        }





    }
    //onresume

    // Handle progress dialogue for buffering...
    private void showPD(Intent bufferIntent) {
        String bufferValue = bufferIntent.getStringExtra("buffering");
        int bufferIntValue = Integer.parseInt(bufferValue);

        switch (bufferIntValue) {
            case 0:
                if (pdBuff != null) {
                    pdBuff.dismiss();
                }
                break;

            case 1:
                BufferDialogue();
                break;


        }
    };

    // Progress dialogue...
    private void BufferDialogue() {

        pdBuff = ProgressDialog.show(Home.this, "Loading KhendoFM" +
                        " Stream...",
                "Acquiring stream...", true);
        pdBuff.getWindow().setBackgroundDrawableResource(R.color.purple_700);
    }

    // Set up broadcast receiver
    private BroadcastReceiver broadcastBufferReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent bufferIntent) {
            showPD(bufferIntent);
        }
    };

    // -- onResume register broadcast receiver. To improve, retrieve saved screen data ---
    @Override
    protected void onResume() {
        // Register broadcast receiver
        if (!mBufferBroadcastIsRegistered) {
            registerReceiver(broadcastBufferReceiver, new IntentFilter(
                    RadioMediaPlayerService.BROADCAST_BUFFER));
            mBufferBroadcastIsRegistered = true;
        }


        super.onResume();
    }
    // -- onPause, unregister broadcast receiver. To improve, also save screen data ---
    @Override
    protected void onPause() {
        // Unregister broadcast receiver
        if (mBufferBroadcastIsRegistered) {
            unregisterReceiver(broadcastBufferReceiver);
            mBufferBroadcastIsRegistered = false;
        }
        super.onPause();
    }
    //
    private void checkConnectivity() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting()
                || cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting())
            isOnline = true;
        else
            isOnline = false;
    }
    public static void shareApp(Context context) {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {

            backToast.cancel();
            super.onBackPressed();
            return;
        } else {

            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}

