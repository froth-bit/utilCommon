package com.wyc.utils.user.service.impl;

import com.wyc.utils.user.entity.TUser;
import com.wyc.utils.user.mapper.TUserMapper;
import com.wyc.utils.user.service.TUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wyc
 * @since 2021-07-26
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

}
