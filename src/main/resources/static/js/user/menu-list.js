$(function () {

    var current = 1;
    var limit = 10;

    var params = {'start': 0, 'limit': limit, 'query': null};
    menuList();

    initPermissionsTree();
    /* 搜索框条件查询 回车*/
    $("#query").keydown(function (event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            current = 1;
            menuList();
        }
    });

    /* 搜索框条件查询 点击按钮*/
    $("#but_query").click(function () {
        current = 1;
        menuList();
    });

    /* 参数*/
    function paramsing() {
        var query = $("#query").val();
        params['start'] = current - 1;
        params['limit'] = limit;
        params['query'] = query
    }

    /* 方法*/
    function menuList() {
        paramsing();
        $("#menuList").empty();
        $.ajax({
            type: "get",
            url: menuData,
            data: params,
            success: function (data) {
                $("#roleTempl").tmpl(data).appendTo("#menuList");
                pageing(data.count);
            }, error: function (data) {
                layer.alert("出现错误信息!");
            }
        });
    }

    /**
     * 分页事件
     */
    function pageing(count) {
        if (count < 1) {
            $(".pagination").empty();
            return false;
        }
        var totalPage = parseInt(count / limit) + ((count % limit) > 0 ? 1 : 0);
        if (totalPage > 1) {
            $(".pagination").show();
            $(".pagination").bootstrapPaginator({
                bootstrapMajorVersion: 3.0,
                currentPage: current,
                totalPages: totalPage,
                numberOfPages: limit,
                itemTexts: function (type, page, currentpage) {
                    switch (type) {
                        case "first":
                            return "首页";
                        case "prev" :
                            return "上一页";
                        case "next" :
                            return "下一页";
                        case "last" :
                            return "尾页";
                        case "page" :
                            return page;
                    }
                }, onPageClicked: function (event, originalEvent, type, page) {
                    if (current == page) {
                        return false;
                    }
                    current = page;
                    menuList();
                }
            });
        } else {
            $(".pagination").hide();
        }
    };
    /**
     *删除菜单 逻辑删除
     */
    $("#menuList").on("click", ".to-delete", function () {
        var id = this.getAttribute("roleid");
        $.ajax({
            url: permissions_delete,
            data: "id=" + id,
            dataType: "json",
            success: function (result) {
                resultMsg(result);
            }
        })
    });

    /* 菜单修改*/
    $("#menuList").on("click", ".to-user", function () {
        $("#j-table-2").find(".j-checked").removeClass("j-checked");
        confirmUserStyle(".j-che-2", ".j-btn-2");
        $("#role-user").modal("show");
        pageNum = 1;
        var id = this.getAttribute("roleid");
        $.ajax({
            url: permissions_query,
            data: "id=" + id,
            dataType: "json",
            success: function (result) {
                if (result.status != 10000) {
                    alertLayerMessage(result.message);
                } else {
                    console.log(result.attrs);
                    var menuObj = result.attrs.data;
                    //获取表单下面所有input控件
                    var formArray = $("#updateForm_id")[0];
                    for (var i = 0; i < formArray.elements.length; i++) {
                        for (var menu in menuObj) {
                            if (formArray.elements[i].name == menu && formArray.elements[i].type != "radio") {
                                formArray.elements[i].value = menuObj[menu];
                            }
                            if (formArray.elements[i].type == "radio") {
                                if (menuObj[menu] == 0) {
                                    $("#menu_type_id")[0].checked = true;
                                }
                                if (menuObj[menu] == 1) {
                                    $("#menu_type_id2")[0].checked = true;
                                }
                                if ($("#updateForm_id div[id='treeview2']")[0].getAttribute("data-name") == menu) {
                                    $('#treeview2').jstree("select_node", menuObj.pid);
                                }
                            }
                        }
                    }
                    $("#menu_id_id").val(menuObj.id);
                    $("#menu_isdeleted_id").val(menuObj.isDeleted);
                }
            }
        });

        // roleUserList($(this).attr("roleId"));//对比存在用户
        // $("#thisRoleId").attr("thisRoleId", $(this).attr("roleId"));
        menuList();//用户列表
    });

    //样式切换
    $("#menuList").on("click", ".j-btn-2", function () {
        confirmUserStyle(".j-che-2", ".j-btn-2");
    });

    //添加角色
    $('.j-role-add').on('click', function () {
        $("#role-type").show();
        $('#role-id').val('');
        $('#input-remake').val('');
        $('#Modal-role-add').find('.modal-title').text('添加菜单');
        $('#Modal-role-add').modal('show');
    })

    // 确定
    $('#do_save_userroles').on("click", function () {
        saveing(function (data) {
            alertLayerMessage("保存成功！");
            $('#Modal-role-add').modal('hide');
            menuList();
        });
    });

    //编辑角色
    $('#menuList').on('click', '.to-edit', function () {
        console.log(111);
        $("#role-type").hide();
        var id_ = $(this).attr("roleId");
        geting(id_, function (data) {
            $('#role-id').val(id_);
            $('#input-remake').val(data.attrs.role.name);
            $('#Modal-role-add').find('.modal-title').text('编辑角色');
            $('#Modal-role-add').modal('show');
        });
    })

    //保存 or 移除
    $("#role-user-add").click(function () {
        var userIds1 = new Array();//页面一共选择多少教师
        var userIds2 = new Array();//已存在组织教师
        for (var i = 0; i < $("#menuList").find(".j-checked").length; i++) {
            userIds1.push($("#menuList").find(".j-checked").eq(i).attr("user-id"));
            userIds2.push($("#menuList").find(".old_id").eq(i).attr("user-id"));
        }

        delteaIds = new Array();//删除
        $("#menuList .old_id").each(function (k, v) {
            if ($(v).attr("class").indexOf("j-checked") == -1) {
                delteaIds.push($(v).attr("user-id"));
            }
        });

        //临时数组存放 --去除重复
        var tempArray1 = [];//临时数组1
        var tempArray2 = [];//临时数组2

        for (var i = 0; i < userIds2.length; i++) {
            tempArray1[userIds2[i]] = true;//将数array2 中的元素值作为tempArray1 中的键，值为true；
        }
        for (var i = 0; i < userIds1.length; i++) {
            if (!tempArray1[userIds1[i]]) {
                tempArray2.push(userIds1[i]);//过滤array1 中与array2 相同的元素；
            }
        }
//    	console.log(tempArray2)
//    	console.log(delteaIds)
        $.ajax({
            type: "POST",
            url: "/admin/roleUser",
            data: {
                userIds: tempArray2,
                delteaIds: delteaIds,
                roleId: $("#thisRoleId").attr("thisRoleId")
            },
            success: function (teachers) {
                $("#role-user").modal("hide");
                menuList();
            }
        })
    });

});

