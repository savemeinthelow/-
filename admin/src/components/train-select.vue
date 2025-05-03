<template>
  <a-select v-model:value="trainCode" show-search allowClear
            :filterOption="filterTrainCodeOption"
            :style="'width:'+ localWidth"
            @change="onChange"
            placeholder="请选择车次"
  >
    <a-select-option v-for="item in trains" :key="item.code"
                     :value="item.code"
                     :label="item.code +item.start + item.end">
      {{ item.code }} | {{ item.start }}~{{ item.end }}
    </a-select-option>
  </a-select>
</template>
<script>
import {defineComponent, onMounted, ref, watch} from "vue";
import axios from "axios";
import {notification} from "ant-design-vue";

export default defineComponent({
  name: "train-select-view",
  props: ["modelValue","width"],
  emits: ['update:modelValue', 'change'],
  setup(props,{emit}) {
    const trainCode = ref()
    const trains = ref([])
    const localWidth = ref(props.width);
    if(Tool.isEmpty(props.width)){
      localWidth.value="100%"
    }
    watch(()=> props.modelValue,()=>{
      trainCode.value = props.modelValue
    },{immediate:true});
    const queryTrainCode = () => {
      axios.get("/business/admin/train/query-all").then((response) => {
        let data = response.data;
        if (data.success) {
          trains.value = data.content;
        } else {
          notification.error({description: data.message});
        }
      });
    };
    const filterTrainCodeOption = (input, option) => {
      return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
    };
    onMounted(()=>{
      queryTrainCode()
    })
    const onChange= (value)=>{
      emit('update:modelValue',value);
      let train = trains.value.filter(item=>item.code===value)[0];
      if(Tool.isEmpty(train)){
        train = {}
      }
      emit('change',train)
    }
    return {
      filterTrainCodeOption,
      trains,
      onChange,
      localWidth
    }
  }
})
</script>
