"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var mockUserData_1 = require("../../mockData/mockUserData");
var PostService = (function () {
    function PostService() {
    }
    PostService.prototype.getFollowedPosts = function (username) {
        // return Promise.resolve(mockPosts);
        return new Promise(function (res, rej) {
            console.log("mud", mockUserData_1.mockUserData[username]);
            var followedPosts = mockUserData_1.getOwnPosts(username);
            var following = mockUserData_1.mockUserData[username].following;
            console.log('following', following);
            following.forEach(function (othersname) {
                console.log('following ', othersname);
                var othersPosts = mockUserData_1.getOwnPosts(othersname);
                console.log('othersPosts', othersPosts);
                followedPosts.push.apply(followedPosts, othersPosts);
            });
            console.log('followedPosts', followedPosts);
            res(followedPosts);
        });
    };
    PostService.prototype.getOwnPosts = function (username) {
        return new Promise(function (res, rej) {
            res(mockUserData_1.getOwnPosts(username));
        });
    };
    PostService.prototype.putPublicPost = function (username, content) {
        return new Promise(function (res) {
            mockUserData_1.addPost(username, content);
            res();
        });
    };
    PostService.prototype.putPrivatePost = function (username, content, recipient) {
        return new Promise(function (res) {
            mockUserData_1.mockUserData[recipient].messages.push({ poster: username, content: content });
            res();
        });
    };
    return PostService;
}());
PostService = __decorate([
    core_1.Injectable()
], PostService);
exports.PostService = PostService;
//# sourceMappingURL=mock_post.service.js.map