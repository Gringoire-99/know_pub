<template>
    <div class="root d-flex flex-column ">
        <div class="header d-flex align-items-center">
            <el-icon>
                <Write></Write>
            </el-icon>
            <span class="ms-1">创作中心</span>
            <span class="fs-sed pointer ms-auto">草稿箱</span>
        </div>
        <div class="operations d-flex  mt-4">
            <div class="d-flex flex-column flex-fill align-items-center justify-content-center pointer ms-1 me-1">
                <div class="blue circle d-flex align-items-center justify-content-center">
                    <el-icon>
                        <answer></answer>
                    </el-icon>
                </div>
                <span class="fs-sed mt-2">回答问题</span>
            </div>
            <div class="d-flex flex-column flex-fill align-items-center justify-content-center pointer ms-1 me-1">
                <div class="orange circle d-flex align-items-center justify-content-center">
                    <el-icon class="fs-4">
                        <videos></videos>
                    </el-icon>
                </div>
                <span class="fs-sed mt-2">发视频</span>
            </div>
            <div class="d-flex flex-column flex-fill align-items-center justify-content-center pointer ms-1 me-1">
                <div class="green circle d-flex align-items-center justify-content-center">
                    <el-icon>
                        <pen></pen>
                    </el-icon>
                </div>
                <span class="fs-sed mt-2">写文章</span>
            </div>
            <div class="d-flex flex-column flex-fill align-items-center justify-content-center pointer ms-1 me-1">
                <div class="yellow circle d-flex align-items-center justify-content-center fs-4">
                    <el-icon>
                        <idea></idea>
                    </el-icon>
                </div>
                <span class="fs-sed mt-2">写想法</span>
            </div>
        </div>
        <div class="p-3 mt-3 d-flex desc align-items-center">
            <div class="me-1">
                <div class="mb-1">发布你的第一篇文章</div>
                <div class="fs-sed">回答问题，发布文章，开始创作<br>快来成为KP创作者吧！</div>
            </div>
            <img class="" src="../../assets/icon_small.png"/>
        </div>
        <div class="w-100 align-self-center mt-3    ">
            <el-button class="w-100" type="primary">
                <el-icon>
                    <plus></plus>
                </el-icon>
                <span>开始创作</span>
            </el-button>
        </div>
    </div>
</template>

<script>
import Write from "@/components/icons/Write.vue";
import Answer from "@/components/icons/Answer.vue";
import Pen from "@/components/icons/Pen.vue";
import Idea from "@/components/icons/Idea.vue";
import Videos from "@/components/icons/Videos.vue";
import Plus from "@/components/icons/Plus.vue";

export default {
    name: "sed-write",
    components: {Plus, Videos, Idea, Pen, Answer, Write}
}
</script>

<style scoped>
.root {
    margin-bottom: 10px;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    border-radius: 1px;
    border: 1px solid #ebebeb;
    padding: 20px;
}

.circle {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    font-size: 20px;
}

.blue {
    background: rgba(133, 200, 250, 0.19);
}

.orange {
    background: rgba(255, 153, 0, 0.11);
}

.yellow {
    background: rgba(255, 204, 0, 0.15);
}

.green {
    background: rgba(0, 204, 153, 0.11);
}

.desc img {
    width: 55px;
    height: 55px;
}
</style>
