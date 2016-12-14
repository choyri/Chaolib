layui.define(['layer', 'form', 'element', 'util', 'upload'], function(exports) {
    var $ = layui.jquery;

    var btnEvent = {
        borrow: function() {
            var id = $(this).data('id');
            layer.confirm('确定借阅？', function() {
                $.post('./api', {id: id, type: 'borrow'}, function(data) {
                    if (data.res == 'error') {
                        layer.msg(data.msg);
                    } else {
                        var amount = parseInt($('#amount').html());
                        $('#amount').html(amount - 1);
                        layer.msg('借阅成功！');
                    }
                }, 'json');
            });
        },
        editBook: function() {
            var par = $(this).parent().parent();
            var id = par.data('id');
            $('input[name="bTitle"]').val(par.find('.td-title').html());
            $('input[name="bAuthor"]').val(par.find('.td-author').html());
            $('input[name="bIsbn"]').val(par.find('.td-isbn').html());
            $('#book dd[lay-value="' + par.find('.td-type').val() + '"]').click();
            $('input[name="bAmount"]').val(par.find('.td-amount').html());
            $('input[name="bPrice"]').val(par.find('.td-price').val());
            $('textarea[name="bIntro"]').val(par.find('.td-intro').val());
            $('input[name="bid"]').val(id);
            $('#book input[name="isEdit"]').val('1');
            $('.layui-form-mid.color-blue').html('如果不修改封面，无需上传。').fadeIn();
            $('#bookHandle').html('修改').click();
        },
        delBook: function() {
            var par = $(this).parent().parent();
            var id = par.data('id');
            layer.confirm('确定删除这本书？', function() {
                $.post('./api', {id: id, type: 'delBook'}, function(data) {
                    if (data.res == 'error') {
                        layer.msg(data.msg);
                    } else {
                        par.fadeOut();
                        layer.msg('删除成功！');
                    }
                }, 'json');
            });
        },
        addType: function() {
            var par = $(this).parent().parent();
            layer.prompt({title: '输入名称：'}, function(value ,index) {
                $.post('./api', {name: value, type: 'addType'}, function(data) {
                    if (data.res == 'error') {
                        layer.msg(data.msg);
                    } else {
                        layer.close(index);
                        layer.msg('新增成功！', {time: 2000}, function() {
                            location.href = './admin';
                        });
                    }
                }, 'json');
            });
        },
        editType: function() {
            var par = $(this).parent().parent();
            var id = par.data('id');
            layer.prompt({title: '输入新名称：'}, function(value ,index) {
                if (par.children('td').eq(1).html() == value) {
                    layer.msg("既然是同样的名称就不要修改了。");
                    return false;
                }
                $.post('./api', {id: id, name: value, type: 'editType'}, function(data) {
                    if (data.res == 'error') {
                        layer.msg(data.msg);
                    } else {
                        layer.close(index);
                        par.children('td').eq(1).html(value);
                        layer.msg('修改成功！');
                    }
                }, 'json');
            });
        },
        delType: function() {
            var par = $(this).parent().parent();
            var id = par.data('id');
            layer.confirm('确定删除这个类别？', function() {
                $.post('./api', {id: id, type: 'delType'}, function(data) {
                    if (data.res == 'error') {
                        layer.msg(data.msg);
                    } else {
                        par.fadeOut();
                        layer.msg('删除成功！');
                    }
                }, 'json');
            });
        },
        returnBook: function() {
            var par = $(this).parent().parent();
            var id = par.data('id');
            layer.confirm('确定已归还？此操作无法撤销。', function() {
                $.post('./api', {id: id, type: 'returnBook'}, function(data) {
                    if (data.res == 'error') {
                        layer.msg(data.msg);
                    } else {
                        par.children('td').eq(4).html('<span class="color-blue">是</span>');
                        par.children('td').eq(5).html('-');
                        layer.msg('已归还！');
                    }
                }, 'json');
            });
        },
        delComment: function() {
            var par = $(this).parent().parent();
            var id = par.data('id');
            layer.confirm('确定删除这条书评？', function() {
                $.post('./api', {id: id, type: 'delComment'}, function(data) {
                    if (data.res == 'error') {
                        layer.msg(data.msg);
                    } else {
                        par.fadeOut();
                        layer.msg('删除成功！');
                    }
                }, 'json');
            });
        },
        editUser: function() {
            var par = $(this).parent().parent();
            var id = par.data('id');
            $('input[name="uAccount"').val(par.children('td').eq(1).html());
            $('input[name="uName"').val(par.children('td').eq(2).html());
            $('input[name="uPwd"').attr({'lay-verify': '', 'placeholder': '如不更改无需输入 [6-20 位]'});
            $('.radioUType').children('div').eq(par.children('td').eq(3).data('id')).click();
            $('input[name="uid"]').val(id);
            $('#user input[name="isEdit"]').val('1');
            $('#userHandle').html('修改').click();
        }
    };

    // 按钮
    $('.lib-btn').on('click', function() {
        var type = $(this).data('type');
        btnEvent[type] ? btnEvent[type].call(this) : '';
    });

    // 浏览器兼容检查
    if (layui.device().ie && layui.device().ie < 8) {
        layer.alert('拒绝给浏览器版本低于 IE8 的用户提供服务。', {title: '冥顽不顾', icon: 2, move: false});
    }

    // 右下角工具块
    layui.util.fixbar({
        bgcolor: '#009688'
    });

    // 通行证：登录 / 注册
    $('#passport').submit(function() {
        $.post('./api', $(this).serialize() + '&type=passport', function(data) {
            if (data.res == 'error') {
                layer.msg(data.msg, function() {
                    if ($('input[name="isRegister"]').val() == 0) {
                        $('input[name="uPwd"]').val('').focus();
                    }
                });
            } else {
                layer.msg('欢迎~', {time: 2000}, function() {
                    location.href = '.' + decodeURIComponent(location.search.substring(1));
                });
            }
        }, 'json');
        return false;
    });

    // 切换注册
    $('.register').on('click', function() {
        var flag = $('input[name="isRegister"]'),
            uAccount = $('input[name="uAccount"]'),
            uName = $('input[name="uName"]'),
            uPwd = $('input[name="uPwd"]');
        if (flag.val() == 0) {
            flag.val(1);
            $('.page-title').html('注册');
            uAccount.attr('placeholder', 'xiaoming [5-30 位]');
            uName.attr('lay-verify', 'required');
            uPwd.attr('placeholder', '6-20 位');
            $('.register').html('我已有帐号>>');
            $('div.uname').fadeIn();
        } else {
            flag.val(0);
            $('.page-title').html('登录');
            uAccount.attr('placeholder', '');
            uName.attr('lay-verify', '');
            uPwd.attr('placeholder', '');
            $('.register').html('没帐号？');
            $('div.uname').fadeOut();
        }
    });

    // 搜索
    $('#search').submit(function() {
        if ($('input[name="so"]').val() == "") {
            layer.msg('没输入内容！');
            return false;
        }
    });

    // 发表书评
    $('#comment').submit(function() {
        $.post('./api', $(this).serialize() + '&type=addComment', function(data) {
            if (data.res == 'error') {
                layer.msg(data.msg);
            } else {
                layer.msg('发表成功', {time: 2000}, function() {
                    console.log($('.comment > p').length);
                    if ($('.nothing').length == 1) {
                        $('.nothing').fadeOut();
                    }
                    $('<div class="comment-list"><span>' + $('.layui-nav .layui-this a').text() + '</span><span>刚刚</span><p>' + $('textarea[name="content"]').val() + '</p></div>').hide().appendTo('.comment').fadeIn();
                    $('textarea[name="content"]').val('');
                });
            }
        }, 'json');
        return false;
    });

    // 图书处理
    $('#book').submit(function() {
        if ($('#book input[name="isEdit"]').val() != '1' && $('#book input[name="bPicRes"]').val() == '') {
            layer.msg('还没上传封面呢！')
            return false;
        }
        $.post('./api', $(this).serialize() + '&type=bookHandle', function(data) {
            if (data.res == 'error') {
                layer.msg(data.msg);
            } else {
                $('.layui-form-mid.color-blue').fadeOut();
                $('#book input[name="bPicRes"]').val("");
                layer.msg($('#bookHandle').html() + '成功', {time: 2000});
                $('#book button[type="reset"]').click();
            }
        }, 'json');
        return false;
    });

    // 重置按钮
    $('#book button[type="reset"]').on('click', function() {
        if ($('#book input[name="isEdit"]').val() == '1') {
            $('#book input[name="isEdit"]').val('0');
            $('.layui-form-mid.color-blue').hide().html('已上传。');
            $('#bookHandle').html('添加');
        }
    });
    $('#user button[type="reset"]').on('click', function() {
        if ($('#user input[name="isEdit"]').val() == '1') {
            $('#user input[name="isEdit"]').val('0');
            $('#user input[name="uid"]').val('');
            $('#userHandle').html('添加');
        }
    });

    // 上传图书封面
    layui.upload({
        url: './api?type=bookPic',
        before: function () {
            layer.msg('Loading...');
        },
        success: function (res) {
            $('input[name="bPicRes"]').val(res.msg);
            $('.layui-form-mid.color-blue').fadeIn();
            layer.msg('上传成功', {time: 2000});
        }
    });

    // 用户处理
    $('#user').submit(function() {
        $.post('./api', $(this).serialize() + '&type=userHandle', function(data) {
            if (data.res == 'error') {
                layer.msg(data.msg);
            } else {
                layer.msg($('#userHandle').html() + '成功', {time: 2000}, function() {
                    location.href = './admin';
                });
            }
        }, 'json');
        return false;
    });

    // 用户设置
    $('#userSet').submit(function() {
        $.post('./api', $(this).serialize() + '&type=userSet', function(data) {
            if (data.res == 'error') {
                layer.msg(data.msg);
            } else {
                $('#user button[type="reset"]').click();
                layer.msg('修改成功', {time: 2000}, function() {
                    location.href = './user';
                });
            }
        }, 'json');
        return false;
    });

    exports('global', {});
});