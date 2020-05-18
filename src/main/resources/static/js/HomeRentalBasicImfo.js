$(".first").click(function (e) {
    $(".okFirst").css({display:"block"});
    $(".okSecond").css({display:"none"})
});
$(".second").click(function (a) {
    $(".okFirst").css({display:"none"});
    $(".okSecond").css({display:"block"})
});

var roomNum = 1;
$("#plusRoom").click(function (b) {
    roomNum++;
    document.getElementById("roomNum").innerHTML = roomNum;
});
$("#minRoom").click(function (minRoom) {
    roomNum--;
    document.getElementById("roomNum").innerHTML = roomNum;
    if (roomNum <= 0){
        roomNum = 1;
        document.getElementById("roomNum").innerHTML = roomNum;
        PromptBox.displayPromptBox('最多可容纳房客不得少于1');
    }

});
var washNum = 1;
$("#plusWash").click(function (plusWash) {
    washNum++;
    document.getElementById("washNum").innerHTML = washNum;
});
$("#minWash").click(function (minWash) {
    washNum--;
    document.getElementById("washNum").innerHTML = washNum;
    if (washNum <= 0){
        washNum = 1;
        document.getElementById("washNum").innerHTML = washNum;
        PromptBox.displayPromptBox('卫生间数量不得少于1');
    }
});
$(".nextStep").click(function (next) {
    var ipt = document.getElementsByTagName("input");
    for (var i = 0; i <ipt.length; i++){
        if (ipt[i].value.length == 0){
            PromptBox.displayPromptBox('请将信息完善');
            ipt[i].focus();
            return false;
        } else if (/none/.test($('.okSecond').css("display"))&&/none/.test($('.okFirst').css("display"))){
            PromptBox.displayPromptBox('勾选“整租”或者“合租”');
        }
        else {
            window.open("HomeRentalSecond.html")
        }
    }
})