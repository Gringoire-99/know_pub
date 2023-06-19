USE know_pub_admin;
-- 菜单SQL
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('1', '', 'kp_admin/action', NULL, '1', 'config', '6');

-- 按钮父菜单ID
    set @parentId = @@identity;

-- 菜单对应按钮SQL
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'kp_admin:action:list,kp_admin:action:info', '2', null, '6';
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'kp_admin:action:save', '2', null, '6';
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'kp_admin:action:update', '2', null, '6';
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'kp_admin:action:delete', '2', null, '6';
