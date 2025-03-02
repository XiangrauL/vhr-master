<template>
  <div id="mapCon" />
</template>

<script>
// 导入 Mapbox 的 JavaScript 库
//import mapboxgl from '../mapbox'
//在index.html中通过CDN引入Mapbox的CSS和JavaScript文件
//在组件中直接使用全局变量mapboxgl，无需再导入
//import mapboxgl from '../mapbox'
export default {
  name: 'Map2D',
    mounted() {
    mapboxgl.accessToken = "pk.eyJ1IjoiamllZ2lzZXJnZyIsImEiOiJjanExcmJjMTYxMGlxM3hueG9lZjQ4eng5In0.F4Ia4OCMj8HZV8scGQvSfQ";
    var map = new mapboxgl.Map({
      container: 'mapCon',
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [114.41703647375107, 23.10750961303711],
      zoom: 12
    })
    console.log(map)
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
  top: 10px;
  right: 10px;
}
</style>