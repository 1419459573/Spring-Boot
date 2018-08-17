package com.example.api.testMybatis.service.impl;


import com.example.api.testMybatis.dao.UserMapper;
import com.example.api.testMybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by XieZhiXin on 2018/8/8.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapperDao;

    @Override
    public String getUserName(int stu_id)
    {
        String  name=userMapperDao.getUserNameById(stu_id);
        return name;
    }
}
