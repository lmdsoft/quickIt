package com.lmdsoft.opentsdb;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.lmdsoft.opentsdb.builder.MetricBuilder;
import com.lmdsoft.opentsdb.request.*;
import com.lmdsoft.opentsdb.response.Response;
import com.lmdsoft.opentsdb.response.SimpleHttpResponse;
import com.lmdsoft.opentsdb.util.Aggregator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import com.lmdsoft.opentsdb.HttpClient;
/**
 * Created by Administrator on 2018/12/3.
 */
public class OpentsdbClient {

    private static Logger log = LoggerFactory.getLogger(OpentsdbClient.class);

    private HttpClient httpClient;

    public OpentsdbClient() {
    }

    public OpentsdbClient(String opentsdbUrl) {
        this.httpClient = new HttpClientImpl(opentsdbUrl);
    }


 public static  void main(String[] args){

        OpentsdbClient opentsdbClient=new OpentsdbClient("http://192.168.0.80:4242/");

        Long startTime=System.currentTimeMillis();


     Map putMap = new HashMap();
     putMap.put("hostId","188");
     putMap.put("type","memory");
     putMap.put("timestamp",1544432107);
     putMap.put("memoryTotal", 1024);
     putMap.put("memoryUsed", 30);
     putMap.put("memoryUsedPercent", 0.9);
     putMap.put("memoryFree", 523);
     opentsdbClient.putData("windows.memory",1544432107L,1L, putMap);

     Map tagMap = new HashMap();
     tagMap.put("hostId", 188);
     tagMap.put("type","memory");
//tagMap.put("timestamp",timestamp);
//String lookup = opentsdbClient.searchLookup("windows.memory",tagMap);
//logger.info("opentsdb获取tsuid的信息："+lookup);
     String windowsS = opentsdbClient.getNewData("windows.memory",tagMap,1544432107L);
     System.out.println("memory获取的信息："+windowsS);
//String windowsLast= opentsdbClient.getLastData("windows.memory",tagMap,timestamp);
//logger.info("memory获取的信息："+windowsLast);

 }

    /**
     * 写入数据
     *
     * @param metric    指标
     * @param timestamp 时间点
     * @param value
     * @param tagMap
     * @return
     * @throws Exception
     */
    public boolean putData(String metric, Date timestamp, Integer value, Map<String, String> tagMap){
        long timsSecs = timestamp.getTime() /  1000;
        return this.putData(metric, timsSecs, value, tagMap);
    }

    /**
     * 写入数据
     *
     * @param metric    指标
     * @param timestamp 时间点
     * @param value
     * @param tagMap
     * @return
     * @throws Exception
     */
    public boolean putData(String metric, Date timestamp, Long value, Map<String, String> tagMap){
        long timsSecs = timestamp.getTime() /  1000;
        return this.putData(metric, timsSecs, value, tagMap);
    }
    /**
     * 写入数据
     *
     * @param metric    指标
     * @param timestamp 时间点
     * @param value
     * @param tagMap
     * @return
     * @throws Exception
     */
    public boolean putData(String metric, Date timestamp, String value, Map<String, String> tagMap){
        long timsSecs = timestamp.getTime() /  1000;
        return this.putData(metric, timsSecs, value, tagMap);
    }

    /**
     * 写入数据
     * @param metric 指标
     * @param timestamp 转化为秒的时间点
     * @param value
     * @param tagMap
     * @return
     * @throws Exception
     */
    public  boolean putData(String metric, long timestamp, Object value, Map tagMap){
        MetricBuilder builder = MetricBuilder.getInstance();
        builder.addMetric(metric).setDataPoint(timestamp, value).addTags(tagMap);
        try {
            log.debug("write quest：{}", builder.build());
            Response response = httpClient.pushMetrics(builder, ExpectResponse.DETAIL);
            log.debug("response.statusCode: {}", response.getStatusCode());
            return response.isSuccess();
        } catch (Exception e) {
            log.error("put data to opentsdb error: ", e);
            e.getMessage();
        }
        return false;
    }
    /**
     * 写入数据
     * @param metric 指标
     * @param timestamp 转化为秒的时间点
     * @param value
     * @param tagMap
     * @return
     * @throws Exception
     */
    public boolean putData(String metric, long timestamp, String value, Map tagMap){
        MetricBuilder builder = MetricBuilder.getInstance();
        builder.addMetric(metric).setDataPoint(timestamp, value).addTags(tagMap);
        try {
            log.debug("write quest：{}", builder.build());
            Response response = httpClient.pushMetrics(builder, ExpectResponse.DETAIL);
            log.debug("response.statusCode: {}", response.getStatusCode());
            return response.isSuccess();
        } catch (Exception e) {
            log.error("put data to opentsdb error: ", e);
            e.getMessage();
        }
        return false;
    }


