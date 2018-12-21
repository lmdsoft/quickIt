package com.lmdsoft.opentsdb;

import com.lmdsoft.opentsdb.builder.MetricBuilder;
import com.lmdsoft.opentsdb.request.QueryBuilder;
import com.lmdsoft.opentsdb.request.QueryLastBuilder;
import com.lmdsoft.opentsdb.response.Response;
import com.lmdsoft.opentsdb.response.SimpleHttpResponse;

import java.io.IOException;

public interface HttpClient extends Client {

	public Response pushMetrics(MetricBuilder builder,
                                ExpectResponse exceptResponse) throws IOException;

	public SimpleHttpResponse pushQueries(QueryBuilder builder,
                                          ExpectResponse exceptResponse,String queryType) throws IOException;
    public SimpleHttpResponse pushQueries(QueryLastBuilder builder,
                                          ExpectResponse exceptResponse, String queryType) throws IOException;

}
