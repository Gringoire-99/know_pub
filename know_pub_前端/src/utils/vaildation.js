class ValidationUtils {
    static REGEX_MOBILE = /^1[3|4|5|7|8][0-9]\d{8}$/;
    static REGEX_EMAIL = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    static REGEX_PASSWORD = /^.{8,30}$/;
    static REGEX_USERNAME = /^[a-zA-Z0-9_-]{4,16}$/;
    static EM_NULL = "参数不能为空";
    static EM_MOBILE = "手机号格式不正确";
    static EM_EMAIL = "邮箱格式不正确";
    static EM_PASSWORD = "密码格式不正确";
    static EM_NAME = "用户名格式不正确";
    static EM_USERNAME = "手机号或邮箱格式不正确";

    constructor() {
        this.msg = ''
        this.status = true
    }

    static validate() {
        return new ValidationUtils();
    }

    validateNull(...objects) {
        if (!this.status) return this
        for (let object of objects) {
            if (object === null || object === undefined) {
                this.status = false
                this.msg = ValidationUtils.EM_NULL
            }
        }
        return this;
    }

    validateEmpty(...strings) {
        if (!this.status) return this
        for (let string of strings) {
            if (string === null || string === undefined || string === "") {
                this.status = false
                this.msg = ValidationUtils.EM_NULL
            }
        }
        return this;
    }

    validateMobile(mobile) {
        if (!this.status) return this
        if (!ValidationUtils.REGEX_MOBILE.test(mobile)) {
            this.status = false
            this.msg = ValidationUtils.EM_MOBILE
        }
        return this;
    }

    validateEmail(email) {
        if (!this.status) return this
        if (!ValidationUtils.REGEX_EMAIL.test(email)) {
            this.status = false
            this.msg = ValidationUtils.EM_EMAIL
        }
        return this;
    }

    validatePassword(password) {
        if (!this.status) return this
        if (!ValidationUtils.REGEX_PASSWORD.test(password)) {
            this.status = false
            this.msg = ValidationUtils.EM_PASSWORD
        }
        return this;
    }

    validateName(name) {
        if (!this.status) return this
        if (!ValidationUtils.REGEX_USERNAME.test(name)) {
            this.status = false
            this.msg = ValidationUtils.EM_NAME
        }
        return this;
    }

    validateUsername(username) {
        if (!this.status) return this
        if (!ValidationUtils.REGEX_EMAIL.test(username) && !ValidationUtils.REGEX_MOBILE.test(username)) {
            this.status = false
            this.msg = ValidationUtils.EM_USERNAME
        }
        return this;
    }
}

export default ValidationUtils
