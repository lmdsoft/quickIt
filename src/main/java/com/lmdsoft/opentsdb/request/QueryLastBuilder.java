package com.lmdsoft.opentsdb.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by shifeng on 2016/5/19.
 * MyProject
 */
public class QueryLastBuilder {
    private QueryLast queryLast = new QueryLast();
    private transient Gson mapper;

    private QueryLastBuilder() {
        GsonBuilder builder = new GsonBuilder();
        mapper = builder.create();
    }



    public static QueryLastBuilder getInstance() {
        return new QueryLastBuilder();
    }

    public QueryLast getQueryLast() {
        return queryLast;
    }

    public void setQueryLast(QueryLast queryLast) {
        this.queryLast = queryLast;
    }

    public Gson getMapper() {
        return mapper;
    }

    public void setMapper(Gson mapper) {
        this.mapper = mapper;
    }

    public String build() throws IOException {
        // verify that there is at least one tag for each metric
        checkState(queryLast.getQueries() != null, " must contain at least one subQuery.");
        return mapper.toJson(queryLast);
    }
}
