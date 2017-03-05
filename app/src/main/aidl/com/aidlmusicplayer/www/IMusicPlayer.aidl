// IMusicPlayer.aidl
package com.aidlmusicplayer.www;

import com.aidlmusicplayer.www.IMusicPlayerListener;


// Declare any non-default types here with import statements

interface IMusicPlayer {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);


        void action(in int action ,in String datum);
        void registerListener(IMusicPlayerListener listener);
        void unregisterListener(IMusicPlayerListener listener);
        Message getCurrentSongInfo();
}
