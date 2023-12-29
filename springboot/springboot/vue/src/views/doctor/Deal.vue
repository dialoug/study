<template>
  <div>
    <el-card>
      <el-form :inline="true" :model="form" class="form" size="medium">
        <el-form-item label="挂号单:">
          <el-input v-model="form.id" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="患者名称:">
          <el-input v-model="form.patient.name" :disabled="true">
<!--            <template v-slot="scope">-->
<!--              {{ scope.row.patient.name }}-->
<!--            </template>-->
          </el-input>
        </el-form-item>
        <el-form-item label="年龄:">
          <el-input v-model="form.patient.age" :disabled="true">
<!--            <template v-slot="scope">-->
<!--              {{ scope.row.patient.age }}-->
<!--            </template>-->
          </el-input>
        </el-form-item>
        <el-form-item label="性别:">
          <el-input v-model="form.patient.gender" :disabled="true">
<!--            <template v-slot="scope">-->
<!--              {{ scope.row.patient.gender }}-->
<!--            </template>-->
          </el-input>
        </el-form-item>
        <el-form-item v-model="form.patient.phone" label="联系方式:">
          <el-input :disabled="true">
<!--            <template v-slot="scope">-->
<!--              {{ scope.row.patient.phone }}-->
<!--            </template>-->
          </el-input>
        </el-form-item>
        <el-form-item style="text-align:center;">
          <el-button type="primary" @click="save">提交</el-button>
          <el-button type="primary" @click="handleEdit">病因填写</el-button>
        </el-form-item>
      </el-form>
      <el-dialog title="报告" :visible.sync="dialogFormVisible" width="50%">
        <el-form :label-position="'top'" label-width="80px" size="small">
          <el-form-item label="病因">
            <el-input type="textarea" :rows="5" v-model="form.etiology" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="建议">
            <el-input type="textarea" :rows="5" v-model="form.advice" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleSave">确 定</el-button>
        </div>
      </el-dialog>

      <el-row>
        <div>
<!--          分一列-->
          <el-col :span="24">
            <div style="margin: 10px 0">
              <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search"
                        v-model="drugName"></el-input>
              <el-button class="ml-5" type="primary" @click="drugPage">搜索</el-button>
              <el-button type="warning" @click="resetDrug">重置</el-button>
            </div>
          </el-col>
