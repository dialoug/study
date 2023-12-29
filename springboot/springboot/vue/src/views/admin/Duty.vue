<template>
  <div>
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="24" style="margin-bottom: 20px;">
          <i style="font-size: 20px" class="el-icon-date"></i>
          <span style="font-size: 20px;margin-left: 5px;">请选择值班日期</span>
        </el-col>
        <el-col :span="2" v-for="item in dateList">
          <el-button @click="checkDate(item)" type="primary" class="btn" icon="el-icon-date">{{ item }}</el-button>
        </el-col>
        <el-col v-if="flag1" :span="24">
          <el-card style="margin-top: 20px;">
            <div v-for="item in departments">
              <el-divider></el-divider>
              <div style="font-size: 24px;font-weight: 700;margin-top: -5px;">
                <i style="padding-right: 10px;" class="el-icon-monitor"></i>
                <span>{{ item.name }}</span>
              </div>
              <div style="margin-top: 4px;">
                <el-button v-for="d in item.children" @click="doctorList(d.id)" class="btn">{{ d.name }}</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col v-if="flag2">
          <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
            <el-button class="ml-5" type="primary" @click="doctorList()">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
          </div>
          <el-table :data="tableData" max-height="680" default-expand-all>
            <el-table-column prop="name" label="项目"></el-table-column>
            <el-table-column prop="gender" label="性别">
              <template v-slot="scope">
                <el-tag>{{ scope.row.gender }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="title" label="职位">
              <template v-slot="scope">
                <el-tag>{{ scope.row.title }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="departmentId" label="科室">
              <template v-slot="scope">
                <el-tag v-if="scope.row.departmentId">{{
                    departmentChildren.find(v => v.id === scope.row.departmentId) ? departmentChildren.find(v => v.id === scope.row.departmentId).name : ''
                  }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="300" align="center">
              <template v-slot="scope">
                <el-button v-if="schedulingList.find(v => v.doctorId === scope.row.id) == null" type="success" @click="addScheduling(scope.row.id)"><i class="el-icon-document"></i> 排班</el-button>
                <el-button v-else type="danger" @click="delScheduling(scope.row.id)"><i class="el-icon-delete"></i> 取消排班</el-button>
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
        </el-col>

      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Duty",
  data() {
    return {
      dateList: [],
      departments: [],
      tableData: [],
      departmentChildren: [],
      schedulingList: [],
      flag1: false,
      flag2: false,
      dateValue: '',
      pageNum: 1,
      pageSize: 10,
      total: 0,
      name: '',
      departmentId: '',
    }
  },
  created() {
    this.load()
  }
  ,
  methods: {
    load() {
      this.request.get('/duty/getDutyDate',{
        params: {
          day: 12
        }
      }).then(res => {
        this.dateList = res.data
      })

      this.request.get('/department/list').then(res => {
        this.departments = res.data
        this.departmentChildren = this.departments.filter(v => v.children).flatMap(v => v.children)
      })
    },
    checkDate(date) {
      this.dateValue = date
      this.flag1 = true
      this.flag2 = false
    },
    doctorList(departmentId) {
      if (departmentId != null){
        this.departmentId = departmentId
      }

      this.request.get('/scheduling/listByDate',{
        params:{
          date: this.dateValue
        }
      }).then(res => {
        this.schedulingList = res.data
      })

      this.request.get('/user/doctorPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          departmentId: this.departmentId
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
        this.flag1 = false
        this.flag2 = true
      })
    },
    addScheduling(id) {
      this.request.post('/scheduling',{
        date: this.dateValue,
        doctorId: id
      }).then(res => {
        if (res.code === '200'){
          this.doctorList()
        }
      })
    },
    delScheduling(id) {
      this.request.delete('/scheduling/remove',{
        params: {
          date: this.dateValue,
          doctorId: id
        }
      }).then(res => {
        if (res.code === '200'){
          this.doctorList()
        }
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.doctorList()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.doctorList()
    },
    reset() {
      this.pageNum = 1
      this.pageSize = 10
      this.name = ''
      this.doctorList()
    }
  }
}
</script>

<style scoped>
.headerBg {
  background: #eee !important;
}

.el-col {
  margin: 5px 0;
}

.btn {
  height: 35px;
  font-size: 15px;
}
</style>