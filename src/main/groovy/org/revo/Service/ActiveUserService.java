package org.revo.Service;

import org.revo.Domain.UserStats;

import java.util.Map;

/**
 * Created by ashraf on 05/11/16.
 */
public interface ActiveUserService {
    void mark(String user);

    Map<String, UserStats> getActiveUsers();
}
