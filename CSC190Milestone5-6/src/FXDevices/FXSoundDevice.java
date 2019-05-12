/*
 * Copyright (C) 2019 csc190
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package FXDevices;

import BridgePattern.ISoundDevice;
import java.io.File;

import java.util.Hashtable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * FX Implementation of ISoundDevice
 *
 * @author csc190
 */
public class FXSoundDevice implements ISoundDevice {

    // ---- DATA MEMBERS ----------------
    Hashtable<String, MediaPlayer> map= new Hashtable();

    // ---- OPERATIONS
    /**
     * Either retrieve it from hashtable or create a new one
     *
     * @param resourcePath - the relative path of .wav file
     * @return
     */
    protected MediaPlayer getMediaPlayer(String resourcePath) {
        if (!map.containsKey(resourcePath)) {
            Media sound = new Media(new File(resourcePath).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            map.put(resourcePath, mediaPlayer);
        }else{
            int kk = 0;
        }

        return map.get(resourcePath);
    }

    @Override
    public void playRepeated(String resourcePath) {
        MediaPlayer mp = getMediaPlayer(resourcePath);
        mp.setOnEndOfMedia(new Runnable() {
            public void run() {
                mp.seek(Duration.ZERO);
            }
        });
        mp.play();
    }

    @Override
    public void playOnce(String resourcePath) {
        MediaPlayer mp = getMediaPlayer(resourcePath);
        mp.seek(Duration.ZERO);
        mp.play();
    }

}