<!--          分两列-->
          <el-col :span="12">
            <el-table :data="drugData" border stripe max-height="240" :header-cell-class-name="'headerBg'">
              <el-table-column prop="name" label="名称"></el-table-column>
              <el-table-column prop="number" label="数量"></el-table-column>
              <el-table-column prop="unit" label="单位"></el-table-column>
              <el-table-column prop="price" label="价格"></el-table-column>
              <el-table-column label="操作" width="280" align="center">
                <template v-slot="scope">
                  <el-button type="success" v-if="scope.row.number > 0" @click="appendDrug(scope.row)"><i
                      class="el-icon-circle-plus-outline"></i> 增加
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="padding: 10px 0">
              <el-pagination
                  @size-change="handleDrugSizeChange"
                  @current-change="handleDrugCurrentChange"
                  :current-page="drugPageNum"
                  :page-sizes="[2, 4, 8, 10]"
                  :page-size="drugPageSize"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="drugTotal">
              </el-pagination>
            </div>
          </el-col>
          <el-col :span="12">
            <el-table :data="drugList" border stripe max-height="200" :header-cell-class-name="'headerBg'">
              <el-table-column prop="name" label="名称"></el-table-column>
              <el-table-column prop="number" label="数量"></el-table-column>
              <el-table-column prop="price" label="价格"></el-table-column>
              <el-table-column prop="total" label="小计"></el-table-column>
              <el-table-column label="操作" width="280" align="center">
                <template v-slot="scope">
                  <el-button type="danger" @click="removeDrug(scope.row)"><i class="el-icon-delete"></i> 移除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="text-align:center;margin-top: 10px;">
              <el-tag>
                总计: {{ drugTotalPrice }}元
              </el-tag>
            </div>
          </el-col>
        </div>
        <div>
          <el-col :span="24">
            <div style="margin: 10px 0">
              <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search"
                        v-model="checksName"></el-input>
              <el-button class="ml-5" type="primary" @click="checksPage">搜索</el-button>
              <el-button type="warning" @click="resetChecks">重置</el-button>
            </div>
          </el-col>
          <el-col :span="12">
            <el-table :data="checksData" border stripe max-height="240" :header-cell-class-name="'headerBg'">
              <el-table-column prop="name" label="名称"></el-table-column>
              <el-table-column prop="price" label="价格"></el-table-column>
              <el-table-column label="操作" width="280" align="center">
                <template v-slot="scope">
                  <el-button type="success" @click="appendChecks(scope.row)"><i
                      class="el-icon-circle-plus-outline"></i> 增加
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="padding: 10px 0">
              <el-pagination
                  @size-change="handleChecksSizeChange"
                  @current-change="handleChecksCurrentChange"
                  :current-page="checksPageNum"
                  :page-sizes="[2, 4, 8, 10]"
                  :page-size="checksPageSize"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="checksTotal">
              </el-pagination>
            </div>
          </el-col>
          <el-col :span="12">
            <el-table :data="checksList" border stripe max-height="200" :header-cell-class-name="'headerBg'">
              <el-table-column prop="name" label="名称"></el-table-column>
              <el-table-column prop="price" label="价格"></el-table-column>
              <el-table-column label="操作" width="280" align="center">
                <template v-slot="scope">
                  <el-button type="danger" @click="removeChecks(scope.row)"><i class="el-icon-delete"></i> 移除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="text-align:center;margin-top: 10px;">
              <el-tag>
                总计: {{ checksTotalPrice }}元
              </el-tag>
            </div>
          </el-col>
        </div>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Deal",
  data() {
    return {
      form: this.$route.params.patient,
      drugData: [],  // 全部药品
      drugList: [],  // 所选药品
      drugTotalPrice: 0,  // 所选药品总价
      drugTotal: 0,  // 药品种类数目
      drugPageNum: 1,  //
      drugPageSize: 4,
      drugName: "",
      checksData: [],  // 全部检查
      checksList: [],  // 所选检查
      checksTotalPrice: 0,  // 所选检查总价
      checksTotal: 0,  // 检查种类数目
      checksPageNum: 1,
      checksPageSize: 4,
      checksName: "",
      dialogFormVisible: false,
    }
  },
  created() {
    this.drugPage()
    this.checksPage()
    console.log()
  },
  methods: {
    drugPage() {
      this.request.get('/drug/page', {
        params: {
          pageNum: this.drugPageNum,
          pageSize: this.drugPageSize,
          name: this.drugName
        }
      }).then(res => {
        this.drugData = res.data.records
        this.drugTotal = res.data.total
      })
    },
    resetDrug() {
      this.drugName = ""
      this.drugPage()
    },
    // 选择每页药品条数
    handleDrugSizeChange(pageSize) {
      this.drugPageSize = pageSize
      this.drugPage()
    },
    // 选择药品页码
    handleDrugCurrentChange(pageNum) {
      this.drugPageNum = pageNum
      this.drugPage()
    },
    appendDrug(row) {
      let drug = this.drugList.find(v => v.id === row.id)
      if (!drug) {  // 如果是新增药品
        let temp = JSON.parse(JSON.stringify(row))  // 复制一份药品库中指定药品的信息
        temp.total = row.price
        temp.number = 1
        this.drugList.push(temp)
        row.number -= 1
      } else {  // 如果是增加数量
        drug.number += 1
        drug.total = drug.total + drug.price
        row.number -= 1
      }
      this.drugTotalPrice = 0
      this.drugList.forEach(v => {
        this.drugTotalPrice += v.total
      })
    },
    removeDrug(row) {
      this.drugList.splice(this.drugList.indexOf(row), 1)
      this.drugTotalPrice -= row.total
      this.drugData.find(v => v.id === row.id).number += row.number
    },
    // 检查单
    checksPage() {
      this.request.get('/checks/page', {
        params: {
          pageNum: this.checksPageNum,
          pageSize: this.checksPageSize,
          name: this.checksName
        }
      }).then(res => {
        this.checksData = res.data.records
        this.checksTotal = res.data.total
      })
    },
    resetChecks() {
      this.checksName = ""
      this.checksPage()
    },
    handleChecksSizeChange(pageSize) {
      this.checksPageSize = pageSize
      this.checksPage()
    },
    handleChecksCurrentChange(pageNum) {
      this.checksPageNum = pageNum
      this.checksPage()
    },
    appendChecks(row) {
      this.checksList.push(row)
      this.checksTotalPrice = 0
      this.checksList.forEach(v => {
        this.checksTotalPrice += v.price
      })
    },
    removeChecks(row) {
      this.checksList.splice(this.checksList.indexOf(row), 1)
      this.checksTotalPrice -= row.price
    },
    save() {
      if (this.drugList.length > 0) {
        this.form.drug = ''
        this.drugList.forEach(v => {
          this.form.drug += v.name + '*' + v.price + '*' + v.number + ' '
        })
        this.form.drug += ' 药物总价' + this.drugTotalPrice + '元'
      }

      if (this.checksList.length > 0) {
        this.form.checks = ''
        this.checksList.forEach(v => {
          this.form.checks += v.name + '*' + v.price + ' '
        })
        this.form.checks += ' 项目总价' + this.checksTotalPrice + '元'
      }
      this.form.totalPrice = this.checksTotalPrice + this.drugTotalPrice
      this.form.state = '1'
      this.request.post('/registration/edit', this.form).then(res => {
        if (res.code === '200') {
          this.$message.success('提交成功! 请通知患者登录系统自助缴费！')
          this.$router.push('/registerToday')
        }
      })

    },
    handleSave() {
      this.dialogFormVisible = false
      this.$message.success('信息保存成功')
    },
    handleEdit() {
      this.form.etiology = ''
      this.form.advice = ''
      this.dialogFormVisible = true
    }
  }
}
</script>

<style scoped>
.headerBg {
  background: #eee !important;
}
</style>