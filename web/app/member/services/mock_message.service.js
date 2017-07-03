"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var mockUserData_1 = require("../../mockData/mockUserData");
var MessageService = (function () {
    function MessageService() {
    }
    MessageService.prototype.getMessageCount = function (username) {
        // return Promise.resolve(mockPosts.length);
        return Promise.resolve(mockUserData_1.getMessages(username).length);
    };
    MessageService.prototype.getMessages = function (username) {
        // return Promise.resolve(mockPosts);
        // return new Promise(r=>setTimeout(()=>r(mockPosts),1e3));
        return Promise.resolve(mockUserData_1.getMessages(username));
    };
    MessageService.prototype.addMessage = function (recipient, sender, content) {
        mockUserData_1.addMessage(recipient, sender, content);
    };
    return MessageService;
}());
MessageService = __decorate([
    core_1.Injectable()
], MessageService);
exports.MessageService = MessageService;
//# sourceMappingURL=mock_message.service.js.map