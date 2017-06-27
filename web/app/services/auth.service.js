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
var Observable_1 = require("rxjs/Observable");
require("rxjs/add/operator/toPromise");
require("rxjs/add/operator/catch");
require("rxjs/add/operator/map");
require("rxjs/add/observable/throw");
var AuthService = (function () {
    function AuthService(http, router) {
        this.http = http;
        this.router = router;
        this._loginUrl = "/session";
    }
    AuthService.prototype.tryLogin = function (username, password) {
        var data = new http_1.URLSearchParams();
        data.append('username', username);
        data.append('password', password);
        return new Observable_1.Observable(function (o) { return o.next(true); });
        // return   this.http.post(this._loginUrl, data)
        //         .map(this._checkStatus)
        //         .catch(this._handleError)
    };
    AuthService.prototype._checkStatus = function (resp) {
        return resp.status === 201;
    };
    AuthService.prototype._handleError = function (error) {
        // In a real world app, you might use a remote logging infrastructure
        var errMsg;
        if (error instanceof http_1.Response) {
            errMsg = error.json().message;
        }
        else {
            errMsg = error.message ? error.message : error.toString();
        }
        return Observable_1.Observable.throw(errMsg);
    };
    AuthService.prototype.trySignup = function (username, password) {
        return Promise.resolve(username === "false");
    };
    AuthService.prototype.assertLoggedIn = function (username) {
        // alert('checking login');
        // send a request to check session status
        var data = new http_1.URLSearchParams();
        data.append('username', username);
        return new Promise(function (res, rej) {
            // this.http.get(this._loginUrl, data)
            // .map(this._checkStatus)
            // .catch(this._handleError)
            new Observable_1.Observable(function (o) { return o.next(username === "member"); })
                .subscribe(function (isLoggedIn) {
                if (isLoggedIn) {
                    res();
                }
                else {
                    this.router.navigate(['/sign-in']);
                    rej("Not logged in. Navigated to sign-in.");
                }
            }.bind(this));
        }.bind(this));
    };
    AuthService.prototype.signout = function () {
        return Promise.resolve();
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