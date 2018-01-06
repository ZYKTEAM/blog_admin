package com.myblog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import com.myblog.ulits.PageList;
import com.myblog.ulits.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.myblog.domain.Permission;
import com.myblog.domain.RolePermission;
import com.myblog.domain.TreeNode;
import com.myblog.service.PermissionService;
import com.myblog.service.RolePermissionService;
import com.myblog.ulits.HttpResults;
import com.myblog.ulits.UserUtil;

@Controller
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;


    @RequestMapping(value = "/permissions/pid", method = RequestMethod.GET)
    public @ResponseBody
    List<TreeNode> findPermissionsByPid(Long pid) {
        List<TreeNode> trees = new ArrayList<TreeNode>();
        pid = pid == null ? 0L : pid;
        List<Permission> list = permissionService.findPermissionsByPId(pid);
        if (UserUtil.getUser().getLoginName().equalsIgnoreCase("admin")) {
            for (Permission perm : list)
                trees.add(new TreeNode(perm.getId() + "", perm.getTitle(), perm.getLeaf() == 0 ? false : true, null, 0, perm.getPid()));
        } else {
            for (Permission perm : list) {
                trees.add(new TreeNode(perm.getId() + "", perm.getTitle(), perm.getLeaf() == 0 ? false : true, null, 0, perm.getPid()));
            }
        }
        return trees;
    }

    @RequestMapping(value = "/rolePermissions", method = RequestMethod.POST)
    public @ResponseBody
    HttpResults saveRolePermissions(Integer type, Long refId, @RequestParam(value = "permIds[]", required = false) Long[] permIds) {
        HttpResults result = new HttpResults();
        if (permIds != null && permIds.length > 0) {
            List<RolePermission> oldList = rolePermissionService.findRolePermissions(type, refId);
            List<Long> oldPermIds = oldList.stream().collect(Collectors.mapping(RolePermission::getPermissionId, Collectors.toList()));
            List<RolePermission> list = Lists.newArrayList();
            for (Long permId : permIds) {
                if (oldPermIds.contains(permId)) oldPermIds.remove(permId);
                else list.add(new RolePermission(permId, type, refId, 0));
            }
            if (oldPermIds.size() > 0) {
                Long[] removeIds = oldPermIds.toArray(new Long[]{});
                rolePermissionService.removeRolePermissionsByRefId(removeIds, type, refId);
            }
            if (list.size() > 0) rolePermissionService.saveRolePermissionBatch(list);
        } else {
            List<RolePermission> oldList = rolePermissionService.findRolePermissions(type, refId);
            List<Long> oldPermIds = oldList.stream().collect(Collectors.mapping(RolePermission::getPermissionId, Collectors.toList()));
            if (oldPermIds.size() > 0) {
                Long[] removeIds = oldPermIds.toArray(new Long[]{});
                rolePermissionService.removeRolePermissionsByRefId(removeIds, type, refId);
            }
        }
        return result;
    }

    @RequestMapping(value = "/rolePermissions/refid", method = RequestMethod.GET)
    public @ResponseBody
    HttpResults findRolePermissionsByRefId(Integer type, Long refId) {
        HttpResults result = new HttpResults();
        List<RolePermission> list = rolePermissionService.findRolePermissions(type, refId);
        List<Long> permIds = list.stream().collect(Collectors.mapping(RolePermission::getPermissionId, Collectors.toList()));
        result.put("permIds", permIds);
        return result;
    }

    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    public @ResponseBody
    List<Permission> findPermissions() {
        return UserUtil.getPermissions();
    }

    /**
     * 菜单主页
     *
     * @return
     */
    @RequestMapping(value = "/permissions/index", method = RequestMethod.GET)
    public String menuIndex() {
        return "menu/menu";
    }

    /**
     * 菜单主页
     *
     * @return
     */
    @RequestMapping(value = "/permissions/data", method = RequestMethod.GET)
    public @ResponseBody
    PageList<Permission> menuData(Integer start, Integer limit, String query) {
        PageParam page = new PageParam(start, limit);
        int count = permissionService.countListPermisson(query);
        PageList<Permission> result = new PageList<>(count, limit);
        if (count > page.getStart())
            result.setList(permissionService.findList(page.getStart(), page.getLimit(), query));
        return result;
    }

    /**
     * 保存菜单
     */
    @RequestMapping(value = "/permissions/saveMenu", method = RequestMethod.POST)
    public @ResponseBody HttpResults saveMenu(Permission menu) {
        HttpResults results = new HttpResults();
        try {
            if (menu == null || menu.getPid() == null || menu.getUrl() == null) {
                results.setStatus("203");
                results.setMessage("参数错误");
                return results;
            }
            permissionService.saveMenu(menu);
        } catch (Exception e) {
            e.printStackTrace();
            results.setStatus("203");
            results.setMessage("参数错误");
            return results;
        }
        results.setMessage("操作成功");
        return results;
    }

    /**
     * 根据ID查询菜单
     *
     * @return
     */
    @RequestMapping(value = "/permissions/query", method = RequestMethod.GET)
    public @ResponseBody
    HttpResults menuData(Long id) {
        HttpResults results = new HttpResults();
        if (id == null) {
            results.setStatus("202");
            results.setMessage("参数错误");
            return results;
        }
        Permission menu = permissionService.getMenuById(id);
        if (menu == null) {
            results.setMessage("该信息不存在");
            results.setStatus("203");
            return results;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", menu);
        results.setAttrs(map);
        return results;
    }


}
