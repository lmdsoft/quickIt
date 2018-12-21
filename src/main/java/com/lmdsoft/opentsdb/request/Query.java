package com.lmdsoft.opentsdb.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by shifeng on 2016/5/19.
 * MyProject
 */
public class Query {
    private Object start;
    private Object end;
    private List<SubQueries> queries = new ArrayList<SubQueries>();
    private Boolean noAnnotations = false;
    private Boolean globalAnnotations = false;
    private Boolean msResolution = false;
    private Boolean showTSUIDs = false;
    private Boolean showSummary = false;
    private Boolean showQuery = false;
    private Boolean delete = false;

    public Query() {
    }

    public Query(long start) {
        this.start = start;
    }

    public Query addSubQuery(SubQueries subQueries) {
        this.queries.add(subQueries);
        return this;
    }

    public Query addStart(long start) {
        this.start = start;
        return this;
    }

    public Query addEnd(long end) {
        this.end = end;
        return this;
    }

    public Object getStart() {
        return start;
    }

    public void setStart(Object start) {
        this.start = start;
    }

    public Object getEnd() {
        return end;
    }

    public void setEnd(Object end) {
        this.end = end;
    }

    public List<SubQueries> getQueries() {
        return queries;
    }

    public void setQueries(List<SubQueries> queries) {
        this.queries = queries;
    }

    public Boolean getNoAnnotations() {
        return noAnnotations;
    }

    public void setNoAnnotations(Boolean noAnnotations) {
        this.noAnnotations = noAnnotations;
    }

    public Boolean getGlobalAnnotations() {
        return globalAnnotations;
    }

    public void setGlobalAnnotations(Boolean globalAnnotations) {
        this.globalAnnotations = globalAnnotations;
    }

    public Boolean getMsResolution() {
        return msResolution;
    }

    public void setMsResolution(Boolean msResolution) {
        this.msResolution = msResolution;
    }

    public Boolean getShowTSUIDs() {
        return showTSUIDs;
    }

    public void setShowTSUIDs(Boolean showTSUIDs) {
        this.showTSUIDs = showTSUIDs;
    }

    public Boolean getShowSummary() {
        return showSummary;
    }

    public void setShowSummary(Boolean showSummary) {
        this.showSummary = showSummary;
    }

    public Boolean getShowQuery() {
        return showQuery;
    }

    public void setShowQuery(Boolean showQuery) {
        this.showQuery = showQuery;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
}
