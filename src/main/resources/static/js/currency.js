$(document).ready(function () {
    let nomalInformation={

    }
    nomalInformation.getInformation=function () {
        $.ajax({
            type: 'post',
            url: URL + '/getInformation',
            contentType: 'application/x-www-form-urlencoded',
            dataType: 'json',
            async: true,
            data: {
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
    }
    $('#FeedBack').click(function () {
        $('#FeedBackBody').modal('show');
    })
    $('#upFeedBack').click(function () {
        $.ajax({
            type: 'post',
            url: URL + '/upFeedBack',
            contentType: 'application/x-www-form-urlencoded',
            dataType: 'json',
            async: true,
            data: {
                FeedBack:document.getElementById('#FeedBackInner').value,
            },
            success: function (result) {
                if(result.code=='200'){
                    document.getElementById('Name').innerHTML=result.data.Name;
                    alert('已经收到您的意见啦~感谢您的反馈')
                }
                else alert(result.msg);
            },
            error: function () {
                alert('服务器开小差啦');
            }
        })
    })

    (function () {
        nomalInformation.getInformation();
    })
})