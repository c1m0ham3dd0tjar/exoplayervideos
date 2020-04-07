package ma.safe.fiklusbueyawmjumea;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class EpisodesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EpisodesAdapter songsAdapter;
    private BroadcastReceiver broadcastReceiver;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);


        recyclerView = findViewById(R.id.recyclerView);


        recyclerView.setHasFixedSize(true);
        songsAdapter = new EpisodesAdapter(getApplicationContext(), VideoPlayerConfig.pics, VideoPlayerConfig.Episodes_titles);
        recyclerView.setAdapter(songsAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Intent mIntent =
                     //   ExoPlayerActivity.getStartIntent(SongsActivity.this, VideoPlayerConfig.DEFAULT_VIDEO_URL);
                        ExoPlayerActivity.getStartIntent(EpisodesActivity.this, VideoPlayerConfig.Episodes_links[i]);
                startActivity(mIntent);

           // startActivity(new Intent(getApplicationContext(),ExoPlayerActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), "No internet connection", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("DISMISS", v -> snackbar.dismiss());

        //processPhoneListenerPermission();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                    if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                        if (snackbar.isShown()) {
                            snackbar.dismiss();
                        }
                    }
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PHONE_STATE");
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, filter);
    }

}