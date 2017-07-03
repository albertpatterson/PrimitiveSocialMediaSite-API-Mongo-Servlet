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
// import { SearchService } from '../services/search.service';
var mock_search_service_1 = require("../services/mock_search.service");
var post_service_1 = require("../services/post.service");
// import {PostService} from './../services/mock_post.service';
var message_service_1 = require("./../services/message.service");
// import {MessageService} from './../services/mock_message.service';
var subscription_service_1 = require("./../services/subscription.service");
var OtherComponent = (function () {
    function OtherComponent(searchService, postService, messageService, subscriptionService) {
        this.searchService = searchService;
        this.postService = postService;
        this.messageService = messageService;
        this.subscriptionService = subscriptionService;
    }
    OtherComponent.prototype.ngOnInit = function () {
        //    this.activatedRoute.params 
        //     .switchMap(function(params: Params){
        //         return this.searchService.search(`^${params["othersName"]}$`);     
        //     }.bind(this))
        console.log('otherName', this.othersName);
        this.searchService.search("^" + this.othersName + "$")
            .then(function (users) {
            if (users.length === 1) {
                this.user = users[0];
                this.postService.getOwnPosts(this.username, this.user.name)
                    .then(function (ownPosts) {
                    this.ownPosts = ownPosts;
                }.bind(this));
            }
            else {
                //handle error
                console.log('multiple matches');
            }
        }.bind(this));
    };
    OtherComponent.prototype.sendMessage = function (message) {
        alert(message);
        this.messageService.addMessage(this.username, message, this.othersName)
            .then(function () { return alert("Message Sent!"); });
    };
    OtherComponent.prototype.subscribe = function () {
        this.subscriptionService.addSubscription(this.username, this.user.name)
            .then(function () { return alert("Subscribed!"); });
    };
    return OtherComponent;
}());
OtherComponent = __decorate([
    core_1.Component({
        selector: 'member-other',
        inputs: ['username', 'othersName'],
        templateUrl: './other.component.html',
        styleUrls: ['./../member.component.css', './other.component.css']
    }),
    __metadata("design:paramtypes", [mock_search_service_1.SearchService,
        post_service_1.PostService,
        message_service_1.MessageService,
        subscription_service_1.SubscriptionService])
], OtherComponent);
exports.OtherComponent = OtherComponent;
//# sourceMappingURL=other.component.js.map