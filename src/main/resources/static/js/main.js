const URL='http://114.115.156.4:8001';
const fileURL='http://114.115.156.4:9494';
let city='';
let CreatPromptBox =function(){
    let newNode=document.createElement('div');
    newNode.classList.add('PromptBox');
    newNode.style.display='none';
    document.body.appendChild(newNode);
    this.newNode=newNode;
};
CreatPromptBox.prototype.displayPromptBox=function(text){
    this.newNode.innerText=text;
    $(this.newNode).fadeIn(500);
    $(this.newNode).fadeOut(1500);
};

let PromptBox = new CreatPromptBox();

let checkScreen=function (){
    let IntvId=setInterval(function () {
        if (window.orientation == 180 || window.orientation == 0) {
            $('#horizontalScreenTips').eq(0).removeClass('hidden');
        }
        if (window.orientation == 90 || window.orientation == -90) {
            $('#horizontalScreenTips').eq(0).addClass('hidden');
        }
        window.clearInterval(IntvId);
    },1000)
}

let IndexInformation={

}
var  City = document.getElementById("city")
IndexInformation.getLocation=function () {
    if (City){
        var cityCode=returnCitySN.cid;              //获取城市
        var cityName=returnCitySN.cname.split("省")[1];

        if (returnCitySN.cname){
            $('#city')[0].innerHTML=cityName;
            if (document.getElementById('choseCity')){
                document.getElementById('choseCity').innerHTML="<img src=\"../img/lie.png\">"+cityName;
                city=cityName;
            }
        } else {
            $('#city')[0].innerHTML='北京';
        }
    }


}
IndexInformation.findHome=function () {
    let city=document.getElementById('city').innerText.split(" ");
    if (!city){
        $.ajax({
            type: 'post',
            url: URL + '/findHome',
            contentType: 'application/x-www-form-urlencoded',
            dataType: 'json',
            async: true,
            data: {
                city:city[2],
            },
            success: function (result) {
                if(result.code=='200'){
                    document.getElementById('Name').innerHTML=result.data.Name;
                }
                else alert(result.msg);
            },
            error: function () {
                alert('服务器开小差啦');
            }
        })
    }else {
        alert('请选择你的地址');
    }

}
IndexInformation.getLocation();



// ————————————————————————————————交互用便捷模块————————————————————————————————
// 使用模板如下：
// let img=new FormData();
// img.append('detail',value);
// img.append('file',element.files[0]);
// new Interactive({
//     childPath:'/oss/uploadFile',
//     method:'PUT',
//     detail:img,
//     isFile:true,
//     successCallback:function (result) {
//          doSomething();
//     },
//     errorCallback:function () {
//          doSomething();
//     },
// }).init();


let Interactive=function (inf) {
    this.Path=inf.childPath;
    this.Method=inf.method||'POST';
    this.detail=inf.detail;
    this.successCallback=inf.successCallback;
    this.errorCallback=inf.errorCallback;
    this.isFile=inf.isFile||false;
    return this;
}
Interactive.prototype={
    init:function () {
        let that=this;
        that.isFile?that.Path=fileURL+that.Path:that.Path=URL+that.Path;
        console.log(that.detail);
        switch (that.Method.toLowerCase()) {
            case 'get':(function myGet(){
                let formdataEntrise=that.detail.entries();
                let form={};
                for (let i of formdataEntrise){
                    form[i[0]]=i[1];
                }

                $.ajax({
                    type: that.Method,
                    url: that.Path,
                    contentType: 'application/x-www-form-urlencoded',
                    dataType: 'json',
                    async: true,
                    data: form,
                    success: function (result) {
                        console.log(result);
                        that.copeResult(result);
                    },
                    error: function () {
                        PromptBox.displayPromptBox('联系不上服务器啦 - 3 - ');
                    }
                })
            })();break;
            default:$.ajax({
                type: that.Method,
                url: that.Path,
                contentType:false,
                dataType: 'json',
                processData:false,
                async: true,
                data: that.detail,
                success: function (result) {

                    that.copeResult(result);
                },
                error: function (result) {
                    switch (result.responseJSON.code) {
                        case '401':PromptBox.displayPromptBox('请先前往登录页进行登录 - 3 - '),document.location.href='../html/login.html';break;
                        default:PromptBox.displayPromptBox('联系不上服务器啦 - 3 - ');break;
                    }

                }
            });break;
        }

        return this;
    },

    copeResult:function (result) {
        switch (result.code) {
            case '200':this.successCallback(result);break;
            case undefined:this.successCallback(result);break;
            default:PromptBox.displayPromptBox('出现了未知错误 ');break;
        }
    }
}



$(".center-block").find('a').eq(3).click(function () {
    $(location).attr('href','../html/LandlordInformation.html')
})

$(".center-block").find('a').eq(2).click(function () {
    $(location).attr('href','../html/HomePage.html')
})

if ($.cookie('User')){
    var User = JSON.parse($.cookie('User'))
    if (!$(location)[0].pathname.match(/index/g)&&!$(location)[0].pathname.match(/homeSend/g)){
        $(".signed").children('div').children('a')[0].text = User.principal.name
    }
    $("#quit").click(function () {
        $.removeCookie('User')
        $(location).attr('href','../html/login.html')
    })
    $.ajax({
        url:'http://114.115.156.4:8001/information/getHeadPortrait',
        type:'get',
        success(res){
            $(".headImg").attr('src',res.data)
        }
    })
}else{
    if($(".signed").length>0){
        console.log(1)
        $(".signed").children('div').children('a')[0].text = '登录'
        $(".surround").click(function () {
            $(location).attr('href','../html/login.html')
        })
        $(".center-block").find('a').eq(2).css({
            'display':'none'
        })
        $(".center-block").find('a').eq(3).css({
            'display':'none'
        })
        $(".signed").children('div').children('a')[1].text = ''
    }
}
