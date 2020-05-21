// function initMap(){
//     createMap();//创建地图
//     setMapEvent();//设置地图事件
//     addMapControl();//向地图添加控件
//     addMapOverlay();//向地图添加覆盖物
// }
// function createMap(){
//     map = new BMap.Map("map");
//     map.centerAndZoom(new BMap.Point(110.365734,21.27669),15);
// }
// function setMapEvent(){
//     map.enableScrollWheelZoom();
//     map.enableKeyboard();
//     map.enableDragging();
//     map.enableDoubleClickZoom()
// }
// function addClickHandler(target,window){
//     target.addEventListener("click",function(){
//         target.openInfoWindow(window);
//     });
// }
// function addMapOverlay(){
// }
// //向地图添加控件
// function addMapControl(){
//     var scaleControl = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
//     scaleControl.setUnit(BMAP_UNIT_IMPERIAL);
//     map.addControl(scaleControl);
//     var navControl = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
//     map.addControl(navControl);
//     var overviewControl = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:true});
//     map.addControl(overviewControl);
// }
// var map;
// initMap();


// 百度地图API功能
// 百度地图API功能
var map = new BMap.Map("map");
var point = new BMap.Point(116.404, 39.915);
map.centerAndZoom(point, 15);

var geolocationControl = new BMap.GeolocationControl();
geolocationControl.addEventListener("locationSuccess", function(e){
    // 定位成功事件
    var address = '';
    console.log(e.addressComponent)
    address += e.addressComponent.province;
    address += e.addressComponent.city;
    address += e.addressComponent.district;
    address += e.addressComponent.street;
    address += e.addressComponent.streetNumber;
    map.centerAndZoom(e.addressComponent.city);
    // 覆盖区域图层测试
    map.addTileLayer(new BMap.PanoramaCoverageLayer());
});
geolocationControl.addEventListener("locationError",function(e){
    // 定位失败事件
    alert(e.message);
});
map.addControl(geolocationControl);

var marker = new BMap.Marker(point);  // 创建标注
map.addOverlay(marker);               // 将标注添加到地图中
marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
var localSearch = new BMap.LocalSearch(map);
localSearch.enableAutoViewport();
// console.log(localSearch.searchNearby('餐厅','附近'))

function searchByStationName() {
    map.clearOverlays(); //清空原来的标注
    var keyword = $(".weizhi")[0].innerText;
    var marker = ''
    localSearch.setSearchCompleteCallback(function(searchResult) {
        var poi = searchResult.getPoi(0);
        console.log(poi.point.lng + "," + poi.point.lat)
        map.centerAndZoom(poi.point, 13);
        var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat)); // 创建标注，为要查询的地方对应的经纬度
        map.addOverlay(marker);
        marker.addEventListener("click", function() {
            this.openInfoWindow(infoWindow);
        });
        marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    });
    localSearch.search(keyword);
    // localSearch.searchNearby('小吃',marker)
}
