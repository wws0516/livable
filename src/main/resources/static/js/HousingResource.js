var oTop = $(".minNav").offset().top;
//获取导航栏的高度，此高度用于保证内容的平滑过渡
var martop = $('.minNav').outerHeight();
var winWidth = 0;
var winHeight = 0;
var sTop = 0;
var H1 = $('.imfo').offset().top;
var alimfo = parseInt($('.imfo').css('height'));
var lastSay = $('.lastSay').offset().top;
var lastSayMore = lastSay-alimfo;
var leftHeight = $('.alLeft').height();
var imfoHeight = $('.imfo').height();
var positionHeight = $('.position').height();
var height = imfoHeight+positionHeight;
var position2Height = $('.position2').height();
var animateHeight =  imfoHeight+positionHeight;
var animateHeightMore = animateHeight+64;
var my = lastSayMore-positionHeight;
var a = imfoHeight+64;
var sayOne = $('#say1').offset().top;
var minNavHeight = $(".minNav").height();
var sayOneMore = sayOne-minNavHeight;
var nailImgTwo = $('.imgTwo');
var nail = $(".thumbNail2");
var index = 0;
var rightWidth = $('.alinfo').width()+20;
var windowWidth = document.body.clientWidth;
var lunboWidth = $(".lunbo").height();

var locat = location.href
var locatLength = locat.length
var loca = locat.indexOf('=')
var houseId = locat.substr(loca+1,locatLength)
// $(window).resize(function () {
//     if (windowWidth<=1370){
//         $(".lunbo").css({"height":"350px"})
//     }
// });
$(window).ready(function () {


    console.log(locat.substr(loca+1,locatLength))
    $.ajax({
        type: 'get',
        url: URL + '/house/getOneHouse',
        contentType: 'application/x-www-form-urlencoded',
        dataType: 'json',
        async: true,
        data :{
            houseID:locat.substr(loca+1,locatLength)
        },
        success: function (result) {
            console.log(result);
            $('.H1').html(result.data.title);
            $(".weizhi").html(result.data.city+result.data.region+result.data.address);
            $(".money").html("￥"+result.data.rent+"/月")
            $("#houseType").html(result.data.houseType)
            $("#rentType").html(result.data.rentType)
            $("#toward").html(result.data.toward)
            $("#elevator").html(result.data.elevator)
            $("#acreage").html(result.data.acreage)
            $("#img1").attr("src",result.data.picture)
            $("#picture1").attr("src",result.data.picture)
            $(".ownerSayText").html(result.data.introduction)
            if (result.data.allocation)
            console.log(result.data.feature)
            let homeFeature = result.data.feature
            console.log(homeFeature)
            for (let a in homeFeature){
                if (homeFeature[a] == 1){
                    console.log(a)
                    let serve = feature(a)
                    console.log(serve)
                    let serv =  '<td><div class="gn">'+serve+'</div></td>'
                    $("#serve").append(serv)
                }
            }


        },
        error: function () {
            alert('服务器开小差啦');
        }
    })
    function feature(a){
        switch (a) {
            case '独立卫浴':return 'independentBathroom';
            case '独立阳台':return 'independentBalcony';
            case '智能锁':return 'smartSock';
            case '可自行装修':return 'selfDecorating';
            case '首次出租':return 'firstRent';
            case '可立即入住':return 'fullyFurnished';
            case '地铁十分钟':return 'independentBathroom';
            case '随时看房':return 'anyTimeToSee';
            case '随时入住':return 'checkInAtOnce';
            case 'independentBathroom':return '独立卫浴';
            case 'independentBalcony':return '独立阳台';
            case 'smartSock':return '智能锁';
            case 'selfDecorating':return '可自行装修';
            case 'firstRent':return '首次出租';
            case 'fullyFurnished':return '拎包入住';
            case 'independentBathroom':return '地铁十分钟';
            case 'anyTimeToSee':return '随时看房';
            case 'checkInAtOnce':return '随时入住';
        }
    }
    // console.log("1")
    // let data=new FormData();
    // console.log("2")
    // new Interactive({
    //     childPath:'GET /getAllHouse',
    //     method:'GET',
    //     detail:data,
    //     isFile:true,
    //     successCallback:function (result) {
    //         console.log(3)
    //     },
    //     errorCallback:function () {
    //         console.log(4)
    //     },
    // }).init();


    if (windowWidth<=730){
        $(".lunbo").css({"height":"130px"})

    } else
    if (windowWidth<=935){
        $(".lunbo").css({"height":"180px"})
    } else
    if (windowWidth<=1150){
        $(".lunbo").css({"height":"230px"})
    } else
    if (windowWidth<=1220){
        $(".lunbo").css({"height":"280px"})
    } else
        if (windowWidth<=1370){
        $(".lunbo").css({"height":"330px"})
    } else
        {
            $(".lunbo").css({"height":"390px"})
        }
    var slider = $(".lunbo");
    var len = slider.find("li").length;
    $(".lunboLeft").click(function(){
        if(index==0){
            index=len
        }else{
            index -= 1
        }
        showImg(index)
    });
    $(".lunboRight").click(function(){
        if(index==len-1){
            index=0
        }else {
            index += 1
        }
        showImg(index)
    });
    $(".smallImg li").click(function () {
        index = $(this).index()
        showImg(index)
    })
    function showImg(index){
        $(".lunbo li").eq(index).show().siblings("li").hide();
        $(".smallImg").find("li").eq(index).addClass("ACTIVE").siblings("li").removeClass("ACTIVE");
    }

    // $(".thumbNail2").text(nailImgTwo);
    $("#say1").onclick=function(){
        (scrollTo(sayOneMore))
    };
    if(nailImgTwo.show())
    {
        $(".thumbNailTwo").css({"opacity":"1"});
    }else {
        $(".thumbNailTwo").css({"opacity":"0.6"});
    }
    // $(".exa>div:nth-child(1)").click(function () {
    //     $(".exa>div:nth-child(1)").find("span").addClass("glyphicon glyphicon-heart change").removeClass("glyphicon glyphicon-heart-empty")
    // })
    $(".exa>div:nth-child(1)").click(function () {
        $(".exa>div:nth-child(1)>span:nth-child(1)").css({"display":"none"})
        $(".exa>div:nth-child(1)>span:nth-child(2)").css({"display":"contents"})
    })

});

