"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var mockPosts = [
    { poster: 'Carl', content: "From Carl" },
    { poster: 'Alan', content: "From Alan" },
    { poster: 'Mike', content: "From Mike" }
];
var PostService = (function () {
    function PostService() {
    }
    PostService.prototype.getFollowedPosts = function () {
        // return Promise.resolve(mockPosts);
        return new Promise(function (r) { return setTimeout(function () { return r(mockPosts); }, 1e3); });
    };
    PostService.prototype.getOwnPosts = function (user) {
        return new Promise(function (r) { return setTimeout(function () { return r(mockPosts); }, 1e3); });
    };
    PostService.prototype.putPublicPost = function (content) {
        var mockPost = { poster: "mockUser", content: content };
        mockPosts.unshift(mockPost);
        return Promise.resolve();
    };
    PostService.prototype.putPrivatePost = function (content) {
        return Promise.resolve();
    };
    return PostService;
}());
PostService = __decorate([
    core_1.Injectable()
], PostService);
exports.PostService = PostService;
//# sourceMappingURL=post.service.js.map