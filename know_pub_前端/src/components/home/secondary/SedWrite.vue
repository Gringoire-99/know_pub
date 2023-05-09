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
            <div class="menu">
                <div class="blue circle menu-content">
                    <el-icon>
                        <answer></answer>
                    </el-icon>
                </div>
                <span class="fs-sed mt-2">回答问题</span>
            </div>
            <div class="menu">
                <div class="orange circle menu-content">
                    <el-icon class="fs-4">
                        <videos></videos>
                    </el-icon>
                </div>
                <span class="fs-sed mt-2">发视频</span>
            </div>
            <div class="menu">
                <div class="green circle menu-content">
                    <el-icon>
                        <pen></pen>
                    </el-icon>
                </div>
                <span class="fs-sed mt-2">写文章</span>
            </div>
            <div class="menu">
                <div class="yellow circle menu-content fs-4">
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
            <img class="" src="../../../assets/icon_small.png"/>
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
    transition-property: background-color, transform;
    transition-duration: 0.5s, 0.5s;
    background-color: rgba(133, 200, 250, 0.19);
}

.blue:hover {
    background-color: rgba(133, 200, 250, 0.5);
}

.orange {
    transition-property: background-color, transform;
    transition-duration: 0.5s, 0.5s;
    background-color: rgba(255, 153, 0, 0.11);
}

.orange:hover {
    background-color: rgba(232, 195, 138, 0.5);
}

.yellow {
    transition-property: background-color, transform;
    transition-duration: 0.5s, 0.5s;
    background-color: rgba(255, 204, 0, 0.11);
}

.yellow:hover {
    background-color: rgba(239, 215, 119, 0.5);
}

.green {
    transition-property: background-color, transform;
    transition-duration: 0.5s, 0.5s;
    background-color: rgba(0, 204, 153, 0.11);
}

.green:hover {
    background-color: rgba(139, 248, 221, 0.48);
}

.desc img {
    width: 55px;
    height: 55px;
}

.menu {
    display: flex;
    flex-direction: column;
    flex: 1 1 auto;
    align-items: center;
    justify-content: center;
    margin: 0 10px;
    cursor: pointer;
}

.menu-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.menu-content:hover {
    transform: scale(1.3, 1.3);
}

.menu-content:not(:hover) {
    transform: scale(1, 1);
}
</style>
