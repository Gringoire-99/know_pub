<template>
    <router-view></router-view>
</template>

<script>

import HomeNavbar from "@/components/nav/NavBar.vue";
import http from "@/utils/http/http";
import Mock from "mockjs";
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
            http.get("/user/info", {
                params: {
                    userId: this.$store.state.userId
                }
            }).then(response => {
                if (response.status === 200) {
                    this.$store.commit("SET_USER", response.data.data)
                    this.$store.commit("LOGIN_STATE", true)

                } else alert("get user info failed")
            }, reason => {
                this.$store.commit("LOGIN_STATE", false)
                alert("failed in app.vue")
            })
        },


    },
    //创建时执行
    created() {
        localStorage.setItem("userId", '1')
        this.$cookies.set("token", Mock.mock('@guid'))

        //     从本地存储中获取用户id
        let userId = localStorage.getItem("userId")
        if (userId) {
            this.$store.commit("SET_USER_ID", userId)
            this.getUserInfo()
        } else {
            this.$store.commit("LOGIN_STATE", false)
            this.$popUp('登录以查看更多内容', '', 'info', 2000)
        }
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
        this.$mockSetUp()

    }

}
</script>

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

</style>
