<template>
  <div class="ptl-dvc">
    <h2 class="title">应急物资清单</h2>
    <div class="controls">
      <div class="search-box">
        <input type="text" v-model="searchQuery" placeholder="搜索..." class="search-input" />
        <button @click="performSearch" class="search-button">搜索</button>
      </div>
    </div>
    <table class="data-table">
      <thead>
        <tr>
          <th>序号</th>
          <th>物资名称</th>
          <th>型号规格</th>
          <th>物资编号</th>
          <th>生产厂家</th>
          <th>储存位置</th>
          <th>数量</th>
          <th>备注</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in paginatedData" :key="index">
          <td>{{ item.id }}</td>
          <td>{{ item.name }}</td>
          <td>{{ item.spec }}</td>
          <td>{{ item.number }}</td>
          <td>{{ item.manufacturer }}</td>
          <td>{{ item.location }}</td>
          <td class="quantity-cell">{{ item.quantity }}</td>
          <td>{{ item.remark }}</td>
        </tr>
      </tbody>
    </table>
    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1" class="page-button">上一页</button>
      <span class="page-info">第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
      <button @click="nextPage" :disabled="currentPage === totalPages" class="page-button">下一页</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PtlEd',
  data() {
    return {
      tableData: [
        { 
          id: '1',
          name: '便携式可燃气体检测仪',
          spec: 'XP-3140',
          number: 'WZCL001',
          manufacturer: '北京安全设备厂',
          location: '应急仓库',
          quantity: '5台',
          remark: '正常'
        },
        { 
          id: '2',
          name: '正压式空气呼吸器',
          spec: 'RHZK5/30',
          number: 'WZCL002',
          manufacturer: '济南消防器材厂',
          location: '应急仓库',
          quantity: '8套',
          remark: '正常'
        },
        { 
          id: '3',
          name: '消防水带',
          spec: 'DN65',
          number: 'WZCL003',
          manufacturer: '上海消防器材厂',
          location: '应急仓库',
          quantity: '20条',
          remark: '完好'
        },
        { 
          id: '4',
          name: '便携式应急照明灯',
          spec: 'LED-500W',
          number: 'WZCL004',
          manufacturer: '深圳照明设备厂',
          location: '应急仓库',
          quantity: '12个',
          remark: '正常'
        },
        { 
          id: '5',
          name: '防毒面具',
          spec: 'HG-87',
          number: 'WZCL005',
          manufacturer: '天津安全设备厂',
          location: '应急仓库',
          quantity: '15个',
          remark: '完好'
        },
        { 
          id: '6',
          name: '消防栓扳手',
          spec: 'CS-102',
          number: 'WZCL006',
          manufacturer: '重庆工具厂',
          location: '消防柜',
          quantity: '10把',
          remark: '正常'
        },
        { 
          id: '7',
          name: '安全帽',
          spec: 'ABS-01',
          number: 'WZCL007',
          manufacturer: '河北安全用品厂',
          location: '应急仓库',
          quantity: '25顶',
          remark: '完好'
        },
        { 
          id: '8',
          name: '急救箱',
          spec: 'JJ-2023',
          number: 'WZCL008',
          manufacturer: '广州医疗器械厂',
          location: '值班室',
          quantity: '3个',
          remark: '正常'
        },
        { 
          id: '9',
          name: '便携式氧气检测仪',
          spec: 'CY-7B',
          number: 'WZCL009',
          manufacturer: '武汉仪器厂',
          location: '应急仓库',
          quantity: '4台',
          remark: '完好'
        },
        { 
          id: '10',
          name: '消防斧',
          spec: 'XF-25',
          number: 'WZCL010',
          manufacturer: '长沙工具厂',
          location: '消防柜',
          quantity: '6把',
          remark: '正常'
        },
        { 
          id: '11',
          name: '防爆对讲机',
          spec: 'FB-868',
          number: 'WZCL011',
          manufacturer: '南京通信设备厂',
          location: '值班室',
          quantity: '10台',
          remark: '完好'
        },
        { 
          id: '12',
          name: '便携式发电机',
          spec: 'FD-5000W',
          number: 'WZCL012',
          manufacturer: '杭州机械厂',
          location: '应急仓库',
          quantity: '2台',
          remark: '正常'
        }
      ],
      searchQuery: '',
      currentPage: 1,
      itemsPerPage: 10,
      filteredDataCache: [],
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.filteredDataCache.length / this.itemsPerPage);
    },
    paginatedData() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.filteredDataCache.slice(start, end);
    },
  },
  methods: {
    performSearch() {
      const query = this.searchQuery.trim().toLowerCase();
      if (!query) {
        this.filteredDataCache = this.tableData;
      } else {
        this.filteredDataCache = this.tableData.filter(item => {
          return Object.values(item).some(value =>
            String(value).toLowerCase().includes(query)
          );
        });
      }
      this.currentPage = 1;
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
  },
  mounted() {
    this.filteredDataCache = this.tableData;
  },
};
</script>

<style scoped>
.ptl-dvc {
  width: 100%;
  max-width: 1000px;
  margin: 30px auto;
  font-family: Arial, sans-serif;
  text-align: left;
}

.title {
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

/* 使用 flex 布局 */
.controls {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.search-box {
  display: flex;
  align-items: center;
}

.search-input {
  padding: 10px;
  width: 250px;
  margin-right: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.search-button {
  padding: 10px 15px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.search-button:hover {
  background-color: #45a049;
}

.data-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  font-size: 14px;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

th {
  background-color: #f4f4f4;
  color: #333;
  text-align: left;
}

tr:nth-child(even) {
  background-color: #f9f9f9;
}

tr:hover {
  background-color: #f1f1f1;
}

.pagination {
  margin-top: 20px;
  text-align: center;
}

.page-button {
  margin: 0 5px;
  padding: 8px 15px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.page-button:hover {
  background-color: #0056b3;
}

.page-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.page-info {
  font-weight: bold;
  margin: 0 10px;
  color: #333;
}

.quantity-cell {
  background-color: #f0f7ff; /* 浅蓝色背景 */
  color: #007BFF; /* 蓝色文字 */
  font-weight: bold;
}

</style>