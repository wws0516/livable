function choseImg(){
    $('#FeedbackImg').trigger('click');
}
$(document).ready(function () {
    let PromptBox=new CreatPromptBox();
    let CreateFeedbackFunction=function () {
        this.AllowFeedback=[false,false,false];
    };
    CreateFeedbackFunction.prototype.DetectionSymbol=function (value) {
        let regEn = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im,
            regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;

        if(regEn.test(value) || regCn.test(value)) {
            PromptBox.displayPromptBox("不能包含特殊字符");
            return false;
        }else {
            return true;
        }
    }
    CreateFeedbackFunction.prototype.checkDetail=function (value) {
        if (!value){
            PromptBox.displayPromptBox("请输入需要反馈的详细信息");
            return false;
        }else {
            return true
        }
    }
    CreateFeedbackFunction.prototype.checkImg=function (element) {
        if (!element.files){
            PromptBox.displayPromptBox("上传图片方便我们更好解决问题");
            return false;
        }else {
            return true
        }
    }
    CreateFeedbackFunction.prototype.Feedback=function(value,element) {
        let img=new FormData();
        // formdata.append('detail',value);
        img.append('file',element.files[0]);

        new Interactive({
            childPath:'/oss/uploadFile',
            method:'PUT',
            detail:img,
            isFile:true,
            successCallback:function (result) {
                let formdata=new FormData();
                formdata.append('userId',1);
                formdata.append('opinion',value);
                formdata.append('picture',result.httpUrl);
                new Interactive({
                    childPath:'/opinion/insertOneOpinion',
                    method:'PUT',
                    detail:formdata,
                    successCallback:function (result) {
                        PromptBox.displayPromptBox('非常感谢您对我们的意见，将为您跳转至主页');
                        setTimeout(function () {
                            location.href='index.html';
                        },3000);
                    },
                    errorCallback:function () {
                        
                    },
                }).init();
            },
            errorCallback:function () {
                
            },
        }).init();

    }
    CreateFeedbackFunction.prototype.DetectionImg=function (value){
        if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(value)) {
            return false;
        }else {
            return true;
        }
    }
    CreateFeedbackFunction.prototype.addImg=function(){
        $('.CoverImg')[0].innerHTML='';

        for (let i=0;i<$('#FeedbackImg')[0].files.length;i++){
            let img=document.createElement('img');
            let reader=new FileReader();
            reader.readAsDataURL($('#FeedbackImg')[0].files[i]);
            reader.onloadend=function(){
                img.src=reader.result;
                $('.CoverImg')[0].append(img);
            }
        }
    }
    let FeedbackFunction=new CreateFeedbackFunction();

    $('#upFeedback').click(function(){
        FeedbackFunction.AllowFeedback[1]=FeedbackFunction.AllowFeedback[1]||FeedbackFunction.DetectionSymbol(document.getElementsByClassName('Feedback')[0].getElementsByTagName('textarea')[0].value);
        FeedbackFunction.AllowFeedback[2]=FeedbackFunction.AllowFeedback[2]||FeedbackFunction.checkDetail(document.getElementsByClassName('Feedback')[0].getElementsByTagName('textarea')[0].value);
        FeedbackFunction.AllowFeedback[3]=FeedbackFunction.AllowFeedback[3]||FeedbackFunction.checkImg(document.getElementById('FeedbackImg'));
        if (FeedbackFunction.AllowFeedback[1]&&FeedbackFunction.AllowFeedback[2]&&FeedbackFunction.AllowFeedback[3]) {
            FeedbackFunction.Feedback(document.getElementsByClassName('Feedback')[0].getElementsByTagName('textarea')[0].value,document.getElementById('FeedbackImg'));
        }else {
            for (let i=0;i<3;i++){
                FeedbackFunction.AllowFeedback[i]=false;
            }
        }
    })
    $('#FeedbackImg').change(function () {
        for (let i=0;i<$('#FeedbackImg')[0].files.length;i++){
            if (!FeedbackFunction.DetectionImg($('#FeedbackImg')[0].files[i].name)){
                PromptBox.displayPromptBox('请选择图片上传');
                $('#FeedbackImg')[0].value = "";
                break;
            }

            if (i==$('#FeedbackImg')[0].files.length-1){

                FeedbackFunction.addImg();
            }
        } 

    })


})