package com.aak1247.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author  aak12 on 2017/5/10.
 */
@Document(collection = "resources")
public class Resource {
    @Id
    private String id;
    private Object resourceBody;
    private String target;
    private String content;
    private String url;
    private boolean ajax;

    public Object getResourceBody() {
        return resourceBody;
    }

    public void setResourceBody(Object resourceBody) {
        this.resourceBody = resourceBody;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isAjax() {
        return ajax;
    }

    public void setAjax(boolean ajax) {
        this.ajax = ajax;
    }
}
