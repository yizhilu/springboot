package com.hc.wechat.service.security;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hc.wechat.common.enums.UseStatus;
import com.hc.wechat.entity.OperatorEntity;
import com.hc.wechat.entity.RoleEntity;
import com.hc.wechat.service.OperatorService;
import com.hc.wechat.service.RoleService;


/**
 * 这个自定义的用户信息查询服务（权限模块使用），用来查询指定的用户详情（包括角色信息）
 * 
 * @author yinwenjie
 *
 */
public class CustomUserSecurityDetailsService implements UserDetailsService {

  @Autowired
  private OperatorService operatorService;

  @Autowired
  private RoleService roleService;

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.
   * String)
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    /*
     * 查找用户的处理过程描述如下： 1、首先查找代理商信息，如果没有找到就差早商户信息，如果还没有找到，就抛出异常 2、查找这个账号对应的角色信息（当然是目前还可以用的角色信息）
     * 3、构造UserDetails对象，并返回
     */
    // 查询用户基本信息
    OperatorEntity currentUser =
        this.operatorService.findByAccountAndStatus(username, UseStatus.STATUS_NORMAL);
    if (currentUser == null) {
      throw new UsernameNotFoundException("没有发现指定的账号，或者账号状态不正确！");
    }

    // 查询用户角色信息
    List<RoleEntity> roles = this.roleService.findByOperatorId(currentUser.getId());;
    if (roles == null || roles.isEmpty()) {
      throw new UsernameNotFoundException("用户权限状态错误，请联系客服人员！");
    }
    List<SimpleGrantedAuthority> authorities = new LinkedList<>();
    for (RoleEntity role : roles) {
      SimpleGrantedAuthority authoritie = new SimpleGrantedAuthority(role.getName());
      authorities.add(authoritie);
    }

    // 角色信息形成authorities集合对象
    UserDetails securityDetails = new User(username, currentUser.getPassword(), authorities);
    return securityDetails;
  }
}
