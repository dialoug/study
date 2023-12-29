<template>
  <div>
    <!--    <div style="margin: 10px 0">
          <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
          <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
          <el-button type="warning" @click="reset">重置</el-button>
        </div>-->
    <el-card>
      <el-table :data="tableData" border stripe max-height="680" :header-cell-class-name="'headerBg'">
        <el-table-column prop="id" label="挂号单" width="80"></el-table-column>
        <el-table-column prop="patientId" label="患者名称">
          <template v-slot="scope">
            {{ scope.row.patient.name }}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="挂号时间"></el-table-column>
        <el-table-column label="操作" width="300" align="center">
          <template v-slot="scope">
            <el-button v-if="scope.row.state === '0'" type="warning" @click="handle(scope.row)"><i class="el-icon-edit"></i> 待处理</el-button>
            <el-button v-else-if="scope.row.state === '1'" type="success"><i class="el-icon-edit"></i> 已处理</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "RegisterToday",
  data() {
    return {
      tableData: [],
      user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {},
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/registration/today", {
        params: {
          doctorId: this.user.id
        }
      }).then(res => {
        this.tableData = res.data
      })
    },
    handle(row) {
      this.$router.push({
        name: 'Deal',
        params: {
          patient: row
        }
      })
    },
  }
}
</script>


<style>
.headerBg {
  background: #eee !important;
}

.fontSize18 {
  font-size: 18px;
}

.fontSize12 {
  font-size: 12px;
}
</style>
