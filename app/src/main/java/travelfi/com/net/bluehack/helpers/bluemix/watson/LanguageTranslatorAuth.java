package travelfi.com.net.bluehack.helpers.bluemix.watson;

/**
 * Created by luiz on 05/11/16.
 */
import com.ibm.watson.developer_cloud.language_translation.v2.LanguageTranslation;
import com.ibm.watson.developer_cloud.language_translation.v2.model.Language;

import travelfi.com.net.bluehack.helpers.bluemix.BlueMixAuth;


public class LanguageTranslatorAuth  extends BlueMixAuth {
    private static final LanguageTranslatorAuth _sharedInstance = new LanguageTranslatorAuth();
    private static LanguageTranslation _service = new LanguageTranslation();
    public static LanguageTranslatorAuth getInstance(){
        _sharedInstance.setUrl("https://gateway.watsonplatform.net/language-translator/api");
        _sharedInstance.setUsername("9df258ed-11d6-40f3-9d36-b26cb9e5ff9d");
        _sharedInstance.setPassword("DGkp4cjy0Ai0");
        _service.setEndPoint(_sharedInstance.getUrl());
        _service.setUsernameAndPassword(_sharedInstance.getUsername(),_sharedInstance.getPassword());
        return _sharedInstance;
    }

    public String Translate(String text, String oringLang, String destinationLang){
        Language OriginL = Language.valueOf(oringLang);
        Language DestinationL = Language.valueOf(destinationLang);
        return _service.translate(text, OriginL,DestinationL).toString();
    }

}
