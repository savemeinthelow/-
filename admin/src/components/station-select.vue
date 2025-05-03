<template>
  <a-select v-model:value="name" show-search allowClear
            :filterOption="filterNameOption"
            :style="'width:'+ localWidth"
            @change="onChange"
            placeholder="请选择车站"
  >
    <a-select-option v-for="item in stations" :key="item.name"
                     :value="item.name"
                     :label="item.name +item.namePinyin + item.namePy">
      {{ item.name }} | {{ item.namePinyin }}~{{ item.namePy }}
    </a-select-option>
  </a-select>
</template>
<script>
import {defineComponent, onMounted, ref, watch} from "vue";
import axios from "axios";
import {notification} from "ant-design-vue";

export default defineComponent({
  name: "station-select-view",
  props: ["modelValue","width"],
  emits: ['update:modelValue', 'change'],
  setup(props,{emit}) {
    const name = ref()
    const stations = ref([])
    const localWidth = ref(props.width);
    if(Tool.isEmpty(props.width)){
      localWidth.value="100%"
    }
    watch(()=> props.modelValue,()=>{
      name.value = props.modelValue
    },{immediate:true});
    const queryName = () => {
      axios.get("/business/admin/station/query-all").then((response) => {
        let data = response.data;
        if (data.success) {
          stations.value = data.content;
        } else {
          notification.error({description: data.message});
        }
      });
    };
    const filterNameOption = (input, option) => {
      return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
    };
    onMounted(()=>{
      queryName()
    })
    const onChange= (value)=>{
      emit('update:modelValue',value);
      let station = stations.value.filter(item=>item.code===value)[0];
      if(Tool.isEmpty(station)){
        station = {}
      }
      emit('change',station)
    }
    return {
      filterNameOption,
      stations,
      onChange,
      localWidth
    }
  }
})
</script>
