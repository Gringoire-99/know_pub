-- 菜单SQL
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('1', '', 'kp_admin/commentlike', NULL, '1', 'config', '6');

-- 按钮父菜单ID
    set @parentId = @@identity;

-- 菜单对应按钮SQL
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'kp_admin:commentlike:list,kp_admin:commentlike:info', '2', null, '6';
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'kp_admin:commentlike:save', '2', null, '6';
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'kp_admin:commentlike:update', '2', null, '6';
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'kp_admin:commentlike:delete', '2', null, '6';
