// $(".BMap_geolocationIcon").click()
var User = JSON.parse($.cookie('User'))
var locat = location.href
var locatLength = locat.length
var loca = locat.indexOf('=')
var houseId = locat.substr(loca+1,locatLength)
console.log(User.principal.userId)
$.ajax({
    url:URL+'/user/getRoommate',
    type:'get',
    data:{
        userId:User.principal.userId,
        houseId:houseId
    },
    success(res){
        console.log(res)
        let length = res.data.length
        console.log(length)
        for (let key = 0;key<length+1;++key){
            if (res.data[key]!=null){
                $("#roommate").append('<div class="col-sm-4 container-fluid roommates" id="'+'roommate'+key+'">\n' +
                    '                    <div class=" col-sm-4 col-xs-4 col-md-4 col-lg-4 roommateHead">\n' +
                    '                        <div class="roommate">\n' +
                    '                            <img src="../img/head_test.jpg" id="'+'roommateHead'+key+'">\n' +
                    '                        </div>\n' +
                    '                    </div>\n' +
                    '                    <table col-sm-8 col-xs-8 col-md-8 col-lg-8>\n' +
                    '                        <tr>\n' +
                    '                            <th class="name">爱好：</th>\n' +
                    '                            <th class="roommateInfo" id="'+'roommateHobby'+key+'">吃饭、睡觉、打豆豆</th>\n' +
                    '                        </tr>\n' +
                    '                        <tr>\n' +
                    '                            <th class="name">年龄：</th>\n' +
                    '                            <th class="roommateInfo" id="'+'roommateAge'+key+'">12</th>\n' +
                    '                        </tr>\n' +
                    '                        <tr>\n' +
                    '                            <th class="name">工作：</th>\n' +
                    '                            <th class="roommateInfo" id="'+'roommateWork'+key+'">吃饭、睡觉、打豆豆</th>\n' +
                    '                        </tr>\n' +
                    '                        <tr>\n' +
                    '                            <th class="name">电话：</th>\n' +
                    '                            <th class="roommateInfo" id="'+'roommatePhone'+key+'">17623811139</th>\n' +
                    '                        </tr>\n' +
                    '                    </table>\n' +
                    '                </div>')
                console.log(res.data[key].hobby)
                document.getElementById('roommateHobby'+key).innerText = res.data[key].hobby;
                document.getElementById('roommateAge'+key).innerText = res.data[key].age;
                document.getElementById('roommateWork'+key).innerText = res.data[key].job;
                document.getElementById('roommatePhone'+key).innerText = res.data[key].phone;
                document.getElementById('roommateHead'+key).src = res.data[key].headPortrait
            }
        }
    }
})
$.ajax({
    url:URL+'/getLandlordByHouseId',
    type:'post',
    data:{
        houseId:houseId
    },
    success(res) {
        if (res.code == 200){
            $(".name2").text = res.data.alipayName
            if (res.data.status!= 678){
                $(".eval3").html('未认证')
            }
        }
        if (res.code == 500){
            $(".name2").html('无此房东信息');
            $(".eval3").html('未认证')
        }
    }
})
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
        searchByStationName()
        if (result.data.allocation.tv == 0){
            $(".TV").css({
                'display':'none'
            })
        }
        if (result.data.allocation.wifi == 0){
            $(".wifi").css({
                'display':'none'
            })
        }
        if (result.data.allocation.airCondition == 0){
            $(".HeatingCooling").css({
                'display':'none'
            })
        }
        if (result.data.allocation.washingMachine == 0){
            $(".WashSupplies").css({
                'display':'none'
            })
        }
        if (result.data.allocation.refrigerator == 0){
            $(".HairDrierr").css({
                'display':'none'
            })
        }
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

$(window).ready(function () {
    var infoShow = 0;
    var infoClose = 0;
    var infoSHight = '';
    $(window).scroll((function () {
        sTop = $(this).scrollTop();
        let imfoTop = $(".imfo")[0].offsetTop+140;
        let leftH = $(".alLeft")[0].offsetHeight-infoSHight

        if (sTop >=leftH){
            if (infoClose == 0){
                infoClose = 1;
                $(".imfo").fadeOut()
            }
        }
        if (sTop <=leftH){
            if (infoClose == 1){
                $(".imfo").fadeIn()
                infoClose = 0
            }
        }
        if (sTop >= imfoTop){
            if (infoShow == 0&&infoClose == 0){
                infoSHight = $(".imfo")[0].offsetHeight
                infoShow = 1;
                $(".position").fadeIn()
                $(".imfo").css({
                    'position':'fixed'
                })
            }
        }
        if (sTop<imfoTop){
            if (infoShow == 1){
                infoShow = 0;
                console.log(444)
                $(".position").fadeOut()
                $(".imfo").css({
                    'position':'relative'
                })
            }
        }
    }))
})

$(".conllect").click(function () {
        $(".exa>div:nth-child(1)").find("span").addClass("glyphicon-heart change").removeClass("glyphicon-heart-empty")
        $(".exa>div:nth-child(1)").find("span").css({
            'color':'pink'
        })
        console.log(houseId)
        $.ajax({
            url:'http://114.115.156.4:8001/like',
            type:'post',
            data:{
                houseId:houseId
            },
            success(res){
                if (res.code == 200){
                    PromptBox.displayPromptBox('添加成功')
                }
                if (res.code == 500){
                    PromptBox.displayPromptBox(res.msg)
                }
            }
        })
})
