<template>
  <a-layout-sider width="200" style="background: #fff">
    <a-menu
        v-model:selectedKeys="selectedKeys"
        v-model:openKeys="openKeys"
        mode="inline"
        :style="{ height: '100%', borderRight: 0 }"
    >

      <a-menu-item key="/welcome"><router-link to="/welcome"><coffee-outlined /> &nbsp;欢迎</router-link></a-menu-item>
      <a-menu-item key="/passenger"><router-link to="/passenger"><coffee-outlined /> &nbsp;乘车人管理</router-link></a-menu-item>
      <a-menu-item key="/ticket"><router-link to="/ticket"><coffee-outlined /> &nbsp;余票查询</router-link></a-menu-item>
      <a-menu-item key="/my-ticket"><router-link to="/my-ticket"><coffee-outlined /> &nbsp;我的车票</router-link></a-menu-item>
      <a-menu-item key="/seat"><router-link to="/seat"><coffee-outlined /> &nbsp;座位销售图</router-link></a-menu-item>

    </a-menu>
  </a-layout-sider>
</template>

<script>
import {defineComponent, ref, watch} from "vue";
import router from "@/router";
export default defineComponent({
  name: "the-side-view",
  setup() {
    const selectedKeys = ref([]);

    watch(() => router.currentRoute.value.path, (newValue) => {
      console.log('watch', newValue);
      selectedKeys.value = [];
      selectedKeys.value.push(newValue);
    }, {immediate: true});
    return {
      selectedKeys
    };
  },
});
</script>

<style scoped>

</style>