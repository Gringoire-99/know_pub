-- 菜单SQL
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('1', '', 'kp_admin/post', NULL, '1', 'config', '6');

-- 按钮父菜单ID
    set @parentId = @@identity;

-- 菜单对应按钮SQL
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'kp_admin:post:list,kp_admin:post:info', '2', null, '6';
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'kp_admin:post:save', '2', null, '6';
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'kp_admin:post:update', '2', null, '6';
    INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'kp_admin:post:delete', '2', null, '6';
