package com.aidlmusicplayer.www.bean;


/**
 * author：agxxxx on 2017/3/3 10:49
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/3.
 */

public class PaySongBean {

    public String error_code;
    public BitrateBean bitrate;
    public SonginfoBean songinfo;

    public static class BitrateBean {
        public String file_bitrate;
        public String free;
        public String file_link;
        public String file_extension;
        public String original;
        public String file_size;
        public String file_duration;
        public String show_link;
        public String song_file_id;
        public String replay_gain;
    }
    public static class SonginfoBean {
        public String artist_id;
        public String all_artist_id;
        public String album_no;
        public String pic_big;
        public String pic_small;
        public String relate_status;
        public String resource_type;
        public String copy_type;
        public String lrclink;
        public String pic_radio;
        public String toneid;
        public String all_rate;
        public String play_type;
        public String has_mv_mobile;
        public String pic_premium;
        public String pic_huge;
        public String resource_type_ext;
        public String bitrate_fee;
        public String publishtime;
        public String si_presale_flag;
        public String del_status;
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
        public String mv_provider;
        public String special_type;
        public String collect_num;
        public String share_num;
        public String comment_num;
    }
}
