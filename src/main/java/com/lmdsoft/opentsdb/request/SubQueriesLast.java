package com.lmdsoft.opentsdb.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shifeng on 2016/5/19.
 * MyProject
 */
public class SubQueriesLast {
    private String metric;
    private Map<String, String> tags = new HashMap<String, String>();
    //private Long timestamp;

    public SubQueriesLast addMetric(String metric) {
        this.metric = metric;
        return this;
    }
//    public SubQueriesLast addTimestamp(Long timestamp) {
//        this.timestamp = timestamp;
//        return this;
//    }

    /**
     * Tags are converted to filters in 2.2
     */
    @Deprecated
    public SubQueriesLast addTag(Map<String, String> tag) {
        this.tags.putAll(tag);
        return this;
    }

    /**
     * Tags are converted to filters in 2.2
     */
    @Deprecated
    public SubQueriesLast addTag(String tag, String value) {
        this.tags.put(tag, value);
        return this;
    }


    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }


    public Map<String, String> getTags() {
        return tags;
    }

    /**
     * Tags are converted to filters in 2.2
     */
    @Deprecated
    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

//    public Long getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(Long timestamp) {
//        this.timestamp = timestamp;
//    }
}
