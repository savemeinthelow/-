<template>
  <a-layout-header class="header">
    <div class="logo" >
      <router-link to="/welcome" style="color: white; font-size: 18px">
        畅行通
      </router-link>
    </div>
    <div style="float: right; color: white;">
      您好: {{member.mobile}} &nbsp;&nbsp;
      <router-link to="/login" style="color:white;">
        退出登录
      </router-link>
    </div>
    <a-menu
        v-model:selectedKeys="selectedKeys"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/welcome"><router-link to="/welcome"><coffee-outlined /> &nbsp;欢迎</router-link></a-menu-item>
      <a-menu-item key="/passenger"><router-link to="/passenger"><coffee-outlined /> &nbsp;乘车人管理</router-link></a-menu-item>
      <a-menu-item key="/ticket"><router-link to="/ticket"><coffee-outlined /> &nbsp;余票查询</router-link></a-menu-item>
      <a-menu-item key="/my-ticket"><router-link to="/my-ticket"><coffee-outlined /> &nbsp;我的车票</router-link></a-menu-item>
      <a-menu-item key="/seat"><router-link to="/seat"><coffee-outlined /> &nbsp;座位销售图</router-link></a-menu-item>
    </a-menu>
  </a-layout-header>
</template>

<script>

import {defineComponent, ref, watch} from 'vue';
import store from "@/store";
import router from "@/router";

export default defineComponent({
  name: "the-header-view",
  setup() {
    let member = store.state.member
    const selectedKeys = ref([]);

    watch(() => router.currentRoute.value.path, (newValue) => {
      console.log('watch', newValue);
      selectedKeys.value = [];
      selectedKeys.value.push(newValue);
    }, {immediate: true});
    return {
      selectedKeys,
      member
    };
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
.logo {
   float: left;
   height: 31px;
   width: 150px;
   color: white;
   font-size: 20px;
 }
</style>
