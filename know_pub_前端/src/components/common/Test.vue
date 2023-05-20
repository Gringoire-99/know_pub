<template>
    <button @click="show=!show">show</button>
    <Transition
            name="p"
    >
        <span v-if="show">hello</span>
    </Transition>
</template>
<script>
export default {
    data() {
        return {show: true}
    },
}
</script>
<style lang="scss" scoped>
@include zoom(p);

span {
  @include hoverUnderLine(left);

}

</style>
