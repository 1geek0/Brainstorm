package com.g1ee0k.brainstorm;

import java.util.concurrent.Executor;

/**
 * Created by geek on 4/9/16.
 */
class DirectExecutor implements Executor {
    public void execute(Runnable r) {
        r.run();
    }
}