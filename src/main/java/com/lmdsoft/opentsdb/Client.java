package com.lmdsoft.opentsdb;


import com.lmdsoft.opentsdb.builder.MetricBuilder;
import com.lmdsoft.opentsdb.request.QueryBuilder;
import com.lmdsoft.opentsdb.request.QueryLastBuilder;
import com.lmdsoft.opentsdb.response.Response;
import com.lmdsoft.opentsdb.response.SimpleHttpResponse;

import java.io.IOException;


public interface Client {

	String PUT_POST_API = "/api/put";

    String QUERY_POST_API = "/api/query";

    String QUERY_POST_LAST_API = "/api/query/last";

    String QUERY_LOOKUP_API = "/api/search/lookup";

	/**
	 * Sends metrics from the builder to the KairosDB server.
	 *
	 * @param builder
	 *            metrics builder
	 * @return response from the server
	 * @throws IOException
	 *             problem occurred sending to the server
	 */
	Response pushMetrics(MetricBuilder builder) throws IOException;

	SimpleHttpResponse pushQueries(QueryBuilder builder) throws IOException;

    SimpleHttpResponse pushQueries(QueryLastBuilder builder) throws IOException;

    SimpleHttpResponse pushQueries(QueryBuilder builder, ExpectResponse expectResponse, String queryType) throws IOException;

    SimpleHttpResponse pushQueries(QueryLastBuilder builder, ExpectResponse expectResponse, String queryType) throws IOException;

}
