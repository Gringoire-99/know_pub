
<template>
    <div class="root">
        <div class="">

            <div>
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
            </div>
        </div>
        <div class="info-short d-flex flex-column">

            <div>
            </div>
        </div>
        <div class="info-short d-flex flex-column">

            <div>
                <button>1</button>
            </div>
        </div>
        <div class="info-short d-flex flex-column d">

            <div>
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
            </div>
            <el-affix position="bottom" target=".d">
                <el-button>1</el-button>
            </el-affix>
        </div>
        <div class="info-short d-flex flex-column first d2">
            <div>
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aliquam aliquid animi deleniti
                earum, eos eum expedita incidunt itaque, iure, libero molestias nostrum odit perspiciatis repellendus
                tempore temporibus ut voluptates.
            </div>
            <el-affix position="bottom" target=".d2">
                <el-button>1</el-button>
            </el-affix>

        </div>
    </div>

</template>

<script>
import PostBody from "@/components/home/main/post/PostBody.vue";
import PostCard from "@/components/user/PostCard.vue";
import Job from "@/components/icons/Job.vue";
import Gender from "@/components/icons/Gender.vue";

export default {
    //组件名
    name: "test",
    //依赖的组件
    components: {Gender, Job, PostCard, PostBody},
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
    props: {}
}
</script>

<style scoped>
.affix-container {
    text-align: center;
    border-radius: 4px;
    background: var(--el-color-primary-light-9);
}

.root {
    height: 500px;
    width: 500px;
    overflow: scroll;
}
</style>