   /**
    * @param metric 要查询的指标
     * @param tagMap 查询的条件
     */
    public String getNewData(String metric, Map tagMap,Long timestamp){
        QueryBuilder queryBuilder = QueryBuilder.getInstance();
        Query query = queryBuilder.getQuery();
        query.setStart(timestamp-1);
        query.setEnd(timestamp);
        List sqList = new ArrayList();
        SubQueries sq = new SubQueries();
        sq.addMetric(metric);
        sq.addTag(tagMap);
        sq.addAggregator(Aggregator.none.name());
        // sq.setDownsample( "1s-sum");
        sqList.add(sq);
        query.setQueries(sqList);
        query.setMsResolution(true);
        query.setGlobalAnnotations(true);
        //query.setShowSummary(true);
        query.getShowQuery();
        try {
            log.debug("query request：{}", queryBuilder.build()); //这行起到校验作用
            SimpleHttpResponse spHttpResponse = httpClient.pushQueries(queryBuilder, ExpectResponse.NONE, Client.QUERY_POST_API);
            log.debug("response.content: {}", spHttpResponse.getContent());
            if (spHttpResponse.isSuccess()) {
                return spHttpResponse.getContent();
            }
            return null;
        } catch (IOException e) {
            log.error("get data from opentsdb error: ", e);
        }
        return null;
    }

    /**
     * @param metric 要查询的指标
     * @param aggregator 查询的聚合类型, 如: OpentsdbClient.AGGREGATOR_AVG, OpentsdbClient.AGGREGATOR_SUM
     * @param tagMap 查询的条件
     * @param downsample 采样的时间粒度, 如: 1s,2m,1h,1d,2d
     * @param startTime 查询开始时间,时间格式为yyyy/MM/dd HH:mm:ss
     * @param endTime 查询结束时间,时间格式为yyyy/MM/dd HH:mm:ss
     */
    public String getData(String metric, Map tagMap, String aggregator, String downsample, String startTime, String endTime){
        QueryBuilder queryBuilder = QueryBuilder.getInstance();
        Query query = queryBuilder.getQuery();
        if(StringUtils.isNotEmpty(startTime)){
            query.setStart(DateTimeUtil.parse(startTime, "yyyy/MM/dd HH:mm:ss").getTime() / 1000);
        }
        if(StringUtils.isNotEmpty(endTime)) {
            query.setEnd(DateTimeUtil.parse(endTime, "yyyy/MM/dd HH:mm:ss").getTime() / 1000);
        }
        List sqList = new ArrayList();
        SubQueries sq = new SubQueries();
        sq.addMetric(metric);
        sq.addTag(tagMap);
        sq.addAggregator(aggregator);
        // sq.setDownsample(downsample + "-" + aggregator);
        sqList.add(sq);
        query.setQueries(sqList);
        query.setMsResolution(true);
        query.getShowQuery();
        try {
            log.debug("query request：{}", queryBuilder.build()); //这行起到校验作用
            SimpleHttpResponse spHttpResponse = httpClient.pushQueries(queryBuilder, ExpectResponse.NONE, Client.QUERY_POST_API);
            log.debug("response.content: {}", spHttpResponse.getContent());
            if (spHttpResponse.isSuccess()) {
                return spHttpResponse.getContent();
            }
            return null;
        } catch (IOException e) {
            log.error("get data from opentsdb error: ", e);
        }
        return null;
    }

