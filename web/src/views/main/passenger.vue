<template>
  <div>

    <p>
      <a-space><a-button type="primary" @click="onAdd">新增</a-button>
        <a-button type="primary" @click="handleQuery()">刷新</a-button>
      </a-space>

    </p>
    <a-table :dataSource="passengers"
             :columns="columns"
             :pagination="pagination"
             @change="handleTableChange"
             :loading="loading">
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'operation'">
          <a-space>
            <a-popconfirm
                title="删除后不可恢复，确认删除?"
                @confirm="onDelete(record)"
                ok-text="确认" cancel-text="取消">
              <a style="color: red">删除</a>
            </a-popconfirm>
            <a @click="onEdit(record)">编辑</a>
          </a-space>
        </template>
        <template v-else-if="column.dataIndex === 'type'">
        </template>
      </template>
    </a-table>
    <a-modal v-model:visible="visible" title="乘车人" @ok="handleOk"
             ok-text="确认" cancel-text="取消">
      <a-form :model="passenger" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
        <a-form-item label="姓名">
          <a-input v-model:value="passenger.name"/>
        </a-form-item>
        <a-form-item label="身份证">
          <a-input v-model:value="passenger.idCard"/>
        </a-form-item>
        <a-form-item label="类型">
          <a-select v-model:value="passenger.type" placeholder="please select your zone">
            <a-select-option value="1">
              成人
            </a-select-option>
            <a-select-option value="2">
              儿童
            </a-select-option>
            <a-select-option value="3">
              学生
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>

</template>

<script>
import {defineComponent, ref, onMounted} from 'vue';
import axios from "axios";
import {notification} from "ant-design-vue";

export default defineComponent({
  name: "passenger-view",
  setup() {
    // const PASSENGER_TYPE_ARRAY = window.PASSENGER_TYPE_ARRAY;
    let loading = ref(false);
    let passengers = ref([]);
    let passenger = ref({
      id: undefined,
      memberId: undefined,
      name: undefined,
      idCard: undefined,
      type: undefined,
      createTime: undefined,
      updateTime: undefined,
    });
    let pagination = ref({
      total: 0,
      current: 1,
      pageSize: 2,
    });

    onMounted(() => {
      handleQuery({
        page: 1,
        size: 2
      });
    });

    const visible = ref(false);
    const onAdd = () => {
      passenger.value = {};
      visible.value = true;
    };

    const onEdit = (record) => {
      passenger.value = window.Tool.copy(record);
      visible.value = true;
    };
    const columns = [
      {
        title: '姓名',
        dataIndex: 'name',
        key: 'name'
      }, {
        title: '身份证',
        dataIndex: 'idCard',
        key: 'idCard'
      },
      {
        title: '旅客类型',
        dataIndex: 'type',
        key: 'type'
      },
      {
        title: '操作',
        dataIndex: 'operation',
      },
    ];
    const handleTableChange = (pagination)=>{
      handleQuery({
        page:pagination.current,
        size:pagination.pageSize
      })
    }
    const handleOk = () => {
      axios.post("/member/passenger/save", passenger.value).then((resp) => {
        let data = resp.data;
        if (data.success) {
          notification.success({
            description: '保存成功!'
          });
          visible.value = false;
          handleQuery({
            page: pagination.value.current,
            size:pagination.value.pageSize
          })
        } else {
          notification.error({description: data.message})
        }
      })
    };
    const handleQuery = (param) => {
      if (!param) {
        param = {
          page: 1,
          size: pagination.value.pageSize
        };
      }
      loading.value = true;
      axios.get("/member/passenger/query-list", {
        params: {
          page: param.page,
          size: param.size
        }
      }).then((response) => {
        loading.value = false;
        let data = response.data;
        if (data.success) {
          passengers.value = data.content.list;
          pagination.value.current = param.page;
          pagination.value.total = data.content.total;
        } else {
          notification.error({description: data.message});
        }
      });
    };
    return {
      visible,
      onAdd,
      onEdit,
      handleOk,
      passenger,
      pagination,
      columns,
      handleQuery,
      passengers,
      handleTableChange,
      loading

    };
  },
});
</script>
