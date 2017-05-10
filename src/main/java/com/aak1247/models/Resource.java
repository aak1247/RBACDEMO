package com.aak1247.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author  aak12 on 2017/5/10.
 */
@Document(collection = "resources")
public class Resource {
    @Id
    private String resourceId;
    private Object resourceBody;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Object getResourceBody() {
        return resourceBody;
    }

    public void setResourceBody(Object resourceBody) {
        this.resourceBody = resourceBody;
    }
}
