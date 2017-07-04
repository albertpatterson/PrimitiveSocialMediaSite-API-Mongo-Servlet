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
var http_1 = require("@angular/http");
require("rxjs/add/operator/toPromise");
var handleResponse_1 = require("../../utils/handleResponse");
var PersonalDataService = (function () {
    function PersonalDataService(http) {
        this.http = http;
        this._personalDataUrl = "/personalData";
    }
    PersonalDataService.prototype.getUserData = function (username, desiredUserName) {
        var resolverFactory = function (res) { return function (resp) { return res(resp.json()); }; };
        return this._getUserData(username, "desiredUserName", desiredUserName, resolverFactory);
    };
    PersonalDataService.prototype.searchUserData = function (username, desiredUserQuery) {
        var resolverFactory = function (res) { return function (resp) { return res(resp.json().data); }; };
        return this._getUserData(username, "desiredUserQuery", desiredUserQuery, resolverFactory);
    };
    PersonalDataService.prototype._getUserData = function (username, paramType, param, resolverFactory) {
        var _this = this;
        return new Promise(function (res, rej) {
            var data = new http_1.URLSearchParams();
            data.append('username', username);
            data.append(paramType, param);
            var resolver = resolverFactory(res);
            _this.http.get(_this._personalDataUrl, { search: data })
                .toPromise()
                .then(function (resp) { return handleResponse_1.assertStatus(resolver, resp, 200, "Could not get personal data."); })
                .catch(function (err) { return handleResponse_1.handleError(rej, err); });
        });
    };
    return PersonalDataService;
}());
PersonalDataService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http])
], PersonalDataService);
exports.PersonalDataService = PersonalDataService;
//# sourceMappingURL=personal-data.service.js.map