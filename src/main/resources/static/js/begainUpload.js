// $("#upload").click(function () {
//     var width = $(".container-fluid").width()
//     // console.log(width)
//     var leftWidth = $('.leftText').width()
//     // console.log(leftWidth)
//     // for (var i = leftWidth;i<width;i++){
//     //     $(".leftText").css({'width':i})
//     //
//     // }
//     setTimeout(function () {
//         if (width>leftWidth){
//
//         }
//     })
$("#upload").click(function () {
    $(".leftText").css({
        'width':'100%'
    })
    setTimeout(function () {
        $(location).attr('href','../html/homeSend.html')
    },2000)
})