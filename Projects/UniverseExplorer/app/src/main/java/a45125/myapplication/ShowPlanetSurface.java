package a45125.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toolbar;
import android.widget.VideoView;

public class ShowPlanetSurface extends AppCompatActivity {
    private int index;
    private Bundle extras;
    private VideoView vv;
    private String videoPath;
    private Uri uri;
    private MediaController mc;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_planet_surface);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vv = findViewById(R.id.surfaceVideo);
        tv = findViewById(R.id.surface);
        mc = new MediaController(this);

        videoPath = "android.resource://" + getPackageName() + "/" + R.raw.flyover;
        uri = Uri.parse(videoPath);
        vv.setVideoURI(uri);
        vv.setMediaController(mc);
        mc.setAnchorView(vv);

        setTitle("Show Planet Surface");

    }

    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
