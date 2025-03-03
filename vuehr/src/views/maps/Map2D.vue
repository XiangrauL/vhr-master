<template>
  <div class="map-container">
    <div id="map"></div>
    <div class="coordinates-panel">
      <div class="coordinate-item">
        <span class="label">经度:</span>
        <span class="value">{{ longitude }}</span>
      </div>
      <div class="coordinate-item">
        <span class="label">纬度:</span>
        <span class="value">{{ latitude }}</span>
      </div>
    </div>
    <button class="reset-rotation-btn" @click="resetRotation" title="回正方向">
      ⇱
    </button>
    <div class="draw-tools">
      <button @click="addInteraction('Point')" title="绘制点">
        <span>⚪</span>
      </button>
      <button @click="addInteraction('LineString')" title="绘制线">
        <span>／</span>
      </button>
      <button @click="addInteraction('Polygon')" title="绘制面">
        <span>▱</span>
      </button>
      <button @click="clearDrawings" title="清除">
        <span>🗑</span>
      </button>
    </div>
    <div class="mouse-tooltip" v-show="showTooltip" :style="tooltipStyle">
      {{ tooltipText }}
    </div>
    <div class="search-box">
      <input 
        type="text" 
        v-model="searchQuery" 
        placeholder="搜索位置" 
        @keyup.enter="searchLocation"
      />
      <button @click="searchLocation">
        🔍
      </button>
    </div>
  </div>
</template>
 
