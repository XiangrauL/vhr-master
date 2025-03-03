<template>
  <div>
    <div id="mapCon" />
    <div class="selectInfo">
      <p>经度: {{ longitude }}</p>
      <p>纬度: {{ latitude }}</p>
    </div>
    <div class="tools">
      <button @click="measureDistance">测量距离</button>
      <button @click="measureArea">测量面积</button>
      <button @click="drawPoint">绘制点</button>
      <button @click="drawLine">绘制线</button>
      <button @click="drawPolygon">绘制面</button>
      <button @click="addMarker">添加地标</button>
      <button @click="clearDrawings">清空标绘</button>
      <input type="text" v-model="searchQuery" placeholder="搜索位置" />
      <button @click="searchLocation">搜索</button>
    </div>
  </div>
</template>

<script>
// 导入 Mapbox 的 JavaScript 库
//import mapboxgl from '../mapbox'
//在index.html中通过CDN引入Mapbox的CSS和JavaScript文件
//在组件中直接使用全局变量mapboxgl，无需再导入
//import mapboxgl from '../mapbox'
//需要开梯子才能正常显示地图
export default {
  name: 'Map3D',
  data() {
    return {
      longitude: 0,
      latitude: 0,
      searchQuery: '', // 添加搜索查询数据
      map: null // 添加 map 属性
    };
  },
  mounted() {
    mapboxgl.accessToken = "pk.eyJ1IjoiamllZ2lzZXJnZyIsImEiOiJjanExcmJjMTYxMGlxM3hueG9lZjQ4eng5In0.F4Ia4OCMj8HZV8scGQvSfQ";
    this.map = new mapboxgl.Map({
      container: 'mapCon',
      style: 'mapbox://styles/mapbox/light-v10',
      //不同的地图风格
      //mapbox://styles/mapbox/streets-v11
      //mapbox://styles/mapbox/outdoors-v11
      //mapbox://styles/mapbox/light-v10
      //mapbox://styles/mapbox/dark-v10
      //mapbox://styles/mapbox/satellite-v9
      //mapbox://styles/mapbox/satellite-streets-v11
      //mapbox://styles/mapbox/navigation-preview-day-v4
      //mapbox://styles/mapbox/navigation-preview-night-v4
      //mapbox://styles/mapbox/navigation-guidance-day-v4
      //mapbox://styles/mapbox/navigation-guidance-night-v4
      center: [121.8843, 38.98], // 修改后的经纬度
      zoom: 12, // 修改后的缩放级别
      pitch: 45, // 设置地图的倾斜角度
      bearing: -17.6, // 设置地图的旋转角度
      antialias: true // 设置抗锯齿，使地图渲染更平滑
    });

    // 添加地图浏览、拖搜缩放、视角切换功能
    this.map.addControl(new mapboxgl.NavigationControl());

    // 添加坐标显示功能
    this.map.on('mousemove', (e) => {
      this.longitude = e.lngLat.lng.toFixed(4);
      this.latitude = e.lngLat.lat.toFixed(4);
    });

    // 添加光照模拟功能
    this.map.on('style.load', () => {
      this.map.setLight({
        anchor: 'viewport',
        color: 'white',
        intensity: 0.4
      });
    });

    // 在地图加载完成后执行的函数
    this.map.on('load', () => {
      // 获取当前地图的所有图层
      const layers = this.map.getStyle().layers
      let labelLayerId // 用于存储标签图层的 ID 
      // 遍历所有图层，查找类型为 'symbol' 并且有 'textfield' 属性的图层
      for (var i = 0; i < layers.length; i++) {
        if (layers[i].type === 'symbol' && layers[i].layout['textfield']) {
          labelLayerId = layers[i].id; // 存储找到的标签图层的 ID 
          break; // 找到后退出循环
        }
      }

      // 在地图中添加一个新的 3D 建筑图层
      this.map.addLayer({
        'id': '3d-buildings', // 图层的 ID 
        'source': 'composite', // 图层的数据源
        'source-layer': 'building', // 数据源中的图层名称
        'filter': ['==', 'extrude', 'true'], // 过滤条件，只显示 extrude属性为 true 的建筑
        'type': 'fill-extrusion', // 图层的类型，这里是填充挤出图层，用于显示 3D 建筑
        'minzoom': 15, // 图层在缩放级别 15 时可见
        'paint': {
          // 设置图层的绘制样式
          'fill-extrusion-color': '#aaa', // 建筑的填充颜色
          'fill-extrusion-height': [
            'interpolate',
            ['linear'],
            ['zoom'],
            15,
            0,
            15.05,
            ['get', 'height'] 
          ], // 建筑的高度，根据缩放级别进行线性插值
          'fill-extrusion-base': [
            'interpolate',
            ['linear'],
            ['zoom'],
            15,
            0,
            15.05,
            ['get', 'min_height']
          ], // 建筑的基础高度，同样根据缩放级别进行线性插值
          'fill-extrusion-opacity': 0.6 // 建筑的透明度
        } 
      }, labelLayerId) // 将新图层添加到找到的标签图层之上
    });

    console.log(this.map);
  },
  methods: {
    measureDistance() {
      // 实现距离测量功能
    },
    measureArea() {
      // 实现面积测量功能
    },
    drawPoint() {
      // 实现绘制点功能
    },
    drawLine() {
      // 实现绘制线功能
    },
    drawPolygon() {
      // 实现绘制面功能
    },
    addMarker() {
      // 实现添加地标功能
    },
    clearDrawings() {
      // 实现清空标绘功能
    },
    searchLocation() {
      // 使用 Mapbox Geocoding API 进行位置搜索
      const geocodingUrl = `https://api.mapbox.com/geocoding/v5/mapbox.places/${this.searchQuery}.json?access_token=${mapboxgl.accessToken}`;
      fetch(geocodingUrl)
        .then(response => response.json())
        .then(data => {
          if (data.features && data.features.length > 0) {
            const [lng, lat] = data.features[0].center;
            this.longitude = lng.toFixed(4);
            this.latitude = lat.toFixed(4);
            this.map.flyTo({ center: [lng, lat], zoom: 14 });
          } else {
            alert('未找到该位置');
          }
        })
        .catch(error => {
          console.error('Error:', error);
        });
    }
  }
}
</script>

<style scoped>
#mapCon {
  height: calc(100vh - 85px);
  width: 100%;
}
.selectInfo {
  position: absolute;
  top: 105px; /* 距离top的距离 */
  left: 221px; /* 距离左边的距离 */
  background: rgba(255, 255, 255, 0.8);
  padding: 10px;
  border-radius: 10px;
}
.tools {
  position: absolute;
  top: 100px;
  left: 340px;
  background: transparent; /* 设置背景为透明 */
  padding: 10px;
  border-radius: 5px;
  display: flex;
  flex-wrap: wrap;
}
.tools input {
  margin: 5px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
.tools button {
  margin: 5px;
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}
.tools button:hover {
  background-color: #0056b3;
}
.tools button:active {
  background-color: #004085;
}
</style>