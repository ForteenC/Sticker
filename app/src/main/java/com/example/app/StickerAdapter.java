package com.example.app;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.List;

/**
 * Created by DBW on 2017/5/28.
 *
 */

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

    private static final String TAG = "StickerAdapter";

    private List<Sticker> mStickers;
    private Context mContext;
    private Handler mHandler;

    static class ViewHolder extends RecyclerView.ViewHolder {

        EditText content;
        CheckBox check;

        ViewHolder(View itemView) {
            super(itemView);
            content = (EditText) itemView.findViewById(R.id.task_content);
            check = (CheckBox) itemView.findViewById(R.id.task_check);
        }
    }

    public StickerAdapter(List<Sticker> stickers, Context context, Handler handler) {

        mStickers = stickers;
        mContext = context;
        mHandler = handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sticker_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        final int po = position;

        final Sticker sticker = mStickers.get(position);

        viewHolder.content.setText(sticker.getContent());
        viewHolder.content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    EditText editText = (EditText) v;
                    Sticker st = new Sticker();
                    st.setContent(editText.getText().toString());
                    st.setPosition(po);
                    st.setChecked(sticker.isChecked());
                    LitePalUtil.save(st);

                    //隐藏软键盘
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
            }
        });

        viewHolder.content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("提示")
                        .setMessage("确定删除该项么？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LitePalUtil.delete(sticker);
                                mStickers.remove(sticker);
                                dialog.dismiss();
                                Bundle bundle = new Bundle();
                                bundle.putInt("position",po);
                                Message msg = new Message();
                                msg.setData(bundle);
                                msg.what = MainActivity.DELETE_DATA;
                                mHandler.sendMessage(msg);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create().show();
                return false;
            }
        });

        if (sticker.isChecked()){
            viewHolder.check.setChecked(true);
            viewHolder.content.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        }
        viewHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Sticker sticker = mStickers.get(po);
                    sticker.setChecked(true);
                    LitePalUtil.update(sticker);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mStickers.size();
    }


}
