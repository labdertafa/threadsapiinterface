package com.laboratorio.threadsapiinterface;

import com.laboratorio.threadsapiinterface.model.ThreadsStatus;
import com.laboratorio.threadsapiinterface.model.ThreadsPostResponse;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 03/09/2024
 * @updated 17/10/2024
 */
public interface ThreadsStatusApi {
    ThreadsStatus retrievePost(String id);
            
    ThreadsStatus postStatus(String text);
    ThreadsStatus postStatus(String text, String imagePath);
    
    ThreadsPostResponse publishStatus(String id);
}