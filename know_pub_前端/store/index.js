//声明一个store
import Vuex from 'vuex';
//actions: 可被分配的动作（预处理，申请ajax）
const actions = {}
//动作调用的实现方法(改变state)
const mutations = {
    //参数1 state 所有组件都可访问到的空间，2 action传递的参数
    SET_LOGIN_STATE(state, flag) {
        state.isLogin = flag;
    },
    SET_USER(state, user) {
        state.userInfo = user;
    },
    SET_LOAD_DATA(state, loadData) {
        state.loadData = loadData;
    },
    SET_SHOW_LOGIN(state, showLogin) {
        state.showLogin = showLogin
    },
    SET_RELOAD(state, reload) {
        state.reload = reload
    }

}
const state = {
    isLogin: false,
    userInfo: {
        answerCount: 0,
        articleCount: 0,
        auth: "0",
        avatar: "",
        background: "",
        collectionCount: 0,
        commentCount: 0,
        company: "",
        createTime: null,
        description: "这个人很懒，什么都没有留下",
        experience: 0,
        followCount: 0,
        followerCount: 0,
        gender: "",
        id: "",
        industry: "",
        job: "",
        koinCount: 0,
        likeCount: 0,
        location: "",
        name: "",
        postCount: 0,
        questionCount: 0,
        resume: "",
        school: "",
        videoCount: 0
    },
    loadData: false,
    showLogin: false,
    reload: false
}
export const store = Vuex.createStore({
    state,
    mutations,
    actions,
})
