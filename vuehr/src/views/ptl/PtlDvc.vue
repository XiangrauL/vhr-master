<template>
  <div class="ptl-dvc">
    <h2 class="title">设备管理</h2>
    <div class="controls">
      <div class="button-group">
        <button @click="showTable('table1')" class="table-button">进站区</button>
        <button @click="showTable('table2')" class="table-button">过滤区</button>
        <button @click="showTable('table3')" class="table-button">计量区</button>
        <button @click="showTable('table4')" class="table-button">调压区</button>
        <button @click="showTable('table5')" class="table-button">出战区</button>
      </div>
      <div class="search-box">
        <input type="text" v-model="searchQuery" placeholder="搜索..." class="search-input" />
        <button @click="performSearch" class="search-button">搜索</button>
      </div>
    </div>
    <table class="data-table">
      <thead>
        <tr>
          <th>编号</th>
          <th>工艺区域</th>
          <th>名称</th>
          <th>压力等级</th>
          <th>尺寸规格</th>
          <th>快速跳转</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in paginatedData" :key="index">
          <td>{{ item.column1 }}</td>
          <td>{{ item.column2 }}</td>
          <td>{{ item.column3 }}</td>
          <td>{{ item.column4 }}</td>
          <td>{{ item.column5 }}</td>
          <!-- 为最后一列单元格添加一个特定的类 -->
          <td class="highlight-text">{{ item.column6 }}</td>
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
  name: 'PtlDvc',
  data() {
    return {
      currentTable: 'table1', // 当前显示的表格
      tableData: {
        table1: [
          { column1: '1', column2: '进站区', column3: '气液联动阀', column4: 'CLASS400', column5: '800', column6: '跳转' },
          { column1: '2', column2: '进站区', column3: '手动球阀', column4: 'CLASS400', column5: '400*350', column6: '跳转' },
          { column1: '3', column2: '进站区', column3: '电动球阀', column4: 'CLASS400', column5: '50*40', column6: '跳转' },
          { column1: '4', column2: '进站区', column3: '节流截止阀', column4: 'CLASS400', column5: '350*300', column6: '跳转' },
          { column1: '5', column2: '进站区', column3: '气液联动阀2', column4: 'CLASS400', column5: '250*200', column6: '跳转' },
          { column1: '6', column2: '进站区', column3: '手动球阀2', column4: 'CLASS400', column5: '800', column6: '跳转' },
          { column1: '7', column2: '进站区', column3: '电动球阀2', column4: 'CLASS400', column5: '250*200', column6: '跳转' },
          { column1: '8', column2: '进站区', column3: '节流截止阀2', column4: 'CLASS400', column5: '800', column6: '跳转' },
          { column1: '9', column2: '进站区', column3: '气液联动阀3', column4: 'CLASS400', column5: '400*350', column6: '跳转' },
          { column1: '10', column2: '进站区', column3: '手动球阀3', column4: 'CLASS400', column5: '800', column6: '跳转' },
          { column1: '11', column2: '进站区', column3: '电动球阀3', column4: 'CLASS400', column5: '800', column6: '跳转' },
          // ...其他数据行
        ],
        table2: [
          { column1: '12', column2: '过滤区', column3: '过滤器', column4: 'CLASS400', column5: '400*350', column6: '跳转' },
          { column1: '13', column2: '过滤区', column3: '旋风分离器', column4: 'CLASS400', column5: '50*40', column6: '跳转' },
          { column1: '14', column2: '过滤区', column3: '过滤分离器', column4: 'CLASS400', column5: '350*300', column6: '跳转' },
          { column1: '15', column2: '过滤区', column3: '气液聚结分离器', column4: 'CLASS400', column5: '50*40', column6: '跳转' },
          { column1: '16', column2: '过滤区', column3: '过滤器2', column4: 'CLASS400', column5: '250*200', column6: '跳转' },
          { column1: '17', column2: '过滤区', column3: '旋风分离器2', column4: 'CLASS400', column5: '50*40', column6: '跳转' },
          { column1: '18', column2: '过滤区', column3: '过滤分离器2', column4: 'CLASS400', column5: '50*40', column6: '跳转' },
          { column1: '19', column2: '过滤区', column3: '气液聚结分离器2', column4: 'CLASS400', column5: '250*200', column6: '跳转' },
          { column1: '20', column2: '过滤区', column3: '过滤器3', column4: 'CLASS400', column5: '50*40', column6: '跳转' },
          { column1: '21', column2: '过滤区', column3: '旋风分离器3', column4: 'CLASS400', column5: '350*300', column6: '跳转' },
          { column1: '22', column2: '过滤区', column3: '气液聚结分离器3', column4: 'CLASS400', column5: '50*40', column6: '跳转' },
          // ...其他数据行
        ],
        table3: [
          { column1: '23', column2: '计量区', column3: '压力表', column4: 'CLASS400', column5: '400*350', column6: '跳转' },
          { column1: '24', column2: '计量区', column3: '差压表', column4: 'CLASS400', column5: '50*40', column6: '跳转' },
          { column1: '25', column2: '计量区', column3: '超声波燃气流量计', column4: 'CLASS400', column5: '350*300', column6: '跳转' },
          { column1: '26', column2: '计量区', column3: '涡轮燃气流量计', column4: 'CLASS400', column5: '250*200', column6: '跳转' },
          { column1: '27', column2: '计量区', column3: '热式燃气流量计', column4: 'CLASS400', column5: '600*500', column6: '跳转' },
          { column1: '28', column2: '计量区', column3: '比例阀', column4: 'CLASS400', column5: '250*200', column6: '跳转' },
          { column1: '29', column2: '计量区', column3: '微型燃气流量计', column4: 'CLASS400', column5: '50*40', column6: '跳转' },
          { column1: '30', column2: '计量区', column3: '整流器', column4: 'CLASS400', column5: '350*300', column6: '跳转' },
          { column1: '31', column2: '计量区', column3: '测压器', column4: 'CLASS400', column5: '250*200', column6: '跳转' },
          { column1: '32', column2: '计量区', column3: '压力表2', column4: 'CLASS400', column5: '250*200', column6: '跳转' },
          { column1: '33', column2: '计量区', column3: '差压表2', column4: 'CLASS400', column5: '400*350', column6: '跳转' },
          // ...其他数据行
        ],
        table4: [
          { column1: '34', column2: '调压区', column3: '轴流式调压器', column4: 'CLASS400', column5: '600*500', column6: '跳转' },
          { column1: '35', column2: '调压区', column3: '闸阀', column4: 'CLASS400', column5: '600*500', column6: '跳转' },
          { column1: '36', column2: '调压区', column3: '球阀', column4: 'CLASS400', column5: '250*200', column6: '跳转' },
          { column1: '37', column2: '调压区', column3: '蝶阀', column4: 'CLASS400', column5: '600*500', column6: '跳转' },
          { column1: '38', column2: '调压区', column3: '安全切断阀', column4: 'CLASS400', column5: '600*500', column6: '跳转' },
          { column1: '39', column2: '调压区', column3: '填料式过滤器', column4: 'CLASS400', column5: '250*200', column6: '跳转' },
          { column1: '40', column2: '调压区', column3: '离心式过滤器', column4: 'CLASS400', column5: '250*200', column6: '跳转' },
          { column1: '41', column2: '调压区', column3: '安全放散阀', column4: 'CLASS400', column5: '400*350', column6: '跳转' },
          { column1: '42', column2: '调压区', column3: '压力传感器', column4: 'CLASS400', column5: '350*300', column6: '跳转' },
          { column1: '43', column2: '调压区', column3: '报警装置', column4: 'CLASS400', column5: '50*40', column6: '跳转' },
          { column1: '44', column2: '调压区', column3: '旁通管', column4: 'CLASS400', column5: '50*40', column6: '跳转' },
          // ...其他数据行
        ],
        table5: [
          { column1: '45', column2: '出战区', column3: '电动球阀', column4: 'CLASS400', column5: '350*300', column6: '跳转' },
          { column1: '46', column2: '出战区', column3: '安全阀', column4: 'CLASS400', column5: '400*350', column6: '跳转' },
          { column1: '47', column2: '出战区', column3: '球阀', column4: 'CLASS400', column5: '250*200', column6: '跳转' },
          { column1: '48', column2: '出战区', column3: '节流截止阀', column4: 'CLASS400', column5: '50*40', column6: '跳转' },
          { column1: '49', column2: '出战区', column3: '放空管', column4: 'CLASS400', column5: '600*500', column6: '跳转' },
          { column1: '50', column2: '出战区', column3: '电动球阀2', column4: 'CLASS400', column5: '800', column6: '跳转' },
          { column1: '51', column2: '出战区', column3: '安全阀2', column4: 'CLASS400', column5: '120*150', column6: '跳转' },
          { column1: '52', column2: '出战区', column3: '球阀2', column4: 'CLASS400', column5: '350*300', column6: '跳转' },
          { column1: '53', column2: '出战区', column3: '节流截止阀2', column4: 'CLASS400', column5: '400*350', column6: '跳转' },
          { column1: '54', column2: '出战区', column3: '放空管2', column4: 'CLASS400', column5: '50*40', column6: '跳转' },
          { column1: '55', column2: '出战区', column3: '电动球阀3', column4: 'CLASS400', column5: '50*40', column6: '跳转' },
          // ...其他数据行
        ],
      },
      searchQuery: '',
      currentPage: 1,
      itemsPerPage: 10, // 每页显示十行
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
    showTable(tableName) {
      this.currentTable = tableName;
      this.filteredDataCache = this.tableData[tableName];
      this.currentPage = 1; // 切换表格时重置到第一页
    },
    performSearch() {
      const query = this.searchQuery.trim().toLowerCase();
      const currentTableData = this.tableData[this.currentTable];
      if (!query) {
        this.filteredDataCache = currentTableData;
      } else {
        this.filteredDataCache = currentTableData.filter(item => {
          return Object.values(item).some(value =>
            String(value).toLowerCase().includes(query)
          );
        });
      }
      this.currentPage = 1; // 搜索时重置到第一页
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
    // 初始化时显示第一个表格的数据
    this.filteredDataCache = this.tableData[this.currentTable];
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

.button-group {
  display: flex;
}

.table-button {
  margin: 0 5px;
  padding: 10px 35px;
  font-size: 16px;
  font-weight: bold;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.table-button:hover {
  background-color: #0056b3;
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
}

th, td {
  border: 1px solid #ddd;
  padding: 12px;
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

.highlight-text {
  color: #007BFF; /* 设置文字颜色为深蓝色 */
  font-weight: bold; /* 设置文字粗细 */
  /* 如果需要，可以添加背景颜色 */
  /* background-color: yellow; */
}

/* 添加鼠标悬停效果 */
.highlight-text:hover {
  color: #0056b3; /* 改变文字颜色 */
  text-decoration: underline; /* 添加下划线效果 */
  /* 可选：改变背景颜色 */
  /* background-color: #fff3e6; */
  cursor: pointer; /* 鼠标变为手型图标 */
}
</style>