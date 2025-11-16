package com.ganzhi.util.canalutil;

import com.ganzhi.domain.User;
import com.ganzhi.util.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

@Slf4j
@Component
@CanalTable(value = "user")
public class UserHander implements EntryHandler<User> {

    private RedisCache redisCache;

    @Override
    public void insert(User user) {
        log.info("insert message  {}", user);
    }

    @Override
    public void update(User before, User after) {
        redisCache.setCacheObject(after.getId().toString(),after);
    }
    @Override
    public void delete(User user) {
        log.info("delete  {}", user);
    }

}
