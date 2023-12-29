<template>
  <div>
<!--    <div style="margin: 10px 0">-->
<!--      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>-->
<!--      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>-->
<!--      <el-button type="warning" @click="reset">重置</el-button>-->
<!--    </div>-->

    <div style="margin: 10px 0">
<!--      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>-->
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch">
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>
    <el-table :data="tableData" max-height="680" :header-cell-class-name="'headerBg'"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
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
      <el-table-column prop="state" label="挂号状态" sortable>
        <template v-slot="scope">
          <el-tag v-if="scope.row.state === '1'" type="success">已完成</el-tag>
          <el-tag v-else-if="scope.row.state ==='0'" type="danger">未完成</el-tag>
          <el-tag v-else type="warning">已过期</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="priceState" label="缴费状态" sortable>
        <template v-slot="scope">
          <el-tag v-if="scope.row.priceState === '1'" type="success">已缴费</el-tag>
          <el-tag v-else-if="scope.row.state === '1' & scope.row.priceState === '0'" type="danger">未缴费</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="180" align="center">
        <template v-slot="scope">
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)">
            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
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

<!--    <el-dialog title="信息" :visible.sync="dialogFormVisible" width="40%">-->
<!--      <el-form label-width="120px" size="small" style="width: 80%; margin: 0 auto">-->
<!--        <el-form-item label="患者id">-->
<!--          <el-input v-model="form.patientId" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="医生id">-->
<!--          <el-input v-model="form.doctorId" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="挂号时间">-->
<!--          <el-date-picker v-model="form.createTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"-->
<!--                          placeholder="选择日期时间"></el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="结束时间">-->
<!--          <el-date-picker v-model="form.endTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"-->
<!--                          placeholder="选择日期时间"></el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="病因">-->
<!--          <el-input v-model="form.etiology" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="药物">-->
<!--          <el-input v-model="form.drug" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="检测项目">-->
<!--          <el-input v-model="form.check" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="建议">-->
<!--          <el-input v-model="form.advice" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="总价">-->
<!--          <el-input v-model="form.totalPrice" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="支付状态">-->
<!--          <el-input v-model="form.priceState" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->

<!--      </el-form>-->
<!--      <div slot="footer" class="dialog-footer">-->
<!--        <el-button @click="dialogFormVisible = false">取 消</el-button>-->
<!--        <el-button type="primary">确 定</el-button>-->
<!--      </div>-->
<!--    </el-dialog>-->
  </div>
</template>

<script>
export default {
  name: "Register",
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
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    // handleAdd() {
    //   this.dialogFormVisible = true
    //   this.form = {}
    // },
    del(id) {
      this.request.delete("/registration/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    delBatch() {
      if (!this.multipleSelection.length) {
        this.$message.error("请选择需要删除的数据")
        return
      }
      let ids = this.multipleSelection.map(v => v.id)
      this.request.post("/registration/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
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
