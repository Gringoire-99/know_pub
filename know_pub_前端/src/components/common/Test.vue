<template>
    <button @click="show=!show">show</button>
    <Transition
        name="p"
    >
        <span v-if="show">hello</span>
    </Transition>
    <div class="animate__fadeIn"></div>
</template>
<script>
export default {
    data() {
        return {show: true}
    },
}
</script>
<style lang="scss" scoped>
//.p {
//
//  &-enter-active {
//    @include a-fadeIn($duration: 0.2s);
//  }
//
//  &-leave-active {
//    @include a-fadeIn($reverse: true, $duration: 0.2s,$x:0,$y:-10%,$z:0);
//  }
//}

span {
    @include hoverUnderLine(left);

}

</style>
