<template>
  <div class="dashboard-container">
    <div class="dashboard-text">用户名:{{ name }}</div>
    <div class="dashboard-text">用户角色:<span v-for="role in roles" :key="role">{{ role }}</span></div>
    <el-button @click="to">授权</el-button>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  },
  methods:{
    to(){
      this.$router.push({
        path: "",
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
