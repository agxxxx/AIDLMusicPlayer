package com.aidlmusicplayer.www.bean;

import java.util.List;

/**
 * author：agxxxx on 2017/3/3 10:49
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/3.
 */

public class RecommandSongListBean {


    public int error_code;
    public ResultBean result;

    public static class ResultBean {
        public List<ListBean> list;
        public static class ListBean {
            public String artist_id;
            public String all_artist_id;
            public String all_artist_ting_uid;
            public String language;
            public String publishtime;
            public String album_no;
            public String toneid;
            public String all_rate;
            public String pic_small;
            public String pic_big;
            public String hot;
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
        }
    }
}
