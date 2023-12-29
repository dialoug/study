<template>
  <div>
    <el-card style="margin-top: 20px;">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/appointment' }">科室选择</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/dateSelect'}">日期选择</el-breadcrumb-item>
<!--        <el-breadcrumb-item>挂号</el-breadcrumb-item>-->
      </el-breadcrumb>
      <el-row style="margin-top: 25px;">
        <el-col style="font-size: 24px" :span="12">
          <i class="el-icon-monitor"></i>
          <span style="margin-left: 4px;">{{ name }}列表</span>
        </el-col>
        <el-col style="margin-bottom: 20px;" :span="12">
          <div style="font-size: 20px">
            <i class="el-icon-date"></i>
            <span style="margin-left: 4px;">请选择你要挂号的日期</span>
          </div>
          <div style="margin-top: 4px;font-size: 14px">
            <el-button v-for="item in dateList" @click="doctorList(item)" class="btn" icon="el-icon-date">{{ item }}
            </el-button>
          </div>
        </el-col>
        <el-col>
          <el-table :data="tableData" border stripe max-height="680">
            <el-table-column prop="name" label="姓名" width="120"></el-table-column>
            <el-table-column prop="gender" label="性别">
              <template v-slot="scope">   <!--获取父组件元素-->
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
            <el-table-column prop="price" label="挂号费"></el-table-column>
            <el-table-column v-if="flag" label="操作" width="200" align="center">
              <template v-slot="scope">
                <el-button type="success" @click="handleEdit(scope.row)"><i class="el-icon-document"></i> 挂号</el-button>
              </template>
            </el-table-column>
          </el-table>

        </el-col>
      </el-row>
    </el-card>

    <el-dialog title="医生信息" :visible.sync="dialogFormVisible" width="40%">
      <el-form label-width="100px" size="small" :model="form">
        <el-form-item prop="time" label="挂号时间段">
          <el-select v-model="form.time">
            <el-option value="08:30-09:00">08:30-09:00</el-option>
            <el-option value="09:00-09:30">09:00-09:30</el-option>
            <el-option value="09:30-10:00">09:30-10:00</el-option>
            <el-option value="10:00-10:30">10:00-10:30</el-option>
            <el-option value="10:30-11:00">10:30-11:00</el-option>
            <el-option value="11:00-11:30">11:00-11:30</el-option>
            <el-option value="11:30-12:00">11:30-12:00</el-option>
            <el-option value="14:30-15:00">14:30-15:00</el-option>
            <el-option value="15:00-15:30">15:00-15:30</el-option>
            <el-option value="15:30-16:00">15:30-16:00</el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.date" label="挂号日期">
          <el-input :disabled="true" v-model="form.date" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="doctorName" label="医生姓名">
          <el-input :disabled="true" v-model="form.doctorName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="title" label="职称">
          <el-input :disabled="true" v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="name" label="患者姓名">
          <el-input :disabled="true" v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="idCard" label="患者身份证号">
          <el-input :disabled="true" v-model="form.idCard" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "DateSelect",
  data() {
    return {
      id: this.$route.query.id,  // 科室id
      name: this.$route.query.name,  // 科室名
      tableData: [],
      departmentChildren: [],
      dateList: [],
      checkDate: '',
      flag: false,
      dialogFormVisible: false,
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},

    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get('/duty/getDutyDate', {  // 获取7天日期
        params: {
          day: 7
        }
      }).then(res => {
        this.dateList = res.data
      })

      this.request.get('/user/listByDepartment/' + this.id).then(res => {  // 获取当前科室的医生
        this.tableData = res.data
      })

      this.request.get('/department/getChildrenList').then(res => {  // 获取所有科室的信息以根据id获得科室的名字
        this.departmentChildren = res.data  // 在代码层对数据进行拼接

      })
    },
    doctorList(date) {
      this.checkDate = date
      this.request.get('/scheduling/userByDate', {
        params: {
          date: date,
          department: this.id
        }
      }).then(res => {
        this.tableData = res.data
      })
      this.flag = true  // 提供挂号选项
    },
    handleEdit(row) {  // 挂号
      this.dialogFormVisible = true
      this.form = {
        date: this.checkDate,
        doctorName: row.name,
        doctorId: row.id,
        title: row.title,
        patientId: this.user.id,
        name: this.user.name,
        idCard: this.user.idCard
      }
    },
    save() {
      this.form.createTime = this.form.date + ' ' + this.form.time
      let date = new Date()
      this.form.id = date.getFullYear() +''+ date.getTime()
      this.request.post('/registration/save',this.form).then(res => {
        this.dialogFormVisible = false
        if (res.code === '200') {
          this.$message.success('挂号成功')
        }
      })
    }
  }
}
</script>

<style scoped>

</style>