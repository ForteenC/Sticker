package com.example.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final int DELETE_DATA = 0;


    RecyclerView mRecyclerView;
    List<Sticker> mStickers;
    StickerAdapter mAdapter;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DELETE_DATA:
                    mAdapter.notifyItemRemoved(msg.getData().getInt("position"));
                    break;
                default:
                    break;

            }
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LitePalUtil.init();
        mStickers = LitePalUtil.getAll();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new StickerAdapter(mStickers, this,mHandler);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    View child = mRecyclerView.getFocusedChild();
                    if (child != null) {
                        child.clearFocus();
                    }
                }
                return false;
            }
        });
    }

    /**
     * 添加一个新任务
     */
    private void addSticker() {

        Sticker sticker = new Sticker();
        String content = "这是新添加的便签";
        sticker.setContent(content);

        mStickers.add(sticker);
        mAdapter.notifyItemInserted(mStickers.size() - 1);
        mRecyclerView.scrollToPosition(mStickers.size() - 1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                addSticker();
                break;
            default:
                Toast.makeText(MainActivity.this, "点击了按钮", Toast.LENGTH_SHORT);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        LitePalUtil.deleteAll();
    }
}
