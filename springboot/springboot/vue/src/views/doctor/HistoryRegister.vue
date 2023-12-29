<template>
  <div>
    <!--    <div style="margin: 10px 0">
          <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
          <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
          <el-button type="warning" @click="reset">重置</el-button>
        </div>-->
    <el-table :data="tableData" max-height="680">
      <el-table-column type="expand">
        <template v-slot="scope">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="患者id"><span>{{ scope.row.patientId }}</span></el-form-item>
            <el-form-item label="医生id"><span>{{ scope.row.doctorId }}</span></el-form-item>
            <el-form-item label="挂号时间"><span>{{ scope.row.createTime }}</span></el-form-item>
            <el-form-item label="结束时间"><span>{{ scope.row.endTime }}</span></el-form-item>
            <el-form-item label="病因"><span>{{ scope.row.etiology }}</span></el-form-item>
            <el-form-item label="药物"><span>{{ scope.row.drug }}</span></el-form-item>
            <el-form-item label="检测项目"><span>{{ scope.row.checks }}</span></el-form-item>
            <el-form-item label="建议"><span>{{ scope.row.advice }}</span></el-form-item>
            <el-form-item label="总价"><span>{{ scope.row.totalPrice }}</span></el-form-item>
          </el-form>

        </template>
      </el-table-column>
      <el-table-column prop="id" label="挂号单" width="80" sortable></el-table-column>
      <el-table-column prop="state" label="挂号状态">
        <template v-slot="scope">
          <el-tag v-if="scope.row.state === '1'" type="success">已完成</el-tag>
          <el-tag v-else-if="scope.row.state ==='0'" type="danger">未完成</el-tag>
          <el-tag v-else type="warning">已过期</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="priceState" label="缴费状态">
        <template v-slot="scope">
          <el-tag v-if="scope.row.priceState === '1'" type="success">已缴费</el-tag>
          <el-tag v-else-if="scope.row.state === '1' & scope.row.priceState === '0'" type="danger">未缴费</el-tag>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "HistoryRegister",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      // name: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/registration/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          // name: this.name,
          doctorId: this.user.id
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    reset() {
      this.name = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
  }
}
</script>


<style>
.headerBg {
  background: #eee !important;
}

.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
