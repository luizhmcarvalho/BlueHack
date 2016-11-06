package travelfi.com.net.bluehack.helpers.bluemix.watson;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;

import java.util.Map;

import travelfi.com.net.bluehack.helpers.bluemix.BlueMixAuth;

/**
 * Created by luiz on 05/11/16.
 */

public class ConversationAuth extends BlueMixAuth {

    private String workspaceId = "34548c72-d49f-47ca-b7d5-70cbff856eaf";
    private static final ConversationAuth _sharedInstance = new ConversationAuth();
    private static ConversationService _service = new ConversationService("2016-09-20");

    public static ConversationAuth getInstance() {
        _sharedInstance.setUrl("https://gateway.watsonplatform.net/conversation/api");
        _sharedInstance.setUsername("5e26e737-07fe-4dd3-9be1-6941a914bd96");
        _sharedInstance.setPassword("tErn4zD5yNCa");
        _service.setEndPoint(_sharedInstance.getUrl());
        _service.setUsernameAndPassword(_sharedInstance.getUsername(), _sharedInstance.getPassword());
        return _sharedInstance;
    }


    public ConversationService getService() {
        return _service;
    }

    public MessageRequest SendMessage(final String message, Map<String, Object> context) {
        if (context == null)
            return new MessageRequest.Builder()
                    .inputText(message)
                    .build();
        else
            return new MessageRequest.Builder()
                    .inputText(message)
                    .context(context)
                    .build();
    }


    public String getWorkspaceId() {
        return workspaceId;
    }


}
