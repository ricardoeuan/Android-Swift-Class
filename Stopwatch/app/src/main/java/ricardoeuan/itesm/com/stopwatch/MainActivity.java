package ricardoeuan.itesm.com.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int milli = 0;
    private boolean running;
    private boolean wasRunning;
    private boolean wasPaused;

    private ArrayList<String> lapList;
    private ArrayAdapter<String> lapListAdapter;

    private TextView timerView;
    private ListView lapListView;
    private LinearLayout controlLinearLayout;
    private Button startBtn;
    private Button stopBtn;
    private Button resumeBtn;
    private Button lapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null) {
            milli = savedInstanceState.getInt("milli");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            wasPaused = savedInstanceState.getBoolean("wasPaused");
            lapList = savedInstanceState.getStringArrayList("laps");
        } else {
            wasRunning = false;
            wasPaused = false;
            lapList = new ArrayList<>();
        }
        controlLinearLayout = (LinearLayout) findViewById(R.id.control_layout);
        startBtn = (Button) findViewById(R.id.start_button);
        stopBtn = (Button) findViewById(R.id.stop_button);
        resumeBtn = (Button) findViewById(R.id.resume_btn);
        lapBtn = (Button) findViewById(R.id.lap_button);
        lapListView = (ListView) findViewById(R.id.lap_list);
        lapListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, lapList);
        lapListView.setAdapter(lapListAdapter);
        runTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning) {
            running = true;
            setRunningUI();
        } else if(wasPaused) {
            setRunningUI();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("milli", milli);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
        savedInstanceState.putBoolean("wasPaused", wasPaused);
        savedInstanceState.putStringArrayList("laps", lapList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickStart(View view) {
        running = true;
        setRunningUI();
    }

    public void onClickStop(View view) {
        running = false;
        wasPaused = true;
        switchStopResume();
    }

    public void OnClickResume(View view) {
        running = true;
        wasPaused = false;
        switchStopResume();
    }

    public void onClickReset(View view) {
        running = false;
        wasPaused = false;
        milli = 0;
        resetUI();
    }

    public void onClickLap(View view) {
        lapList.add(timerView.getText().toString());
        lapListAdapter.notifyDataSetChanged();
    }

    private void setRunningUI() {
        switchStopResume();
        startBtn.setVisibility(View.GONE);
        controlLinearLayout.setVisibility(View.VISIBLE);
        lapBtn.setVisibility(View.VISIBLE);
    }

    private void resetUI() {
        controlLinearLayout.setVisibility(View.GONE);
        lapBtn.setVisibility(View.GONE);
        startBtn.setVisibility(View.VISIBLE);
        lapListAdapter.clear();
        lapListAdapter.notifyDataSetChanged();
    }

    private void switchStopResume(){
        if(running) {
            resumeBtn.setVisibility(View.GONE);
            stopBtn.setVisibility(View.VISIBLE);
        } else {
            stopBtn.setVisibility(View.GONE);
            resumeBtn.setVisibility(View.VISIBLE);
        }
    }

    private void runTimer() {
        timerView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = milli / 3600000;
                int minutes = milli / 3600;
                int secs = (milli % 3600) / 60;
                int msecs = milli % 60;
                String time = String.format("%02d:%02d:%02d:%02d", hours, minutes, secs, msecs);
                timerView.setText(time);
                if (running) {
                    milli++;
                }
                handler.postDelayed(this, 10);
            }
        });
    }
}
