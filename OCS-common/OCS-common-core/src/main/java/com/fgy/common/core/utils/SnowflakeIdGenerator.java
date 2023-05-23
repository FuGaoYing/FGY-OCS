package com.fgy.common.core.utils;

/**
 * @author fgy
 * description
 * date 2023/5/23 17:08
 */
public class SnowflakeIdGenerator {
    private static volatile SnowflakeIdGenerator INSTANCE;

    /**
     *  起始时间戳，用于对时间戳进行缩放，避免在未来产生重复 ID
     */
    private final long twepoch = 1288834974657L;
    /**
     * 机器 ID 所占的位数
     */
    private final long workerIdBits = 5L;
    /**
     *  数据中心 ID 所占的位数
     */
    private final long datacenterIdBits = 5L;
    /**
     *  支持的最大机器 ID，结果为 31
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     *  支持的最大数据中心 ID，结果为 31
     */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    /**
     *  序列号所占的位数
     */
    private final long sequenceBits = 12L;
    /**
     * 机器 ID 向左移动的位数，结果为 12
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据中心 ID 向左移动的位数，结果为 17
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    /**
     *   时间戳向左移动的位数，结果为 22
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    /**
     * 序列号的最大值，结果为 4095
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long datacenterId;
    private long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private SnowflakeIdGenerator(long datacenterId, long workerId) {
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than maxDatacenterId or less than 0");
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("workerId can't be greater than maxWorkerId or less than 0");
        }
        this.datacenterId = datacenterId;
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    /**
     * @param datacenterId 数据中心id
     * @param workerId 机器id
     * @return id生成实例
     */
    public static SnowflakeIdGenerator getInstance(long datacenterId, long workerId) {
        if (INSTANCE == null) {
            synchronized (SnowflakeIdGenerator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SnowflakeIdGenerator(datacenterId, workerId);
                }
            }
        }
        return INSTANCE;
    }
}
