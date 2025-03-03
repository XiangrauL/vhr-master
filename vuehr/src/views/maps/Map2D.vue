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
import MapboxDraw from '@mapbox/mapbox-gl-draw'; // 导入 MapboxDraw
export default {
  name: 'Map2D',
  data() {
    return {
      longitude: 0,
      latitude: 0,
      searchQuery: '', // 添加搜索查询数据
      map: null, // 添加 map 属性
      currentDraw: null
    };
  },
  mounted() {
    //确保 mapboxgl 已定义
    if (typeof mapboxgl === 'undefined') {
      console.error('mapboxgl is not defined');
      return;
    }
    mapboxgl.accessToken = "pk.eyJ1IjoiamllZ2lzZXJnZyIsImEiOiJjanExcmJjMTYxMGlxM3hueG9lZjQ4eng5In0.F4Ia4OCMj8HZV8scGQvSfQ";
    this.map = new mapboxgl.Map({
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

    console.log(this.map);
  },
  methods: {
    measureDistance() {
      // 创建Mapbox Draw实例，用于绘制线条
      const draw = new MapboxDraw({
        displayControlsDefault: false, // 不显示默认控件
        controls: {
          line_string: true, // 只显示线条绘制控件
          trash: true // 显示垃圾桶控件以删除绘制
        },
        styles: [{
          // 设置绘制线条的样式
          id: 'gl-draw-line',
          type: 'line',
          filter: ['all', ['==', '$type', 'LineString']],
          layout: {
            'line-cap': 'round',
            'line-join': 'round'
          },
          paint: {
            'line-color': '#D20C0C',
            'line-width': 2
          }
        }]
      });

      // 如果已有Draw控件，则移除它
      if (this.currentDraw) {
        this.map.removeControl(this.currentDraw);
        // 可选：清除先前的绘制（如果需要）
        // this.map.setPaintProperty('gl-draw-line', 'line-opacity', 0);
        // setTimeout(() => this.map.removeLayer('gl-draw-line'), 1000); // 延迟移除层以避免闪烁
      }

      // 保存新的Draw控件实例
      this.currentDraw = draw;
      // 将Draw控件添加到地图上
      this.map.addControl(draw);

      // 监听绘制完成事件
      this.map.on('draw.create', (e) => {
        const data = draw.getAll(); // 获取所有绘制的数据
        if (data.features.length > 0) {
          const line = data.features[0]; // 获取第一个（也是唯一一个）LineString特征
          const distance = turf.length(line, { units: 'kilometers' }); // 直接以公里为单位计算长度（如果turf.js版本支持）
          // 注意：如果turf.length不支持直接指定单位，则需要手动转换：const distanceInMeters = turf.length(line); const distanceInKilometers = distanceInMeters / 1000;
          alert(`测量的距离为: ${distance.toFixed(2)} 公里`);
          
          // 可选：清除绘制以准备下一次测量
          // draw.deleteAll();
        }
      });
    },
    measureArea() {
      // 实现面积测量功能
    },
    drawPoint() {
      // 实现绘制点功能
      // 创建一个新的 Draw 实例
      const draw = new MapboxDraw({
        displayControlsDefault: false,
        controls: {
          point: true,
          trash: true
        }
      });
      this.map.addControl(draw);
      console.log(this.map);
      // 监听绘制完成事件
      this.map.on('draw.create', (e) => {
        const data = draw.getAll();
        if (data.features.length > 0) {
          const point = data.features[0];
          alert(`绘制的点坐标为: 经度 ${point.geometry.coordinates[0].toFixed(4)}, 纬度 ${point.geometry.coordinates[1].toFixed(4)}`);
        }
      });
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
  /* justify-content: flex-end; 将内容对齐到最右边 */
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