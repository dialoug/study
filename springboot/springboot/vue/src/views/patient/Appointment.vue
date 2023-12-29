<template>
  <div>
    <el-card style="margin-top: 20px;">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/appointment' }">科室选择</el-breadcrumb-item>
<!--        <el-breadcrumb-item>日期选择</el-breadcrumb-item>-->
<!--        <el-breadcrumb-item>挂号</el-breadcrumb-item>-->
      </el-breadcrumb>
      <div v-for="item in tableData">
        <el-divider></el-divider>
        <div style="font-size: 24px;font-weight: 700;margin-top: -5px;">
          <i style="padding-right: 10px;" class="el-icon-monitor"></i>
          <span>{{ item.name }}</span>
        </div>
        <div style="margin-top: 4px;">
          <el-button type="primary" v-for="d in item.children" @click="dataSelect(d.id,d.name)" class="btn">{{d.name }}</el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Appointment",
  data() {
    return {
      tableData: []
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get('/department/list').then(res => {
        this.tableData = res.data
      })
    },
    dataSelect(id,name) {
      this.$router.push({
        name: 'DateSelect',
        query: {
          id: id,
          name: name
        }
      })
    }
  }
}
</script>

<style scoped>
.btn {
  height: 40px;
  font-size: 15px;
}
</style>