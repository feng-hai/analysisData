package cn.ngsoc.hbase.util;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: HBase表数据分页模型类�?<br>
 * 利用此类可管理多个HBaseQualifierModel对象�?
 * @author Jason Chen
 * @version 1.0
 */
public class HBasePageModel implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(HBasePageModel.class);

    private static final long serialVersionUID = 330410716100946538L;
    private int pageSize = 100;
    private int pageIndex = 0;
    private int prevPageIndex = 1;
    private int nextPageIndex = 1;
    private int pageCount = 0;
    private int pageFirstRowIndex = 1;
    private byte[] pageStartRowKey = null;
    private byte[] pageEndRowKey = null;
    private boolean hasNextPage = true;
    private int queryTotalCount = 0;
    private long startTime = System.currentTimeMillis();
    private long endTime = System.currentTimeMillis();
    private List<Result> resultList = new ArrayList<Result>();
    public HBasePageModel(int pageSize) {
        this.pageSize = pageSize;
    }
    /**
     * 获取分页记录数量
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }
    /**
     * 设置分页记录数量
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    /**
     * 获取当前页序�?
     * @return
     */
    public int getPageIndex() {
        return pageIndex;
    }
    /**
     * 设置当前页序�?
     * @param pageIndex
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
    /**
     * 获取分页总数
     * @return
     */
    public int getPageCount() {
        return pageCount;
    }
    /**
     * 设置分页总数
     * @param pageCount
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    /**
     * 获取每页的第�?行序�?
     * @return
     */
    public int getPageFirstRowIndex() {
        this.pageFirstRowIndex = (this.getPageIndex() - 1) * this.getPageSize() + 1;
        return pageFirstRowIndex;
    }
    /**
     * 获取每页起始行键
     * @return
     */
    public byte[] getPageStartRowKey() {
        return pageStartRowKey;
    }
    /**
     * 设置每页起始行键
     * @param pageStartRowKey
     */
    public void setPageStartRowKey(byte[] pageStartRowKey) {
        this.pageStartRowKey = pageStartRowKey;
    }
    /**
     * 获取每页结束行键
     * @return
     */
    public byte[] getPageEndRowKey() {
        return pageEndRowKey;
    }
    /**
     * 设置每页结束行键
     */
    public void setPageEndRowKey(byte[] pageEndRowKey) {
        this.pageEndRowKey = pageEndRowKey;
    }
    /**
     * 获取上一页序�?
     * @return
     */
    public int getPrevPageIndex() {
        if(this.getPageIndex() > 1) {
            this.prevPageIndex = this.getPageIndex() - 1;
        } else {
            this.prevPageIndex = 1;
        }
        return prevPageIndex;
    }
    /**
     * 获取下一页序�?
     * @return
     */
    public int getNextPageIndex() {
        this.nextPageIndex = this.getPageIndex() + 1;
        return nextPageIndex;
    }
    /**
     * 获取是否有下�?�?
     * @return
     */
    public boolean isHasNextPage() {
//这个判断是不严谨的，因为很有可能剩余的数据刚好够�?页�??
        if(this.getResultList().size() == this.getPageSize()) {
            this.hasNextPage = true;
        } else {
            this.hasNextPage = false;
        }
        return hasNextPage;
    }
    /**
     * 获取已检索�?�记录数
     */
    public int getQueryTotalCount() {
        return queryTotalCount;
    }
    /**
     * 获取已检索�?�记录数
     * @param queryTotalCount
     */
    public void setQueryTotalCount(int queryTotalCount) {
        this.queryTotalCount = queryTotalCount;
    }
    /**
     * 初始化起始时间（毫秒�?
     */
    public void initStartTime() {
        this.startTime = System.currentTimeMillis();
    }
    /**
     * 初始化截止时间（毫秒�?
     */
    public void initEndTime() {
        this.endTime = System.currentTimeMillis();
    }
    /**
     * 获取毫秒格式的�?�时信息
     * @return
     */
    public String getTimeIntervalByMilli() {
        return String.valueOf(this.endTime - this.startTime) + "毫秒";
    }
    /**
     * 获取秒格式的耗时信息
     * @return
     */
    public String getTimeIntervalBySecond() {
        double interval = (this.endTime - this.startTime)/1000.0;
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(interval) + "�?";
    }
    /**
     * 打印时间信息
     */
    public void printTimeInfo() {
        logger.info("起始时间�?" + this.startTime);
        logger.info("截止时间�?" + this.endTime);
        logger.info("耗费时间�?" + this.getTimeIntervalBySecond());
    }
    /**
     * 获取HBase�?索结果集�?
     * @return
     */
    public List<Result> getResultList() {
        return resultList;
    }
    /**
     * 设置HBase�?索结果集�?
     * @param resultList
     */
    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }
}