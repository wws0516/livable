
function changepic() {
    $(".picture").css("display", "none");
    var reads = new FileReader();
    f = document.getElementById('file').files[0];
    reads.readAsDataURL(f);
    reads.onload = function(e) {
        // document.getElementsByClassName('imgShow').src = this.result;
        document.getElementById('imgShow').src = this.result;
        $(".imgShow").css("display", "block");
    };
}

function changevid() {
    $(".video").css("display", "none");
    var reads = new FileReader();
    f = document.getElementById('video').files[0];
    reads.readAsDataURL(f);
    reads.onload = function(e) {
        // document.getElementsByClassName('imgShow').src = this.result;
        document.getElementById('videoShow').src = this.result;
        $(".videoShow").css("display", "block");
    };
};
$(".nextStep").click(function (next) {
    var ipt = document.getElementsByTagName("input");
    for (var i = 0; i <ipt.length; i++){
        if (ipt[i].value.length == 0){
            PromptBox.displayPromptBox('请将信息完善');;
            ipt[i].focus();
            return false;
        } else {
            window.open("HomeRentalBasicImfo.html")
        }
    }
})