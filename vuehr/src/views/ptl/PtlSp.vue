<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <!-- 左侧4个圆形仪表盘 -->
      <el-col :span="12">
        <el-row :gutter="20">
          <el-col :span="12" v-for="(gauge, index) in normalGauges" :key="'normal-'+index">
            <div class="gauge-wrapper">
              <div :ref="'normalGauge'+index" class="gauge-chart normal-gauge"></div>
              <div class="gauge-info">
                <div class="gauge-name">{{gauge.name}}</div>
                <div class="gauge-value">
                  {{gauge.unit === 'MPa' ? gauge.value.toFixed(4) + ' MPa' : gauge.value.toFixed(3) + ' °C'}}
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </el-col>
      
      <!-- 右侧2个半圆形仪表盘 -->
      <el-col :span="12">
        <el-row :gutter="20">
          <el-col :span="24" v-for="(gauge, index) in halfGauges" :key="'half-'+index">
            <div class="gauge-wrapper">
              <div :ref="'halfGauge'+index" class="gauge-chart half-gauge"></div>
              <div class="gauge-info">
                <div class="gauge-name">{{gauge.name}}</div>
                <!-- 移除数值显示 -->
              </div>
            </div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'PtlSp',
  data() {
    return {
      normalGauges: [
        { value: 21.294, name: '进站区温变TT_206101温度', unit: '°C', min: -5, max: 100 },
        { value: 4.5592, name: '进站区压变PT_206101压力', unit: 'MPa', min: 2, max: 10 },
        { value: 14.383, name: '进站区温变TT206102温度', unit: '°C', min: -5, max: 100 },
        { value: 4.5568, name: '进站区压变PT_206102压力', unit: 'MPa', min: 2, max: 10 }
      ],
      halfGauges: [
        { value: 15, name: '进站区气液联动阀 HV_206101' },
        { value: 18, name: '进站区气液联动阀 HV_20610' }
      ],
      charts: []
    }
  },
  mounted() {
    this.initAllGauges()
  },
  methods: {
    initAllGauges() {
      // 初始化普通仪表盘
      this.normalGauges.forEach((gauge, index) => {
        const chart = echarts.init(this.$refs['normalGauge'+index][0])
        this.updateNormalGauge(chart, gauge)
        this.charts.push(chart)
      })
      
      // 初始化半圆仪表盘
      this.halfGauges.forEach((gauge, index) => {
        const chart = echarts.init(this.$refs['halfGauge'+index][0])
        this.updateHalfGauge(chart, gauge.value, gauge.name)
        this.charts.push(chart)
      })
    },
    updateNormalGauge(chart, gauge) {
      const option = {
        grid: {
          top: 10,
          bottom: 10
        },
        series: [{
          name: gauge.name,
          type: 'gauge',
          progress: { show: false },  // 关闭内部进度条
          radius: '85%',  
          center: ['50%', '50%'],
          min: gauge.min,
          max: gauge.max,
          axisLine: { 
            lineStyle: { 
              width: 2,  // 减小轴线宽度
              color: [[1, '#999']]  // 统一轴线颜色
            }
          },
          axisTick: { 
            show: true,
            distance: -20,  // 调整刻度位置
            length: 6,      // 减小刻度长度
            lineStyle: {
              color: '#999'
            }
          },
          splitLine: {
            distance: -22,  // 调整分隔线位置
            length: 20,     // 减小分隔线长度
            lineStyle: { 
              width: 2,
              color: '#999'
            }
          },
          axisLabel: {
            distance: -10,  // 调整标签位置
            color: '#999',
            fontSize: 10,    // 减小字体大小
            formatter: function(value) {
              if (gauge.unit === 'MPa') {
                return value.toFixed(3);
              }
              return value.toFixed(2);
            }
          },
          pointer: { 
            show: true,
            width: 3,      // 减小指针宽度
            itemStyle: {
              color: '#409EFF'
            }
          },
          detail: { show: false }, // 隐藏内部数值
          data: [{ 
            value: gauge.value, 
            name: '' // 清空name，避免重复显示
          }],
          title: {
            show: true,
            offsetCenter: [0, '20%'],
            fontSize: 12,
            color: '#999'
          },
          anchor: {
            show: true,
            size: 12,      // 减小指针固定点大小
            itemStyle: {
              color: '#409EFF'
            }
          }
        },
        {
          // 外部颜色环
          type: 'gauge',
          radius: '95%',
          center: ['50%', '50%'],
          axisLine: {
            lineStyle: {
              width: 10,   // 减小外环宽度
              color: [
                [0.3, '#67C23A'],
                [0.7, '#E6A23C'],
                [1, '#F56C6C']
              ]
            }
          },
          axisLabel: { show: false },
          axisTick: { show: false },
          splitLine: { show: false },
          pointer: { show: false },
          detail: { show: false }
        }]
      }
      chart.setOption(option)
    },
    updateHalfGauge(chart, value, name) {
      const option = {
        grid: {
          top: 10,
          bottom: 10
        },
        series: [{
          name: name,
          type: 'gauge',
          startAngle: 180,
          endAngle: 0,
          progress: { show: false },
          radius: '85%',
          center: ['50%', '60%'],
          axisLine: { 
            lineStyle: { 
              width: 100,  // 调整宽度以便文字显示
              color: [
                [0.3, '#67C23A'],  // 绿色区域 0-30%
                [0.7, '#E6A23C'],  // 黄色区域 30-70%
                [1, '#F56C6C']     // 红色区域 70-100%
              ]
            }
          },
          axisTick: { 
            show: false  // 隐藏刻度线
          },
          splitLine: {
            show: false  // 隐藏分隔线
          },
          axisLabel: {
            show: true,  // 显示刻度标签
            distance: 0,  // 调整标签位置
            fontSize: 18,
            color: '#fff',
            formatter: function(value) {
                // 控制标签在特定位置显示
                if (value == 10) return '开到位';
                if (value == 50) return '故障';
                if (value == 90) return '关到位';
                return '';  // 其他位置不显示文字
           }
          },
          pointer: { 
            show: true,
            width: 3,      // 减小指针宽度
            itemStyle: {
              color: '#409EFF'
            }
          },
          detail: { show: false }, // 隐藏内部数值
          data: [{ 
            value: value, 
            name: '' // 清空name，避免重复显示
          }],
          title: {
            show: false  // 隐藏原有标题
          },
          anchor: {
            show: true,
            size: 12,      // 减小指针固定点大小
            itemStyle: {
              color: '#409EFF'
            }
          }
        },
        {
          // 移除外部颜色环，因为已经在主表盘添加了颜色
          type: 'gauge',
          startAngle: 180,
          endAngle: 0,
          radius: '95%',
          center: ['50%', '60%'],
          axisLine: { show: false },
          axisLabel: { show: false },
          axisTick: { show: false },
          splitLine: { show: false },
          pointer: { show: false },
          detail: { show: false }
        }]
      }
      chart.setOption(option)
    }
  },
  beforeDestroy() {
    this.charts.forEach(chart => chart?.dispose())
  }
}
</script>

<style scoped>
.dashboard-container {
  margin-top: 50px;
}
.gauge-wrapper {
  position: relative;
  width: 100%;
  padding: 10px;
  margin-bottom: 40px; /* 增加表盘容器的下边距 */
}
.normal-gauge {
  height: 250px;
  width: 100%;
}
.half-gauge {
  height: 240px;
  width: 100%;
}
.gauge-info {
  position: absolute;
  text-align: center;
  padding: 10px;
  left: 0;
  right: 0;
}
/* 左侧仪表盘信息位置 */
.normal-gauge + .gauge-info {
  bottom: -30px;  
}
/* 右侧仪表盘信息位置保持不变 */
.half-gauge + .gauge-info {
  bottom: 0;
}
.gauge-name {
  font-size: 17px;  /* 增大名称字体 */
  color: #606266;
  margin-bottom: 8px;  /* 增加间距 */
  line-height: 1.4;
}
.gauge-value {
  font-size: 18px;  /* 增大数值字体 */
  color: #409EFF;
  font-weight: bold;
}
</style>
