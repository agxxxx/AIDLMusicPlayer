package com.aidlmusicplayer.www.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author：agxxxx on 2017/3/3 17:31
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/3.
 */

public class SongListBean implements Parcelable {

    public String artist_id;
    public String language;
    public String pic_big;
    public String pic_small;
    public String country;
    public String area;
    public String publishtime;
    public String album_no;
    public String lrclink;
    public String copy_type;
    public String hot;
    public String all_artist_ting_uid;
    public String resource_type;
    public String is_new;
    public String rank_change;
    public String rank;
    public String all_artist_id;
    public String style;
    public String del_status;
    public String relate_status;
    public String toneid;
    public String all_rate;
    public String file_duration;
    public String has_mv_mobile;
    public String versions;
    public String bitrate_fee;
    public String song_id;
    public String title;
    public String ting_uid;
    public String author;
    public String album_id;
    public String album_title;
    public String is_first_publish;
    public String havehigh;
    public String charge;
    public String has_mv;
    public String learn;
    public String song_source;
    public String piao_id;
    public String korean_bb_song;
    public String resource_type_ext;
    public String mv_provider;
    public String artist_name;


    protected SongListBean(Parcel in) {
        artist_id = in.readString();
        language = in.readString();
        pic_big = in.readString();
        pic_small = in.readString();
        country = in.readString();
        area = in.readString();
        publishtime = in.readString();
        album_no = in.readString();
        lrclink = in.readString();
        copy_type = in.readString();
        hot = in.readString();
        all_artist_ting_uid = in.readString();
        resource_type = in.readString();
        is_new = in.readString();
        rank_change = in.readString();
        rank = in.readString();
        all_artist_id = in.readString();
        style = in.readString();
        del_status = in.readString();
        relate_status = in.readString();
        toneid = in.readString();
        all_rate = in.readString();
        file_duration = in.readString();
        has_mv_mobile = in.readString();
        versions = in.readString();
        bitrate_fee = in.readString();
        song_id = in.readString();
        title = in.readString();
        ting_uid = in.readString();
        author = in.readString();
        album_id = in.readString();
        album_title = in.readString();
        is_first_publish = in.readString();
        havehigh = in.readString();
        charge = in.readString();
        has_mv = in.readString();
        learn = in.readString();
        song_source = in.readString();
        piao_id = in.readString();
        korean_bb_song = in.readString();
        resource_type_ext = in.readString();
        mv_provider = in.readString();
        artist_name = in.readString();

    }

    public static final Creator<SongListBean> CREATOR = new Creator<SongListBean>() {
        @Override
        public SongListBean createFromParcel(Parcel in) {
            return new SongListBean(in);
        }

        @Override
        public SongListBean[] newArray(int size) {
            return new SongListBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(artist_id);
        dest.writeString(language);
        dest.writeString(pic_big);
        dest.writeString(pic_small);
        dest.writeString(country);
        dest.writeString(area);
        dest.writeString(publishtime);
        dest.writeString(album_no);
        dest.writeString(lrclink);
        dest.writeString(copy_type);
        dest.writeString(hot);
        dest.writeString(all_artist_ting_uid);
        dest.writeString(resource_type);
        dest.writeString(is_new);
        dest.writeString(rank_change);
        dest.writeString(rank);
        dest.writeString(all_artist_id);
        dest.writeString(style);
        dest.writeString(del_status);
        dest.writeString(relate_status);
        dest.writeString(toneid);
        dest.writeString(all_rate);
        dest.writeString(file_duration);
        dest.writeString(has_mv_mobile);
        dest.writeString(versions);
        dest.writeString(bitrate_fee);
        dest.writeString(song_id);
        dest.writeString(title);
        dest.writeString(ting_uid);
        dest.writeString(author);
        dest.writeString(album_id);
        dest.writeString(album_title);
        dest.writeString(is_first_publish);
        dest.writeString(havehigh);
        dest.writeString(charge);
        dest.writeString(has_mv);
        dest.writeString(learn);
        dest.writeString(song_source);
        dest.writeString(piao_id);
        dest.writeString(korean_bb_song);
        dest.writeString(resource_type_ext);
        dest.writeString(mv_provider);
        dest.writeString(artist_name);
    }
}