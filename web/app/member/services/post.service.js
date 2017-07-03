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
var handleResponse_1 = require("../../utils/handleResponse");
require("rxjs/add/operator/toPromise");
var PostService = (function () {
    function PostService(http) {
        this.http = http;
        this._postUrl = "/post";
    }
    PostService.prototype.getFollowedPosts = function (username) {
        return this._getPosts(username, "followed");
    };
    PostService.prototype.getOwnPosts = function (username) {
        return this._getPosts(username, "own");
    };
    PostService.prototype._getPosts = function (username, type) {
        var _this = this;
        return new Promise(function (res, rej) {
            var data = new http_1.URLSearchParams();
            data.append('username', username);
            data.append('type', type);
            var resolver = function (resp) { return res(resp.json().data); };
            _this.http.get(_this._postUrl, { search: data })
                .toPromise()
                .then(function (resp) { return handleResponse_1.assertStatus(resolver, resp, 200, "Could not get posts."); })
                .catch(function (err) { return handleResponse_1.handleError(rej, err); });
        });
    };
    // }
    PostService.prototype.addPost = function (username, content) {
        var _this = this;
        console.log('add post', username, content);
        return new Promise(function (res, rej) {
            var data = new http_1.URLSearchParams();
            data.append('username', username);
            data.append('content', content);
            _this.http.post(_this._postUrl, data)
                .toPromise()
                .then(function (resp) { return handleResponse_1.assertStatus(res, resp, 201, "Could not add post."); })
                .catch(function (err) { return handleResponse_1.handleError(rej, err); });
        });
    };
    PostService.prototype.deletePost = function (username, idx) {
        var _this = this;
        return new Promise(function (res, rej) {
            var data = new http_1.URLSearchParams();
            data.append('username', username);
            data.append('index', idx.toString());
            _this.http.post(_this._postUrl, data)
                .toPromise()
                .then(function (resp) { return handleResponse_1.assertStatus(res, resp, 204, "Could not delete post."); })
                .catch(function (err) { return handleResponse_1.handleError(rej, err); });
        });
    };
    return PostService;
}());
PostService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http])
], PostService);
exports.PostService = PostService;
//# sourceMappingURL=post.service.js.map