/**
 * 根据ID查询数据
 * @param callback
 */
function getMenuById(menuid) {
    $.ajax({
        url: getMenuData,
        "type": "GET",
        dataType: "json",
        data: "menuid=" + menuid,
        success: function (data) {
            if (data.status == 200) {
                var result = data.data;
                $("#menu_name_id").val(result.title);
                $("#menu_url_id").val(result.url);
                if (result.type == 0) {
                    $("#menu_type_id").attr({checked: "checked"});
                    $("#menu_type_id2").removeAttr("checked");
                } else if (result.type == 1) {
                    $("#menu_type_id2").attr({checked: "checked"});
                    $("#menu_type_id").removeAttr("checked");
                }
            } else if (data.status == 202) {
                alertLayerMessage("参数错误")
            } else if (data.status == 203) {
                alertLayerMessage("该信息不存在")
            }
        }, error: function () {
            alertLayerMessage("操作异常")
        }
    })

}

//添加、修改请求
function saveing(callback) {
    var saveParams = saveParamsing();
    $.ajax({
        type: "POST",
        url: "/admin/roles",
        dataType: "json",
        data: saveParams,
        success: function (data) {
            if (data.status == 10000)
                (typeof callback == 'function') ? callback() : $.dialog({'title': '提示:', 'content': '操作成功！'});
            else if (data.message)
                alertLayerMessage(data.message);
            else alertLayerMessage("操作成功");
            ;
        }, error: function () {
            alertLayerMessage("操作异常");
        }
    });
}

