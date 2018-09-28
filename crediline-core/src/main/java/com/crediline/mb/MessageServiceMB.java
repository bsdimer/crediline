package com.crediline.mb;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by dimer on 1/20/14.
 */
@Component("messageServiceMB")
@Scope("session")
public class MessageServiceMB extends CommonManagedBean implements Serializable {

    private String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
