<template>
  <div class="ptl-dvc">
    <h2 class="title">应急物资清单</h2>
    <div class="controls">
      <div style="display: flex;justify-content: space-between;width: 100%">
        <div>
          <el-input
            placeholder="请输入物资名称进行搜索..."
            prefix-icon="el-icon-search"
            clearable
            @clear="initEmps"
            style="width: 350px;margin-right: 10px"
            v-model="keyword"
            @keydown.enter.native="initEmps"
            :disabled="showAdvanceSearchView">
          </el-input>
          <el-button icon="el-icon-search" type="primary" @click="initEmps" :disabled="showAdvanceSearchView">
            搜索
          </el-button>
          <el-button type="primary" @click="showAdvanceSearchView = !showAdvanceSearchView">
            <i :class="showAdvanceSearchView?'fa fa-angle-double-up':'fa fa-angle-double-down'"
               aria-hidden="true"></i>
            高级搜索
          </el-button>
        </div>
        <div style="margin-left: 20px;">
          <el-button type="primary" icon="el-icon-plus" @click="showAddEmpView">添加物资</el-button>
        </div>
      </div>
    </div>
    
    <!-- 添加高级搜索面板 -->
    <transition name="slide-fade">
      <div v-show="showAdvanceSearchView"
           style="border: 1px solid #409eff;border-radius: 5px;box-sizing: border-box;padding: 5px;margin: 10px 0px;">
        <el-row>
          <el-col :span="6">
            物资名称:
            <el-input v-model="searchValue.name" size="mini" style="width: 130px"></el-input>
          </el-col>
          <el-col :span="6">
            物资编号:
            <el-input v-model="searchValue.number" size="mini" style="width: 130px"></el-input>
          </el-col>
          <el-col :span="6">
            储存位置:
            <el-input v-model="searchValue.store" size="mini" style="width: 130px"></el-input>
          </el-col>
          <el-col :span="6">
            生产厂家:
            <el-input v-model="searchValue.company" size="mini" style="width: 130px"></el-input>
          </el-col>
        </el-row>
        <el-row style="margin-top: 10px">
          <el-col :span="24" style="text-align: right">
            <el-button size="mini" @click="showAdvanceSearchView = false">取消</el-button>
            <el-button size="mini" icon="el-icon-search" type="primary" @click="initEmps('advanced')">搜索</el-button>
          </el-col>
        </el-row>
      </div>
    </transition>

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
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in tableData" :key="index">
          <td>{{ item.id }}</td>
          <td>{{ item.name }}</td>
          <td>{{ item.scale }}</td>
          <td>{{ item.number }}</td>
          <td>{{ item.company }}</td>
          <td>{{ item.store }}</td>
          <td class="quantity-cell">{{ item.num }}</td>
          <td>{{ item.etc }}</td>
          <td>
            <el-button size="mini" @click="showEditEmpView(item)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteEmp(item)">删除</el-button>
          </td>
        </tr>
      </tbody>
    </table>
    <div style="display: flex;justify-content: flex-end;margin-top: 10px">
      <el-pagination
        background
        @current-change="currentChange"
        @size-change="sizeChange"
        layout="sizes, prev, pager, next, jumper, ->, total, slot"
        :total="total"
        :page-size="size"
        :current-page="page">
      </el-pagination>
    </div>
    <div v-if="loading" class="loading-overlay">
      <div class="loading-spinner"></div>
    </div>

    <!-- 添加/编辑对话框 -->
    <el-dialog :title="title" :visible.sync="dialogVisible" width="60%">
      <el-form :model="emp" :rules="rules" ref="empForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="物资名称:" prop="name">
              <el-input v-model="emp.name" size="mini" style="width: 200px;"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="型号规格:" prop="scale">
              <el-input v-model="emp.scale" size="mini" style="width: 200px;"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="物资编号:" prop="number">
              <el-input v-model="emp.number" size="mini" style="width: 200px;"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生产厂家:" prop="company">
              <el-input v-model="emp.company" size="mini" style="width: 200px;"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="储存位置:" prop="store">
              <el-input v-model="emp.store" size="mini" style="width: 200px;"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数量:" prop="num">
              <el-input v-model="emp.num" size="mini" style="width: 200px;"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注:" prop="etc">
              <el-input v-model="emp.etc" type="textarea" size="mini" style="width: 600px;"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="doAddEmp">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'PtlEd',
  data() {
    return {
      tableData: [], // 清空初始数据，改为从后端获取
      searchQuery: '',
      currentPage: 1,
      itemsPerPage: 10,
      loading: false, // 添加加载状态标志
      total: 0,
      page: 1,
      size: 10,
      keyword: '',
      showAdvanceSearchView: false,
      searchValue: {
        name: '',
        number: '',
        store: '',
        company: ''
      },
      dialogVisible: false,
      title: '',
      emp: {
        id: null,
        name: '',
        scale: '',
        number: '',
        company: '',
        store: '',
        num: '',
        etc: ''
      },
      rules: {
        name: [{required: true, message: '请输入物资名称', trigger: 'blur'}],
        number: [{required: true, message: '请输入物资编号', trigger: 'blur'}],
        store: [{required: true, message: '请输入储存位置', trigger: 'blur'}],
        num: [{required: true, message: '请输入数量', trigger: 'blur'}]
      }
    };
  },
  methods: {
    async fetchData() {
      this.loading = true;
      try {
        const response = await this.$axios.get('/ptl/ed');
        if (response.status === 200) {
          this.tableData = response.data;
        }
      } catch (error) {
        console.error('获取物资清单失败:', error);
        this.$message.error('获取物资清单失败');
      } finally {
        this.loading = false;
      }
    },

    async performSearch() {
      this.page = 1;
      this.keyword = this.searchQuery;
      await this.initEmps();
    },

    async initEmps(type) {
      this.loading = true;
      let url = '/ptl/ed?page=' + this.page + '&size=' + this.size;  // 去掉多余的斜杠
      
      if (type && type == 'advanced') {
        // 添加高级搜索参数
        if (this.searchValue.name) {
          url += '&name=' + this.searchValue.name;
        }
        if (this.searchValue.number) {
          url += '&number=' + this.searchValue.number;
        }
        if (this.searchValue.store) {
          url += '&store=' + this.searchValue.store;
        }
        if (this.searchValue.company) {
          url += '&company=' + this.searchValue.company;
        }
      } else {
        url += "&keyword=" + this.keyword;
      }

      try {
        const response = await this.getRequest(url);
        if (response) {
          this.tableData = response.data;
          this.total = response.total;
        }
      } catch (error) {
        console.error('获取物资清单失败:', error);
        this.$message.error('获取物资清单失败');
      } finally {
        this.loading = false;
      }
    },

    sizeChange(currentSize) {
      this.size = currentSize;
      this.page = 1;  // 重置到第一页
      this.initEmps();
    },

    currentChange(currentPage) {
      this.page = currentPage;
      this.initEmps();
    },

    showAddEmpView() {
      this.title = '添加物资';
      this.emp = {
        id: null,
        name: '',
        scale: '',
        number: '',
        company: '',
        store: '',
        num: '',
        etc: ''
      };
      this.dialogVisible = true;
    },
    
    showEditEmpView(data) {
      this.title = '编辑物资';
      this.emp = Object.assign({}, data);
      this.dialogVisible = true;
    },

    doAddEmp() {
      this.$refs['empForm'].validate(async (valid) => {
        if (valid) {
          let resp;
          if (this.emp.id) {
            resp = await this.putRequest('/ptl/ed', this.emp); // 确保URL与后端一致
          } else {
            resp = await this.postRequest('/ptl/ed', this.emp);
          }
          if (resp) {
            this.dialogVisible = false;
            this.initEmps();
          }
        }
      });
    },

    deleteEmp(data) {
      this.$confirm('此操作将永久删除【' + data.name + '】, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let resp = await this.deleteRequest('/ptl/ed/' + data.id);
        if (resp) {
          this.initEmps();
        }
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    }
  },
  mounted() {
    this.initEmps(); // 组件挂载时获取数据
  }
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

.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.loading-spinner {
  width: 50px;
  height: 50px;
  border: 5px solid #f3f3f3;
  border-top: 5px solid #3498db;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

/* 添加过渡动画样式 */
.slide-fade-enter-active {
  transition: all .8s ease;
}

.slide-fade-leave-active {
  transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}

.slide-fade-enter, .slide-fade-leave-to {
  transform: translateX(10px);
  opacity: 0;
}
</style>