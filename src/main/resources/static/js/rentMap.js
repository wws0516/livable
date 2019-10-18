

function load(city, regions, aggData) {

    var map = new BMap.Map("allMap", {minZoom: 12});
    var point = new BMap.Point(116.404185, 39.915574);

    map.centerAndZoom(point, 12);

    map.addControl(new BMap.NavigationControl({enableGeolocation: true}));

    map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT}));

    map.enableScrollWheelZoom(true);

}

/**
 * 刻画地区
 * @param map
 * @param regionList
 */
function drawRegion(map, regionList) {
    var boundary = new BMap.Boundary();
    var polygonContext = {};
    var regionPoint;
    var textLabel;
    for (var i = 0; i < regionList.length; i++) {

        regionPoint = new BMap.Point(regionList[i].baiduMapLongitude, regionList[i].baiduMapLatitude);

        var houseCount = 0;
        if (regionList[i].en_name in regionCountMap) {
            houseCount = regionCountMap[regionList[i].en_name];
        }

        var textContent = '<p style="margin-top: 20px; pointer-events: none">' +
            regionList[i].cn_name + '</p>' + '<p style="pointer-events: none">' +
            houseCount + '套</p>';
        textLabel = new BMap.Label(textContent, {
            position: regionPoint, // 标签位置
            offset: new BMap.Size(-40, 20) // 文本偏移量
        });

        textLabel.setStyle({
            height: '78px',
            width: '78px',
            color: '#fff',
            backgroundColor: '#0054a5',
            border: '0px solid rgb(255, 0, 0)',
            borderRadius: "50%",
            fontWeight: 'bold',
            display: 'inline',
            lineHeight: 'normal',
            textAlign: 'center',
            opacity: '0.8',
            zIndex: 2,
            overflow: 'hidden'
        });

        map.addOverlay(textLabel); // 将标签画在地图上
        labels.push(textLabel);

        // 记录行政区域覆盖物
        polygonContext[textContent] = []; // 点集合
        (function (textContent) { // 闭包传参
            boundary.get(city.cn_name + regionList[i].cn_name, function (rs) { // 获取行政区域
                var count = rs.boundaries.length; // 行政区域边界点集合长度
                if (count === 0) {
                    alert('未能获取当前输入行政区域')
                    return;
                }

                for (var j = 0; j < count; j++) {
                    // 建立多边形覆盖物
                    var polygon = new BMap.Polygon(
                        rs.boundaries[j],
                        {
                            strokeWeight: 2,
                            strokeColor: '#0054a5',
                            fillOpacity: 0.3,
                            fillColor: '#0054a5'
                        }
                    );
                    map.addOverlay(polygon); // 添加覆盖物
                    polygonContext[textContent].push(polygon);
                    polygon.hide(); // 初始化隐藏边界
                }
            })
        })(textContent);

        textLabel.addEventListener('mouseover', function (event) {
            var label = event.target;
            var boundaries = polygonContext[label.getContent()];

            label.setStyle({backgroundColor: '#1AA591'});
            for (var n = 0; n < boundaries.length; n++) {
                boundaries[n].show();
            }
        });

        textLabel.addEventListener('mouseout', function (event) {
            var label = event.target;
            var boundaries = polygonContext[label.getContent()];

            label.setStyle({backgroundColor: '#0054a5'});
            for (var n = 0; n < boundaries.length; n++) {
                boundaries[n].hide();
            }
        });

        textLabel.addEventListener('click', function (event) {
            var label = event.target;
            var map = label.getMap();
            map.zoomIn();
            map.panTo(event.point);
        });
    }

    if (!customLayer) {
        customLayer = new BMap.CustomLayer({
            geotableId: 175730,
            q: '', // 检索关键字
            tags: '', // 空格分隔的字符串
            filter: '', // 过滤条件，参考：http://lbsyun.baidu.com/index.php?title=lbscloud/api/geosearch
            pointDensityType: BMAP_POINT_DENSITY_HIGH
        });
        map.addTileLayer(customLayer); // 添加自定义图层
        customLayer.addEventListener('onhotspotclick', houseTip); // 单击图层事件
    }
}