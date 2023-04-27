<template>
    <div class="w-100">
        <!--                  TODO 帖子：用户发布的内容会显示在这里-->

        <post v-for="post in posts" :post="post" class="post"></post>

    </div>
</template>
<script>
import Post from "@/components/home/main/post/Post.vue";
import axios from "axios";

export default {
    name: 'main-recommended',
    components: {Post},
    data() {
        return {
            posts: []
        }
    },
    created() {
        axios.get('http://localhost:8080/recommended-posts', {}).then(response => {
            this.posts = response.data
        }, reason => {
            console.log(reason)
        })
    }
}
</script>
<style scoped>
:deep(.post) {
    margin-bottom: 10px;
}
</style>
