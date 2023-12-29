<template>
  <div style="color: #666;font-size: 14px;">
    <div style="padding-bottom: 20px">
      <b>您好！{{ user.name }}</b>
    </div>
    <el-card>
      欢迎使用本系统
      <el-divider/>
      现在时间: <span>{{ timeNow }}</span>
    </el-card>

  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      timeNow: ''
    }
  },
  created() {
    setInterval(this.time, 1000)
  },
  methods: {
    time() {
      let vWeek, vWeek_s, vDay;
      vWeek = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
      let date = new Date();
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();
      let hours = date.getHours();
      let minutes = date.getMinutes();
      let seconds = date.getSeconds();
      if (hours < 10) {
        hours = "0" + hours;
      }
      if (minutes < 10) {
        minutes = "0" + minutes;
      }
      if (seconds < 10) {
        seconds = "0" + seconds;
      }
      vWeek_s = date.getDay();
      this.timeNow = year + "年" + month + "月" + day + "日" + "\t" + hours + ":" + minutes + ":" + seconds + "\t" + vWeek[vWeek_s]
    }
  }
}
</script>
