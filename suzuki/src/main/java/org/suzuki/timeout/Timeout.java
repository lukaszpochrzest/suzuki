package org.suzuki.timeout;

import java.util.concurrent.*;

public class Timeout {

    private ScheduledExecutorService scheduledExecutorService;

    private ScheduledFuture<?> scheduledFuture;

    public Timeout() {
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
        ((ScheduledThreadPoolExecutor)scheduledExecutorService).setRemoveOnCancelPolicy(true);
    }

    public void executeOnTimeout(Runnable r, long timeoutMillis) {
        scheduledFuture = scheduledExecutorService.schedule(
                r,
                timeoutMillis,
                TimeUnit.MILLISECONDS
        );

    }

    public void cancel() {
        scheduledFuture.cancel(true);
    }


}