//添加、修改参数
function saveParamsing() {
    var roleid = $('#role-id').val();
    var remark = $('#input-remake').val();
    var type = $("#input-type").val();
    var name = "";
    if (type == 1) {
        name = "管理员";
    } else {
        name = "一般用户";
    }
    var saveParams = {'id': roleid, 'name': name, 'type': type, 'remark': remark};
    return saveParams;
}

//获取单个信息
function geting(id_, callback) {
    if (!id_) {
        layer.alert("请求异常！", {title: "请求错误，缺少参数"});
        return false;
    }
    $.ajax({
        type: "GET",
        url: "/admin/roles" + '/' + id_,
        dataType: "json",
        success: function (data) {
            if (typeof callback == 'function') callback(data);
        }, error: function () {
            layer.alert("请求异常！", {title: "请求异常"});
        }
    });
}

//组织存在教师
function roleUserList(roleId) {
    $.ajax({
        type: "GET",
        url: "/admin/findRoleUserByRoleId",
        data: {
            roleId: roleId
        },
        success: function (date) {
            var userIds = "";
            for (var i = 0; i < date.length; i++) {
                userIds += "," + date[i].userId;
            }
            $("#exist-userId").attr("userIds", userIds);
        }
    })
}


/**
 * 模态框 - 用户列表
 */
var size1 = 10;
var pageNum1 = 1;
var paramsu = {'start': 0, 'limit': size1, 'query': null};

function userParamsing() {
    var query = $("#query").val();
    paramsu['start'] = pageNum1 - 1;
    paramsu['limit'] = size1;
    paramsu['query'] = query
}

function menuList() {
    userParamsing();
    $.ajax({
        type: "GET",
        url: "/admin/listUser",
        data: paramsu,
        success: function (data) {

            $("#menuList").empty();
            $("#userTmpl").tmpl(data).appendTo("#menuList");

            var roleIdStr = $("#exist-userId").attr("userIds");
            var roleIds = roleIdStr.split(",");
            $("#menuList tr").each(function (k, v) {
                var selectedTeaId = $(v).attr("user-id");
                var exist = $.inArray(selectedTeaId, roleIds);
                if (exist != -1) {
                    $(v).addClass("j-checked old_id");//old_id 区分教师
                    $(v).find(".j-btn-2").removeClass("btn-info").addClass("btn-primary").text("已选");
                }
            });
            pagination(data.count);
            opt.cusCheckbox(".j-che-2", ".j-btn-2"); //声明用户多选'
        }
    })
}

/**
 * 分页事件
 */
function pagination(count) {
    if (count < 1) {
        $("#userPage").empty();
        return false;
    }
    var totalPage1 = parseInt(count / size1) + ((count % size1) > 0 ? 1 : 0);
    if (totalPage1 > 1) {
        $("#userPage").show();
        $("#userPage").bootstrapPaginator({
            bootstrapMajorVersion: 3.0,
            currentPage: pageNum1,
            totalPages: totalPage1,
            numberOfPages: 10,
            itemTexts: function (type1, page1, currentpage1) {
                switch (type1) {
                    case "first":
                        return "首页";
                    case "prev" :
                        return "上一页";
                    case "next" :
                        return "下一页";
                    case "last" :
                        return "尾页";
                    case "page" :
                        return page1;
                }
            },
            onPageClicked: function (event, originalEvent, type1, current1) {
                if (pageNum1 == current1) {
                    return false;
                }
                pageNum1 = current1;
                menuList();
            }
        });
    } else {
        $("#userPage").hide();
    }
}