<script>
  import Map from "ol/Map"
  import {Tile as TileLayer, Vector as VectorLayer} from "ol/layer"
  import {OSM, Vector as VectorSource} from "ol/source"
  import View from "ol/View"
  import {fromLonLat, transform} from "ol/proj"
  import {Draw, Modify, Snap} from 'ol/interaction'
  import {Circle as CircleStyle, Fill, Stroke, Style, Text} from 'ol/style'
  import {getLength, getArea} from 'ol/sphere'
  import Feature from 'ol/Feature'
  import {LineString, Polygon} from 'ol/geom'
  // 移除 Vuex 相关导入
 
  export default {
    name: 'Map2D',
    data(){
      return {
        map: {},
        longitude: '0.0000',
        latitude: '0.0000',
        source: null,
        vector: null,
        draw: null,
        snap: null,
        showTooltip: false,
        tooltipText: '',
        tooltipStyle: {
          left: '0px',
          top: '0px'
        },
        searchQuery: '',
        // 添加原来在 Vuex 中的状态
        viewState: {
          center: [121.8471, 38.9961],
          zoom: 12,
          rotation: 0
        },
        isActive: true  // 替代原来的 activeMap 判断
      }
    },
    methods:{
      // 移除 Vuex actions 映射
      updateViewState(newState) {
        this.viewState = { ...this.viewState, ...newState };
      },
      setActive(status) {
        this.isActive = status;
      },
      
      initialMap(){
        let that = this;
        
        // 创建矢量数据源
        this.source = new VectorSource();
        
        // 创建矢量图层
        this.vector = new VectorLayer({
          source: this.source,
          style: new Style({
            fill: new Fill({
              color: 'rgba(255, 255, 255, 0.2)'
            }),
            stroke: new Stroke({
              color: '#ffcc33',
              width: 2
            }),
            image: new CircleStyle({
              radius: 7,
              fill: new Fill({
                color: '#ffcc33'
              })
            })
          })
        });

        that.map = new Map({
          target:"map", //挂载到id为map的div容器上
          layers:[
            new TileLayer({
              source: new OSM()	//OSM地图
            }),
            this.vector  // 添加矢量图层
          ],
          //设置视图，包括中心点，坐标系，默认缩放级别，最大、最小缩放级别
          view: new View({
            projection:'EPSG:3857',	//坐标系
            center: fromLonLat([121.8471,38.9961]),	//中心点
            zoom: 12,	  //默认缩放级别
            minZoom:1,	//最小缩放级别
            maxZoom: 18,	//最大缩放级别
            rotation: 0.2 // OpenLayers 使用 rotation 而不是 bearing
          })
        });

        // 添加鼠标移动监听器更新坐标
        that.map.on('pointermove', (evt) => {
          const coords = transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');
          that.longitude = coords[0].toFixed(4);
          that.latitude = coords[1].toFixed(4);
        });

        // 添加视图变化监听
        this.map.on('moveend', () => {
          if (!this.isActive) {
            return;
          }
          const view = this.map.getView();
          const center = transform(view.getCenter(), 'EPSG:3857', 'EPSG:4326');
          const zoom = view.getZoom();
          const rotation = view.getRotation();
          
          this.updateViewState({
            center,
            zoom,
            rotation
          });
        });

        // 监听交互开始
        this.map.on('movestart', () => {
          this.setActive(true);
        });
      },
      
      addInteraction(type) {
        // 移除现有的绘制交互
        if (this.draw) {
          this.map.removeInteraction(this.draw);
        }
        if (this.snap) {
          this.map.removeInteraction(this.snap);
        }
        
        // 添加新的绘制交互
        this.draw = new Draw({
          source: this.source,
          type: type
        });

        // 设置初始提示文本
        const tooltipMessages = {
          'Point': '单击添加点',
          'LineString': '单击开始绘制线条，双击结束',
          'Polygon': '单击开始绘制多边形，双击结束'
        };
        this.tooltipText = tooltipMessages[type];
        this.showTooltip = true;

        // 监听鼠标移动更新提示位置
        this.map.on('pointermove', this.updateTooltipPosition);

        // 监听绘制过程
        this.draw.on('drawstart', () => {
          if (type === 'LineString') {
            this.tooltipText = '单击继续绘制，双击结束';
          } else if (type === 'Polygon') {
            this.tooltipText = '单击继续绘制，双击闭合';
          }
        });

        // 监听绘制完成
        this.draw.on('drawend', (evt) => {
          this.showTooltip = false;
          this.map.un('pointermove', this.updateTooltipPosition);
          
          const feature = evt.feature;
          const geometry = feature.getGeometry();
          
          if (type === 'LineString') {
            const length = getLength(geometry);
            const lengthKm = (length / 1000).toFixed(2);
            
            feature.setStyle(new Style({
              stroke: new Stroke({
                color: '#ffcc33',
                width: 2
              }),
              text: new Text({
                text: `距离: ${lengthKm} 公里`,
                fill: new Fill({color: '#000'}),
                stroke: new Stroke({color: '#fff', width: 2}),
                offsetY: -10,
                font: '14px Microsoft YaHei'
              })
            }));
          } 
          else if (type === 'Polygon') {
            const area = getArea(geometry);
            const areaKm = (area / 1000000).toFixed(2);
            
            feature.setStyle(new Style({
              fill: new Fill({
                color: 'rgba(255, 255, 255, 0.2)'
              }),
              stroke: new Stroke({
                color: '#ffcc33',
                width: 2
              }),
              text: new Text({
                text: `面积: ${areaKm} 平方公里`,
                fill: new Fill({color: '#000'}),
                stroke: new Stroke({color: '#fff', width: 2}),
                offsetY: -10,
                font: '14px Microsoft YaHei'
              })
            }));
          }
        });

        this.map.addInteraction(this.draw);
        
        // 添加捕捉交互
        this.snap = new Snap({source: this.source});
        this.map.addInteraction(this.snap);
      },
      
      updateTooltipPosition(evt) {
        const pixel = evt.pixel;
        this.tooltipStyle = {
          left: (pixel[0] + 250) + 'px', // 提示信息距离鼠标的距离从10px改为250px
          top: (pixel[1] + 100) + 'px'   // 从10px改为5px
        };
      },

      clearDrawings() {
        // 清除所有绘制的图形
        this.source.clear();
        
        // 移除绘制交互
        if (this.draw) {
          this.map.removeInteraction(this.draw);
          this.draw = null;
        }
        
        // 移除捕捉交互
        if (this.snap) {
          this.map.removeInteraction(this.snap);
          this.snap = null;
        }
        
        // 隐藏提示框
        this.showTooltip = false;
        
        // 移除鼠标移动监听
        this.map.un('pointermove', this.updateTooltipPosition);
      },
      
      resetRotation() {
        const view = this.map.getView();
        view.setRotation(0);
      },

      searchLocation() {
        if (!this.searchQuery) return;
        
        // 使用 OpenStreetMap Nominatim API 进行地理编码搜索
        fetch(`https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(this.searchQuery)}`)
          .then(response => response.json())
          .then(data => {
            if (data && data.length > 0) {
              const location = data[0];
              const center = [parseFloat(location.lon), parseFloat(location.lat)];
              
              // 转换坐标为EPSG:3857
              const transformedCenter = transform(center, 'EPSG:4326', 'EPSG:3857');
              
              // 移动到搜索位置
              this.map.getView().animate({
                center: transformedCenter,
                zoom: 14,
                duration: 1000
              });
            } else {
              alert('未找到该位置');
            }
          })
          .catch(error => {
            console.error('搜索错误:', error);
            alert('搜索失败，请稍后重试');
          });
      },

      // 监听Vuex状态变化，同步地图视图
      updateMapView() {
        if (this.isActive) return;
        
        const view = this.map.getView();
        const center = transform(this.viewState.center, 'EPSG:4326', 'EPSG:3857');
        
        view.animate({
          center: center,
          zoom: this.viewState.zoom,
          rotation: this.viewState.rotation,
          duration: 100
        });
      }
    },
    mounted() {
      this.initialMap()
    },
    beforeDestroy() {
      // 组件销毁前清理交互
      if (this.draw) {
        this.map.removeInteraction(this.draw);
      }
      if (this.snap) {
        this.map.removeInteraction(this.snap);
      }
    },
    watch: {
      viewState: {
        deep: true,
        handler: 'updateMapView'
      }
    }
  }
