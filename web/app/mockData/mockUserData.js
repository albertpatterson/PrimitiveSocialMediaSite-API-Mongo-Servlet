"use strict";
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var User_1 = require("../member/User");
var Post_1 = require("../member/Post");
var UserData = (function (_super) {
    __extends(UserData, _super);
    function UserData() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    return UserData;
}(User_1.User));
var imgCount = 0;
function imageIng() {
    imgCount = (imgCount + 1) % 20;
    return "static/" + imgCount + ".png";
}
function makeMockUserData(username, following, followedBy) {
    return {
        name: username,
        age: 0,
        location: username + "_location",
        business: username + "_business",
        picture: imageIng(),
        followedBy: followedBy,
        following: following,
        ownPosts: [{ poster: username, content: "post1" }, { poster: username, content: "post2" }],
        messages: [{ poster: "friend1", content: "post1" }, { poster: "friend2", content: "post2" }],
        password: username + "pw",
        premiumContent: [imageIng(), imageIng(), imageIng()]
    };
}
var usernames = ["Anne", "Kim", "Dan", "Bob", "Pam", "Jen"];
var mud = {};
usernames.forEach(function (name, idx) {
    var start = (idx + 1) % usernames.length;
    var followedBy = usernames.slice(start, start + 2);
    start = (idx + 3) % usernames.length;
    var following = usernames.slice(start, start + 2);
    console.log(name, following);
    mud[name] = makeMockUserData(name, followedBy, following);
});
exports.mockUserData = mud;
function addUser(username, password) {
    mud[username] = makeMockUserData(username, [], []);
    mud[username].password = password;
}
exports.addUser = addUser;
function addPost(username, content) {
    mud[username].ownPosts.push(new Post_1.Post(username, content));
}
exports.addPost = addPost;
function getOwnPosts(username) {
    return mud[username].ownPosts.slice();
}
exports.getOwnPosts = getOwnPosts;
function addMessage(recipient, sender, content) {
    mud[recipient].messages.push(new Post_1.Post(sender, content));
}
exports.addMessage = addMessage;
function getMessages(username) {
    return mud[username].messages.slice();
}
exports.getMessages = getMessages;
function addPremuimContent(recipient) {
    // mud[recipient].messages.push(new Post(sender, content));
}
exports.addPremuimContent = addPremuimContent;
function getPremuimContent(username) {
    return mud[username].premiumContent.slice();
}
exports.getPremuimContent = getPremuimContent;
//# sourceMappingURL=mockUserData.js.map