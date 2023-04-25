<template>
    <div>
        <el-row :gutter="10">
            <el-col :lg="18" :md="18" :sm="24" :xl="18" :xs="24">
                <main-list/>
            </el-col>
            <el-col :span="6" class="d-none d-lg-inline ">
                <main-secondary></main-secondary>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import MainList from "@/components/home/MainList.vue";
import MainSecondary from "@/components/home/MainSecondary.vue";

/**
 * 主界面的主要内容区域，用于展示帖子，和次要信息
 */
export default {
    name: "home-main",
    components: {MainSecondary, MainList}
}
</script>

