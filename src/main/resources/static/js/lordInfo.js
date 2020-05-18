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

$.ajax({
    url:'http://114.115.156.4:8001/information/getPersonalInformation',
    type:'get',
    success(res){
        if (res.code == 200){
            $("#phone").val(res.data.phone)
            $("#age").val(res.data.age)
            $("#hobby").val(res.data.hobby)
            $("#job").val(res.data.job)
            $("#name").val(res.data.name)
        }
        if (res.code == 500){
            PromptBox.displayPromptBox('请先登录。')
        }
    }
})


function upFile() {
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

    // reads.readAsDataURL(f);
    function change(url) {
        document.getElementById('headImg').src = url;
    };
}

$("#save").click(function () {
    let data = {
        phone:$("#phone").val(),
        age: $("#age").val(),
        job: $("#job").val(),
        hobby: $("#hobby").val(),
        headPortrait:picture
    }
    console.log(data)
    for ( let key in data){
        console.log(data[key])
        if (data[key] == ''){
            return PromptBox.displayPromptBox('请完善资料');
        }
    }
    $.ajax({
        url:'http://114.115.156.4:8001/information/personalInformation',
        type:'post',
        dataType:'json',
        data:data,
        success(res){
            if (res.code == 500) {
                PromptBox.displayPromptBox(res.msg);
            }
            PromptBox.displayPromptBox('上传成功');
        }
    })
})