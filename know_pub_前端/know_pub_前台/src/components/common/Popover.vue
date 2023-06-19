<template>
    <el-popover
            :placement="placement"
            :show-after="showAfter"
            :trigger="trigger"
            :width="width"
            @after-enter="isLoad=true"
    >
        <template #reference>
            <slot name="reference"></slot>
        </template>
        <slot v-if="!isLazy||isLoad" class="content"></slot>
    </el-popover>
</template>

<script>
export default {
    //组件名
    name: "popover",
    //依赖的组件
    components: {},
    //数据
    data() {
        return {
            isLoad: false
        }
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
        'showAfter': {
            type: Number,
            default: 0
        },
        'placement': {
            type: String,
            default: 'top'
        },
        'trigger': {
            type: String,
            default: 'hover'
        },
        'width': {
            type: Number,
        },
        isLazy: {
            type: Boolean,
            default: true
        }
    }
}
</script>

<style lang="scss">
</style>
