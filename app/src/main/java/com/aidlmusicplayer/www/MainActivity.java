package com.aidlmusicplayer.www;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.aidlmusicplayer.www.activity.MusicActivity;
import com.aidlmusicplayer.www.base.BaseRecyclerViewAdapter;
import com.aidlmusicplayer.www.bean.SongBillListBean;
import com.aidlmusicplayer.www.image.ImageLoader;
import com.aidlmusicplayer.www.image.ImageLoaderProxy;
import com.aidlmusicplayer.www.net.NetCallBack;
import com.aidlmusicplayer.www.net.NetManager;
import com.bumptech.glide.load.resource.bitmap.FitCenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * author：agxxxx on 2017/3/3 10:49
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/3.
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView mRvContainer;
    private List<SongBillListBean.SongListBean> mSong_list = new ArrayList<>();
    private SongListAdapter mSongListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRvContainer = ButterKnife.findById(this, R.id.rv_container);


        initVie();
        initData();


    }




    int mType = 1;
    int mSize = 10;
    int mOffset = 0;
    private void initData() {
        NetManager.
                getInstance().getSongBillListData(mType,
                mSize,
                mOffset,
                new NetCallBack<SongBillListBean>() {
                    @Override
                    public void onSuccess(SongBillListBean songBillListBean) {
                        mSongListAdapter.removeAll();
                        mSongListAdapter.addAll(songBillListBean.song_list);
                    }
                    @Override
                    public void onFailure(String msg) {
                    }
                });
    }




    private void initVie() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRvContainer.setLayoutManager(staggeredGridLayoutManager);
        mSongListAdapter = new SongListAdapter(mSong_list);
        mSongListAdapter.setOnItemClickListener(mSongListAdapter);
        mRvContainer.setAdapter(mSongListAdapter);
    }

    class SongListAdapter extends BaseRecyclerViewAdapter<SongBillListBean.SongListBean>
            implements BaseRecyclerViewAdapter.OnItemClickListener<SongBillListBean.SongListBean>{
        public SongListAdapter(List<SongBillListBean.SongListBean> mDatum) {
            super(mDatum);
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.item_music;
        }
        @Override
        protected void onBind(ViewHolder holder, int position, SongBillListBean.SongListBean data) {
            ImageView imageView = holder.getImageView(R.id.iv_icon);
            ImageLoaderProxy.getInstance().load(MainActivity.this,
                    new ImageLoader.Builder()
                            .load(data.pic_big)
                            .into(imageView)
                            .transform(new FitCenter(MainActivity.this))
                            .build());
            holder.setTextView(R.id.tv_name,data.title);
        }
        @Override
        public void onItemClick(View view, int position, SongBillListBean.SongListBean info) {
            Intent intent = new Intent(MainActivity.this, MusicActivity.class);
            startActivity(intent);
        }
    }


}
