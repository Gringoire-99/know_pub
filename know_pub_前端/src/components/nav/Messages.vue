<template>
    <div class="card">
        <div class="card-body">
            <el-tabs :stretch="true" class="demo-tabs" type="border-card">
                <el-tab-pane>
                    <template #label>
                        <el-icon :size="iconSize">
                            <ChatDotRound/>
                        </el-icon>
                    </template>
                    Route
                </el-tab-pane>
                <el-tab-pane label="Task">
                    <template #label>
                        <el-icon :size="iconSize">
                            <Avatar/>
                        </el-icon>
                    </template>
                    Route
                </el-tab-pane>
                <el-tab-pane label="Task">
                    <template #label>
                        <el-icon :size="iconSize    ">
                            <StarFilled/>
                        </el-icon>
                    </template>
                    <ul v-infinite-scroll="loadMessages" class="infinite-list" style="overflow: auto">
                        <li v-for="msg in messages" :key="msg.id" class="infinite-list-item">{{ msg.content }}</li>
                    </ul>
                </el-tab-pane>
            </el-tabs>

        </div>

        <div class="card-footer d-flex align-items-center ">
            <div class="d-flex align-items-center flex-fill">
                <el-icon>
                    <Setting/>
                </el-icon>
                <span class="me-auto">设置</span>
                <span>查看所有通知</span>
            </div>

        </div>
    </div>


</template>

<script>

import axios from "axios";

export default {
    name: "Messages",
    data() {
        return {
            iconSize: 23,
            pageSize: 10,
            pageIndex: 0,
            messages: []
        }
    },
    methods: {
        loadMessages() {
            axios.get('http://localhost:8080/messages', {
                params: {
                    pageSize: this.pageSize,
                    pageIndex: this.pageIndex
                }
            }).then(
                resolve => {
                    if (resolve.status === 200) {
                        this.messages.concat(resolve.data)
                    } else {
                        alert("failed")
                    }
                }, reason => {
                    alert("failed")
                }
            )
        }
    },
    created() {
        this.loadMessages()
    }
}
</script>

<style scoped>
.el-tabs {
    border: none;
}

.card-body {
    padding: 0;
}

.card-footer {
    background-color: #ffffff;
    border-color: rgba(204, 204, 211, 0.45);
}

.infinite-list {
    height: 300px;
    padding: 0;
    margin: 0;
    list-style: none;
}

.infinite-list .infinite-list-item {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 50px;
    background: var(--el-color-primary-light-9);
    margin: 10px;
    color: var(--el-color-primary);
}

.infinite-list .infinite-list-item + .list-item {
    margin-top: 10px;
}
</style>