$(window).scroll((function () {
    sTop = $(this).scrollTop();
    if (sTop >= my)
    {
        $(".imfo").css({"position": "absolute",  "top":"auto" ,"bottom":"0"});
    }
    else if (sTop >= H1)
    {
        $(".imfo").stop(true);
        $(".imfo").animate({"height":650+'px'},"1","swing","callback");
        $(".imfo").css({"position": "fixed", "top" : "0", "height":height+'px' ,"width":rightWidth + "px"});
        $(".position").slideDown();
        $(".position2").css({"display":"none"});
    } else
    {
        $(".imfo").css({"position":"relative"});
        $(".position").slideUp();
        $(".imfo").stop(true);
        $(".imfo").animate({"height":a+'px'},"1","swing","callback");
        $(".position2").css({"display":"block"});
    }
    if (sTop >= sayOne) {
        // 修改导航栏position属性，使之固定在屏幕顶端
        $(".minNav2").stop(true);
        $(".minNav2").css({"display":"block"});
        $(".minNav2").animate({"height":"34px"});
    } else {
        $(".minNav2").stop(true);
        $(".minNav2").css({"display":"none"});
        $(".minNav2").animate({"height":"0px"});
    }

}));
$("#time").click(function () {
    xvDate({
        'targetId':'time',//时间写入对象的id
        'triggerId':['time'],//触发事件的对象id
        'alignId':'time',//日历对齐对象
        'format':'-',//时间格式 默认'YYYY-MM-DD HH:MM:SS'
        'min':'2020-03-20 10:00:00',//最大时间
        'max':'2099-10-30 10:00:00'//最小时间
    });
})
$(".lookButton").click(function () {
    let  time = $("#time").val()
    let data = {
        houseId:houseId,
        data:time.slice(0,10),
        site:$("#site").val()
    }
    console.log(data)
    $.ajax({
        url:'http://114.115.156.4:8001/looking/insertLooking',
        type: 'post',
        data:data,
        success(res){
            if (res.code == 200){
                setTimeout(function () {
                    PromptBox.displayPromptBox('发送成功')
                },1000)
                if (res.code == 500) {
                    setTimeout(function () {
                        PromptBox.displayPromptBox('发送失败，已发送')
                    }, 1000)
                }
            }
        }
    })
})