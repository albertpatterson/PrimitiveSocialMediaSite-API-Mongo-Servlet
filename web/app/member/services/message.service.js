"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var mockPosts = [
    { poster: 'Carl', content: "Message From Carl" },
    { poster: 'Alan', content: "Message From Alan" },
    { poster: 'Mike', content: "Message From Mike" }
];
var MessageService = (function () {
    function MessageService() {
    }
    MessageService.prototype.getMessageCount = function () {
        // return Promise.resolve(mockPosts.length);
        return this.getMessages().then(function (ms) { return ms.length; });
    };
    MessageService.prototype.getMessages = function () {
        // return Promise.resolve(mockPosts);
        return new Promise(function (r) { return setTimeout(function () { return r(mockPosts); }, 1e3); });
    };
    return MessageService;
}());
MessageService = __decorate([
    core_1.Injectable()
], MessageService);
exports.MessageService = MessageService;
//# sourceMappingURL=message.service.js.map