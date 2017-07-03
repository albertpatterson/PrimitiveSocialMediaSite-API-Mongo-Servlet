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
var http_1 = require("@angular/http");
require("rxjs/add/operator/toPromise");
require("rxjs/add/operator/catch");
require("rxjs/add/operator/map");
require("rxjs/add/observable/throw");
var handleResponse_1 = require("../utils/handleResponse");
var AuthService = (function () {
    function AuthService(http, router) {
        this.http = http;
        this.router = router;
        this._loginUrl = "/session";
    }
    AuthService.prototype.tryLogin = function (username, password) {
        var _this = this;
        var data = new http_1.URLSearchParams();
        data.append('username', username);
        data.append('password', password);
        return new Promise(function (res, rej) {
            _this.http.post(_this._loginUrl, data)
                .toPromise()
                .then(function (resp) { return handleResponse_1.assertStatus(res, resp, 201, "Sign-in failed"); })
                .catch(function (err) { return handleResponse_1.handleError(rej, err); });
        });
        // return new Observable((o:any)=>o.next(true))
        // return   this.http.post(this._loginUrl, data)
        //         .map(this._checkStatus)
        //         .catch(this._handleError)
    };
    AuthService.prototype.trySignup = function (username, password) {
        return Promise.resolve(username === "false");
    };
    AuthService.prototype.assertLoggedIn = function (username) {
        // alert('checking login');
        // send a request to check session status
        console.log("check session " + username);
        var data = new http_1.URLSearchParams();
        data.append('username', username);
        return new Promise(function (res, rej) {
            var _this = this;
            var rejector = function () { _this.router.navigate(['/sign-in']); rej(); };
            this.http.get(this._loginUrl, { search: data })
                .toPromise()
                .then(function (resp) { return handleResponse_1.assertStatus(res, resp, 200, "invalid session"); })
                .catch(function (err) { return handleResponse_1.handleError(rejector, err); });
        }.bind(this));
        // this.http.get(this._loginUrl, data)
        // .map(this._checkStatus)
        // .catch(this._handleError)
        // new Observable((o:any)=>o.next(username==="member"))
        // .subscribe(function(isLoggedIn:boolean){
        //     if(isLoggedIn){
        //         res();
        //     }else{
        //         this.router.navigate(['/sign-in'])
        //         rej("Not logged in. Navigated to sign-in.");
        //     }
        // }.bind(this))
        // }.bind(this))
    };
    AuthService.prototype.signout = function (username) {
        console.log("delete session " + username);
        var data = new http_1.URLSearchParams();
        data.append('username', username);
        return new Promise(function (res, rej) {
            this.http.delete(this._loginUrl, { search: data })
                .toPromise()
                .then(function (resp) { return handleResponse_1.assertStatus(res, resp, 204, "invalid session, signout failed"); })
                .catch(function (err) { return handleResponse_1.handleError(rej, err); });
        }.bind(this));
    };
    return AuthService;
}());
AuthService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http,
        router_1.Router])
], AuthService);
exports.AuthService = AuthService;
//# sourceMappingURL=auth.service.js.map