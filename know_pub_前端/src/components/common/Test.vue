<template>
    <button @click="show=!show">show</button>
    <span>text</span>
</template>
<script>
export default {
    data() {
        return {show: true}
    },
}
</script>
<style lang="scss" scoped>
span {
    @include clickEffect();
}

</style>
