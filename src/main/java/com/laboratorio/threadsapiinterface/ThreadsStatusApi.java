package com.laboratorio.threadsapiinterface;

import com.laboratorio.threadsapiinterface.model.ThreadsPost;
import com.laboratorio.threadsapiinterface.model.ThreadsPostResponse;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 03/09/2024
 * @updated 03/09/2024
 */
public interface ThreadsStatusApi {
    ThreadsPost retrievePost(String id);
            
    ThreadsPostResponse postStatus(String text);
    ThreadsPostResponse postStatus(String text, String imagePath);
    
    ThreadsPostResponse publishStatus(String id);
}