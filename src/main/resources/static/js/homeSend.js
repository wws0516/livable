// $("#nav div").click(function () {
//     console.log(1)
//     this.css({'color':'red'})
// })

// var
//     tv=0,
//     refrigerator=0,
//     washingMachine=0,
//     airCondition=0,
//     wifi=0,
//     beds=0,
//     waterHeater=0,
//     chest=0,
//     desk=0,
//     independentBathroom=0,
//     independentBalcony=0,
//     smartSock=0,
//     selfDecorating=0,
//     firstRent=0,
//     fullyFurnished=0,
//     nearbySubway=0,
//     anyTimeToSee=0,
//     checkInAtOnce=0,
//     cooking=0
// ;
var data = {
    tv : '0',
    refrigerator : '0',
    washingMachine : '0',
    airCondition : '0',
    wifi : '0',
    beds : '0',
    waterHeater : '0',
    chest : '0',
    desk : '0',
    independentBathroom : '0',
    independentBalcony : '0',
    smartSock : '0',
    selfDecorating : '0',
    firstRent : '0',
    fullyFurnished : '0',
    nearbySubway : '0',
    anyTimeToSee : '0',
    checkInAtOnce : '0',
    cooking : '0',
    rentWay : '月',
    picture:'',
    rentType:'整租'
};



var index = 0
var nav = $('#nav').find("div")
nav.click(function () {
    $(this).css("color","#e88500").siblings().css("color","black")
    console.log($(this).index())
    index = $(this).index()
    show(index)
})
function show(index) {
    var  mid = $("#mid")
    if (index == 0){
        $("#0").addClass('active').siblings().removeClass('active')
    }
    if (index == 1){
        $("#1").addClass('active').siblings().removeClass('active')
    }
    if (index == 2){
        $("#2").addClass('active').siblings().removeClass('active')
    }
}
$(".next").click(function () {
    index = index+1;
    show(index)
    nav.eq(index).css("color","#e88500").siblings().css("color","black")
    if (index>2){
        //Ajax
            data.title  = $("#title").val(),
            data.city  = $("#choseCity").val(),
            data.region  = $("#choseCity").val(),
            data.address  = $("#address").val(),
            data.houseType = $("#room").val()+'室'+$("#living").val()+'厅'+$("#bath").val()+'卫',
            data.rent  = $("#rent").val(),
            data.numberOfPeople  = $("#numberOfPeople").val(),
            data.rentWay = $("#rentWay").val(),
            data.numberOfPeople = $("#numberOfPeople").val(),
            data.elevator = $("#elevator").val(),
            data.toward  = $("#toward").val(),
            data.carport = $("#carport").val(),
            data.energyCharge = $("#energyCharge").val(),
            data.toward  = $("#waterCharge").val()

        for (let p in data){
            if (data[p]==''){
                return PromptBox.displayPromptBox('请完善信息')
            }
        }
        console.log('ok')
        $.ajax({
            url: 'http://114.115.156.4:8001/house/insert',
            async:true,
            type: 'POST',
            data: data,
            dataType: 'json',
            success: function (res) {
                if (code == 200){
                    PromptBox.displayPromptBox('发布成功')
                    setTimeout(function () {
                        $(location).attr('href','../html/myHome.html')
                    },2000)
                }
            }

        })
    }
})


$("#hold").click(function () {
    $("#checkFirst").addClass('active')
    $("#checkSecond").removeClass('active')
    data.rentWay = '整租'
});
$("#cooperate").click(function () {
    data.rentWay = '合租'
    $("#checkSecond").addClass('active');
    $("#checkFirst").removeClass('active')
});

$("#furniture").find('div').click(function () {
    let id = this.id

    if ($(this).hasClass('furnitureAdd')){
        $(this).removeClass('furnitureAdd')
        return data[id] = '0'
    } else {
        $(this).addClass('furnitureAdd')
        return data[id] = '1'
    }
});
$("#charact").find('div').click(function () {
    let id = $(this).id
    if ($(this).hasClass('furnitureAdd')){
        $(this).removeClass('furnitureAdd')

        return data[id] = '0'
    } else {
        $(this).addClass('furnitureAdd')
        return data[id] = '1'
    }
});



$('body').eq(0).click(function () {
    if (event.target!=$('#choseCity')[0]&&event.target!=$('.choseCity')[0]&&event.target.tagName!='LI'){
        $('.choseCity').eq(0).addClass('hidden');
    }

})
$('#choseCity').focus(function () {
    $('.choseCity').eq(0).removeClass('hidden');
})



function changepic() {
    var reads = new FileReader();
    f = document.getElementById('upImg').files[0]
    let img=new FormData();
    img.append('file',f);

    $.ajax({
        url:'http://114.115.156.4:9494/oss/uploadFile',
        type: 'put',
        dataType: 'json',
        contentType:false,
        data:img,
        processData:false,
        success(res){
            data.picture = res.httpUrl
            console.log(data.picture)
        }
    })

    reads.readAsDataURL(f);
    reads.onload = function(e) {
        document.getElementById('imgShow').src = this.result;
    };
}

$("#upVideo").change(function() {
    upload_file = this.files[0];
    var readerVideo = new FileReader();
    readerVideo.readAsDataURL(upload_file)
    readerVideo.onload = function (e) {
        console.log(url)
        $("#videoShow").attr("src", url);
    }
});