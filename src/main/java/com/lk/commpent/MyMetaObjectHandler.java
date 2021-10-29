package com.lk.commpent;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lk.entity.bse.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author LvKang
 * @createTime 2021-10-15
 * mybatis 自动填充
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    private static String CREATE_ID = "createId";
    private static String CREATE_TIME = "createTime";
    private static String UPDATE_ID = "updateId";
    private static String UPDATE_TIME = "updateTime";
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        LocalDateTime now = LocalDateTime.now();
        Long userId = MyUser.getUserId();

        this.strictInsertFill(metaObject, CREATE_ID, Long.class, userId);
        this.strictInsertFill(metaObject, CREATE_TIME,  LocalDateTime.class, now);
        this.strictInsertFill(metaObject, UPDATE_ID, Long.class, userId);
        this.strictInsertFill(metaObject, UPDATE_TIME,  LocalDateTime.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //这里只有为空才会填充数据 所以修改一下
        try {
            if(metaObject.hasSetter(UPDATE_ID)){
                metaObject.setValue(UPDATE_ID, null);
            }
            if (metaObject.hasSetter(UPDATE_TIME)) {
                log.debug("自动插入 updateTime");
                metaObject.setValue(UPDATE_TIME, null);
            }
        }catch (Exception e){
            log.error("设为空失败");
        }
        Long userId = MyUser.getUserId();

        //this.strictUpdateFill(metaObject, UPDATE_ID, ()-> userId, Long.class);
        this.strictUpdateFill(metaObject, UPDATE_ID,  Long.class, userId);
        this.strictUpdateFill(metaObject, UPDATE_TIME,  LocalDateTime.class, LocalDateTime.now());
    }
}
