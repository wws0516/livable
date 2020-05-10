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

if ($.cookie('User')){
    var User = JSON.parse($.cookie('User'))
} else{
    PromptBox.displayPromptBox('请先登录')
}


$.ajax({
    url: 'http://114.115.156.4:8001/getLandlordState',
    type: 'get',
    success(res){
        if (res.data == 666){
            PromptBox.displayPromptBox('证件信息正在审核')
            setTimeout(function () {
                $(location).attr('href','../html/Certificates.html')
            },2000)
        }
        if (res.data == 677){
            PromptBox.displayPromptBox('审核未通过，请重新审核')
            setTimeout(function () {
                $(location).attr('href','../html/Certificates.html')
            },2000)
        }
        if (res.data == 678){
                homeShow()
            console.log(1)
        }
        console.log(res.data)
    }
})

function delect(obj){
    console.log(this.id)
    let id = this.id
    console.log(id)
    $.ajax({
        url: 'http://114.115.156.4:8001/house/deleteHouse',
        type: 'DELETE',
        dataType: 'json',
        data: {
            houseId:id
        },
        success(res){
            // $(location).attr('href','../html/myHome.html')
        }
    })
}

// $(".tdOne").click(function () {
//     console.log(this)
//     console.log($(this))
// })


function homeShow() {
    $.ajax({
        url:'http://114.115.156.4:8001/getRelationByUserId',
        dataType:'json',
        type:'get',
        data:{
            userId : User.principal.userId
        },
        success(res){
            for (let key in res.data){
                var EveryHome = function () {
                    let newNode = document.createElement('div');
                    let nodeSonO = document.createElement('div')
                    let nodeSonTw = document.createElement('div')
                    let nodeSonTh = document.createElement('div')
                    nodeSonO.classList.add('col-sm-6', 'col-md-6', 'col-lg-6', 'col-xs-6')
                    nodeSonTw.classList.add('col-sm-3', 'col-md-3', 'col-lg-3', 'col-xs-3')
                    nodeSonTh.classList.add('tdOne')
                    nodeSonTh.innerText = '删除'
                    nodeSonTh.addEventListener('click',delect)
                    newNode.classList.add('line','row')
                    newNode.appendChild(nodeSonO)
                    newNode.appendChild(nodeSonTw)
                    newNode.appendChild(nodeSonTh)
                    let container = document.getElementById('container')
                    container.appendChild(newNode)
                    this.newNode = newNode
                    this.nodeSonO = nodeSonO
                    this.nodeSonTw = nodeSonTw
                    this.nodeSonTh = nodeSonTh
                }
                EveryHome.prototype.info = function (obj){
                    this.newNode.id = key
                    this.nodeSonO.innerText = obj.houseTitle
                    this.nodeSonTw.innerText = obj.publishTime
                    this.nodeSonTh.id = obj.houseId
                }
                let a = new EveryHome()
                a.info(res.data[key])

            }


        }

    })

}


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