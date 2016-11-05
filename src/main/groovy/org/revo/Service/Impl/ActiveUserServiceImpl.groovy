package org.revo.Service.Impl

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import org.revo.Domain.UserStats
import org.revo.Service.ActiveUserService
import org.springframework.stereotype.Service

import java.util.concurrent.ConcurrentMap
import java.util.concurrent.TimeUnit

/**
 * Created by ashraf on 05/11/16.
 */
@Service
class ActiveUserServiceImpl implements ActiveUserService{
    private LoadingCache<String, UserStats> statsByUser = CacheBuilder.newBuilder()
            .expireAfterAccess(20, TimeUnit.MINUTES)
            .build(new CacheLoader<String, UserStats>() {
        @Override
        UserStats load(String s) throws Exception {
            return new UserStats()
        }
    });

    public void mark(String user) {
        this.statsByUser.getUnchecked(user).mark()
    }

    public Map<String,UserStats> getActiveUsers() {
        return this.statsByUser.asMap()
    }
}
