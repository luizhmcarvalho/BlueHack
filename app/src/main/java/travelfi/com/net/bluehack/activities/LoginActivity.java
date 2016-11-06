package travelfi.com.net.bluehack.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.internal.TwitterApi;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetui.TweetUtils;

import java.util.Arrays;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import travelfi.com.net.bluehack.R;
import travelfi.com.net.bluehack.helpers.bluemix.watson.NaturalLanguageClassifierAuth;
import travelfi.com.net.bluehack.models.UserProfile;

public class LoginActivity extends AppCompatActivity {

    private TwitterLoginButton loginButton;
    private TwitterAuthConfig authConfig;
    private String twittes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authConfig = new TwitterAuthConfig("rztq1DF1iLJSuKLsehBPY6cBq", "UVffLV4HtSBfWmnIQihUuWW57M4gMzEpREutDF3DVCSEjTmn4F");
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                login(result);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

    }

    public void login(Result<TwitterSession> result) {
        TwitterSession session = result.data;
        final List<Long> tweetIds = Arrays.asList(result.data.getUserId());
        TweetUtils.loadTweets(tweetIds, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {

                for (Tweet tweet : result.data) {
                    twittes += tweet.text;
                }
                NaturalLanguageClassifierAuth.getInstance().classify(twittes);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

        Call<User>userCallback=Twitter.getApiClient(session)
                .getAccountService()
                .verifyCredentials(true,false);
        userCallback.enqueue(new Callback<User>(){
            @Override
            public void success(Result<User>result){
                SaveUser(result.data);
            }

            @Override
            public void failure(TwitterException exception){

            }
        });

    }


    private void SaveUser(User session) {
        UserProfile usrProfile = UserProfile.getInstance();

        usrProfile.setId(session.getId());
        usrProfile.setUserName(session.name);
        usrProfile.setName(session.screenName);
        Intent i = new Intent(LoginActivity.this, ProfileActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
