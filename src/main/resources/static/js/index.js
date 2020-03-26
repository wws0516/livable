
$(document).ready(function () {
    const URL='';

    let IndexInformation={
        timeInner:[],
        timeInnerKey:false,
    }

    IndexInformation.getLocation=function () {
        var cityCode=returnCitySN.cid;              //获取城市
        console.log(returnCitySN.cid);
        var cityName=returnCitySN.cname.split("省")[1];
        if (returnCitySN.cname){
            $('#city')[0].innerHTML=cityName;
        } else {
            $('#city')[0].innerHTML='北京';
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

    $('body').eq(0).click(function () {
        console.log(event);
        if (event.target!=$('#choseCity')[0]&&event.target!=$('.choseCity')[0]&&event.target.tagName!='LI'){
            console.log(event.target.tagName,$('#choseCity')[0])
            $('.choseCity').eq(0).addClass('hidden');
        }

    })
    $('#choseCity').focus(function () {
        $('.choseCity').eq(0).removeClass('hidden');
    })
    $('#toFindHome').click(function () {
        $('#toFindHome').addClass('rubberBand');
        $.cookie('lie',$('#choseCity'));
        $.cookie('time',$('#time'));
        window.location.href='./findHome.html';
    })
    $('#toFindHome').on('animationend',function () {
        $('#toFindHome').removeClass('rubberBand')
    })
    $('#time').click(function () {
        IndexInformation.timeInnerKey=true;
        $('#time').change(function () {
            if (IndexInformation.timeInnerKey){
                if (!IndexInformation.timeInner[0]){
                    IndexInformation.timeInner.push($(this).val());
                    $(this).attr('placeholder',IndexInformation.timeInner[0]+' 请选择退房日期');
                    IndexInformation.timeInnerKey=false;
                    $(this).val('');
                }else {
                    IndexInformation.timeInner.push($(this).val());
                    IndexInformation.timeInnerKey=false;
                    $(this).val(IndexInformation.timeInner[0]+' '+IndexInformation.timeInner[1]);
                    IndexInformation.timeInner=[];
                }
            }
        })


    })

    $.fn.datetimepicker.dates['zh'] = {
        days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
        daysShort: ["日", "一", "二", "三", "四", "五", "六", "日"],
        daysMin: ["日", "一", "二", "三", "四", "五", "六", "日"],
        months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthsShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],
        meridiem: ["上午", "下午"],
        //suffix:      ["st", "nd", "rd", "th"],
        today: "今天"
    };
    $("#time").datetimepicker({
        language: 'zh',  //用自己设置的时间文字
        //weekStart: 1,  //一周从那天开始，默认为0，从周日开始，可以设为1从周一开始
        // startDate:"2018-5-20", //开始时间，可以写字符串，也可以直接写日期格式new Date(),在这之前的日期不能选择
        //endDate:"2018-6-20",
        //daysOfWeekDisabled: [0,4,6],  //一周的周几不能选
        todayBtn: 1,  //是否显示今天按钮，0为不显示
        autoclose: 1, //选完时间后是否自动关闭
        todayHighlight: 1,  //高亮显示当天日期
        startView: 2, //0从小时视图开始，选分;1	从天视图开始，选小时;2从月视图开始，选天;3从年视图开始，选月;4从十年视图开始，选年
        minView: 2,//最精确时间，默认0；0从小时视图开始，选分；1从天视图开始，选小时；2从月视图开始，选天；3从年视图开始，选月；4从十年视图开始，选年
        //maxView:4,  //默认值：4, ‘decade’
        //keyboardNavigation:true,  //是否可以用键盘方向键选日期，默认true
        forceParse: 0, //强制解析,你输入的可能不正规，但是它胡强制尽量解析成你规定的格式（format）
        format: 'yyyy-mm-dd',// 格式,注意ii才是分，mm或MM都是月
        minuteStep: 5, //选择分钟时的跨度，默认为5分钟
        //pickerPosition:"top-right",  // ‘bottom-left’，’top-right’，’top-left’’bottom-right’
        showMeridian: 0, //在日期和小时选择界面，出现上下午的选项,默认false
        // showSecond: false,
        // showMillisec: true,
        //timeFormat: 'hh:mm:ss:l',
        //bootcssVer: 3,

    })
    checkScreen();
})
// var User = JSON.parse()
if ($.cookie('User')){
    console.log($.cookie('User'))
    var User = JSON.parse($.cookie('User'))
    $(".login").css({'display':'none'})
    $('.hLogin').append(' '+User.principal.name)
} else{
    $(".hLogin").css({'display':'none'})
}

function homeSend() {
    // $.ajax({
    //     url: 'http://114.115.156.4:8001/getLandlordState',
    //     type: 'get',
    //     success(res){
    //         if (res.data == 555){
    //             PromptBox.displayPromptBox('未审核')
    //
    //         }
    //         if (res.data == 566){
    //             PromptBox.displayPromptBox('审核未通过，请重新审核')
    //             setTimeout(function () {
    //                 $(location).attr('href','../html/LandlordInformation.html')
    //             },2000)
    //         }
    //         if (res.data == 567){
    //                 $(location).attr('href','../html/homeSend.html')
    //         }
    //         console.log(res)
    //     }
    // })
    $(location).attr('href','../html/LandlordInformation.html')
}

