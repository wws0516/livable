$(document).ready(function ( ) {
    $(".nextStep").click(function () {
        var option = document.getElementsByClassName("week");
        if(option.selectedIndex = "选择时间"){
            PromptBox.displayPromptBox('请完善信息');
        } else {
            console.log(1223);
            window.open("HomeRentalAllocation.html")
        }
    })
})