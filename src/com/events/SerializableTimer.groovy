package com.events

/**
 * Created by Tom on 8/19/2017.
 */
class SerializableTimer extends Timer implements Serializable {
    private transient final Timer timer = new Timer()
    long delay_ms
    Closure callback // TODO: make transient?
    int repeatCount
    long t_ms

    // Reference: http://mrhaki.blogspot.com/2009/11/groovy-goodness-run-code-at-specified.html
    private class GroovyTimerTask extends TimerTask {
        Closure closure

        GroovyTimerTask(Closure closure) {
            this.closure = closure
        }

        @Override
        void run() {
            closure()
        }
    }

    SerializableTimer(long delay_ms = 1000L, Closure callback, int repeatCount = 0, long t_ms = 1000L) {
        this.delay_ms = delay_ms
        this.callback = callback
        this.repeatCount = repeatCount
        this.t_ms = t_ms
    }

    void start() {
//        if(repeatCount == 0) {
//            timer.runAfter(delay_ms, callback)
//        } else if(repeatCount > 0) {
        if(repeatCount >= 0) {
            for (int i = 0; i < repeatCount; i++) {
                timer.schedule(new GroovyTimerTask(callback), delay_ms, t_ms * i)
            }
        } else {
            timer.scheduleAtFixedRate(new GroovyTimerTask(callback), delay_ms, t_ms)
        }
    }

    void stop() {
        timer.cancel()
        timer.purge() // TODO: check
    }

    void restart() {
        stop()
        start()
    }

    // TODO: serialization methods

}
