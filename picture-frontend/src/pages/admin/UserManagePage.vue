<template>
  <div id="userManagePage">
    <!-- 搜索表单 -->
    <a-form layout="inline" :model="searchParams" @finish="doSearch">
      <a-form-item label="账号">
        <a-input v-model:value="searchParams.userAccount" placeholder="输入账号" allow-clear />
      </a-form-item>
      <a-form-item label="用户名">
        <a-input v-model:value="searchParams.userName" placeholder="输入用户名" allow-clear />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit">搜索</a-button>
      </a-form-item>
    </a-form>
    <div style="margin-bottom: 16px" />
    <!-- 表格 -->
    <a-table
      :columns="columns"
      :data-source="dataList"
      :pagination="pagination"
      @change="doTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'userAvatar'">
          <a-image :src="record.userAvatar" :width="120" />
        </template>
        <template v-else-if="column.dataIndex === 'userRole'">
          <div v-if="record.userRole === 'admin'">
            <a-tag color="green">管理员</a-tag>
          </div>
          <div v-else>
            <a-tag color="blue">普通用户</a-tag>
          </div>
        </template>
        <template v-if="column.dataIndex === 'createTime'">
          {{ dayjs(record.createTime).format('YYYY-MM-DD HH:mm:ss') }}
        </template>
        <template v-else-if="column.key === 'action'">
          <a-button danger @click="doDelete(record.id)">删除</a-button>
        </template>
      </template>
    </a-table>
  </div>
</template>
<script lang="ts" setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { deleteUserUsingPost, listUserVoByPageUsingPost } from '@/api/userController.ts'
import { message } from 'ant-design-vue'
import dayjs from 'dayjs'

const columns = [
  {
    title: 'id',
    dataIndex: 'id',
  },
  {
    title: '账号',
    dataIndex: 'userAccount',
  },
  {
    title: '用户名',
    dataIndex: 'userName',
  },
  {
    title: '头像',
    dataIndex: 'userAvatar',
  },
  {
    title: '简介',
    dataIndex: 'userProfile',
  },
  {
    title: '用户角色',
    dataIndex: 'userRole',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
  {
    title: '操作',
    key: 'action',
  },
]

// 定义数据
const dataList = ref<API.UserVO[]>([])
const total = ref(0)

// 搜索条件
const searchParams = reactive<API.UserQueryRequest>({
  current: 1,
  pageSize: 10,
  sortField: 'createTime',
  sortOrder: 'ascend',
})

// 获取数据
const fetchData = async () => {
  const res = await listUserVoByPageUsingPost({
    ...searchParams,
  })
  if (res.data.code === 0 && res.data.data) {
    dataList.value = res.data.data.records ?? []
    total.value = res.data.data.total ?? 0
  } else {
    message.error('获取数据失败，' + res.data.message)
  }
}

// 页面加载时获取数据，请求一次
onMounted(() => {
  fetchData()
})

// 分页参数
const pagination = computed(() => {
  return {
    current: searchParams.current,
    pageSize: searchParams.pageSize,
    total: total.value,
    showSizeChanger: true,
    showTotal: (total) => `共 ${total} 条`,
  }
})

// 表格变化之后，重新获取数据
const doTableChange = (page: any) => {
  searchParams.current = page.current
  searchParams.pageSize = page.pageSize
  fetchData()
}

// 搜索数据
const doSearch = () => {
  // 重置页码
  searchParams.current = 1
  fetchData()
}

// 删除数据
const doDelete = async (id: string) => {
  if (!id) {
    return
  }
  const res = await deleteUserUsingPost({ id })
  if (res.data.code === 0) {
    message.success('删除成功')
    // 刷新数据
    fetchData()
  } else {
    message.error('删除失败')
  }
}
</script>

<style scoped>
#userManagePage :deep(.ant-form-item-label > label) {
  color: var(--tech-text, #e4e4e7);
}

#userManagePage :deep(.ant-input) {
  background: var(--tech-bg-container, #1a1a2e);
  border-color: var(--tech-border, #2d2d44);
  color: var(--tech-text, #e4e4e7);
}

#userManagePage :deep(.ant-input:hover),
#userManagePage :deep(.ant-input:focus) {
  border-color: var(--tech-primary, #6366f1);
}

#userManagePage :deep(.ant-btn-primary) {
  background: linear-gradient(135deg, #6366f1 0%, #a855f7 100%);
  border: none;
  box-shadow: 0 0 15px rgba(99, 102, 241, 0.3);
}

#userManagePage :deep(.ant-btn-primary:hover) {
  box-shadow: 0 0 25px rgba(99, 102, 241, 0.5);
}

#userManagePage :deep(.ant-table) {
  background: var(--tech-bg-container, #1a1a2e);
}

#userManagePage :deep(.ant-table-thead > tr > th) {
  background: var(--tech-bg-elevated, #16213e);
  border-color: var(--tech-border, #2d2d44);
  color: var(--tech-text, #e4e4e7);
}

#userManagePage :deep(.ant-table-tbody > tr > td) {
  border-color: var(--tech-border, #2d2d44);
  color: var(--tech-text, #e4e4e7);
}

#userManagePage :deep(.ant-table-tbody > tr:hover > td) {
  background: rgba(99, 102, 241, 0.08);
}

#userManagePage :deep(.ant-pagination-item) {
  background: var(--tech-bg-container, #1a1a2e);
  border-color: var(--tech-border, #2d2d44);
}

#userManagePage :deep(.ant-pagination-item a) {
  color: var(--tech-text-secondary, #a1a1aa);
}

#userManagePage :deep(.ant-pagination-item-active) {
  background: rgba(99, 102, 241, 0.15);
  border-color: var(--tech-primary, #6366f1);
}

#userManagePage :deep(.ant-pagination-item-active a) {
  color: var(--tech-primary, #6366f1);
}

#userManagePage :deep(.ant-btn-dangerous) {
  background: rgba(239, 68, 68, 0.1);
  border-color: rgba(239, 68, 68, 0.5);
  color: #ef4444;
}

#userManagePage :deep(.ant-btn-dangerous:hover) {
  background: rgba(239, 68, 68, 0.2);
  border-color: #ef4444;
}
</style>
