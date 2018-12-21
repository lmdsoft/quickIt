package com.lmdsoft.opentsdb.request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shifeng on 2016/5/19.
 * MyProject
 */
public class QueryLast {
    private List<SubQueriesLast> queries = new ArrayList<SubQueriesLast>();
    private Boolean resolveNames;
    private Integer backScan;
    public QueryLast() {
    }

    public QueryLast addSubQuery(SubQueriesLast subQueries) {
        this.queries.add(subQueries);
        return this;
    }
    public QueryLast addSubQuery(Boolean resolveNames) {
        this.resolveNames = resolveNames;
        return this;
    }
    public QueryLast addSubQuery(Integer backScan) {
        this.backScan = backScan;
        return this;
    }

    public Boolean getResolveNames() {
        return resolveNames;
    }

    public void setResolveNames(Boolean resolveNames) {
        this.resolveNames = resolveNames;
    }

    public Integer getBackScan() {
        return backScan;
    }

    public void setBackScan(Integer backScan) {
        this.backScan = backScan;
    }

    public List<SubQueriesLast> getQueries() {
        return queries;
    }

    public void setQueries(List<SubQueriesLast> queries) {
        this.queries = queries;
    }
}
