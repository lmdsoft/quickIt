package com.lmdsoft.opentsdb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lmdsoft.opentsdb.builder.MetricBuilder;
import com.lmdsoft.opentsdb.request.QueryBuilder;
import com.lmdsoft.opentsdb.request.QueryLastBuilder;
import com.lmdsoft.opentsdb.response.ErrorDetail;
import com.lmdsoft.opentsdb.response.Response;
import com.lmdsoft.opentsdb.response.SimpleHttpResponse;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * HTTP implementation of a client.
 */
public class HttpClientImpl implements HttpClient {


    private String serviceUrl;

    private Gson mapper;

    //private PoolingHttpClient httpClient = new PoolingHttpClient();
    private PoolingHttpClient httpClient = PoolingHttpClient.getInstance();
    public HttpClientImpl(String serviceUrl) {
        this.serviceUrl = serviceUrl;

        GsonBuilder builder = new GsonBuilder();
        mapper = builder.create();
    }

    @Override
    public Response pushMetrics(MetricBuilder builder) throws IOException {
        return pushMetrics(builder, ExpectResponse.DETAIL);

    }

    @Override
    public Response pushMetrics(MetricBuilder builder,
                                ExpectResponse expectResponse) throws IOException {
        checkNotNull(builder);

        // TODO 错误处理，比如IOException或者failed>0，写到队列或者文件后续重试。
        SimpleHttpResponse response = httpClient.doPost(buildUrl(serviceUrl, PUT_POST_API, expectResponse),builder.build());

        return getResponse(response);
    }

    public SimpleHttpResponse pushQueries(QueryBuilder builder) throws IOException {
        return pushQueries(builder, ExpectResponse.STATUS_CODE,"");

    }

    public SimpleHttpResponse pushQueries(QueryLastBuilder builder) throws IOException {
        return pushQueries(builder, ExpectResponse.STATUS_CODE,Client.QUERY_POST_LAST_API);

    }

    public SimpleHttpResponse pushQueries(QueryBuilder builder,
                                          ExpectResponse expectResponse,String queryType) throws IOException {
        checkNotNull(builder);

        // TODO 错误处理，比如IOException或者failed>0，写到队列或者文件后续重试。
        SimpleHttpResponse response = httpClient
                .doPost(buildUrl(serviceUrl, queryType, expectResponse),
                        builder.build());

        return response;
    }

    public SimpleHttpResponse pushQueries(QueryLastBuilder builder,
                                          ExpectResponse expectResponse,String queryType) throws IOException {
        checkNotNull(builder);

        // TODO 错误处理，比如IOException或者failed>0，写到队列或者文件后续重试。
        SimpleHttpResponse response = httpClient
            .doPost(buildUrl(serviceUrl, queryType, expectResponse),
                builder.build());

        return response;
    }

    private String buildUrl(String serviceUrl, String postApiEndPoint,
                            ExpectResponse expectResponse) {
        String url = serviceUrl + postApiEndPoint;

        switch (expectResponse) {
            case SUMMARY:
                url += "?summary";
                break;
            case DETAIL:
                url += "?details";
                break;
            case NONE:
                break;
        }
        return url;
    }

    private Response getResponse(SimpleHttpResponse httpResponse) {
        Response response = new Response(httpResponse.getStatusCode());
        String content = httpResponse.getContent();
        if (StringUtils.isNotEmpty(content)) {
            if (response.isSuccess()) {
                ErrorDetail errorDetail = mapper.fromJson(content,
                        ErrorDetail.class);
                response.setErrorDetail(errorDetail);
            } else {
                //logger.error("request failed!" + httpResponse);
            }
        }
        return response;
    }
}
