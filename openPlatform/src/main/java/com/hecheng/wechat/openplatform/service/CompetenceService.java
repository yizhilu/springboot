package com.hecheng.wechat.openplatform.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hecheng.wechat.openplatform.common.enums.UseStatus;
import com.hecheng.wechat.openplatform.entity.CompetenceEntity;
import com.hecheng.wechat.openplatform.entity.OperatorEntity;


/**
 * 权限service接口.
 * 
 * @author ly yinwenjie
 * @date 2017年8月18日 下午3:19:03
 * @version V1.0
 */
public interface CompetenceService {
  /**
   * 查询符合权限状态的信息.
   * 
   * @param useStatus 状态（正常/禁用）
   * @return List<CompetenceEntity>
   */
  List<CompetenceEntity> findByStatus(UseStatus useStatus);

  /**
   * 查询为当前URL设置的功能信息，注意，这里没有通过method进行过滤.
   * 
   * @param resource 权限串
   * @return List<CompetenceEntity>
   */
  List<CompetenceEntity> findByResource(String resource);

  /**
   * 查询指定的角色已绑定的功能信息，无论这些功能是否可用（状态是否正常）.
   * 
   * @param roleId 角色id
   * @return List<CompetenceEntity>
   */
  List<CompetenceEntity> findByRoleId(String roleId);

  /**
   * 查询指定的角色已绑定的功能信息，且这些功能状态符合查询的要求.
   * 
   * @param roleId 角色id
   * @param status 状态（正常/禁用）
   * @return List<CompetenceEntity>
   */
  List<CompetenceEntity> findByRoleId(String roleId, UseStatus status);

  /**
   * 新增权限.
   * 
   * @param comp 权限对象
   * @return CompetenceEntity
   */
  CompetenceEntity addCompetence(CompetenceEntity comp);

  /**
   * 修改权限.
   * 
   * @param comp 权限对象
   * @return CompetenceEntity
   */
  CompetenceEntity updateCompetence(CompetenceEntity comp);

  /**
   * 启用/禁用.
   * 
   * @param id 权限id
   * @param flag 标识（true：启用操作；false：禁用操作）
   * @param operator 操作者
   * @return CompetenceEntity
   */
  CompetenceEntity diableOrUndisable(String id, Boolean flag, OperatorEntity operator);

  /**
   * 根据id获取权限详情（单条）.
   * 
   * @param id 权限id
   * @return CompetenceEntity
   */
  CompetenceEntity getById(String id);

  /**
   * 条件分页查询.
   * 
   * @param params 条件
   * @param pageable 分页
   * @return Page<CompetenceEntity>
   */
  Page<CompetenceEntity> getByCondition(Map<String, Object> params, Pageable pageable);


  /**
   * 根据methods和resource查找是否存在权限对象.
   * 
   * @param method 方法
   * @param resource 权限串
   * @param id 权限id
   * @return boolean
   */
  boolean isExit(String methods, String resource, String id);

  /**
   * 基于功能的URL信息，确定当前登录者针对这个功能有没有被授权
   * 
   * @param opUserAccount
   * @param url 功能的url信息，权限URL串。注意如果URL信息中有参数信息。则使用“{param}”代替。例如：<br>
   *        /vz/param4/{param}
   * @param methods POST或者POST|GET|DELETE|PATCH等，传入的大小写没有关系，反正都会转为大写
   * @return 如果被授权则返回true；其它情况返回false
   */
  Boolean findCompetencePermissionByUrl(String opUserAccount, String url, String methods);

  /**
   * 基于功能的唯一编号信息，确定当前登录者针对这个功能有没有被授权
   * 
   * @param opUserAccount 当前登录的用户账户信息
   * @param competenceId 指定的功能编号信息
   * @return 如果被授权则返回true；其它情况返回false
   */
  Boolean findCompetencePermissionById(String opUserAccount, String competenceId);
}
