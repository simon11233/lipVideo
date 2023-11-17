<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter Keyword" style="margin-bottom: 30px"></el-input>
    <el-tree
      ref="subjectTree"
      :data="subjectList"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all></el-tree>
  </div>
</template>
<script>
import {getNestedTreeList}from "@/api/edu/subject"

    export default {
      name:'list',
      data(){
        return{
          filterText:'',
          subjectList:[],
          defaultProps:{
            children:'children',
            label:'title'
          }
        }
      },
      watch:{
        filterText(val){
          this.$refs.subjectTree.filter(val)
        }
      },
      created() {
        this.fetchNodeList()
      },
      methods:{
        fetchNodeList(){
          getNestedTreeList().then(response=>{
            if(response.success===true){
              this.subjectList = response.data.items;
            }
          })
        },
        filterNode(value,data){
          if(!value)return true
          return data.titlee.toLowerCase().indexOf(value.toLowerCase()) !== -1
        }
      }
    }
</script>
