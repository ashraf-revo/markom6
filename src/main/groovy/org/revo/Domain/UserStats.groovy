package org.revo.Domain

import java.util.concurrent.atomic.AtomicLong

/**
 * Created by ashraf on 05/11/16.
 */
class UserStats {
    private AtomicLong atomicLong = new AtomicLong(System.currentTimeMillis())

    void mark() {
        this.atomicLong.set(System.currentTimeMillis())
    }

    Long lastAccess() {
        return this.atomicLong.get()
    }
}
