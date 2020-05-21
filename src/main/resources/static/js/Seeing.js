// JavaScript Document
var Tbody=document.getElementById('tbody');
	let serverURL='http://114.115.156.4:8001/';
    console.log(Tbody);
	Tbody.innerHTML='';
var str='';
var lookid=[];   
  $.ajax({
                type: 'get',
                url: serverURL + 'looking/getLooking',
                contentType: 'application/x-www-form-urlencoded',
                dataType: 'json',
                async: true,
                data: {
                    //city:city[2],	
                },
                success: function (result) {
                    if(result.code=='200'){
						for(var i=0;i<result.data.length;i++)
						{
						var str1='<tr>'+'<td>'+result.data[i].lookId+'</td>' +'<td>'+result.data[i].landlordInformation+'</td>'+ '<td class="td">'+result.data[i].address+'</td>'+'<td>'+result.data[i].date+'</td>'+'<td class="td">'+'<button type="button" class="btn btn-warning btn-sm butt">删除</button>'+'</td>'+'</tr>';
						str +=str1;
				        str1='';
						lookid+=result.data[i].lookId;
						}
						Tbody.innerHTML=str;
						var button=document.getElementsByTagName('button');
						//  console.log(button[1]);
						for(let j=1;j<button.length;j++)
						{
							console.log(button[j]);
						 button[j].onclick=function(){
							 	 let id=lookid[j-1];
							 alert(id);
		      $.ajax({
						type: 'delete',
						url: serverURL + 'looking/deleteLooking',
						contentType: 'application/x-www-form-urlencoded',
						dataType: 'json',
						async: true,
						data: {
							lookingID:id
						},
						success: function (result) {
							if(result.code=='200'){
								alert('成功啦');
								window.location.reload();
							}
							else alert(result.msg);
						},
						error: function () {
							alert('服务器开小差啦');
						}
					})
							   }
						 }
							
                    }
                    else alert(result.msg);
                },
                error: function () {
                    alert('服务器开小差啦');
                }
            })

var oImg =document.getElementById('img-src');
oImg.src=" ";

$.ajax({
	type: 'get',
	url: serverURL + 'information/getHeadPortrait',
	contentType: 'application/x-www-form-urlencoded',
	dataType: 'json',
	async: true,
	data: {
		//city:city[2],
	},
	success: function (result) {
		if(result.code=='200'){
			console.log(result)
			oImg.src=result.data;
			//document.getElementById('Name').innerHTML=result.data.Name;
		}
		else alert(result.msg);
	},
	error: function () {
		alert('服务器开小差啦');
	}
})