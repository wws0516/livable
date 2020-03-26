$(document).ready(function () {
    $("#ToRegist").click(function () {
        var inputO = document.getElementsByTagName("input")[0];
        var inputTw = document.getElementsByTagName("input")[1];
        var inputTr = document.getElementsByTagName("input")[2];
        if (inputO.value.length = 0){
            PromptBox.displayPromptBox('请输入正确的姓名');
            return false;
        } else if (inputTw.value.length = 0){
            PromptBox.displayPromptBox('请输入正确的密码');
            return false;
        } else if (inputTr.value.length = 0){
            PromptBox.displayPromptBox('请输入正确的验证码');
            return false;
        }
        let img=new FormData();
        img.append('username',$("#Email").val());
        img.append('password',$("#Password").val());
        img.append('emailCode',$("#EmailKey").val());
        new Interactive({
            childPath:'/user/login',
            method:'post',
            detail:img,
            isFile:false,
            successCallback:function (result) {
                console.log(result);
                $.cookie('User',JSON.stringify(result.data))
                $(location).attr('href','../html/index.html')
            },
            errorCallback:function () {
                console.log(222);
            },
        }).init();
    })

});