    /**
     * @param metric 要查询的指标
     * @param tagMap 查询的条件
     */
    public String searchLookup(String metric, Map tagMap){
        QueryLastBuilder queryBuilder = QueryLastBuilder.getInstance();
        QueryLast query = queryBuilder.getQueryLast();
        List sqList = new ArrayList();
        SubQueriesLast sq = new SubQueriesLast();
        sq.addMetric(metric);
        sq.addTag(tagMap);
        sqList.add(sq);
        query.setQueries(sqList);
        query.setResolveNames(true);
        query.setBackScan(1);
        try {
            log.debug("query request：{}", queryBuilder.build()); //这行起到校验作用
            SimpleHttpResponse spHttpResponse = httpClient.pushQueries(queryBuilder, ExpectResponse.NONE, Client.QUERY_LOOKUP_API);
            log.debug("response.content: {}", spHttpResponse.getContent());
            if (spHttpResponse.isSuccess()) {
                return spHttpResponse.getContent();
            }
            return null;
        } catch (IOException e) {
            log.error("get data from opentsdb error: ", e);
        }
        return null;
    }

    /**
     * @param metric 要查询的指标
     * @param tagMap 查询的条件
     */
    public String getLastData(String metric, Map tagMap,Long timestamp){
        QueryLastBuilder queryBuilder = QueryLastBuilder.getInstance();
        QueryLast query = queryBuilder.getQueryLast();
        List sqList = new ArrayList();
        SubQueriesLast sq = new SubQueriesLast();
        sq.addMetric(metric);
        sq.addTag(tagMap);
        //sq.setTimestamp(timestamp);
        sqList.add(sq);
        query.setQueries(sqList);
        query.setResolveNames(true);
        query.setBackScan(0);
        try {
            log.debug("last query request：{}", queryBuilder.build()); //这行起到校验作用
            SimpleHttpResponse spHttpResponse = httpClient.pushQueries(queryBuilder, ExpectResponse.NONE, Client.QUERY_POST_LAST_API);
            log.debug("last response.content: {}", spHttpResponse.getContent());
            if (spHttpResponse.isSuccess()) {
                return spHttpResponse.getContent();
            }
            return null;
        } catch (IOException e) {
            log.error("get data from opentsdb error: ", e);
        }
        return null;
    }


    /**
     * 查询数据， 返回的数据为json格式，结构为：
     * @param metric      要查询的指标
     * @param tagk        tagk
     * @param tagvFtype   tagv分过滤规则
     * @param tagvFilter  tagv的匹配规则
     * @param aggregator  查询的聚合类型，如： OpentsdbClient.AGGREGATOR_AVG, OpentsdbClient.AGGREGATOR_SUM
     * @param downsample  采样的时间粒度，如： 1s, 2m, 1h, 1d, 2d
     * @param startTime   查询开始时间，时间格式为 yyyy-MM-dd HH:mm:ss
     * @param endTime     查询结束时间，时间格式为 yyyy-MM-dd HH:mm:ss
     * @return
     * @throws IOException
     */
    public String getData (String metric, String tagk, String tagvFtype, String tagvFilter, String aggregator, String downsample, Date startTime, Date endTime) throws IOException {
        QueryBuilder queryBuilder = QueryBuilder.getInstance();
        Query query = queryBuilder.getQuery();
        query.setStart(DateTimeUtil.format(startTime).getTime() / 1000);
        query.setEnd(DateTimeUtil.format(endTime).getTime() / 1000);
        List<SubQueries> sqList = new ArrayList<>();
        SubQueries sq = new SubQueries();
        sq.setMetric(metric);
        sq.setAggregator(aggregator);
        List<Filter> filters = new ArrayList<>();
        Filter filter = new Filter();
        filter.setTagk(tagk);
        filter.setType(tagvFtype);
        filter.setFilter(tagvFilter);
        filter.setGroupBy(Boolean.TRUE);
        filters.add(filter);
        sq.setFilters(filters);
        sq.setDownsample(downsample + "-" + aggregator);
        sqList.add(sq);
        query.setQueries(sqList);
        try{
            log.debug("query rqeust: {}", queryBuilder.build()); // 查询校验
            SimpleHttpResponse spHttpResponse = httpClient.pushQueries(queryBuilder, ExpectResponse.DETAIL,Client.QUERY_POST_API);
            log.debug("response.content: {}", spHttpResponse.getContent());
            if (spHttpResponse.isSuccess()) {
                return spHttpResponse.getContent();
            }
            return null;
        } catch (IOException ioe) {
            log.error("get data from opentsdb error: ", ioe);
            throw ioe;
        }
    }

