$(document).ready(function () {
    let params =function () {
        this.currentX=0;
        this.currentY= 0;
        this.flag= false;
        this.nowtarget='';
        this.aimElement=document.getElementById('container').getElementsByTagName('div');
        this.elementLength=0;
        this.currentEle='';
        this.lie=['topText','rightText','bottomText','leftText','midText'];
        this.elementInformation= new Array();
    };


    let createElementInformation=function (e){
        this.left=parseInt($(e).css('left').replace('px',''));
        this.top=parseInt($(e).css('top').replace('px',''));
        this.width=parseInt($(e).css('width').replace('px',''));
        this.height=parseInt($(e).css('height').replace('px',''));
        this.lie=this.left*this.left+this.top*this.top;
        this.id=$(e).attr('id');
        this.class=$(e).attr('class');
        this.tag=(function () {
            let tag=[];
            for (let x of $(e).children('p')){
                tag.push($(x).text());
            }
            return tag;
        })()
    }

    params.prototype.getCss = function(o,key){
        return o.currentStyle? o.currentStyle[key] : document.defaultView.getComputedStyle(o,false)[key];
    };
    params.prototype.Drag = function(bar, target, callback){
        let params={};
        let e = event;
        let t=this;

        if(this.getCss(target, "left") !== "auto"){
            params.left = this.getCss(target, "left");
        }
        if(this.getCss(target, "top") !== "auto"){
            params.top = this.getCss(target, "top");
        }

        params.flag = true;
        if(!event){
            event = window.event;
            bar.onselectstart = function(){
                return false;
            }
        }

        params.currentX = e.clientX;
        params.currentY = e.clientY;

        document.onmouseup = function(){
            params.flag = false;
            if(t.getCss(target, "left") !== "auto"){
                params.left = t.getCss(target, "left");
            }
            if(t.getCss(target, "top") !== "auto"){
                params.top = t.getCss(target, "top");
            }
            t.elementInformation=[];
            t.addAllDiv();
        };
        document.onmousemove = function(event){
            let e = event ? event: window.event;
            if(params.flag){
                let nowX = e.clientX, nowY = e.clientY;
                let disX = nowX - params.currentX, disY = nowY - params.currentY;
                let ele={
                    left:parseInt(params.left) + disX,
                    top:parseInt(params.top) + disY,
                    width:parseInt($(e.target).css('width')),
                    height:parseInt($(e.target).css('height')),
                    id:$(e.target).attr('id'),
                };
                let aim=t.findAim(ele);

                target.style.left = aim.disX?parseInt(params.left) + aim.disX+disX + "px":parseInt(params.left) + disX + "px";
                target.style.top = aim.disY?parseInt(params.top) + aim.disY+disY + "px":parseInt(params.top) + disY + "px";

                if (typeof callback == "function") {
                    callback((parseInt(params.left) || 0) + disX, (parseInt(params.top) || 0) + disY);
                }

                if (event.preventDefault) {
                    event.preventDefault();
                }
                return false;
            }


        }
    };
    params.prototype.addAllDiv=function(){
        for (let e of $('#container').children()){
            this.elementInformation.push(new createElementInformation(e));
            $(e).addClass('added');
        }
        this.elementInformation.sort(function (head,last) {
            return last.lie-head.lie;
        })
    };
    params.prototype.findAim=function(ele){
        let aim=new Array();

        for (let x of this.elementInformation ){
            if (ele.id!=x.id){
                aim.push(Math.abs(x.top-ele.top),Math.abs(x.top-ele.top-ele.height),Math.abs(x.left+x.width-ele.left-ele.width),Math.abs(x.left+x.width-ele.left),Math.abs(x.top+x.height-ele.top-ele.height),Math.abs(x.top+x.height-ele.top),Math.abs(x.left-ele.left),Math.abs(x.left-ele.left-ele.width));
            }
        }
        let min=aim.indexOf(Math.min.apply(Math,aim));

        if (aim[min]<15){
            switch (min%8) {
                case 0:case 5:return {disX:null,disY:aim[min]};break;
                case 1:case 4:return {disX:null,disY:-aim[min]};break;
                case 2:case 7:return {disX:aim[min],disY:null};break;
                case 3:case 6:return {disX:-aim[min],disY:null};break;
            }
        } else {
            return{disX:null,disY:null};
        }


    };
    params.prototype.inputEvent=function(ele){
        let t=this;
        $(ele).focus(function () {
            $(ele).blur(function () {
                let tap=document.createElement('p');
                tap.innerHTML=$(ele).val();
                $(tap).addClass($(ele).attr('class'))
                $(ele).after(tap);
                t.pEvent(tap);
                $(ele).remove();
            })
        })
    }
    params.prototype.pEvent=function(ele){
        let t=this;
        $(ele).dblclick(function () {
            let tap=document.createElement('input');
            $(tap).addClass($(ele).attr('class'))
            $(tap).val(ele.innerHTML);
            $(ele).after(tap);
            t.inputEvent(tap);
            $(ele).remove();
        })
    }
    params.prototype.save=function(){
        return this.elementInformation;
    }
    params.prototype.creatAll=function(elementInformation){
        for (let ele of elementInformation){
            let div=document.createElement('div');

            $(div).attr('class',ele.class);
            $(div).attr('id',ele.id);
            $(div).css('width',ele.width);
            $(div).css('height',ele.height);
            $(div).css('left',ele.left);
            $(div).css('top',ele.top);
            for (let x of ele.tag){
                let tag=document.createElement('p');
                $(tag).html(x);
                $(div).append(tag);
            }
            $('#container').append(div);
        }
    }

    let Magnetic=new params();
    Magnetic.addAllDiv();

    let menu = new BootstrapMenu('#container', {
        actionsGroups: [
            ['changeTip','addTip','showTip'],
            ['styleBlue','styleOringe', 'stylePink' ],
            ['save']

        ],
        actions: {
            addRoom:{
                name:'添加房间块',
                onClick: function () {
                    let ele = document.createElement('div');
                    $(ele).addClass('defaultDiv');
                    $(ele).attr('id', Date.parse(new Date()));
                    $('#container').append(ele);
                }
            },
            deletRoom:{
                name:'删除房间块',
                onClick: function  () {
                    $('#' + Magnetic.currentEle).remove();
                }
            },
            lagerRoom:{
                name:'增大房间块',
                onClick: function  () {
                    let width=parseInt($('#' + Magnetic.currentEle).css('width'))*1.2;
                    let height=parseInt($('#' + Magnetic.currentEle).css('height'))*1.2;
                    console.log($('#' + Magnetic.currentEle).css('height'))
                    $('#' + Magnetic.currentEle).css('width',width+'px');
                    $('#' + Magnetic.currentEle).css('height',height+'px');
                }
            },
            smallRoom:{
                name:'缩小房间块',
                onClick: function  () {
                    let width=parseInt($('#' + Magnetic.currentEle).css('width'))*1.2;
                    let height=parseInt($('#' + Magnetic.currentEle).css('height'))*1.2;
                    console.log($('#' + Magnetic.currentEle).css('height'))
                    $('#' + Magnetic.currentEle).css('width',width+'px');
                    $('#' + Magnetic.currentEle).css('height',height+'px');
                }
            },
            changeTip:{
                name:'变更标签位置<span class="glyphicon glyphicon-triangle-top"></span>',
                onClick: function () {
                    lie = Magnetic.lie.indexOf($(document.getElementById(Magnetic.currentEle)).children('input').attr('class') || $(document.getElementById(Magnetic.currentEle)).children('p').attr('class'));
                    if (lie < Magnetic.lie.length - 1) {
                        $('#' + Magnetic.currentEle).children('input').attr('class', Magnetic.lie[lie + 1]);
                        $('#' + Magnetic.currentEle).children('p').attr('class', Magnetic.lie[lie + 1]);
                    } else {
                        $('#' + Magnetic.currentEle).children('input').attr('class', Magnetic.lie[0]);
                        $('#' + Magnetic.currentEle).children('p').attr('class', Magnetic.lie[0]);
                    }

                }
            },
            addTip:{
                name:'添加标签',
                onClick: function () {
                    let tap=document.createElement('input');
                    $(tap).addClass('midText')
                    $('#' + Magnetic.currentEle).append(tap);
                    Magnetic.inputEvent(tap);
                }
            },
            showTip:{
                name:'显示/隐藏网格',
                onClick: function () {
                    /bgLine/i.test($('#container').attr('class'))?$('#container').removeClass('bgLine'):$('#container').addClass('bgLine');
                }
            },
            styleBlue:{
                name:'变更色调<img class="display styleBlue">',
                onClick: function () {
                    $('#' + Magnetic.currentEle).attr('class','defaultDiv styleBlue');
                }
            },
            styleOringe:{
                name:'变更色调<img class="display styleOringe">',
                onClick: function () {
                    $('#' + Magnetic.currentEle).attr('class','defaultDiv styleOringe');
                }
            },
            stylePink:{
                name:'变更色调<img class="display stylePink">',
                onClick: function () {
                    $('#' + Magnetic.currentEle).attr('class','defaultDiv stylePink');
                }
            },
            save:{
                name:'保存布局',
                onClick: function () {
                    console.log(Magnetic.save());
                }
            },
        }
    });

    document.getElementById('container').addEventListener( "mousedown", function(event){
        if (event.target.id !== 'container'&& event.target.localName!=='input' && event.target.localName!=='p'){
            Magnetic.currentEle=event.target.id;
            Magnetic.Drag(event.target,event.target);
            console.log(Magnetic.currentEle);
        }
    });
    $('#container input').focus(function () {
        $('#container input').blur(function () {
            let tap=document.createElement('p');
            tap.innerHTML=$(event.target).val();
            $(tap).addClass($(event.target).attr('class'))

            $(event.target).after(tap);
            Magnetic.pEvent(tap);
            $(event.target).remove();
        })
    })
    $('#container p').dblclick(function () {
        let tap=document.createElement('input');
        $(tap).addClass($(event.target).attr('class'))
        $(tap).val(event.target.innerHTML);

        $(event.target).after(tap);
        Magnetic.inputEvent(tap);
        $(event.target).remove();
    })




})





