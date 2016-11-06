package travelfi.com.net.bluehack.helpers.bluemix.watson;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifiedClass;

import travelfi.com.net.bluehack.helpers.bluemix.BlueMixAuth;

/**
 * Created by luiz on 05/11/16.
 */

public class NaturalLanguageClassifierAuth  extends BlueMixAuth {
    private static final NaturalLanguageClassifierAuth _sharedInstance = new NaturalLanguageClassifierAuth();
    private static  NaturalLanguageClassifier _service;

    public static NaturalLanguageClassifierAuth getInstance(){
        _sharedInstance.setUrl("https://gateway.watsonplatform.net/natural-language-classifier/api");
        _sharedInstance.setUsername("a7664602-29ee-4107-ba4a-8ba239671451");
        _sharedInstance.setPassword("OH8LzofSo6Ud");

        _service = new NaturalLanguageClassifier(_sharedInstance.getUsername(),_sharedInstance.getPassword());

        return _sharedInstance;
    }

    public void classify(String text) {
        if(text == null) return;
        _service.classify("e3ca6dx107-nic-4517", text).enqueue(new ServiceCallback<Classification>() {
            @Override
            public void onResponse(Classification response) {
                 for(ClassifiedClass classe :response.getClasses()){
                     classe.getName();
                 }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}
