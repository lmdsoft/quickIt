<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>QuickIT系统</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.css">
    <link rel="stylesheet" href="css/AdminLTE.min.css">
    <link rel="stylesheet" href="css/all-skins.min.css">
    <link rel="stylesheet" href="css/main.css">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</head>

<body class="hold-transition login-page" style="background:#1F3567">
<div class="login-box" id="rrapp" style="margin-top: 12%" v-cloak>
    <div class="login-box-body">
        <p class="login-box-msg" style="font-size: 25px;font-weight:bold">QuickIT系统</p>
        <div v-if="error" class="alert alert-danger alert-dismissible">
            <h4 style="margin-bottom: 0px;"><i class="fa fa-exclamation-triangle"></i> {{errorMsg}}</h4>
        </div>
        <div class="form-group has-feedback">
            <input type="text" class="form-control" v-model="username" placeholder="账号">
            <span class="fa fa-user form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
            <input type="password" class="form-control" v-model="password" placeholder="密码">
            <span class="fa fa-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
            <input type="text"style="width: 50%;display: inline-block" class="form-control" v-model="captcha" @keyup.enter="login" placeholder="验证码">
            <img alt="如果看不清楚，请单击图片刷新！" style="width: 45%;display: inline-block" class="pointer" :src="src" @click="refreshCode">
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox"  name="isRememberMe" v-model="isRememberMe">记住我,下次免登陆
            </label>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <button type="button" class="btn btn-block btn-success btn-lg" @click="login">登录</button>
            </div>
        </div>
    </div>
</div>
<script src="js/jquery.min.js"></script>
<script src="js/vue.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.slimscroll.min.js"></script>
<script src="js/fastclick.min.js"></script>
<script src="js/app.js"></script>
<script type="text/javascript">
    var vm = new Vue({
        el:'#rrapp',
        data:{
            username: '',
            password: '',
            captcha: '',
            error: false,
            errorMsg: '',
            isRememberMe:false,
            src: 'login/captcha'
        },
        beforeCreate: function(){
            if(self != top){
                top.location.href = self.location.href;
            }
        },
        methods: {
            refreshCode: function(){
                this.src = "login/captcha?t=" + $.now();
            },
            login: function (event) {
                var data = "username="+vm.username+"&password="+vm.password+"&captcha="+vm.captcha+"&isRememberMe="+vm.isRememberMe;
                $.ajax({
                    type: "POST",
                    url: "login/login",
                    data: data,
                    dataType: "json",
                    success: function(result){
                        if(result.code == 0){//登录成功
                            parent.location.href ='index';
                        }else{
                            vm.error = true;
                            vm.errorMsg = result.msg;
                            vm.refreshCode();
                        }
                    }
                });
            }
        }
    });
</script>
</body>
</html>