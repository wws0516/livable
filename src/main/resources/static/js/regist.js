// 注意事项:

// 含有部分为
// 姓名(Name)
// 性别(Sex)
// 密码(Password)
// 邮箱(Email)
// 邮箱验证码(EmailKey)
//
// 每部分加入类RegistInput
// 获取邮箱验证码Id为GetEmailKey
// 去注册的Id为ToRegist
//
// -----------------------------------------------------------------------------------------------

$(document).ready(function () {

    let CreateRegistFunction=function (allId) {
        this.AllowRegistration=[false,false,false,false,false,false];
        this.time=0;
        this.id=allId||[];
    };
    CreateRegistFunction.prototype.regist=function() {
        let formdata=new FormData();
        formdata.append('name',$('#Name').val());
        formdata.append('gender',$('#Sex').val());
        formdata.append('password',$('#Password').val());
        formdata.append('email',$('#Email').val());
        formdata.append('emailCode',$('#EmailKey').val());
        new Interactive({
            childPath:'/user/register',
            method:'POST',
            detail:formdata,
            successCallback:function (result) {
                PromptBox.displayPromptBox('注册成功');
                setTimeout(function () {
                    window.location='login.html'
                },3000)

            },
            errorCallback:function () {

            },
        }).init();

    }
    CreateRegistFunction.prototype.DetectionSymbol=function (value) {
        let regEn = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im,
            regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;

        if(regEn.test(value) || regCn.test(value)) {
            PromptBox.displayPromptBox("不能包含特殊字符");
            return false;
        }else {
           return true;
        }
    }
    CreateRegistFunction.prototype.DetectionNum=function(value){
        if (value.length>=6&&value.length<=18) {
            return true;
        }else {
            if (value.length>18) {
                PromptBox.displayPromptBox('输入超过18个字符，请重新输入');
                return false;
            }else {
                PromptBox.displayPromptBox('账号密码小于6个字符，请重新输入');
                return false;
            }

        }
    }
    CreateRegistFunction.prototype.DetectionEmail=function (str){
        var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
        return reg.test(str);
    }
    CreateRegistFunction.prototype.getEmailKey=function(){

        if (RegistFunction.DetectionEmail($('#Email').val()) && this.time==0){
            console.log($('#Email').val());
            let form=new FormData();
            form.append('email',$('#Email').val());
            new Interactive({
                childPath:'/emailCode',
                method:'GET',
                detail:form,
                successCallback:function (result) {
                    $('#GetEmailKey').removeClass('btn-success');
                    $('#GetEmailKey').addClass('disabled');

                    (function () {
                        this.time=30;
                        let IntvId=setInterval(function () {
                            if (CreateRegistFunction.prototype.time>0){
                                CreateRegistFunction.prototype.time--;
                                document.getElementById('GetEmailKey').value=CreateRegistFunction.prototype.time;
                            }else {
                                $('#GetEmailKey').removeClass('disabled');
                                $('#GetEmailKey').addClass('btn-success');
                                document.getElementById('GetEmailKey').value='获取验证码';
                                window.clearInterval(IntvId);
                            }
                        },1000)
                    })()
                },
                errorCallback:function () {
                },
            }).init();

        }else {
            if (!RegistFunction.DetectionEmail(document.getElementById('EmailKey').value)){
                PromptBox.displayPromptBox('请填写邮箱');
            }else {
                PromptBox.displayPromptBox('获取验证码过于频繁');
            }
        }
    }
    CreateRegistFunction.prototype.DetectionAll=function(id,innervalue,alert){
        switch (id) {
            case 'Name':(function () {
                if (innervalue.length>1&&innervalue.length<5&& RegistFunction.DetectionSymbol(innervalue)){
                    RegistFunction.AllowRegistration[0]=true;
                } else {
                    RegistFunction.AllowRegistration[0]=false;
                    !alert?void (0):PromptBox.displayPromptBox('请输入正确的姓名');
                }
            })();break;
            case 'Sex':(function () {
                console.log(innervalue);
                if (innervalue){
                    RegistFunction.AllowRegistration[1]=true;
                } else {
                    RegistFunction.AllowRegistration[1]=false;
                    !alert?void (0):PromptBox.displayPromptBox('请选择性别');
                }
            })();break;
            case 'Password':(function () {
                if (RegistFunction.DetectionSymbol(innervalue)&&RegistFunction.DetectionNum(innervalue)){
                    RegistFunction.AllowRegistration[2]=true;
                } else {
                    RegistFunction.AllowRegistration[2]=false;
                    !alert?void (0):PromptBox.displayPromptBox('请输入正确的密码');
                }
            })();break;
            case 'Passwords':(function () {
                if (document.getElementById('Password').value==document.getElementById('Passwords').value){
                    RegistFunction.AllowRegistration[3]=true;
                } else {
                    RegistFunction.AllowRegistration[3]=false;
                    !alert?void (0):PromptBox.displayPromptBox('两次密码不一致');
                }
            })();break;
            case 'Email':(function () {
                console.log(innervalue);
                if (RegistFunction.DetectionEmail(innervalue)){
                    RegistFunction.AllowRegistration[4]=true;
                } else {
                    RegistFunction.AllowRegistration[4]=false;
                    !alert?void (0):PromptBox.displayPromptBox('请输入正确的邮箱');
                }
            })();break;
            case 'EmailKey':(function () {
                if (innervalue){
                    RegistFunction.AllowRegistration[5]=true;
                } else {
                    RegistFunction.AllowRegistration[5]=false;
                    !alert?void (0):PromptBox.displayPromptBox('请输入验证码');
                }
            })();break;
        }
    }
    let RegistFunction=new CreateRegistFunction(['Name','Sex','Email','Password','Passwords','EmailKey']);

    (function main() {

        $('.RegistInput').blur(function () {
            let innervalue=event.path[0].value;
            console.log(event.path[0].id);
            RegistFunction.DetectionAll(event.path[0].id,innervalue,true);

        })
        $('#ToRegist').click(function () {
            let Status=true;
            for (let i=0;i<6;i++ ){
                RegistFunction.DetectionAll(RegistFunction.id[i],$('#'+RegistFunction.id[i]).val(),false);
            }
            for (let i of RegistFunction.AllowRegistration){
                Status=Status && i;
                console.log(Status);
            }
            $('#ToRegist').addClass('rubberBand');

            if (Status){
                RegistFunction.regist();
                $('#ToRegist').on('animationend',function () {
                    $('#ToRegist').removeClass('rubberBand')
                })
            } else {
                PromptBox.displayPromptBox('您有信息未填写或者填写有误');
                $('#ToRegist').on('animationend',function () {
                    $('#ToRegist').removeClass('rubberBand')
                })
            }
        })
        $('#GetEmailKey').click(function (){
            RegistFunction.getEmailKey.call(RegistFunction);
        })
        $('.registInnerBody>div>input').on('keypress',function () {
            if (event.keyCode==13){
                $('#ToRegist').click();
            }
        })

    })()
})