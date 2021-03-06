package com.example.pekon.helloandroid.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.pekon.helloandroid.entity.PhoneInfoEntity;

import com.example.pekon.helloandroid.greendao.gen.PhoneInfoEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig phoneInfoEntityDaoConfig;

    private final PhoneInfoEntityDao phoneInfoEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        phoneInfoEntityDaoConfig = daoConfigMap.get(PhoneInfoEntityDao.class).clone();
        phoneInfoEntityDaoConfig.initIdentityScope(type);

        phoneInfoEntityDao = new PhoneInfoEntityDao(phoneInfoEntityDaoConfig, this);

        registerDao(PhoneInfoEntity.class, phoneInfoEntityDao);
    }
    
    public void clear() {
        phoneInfoEntityDaoConfig.clearIdentityScope();
    }

    public PhoneInfoEntityDao getPhoneInfoEntityDao() {
        return phoneInfoEntityDao;
    }

}
