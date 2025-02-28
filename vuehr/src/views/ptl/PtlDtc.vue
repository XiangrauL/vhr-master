<template>
  <div class="data-display">
    <h1>工业现场实时数据展示</h1>
    <div class="data-grid">
      <div class="data-section" style="grid-area: volume;">
        <h2>体积流量数据</h2>
        <div class="flow-data">
          <p><strong>超声波流量计 FT206301 (ALTO9ONICV12):</strong></p>
          <p>瞬时标况体积流量: 46975 Nm³/h</p>
          <p>当日标况体积流量: 2.1796×10^5 Nm³</p>
          <p>昨日标况体积流量: 3.7187×10^5 Nm³</p>
          <p>累计标况体积流量: 1.0529×10^6 Nm³</p>
        </div>
        <div class="flow-data">
          <p><strong>工况体积流量:</strong></p>
          <p>瞬时工况体积流量: 909.33 m³/h</p>
          <p>当日工况体积流量: 4703.1 m³</p>
          <p>昨日工况体积流量: 8121.9 m³</p>
          <p>累计工况体积流量: 2.3065×10^7 m³</p>
        </div>
      </div>
      <div class="data-section" style="grid-area: energy;">
        <h2>能量流量数据</h2>
        <p>瞬时能量流量: 1.7791×10^6 MJ/h</p>
        <p>当日能量流量: 8.1735×10^6 MJ</p>
        <p>昨日能量流量: 14002×10^7 MJ</p>
        <p>累计能量流量: 3.8791×10^10 MJ</p>
      </div>
      <div class="data-section" style="grid-area: alarm;">
        <h2>报警状态</h2>
        <p>综合故障报警: 正常</p>
        <p>流量计状态异常报警: 正常</p>
        <p>流量计算机状态异常报警: 正常</p>
        <p>工艺参数状态异常报警: 正常</p>
      </div>
      <div class="data-section" style="grid-area: temperature;">
        <h2>温度数据</h2>
        <p>实时温度: 14.906℃</p>
        <div ref="temperatureChart" class="chart"></div>
      </div>
      <div class="data-section" style="grid-area: pressure;">
        <h2>压力数据</h2>
        <p>实时压力: 4.6467 MPa</p>
        <div ref="pressureChart" class="chart"></div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'PtlDtc',
  data() {
    return {
      temperatureData: this.generateTemperatureData(),
      pressureData: this.generatePressureData()
    };
  },
  mounted() {
    this.initTemperatureChart();
    this.initPressureChart();
  },
  methods: {
    generateTemperatureData() {
      const data = [];
      for (let i = 0; i < 24; i++) {
        data.push({
          time: `2023-10-01 ${i < 10 ? '0' + i : i}:00`,
          value: (14 + Math.random()).toFixed(3)
        });
      }
      return data;
    },
    generatePressureData() {
      const data = [];
      for (let i = 0; i < 24; i++) {
        data.push({
          time: `2023-10-01 ${i < 10 ? '0' + i : i}:00`,
          value: (4 + Math.random()).toFixed(4)
        });
      }
      return data;
    },
    initTemperatureChart() {
      const chart = echarts.init(this.$refs.temperatureChart);
      const option = {
        title: {
          text: '温度实时数据'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: this.temperatureData.map(item => item.time)
        },
        yAxis: {
          type: 'value',
          min: value => value.min - 1.5,
          axisLabel: {
            formatter: '{value} ℃'
          }
        },
        series: [{
          data: this.temperatureData.map(item => item.value),
          type: 'line',
          smooth: true
        }]
      };
      chart.setOption(option);
    },
    initPressureChart() {
      const chart = echarts.init(this.$refs.pressureChart);
      const option = {
        title: {
          text: '压力实时数据'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: this.pressureData.map(item => item.time)
        },
        yAxis: {
          type: 'value',
          min: value => value.min - 1,
          axisLabel: {
            formatter: '{value} MPa'
          }
        },
        series: [{
          data: this.pressureData.map(item => item.value),
          type: 'line',
          smooth: true
        }]
      };
      chart.setOption(option);
    }
  }
}
</script>

<style scoped>
.data-display {
  width: 80%;
  margin: 0 auto;
  text-align: center;
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.data-display h1 {
  color: #333;
  margin-bottom: 20px;
}

.data-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas: 
    "volume temperature"
    "energy pressure"
    "alarm pressure";
  gap: 20px;
}

.data-section {
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.data-section h2 {
  color: #409EFF;
  margin-bottom: 10px;
}

.data-section p {
  color: #666;
  font-size: 16px;
  margin: 5px 0;
}

.flow-data {
  margin-top: 10px;
}

.flow-data strong {
  display: block;
  margin-bottom: 5px;
  font-size: 1.2em;
  color: #555;
}

.chart {
  width: 100%;
  height: 400px;
}
</style>