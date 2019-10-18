const URL='';
let CreatPromptBox =function(){
    let newNode=document.createElement('div');
    newNode.classList.add('PromptBox');
    newNode.style.display='none';
    document.body.appendChild(newNode);
    this.newNode=newNode;
}
CreatPromptBox.prototype.displayPromptBox=function(text){
    this.newNode.innerText=text;
    $(this.newNode).fadeIn(500);
    $(this.newNode).fadeOut(1500);
}

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

IndexInformation.getLocation=function () {
    var cityCode=returnCitySN.cid;              //获取城市
    var cityName=returnCitySN.cname.split("省")[1];
    if (returnCitySN.cname){
        $('#city')[0].innerHTML=cityName;
        if (document.getElementById('choseCity')){
            document.getElementById('choseCity').innerHTML="<img src=\"../img/lie.png\">"+cityName;

        }
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
