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
package BridgePattern;

/**
 * Represents the functions that should be provided by the platform related to music and sound effects play.
 * Only supports .wav files
 * @author csc190
 */
public interface ISoundDevice {
    
    /**
     * Play the given sound file repeated
     * @param resourcePath - relative path of the .wave file, e.g., "resources/sounds/backgroundMusic.wav"
     */
    public void playRepeated(String resourcePath);
    
    /**
     * Play the given sound file (.wav) for just once
     * @param resourcePath - relative path of the .wav file, e.g., "resources/sound/gunshot.wav"
     */
    public void playOnce(String resourcePath);
    
}
