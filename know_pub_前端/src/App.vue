<template>
    <router-view></router-view>
</template>

<script>

import HomeNavbar from "@/components/nav/NavBar.vue";
import {http} from "@/utils/http/http";

export default {
    //组件名
    name: "app",
    //依赖的组件
    components: {HomeNavbar},
    //数据
    data() {
        return {}
    },
    //方法
    methods: {
        getUserInfo() {
            let id = localStorage.getItem('userId')
            if (id == null || id === '') return
            http.get('/user/info-detail', {
                params: {
                    userId: id
                }
            }).then(res => {
                if (res.data.code === 200) {
                    this.$store.commit('SET_USER', res.data.data)
                    this.$store.commit('SET_LOGIN_STATE', true)
                } else {
                }
            }, err => {
                console.log(err)
            })
        },


    },

    //创建时执行
    created() {
        this.getUserInfo()
    },
    //侦听器
    watch: {
        // 每当 question 改变时，这个函数就会执行
        // question(newQuestion, oldQuestion) {}
    }
    ,
    //计算属性
    computed: {}
    ,
    //绑定父组件的属性
    props: {},
    beforeCreate() {
        // this.$mockSetUp()

    }

}
</script>
<style lang="scss">

</style>
<style>
.fs-sed {
    font-size: 12px;
    font-weight: lighter;
}

.text-white {
    color: white;
}

.text-pink {
    color: #ff4d7a;
}

.text-red {
    color: #ff0000;
}

.text-blue {
    color: #0794ff;
}

.pointer {
    cursor: pointer;
}


/* 滚动条整体 */
*::-webkit-scrollbar {
    width: 5px;
}

/* 两个滚动条交接处 -- x轴和y轴 */
*::-webkit-scrollbar-corner {
    background-color: transparent;
}

/* 滚动条滑块 */
*::-webkit-scrollbar-thumb {
    border-radius: 10px;
    -webkit-box-shadow: inset 0 0 5px rgb(7, 123, 255);
    background: #0794ff;

}

/* 滚动条轨道 */
*::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 5px rgba(215, 160, 160, 0.2);
    border-radius: 10px;
    background: #ededed;
}

/* 滚动条两端按钮 */
body::-webkit-scrollbar-button {
}

.fs-xsm {
    font-size: 5px;
}

.text-gray {
    color: #8c8c8c;
}

.space {
    flex-grow: 0;
    flex-shrink: 1;
}
</style>
<style lang="scss">
.el-dialog {
    .el-dialog__body {
        padding: 0 !important;
    }

    .el-dialog__header {
        padding: 0 !important;
    }
}
</style>
