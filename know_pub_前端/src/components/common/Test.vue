<template>
    <button @click="show=!show">show</button>
    <transition
        name="box"
    >

        <div v-show="show" class="">
            1
        </div>
    </transition>

</template>
<script>
export default {
    data() {
        return {show: true}
    },
}
</script>
<style lang="scss" scoped>
@include zoom(box, 0.3s, (0.05, 0.05, 0.05), (0, 10px, 0), $reverse: false);


</style>
