package com.aidlmusicplayer.www.bean;

import java.util.List;
/**
 * author：agxxxx on 2017/3/3 10:49
 * email：agxxxx@126.com
 * blog: http://blog.csdn.net/zuiaisha1
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/3.
 */
public class SearchSongBean {

    public String order;
    public int error_code;
    public List<SongBean> song;
    public List<AlbumBean> album;

    public static class SongBean {
        /**
         * bitrate_fee : {"0":"0|0","1":"0|0"}
         * weight : 18340
         * songname : 海阔天空
         * songid : 14880013
         * has_mv : 0
         * yyr_artist : 0
         * artistname : 林忆莲
         * resource_type_ext : 0
         * resource_provider : 1
         * control : 0000000000
         * encrypted_songid : 1606e30d0d095771062dL
         */

        public String bitrate_fee;
        public String weight;
        public String songname;
        public String songid;
        public String has_mv;
        public String yyr_artist;
        public String artistname;
        public String resource_type_ext;
        public String resource_provider;
        public String control;
        public String encrypted_songid;
    }

    public static class AlbumBean {
        /**
         * albumname : 海阔天空任我闯
         * weight : 0
         * artistname : 张明敏
         * resource_type_ext : 0
         * artistpic : http://qukufile2.qianqian.com/data2/music/66C7BCD4198951DDBC655A3B3F5886C1/253705842/253705842.jpg@s_0,w_40
         * albumid : 25534678
         */

        public String albumname;
        public String weight;
        public String artistname;
        public String resource_type_ext;
        public String artistpic;
        public String albumid;
    }
}
