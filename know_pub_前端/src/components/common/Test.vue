<template>
    <div class="body">
        <div class="left">
            <div class="box">11111111</div>
            <div class="box">11111111</div>
            <div class="box">11111111</div>
        </div>
        <div class="right">
            <div class="box">11111111</div>
            <div class="box">11111111</div>
            <div class="box">11111111</div>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {show: true}
    },
}
</script>
<style lang="scss" scoped>
.body {
    @include align(grid);
    grid-template-columns: 1fr 2fr;

    & > div {
        @include align($grow: 1);
    }
}

</style>
