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
  name: 'Map2D',
  data() {
    return {
      longitude: 0,
      latitude: 0
    };
  },
  mounted() {
    //确保 mapboxgl 已定义
    if (typeof mapboxgl === 'undefined') {
      console.error('mapboxgl is not defined');
      return;
    }
    mapboxgl.accessToken = "pk.eyJ1IjoiamllZ2lzZXJnZyIsImEiOiJjanExcmJjMTYxMGlxM3hueG9lZjQ4eng5In0.F4Ia4OCMj8HZV8scGQvSfQ";
    var map = new mapboxgl.Map({
      container: 'mapCon',
      style: 'mapbox://styles/mapbox/satellite-streets-v11',
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
      zoom: 12,// 修改后的缩放级别
      pitch: 45, // 设置地图的倾斜角度
      bearing: -17.6, // 设置地图的旋转角度
      antialias: true // 设置抗锯齿，使地图渲染更平滑 
    });

    // 添加地图浏览、拖搜缩放、视角切换功能
    map.addControl(new mapboxgl.NavigationControl());

    // 添加坐标显示功能
    map.on('mousemove', (e) => {
      this.longitude = e.lngLat.lng.toFixed(4);
      this.latitude = e.lngLat.lat.toFixed(4);
    });

    // 添加光照模拟功能
    map.on('style.load', () => {
      map.setLight({
        anchor: 'viewport',
        color: 'white',
        intensity: 0.4
      });
    });

    console.log(map);
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
  left: 600px;
  background: transparent; /* 设置背景为透明 */
  padding: 10px;
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