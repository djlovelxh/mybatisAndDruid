package dj.mybatis.utils;

import org.springframework.stereotype.Component;

/**
 * 经典雪花算法生成id，用于分布式
 * Created by dj on 2020/2/28.
 */
public class SnowFlakeHelper {
        /**
         * 起始的时间戳
         */
        private static final long START_STMP = 1480166465631L;

        /**
         * 每一部分占用的位数
         * 序列号占用的位数
         */
        private static final long SEQUENCE_BIT = 12;
        /**
         * 机器标识占用的位数
         */
        private static final long MACHINE_BIT = 5;
        /**
         * 数据中心占用的位数
         */
        private static final long DATACENTER_BIT = 5;

        /**
         *
         */
        private static final long ONE_MILLION = 1000000;
        /**
         * lastStmp
         */
        private static final long LAST_STMP = -1L;

        /**
         * 每一部分的最大值
         */
        private static final long MAX_DATACENTER_NUM = LAST_STMP ^ (LAST_STMP << DATACENTER_BIT);
        /**
         *
         */
        private static final long MAX_MACHINE_NUM = LAST_STMP ^ (LAST_STMP << MACHINE_BIT);
        /**
         *
         */
        private static final long MAX_SEQUENCE = LAST_STMP ^ (LAST_STMP << SEQUENCE_BIT);

        /**
         * 每一部分向左的位移
         */
        private static final long MACHINE_LEFT = SEQUENCE_BIT;
        /**
         *
         */
        private static final long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
        /**
         *
         */
        private static final long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;
        /**
         * 数据中心
         */
        private long datacenterId;
        /**
         * 机器标识
         */
        private long machineId;
        /**
         * 序列号
         */
        private long sequence = 0L;
        /**
         * 上一次时间戳
         */
        private long lastStmp = LAST_STMP;

        public SnowFlakeHelper(long datacenterId, long machineId) {
            if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
                throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
            }
            if (machineId > MAX_MACHINE_NUM || machineId < 0) {
                throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
            }
            this.datacenterId = datacenterId;
            this.machineId = machineId;
        }

        /**
         * 产生下一个ID
         *
         * @return long
         */
        public synchronized long nextId() {
            long currStmp = getNewstmp();
            if (currStmp < lastStmp) {
                throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
            }

            if (currStmp == lastStmp) {
                //相同毫秒内，序列号自增
                sequence = (sequence + 1) & MAX_SEQUENCE;
                //同一毫秒的序列数已经达到最大
                if (sequence == 0L) {
                    currStmp = getNextMill();
                }
            } else {
                //不同毫秒内，序列号置为0
                sequence = 0L;
            }

            lastStmp = currStmp;

            return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                    | datacenterId << DATACENTER_LEFT       //数据中心部分
                    | machineId << MACHINE_LEFT             //机器标识部分
                    | sequence;                             //序列号部分
        }

        private long getNextMill() {
            long mill = getNewstmp();
            while (mill <= lastStmp) {
                mill = getNewstmp();
            }
            return mill;
        }

        private long getNewstmp() {
            return System.currentTimeMillis();
        }

    /*public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake(1, 1);

        long start = System.currentTimeMillis();
        for (int i = 0; i < ONE_MILLION; i++) {
            System.out.println(snowFlake.nextId());
        }

        System.out.println(System.currentTimeMillis() - start);


    }*/

}
