package travelfi.com.net.bluehack.helpers.bluemix.cloudant;


import android.content.Context;

import com.cloudant.http.interceptors.CookieInterceptor;
import com.cloudant.sync.datastore.Datastore;
import com.cloudant.sync.datastore.DatastoreException;
import com.cloudant.sync.datastore.DatastoreManager;
import com.cloudant.sync.datastore.DatastoreNotCreatedException;
import com.cloudant.sync.datastore.DocumentBodyFactory;
import com.cloudant.sync.datastore.DocumentException;
import com.cloudant.sync.datastore.DocumentRevision;
import com.cloudant.sync.replication.Replicator;
import com.cloudant.sync.replication.ReplicatorBuilder;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import travelfi.com.net.bluehack.helpers.bluemix.BlueMixAuth;

/**
 * Created by luiz on 05/11/16.
 */

public class CloudantAuth extends BlueMixAuth {

    private static CloudantAuth _sharedInstance = new CloudantAuth();
    private static Replicator replicator;
    private Context context;
    private Datastore ds;
    private DocumentRevision revision = new DocumentRevision();
    private static DatastoreManager manager;

    public static CloudantAuth getInstance(Context context) {
        File path = context.getDir("datastores", Context.MODE_PRIVATE);
        manager = new DatastoreManager(path.getAbsolutePath());
        CookieInterceptor interceptor = new CookieInterceptor("56f69d71-2701-4d43-9124-21e652f15505-bluemix","58a7157b6ee85d9af58c5b482008c19f64b5db5b9de0faa28adb7c5fb127775d");

        try {
            _sharedInstance.ds = manager.openDatastore("bluetravel");
        } catch (DatastoreNotCreatedException e) {
            e.printStackTrace();
        }
        try {
            replicator = ReplicatorBuilder.push()
                    .to(new URI("https://56f69d71-2701-4d43-9124-21e652f15505-bluemix:58a7157b6ee85d9af58c5b482008c19f64b5db5b9de0faa28adb7c5fb127775d@56f69d71-2701-4d43-9124-21e652f15505-bluemix.cloudant.com/bluetravel/"))
                    .from(_sharedInstance.ds)
                    .addRequestInterceptors(interceptor)
                    .addResponseInterceptors(interceptor)
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        replicator.start();
        return _sharedInstance;
    }

    public DocumentRevision saveObject(String key, Object object) {
        try {

            Map<String, Object> body = new HashMap<String, Object>();
            body.put(key, object);
            _sharedInstance.revision.setBody(DocumentBodyFactory.create(body));
            return  ds.createDocumentFromRevision(_sharedInstance.revision);
        } catch (DocumentException documentException) {

            // this will be thrown in case of errors performing CRUD operations on
            // documents
            System.err.println("Problem accessing datastore: " + documentException);
        }
        return null;
    }

    public void readObject( DocumentRevision saved ){
        try {
            // Read a document
            Datastore ds = manager.openDatastore("bluehack_db");
            DocumentRevision aRevision = ds.getDocument(saved.getId());
        } catch (DatastoreException datastoreException) {

            // this will be thrown if we don't have permissions to write to the
            // datastore path
            System.err.println("Problem opening datastore: "+datastoreException);
        } catch (DocumentException documentException) {

            // this will be thrown in case of errors performing CRUD operations on
            // documents
            System.err.println("Problem accessing datastore: "+documentException);
        }
    }
}
