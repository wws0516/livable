
$(document).ready(function () {
    const URL='';

    let IndexInformation={

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
    })
    $('#toFindHome').on('animationend',function () {
        $('#toFindHome').removeClass('rubberBand')
    })
    checkScreen();
})