"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var auth_service_1 = require("./../services/auth.service");
var Model = (function () {
    function Model() {
    }
    return Model;
}());
var SignInComponent = (function () {
    function SignInComponent(authService, router) {
        this.authService = authService;
        this.router = router;
        this.usernamePattern = "\\w{1,10}";
        this.usernameDiagnostic = "username must be alphanumeric and be between 1 and 10 characters";
        this.passwordPattern = "\\S{1,10}";
        this.passwordDiagnostic = "password must contain no white space and be between 1 and 10 characters";
        this.signUpUsername = this.username;
        this.signUpPassword = this.password;
        this.locationPattern = ".*";
        this.locationDiagnostic = "Your current location";
        this.DOBDiagnostic = "Your birth date";
        this.businessPattern = ".*";
        this.businessDiagnostic = "Your current business";
        this.pictureDiagnostic = "Your photo";
        this.invalidCredentials = false;
        this.invalidCredentialsBaseDiagnostic = "Invalid username and/or password!";
        this.invalidCredentialsDiagnostic = "";
    }
    SignInComponent.prototype.ngOnInit = function () {
        this.DOBMax = this._getTodaysDate();
    };
    SignInComponent.prototype.signIn = function () {
        this.authService.tryLogin(this.username, this.password)
            .subscribe(this._handleLoginResult.bind(this), this._handleLoginError.bind(this));
    };
    SignInComponent.prototype._handleLoginResult = function (isValid) {
        if (isValid) {
            this.router.navigate(['/member/home']);
        }
        else {
            this._handleLoginError('');
        }
    };
    SignInComponent.prototype._handleLoginError = function (error) {
        this.invalidCredentials = true;
        this.password = '';
        this.invalidCredentialsDiagnostic = this.invalidCredentialsBaseDiagnostic + (error ? (" " + error) : null);
    };
    SignInComponent.prototype.signUp = function () {
        this.signIn();
    };
    SignInComponent.prototype.addPicture = function (event) {
        this.picture = event.srcElement.files[0];
        console.log(this.picture);
    };
    SignInComponent.prototype._getTodaysDate = function () {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        return year + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day);
    };
    return SignInComponent;
}());
SignInComponent = __decorate([
    core_1.Component({
        selector: 'sign-in',
        templateUrl: './sign-in.component.html',
        styleUrls: ['./sign-in.component.css']
    }),
    __metadata("design:paramtypes", [auth_service_1.AuthService,
        router_1.Router])
], SignInComponent);
exports.SignInComponent = SignInComponent;
//# sourceMappingURL=sign-in.component.js.map