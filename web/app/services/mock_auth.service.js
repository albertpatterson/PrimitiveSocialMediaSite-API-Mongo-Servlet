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
var mockUserData_1 = require("../mockData/mockUserData");
var AuthService = (function () {
    function AuthService(router) {
        this.router = router;
        this._loginUrl = "/session";
    }
    AuthService.prototype.tryLogin = function (username, password) {
        console.log(mockUserData_1.mockUserData[username]);
        console.log(mockUserData_1.mockUserData[username].password);
        console.log(password);
        return new Promise(function (res, rej) {
            if (mockUserData_1.mockUserData[username] && (mockUserData_1.mockUserData[username].password === password)) {
                this._session = username;
                res();
            }
            else {
                rej("Invalid username or password.");
            }
        }.bind(this));
    };
    // _checkStatus(resp:Response){
    //     return resp.status === 201;
    // }
    AuthService.prototype.trySignup = function (username, password) {
        return new Promise(function (res, rej) {
            if (!mockUserData_1.mockUserData[username]) {
                this._session = username;
                mockUserData_1.mockUserData.addUser(username, password);
                res(true);
            }
            else {
                res(false);
            }
        }.bind(this));
    };
    AuthService.prototype.assertLoggedIn = function (username) {
        return new Promise(function (res, rej) {
            console.log('session', this._session);
            if (username === this._session) {
                res();
            }
            else {
                this.router.navigate(['/sign-in']);
                rej("Not logged in. Navigated to sign-in.");
            }
        }.bind(this));
    };
    AuthService.prototype.signout = function () {
        return Promise.resolve();
    };
    return AuthService;
}());
AuthService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [router_1.Router])
], AuthService);
exports.AuthService = AuthService;
//# sourceMappingURL=mock_auth.service.js.map