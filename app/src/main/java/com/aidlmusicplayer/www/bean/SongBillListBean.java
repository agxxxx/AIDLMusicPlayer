package com.aidlmusicplayer.www.bean;

import java.util.List;

/**
 * author：agxxxx on 2017/3/3 11:20
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/3.
 */

public class SongBillListBean {



    public BillboardBean billboard;
    public String error_code;
    public List<SongListBean> song_list;

    public static class BillboardBean {
    

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
    }

    public static class SongListBean {
       

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
    }
}
