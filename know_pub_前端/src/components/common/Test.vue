<template>
    <post-question></post-question>
</template>
<script>
import PostEditor from "@/components/post-editor/PostEditor.vue";
import PostComment from "@/components/home/main/post/comment/PostComment.vue";
import PostQuestion from "@/components/home/question/PostQuestion.vue";

export default {
    components: {PostQuestion, PostComment, PostEditor},
    data() {
        return {show: true}
    },
}
</script>

