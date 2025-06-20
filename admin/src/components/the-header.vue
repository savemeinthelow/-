<template>
  <a-layout-header class="header">
    <div class="logo" >
    <router-link to="/welcome" style="color: white; font-size: 18px">
      畅行通
    </router-link>
    </div>
    <div style="float: right; color: white;">
      欢迎使用管理控台
    </div>
    <a-menu
        v-model:selectedKeys="selectedKeys"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/welcome"><router-link to="/welcome"><coffee-outlined /> &nbsp;欢迎</router-link></a-menu-item>
      <a-sub-menu key="base">
        <template #title>
          <span>
            <UnorderedListOutlined />
            基础数据
          </span>
        </template>
        <a-menu-item key="/base/station">
          <router-link to="/base/station">
            <MenuUnfoldOutlined /> &nbsp; 车站管理
          </router-link>
        </a-menu-item>
        <a-menu-item key="/base/train">
          <router-link to="/base/train">
            <MenuUnfoldOutlined /> &nbsp; 火车管理
          </router-link>
        </a-menu-item>
        <a-menu-item key="/base/train-station">
          <router-link to="/base/train-station">
            <MenuUnfoldOutlined /> &nbsp; 火车车站
          </router-link>
        </a-menu-item>
        <a-menu-item key="/base/train-carriage">
          <router-link to="/base/train-carriage">
            <MenuUnfoldOutlined /> &nbsp; 火车车厢
          </router-link>
        </a-menu-item>
        <a-menu-item key="/base/train-seat">
          <router-link to="/base/train-seat">
            <MenuUnfoldOutlined /> &nbsp; 火车座位
          </router-link>
        </a-menu-item>
      </a-sub-menu>
      <a-sub-menu key="batch">
        <template #title>
          <span>
            <UnorderedListOutlined />
            跑批管理
          </span>
        </template>
        <a-menu-item key="/batch/job">
          <router-link to="/batch/job">
            <MenuUnfoldOutlined /> &nbsp; 任务管理
          </router-link>
        </a-menu-item>
      </a-sub-menu>
      <a-sub-menu key="business">
        <template #title>
          <span>
            <UnorderedListOutlined />
            业务管理
          </span>
        </template>
        <a-menu-item key="/business/sk-token">
          <router-link to="/business/sk-token">
            <MenuUnfoldOutlined /> &nbsp; 令牌余量
          </router-link>
        </a-menu-item>
        <a-menu-item key="/business/confirm-order">
          <router-link to="/business/confirm-order">
            <MenuUnfoldOutlined /> &nbsp; 订单信息
          </router-link>
        </a-menu-item>
        <a-menu-item key="/business/daily-train">
          <router-link to="/business/daily-train">
            <MenuUnfoldOutlined /> &nbsp; 每日车次
          </router-link>
        </a-menu-item>
        <a-menu-item key="/business/daily-train-station">
          <router-link to="/business/daily-train-station">
            <MenuUnfoldOutlined /> &nbsp; 每日车站
          </router-link>
        </a-menu-item>
        <a-menu-item key="/business/daily-train-carriage">
          <router-link to="/business/daily-train-carriage">
            <MenuUnfoldOutlined /> &nbsp; 每日车厢
          </router-link>
        </a-menu-item>
        <a-menu-item key="/business/daily-train-seat">
          <router-link to="/business/daily-train-seat">
            <MenuUnfoldOutlined /> &nbsp; 每日座位
          </router-link>
        </a-menu-item>
        <a-menu-item key="/business/daily-train-ticket">
          <router-link to="/business/daily-train-ticket">
            <MenuUnfoldOutlined /> &nbsp; 余票查询
          </router-link>
        </a-menu-item>
      </a-sub-menu>
    </a-menu>
  </a-layout-header>
</template>

<script>

import {defineComponent, ref, watch} from 'vue';
import router from "@/router";

export default defineComponent({
  name: "the-header-view",
  setup() {
    const selectedKeys = ref([]);

    watch(() => router.currentRoute.value.path, (newValue) => {
      console.log('watch', newValue);
      selectedKeys.value = [];
      selectedKeys.value.push(newValue);
    }, {immediate: true});
    return {
      selectedKeys,
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
