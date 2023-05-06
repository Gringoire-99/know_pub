<template>
    <div class="root d-flex flex-column w-100">
        <sed-write></sed-write>
        <sed-recommended-users></sed-recommended-users>
        <sed-menu></sed-menu>
        <sed-nav></sed-nav>
    </div>
</template>

<script>

import SedWrite from "@/components/home/secondary/SedWrite.vue";
import SedRecommendedUsers from "@/components/home/secondary/SedRecommendedUsers.vue";
import SedMenu from "@/components/home/secondary/SedMenu.vue";
import SedNav from "@/components/home/secondary/SedNav.vue";

export default {
    name: "main-secondary",
    components: {SedNav, SedMenu, SedRecommendedUsers, SedWrite,}
}
</script>

<style scoped>

</style>
