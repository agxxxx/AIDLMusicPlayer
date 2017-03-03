package com.aidlmusicplayer.www.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * author：agxxxx on 2017/3/3 11:20
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/3.
 */

public class SongBillListBean implements Parcelable {



    public BillboardBean billboard;
    public String error_code;
    public List<SongListBean> song_list;

    protected SongBillListBean(Parcel in) {
        error_code = in.readString();
    }

    public static final Creator<SongBillListBean> CREATOR = new Creator<SongBillListBean>() {
        @Override
        public SongBillListBean createFromParcel(Parcel in) {
            return new SongBillListBean(in);
        }

        @Override
        public SongBillListBean[] newArray(int size) {
            return new SongBillListBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(error_code);
    }

    public static class BillboardBean implements Parcelable  {
    

        public String billboard_type;
        public String billboard_no;
        public String update_date;
        public String billboard_songnum;
        public String havemore;
        public String name;
        public String comment;
        public String pic_s640;
        public String pic_s444;
        public String pic_s260;
        public String pic_s210;
        public String web_url;

        protected BillboardBean(Parcel in) {
            billboard_type = in.readString();
            billboard_no = in.readString();
            update_date = in.readString();
            billboard_songnum = in.readString();
            havemore = in.readString();
            name = in.readString();
            comment = in.readString();
            pic_s640 = in.readString();
            pic_s444 = in.readString();
            pic_s260 = in.readString();
            pic_s210 = in.readString();
            web_url = in.readString();
        }

        public static final Creator<BillboardBean> CREATOR = new Creator<BillboardBean>() {
            @Override
            public BillboardBean createFromParcel(Parcel in) {
                return new BillboardBean(in);
            }

            @Override
            public BillboardBean[] newArray(int size) {
                return new BillboardBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(billboard_type);
            dest.writeString(billboard_no);
            dest.writeString(update_date);
            dest.writeString(billboard_songnum);
            dest.writeString(havemore);
            dest.writeString(name);
            dest.writeString(comment);
            dest.writeString(pic_s640);
            dest.writeString(pic_s444);
            dest.writeString(pic_s260);
            dest.writeString(pic_s210);
            dest.writeString(web_url);
        }
    }


}
