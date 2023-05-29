<template>
    <div class="mr-root">
        <transition-group name="post">
            <post v-for="post in posts" :key="post.id" :post="post" class="post"></post>
        </transition-group>
    </div>
</template>
<script>
import Post from "@/components/home/main/post/Post.vue";
import http, {http_no_token} from "@/utils/http/http";

export default {
    name: 'main-recommended',
    components: {Post},
    data() {
        return {
            posts: [],
            pageSize: 10,
            currentPage: 1,
            isLoading: false,
        }
    }, methods: {
        getPosts() {
            this.isLoading = true
            let id = this.$store.state.userInfo.id
            http.get('/post/recommended-posts', {
                params: {
                    id: (id === '' || id == null) ? 1 : 1,
                    pageSize: this.pageSize,
                    currentPage: this.currentPage,
                }
            }).then(response => {
                this.currentPage += 1
                this.posts.push(...response.data.data.page)
                this.isLoading = false
            }, reason => {
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
@include fade(post, 0.6s, (-30px, 0, 0));
</style>
