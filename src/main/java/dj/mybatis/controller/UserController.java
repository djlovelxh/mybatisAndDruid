package dj.mybatis.controller;

import dj.mybatis.dao.IUserDao;
import dj.mybatis.dao.SecondaryUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by dj on 2020/2/28.
 */
@RestController
@RequestMapping(value = "/")
public class UserController {


    @Autowired
    dj.mybatis.dao.IUserDao iUserDao;
    @Autowired
    dj.mybatis.dao.SecondaryUserMapper SecondaryUserMapper;

    @RequestMapping("test")
    public Object primary() {
        List<Map<String, Object>> list1 = iUserDao.getName();
        List<Map<String, Object>> list = SecondaryUserMapper.findAll();
        System.out.print(list);
        System.out.print(list1);
        return list1;
    }
}
