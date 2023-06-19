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
                    <ul class="infinite-list" @scroll="loadMessages">
                        <li v-for="msg in messages" :key="msg.id" class="infinite-list-item hvr-glow ">{{
                            msg.content
                            }}
                        </li>
                        <li v-loading="isLoading" class="infinite-list-item loading-item"
                            element-loading-text="正在加载中..."></li>

                    </ul>
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

import {http} from "@/utils/http/http";

export default {
    name: "messages",
    data() {
        return {
            iconSize: 23,
            pageSize: 10,
            currentPage: 1,
            messages: [],
            isLoading: false
        }
    },
    methods: {
        getMessages() {
            this.isLoading = true
            // 休眠5秒，再启动

            http.get('/messages', {
                params: {
                    pageSize: this.pageSize,
                    currentPage: this.currentPage
                }
            }).then(
                resolve => {
                    if (resolve.status === 200) {
                        // 连接两个数组
                        this.messages.push(...resolve.data)
                        this.isLoading = false
                    } else {
                        alert("failed")
                    }
                }, reason => {
                }
            )

        },

        loadMessages(e) {
            if (this.isLoading) return
            let distance = 100
            const {scrollTop, clientHeight, scrollHeight} = e.target
            if (scrollTop + clientHeight + distance > scrollHeight) {
                this.currentPage += this.pageSize
            }


        }
    },
    created() {
        this.getMessages()
    },
    watch: {
        currentPage: function (newVal, oldVal) {
            this.getMessages()
        }
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
    height: 400px;
    padding: 5px;
    margin: 0;
    list-style: none;
    overflow: scroll !important;
    overflow-x: hidden !important;
}

.infinite-list .infinite-list-item {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 50px;
    background: var(--el-color-primary-light-9);
    margin: 5px;
    color: var(--el-color-primary);
    cursor: pointer;
}

.infinite-list .infinite-list-item + .list-item {
    margin-top: 10px;
}


/* 滚动条两端按钮 */
.infinite-list::-webkit-scrollbar-button {
}

.hvr-glow {
    display: inline-block;
    vertical-align: middle;
    -webkit-transform: perspective(1px) translateZ(0);
    transform: perspective(1px) translateZ(0);
    /*右下阴影*/

    -webkit-transition-duration: 0.3s;
    transition-duration: 0.3s;
    -webkit-transition-property: box-shadow;
    transition-property: box-shadow;
}

.hvr-glow:hover, .hvr-glow:focus, .hvr-glow:active {
    box-shadow: 0 0 3px rgb(7, 148, 255);
}

.card-footer div * {
    color: rgba(98, 94, 94, 0.65);
    cursor: pointer;

}

.card-footer div *:hover {
    color: #2b2b2c;
}
</style>
