<template>
    <div class="collection-root">
        <div class="header">
            <span>{{ collection.name }}</span>
            <el-icon v-show="collection.isPrivate">
                <unlock></unlock>
            </el-icon>
            <div/>
        </div>
        <div class="footer">
            <div class="footer-info">
                <span>{{ collection.updateTime }}</span>
                <span>{{ collection.collctionCount }}</span>
                <span>{{ collection.followerCount }}</span>
            </div>
            <div class="footer-operations">
                <div class="operation">
                    <el-icon>
                        <chat-dot-round></chat-dot-round>
                    </el-icon>
                    <span>添加评论</span>
                </div>
                <div class="operation">
                    <el-icon>
                        <edit></edit>
                    </el-icon>
                    <span>编辑</span>
                </div>
                <div class="operation">
                    <el-icon>
                        <delete></delete>
                    </el-icon>
                    <span>删除</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {ChatDotRound, Delete, Unlock} from "@element-plus/icons-vue";
import Edit from "@/components/icons/Edit.vue";

export default {
    //组件名
    name: "collection",
    //依赖的组件
    components: {Delete, Edit, ChatDotRound, Unlock},
    //数据
    data() {
        return {}
    },
    //方法
    methods: {},
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
    },
    //侦听器
    watch: {
        // 每当 question 改变时，这个函数就会执行
        // question(newQuestion, oldQuestion) {}
    }
    ,
    //计算属性
    computed: {}
    ,
    //绑定父组件的属性
    props: {
        collection: {
            type: Object,
            require: true
        }
    }
}
</script>

<style lang="scss" scoped>
.collection-root {
    display: flex;
    flex-direction: column;
    gap: 1em;

    .header {
        display: flex;
        align-items: center;
        font-weight: bold;
    }

    .footer {
        display: flex;
        gap: 0.5em;

        & > * {
            flex: 1;
            display: flex;
            gap: 0.5em;
            align-items: center;
        }
    }
}

</style>