//选择用户样式重置
function confirmUserStyle(list, listbtn) {
    for (var i = 0; i < $(list).length; i++) {
        if ($(list).eq(i).attr("class").indexOf("j-checked") != -1) {
            $(list).eq(i).find(listbtn).removeClass("btn-info").addClass("btn-primary").text("已选");
        } else {
            $(list).eq(i).find(listbtn).removeClass("btn-primary").addClass("btn-info").text("可选");
        }
    }
}

/* 加载权限模态框树形结构*/
function initPermissionsTree() {
    var organizationTree = $('#treeview2').jstree({
        'core': {
            'multiple': false,
            "themes": {
                "responsive": false
            },
            'data': {
                'url': '/admin/permissions/pid',
                'type': 'get',
                'dataType': 'json',
                'async': false,
                'data': function (data) {
                    return {'pid': data.id == '#' ? null : data.id};
                },
                'success': function (response) {
                }
            },
        },
        'types': {
            "default": {"icon": "fa fa-folder-open"},
            "demo": {"icon": "fa fa-flash"}
        },
        "checkbox": {
            "three_state": false
        },
        "plugins": ["search", "checkbox", "wholerow", "types"],
    }).bind("select_node.jstree", function (e, data) { //点击事件
        //$("#treeview2").jstree('open_all', data.node);
    }).bind("open_node.jstree", function (e, data) {
        //$("#treeview2").jstree('open_all', data.node);
        // $("#treeview2").jstree("load_all");
    }).bind("loaded.jstree", function (e, data) {
        $("#treeview2").jstree("load_all");
    });
}


/* 获取树形结构参数*/
function saveParamsing() {
    var nodes = $('#treeview2').jstree().get_checked(true);
    return nodes[0].id;
}

/**
 * 保存系统菜单
 * @param callback
 */
function saveMenu() {
    var parentid = saveParamsing();
    if (parentid == "") {
        alertLayerMessage("参数错误")
        return;
    }
    var formArray = $("#updateForm_id").serializeArray();
    formArray.push({name: 'pid', value: parentid});
    $.ajax({
        type: "POST",
        url: saveMenuUrl,
        dataType: "json",
        data: formArray,
        success: function (data) {
            if (data.status == 10000) {
                location.reload();
                alertLayerMessage("操作成功");
            } else if (data.message) {
                alertLayerMessage(data.message);
            } else {
                alertLayerMessage("操作成功");
                location.reload();
            }
        }, error: function () {
            alertLayerMessage("操作异常");
        }
    });
}

$("#insertMenu_id").on("click", function () {
    $("#j-table-2").find(".j-checked").removeClass("j-checked");
    confirmUserStyle(".j-che-2", ".j-btn-2");
    $("#role-user").modal("show");
    pageNum = 1;
    //获取表单下面所有input控件
    var formArray = $("#updateForm_id")[0];
    for (var i = 0; i < formArray.elements.length; i++) {
        if (formArray.elements[i].type == "text") {
            formArray.elements[i].value = "";
        }
        if (formArray.elements[i].type == "radio") {
            formArray.elements[i].checked = false;
        }
    }
    //清空点击编辑框回显之后的值
    // $('#treeview2').jstree("select_node", "");
});

function resultMsg(data) {
    if (data.status == 10000) {
        alertLayerMessage("操作成功");
        location.reload();
    } else if (data.message) {
        alertLayerMessage(data.message);
    }
}

var saveMenuUrl = "/admin/permissions/saveMenu";
var menuData = "/admin/permissions/data";
var getMenuData = "";
var permissions_query = "/admin/permissions/query";
var permissions_delete = "/admin/permissions/deleteMenu";