


var data = {
    landlordId:'',
    idNumber:'',
    idCardPictureF:'',
    idCardPictureR:'',
    alipayName:'',
    alipayAccount:''
}

function upFile(a) {
    $("#addTrue").css({'display':'none'})
    var reads = new FileReader();
    f = document.getElementById('imgTrue').files[0]

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
            console.log(res)
            data.idCardPictureF = res.httpUrl
            change(data.idCardPictureF)
        }
    })

    // reads.readAsDataURL(f);
    function change(url) {
        console.log(url)
        $("#trueShow").attr('src',url)
        $(".trueIpt").css({'height':'auto'})
    };
}

function upfFile(a) {
    $("#addFalse").css({'display':'none'})
    var reads = new FileReader();
    f = document.getElementById('imgFalse').files[0]

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
            console.log(res)
            data.idCardPictureR = res.httpUrl
            change(data.idCardPictureR)
        }
    })

    // reads.readAsDataURL(f);
    function change(url) {
        console.log(url)
        $("#falseShow").attr('src',url)
        $(".falseIpt").css({'height':'auto'})
    };
}
$(".preservat").click(function () {
    if ($.cookie('User')){
        let user = JSON.parse($.cookie('User'))
        console.log(user)
        data.landlordId = user.principal.userId
        data.idNumber=$("#idNumber").val(),
        data.alipayName=$("#alipayName").val(),
        data.alipayAccount=$("#alipayAccount").val()
        for (let key in data){
            if (data[key] == ''){
                return PromptBox.displayPromptBox('请完善信息')
            }
        }



        $.ajax({
            url:'http://114.115.156.4:8001/registerLandlord',
            dataType:'json',
            type:'post',
            data:data,
            success(res){
                PromptBox.displayPromptBox(res.msg)
            }
        })
    }else{
        PromptBox.displayPromptBox('请先登录')
    }



})


var picture = '';

$.ajax({
    url:'http://114.115.156.4:8001/information/getHeadPortrait',
    type:'get',
    success(res){
        if (res.code == 500){
            PromptBox.displayPromptBox('请先登录。')
        }
        if (res.code == 200){
            let url = res.data.replace(/\"/g,'')
            console.log(url)
            document.getElementById('headImg').src = url;
            picture = url
        }
    }
})


function upImgFile() {
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
            picture = res.httpUrl
            console.log(picture)
            change(picture)
        }
    })
    var Url = new FormData()
    // reads.readAsDataURL(f);
    function change(url) {
        document.getElementById('headImg').src = url;
        Url.append('headPortrait',url)
        $.ajax({
            url:'http://114.115.156.4:8001/information/headPortrait',
            type:'post',
            dataType:'json',
            contentType: false,
            processData:false,
            data:Url,
            success(res) {
                console.log(res)
            }
        })
    };
}