</script>
 
<style scoped>
.map-container {
  position: relative;
  width: 100%;
  height: 100vh;
}

#map {
  width: 100%;
  height: 100%;
}

.coordinates-panel {
  position: absolute;
  top: 20px;
  left: 20px;
  background: rgba(255, 255, 255, 0.9);
  padding: 12px 16px;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(5px);
  border: 1px solid rgba(255, 255, 255, 0.5);
  z-index: 1000;
}

.coordinate-item {
  margin: 4px 0;
  display: flex;
  align-items: center;
}

.label {
  font-size: 14px;
  color: #666;
  font-weight: bold;
  margin-right: 8px;
  min-width: 40px;
}

.value {
  font-family: 'Consolas', monospace;
  font-size: 14px;
  color: #333;
  background: rgba(0, 0, 0, 0.05);
  padding: 2px 6px;
  border-radius: 4px;
}

.reset-rotation-btn {
  position: absolute;
  top: 20px;
  right: 20px;
  width: 40px;
  height: 40px;
  background: rgba(255, 255, 255, 0.9);
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(5px);
  transition: all 0.3s ease;
  z-index: 1000;
}

.reset-rotation-btn:hover {
  background: rgba(255, 255, 255, 1);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
  transform: translateY(-1px);
}

.reset-rotation-btn:active {
  transform: translateY(0);
}

.draw-tools {
  position: absolute;
  top: 20px;
  left: 200px;
  display: flex;
  gap: 8px;
  background: rgba(255, 255, 255, 0.9);
  padding: 8px;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(5px);
  z-index: 1000;
}

.draw-tools button {
  width: 36px;
  height: 36px;
  border: none;
  border-radius: 4px;
  background: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  color: #666;
  transition: all 0.3s ease;
}

.draw-tools button:hover {
  background: #f0f0f0;
  transform: translateY(-1px);
}

.draw-tools button:active {
  transform: translateY(0);
}

.mouse-tooltip {
  position: fixed;
  background: rgba(0, 0, 0, 0.7);
  color: white;
  padding: 4px 8px;          /* 减小内边距 */
  border-radius: 4px;
  font-size: 12px;           /* 稍微减小字体 */
  pointer-events: none;
  z-index: 1001;
  font-family: 'Microsoft YaHei', sans-serif;
  white-space: nowrap;
  transform: translate(0, -50%); /* 垂直居中对齐 */
}

.search-box {
  position: absolute;
  top: 15px;
  right: 70px;
  display: flex;
  gap: 8px;
  /*background: rgba(255, 255, 255, 0.9);*/
  padding: 8px;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(5px);
  z-index: 1000;
}

.search-box input {
  padding: 8px 12px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  border-radius: 4px;
  font-size: 14px;
  width: 200px;
  outline: none;
}

.search-box input:focus {
  border-color: #007bff;
}

.search-box button {
  padding: 8px 16px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.search-box button:hover {
  background: #0056b3;
}
</style>
