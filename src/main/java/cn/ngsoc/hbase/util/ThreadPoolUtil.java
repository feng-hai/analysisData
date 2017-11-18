package cn.ngsoc.hbase.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 线程池工具类
 * Created by babylon on 2016/12/4.
 */
public class ThreadPoolUtil {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolUtil.class);

    private static ThreadPoolUtil threadPool;
    private ThreadPoolExecutor executor=null;
    //线程池的基础参数 实际使用可写入到配置文件�?
    private int corePoolSize = 10;          // 核心池的大小 运行线程的最大�?? 当线程池中的线程数目达到corePoolSize后，就会把多余的任务放到缓存队列当中�?
    private int maximumPoolSize = 15;  // 创建线程�?大�??
    private long keepAliveTime = 1;     // 线程没有执行任务�? 被保留的�?长时�? 超过这个时间就会被销�? 直到线程数等�? corePoolSize
    private long timeout = 10;             // 等待线程池任务执行结束超时时�?

    /**    参数keepAliveTime的时间单位，�?7种取值，在TimeUnit类中�?7种静态属性：
     TimeUnit.DAYS;               �?
     TimeUnit.HOURS;             小时
     TimeUnit.MINUTES;           分钟
     TimeUnit.SECONDS;           �?
     TimeUnit.MILLISECONDS;      毫秒
     TimeUnit.MICROSECONDS;      微妙
     TimeUnit.NANOSECONDS;       纳秒***/
    private TimeUnit unit= TimeUnit.SECONDS;

    /**
     *     用来储存等待中的任务的容�?
     *
     *     几种选择�?
     *    ArrayBlockingQueue;
     *    LinkedBlockingQueue;
     *    SynchronousQueue;
     *    区别太罗嗦请百度  http://blog.csdn.net/mn11201117/article/details/8671497
     */
    private LinkedBlockingQueue workQueue=new LinkedBlockingQueue<Runnable>();

    /**
     * 单例
     * @return
     */
    public static ThreadPoolUtil init(){
        if(threadPool==null)
            threadPool=new ThreadPoolUtil();
        return threadPool;
    }

    /**
     * 私有构�?�方�?
     */
    private ThreadPoolUtil(){
        //实现线程�?
        executor=new ThreadPoolExecutor(corePoolSize,maximumPoolSize, keepAliveTime, unit,
                workQueue);
        System.out.println("线程池初始化成功");
    }

    /**
     * 线程池获取方�?
     * @return
     */
    public ThreadPoolExecutor getExecutor() {return executor;}

    /**
     *  准备执行 抛入线程�?
     * @param t
     */
    public void execute(Thread t){
        executor.execute(t);
    }

    public void execute(Runnable t){ executor.execute(t);}

    public int getQueueSize(){
        return executor.getQueue().size();
    }

    /**
     * 异步提交返回 Future
     * Future.get()可获得返回结�?
     * @return
     */
    public Future<?> submit(Runnable t){return executor.submit(t);}

    /**
     * 异步提交返回 Future
     * Future.get()可获得返回结�?
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Future<?> submit(Callable t){return getExecutor().submit(t);}

    /**
     * �?毁线程池
     * */
    public void shutdown(){
        getExecutor().shutdown();
    }

    /**
     * 阻塞，直到线程池里所有任务结�?
     */
    public void awaitTermination() throws InterruptedException {
        logger.info("Thread pool ,awaitTermination started, please wait till all the jobs complete.");
        executor.awaitTermination(timeout, unit);
    }

}
