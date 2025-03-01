<template>
  <div class="ptl-dvc">
    <h2 class="title">资料管理</h2>
    <div class="controls">
      <div class="button-group">
        <button @click="showTable('table1')" class="table-button">资料</button>
        <button @click="showTable('table2')" class="table-button">工作票</button>
      </div>
      <div class="search-box">
        <input type="text" v-model="searchQuery" placeholder="搜索..." class="search-input" />
        <button @click="performSearch" class="search-button">搜索</button>
      </div>
    </div>
    <table class="data-table">
      <thead>
        <tr>
          <th>序号</th>
          <th>资料名称</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in paginatedData" :key="index">
          <td>{{ item.column1 }}</td>
          <td>{{ item.column2 }}</td>
          <td>
            <span class="highlight-text">查看</span>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <span class="highlight-text">下载</span>
          </td>
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
          { column1: '1', column2: '超声波流量计技术规程' },
          { column1: '2', column2: '孔板流量计技术规程' },
          { column1: '3', column2: '通用仪表技术规程' },
          { column1: '4', column2: '安全切断阀技术规程' },
          { column1: '5', column2: '气相色谱仪检定规程' },
          { column1: '6', column2: '绝缘接头保护装置技术规程' },
          { column1: '7', column2: '气液联动执行机构技术规程' },
          { column1: '8', column2: '自立式调压阀技术规程' },
          { column1: '9', column2: '污水处理系统技术规程' },
          { column1: '10', column2: '电动调节阀技术规程' },
          { column1: '11', column2: '其他规程' },
          // ...其他数据行
        ],
        table2: [
          { column1: '12', column2: '工作票1' },
          { column1: '13', column2: '工作票2' },
          { column1: '14', column2: '工作票3' },
          { column1: '15', column2: '工作票4' },
          { column1: '16', column2: '工作票5' },
          
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
  margin-left: 200px;
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
  padding: 4px 8px;
  margin: 0 4px;
  display: inline-block;
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