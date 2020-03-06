package dj.mybatis.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 列举各种多线程用法
 * Created by dj on 2020/2/28.
 */
public class MultithreadHelper {
    private static ConcurrentLinkedQueue<Integer> efficiencyDataList = null;

    public static void main(String[] args) {
        test1();
        test2();
        test3();
//        test4();

    }

    /**
     * 继承thread类的方法
     */
    private static void test1() {

    }

    /**
     * 实现runable接口的方法
     */
    private static void test2() {
    }

    /**
     * ExcuterService 线程池形式
     */
    private static void test3() {
        //初始化多线程使用的数据表
        efficiencyDataList = new ConcurrentLinkedQueue();
        for (int i = 0; i < 5; i++) {
            efficiencyDataList.offer(i);
        }
        // 创建一个线程池
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ExecutorService executorService = new ThreadPoolExecutor(5, 5,
                60L, TimeUnit.SECONDS,
                 new ArrayBlockingQueue(5));
        // 创建存储任务的容器
        List<Callable<String>> tasks = new ArrayList<Callable<String>>();
        // 提交任务
        for (int i = 0; i < 5; i++) {
            Callable<String> task = new Callable<String>() {
                public String call() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int j = 0; j < 5000; j++) {
                        if (j % 2 == 0) {
                            System.out.println(j);
                        }
                    }
                    return "1";
                }
            };
            executorService.submit(task);
            // 将task添加进任务队列
            tasks.add(task);
        }

        // 获取线程的返回结果
        List<Future<String>> results = null;
        try {
            results = executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 输出结果
        for (int i = 0; i < 5; i++) {
            // 获取包含返回结果的future对象
            Future<String> future = results.get(i);
            // 从future中取出执行结果（若尚未返回结果，则get方法被阻塞，直到结果被返回为止）
            String result1 = null;
            try {
                result1 = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(result1);
        }
    }

    /**
     * countDownLatch方式实现
     */
    private static void test4() {
        //CountDownLatch 为唯一的、共享的资源
        final CountDownLatch latch = new CountDownLatch(5);

        LatchDemo latchDemo = new LatchDemo(latch);

        long begin = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            new Thread(latchDemo).start();
        }
        try {
            //多线程运行结束前一直等待
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();

        System.out.println("耗费时间：" + (end - begin));

    }

    static class LatchDemo implements Runnable {

        private CountDownLatch latch;

        public LatchDemo(CountDownLatch latch) {
            this.latch = latch;
        }

        public LatchDemo() {
            super();
        }

        @Override
        public void run() {
            //当前对象唯一，使用当前对象加锁，避免多线程问题
            synchronized (this) {
                try {
                    for (int i = 0; i < 50000; i++) {
                        if (i % 2 == 0) {
                            System.out.println(i);
                        }
                    }
                } finally {
                    //保证肯定执行
                    latch.countDown();
                }
            }
        }

    }

}
