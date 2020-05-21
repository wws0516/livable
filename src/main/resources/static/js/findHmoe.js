$(document).ready(function () {

    let information = function () {
        this.nowSort = '综合排序';
        this.condition = {
            lie: '',
            time: '',
            price: '',
            area: '',
            key: '',
            type: '',
            tag: [],
        }
    }

    information.prototype.getNewSort = function () {
        let information=new FormData();
        information.append('city',city);
        information.append('city',city);
        new Interactive({
            childPath:'/house/search',
            method:'POST',
            detail:information,
            successCallback:function (result) {
                console.log(result);
            },
            errorCallback:function () {
                console.log('服务器出现未知错误');
            },
        }).init();
    }
    information.prototype.resetTypeValue = function (target) {
        $("[name='whole']")[0].src = '../img/whole.png';
        $("[name='double']")[0].src = '../img/double.png';
        $("[name='bed']")[0].src = '../img/bed.png';
        $('.type').children().removeClass('chosed');
        $(target).addClass('chosed');
    }
    information.prototype.findByKey = function (key) {
        console.log(key);
        let mykey=new FormData();
        mykey.append('prefix',key);
        new Interactive({
            childPath:'/house/rent/autocomplete',
            method:'get',
            detail:mykey,
            successCallback:function (result) {
                let str='';
                for (let i of result){
                    str+='<li class="list-group-item">'+i+'</li>';
                }
                $('.searchPromptBox>ul').html(str);
                $('.searchPromptBox').removeClass('hidden')
            },
            errorCallback:function () {
            },
        }).init();
    }
    information.prototype.displayHome = function (homeInf) {
        $('#homeNumber')[0].innerHTML = homeInf.length;
        homeInf.forEach(onselfCard => $('.homeDeatil').eq(0).append("<div class=\" margin-top-md homeCard col-lg-10 animated bounceInLeft \" id='\"" + homeInf.homeId + "\"'>\n" +
            "                <div class=\"col-lg-12 aboutHouserOwner\">\n" +
            "                    <div class=\"pull-left\">\n" +
            "                        <img class=\"\" src=\"../img/houseOwner.png\">\n" +
            "                    </div>\n" +
            "                    <div class=\"pull-left\">\n" +
            "                        <span>" + homeInf.ownerName + "</span><br/>\n" +
            "                        " + homeInf.ownerInf + "\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <img class=\"pull-left\" src=\"../img/background2.jpg\">\n" +
            "                <div class=\"pull-left col-lg-6\">\n" +
            "                    <h4>" + homeInf.homeName + "<img class=\"love pull-right\" src=\"../img/love.png\"></h4>\n" +
            "                    <h5 class=\"margin-top-md\">" + homeInf.lieAndSize + "<sup>2</sup> | 东南 | 3室1厅2卫 </h5>\n" +
            "                    <p class=\"text-muted margin-top-sm\">发布时间:<span> 2019.09.07</span></p>\n" +
            "                    <div class=\"pull-left col-lg-7 margin-top-md tag\">\n" +
            "                        <label>智能锁</label>\n" +
            "                        <label>立即入住</label>\n" +
            "                    </div>\n" +
            "                    <div class=\"pull-right col-lg-5 margin-top-md money\"><span>" + homeInf.price + "</span></div>\n" +
            "\n" +
            "\n" +
            "                </div>\n" +
            "            </div>"))
    }
    information.prototype.creatTag = function () {
        let inner = '';
        let that=this;
        for (let i in this.condition) {
            console.log(this.condition.time)
            if (i != 'tag' && this.condition[i]) {

                inner += "<label class=\"label screeningConditions\">" + this.condition[i] + "<span class=\"glyphicon glyphicon-remove\"></span></label>"
            }
        }
        $('.tagList>div:last-child').html(' ');
        findHomeInf.condition.tag.forEach(value => inner += "<label class=\"label screeningConditions\">" + value + "<span class=\"glyphicon glyphicon-remove\"></span></label>");
        $('.tagList>div:last-child').append(inner);

        let tag=new FormData();
        tag.append('city',$.cookie('lie').split(' ')[1]);
        tag.append('region',$.cookie('lie').split(' ')[2]);
        tag.append('feature.independentBathroom','0');
        tag.append('feature.independentBalcony','0');
        tag.append('feature.smartSock','0');
        tag.append('feature.selfDecorating','0');
        tag.append('feature.firstRent','0');
        tag.append('feature.fullyFurnished','0');
        tag.append('feature.nearbySubway','0');
        tag.append('feature.anyTimeToSee','0');
        tag.append('feature.checkInAtOnce','0');

        for (let i in this.condition){
            if (this.condition[i]!='') {
                switch (i) {
                    case 'lie':;break;
                    case 'time':;break;
                    case 'price':switch (this.condition[i]) {
                        case '1000元以下':tag.append('priceBlock','*-1000');break;
                        case '1000-2000元':tag.append('priceBlock','1000-2000');break;
                        case '2000-3000元':tag.append('priceBlock','2000-3000');break;
                        case '3000元以上':tag.append('priceBlock','3000-*');break;
                    };break;
                    case 'area':switch (this.condition[i]) {
                        case '50m2以下':tag.append('acreageBlock','*-50');break;
                        case '50-80m2':tag.append('acreageBlock','50-80');break;
                        case '80-100m2':tag.append('acreageBlock','80-100');break;
                        case '100-120m2':tag.append('acreageBlock','100-120');break;
                        case '120m2以上':tag.append('acreageBlock','120-*');break;
                    };break;
                    case 'key':tag.append('keyWords',this.condition[i]);break;
                    case 'type':tag.append('rentWay',this.condition[i]);break;
                    case 'tag':for (let x of this.condition.tag){

                            tag.set('feature.'+this.tagInterpret(x.replace(' ','')),'1');
                    }
                }
            }
        }
        new Interactive({
            childPath:'/house/search',
            method:'POST',
            detail:tag,
            successCallback:function (result) {
                $('.homeDeatil').eq(0).html('<div class="col-lg-12 ">\n' +
                    '                <div>已为您找到<span id="homeNumber">'+(()=>{if (result.data){return result.data.length} return 0 })()+'</span>套房</div>\n' +
                    '                <div class="">\n' +
                    '                    <button class="sort chosedSort">综合排序</button>\n' +
                    '                    <button class="sort">评价最高</button>\n' +
                    '                    <button class="sort">价格<img src="../img/priceSort.png"></button>\n' +
                    '                </div>\n' +
                    '            </div>');
                let str='';
                for (let i of result.data){
                    let tag='';
                    for (let d in i.feature) {
                        i.feature[d]&&that.tagInterpret(d)?tag+='                        <label>'+that.tagInterpret(d)+'</label>\n':void(0);
                    }
                    str+='<div class=" margin-top-md homeCard col-lg-10 col-md-10 col-sm-12 animated bounceInLeft " id="'+i.houseId+'">\n' +
                        '                <div class="col-lg-12 col-sm-12 aboutHouserOwner">\n' +
                        '                    <div class="pull-left">\n' +
                        '                        <img class="" src="../img/houseOwner.png">\n' +
                        '                    </div>\n' +
                        '                    <div class="pull-left">\n' +
                        '                        <span>房东昵称</span><br/>\n' +
                        '                        90后 | 白羊座 | 设计师\n' +
                        '                    </div>\n' +
                        '                </div>\n' +
                        '                <img class="pull-left" src="'+i.picture+'">\n' +
                        '                <div class="pull-left col-lg-6">\n' +
                        '                    <h4>'+i.title+' <img class="love pull-right animated" src="../img/love.png"></h4>\n' +
                        '                    <h5 class="margin-top-md">'+i.region+' | '+i.acreage+'m<sup>2</sup> | '+i.toward+' | '+i.houseType+' </h5>\n' +
                        '                    <p class="text-muted margin-top-sm">发布时间:<span> 2019.09.07</span></p>\n' +
                        '                    <div class="pull-left col-lg-7 margin-top-md tag">\n' +
                                             tag +
                        '                    </div>\n' +
                        '                    <div class="pull-right col-lg-5 margin-top-md money"><span>'+i.rent+'/月</span></div>\n' +
                        '\n' +
                        '\n' +
                        '                </div>\n' +
                        '            </div>';
                }
                $('.homeDeatil').eq(0).append(str);
                $('.love').click(function () {
                    that.loved($(event.path[3]).attr('id'));
                })
                $('.homeCard').click(function () {
                    if (!/love/.test($(event.target).attr('class'))){
                        for (let i of event.path){
                            if (/homeCard/.test($(i).attr('class'))) {
                                that.toDetail($(i).attr('id'));
                            }
                        }
                    }

                })
            },
            errorCallback:function () {
                console.log('服务器出现未知错误');
            },
        }).init();
    }
    information.prototype.loved = function (homeId) {
        let information=new FormData();
        information.append('houseId',homeId);
        // information.append('userId','1');
        // $.click('userId')
        new Interactive({
            childPath:'/house/addHouseToLike',
            method:'GET',
            detail:information,
            isFile:true,
            successCallback:function (result) {
                let aim = event.target;
                aim.src = '../img/loved.png';
                $(aim).addClass('rubberBand');
                $('.love').on('animationend', function () {
                    $(aim).removeClass('rubberBand');
                })
            },
            errorCallback:function () {

            },
        }).init();
    }
    information.prototype.tagInterpret=function(value){
        console.log(value);
        switch (value) {
            case '独立卫浴':return 'independentBathroom';
            case '独立阳台':return 'independentBalcony';
            case '智能锁':return 'smartSock';
            case '可自行装修':return 'selfDecorating';
            case '首次出租':return 'firstRent';
            case '可立即入住':return 'fullyFurnished';
            case '地铁十分钟':return 'nearbySubway';
            case '随时看房':return 'anyTimeToSee';
            case '随时入住':return 'checkInAtOnce';
            case 'independentBathroom':return '独立卫浴';
            case 'independentBalcony':return '独立阳台';
            case 'smartSock':return '智能锁';
            case 'selfDecorating':return '可自行装修';
            case 'firstRent':return '首次出租';
            case 'fullyFurnished':return '拎包入住';
            case 'nearbySubway':return '地铁十分钟';
            case 'anyTimeToSee':return '随时看房';
            case 'checkInAtOnce':return '随时入住';
        }
    }
    information.prototype.toDetail=function(id){
        // $.cookie('houseId',id);
        // window.location.href='fangyuan.html';
        location.href='../html/fangyuan.html?'+'id=' + id;
    }
    information.prototype.findHomeByCity=function(){
        let that=this;
        let mykey=new FormData();
        mykey.append('city',$.cookie('lie').split(' ')[1]);
        mykey.append('region',$.cookie('lie').split(' ')[2]);
        new Interactive({
            childPath:'/house/search',
            method:'post',
            detail:mykey,
            successCallback:function (result) {
                $('.homeDeatil').eq(0).html('<div class="col-lg-12 ">\n' +
                    '                <div>已为您找到<span id="homeNumber">'+(()=>{if (result.data){return result.data.length} return 0 })()+'</span>套房</div>\n' +
                    '                <div class="">\n' +
                    '                    <button class="sort chosedSort">综合排序</button>\n' +
                    '                    <button class="sort">评价最高</button>\n' +
                    '                    <button class="sort">价格<img src="../img/priceSort.png"></button>\n' +
                    '                </div>\n' +
                    '            </div>');
                let str='';
                for (let i of result.data){
                    let tag='';
                    for (let d in i.feature) {
                        i.feature[d]&&that.tagInterpret(d)?tag+='                        <label>'+that.tagInterpret(d)+'</label>\n':void(0);
                    }
                    str+='<div class=" margin-top-md homeCard col-lg-10 col-md-10 col-sm-12 animated bounceInLeft " id="'+i.houseId+'">\n' +
                        '                <div class="col-lg-12 col-sm-12 aboutHouserOwner">\n' +
                        '                    <div class="pull-left">\n' +
                        '                        <img class="" src="../img/houseOwner.png">\n' +
                        '                    </div>\n' +
                        '                    <div class="pull-left">\n' +
                        '                        <span>房东昵称</span><br/>\n' +
                        '                        90后 | 白羊座 | 设计师\n' +
                        '                    </div>\n' +
                        '                </div>\n' +
                        '                <img class="pull-left" src="'+i.picture+'">\n' +
                        '                <div class="pull-left col-lg-6">\n' +
                        '                    <h4>'+i.title+' <img class="love pull-right animated" src="../img/love.png"></h4>\n' +
                        '                    <h5 class="margin-top-md">'+i.region+' | '+i.acreage+'m<sup>2</sup> | '+i.toward+' | '+i.houseType+' </h5>\n' +
                        '                    <p class="text-muted margin-top-sm">发布时间:<span> 2019.09.07</span></p>\n' +
                        '                    <div class="pull-left col-lg-7 margin-top-md tag">\n' +
                        tag +
                        '                    </div>\n' +
                        '                    <div class="pull-right col-lg-5 margin-top-md money"><span>'+i.rent+'/月</span></div>\n' +
                        '\n' +
                        '\n' +
                        '                </div>\n' +
                        '            </div>';
                }
                $('.homeDeatil').eq(0).append(str);
                $('.love').click(function () {
                    that.loved($(event.path[3]).attr('id'));
                })
                $('.homeCard').click(function () {
                    if (!/love/.test($(event.target).attr('class'))){
                        for (let i of event.path){
                            if (/homeCard/.test($(i).attr('class'))) {
                                that.toDetail($(i).attr('id'));
                            }
                        }
                    }

                })
            },
            errorCallback:function () {
            },
        }).init();
    }

    let findHomeInf = new information();
    findHomeInf.findHomeByCity();


    $('button').click(function () {
        if (event.target.classList[0] != 'sort'&&event.target.innerText!='地图找房') {
            console.log(event)
            if ($(event.path[2]).children('div').eq(0).text() != '出租类型' && $(event.path[3]).children('div').eq(0).text() != '出租类型') {
                if(/chosed/.test($(event.target).attr('class'))){
                    $(event.path[1]).children('button').removeClass('chosed')
                    switch ($(event.target).parent().prev().text()) {
                        case '价格':
                            findHomeInf.condition.price = '';
                            findHomeInf.creatTag();
                            break;
                        case '面积':
                            findHomeInf.condition.area = '';
                            findHomeInf.creatTag();
                            break;
                    }
                }else {
                    $(event.path[1]).children('button').removeClass('chosed');
                    $(event.target).addClass('chosed');
                    switch ($(event.target).parent().prev().text()) {
                        case '价格':
                            findHomeInf.condition.price = event.target.innerText;
                            findHomeInf.creatTag();
                            break;
                        case '面积':
                            findHomeInf.condition.area = event.target.innerText;
                            findHomeInf.creatTag();
                            break;
                    }
                };
                console.log($(event.target).parent().prev().text());

            } else {
                let target;
                event.target.tagName == 'IMG' ? target = event.path[1] : target = event.target;
                console.log($(target).children('img')[0].name);
                switch ($(target).children('img')[0].name) {
                    case 'whole':
                        findHomeInf.resetTypeValue(target);
                        $(target).children('img')[0].src = '../img/whole-.png';
                        findHomeInf.condition.type = '整租';
                        findHomeInf.creatTag();
                        break;
                    case 'bed':
                        findHomeInf.resetTypeValue(target);
                        $(target).children('img')[0].src = '../img/bed-.png';
                        findHomeInf.condition.type = '床位';
                        findHomeInf.creatTag();
                        break;
                    case 'double':
                        findHomeInf.resetTypeValue(target);
                        $(target).children('img')[0].src = '../img/double-.png';
                        findHomeInf.condition.type = '合租';
                        findHomeInf.creatTag();
                        break;
                }
            }
        } else {
            $(event.path[1]).children('button').removeClass('chosedSort')
            $(event.target).addClass('chosedSort');
            findHomeInf.nowSort = $(event.target).text();
            findHomeInf.getNewSort()
        }

    })
    $('#findByKey').parent().click(function () {
        findHomeInf.condition.key=$('[name="findByKey"]').val();
        findHomeInf.creatTag();
    })
    $(".queryCriteria>div:nth-last-child(2)").click(function () {
        if (event.target.tagName == 'INPUT') {
            let key = true;
            let val;
            val = $(event.target).parent()[0].innerText;
            for (let i = 0, l = findHomeInf.condition.tag.length; i <= l; i++) {
                if (findHomeInf.condition.tag[i] == val) {
                    findHomeInf.condition.tag.splice(i, 1);
                    key = false;
                    break;
                }
            }
            key ? findHomeInf.condition.tag.push(val) : void(0);
            // key?findHomeInf.condition.tag[0]?findHomeInf.condition.tag.push(val):findHomeInf.condition.tag[0]=val:void(0);
            console.log(event.target, findHomeInf.condition.tag);
            findHomeInf.creatTag();
            console.log(findHomeInf);
        }

    })
    $('#starttime').change(function () {

        findHomeInf.condition.time=$('#starttime').val();
        findHomeInf.creatTag();
    })
    $('[name="findByKey"]').bind("input propertychange", function(){
        findHomeInf.findByKey($(event.target).val());
    });
    $('[name="findByKey"]').blur(function () {
        $('.searchPromptBox').addClass('hidden');
    })
    $('[name="findByKey"]').focus(function () {
        $('.searchPromptBox').removeClass('hidden');
    })
    $('.searchPromptBox>ul').mousedown(function () {
        console.log(event);
        $('[name="findByKey"]').val($(event.target).text());
        $('.searchPromptBox').addClass('hidden');
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
    $("#starttime,#endtime").datetimepicker({
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
})