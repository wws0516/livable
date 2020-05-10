// JavaScript Document
let serverURL='http://114.115.156.4:8001/';
var fileM=document.querySelector("#fileUp");
var fileM1=document.querySelector("#fileUp1");
var idCardPicZ='';
var idCardPicF='';
var oImg =document.getElementById('img-src');
oImg.src=" ";

$("#fileUp").on("change",function() {
    //获取文件对象，files是文件选取控件的属性，存储的是文件选取控件选取的文件对象，类型是一个数组
    var fileObj = fileM.files[0];
    //创建formdata对象，formData用来存储表单的数据，表单数据时以键值对形式存储的。
    var formData = new FormData();
    formData.append('file', fileObj);
    $.ajax({
		type: "put",
        url: "http://114.115.156.4:9494/oss/uploadFile",
        dataType: "json",
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (result) {
            PromptBox.displayPromptBox('加载成功。')
			idCardPicZ=result.httpUrl;
			
        }
    });
});

$("#fileUp1").on("change",function() {
    //获取文件对象，files是文件选取控件的属性，存储的是文件选取控件选取的文件对象，类型是一个数组
    var fileObj = fileM1.files[0];
    //创建formdata对象，formData用来存储表单的数据，表单数据时以键值对形式存储的。
    var formData = new FormData();
    formData.append('file', fileObj);
    $.ajax({
		type: "put",
        url: "http://114.115.156.4:9494/oss/uploadFile",
        dataType: "json",
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (result) {
            PromptBox.displayPromptBox('加载成功。')
			idCardPicF=result.httpUrl;
        },
    });
});

var nich=document.getElementById('nich');
var mark=document.getElementById('mark');
var type=document.getElementById('type');
var button=document.getElementById('butt');

console.log(nich.value);

$.ajax({
    type: 'get',
    url: serverURL + 'information/getIDInformation',
    contentType: 'application/x-www-form-urlencoded',
    dataType: 'json',
    async: true,
    data: {
        //city:city[2],
    },
    success: function (result) {
        if(result.code=='200'){
            //document.getElementById('Name').innerHTML=result.data.Name;
            console.log(result.data.name);
            nich.value=result.data.name;
            mark.value= result.data.idNumber;
            type.value= result.data.idCardType;
        }
        else {
            PromptBox.displayPromptBox(result.msg)
        }
    },
    error: function () {
        alert('服务器开小差啦');
    }
            })






button.onclick=function(){
    let nich1=nich.value;
    let	idNumber=mark.value;
    let	idCardType=type.value;
    if (!(idCardPicZ && idCardPicF)){
        return PromptBox.displayPromptBox('请上传证件照')
    }
    if (idNumber.length != 18){
        return PromptBox.displayPromptBox('请填写正确的证件号')
    }
    let ipt = document.getElementsByTagName('input')
    if (!(nich1&&idNumber&&idCardType)){
        return PromptBox.displayPromptBox('请将信息填写完整')
    }
    $.ajax({
        type: 'post',
        url: serverURL + 'information/IDInformation',
        contentType: 'application/x-www-form-urlencoded',
        dataType: 'json',
        async: true,
        data: {
            name:nich1,
            idNumber:idNumber,
            idCardType:idCardType,
            idCardPicZ:idCardPicZ,
            idCardPicF:idCardPicF
        },
        success: function (result) {
            if(result.code=='200'){
                // alert('成功啦');
                PromptBox.displayPromptBox('上传成功。')
                //document.getElementById('Name').innerHTML=result.data.Name;
            }
            console.log(result)
            // else alert(result.msg);
        },
        error: function () {
            alert('服务器开小差啦');
        }
    })
}


$.ajax({
    type: 'get',
    url: serverURL + 'information/getHeadPortrait',
    contentType: 'application/x-www-form-urlencoded',
    dataType: 'json',
    async: true,
    data: {
        //city:city[2],
    },
    success: function (result) {
        if(result.code=='200'){
            console.log(result)
            oImg.src=result.data;
            //document.getElementById('Name').innerHTML=result.data.Name;
        }
        else alert(result.msg);
    },
    error: function () {
        alert('服务器开小差啦');
    }
})

