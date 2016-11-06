package travelfi.com.net.bluehack.activities;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.Map;

import io.fabric.sdk.android.Fabric;
import travelfi.com.net.bluehack.R;
import travelfi.com.net.bluehack.adapters.ChatArrayAdapter;
import travelfi.com.net.bluehack.helpers.bluemix.watson.ConversationAuth;
import travelfi.com.net.bluehack.models.ChatMessage;

public class ChatActivity extends AppCompatActivity {


    private static final String TAG = "ChatActivity";

    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private boolean side = false;
    private Map<String, Object> chatContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setTitle("CHAT");
        buttonSend = (Button) findViewById(R.id.send);

        listView = (ListView) findViewById(R.id.msgview);
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.right);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText) findViewById(R.id.msg);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage(false);
                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage(false);
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });


        sendChatMessage(true);
    }

    private boolean sendChatMessage(boolean initial) {
        if(!initial)
            chatArrayAdapter.add(new ChatMessage(side, chatText.getText().toString()));
        MessageRequest message = ConversationAuth.getInstance().SendMessage(chatText.getText().toString(), chatContext);
        ConversationAuth.getInstance().getService()
                .message(ConversationAuth.getInstance().getWorkspaceId(), message)
                .enqueue(new ServiceCallback<MessageResponse>() {
                    @Override
                    public void onResponse(MessageResponse response) {
                        chatContext = response.getContext();
                        ChatMessage chatm = new ChatMessage(true, response
                                .getOutput()
                                .get("text").toString());
                        chatArrayAdapter.add(chatm);
                        chatArrayAdapter.setNotifyOnChange(true);
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
        chatText.setText("");
        //side = !side;
        return true;
    }
}
