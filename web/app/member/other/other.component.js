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
var search_service_1 = require("../services/search.service");
var post_service_1 = require("../services/post.service");
var OtherComponent = (function () {
    function OtherComponent(searchService, postService, activatedRoute) {
        this.searchService = searchService;
        this.postService = postService;
        this.activatedRoute = activatedRoute;
    }
    OtherComponent.prototype.ngOnInit = function () {
        this.activatedRoute.params
            .switchMap(function (params) {
            return this.searchService.search("^" + params["name"] + "$");
        }.bind(this))
            .subscribe(function (users) {
            if (users.length === 1) {
                this.user = users[0];
                this.postService.getOwnPosts(this.user.name)
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
        this.postService.putPrivatePost(message)
            .then(function () {
            alert("Message Sent!");
        }.bind(this));
    };
    return OtherComponent;
}());
OtherComponent = __decorate([
    core_1.Component({
        selector: 'member-other',
        templateUrl: './other.component.html',
        styleUrls: ['./../member.component.css', './other.component.css']
    }),
    __metadata("design:paramtypes", [search_service_1.SearchService,
        post_service_1.PostService,
        router_1.ActivatedRoute])
], OtherComponent);
exports.OtherComponent = OtherComponent;
//# sourceMappingURL=other.component.js.map