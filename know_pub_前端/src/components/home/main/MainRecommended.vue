<template>
    <div class="mr-root">
        <transition-group name="post">
            <post v-for="post in posts" :key="post.id" :post="post" class="post"></post>
        </transition-group>
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
            posts: [],
            pageSize: 5,
            currentPage: 1,
            isLoading: false,
        }
    }, methods: {
        getPosts() {
            this.isLoading = true
            axios.get('/api/recommended-posts', {
                id: this.$store.userId,
                pageSize: this.pageSize,
                currentPage: this.currentPage,

            }).then(response => {
                this.posts.push(...response.data)
                this.isLoading = false
            }, reason => {
                alert(reason)
            }).finally(() => {
                this.$store.state.loadData = false
            })
        },
    },
    created() {
        this.getPosts()
    },
    watch: {
        $store: {
            handler() {
                if (this.$store.state.loadData) {
                    this.getPosts()

                }
            },
            deep: true
        }
    }
}
</script>
<style lang="scss" scoped>
@include fade(post, 0.2s, (-10px, 0, 0));

</style>
