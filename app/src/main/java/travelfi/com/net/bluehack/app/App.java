package travelfi.com.net.bluehack.app;
/**
 * Copyright (C) 2014 Twitter Inc and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

        import android.app.Application;
        import android.content.SharedPreferences;
        import android.graphics.Typeface;
        import android.os.Environment;
        import android.preference.PreferenceManager;

        import com.twitter.sdk.android.Twitter;
        import com.twitter.sdk.android.core.BuildConfig;
        import com.twitter.sdk.android.core.TwitterAuthConfig;

        import java.io.File;

        import io.fabric.sdk.android.Fabric;

/**
 * This class represents the Application and extends Application it is used to initiate the
 * application.
 */

public class App extends Application {

    public final static String CRASHLYTICS_KEY_CRASHES = "are_crashes_enabled";
    public final static String POEM_PIC_DIR = "cannonball";

    private static App singleton;
    private TwitterAuthConfig authConfig;
    private Typeface avenirFont;

    public static App getInstance() {
        return singleton;
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        final String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        final String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getPoemFile(String fileName) {
        // Get the directory for the user's public pictures directory.
        final File picsDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        final File poemPicsDir = new File(picsDir, POEM_PIC_DIR);
        poemPicsDir.mkdirs();
        return new File(poemPicsDir, fileName);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;


    }

}