    /**
     * 查询数据，返回的数据为json格式
     * @param metric         要查询的指标
     * @param filter         查询过滤的条件，原来使用的tags在v2.2后已不适用
     *                       filter.setType(): 设置过滤类型, 如: wildcard, regexp
     *                       filter.setTagk(): 设置tag
     *                       filter.setFilter(): 根据type设置tagv的过滤表达式, 如: hqdApp|hqdWechat
     *                       filter.setGroupBy():设置成true, 不设置或设置成false会导致读超时
     * @param aggregator     查询的聚合类型， 如： OpentsdbClient.AGGREGATOR_AVG, OpentsdbClient.AGGREGATOR_SUM
     * @param downsample     采样的时间粒度， 如： 1s, 2m, 1h, 1d, 2d
     * @param startTime      查询开始时间，时间格式为：yyyy-MM-dd HH:mm:ss
     * @param endTime        查询结束时间，时间格式为: yyyy-MM-dd HH:mm:ss
     * @return
     * @throws IOException
     */
    public String getData (String metric, Filter filter, String aggregator, String downsample, Date startTime, Date endTime) throws IOException{
        QueryBuilder queryBuilder = QueryBuilder.getInstance();
        Query query = queryBuilder.getQuery();
        query.setStart(DateTimeUtil.format(startTime).getTime() / 1000);
        query.setEnd(DateTimeUtil.format(endTime).getTime() / 1000);
        List<SubQueries> sqList = new ArrayList<>();
        SubQueries sq = new SubQueries();
        sq.addMetric(metric);
        sq.addAggregator(aggregator);

        List<Filter> filters = new ArrayList<>();
        filters.add(filter);
        sq.setFilters(filters);

        sq.setDownsample(downsample + "-" + aggregator);
        sqList.add(sq);
        query.setQueries(sqList);
        try{
            log.debug("query request: {}", queryBuilder.build());
            SimpleHttpResponse spHttpResponse =httpClient.pushQueries(queryBuilder, ExpectResponse.DETAIL,Client.QUERY_POST_API);
            log.debug("response.content: {}", spHttpResponse.getContent());
            if (spHttpResponse.isSuccess()){
                return spHttpResponse.getContent();
            }
            return null;
        } catch (IOException e){
            log.error("get data from opentsdb error: ", e);
            throw e;
        }
    }

    /**
     * 查询数据，返回tags与时序值的映射: Map>
     * @param metric 要查询的指标
     * @param aggregator 查询的聚合类型, 如: OpentsdbClient.AGGREGATOR_AVG, OpentsdbClient.AGGREGATOR_SUM
     * @param tagMap 查询的条件
     * @param downsample 采样的时间粒度, 如: 1s,2m,1h,1d,2d
     * @param startTime 查询开始时间, 时间格式为yyyy/MM/dd HH:mm:ss
     * @param endTime 查询结束时间, 时间格式为yyyy/MM/dd HH:mm:ss
     * @param retTimeFmt 返回的结果集中，时间点的格式, 如：yyyy/MM/dd HH:mm:ss 或 yyyyMMddHH 等
     * @return Map>
     */
    public Map getData(String metric, Map tagMap, String aggregator, String downsample, String startTime, String endTime, String retTimeFmt) throws IOException {
        String resContent = this.getData(metric, tagMap, aggregator, downsample, startTime, endTime);
        return this.convertContentToMap(resContent, retTimeFmt);
    }

    public Map convertContentToMap(String resContent, String retTimeFmt) {
        Map tagsValuesMap = new HashMap();
        if (resContent == null || "".equals(resContent.trim())) {
            return tagsValuesMap;
        }
        JSONArray array = (JSONArray) JSONObject.parse(resContent);
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                JSONObject tags = (JSONObject) obj.get("tags");
                JSONObject dps = (JSONObject) obj.get("dps"); //timeValueMap.putAll(dps); Map timeValueMap = new HashMap(); for (Iterator it = dps.keySet().iterator(); it.hasNext(); ) { String timstamp = it.next(); Date datetime = new Date(Long.parseLong(timstamp)*1000); timeValueMap.put(DateTimeUtil.format(datetime, retTimeFmt), dps.get(timstamp)); } tagsValuesMap.put(tags.toString(), timeValueMap); } } return tagsValuesMap; }
            }
        }
        return tagsValuesMap;
    }

}
