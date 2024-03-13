package me.zhengjie.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * @author wxj
 * 2022-1-12
 */
public class SnowflakeIdWorker implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(SnowflakeIdWorker.class);
    //开始该类生成ID的时间截
    private final long startTime = 1527148601767L;

    //机器id所占的位数
    private long workerIdBits = 5L;

    //数据标识id所占的位数
    private long datacenterIdBits = 5L;

    //支持的最大机器id
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);

    //支持的最大数据标识id
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    //序列在id中占的位数
    private long sequenceBits = 12L;

    //机器id向左移的位数
    private long workerIdLeftShift = sequenceBits;

    //数据标识id向左移的位数
    private long datacenterIdLeftShift = workerIdBits + workerIdLeftShift;

    //时间截向左移的位置
    private long timestampLeftShift = datacenterIdBits + datacenterIdLeftShift;

    //生成序列的掩码
    private long sequenceMask = -1 ^ (-1 << sequenceBits);

    private long workerId;

    private long datacenterId;

    //同一个时间截内生成的序列数，初始值是0，从0开始
    private long sequence = 0L;

    //上次生成id的时间截
    private long lastTimestamp = -1L;

    public SnowflakeIdWorker(long workerId, long datacenterId){
        if(workerId < 0 || workerId > maxWorkerId){
            throw new IllegalArgumentException(
                    String.format("workerId[%d] is less than 0 or greater than maxWorkerId[%d].", workerId, maxWorkerId));
        }
        if(datacenterId < 0 || datacenterId > maxDatacenterId){
            throw new IllegalArgumentException(
                    String.format("datacenterId[%d] is less than 0 or greater than maxDatacenterId[%d].", datacenterId, maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    //生成id
    public synchronized long nextId(){
        long timestamp = timeGen();
        if(timestamp < lastTimestamp){
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //表示是同一时间截内生成的id
        if(timestamp == lastTimestamp){
            sequence = (sequence + 1) & sequenceMask;
            //说明当前时间生成的序列数已达到最大
            if(sequence == 0){
                //生成下一个毫秒级的序列
                timestamp = tilNextMillis();
                //序列从0开始
                sequence = 0L;
            }
        }else{
            //新的时间截，则序列从0开始
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - startTime) << timestampLeftShift) //时间截部分
                | (datacenterId << datacenterIdLeftShift) //数据标识id部分
                | (workerId << workerIdLeftShift) //机器id部分
                | sequence; //序列部分
    }

    protected long tilNextMillis(){
        long timestamp = timeGen();
        while(timestamp <= lastTimestamp){
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen(){
        return System.currentTimeMillis();
    }

    private volatile static SnowflakeIdWorker uniqueInstance;

    public static SnowflakeIdWorker getInstance() {
        long datacenterId = 0L;
        try{
            if(uniqueInstance == null) {
                datacenterId = Math.abs(InetAddress.getLocalHost().getHostName().hashCode()%32);
                synchronized(SnowflakeIdWorker.class) {
                    if(uniqueInstance == null)
                        uniqueInstance = new SnowflakeIdWorker(0,datacenterId);
                }
            }
        }catch (Exception e){
            if(uniqueInstance == null) {
                synchronized(SnowflakeIdWorker.class) {
                    if(uniqueInstance == null)
                        uniqueInstance = new SnowflakeIdWorker(0,datacenterId);
                }
            }
        }
        return uniqueInstance;
    }

}
